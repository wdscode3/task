package org.wds.taskmanager.domain.enums;

public enum StatusEnum {

    RUNNING, PAUSED, FINISHED;

    public boolean isRunning() {
        return RUNNING.equals(this);
    }

    public boolean isFinished() {
        return FINISHED.equals(this);
    }

    public boolean isPaused() {
        return PAUSED.equals(this);
    }
}
