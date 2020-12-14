package com.checkraiseit.adventofcode.y2020.d12;

public class Ferry {

    private Coordinates location;
    private Direction direction;
    private Coordinates waypoint;

    public Ferry() {
        location = new Coordinates(0, 0);
        waypoint = new Coordinates(10, 1);
        direction = new East();
    }

    public Coordinates currentLocation() {
        return location;
    }

    public void turnLeft() {
        direction = direction.left();
    }

    public void turnRight() {
        direction = direction.right();
    }

    public void moveForward(int value) {
        location = direction.move(location, value);
    }

    public void moveEast(int value) {
        location = location.east(value);
    }

    public void moveNorth(int value) {
        location = location.north(value);
    }

    public void moveSouth(int value) {
        location = location.south(value);
    }

    public void moveWest(int value) {
        location = location.west(value);
    }

    public void moveWaypointEast(int value) {
        waypoint = waypoint.east(value);
    }

    public void moveWaypointNorth(int value) {
        waypoint = waypoint.north(value);
    }

    public void moveWaypointSouth(int value) {
        waypoint = waypoint.south(value);
    }

    public void moveWaypointWest(int value) {
        waypoint = waypoint.west(value);
    }

    public void rotateWaypointLeft() {
        waypoint = waypoint.rotateLeftAboutOrigin();
    }

    public void rotateWaypointRight() {
        waypoint = waypoint.rotateRightAboutOrigin();
    }

    public void moveTowardWaypoint(int value) {
        for (int i = 0; i < value; i++) {
            location = location.moveToward(waypoint);
        }
    }
}
