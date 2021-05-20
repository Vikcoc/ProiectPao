package Services.Classes;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ReportServiceImpl {
    private static final String filePath = "D:\\faculta\\2sem2\\Pao\\Proiect\\ProiectPao\\src\\files\\report.csv";

    private ReportServiceImpl(){}

    private static class SINGLETON_HOLDER{
        private static final ReportServiceImpl INSTANCE = new ReportServiceImpl();
    }

    public static ReportServiceImpl getInstance(){
        return SINGLETON_HOLDER.INSTANCE;
    }

    public void Log(String event) {
        if(! Files.exists(Paths.get(filePath))) {
            try {
                Files.createFile(Paths.get(filePath));
                StringBuilder headers = new StringBuilder();
                //build header report
                headers.append("NUME_ACTIUNE");
                headers.append(",");
                headers.append("TIMESTAMP");
                headers.append("\n");
                Files.write(Paths.get(filePath), headers.toString().getBytes(StandardCharsets.UTF_8));

            }catch (IOException e) {
                System.out.println("log fail");
                return;
            }
        }

        var content = event + "," + new SimpleDateFormat("yyyy'_'MM'_'dd'_'mm'_'ss").format(new Date()) + '\n';
        try {
            Files.write(Paths.get(filePath), content.toString().getBytes(StandardCharsets.UTF_8), StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.out.println("log fail");
        }
    }
}
