package attendance.view;

import attendance.model.Attendance;
import attendance.model.Attendances;
import camp.nextstep.edu.missionutils.DateTimes;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
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
}
