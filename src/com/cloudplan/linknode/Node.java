package com.cloudplan.linknode;

import java.util.ArrayList;
import java.util.List;

public class Node {
    public List<Node> children=new ArrayList<>();
    public Node right;
    public String name;

    public Node(String name){
        this.name=name;
    }
}
