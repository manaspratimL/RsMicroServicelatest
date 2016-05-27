package com.rs.ms.twitter;

import java.util.List;

import twitter4j.Status;

/**
 * Created by babluj on 5/9/16.
 */
public class TimeLine {

    private List<Status> statuses;

    TimeLine(List<Status> statuses) {
        this.statuses = statuses;
    }

    public void addStatus(Status status) {
        this.statuses.add(status);
    }

    public List<Status> getStatuses() {
        return this.statuses;
    }

    @Override
    public String toString() {
        return this.statuses.toString();
    }
}
