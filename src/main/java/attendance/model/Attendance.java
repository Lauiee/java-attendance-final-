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

    // 과거 기록 생성자
    public Attendance(String creNickName, String attendanceDate, String attendanceTime){
        String[] dateSplit = attendanceDate.split("-");
        int year = Integer.parseInt(dateSplit[0]);
        int month = Integer.parseInt(dateSplit[1]);
        int date = Integer.parseInt(dateSplit[2]);

        this.crewNickName = creNickName;
        this.month = month;
        this.day = date;
        this.dayOfWeek = LocalDate.of(year,month,date).getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN);
        this.hour = Integer.parseInt(attendanceTime.substring(0,2));
        this.minute = Integer.parseInt(attendanceTime.substring(3,5));
        this.attendanceResult = getAttendanceResult(dayOfWeek, hour, minute);
    }

    // 해당 날짜 출석 메서드, 이미 출석 기록 존재하면 예외 발생
    public static Attendance checkAttendance(Crew crew, String attendanceTime){
        if(crew.checkTodayAttendance()){
            throw new IllegalArgumentException("[ERROR] 이미 출석을 확인하였습니다. 필요한 경우 수정 기능을 이용해 주세요.");
        }
        Attendance newAttendance = new Attendance(crew.getNickName(), attendanceTime);
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
        LocalDateTime time = LocalDateTime.of(1,1,1,1,1);
        int totalMinute = hour * HOUR_TO_MINUTE + minute;

        int classStartTime;

        if (dayOfWeek.equals("월요일")) {
            classStartTime = MONDAY_CLASS_START_TIME;
        } else classStartTime = REMAIN_WEEKDAY_CLASS_START_TIME;

        return decideAttendanceResult(totalMinute, classStartTime);
    }

    private String decideAttendanceResult(int totalMinute, int classStartTime) {
        if (totalMinute >= classStartTime * HOUR_TO_MINUTE + TARDY_LIMIT_TIME) { // true면 지각
            if (totalMinute >= classStartTime * HOUR_TO_MINUTE + ABSENCE_LIMIT_TIME) { // true면 결석
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
