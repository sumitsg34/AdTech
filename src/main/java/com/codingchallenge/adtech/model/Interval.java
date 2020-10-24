package com.codingchallenge.adtech.model;

import java.io.Serializable;
import java.util.Date;

public class Interval implements Serializable {

    private static final long serialVersionUID = 1l;

    Date start;
    Date end;

    public Interval(Date start, Date end) {
        this.start = start;
        this.end = end;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }
}
