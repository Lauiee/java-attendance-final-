package attendance.view;

import camp.nextstep.edu.missionutils.Console;
import camp.nextstep.edu.missionutils.DateTimes;
import java.time.LocalDateTime;

public class InputView {

    public String inputFunctions(){
        System.out.println("1. 출석 확인");
        System.out.println("2. 출석 수정");
        System.out.println("3. 크루별 출석 기록 확인");
        System.out.println("4. 제적 위험자 확인");
        System.out.println("0. 종료");

        return Console.readLine();
    }

    public String inputNickName(){
        System.out.println("\n닉네임을 입력해 주세요.");
        return Console.readLine();
    }

    public String inputAttendanceTime(){
        System.out.println("등교 시간을 입력해 주세요.");
        return Console.readLine();
    }
}
