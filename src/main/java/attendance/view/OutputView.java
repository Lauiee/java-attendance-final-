package attendance.view;

import attendance.model.Attendance;
import camp.nextstep.edu.missionutils.DateTimes;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
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
        System.out.printf("\n%d월 %d일 %s %d:%d (%s) -> %d:%d (%s) 수정 완료!",
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
}
