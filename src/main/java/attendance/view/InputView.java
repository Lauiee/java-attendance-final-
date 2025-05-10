package attendance.view;

import camp.nextstep.edu.missionutils.Console;

public class InputView {

    public String inputFunctions(){
        System.out.println("1. 출석 확인");
        System.out.println("2. 출석 수정");
        System.out.println("3. 크루별 출석 기록 확인");
        System.out.println("4. 제적 위험자 확인");
        System.out.println("Q. 종료");

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

    public String inputUpdateCrewNickName(){
        System.out.println("\n출석을 수정하려는 크루의 닉네임을 입력해 주세요.");
        return Console.readLine();
    }

    public String inputUpdateDay(){
        System.out.println("수정하려는 날짜(일)을 입력해 주세요.");
        return Console.readLine();
    }

    public String inputUpdateTime(){
        System.out.println("언제로 변경하시겠습니까?");
        return Console.readLine();
    }
}
