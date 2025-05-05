package attendance.model;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public class Crews {

    private final List<Crew> crews = new ArrayList<>();

    public List<Crew> getCrews() {
        return crews.stream()
                .map(crew -> new Crew(crew.getNickName(), crew.getAttendances()))
                .toList();
    }

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
