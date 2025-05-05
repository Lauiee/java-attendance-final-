package attendance.service;

import attendance.model.Attendance;
import attendance.model.Crew;
import attendance.model.Crews;
import attendance.model.ExpelStatus;
import attendance.model.ExpelTarget;
import java.util.ArrayList;
import java.util.List;

// 제적 위험자 확인 로직
public class ExpelDangerCheckService {

    public List<ExpelTarget> findExpelTargets(Crews crews) {

        List<ExpelTarget> expelTargets = new ArrayList<>();

        for (Crew crew : crews.getCrews()) {
            ExpelTarget expelTarget = isExpelTarget(crew);
            if (expelTarget != null){
                expelTargets.add(expelTarget);
            }
        }

        return expelTargets;
    }

    private ExpelTarget isExpelTarget(Crew crew) {
        List<Attendance> attendances = crew.getAttendances();

        int lateCount = 0;
        int absenceCount = 0;

        for (Attendance attendance : attendances) {
            String result = attendance.getAttendanceResult();
            if ("출석".equals(result)) {
                continue;
            }
            if ("지각".equals(result)) {
                lateCount++;
                continue;
            }
            absenceCount++;
        }

        int absenceTotal = (lateCount / 3) + absenceCount;

        if (absenceTotal > 5) return new ExpelTarget(crew.getNickName(), absenceCount, lateCount, ExpelStatus.EXPEL);
        if (absenceTotal >= 3) return new ExpelTarget(crew.getNickName(), absenceCount, lateCount, ExpelStatus.INTERVIEW);
        if (absenceTotal >= 2) return new ExpelTarget(crew.getNickName(), absenceCount, lateCount, ExpelStatus.WARNING);

        return null;
    }
}
