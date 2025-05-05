package attendance.view;

import camp.nextstep.edu.missionutils.DateTimes;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.Locale;
import org.junit.jupiter.api.Test;

class OutputViewTest {

    OutputView outputView = new OutputView();

    @Test
    void 날짜_출력(){
        LocalDateTime today = DateTimes.now();
        System.out.println(today); // 전체
        System.out.println(today.getMonthValue()); // 월(숫자)
        System.out.println(today.getMonth()); // 월(영어)
        System.out.println(today.getDayOfMonth()); // 일(숫자)
        System.out.println(today.getDayOfWeek()); // 요일(영어)
        System.out.println(DateTimes.now().getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN)); // 요일(한국어)
    }

    @Test
    void 실행_날짜_출력(){
        outputView.printToday();
    }

}