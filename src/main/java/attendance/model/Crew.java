package attendance.model;

public class Crew {

    private final String nickName;
    private final Attendances attendances;

    public Crew(String nickName, Attendances attendances) {
        this.nickName = nickName;
        this.attendances = attendances;
    }

    public String getNickName() {
        return nickName;
    }

    // 출석 기록 추가
    public void addAttendance(Attendance attendance){
        this.attendances.addAttendance(attendance);
    }

    public boolean isYourNickName(String nickName){
        return this.nickName.equals(nickName);
    }

    // 당일 출석 기록 있는지 확인(기록 있으면 true, 없으면 false)
    public boolean checkTodayAttendance(){
        return attendances.isAttendToday();
    }
}
