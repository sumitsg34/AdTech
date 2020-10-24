package com.codingchallenge.adtech.model;

import java.io.Serializable;

public class OverallStatisticsResponse implements Serializable {

    private static final long serialVersionUID = 1l;

    private Interval interval;
    private Statistic statistic;

    public Interval getInterval() {
        return interval;
    }

    public void setInterval(Interval interval) {
        this.interval = interval;
    }

    public Statistic getStatistics() {
        return statistic;
    }

    public void setStatistics(Statistic statistic) {
        this.statistic = statistic;
    }
}
