package com.example.enyakinrestorant;

public class MapEdge {
    private String startId;
    private String endId;
    private double cost;

    public MapEdge() {}

    public MapEdge(String startId, String endId, double cost) {
        this.startId = startId;
        this.endId = endId;
        this.cost = cost;
    }

    public String getStartId() { return startId; }
    public void setStartId(String startId) { this.startId = startId; }

    public String getEndId() { return endId; }
    public void setEndId(String endId) { this.endId = endId; }

    public double getCost() { return cost; }
    public void setCost(double cost) { this.cost = cost; }
}