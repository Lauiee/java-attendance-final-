package attendance.service;

import attendance.model.Attendance;
import attendance.model.Crew;
import attendance.model.Crews;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

// 출석 확인 로직
public class AttendanceCheckService {

    // 크루원 인스턴스가 있는지 확인하고, 있으면 반환하고 없으면 새로 만들어서 반환하는 메서드
    public Crew findOrCreateCrew(Crews crews, String nickName){
        Crew findCrew = crews.isCrewIn(nickName);

        // 있으면 해당 크루원 인스턴스 반환, 없다면 새로 만들어 인스턴스 반환
        if(findCrew == null){
            Crew newCrew = new Crew(nickName, new ArrayList<>());
            crews.addCrew(newCrew);
            return newCrew;
        }
        return findCrew;
    }

    // 해당 날짜 출석 메서드, 이미 출석 기록 존재하면 예외 발생
    public Attendance checkAttendance(Crew crew, String attendanceTime){
        if(crew.checkTodayAttendance()){
            throw new IllegalArgumentException("[ERROR] 이미 출석을 확인하였습니다. 필요한 경우 수정 기능을 이용해 주세요.");
        }
        Attendance newAttendance = new Attendance(crew.getNickName(), attendanceTime);
        crew.addAttendance(newAttendance);

        return newAttendance;
    }

    public void parseCrewFromCsv(Crews crews) {
        String filePath = "src/main/resources/attendances.csv";
        Map<String, List<String>> records = new HashMap<>();

        try{
            parseRecordsFromCsv(filePath, records);
        } catch (FileNotFoundException e){
            e.printStackTrace();
        }

        for (String crewNickName : records.keySet()) {
            List<String> attendanceRecords = records.get(crewNickName);

            List<Attendance> attendances = attendanceRecords.stream()
                    .map(record -> createAttendance(crewNickName, record))
                    .collect(Collectors.toList());

            crews.addCrew(new Crew(crewNickName, attendances));
        }
    }

    private Attendance createAttendance(String crewNickName, String record){
        String[] recordSplit = record.split(" ");
        String attendanceDate = recordSplit[0];
        String attendanceTime = recordSplit[1];
        return new Attendance(crewNickName, attendanceDate, attendanceTime);
    }

    private static void parseRecordsFromCsv(String filePath, Map<String, List<String>> records) throws FileNotFoundException {
        try(FileReader fileReader = new FileReader(filePath)){
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            bufferedReader.readLine(); // 첫 줄 의미 없기에 그냥 readLine 1회 호출
            String attendanceRecord;
            while ((attendanceRecord = bufferedReader.readLine()) != null){
                String[] recordSplit = attendanceRecord.split(",");
                String nickname = recordSplit[0];
                String attendanceDateWithTime = recordSplit[1];

                if (records.containsKey(nickname)){
                    List<String> attendances = new ArrayList<>(records.get(nickname));
                    attendances.add(attendanceDateWithTime);
                    records.replace(nickname, attendances);
                }
                else records.put(nickname, List.of(attendanceDateWithTime));
            }
        } catch (Exception e){
            e.printStackTrace();
            throw new FileNotFoundException("[ERROR] CSV 파일 찾을 수 없음.");
        }
    }
}
