package attendance.model;

public enum AttendanceTimeConstants {

    HOUR_TO_MINUTE(60),
    TARDY_LIMIT_TIME(5),
    ABSENCE_LIMIT_TIME(30),
    MONDAY_CLASS_START_TIME(13),
    REMAIN_WEEKDAY_CLASS_START_TIME(10);

    private final int time;

    AttendanceTimeConstants(int time) {
        this.time = time;
    }

    public int getTime() {
        return time;
    }
}
