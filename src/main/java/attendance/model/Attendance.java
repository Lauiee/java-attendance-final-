package attendance.model;

import static attendance.model.AttendanceTimeConstants.ABSENCE_LIMIT_TIME;
import static attendance.model.AttendanceTimeConstants.HOUR_TO_MINUTE;
import static attendance.model.AttendanceTimeConstants.MONDAY_CLASS_START_TIME;
import static attendance.model.AttendanceTimeConstants.REMAIN_WEEKDAY_CLASS_START_TIME;
import static attendance.model.AttendanceTimeConstants.TARDY_LIMIT_TIME;

import camp.nextstep.edu.missionutils.DateTimes;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.Locale;

public class Attendance {

    private final String crewNickName;
    private final int month;
    private final int day;
    private final String dayOfWeek;
    private final int hour;
    private final int minute;
    private final String attendanceResult;

    public Attendance(String crewNickName, String attendanceTime)   {
        this.crewNickName = crewNickName;
        this.month = DateTimes.now().getMonthValue();
        this.day = DateTimes.now().getDayOfMonth();
        this.dayOfWeek = DateTimes.now().getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN);
        this.hour = Integer.parseInt(attendanceTime.substring(0,2));
        this.minute = Integer.parseInt(attendanceTime.substring(3,5));
        this.attendanceResult = getAttendanceResult(dayOfWeek, hour, minute);
    }

    // 수정용 생성자
    public Attendance(Attendance beforeAttendance, String afterAttendanceTime)   {
        this.crewNickName = beforeAttendance.getCrewNickName();
        this.month = beforeAttendance.getMonth();
        this.day = beforeAttendance.getDay();
        this.dayOfWeek = beforeAttendance.getDayOfWeek();
        this.hour = Integer.parseInt(afterAttendanceTime.substring(0,2));
        this.minute = Integer.parseInt(afterAttendanceTime.substring(3,5));
        this.attendanceResult = getAttendanceResult(dayOfWeek, hour, minute);
    }

    // 복사용 생성자
    public Attendance(Attendance copyTargetAttendance)   {
        this.crewNickName = copyTargetAttendance.getCrewNickName();
        this.month = copyTargetAttendance.getMonth();
        this.day = copyTargetAttendance.getDay();
        this.dayOfWeek = copyTargetAttendance.getDayOfWeek();
        this.hour = copyTargetAttendance.getHour();
        this.minute = copyTargetAttendance.getMinute();
        this.attendanceResult = copyTargetAttendance.getAttendanceResult();
    }

    private String getAttendanceResult(String dayOfWeek, int hour, int minute) {
        LocalDateTime time = LocalDateTime.of(1,1,1,1,1);
        int totalMinute = hour * HOUR_TO_MINUTE.getTime() + minute;

        int classStartTime;

        if (dayOfWeek.equals("월요일")) {
            classStartTime = MONDAY_CLASS_START_TIME.getTime();
        } else classStartTime = REMAIN_WEEKDAY_CLASS_START_TIME.getTime();

        return decideAttendanceResult(totalMinute, classStartTime);
    }

    private String decideAttendanceResult(int totalMinute, int classStartTime) {
        if (totalMinute >= classStartTime * HOUR_TO_MINUTE.getTime() + TARDY_LIMIT_TIME.getTime()) { // true면 지각
            if (totalMinute >= classStartTime * HOUR_TO_MINUTE.getTime() + ABSENCE_LIMIT_TIME.getTime()) { // true면 결석
                return "결석";
            }
            return "지각";
        }
        return "출석";
    }

    public String getCrewNickName() {
        return crewNickName;
    }

    public String getAttendanceResult() {
        return attendanceResult;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }
}
