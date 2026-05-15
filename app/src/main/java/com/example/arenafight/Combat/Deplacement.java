package com.example.arenafight.Combat;

public class Deplacement {
        private String nom;
        private int distance;

        public Deplacement(String nom, int distance) {
            this.nom = nom;
            this.distance = distance;
        }

        // Getters
        public String getNom() { return nom; }
        public int getDistance() { return distance; }
}
