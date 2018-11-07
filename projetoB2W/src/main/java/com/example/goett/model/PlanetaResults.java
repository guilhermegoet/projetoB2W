package com.example.goett.model;

import com.example.goett.model.Planetas;

import java.util.ArrayList;

public class PlanetaResults {

    private int count;
    private String next;
    private String previous;
    private ArrayList<Planetas> results;

    public PlanetaResults() {

    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public String getPrevious() {
        return previous;
    }

    public void setPrevious(String previous) {
        this.previous = previous;
    }

    public ArrayList<Planetas> getResults() {
        return results;
    }

    public void setResults(ArrayList<Planetas> results) {
        this.results = results;
    }


}
