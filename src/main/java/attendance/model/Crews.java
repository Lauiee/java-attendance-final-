package attendance.model;

import java.util.ArrayList;
import java.util.List;

public class Crews {

    private final List<Crew> crews = new ArrayList<>();

    public List<Crew> getCrews() {
        return crews.stream()
                .map(crew -> new Crew(crew.getNickName(), crew.getAttendances()))
                .toList();
    }

    public Crew getCrewIfExist(String nickName){
        for (Crew crew : crews) {
            if (crew.isYourNickName(nickName)) return crew;
        }
        throw new IllegalArgumentException("[ERROR] 등록되지 않은 닉네임입니다.");
    }

    public List<Attendance> checkCrewAttendances(String inputNickName) {
        Crew findCrew = this.getCrewIfExist(inputNickName);
        return findCrew.getAttendances();
    }

    public void addCrew(Crew crew){
        crews.add(crew);
    }
}
