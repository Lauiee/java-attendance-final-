package attendance.model;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class AttendancesTest {

    @Test
    void 기록_탐색(){
        Attendances attendances = new Attendances();
        attendances.addAttendance(new Attendance("test", "13:10"));

        Attendance findAttendance = attendances.findAttendance("test", 5);

        assertThat(findAttendance.getCrewNickName()).isEqualTo("test");
        assertThat(findAttendance.getDay()).isEqualTo(5);
    }
}