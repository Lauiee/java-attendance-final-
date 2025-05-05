package attendance.service;

import attendance.model.Attendance;
import attendance.model.Attendances;
import attendance.model.Crew;
import attendance.model.Crews;
import java.util.ArrayList;
import java.util.List;

// 크루별(개별) 출석 기록 확인 로직
public class PersonalAttendanceCheckService {

    public List<Attendance> checkCrewAttendances(Crews crews, String inputNickName) {
        Crew findCrew = findCrewByNickName(crews, inputNickName);
        return findCrew.getAttendances();
    }

    // 해당 닉네임을 가진 크루가 있는지 탐색
    private Crew findCrewByNickName(Crews crews, String nickName){
        Crew findCrew = crews.isCrewIn(nickName);

        // 있으면 해당 크루원 인스턴스 반환, 없다면 새로 만들어 인스턴스 반환
        if(findCrew == null){
            return new Crew(nickName, new ArrayList<>());
        }
        return findCrew;
    }
}
