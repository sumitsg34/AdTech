package com.codingchallenge.adtech.model;

import java.io.Serializable;
import java.util.Map;

public class Data implements Serializable {

    private Map<String, String> fields;
    private Statistic stats;

    public Map<String, String> getFields() {
        return fields;
    }

    public void setFields(Map<String, String> fields) {
        this.fields = fields;
    }

    public Statistic getStats() {
        return stats;
    }

    public void setStats(Statistic stats) {
        this.stats = stats;
    }
}
