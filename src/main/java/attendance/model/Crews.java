package attendance.model;

import java.util.ArrayList;
import java.util.List;

public class Crews {

    private final List<Crew> crews = new ArrayList<>();

    public Crew isCrewIn(String nickName){
        for (Crew crew : crews) {
            if (crew.isYourNickName(nickName)) return crew;
        }
        return null;
    }

    public void addCrew(Crew crew){
        crews.add(crew);
    }
}
