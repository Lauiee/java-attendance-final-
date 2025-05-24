package attendance.model;

import java.util.ArrayList;
import java.util.List;

public class ExpelTarget {

    private final String nickName;
    private final int absenceCount;
    private final int lateCount;
    private final ExpelStatus expelStatus;

    public ExpelTarget(String nickName, int absenceCount, int lateCount, ExpelStatus expelStatus) {
        this.nickName = nickName;
        this.absenceCount = absenceCount;
        this.lateCount = lateCount;
        this.expelStatus = expelStatus;
    }

    public static List<ExpelTarget> findExpelTargets(Crews crews) {

        List<ExpelTarget> expelTargets = new ArrayList<>();

        for (Crew crew : crews.getCrews()) {
            ExpelTarget expelTarget = isExpelTarget(crew);
            if (expelTarget != null){
                expelTargets.add(expelTarget);
            }
        }

        return expelTargets;
    }

    private static ExpelTarget isExpelTarget(Crew crew) {
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

    public String getNickName() {
        return nickName;
    }

    public int getAbsenceCount() {
        return absenceCount;
    }

    public int getLateCount() {
        return lateCount;
    }

    public ExpelStatus getExpelStatus() {
        return expelStatus;
    }
}
