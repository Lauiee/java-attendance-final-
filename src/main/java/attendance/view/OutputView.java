package attendance.view;

import attendance.model.Attendance;
import attendance.model.ExpelTarget;
import camp.nextstep.edu.missionutils.DateTimes;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public class OutputView {

    public void printToday(){
        LocalDateTime today = DateTimes.now();
        System.out.printf("오늘은 %d월 %d일 %s입니다. 기능을 선택해 주세요.\n",today.getMonthValue(), today.getDayOfMonth(), today.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN));
    }

    public void printAttendance(Attendance newAttendance){
        System.out.printf("\n%d월 %d일 %s %d:%d (%s)\n\n",
                newAttendance.getMonth(),
                newAttendance.getDay(),
                newAttendance.getDayOfWeek(),
                newAttendance.getHour(),
                newAttendance.getMinute(),
                newAttendance.getAttendanceResult());
    }

    public void printUpdateResult(Attendance before, Attendance after){
        System.out.printf("\n%d월 %d일 %s %d:%d (%s) -> %d:%d (%s) 수정 완료!\n\n",
                before.getMonth(),
                before.getDay(),
                before.getDayOfWeek(),
                before.getHour(),
                before.getMonth(),
                before.getAttendanceResult(),
                after.getHour(),
                after.getMinute(),
                after.getAttendanceResult());
    }

    public void printCheck(List<Attendance> attendances, String nickName){
        System.out.printf("\n이번 달 %s의 출석 기록입니다.\n", nickName);
        attendances.stream().sorted();

        int attendanceCount = 0;
        int lateCount = 0;
        int absenceCount = 0;

        for (Attendance attendance : attendances) {
            System.out.printf("\n%d월 %d일 %s %d:%d (%s)",
                    attendance.getMonth(),
                    attendance.getDay(),
                    attendance.getDayOfWeek(),
                    attendance.getHour(),
                    attendance.getMinute(),
                    attendance.getAttendanceResult());

            if (!attendance.getAttendanceResult().equals("출석")){
                if (!attendance.getAttendanceResult().equals("지각")){
                    absenceCount++;
                    continue;
                }
                lateCount++;
                continue;
            }
            attendanceCount++;
        }

        System.out.println();
        System.out.println();
        System.out.printf("출석: %d회\n", attendanceCount);
        System.out.printf("지각: %d회\n", lateCount);
        System.out.printf("결석: %d회\n", absenceCount);
        System.out.println();

        int absenceTotal = lateCount/3 + absenceCount;

        if (absenceTotal >= 2){
            if(absenceTotal >= 3){
                if (absenceTotal > 5){
                    System.out.println("제적 대상자입니다.\n");
                }
                System.out.println("면담 대상자입니다.\n");
            }
            System.out.println("경고 대상자입니다.\n");
        }
    }

    public void printExpelTargets(List<ExpelTarget> expelTargets){
        System.out.println("\n제적 위험자 조회 결과");

        Comparator<ExpelTarget> expelTargetComparator = Comparator
                .comparingInt((ExpelTarget e) -> e.getAbsenceCount() + (e.getLateCount() / 3))
                .reversed()
                .thenComparing(ExpelTarget::getNickName);

        List<ExpelTarget> realExpelTarget = expelTargets.stream()
                .filter(expelTarget -> expelTarget.getExpelStatus().equals("제적"))
                .sorted(expelTargetComparator)
                .toList();

        for (ExpelTarget target : realExpelTarget) {
            System.out.printf("- %s: 결석 %d회, 지각 %d회 (제적)\n", target.getNickName(), target.getAbsenceCount(), target.getLateCount());
        }

        List<ExpelTarget> interviewTarget = expelTargets.stream()
                .filter(expelTarget -> expelTarget.getExpelStatus().equals("인터뷰"))
                .sorted(expelTargetComparator)
                .toList();

        for (ExpelTarget target : interviewTarget) {
            System.out.printf("- %s: 결석 %d회, 지각 %d회 (면담)\n", target.getNickName(), target.getAbsenceCount(), target.getLateCount());
        }

        List<ExpelTarget> warningTarget = expelTargets.stream()
                .filter(expelTarget -> expelTarget.getExpelStatus().equals("경고"))
                .sorted(expelTargetComparator)
                .toList();

        for (ExpelTarget target : warningTarget) {
            System.out.printf("- %s: 결석 %d회, 지각 %d회 (경고)\n", target.getNickName(), target.getAbsenceCount(), target.getLateCount());
        }

        System.out.println();
    }
}
