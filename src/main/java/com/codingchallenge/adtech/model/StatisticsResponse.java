package com.codingchallenge.adtech.model;

import java.io.Serializable;
import java.util.List;

public class StatisticsResponse implements Serializable {

    private static final long serialVersionUID = 1l;

    private Interval interval;
    private List<Data> data;

    public Interval getInterval() {
        return interval;
    }

    public void setInterval(Interval interval) {
        this.interval = interval;
    }

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }
}
