package attendance.service;

import static org.assertj.core.api.Assertions.*;
import attendance.model.Crew;
import attendance.model.Crews;
import java.io.FileNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AttendanceCheckServiceTest {

    private AttendanceCheckService acs;

    @BeforeEach
    void init(){
        acs = new AttendanceCheckService();
    }

    @Test
    void csv_출석_기록_생성() throws FileNotFoundException {
        // given
        Crews crews = new Crews();

        // when
        acs.parseCrewFromCsv(crews);
        Crew cookie = crews.getCrews().stream().filter(crew -> crew.getNickName().equals("쿠키")).findAny().orElseThrow();

        // then
        assertThat(crews.getCrews().size()).isEqualTo(5); // 5명의 Crew가 생성되었는지 검증
        assertThat(cookie.getAttendances().size()).isEqualTo(8); // 쿠키의 출석기록이 8개 들어갔는지 검증
    }

}