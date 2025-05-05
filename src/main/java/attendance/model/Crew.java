package attendance.model;

import java.util.List;

public class Crew {

    private final String nickName;
    private final Attendances attendances;

    public Crew(String nickName, List<Attendance> attendances) {
        this.nickName = nickName;
        this.attendances = new Attendances(attendances);
    }

    public String getNickName() {
        return nickName;
    }

    // 출석 기록 추가
    public void addAttendance(Attendance attendance){
        this.attendances.addAttendance(attendance);
    }

    // 출석 기록 변경
    public Attendance updateAttendance(Attendance attendance, String updateTime){
        return attendances.updateAttendance(attendance, updateTime);
    }

    // 출석 기록 탐색
    public Attendance findAttendance(int updateDay){
        return attendances.findAttendance(this.nickName, updateDay);
    }

    // 전체 출석 기록 조회
    public List<Attendance> getAttendances(){
        return attendances.getAttendances();
    }

    public boolean isYourNickName(String nickName){
        return this.nickName.equals(nickName);
    }

    // 당일 출석 기록 있는지 확인(기록 있으면 true, 없으면 false)
    public boolean checkTodayAttendance(){
        return attendances.isAttendToday();
    }
}
