package attendance.view;

import camp.nextstep.edu.missionutils.Console;
import camp.nextstep.edu.missionutils.DateTimes;
import java.time.format.TextStyle;
import java.util.Locale;

public class InputView {

    public String inputFunctions(){
        System.out.println("1. 출석 확인");
        System.out.println("2. 출석 수정");
        System.out.println("3. 크루별 출석 기록 확인");
        System.out.println("4. 제적 위험자 확인");
        System.out.println("Q. 종료");

        return validateFunctionInput(Console.readLine());
    }

    private String validateFunctionInput(String s) {
        if (s.equals("1") || s.equals("2") || s.equals("3") || s.equals("4") || s.equals("Q")){
            return s;
        }
        throw new IllegalArgumentException("[ERROR] 잘못된 형식을 입력하였습니다.");
    }

    public String inputNickName(){
        System.out.println("\n닉네임을 입력해 주세요.");
        return Console.readLine();
    }

    public String inputNickNameToAttendance(){
        validateWeekend();
        System.out.println("\n닉네임을 입력해 주세요.");
        return Console.readLine();
    }

    private void validateWeekend() {
        int dayOfWeekValue = DateTimes.now().getDayOfWeek().getValue();
        if (dayOfWeekValue == 6 || dayOfWeekValue == 7){
            throw new IllegalArgumentException("[ERROR] " +
                    DateTimes.now().getMonthValue() +
                    "월 " +
                    DateTimes.now().getDayOfMonth() +
                    "일 " +
                    DateTimes.now().getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN)
                    +"은 등교일이 아닙니다.");
        }
    }

    public String inputAttendanceTime(){
        System.out.println("등교 시간을 입력해 주세요.");
        return validateTime(Console.readLine());
    }

    public String inputUpdateCrewNickName(){
        System.out.println("\n출석을 수정하려는 크루의 닉네임을 입력해 주세요.");
        return Console.readLine();
    }

    public String inputUpdateDay(){
        System.out.println("수정하려는 날짜(일)을 입력해 주세요.");
        return validateFuture(Console.readLine());
    }

    private String validateFuture(String input){
        if (Integer.parseInt(input) > DateTimes.now().getDayOfMonth()){
            throw new IllegalArgumentException("[ERROR] 아직 수정할 수 없습니다.");
        }
        return input;
    }

    public String inputUpdateTime(){
        System.out.println("언제로 변경하시겠습니까?");
        return validateTime(Console.readLine());
    }

    private String validateTime(String input){
        validateDelimiter(input);
        if (input.length() != 5){ // xx:xx 로 총 5개의 문자만이 입력됨
            throw new IllegalArgumentException("[ERROR] 잘못된 형식을 입력하였습니다.");
        }
        String[] timeSplit = input.split(":");
        int hour = Integer.parseInt(timeSplit[0]);
        int min = Integer.parseInt(timeSplit[1]);

        if (hour <= 8 || hour >= 23){  // 캠퍼스 운영시간 내로만 입력 가능
            if ((hour == 8 || hour == 23) && min == 0){
                return input;
            }
            throw new IllegalArgumentException("[ERROR] 잘못된 형식을 입력하였습니다.");
        }

        return input;
    }

    private void validateDelimiter(String input) {
        // 콜론(:)으로 나누었을 때, 콜론 이외의 문자가 포함되어 있으면 예외 처리
        if (!input.replaceAll(" ","").matches("[0-9:]+")) {
            throw new IllegalArgumentException("[ERROR] 잘못된 형식을 입력하였습니다.");
        }
    }
}
