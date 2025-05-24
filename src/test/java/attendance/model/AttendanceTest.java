package attendance.model;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class AttendanceTest {

    @Test
    void 출석_기록_생성(){

        Attendance test = Attendance.createCurrentAttendance("test", "11:10");

        System.out.println(test.toString());

        assertThat(test.getCrewNickName()).isEqualTo("test");
        //assertThat(test.getAttendanceResult()).isEqualTo("결석");
    }

}