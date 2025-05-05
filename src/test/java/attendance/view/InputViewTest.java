package attendance.view;

import camp.nextstep.edu.missionutils.DateTimes;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class InputViewTest {

    InputView inputView = new InputView();

    @Test
    void 기능_목록_입력(){
        inputView.inputFunctions();
    }
}