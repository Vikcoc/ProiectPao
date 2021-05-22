package Services.Classes;

import DataLayer.Entities.BaseEntity;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class TemplateInsertedReport <T extends BaseEntity>{
    private static final String filePath = "D:\\faculta\\2sem2\\Pao\\Proiect\\ProiectPao\\src\\files\\";

    private TemplateInsertedReport(){}

    private static class SINGLETON_HOLDER{
        private static final TemplateInsertedReport INSTANCE = new TemplateInsertedReport();
    }

    public static TemplateInsertedReport getInstance(){
        return TemplateInsertedReport.SINGLETON_HOLDER.INSTANCE;
    }

    public void WriteIt(T entity){
        if(! Files.exists(Paths.get(filePath + entity.getClass().getSimpleName() + ".csv"))){
            try {
                Files.createFile(Paths.get(filePath + entity.getClass().getSimpleName() + ".csv"));

                Files.write(Paths.get(filePath + entity.getClass().getSimpleName() + ".csv"),
                        entity.getHeaders().toString().getBytes(StandardCharsets.UTF_8));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            Files.write(Paths.get(filePath + entity.getClass().getSimpleName() + ".csv"),
                    entity.asCsv().getBytes(StandardCharsets.UTF_8), StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
