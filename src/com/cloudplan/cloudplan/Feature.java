package com.cloudplan.cloudplan;

public class Feature {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Feature && name.equals(((Feature) obj).name);
    }
}
