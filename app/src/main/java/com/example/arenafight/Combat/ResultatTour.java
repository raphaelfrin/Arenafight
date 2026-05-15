package com.example.arenafight.Combat;

public class ResultatTour {
    private boolean defenseActive;
    private int distance;

    public ResultatTour(boolean defenseActive, int distance) {
        this.defenseActive = defenseActive;
        this.distance = distance;
    }

    public boolean isDefenseActive() {
        return defenseActive;
    }

    public int getDistance() {
        return distance;
    }
}