package attendance.model;

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
