package attendance;

import attendance.controller.AttendanceController;
import attendance.service.AttendanceCheckService;
import attendance.service.AttendanceUpdateService;
import attendance.service.ExpelDangerCheckService;
import attendance.service.PersonalAttendanceCheckService;
import attendance.view.InputView;
import attendance.view.OutputView;
import java.io.FileNotFoundException;

public class Application {
    public static void main(String[] args) throws FileNotFoundException {
        InputView inputView = new InputView();
        OutputView outputView = new OutputView();
        AttendanceCheckService attendanceCheckService = new AttendanceCheckService();
        AttendanceUpdateService attendanceUpdateService = new AttendanceUpdateService();
        ExpelDangerCheckService expelDangerCheckService = new ExpelDangerCheckService();
        PersonalAttendanceCheckService personalAttendanceCheckService = new PersonalAttendanceCheckService();

        AttendanceController attendanceController = new AttendanceController(
                inputView,outputView,
                attendanceCheckService,
                attendanceUpdateService,
                expelDangerCheckService,
                personalAttendanceCheckService
        );

        attendanceController.run();
    }
}
