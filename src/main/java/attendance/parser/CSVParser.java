package attendance.parser;

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

public class CSVParser {
    public static void parseCrewFromCsv(Crews crews) {
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

    private static Attendance createAttendance(String crewNickName, String record){
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
