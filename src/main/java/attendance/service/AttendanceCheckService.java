package attendance.service;

import attendance.model.Attendance;
import attendance.model.Attendances;
import attendance.model.Crew;
import attendance.model.Crews;
import java.util.ArrayList;

// 출석 확인 로직
public class AttendanceCheckService {

    // 크루원 인스턴스가 있는지 확인하고, 있으면 반환하고 없으면 새로 만들어서 반환하는 메서드
    public Crew findOrCreateCrew(Crews crews, String nickName){
        Crew findCrew = crews.isCrewIn(nickName);

        // 있으면 해당 크루원 인스턴스 반환, 없다면 새로 만들어 인스턴스 반환
        if(findCrew == null){
            Crew newCrew = new Crew(nickName, new ArrayList<>());
            crews.addCrew(newCrew);
            return newCrew;
        }
        return findCrew;
    }

    // 해당 날짜 출석 메서드, 이미 출석 기록 존재하면 예외 발생
    public Attendance checkAttendance(Crew crew, String attendanceTime){
        if(crew.checkTodayAttendance()){
            throw new IllegalArgumentException("[ERROR] 이미 출석을 확인하였습니다. 필요한 경우 수정 기능을 이용해 주세요.");
        }
        Attendance newAttendance = new Attendance(crew.getNickName(), attendanceTime);
        crew.addAttendance(newAttendance);

        return newAttendance;
    }
}
