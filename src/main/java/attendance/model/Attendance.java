package attendance.model;


import camp.nextstep.edu.missionutils.DateTimes;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.Locale;

public class Attendance {

    private static final int HOUR_TO_MINUTE = 60;
    private static final int TARDY_LIMIT_TIME = 5;
    private static final int ABSENCE_LIMIT_TIME = 30;
    private static final int MONDAY_CLASS_START_TIME = 13;
    private static final int REMAIN_WEEKDAY_CLASS_START_TIME = 10;
    public static final String MONDAY = "월요일";
    public static final String ABSENSE = "결석";
    public static final String LATE = "지각";
    public static final String ATTENDENCE = "출석";

    private final String crewNickName;
    private final int month;
    private final int day;
    private final String dayOfWeek;
    private final int hour;
    private final int minute;
    private final String attendanceResult;

    // 기본 생성자는 private으로 만들어 직접적인 생성을 막을 수 있습니다.
    private Attendance(String crewNickName, int month, int day, String dayOfWeek, int hour, int minute) {
        this.crewNickName = crewNickName;
        this.month = month;
        this.day = day;
        this.dayOfWeek = dayOfWeek;
        this.hour = hour;
        this.minute = minute;
        this.attendanceResult = getAttendanceResult(dayOfWeek, hour, minute);
    }

    // 현재 시간 기준 출석 기록 생성
    public static Attendance createCurrentAttendance(String crewNickName, String attendanceTime) {
        LocalDateTime now = DateTimes.now();
        int currentMonth = now.getMonthValue();
        int currentDay = now.getDayOfMonth();
        String currentDayOfWeek = now.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN);
        int Hh = Integer.parseInt(attendanceTime.substring(0, 2));
        int mm = Integer.parseInt(attendanceTime.substring(3, 5));
        return new Attendance(crewNickName, currentMonth, currentDay, currentDayOfWeek, Hh, mm);
    }

    // 기존 출석 기록 기반 수정 (시간만 변경)
    public static Attendance createUpdatedAttendance(Attendance beforeAttendance, String afterAttendanceTime) {
        int Hh = Integer.parseInt(afterAttendanceTime.substring(0, 2));
        int mm = Integer.parseInt(afterAttendanceTime.substring(3, 5));
        // 기존 날짜 정보는 그대로 사용
        return new Attendance(
                beforeAttendance.getCrewNickName(),
                beforeAttendance.getMonth(),
                beforeAttendance.getDay(),
                beforeAttendance.getDayOfWeek(),
                Hh,
                mm
        );
    }

    // 출석 객체 복사
    public static Attendance copyOf(Attendance copyTargetAttendance) {
        return new Attendance(
                copyTargetAttendance.getCrewNickName(),
                copyTargetAttendance.getMonth(),
                copyTargetAttendance.getDay(),
                copyTargetAttendance.getDayOfWeek(),
                copyTargetAttendance.getHour(),
                copyTargetAttendance.getMinute()
        );
    }

    // 과거 날짜 기준 출석 기록 생성
    public static Attendance createPastAttendance(String crewNickName, String attendanceDate, String attendanceTime) {
        String[] dateSplit = attendanceDate.split("-");
        int year = Integer.parseInt(dateSplit[0]);
        int month = Integer.parseInt(dateSplit[1]);
        int dayOfMonth = Integer.parseInt(dateSplit[2]);
        String dayOfWeek = LocalDate.of(year, month, dayOfMonth).getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN);
        int Hh = Integer.parseInt(attendanceTime.substring(0, 2));
        int mm = Integer.parseInt(attendanceTime.substring(3, 5));
        return new Attendance(crewNickName, month, dayOfMonth, dayOfWeek, Hh, mm);
    }


    // 해당 날짜 출석 메서드, 이미 출석 기록 존재하면 예외 발생
    public static Attendance checkAttendance(Crew crew, String attendanceTime){
        if(crew.checkTodayAttendance()){
            throw new IllegalArgumentException("[ERROR] 이미 출석을 확인하였습니다. 필요한 경우 수정 기능을 이용해 주세요.");
        }
        Attendance newAttendance = Attendance.createCurrentAttendance(crew.getNickName(), attendanceTime);
        crew.addAttendance(newAttendance);

        return newAttendance;
    }

    // 기록 수정 진행
    public static Attendance updateAttendance(Crew crew, Attendance targetAttendance, String updateTime){
        return crew.updateAttendance(targetAttendance, updateTime);
    }

    // 수정 대상 날짜(일) 기록 존재 탐색, 출석 기록 없으면 예외 발생
    public static Attendance findAttendance(Crew crew, int updateDay){
        return crew.findAttendance(updateDay);
    }

    private String getAttendanceResult(String dayOfWeek, int hour, int minute) {
        int totalMinute = hour * HOUR_TO_MINUTE + minute;

        int classStartTime;

        if (dayOfWeek.equals(MONDAY)) {
            classStartTime = MONDAY_CLASS_START_TIME;
        } else classStartTime = REMAIN_WEEKDAY_CLASS_START_TIME;

        return decideAttendanceResult(totalMinute, classStartTime);
    }

    private String decideAttendanceResult(int totalMinute, int classStartTime) {
        if (totalMinute >= classStartTime * HOUR_TO_MINUTE + TARDY_LIMIT_TIME) { // true면 지각
            if (totalMinute >= classStartTime * HOUR_TO_MINUTE + ABSENCE_LIMIT_TIME) { // true면 결석
                return ABSENSE;
            }
            return LATE;
        }
        return ATTENDENCE;
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
