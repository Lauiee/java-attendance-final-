package attendance.service;

import attendance.model.Attendance;
import attendance.model.Attendances;
import attendance.model.Crew;
import attendance.model.Crews;

// 출석 수정 로직
public class AttendanceUpdateService {

    // 해당 닉네임을 가진 크루가 있는지 탐색
    public Crew findCrewByNickName(Crews crews, String nickName){
        Crew findCrew = crews.isCrewIn(nickName);

        // 있으면 해당 크루원 인스턴스 반환, 없다면 새로 만들어 인스턴스 반환
        if(findCrew == null){
            return new Crew(nickName, new Attendances());
        }
        return findCrew;
    }

    // 수정 대상 날짜(일) 기록 존재 탐색, 출석 기록 없으면 예외 발생
    public Attendance findAttendance(Crew crew, int updateDay){
        return crew.findAttendance(updateDay);
    }

    // 기록 수정 진행
    public Attendance updateAttendance(Crew crew, Attendance targetAttendance, String updateTime){
        return crew.updateAttendance(targetAttendance, updateTime);
    }
}
