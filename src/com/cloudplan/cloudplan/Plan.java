package com.cloudplan.cloudplan;

import java.util.List;

public class Plan implements Comparable<Plan> {
    private String name;
    private double cost;
    private List<Feature> features;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public List<Feature> getFeatures() {
        return features;
    }

    public void setFeatures(List<Feature> features) {
        this.features = features;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Plan && name.equals(((Plan) obj).name);
    }

    @Override
    public int compareTo(Plan o) {
        if (o.cost<cost){
            return 1;
        }
        else if (o.cost>cost){
            return -1;
        }
        else return 0;
    }

}
