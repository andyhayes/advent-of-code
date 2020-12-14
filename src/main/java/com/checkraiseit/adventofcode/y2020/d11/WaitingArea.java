package com.checkraiseit.adventofcode.y2020.d11;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.google.common.collect.Lists.newArrayList;

@RequiredArgsConstructor
@EqualsAndHashCode
public class WaitingArea {
    private final List<List<WaitingAreaSpace>> waitingAreaLists;

    WaitingArea tickWithOccupiedNeighbours() {
        return tick(this::numberOfOccupiedNeighbours);
    }

    WaitingArea tickWithVisiblyOccupiedNeighbours() {
        return tick(this::numberOfVisiblyOccupiedNeighbours);
    }

    WaitingArea tick(Function<Coordinates, Integer> occupiedSeatCalculation) {
        List<List<WaitingAreaSpace>> newWaitingAreaLists = newArrayList();
        for (int i = 0; i < waitingAreaLists.size(); i++) {
            List<WaitingAreaSpace> waitingAreaSpaces = waitingAreaLists.get(i);
            List<WaitingAreaSpace> newWaitingAreaSpaces = newArrayList();
            for (int j = 0; j < waitingAreaSpaces.size(); j++) {
                WaitingAreaSpace waitingAreaSpace = waitingAreaSpaces.get(j);
                int occupiedNeighbours = occupiedSeatCalculation.apply(new Coordinates(i, j));
                WaitingAreaSpace next = waitingAreaSpace.next(occupiedNeighbours);
                newWaitingAreaSpaces.add(next);
            }
            newWaitingAreaLists.add(newWaitingAreaSpaces);
        }
        return new WaitingArea(newWaitingAreaLists);
    }

    int numberOfVisiblyOccupiedNeighbours(Coordinates coordinates) {
        return visiblyOccupied(coordinates, new Coordinates(-1, -1)) +
                visiblyOccupied(coordinates, new Coordinates(-1, 0)) +
                visiblyOccupied(coordinates, new Coordinates(-1, +1)) +
                visiblyOccupied(coordinates, new Coordinates(0, -1)) +
                visiblyOccupied(coordinates, new Coordinates(0, +1)) +
                visiblyOccupied(coordinates, new Coordinates(+1, -1)) +
                visiblyOccupied(coordinates, new Coordinates(+1, 0)) +
                visiblyOccupied(coordinates, new Coordinates(+1, +1));
    }

    private int visiblyOccupied(Coordinates coordinates, Coordinates delta) {
        int i = coordinates.i;
        int j = coordinates.j;
        int maxI = waitingAreaLists.size();
        int maxJ = waitingAreaLists.get(0).size();
        for (i += delta.i, j += delta.j; i >= 0 && i < maxI && j >= 0 && j < maxJ; i += delta.i, j += delta.j) {
            List<WaitingAreaSpace> waitingAreaSpaces = waitingAreaLists.get(i);
            WaitingAreaSpace waitingAreaSpace = waitingAreaSpaces.get(j);
            if (waitingAreaSpace instanceof EmptySeat) {
                return 0;
            }
            if (waitingAreaSpace instanceof OccupiedSeat) {
                return 1;
            }
        }
        return 0;
    }

    private int numberOfOccupiedNeighbours(Coordinates coordinates) {
        return occupied(coordinates.i - 1, coordinates.j - 1) +
                occupied(coordinates.i - 1, coordinates.j) +
                occupied(coordinates.i - 1, coordinates.j + 1) +
                occupied(coordinates.i, coordinates.j - 1) +
                occupied(coordinates.i, coordinates.j + 1) +
                occupied(coordinates.i + 1, coordinates.j - 1) +
                occupied(coordinates.i + 1, coordinates.j) +
                occupied(coordinates.i + 1, coordinates.j + 1);
    }

    private int occupied(int i, int j) {
        if (i >= 0 && i < waitingAreaLists.size()) {
            List<WaitingAreaSpace> waitingAreaSpaces = waitingAreaLists.get(i);
            if (j >= 0 && j < waitingAreaSpaces.size()) {
                return occupied(waitingAreaSpaces.get(j));
            }
        }
        return 0;
    }

    private int occupied(WaitingAreaSpace waitingAreaSpace) {
        if (isOccupied(waitingAreaSpace)) {
            return 1;
        }
        return 0;
    }

    private boolean isOccupied(WaitingAreaSpace waitingAreaSpace) {
        return waitingAreaSpace instanceof OccupiedSeat;
    }

    long numberOfOccupiedSeats() {
        return waitingAreaLists.stream().map(this::numberOfOccupiedWaitingAreaSpaces).reduce(0L, Long::sum);
    }

    long numberOfOccupiedWaitingAreaSpaces(List<WaitingAreaSpace> waitingAreaSpaces) {
        return waitingAreaSpaces.stream().filter(waitingAreaSpace -> waitingAreaSpace instanceof OccupiedSeat).count();
    }

    @Override
    public String toString() {
        return "\n" + waitingAreaLists.stream()
                .map(was -> was.stream().map(WaitingAreaSpace::toString).collect(Collectors.joining("")))
                .collect(Collectors.joining("\n"));
    }
}
