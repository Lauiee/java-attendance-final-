package attendance.controller;

import attendance.model.Attendance;
import attendance.model.Attendances;
import attendance.model.Crew;
import attendance.model.Crews;
import attendance.service.AttendanceCheckService;
import attendance.service.AttendanceUpdateService;
import attendance.service.ExpelDangerCheckService;
import attendance.service.PersonalAttendanceCheckService;
import attendance.view.InputView;
import attendance.view.OutputView;

public class AttendanceController {

    private final InputView inputView;
    private final OutputView outputView;
    private final AttendanceCheckService attendanceCheckService;
    private final AttendanceUpdateService attendanceUpdateService;
    private final ExpelDangerCheckService expelDangerCheckService;
    private final PersonalAttendanceCheckService personalAttendanceCheckService;

    public AttendanceController(
            InputView inputView, OutputView outputView,
            AttendanceCheckService attendanceCheckService,
            AttendanceUpdateService attendanceUpdateService,
            ExpelDangerCheckService expelDangerCheckService,
            PersonalAttendanceCheckService personalAttendanceCheckService) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.attendanceCheckService = attendanceCheckService;
        this.attendanceUpdateService = attendanceUpdateService;
        this.expelDangerCheckService = expelDangerCheckService;
        this.personalAttendanceCheckService = personalAttendanceCheckService;
    }

    public void run() {

        // csv파일 읽어 미리 Crews 생성
        Crews crews = new Crews();

        // 오늘 날짜 출력
        outputView.printToday();

        // 기능 목록 출력 및 입력 루프
        while (true) {
            String inputFunction = inputView.inputFunctions();

            if (inputFunction.equals("Q")) break;

            if (inputFunction.equals("1")) {
                attendanceCheck(crews);
                continue;
            }
            if (inputFunction.equals("2")) {
                attendanceUpdate();
                continue;
            }
            if (inputFunction.equals("3")) {
                personalAttendancesCheck();
                continue;
            }
            if (inputFunction.equals("4")) {
                expelDangerCheck();
            }
        }
    }

    private void attendanceCheck(Crews crews) {

        // 닉네임 입력, 현존하는 크루원인지 확인 후 없던 크루원이면 새로 생성
        String inputNickName = inputView.inputNickName();
        Crew attendanceCrew = attendanceCheckService.findOrCreateCrew(crews, inputNickName);

        // 출석 시간 입력
        String attendanceTime = inputView.inputAttendanceTime();
        Attendance newAttendance = attendanceCheckService.checkAttendance(attendanceCrew, attendanceTime);

        // 출석 기록 출력
        outputView.printAttendance(newAttendance);
    }

    private void attendanceUpdate() {

    }

    private void personalAttendancesCheck() {

    }

    private void expelDangerCheck() {

    }
}
