package attendance.model;

import camp.nextstep.edu.missionutils.DateTimes;
import java.util.ArrayList;
import java.util.List;

public class Attendances {

    private final List<Attendance> attendances = new ArrayList<>();

    public void addAttendance(Attendance attendance){
        this.attendances.add(attendance);
    }

    // 당일 출석 기록 있다면 true, 없으면 false
    public boolean isAttendToday(){
        for (Attendance attendance : attendances){
            if (attendance.getMonth() == DateTimes.now().getMonthValue() && attendance.getDay() == DateTimes.now().getDayOfMonth()) return true;
        }
        return false;
    }
}
