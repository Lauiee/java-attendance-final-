package attendance.model;

import java.util.ArrayList;
import java.util.List;

public class ExpelTarget {

    private static final int EXPEL_LIMIT = 5;
    private static final int INTERVIEW_LIMIT = 3;
    private static final int WARNING_LIMIT = 2;
    private static final int LATE_TO_ABSENT = 3;

    public static final String ATTENDANCE = "출석";
    public static final String LATE = "지각";
    public static final String EXPEL = "제적";
    public static final String INTERVIEW = "인터뷰";
    public static final String WARNING = "경고";

    private final String nickName;
    private final int absenceCount;
    private final int lateCount;
    private final String expelStatus;

    public ExpelTarget(String nickName, int absenceCount, int lateCount, String expelStatus) {
        this.nickName = nickName;
        this.absenceCount = absenceCount;
        this.lateCount = lateCount;
        this.expelStatus = expelStatus;
    }

    public static List<ExpelTarget> findExpelTargets(Crews crews) {

        List<ExpelTarget> expelTargets = new ArrayList<>();

        for (Crew crew : crews.getCrews()) {
            ExpelTarget expelTarget = isExpelTarget(crew);
            if (expelTarget!=null) {
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
            if (ATTENDANCE.equals(result)) {
                continue;
            }
            if (LATE.equals(result)) {
                lateCount++;
                continue;
            }
            absenceCount++;
        }

        int absenceTotal = (lateCount / LATE_TO_ABSENT) + absenceCount;

        if (absenceTotal > EXPEL_LIMIT) return new ExpelTarget(crew.getNickName(), absenceCount, lateCount, EXPEL);
        if (absenceTotal >= INTERVIEW_LIMIT) return new ExpelTarget(crew.getNickName(), absenceCount, lateCount, INTERVIEW);
        if (absenceTotal >= WARNING_LIMIT) return new ExpelTarget(crew.getNickName(), absenceCount, lateCount, WARNING);

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

    public String getExpelStatus() {
        return expelStatus;
    }
}
