package attendance.model;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class AttendancesTest {

    @Test
    void 특정_기록_탐색(){
        Attendances attendances = new Attendances(new ArrayList<>());
        attendances.addAttendance(Attendance.createCurrentAttendance("test", "13:10"));

        Attendance findAttendance = attendances.findAttendance("test", 5);

        assertThat(findAttendance.getCrewNickName()).isEqualTo("test");
        assertThat(findAttendance.getDay()).isEqualTo(5);
    }

    @Test
    void 전체_기록_탐색(){
        Attendances attendances = new Attendances(new ArrayList<>());
        attendances.addAttendance(Attendance.createCurrentAttendance("test", "13:10"));

        List<Attendance> attendanceList = attendances.getAttendances();

        assertThat(attendanceList).size().isEqualTo(1);
    }
}