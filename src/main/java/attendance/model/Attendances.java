package attendance.model;

import camp.nextstep.edu.missionutils.DateTimes;
import java.util.ArrayList;
import java.util.List;

public class Attendances {

    private final List<Attendance> attendances;

    public Attendances(List<Attendance> attendances) {
        this.attendances = attendances;
    }

    public void addAttendance(Attendance attendance){
        this.attendances.add(attendance);
    }

    public List<Attendance> getAttendances() {
        return attendances.stream()
                .map(Attendance::copyOf)
                .toList();
    }

    // 당일 출석 기록 있다면 true, 없으면 false
    public boolean isAttendToday(){
        for (Attendance attendance : attendances){
            if (attendance.getMonth() == DateTimes.now().getMonthValue() && attendance.getDay() == DateTimes.now().getDayOfMonth()) return true;
        }
        return false;
    }

    public Attendance findAttendance(String nickName, int updateDay){
        // 기존 기록 탐색
        Attendance findAttendance = attendances.stream()
                .filter(attendance -> attendance.getCrewNickName().equals(nickName))
                .filter(attendance -> attendance.getDay() == updateDay)
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 수정 대상 출석 기록을 찾을 수 없습니다."));

        return Attendance.copyOf(findAttendance);
    }

    public Attendance updateAttendance(Attendance targetAttendance, String updateTime){
        // 기존 기록 제거
        attendances.removeIf(a -> a.getCrewNickName().equals(targetAttendance.getCrewNickName()) && a.getDay() ==targetAttendance.getDay());

        // 새로운 수정된 기록 생성
        Attendance newAttendance = Attendance.createUpdatedAttendance(targetAttendance, updateTime);
        addAttendance(newAttendance);

        return Attendance.copyOf(newAttendance);
    }
}
