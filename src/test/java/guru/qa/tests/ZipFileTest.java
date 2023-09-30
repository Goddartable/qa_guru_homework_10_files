package guru.qa.tests;

import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.assertj.core.api.Assertions.assertThat;

public class ZipFileTest {
    private String pathToPrepackageFile = "src/test/resources/zip/files.zip";
    @Test
    @DisplayName("Проверка содержимого zip-архива")
    void checkingTheZipContents() throws IOException, CsvException {
        try (ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(pathToPrepackageFile))) {
            ZipEntry entry;
            while ((entry = zipInputStream.getNextEntry()) != null) {
                switch (entry.getName().substring(((ZipEntry) entry).getName().lastIndexOf('.'))) {
                    case ".pdf":
                        PDF pdf = new PDF(zipInputStream);
                        assertThat(pdf.text).contains("Hello World");
                        break;
                    case ".xlsx":
                        XLS xls = new XLS(zipInputStream);
                        assertThat(xls.excel.getSheetAt(0)
                                .getRow(1)
                                .getCell(0)
                                .getStringCellValue()).contains("plain text");
                        break;
                    case ".csv":
                        CSVReader csvFileReader = new CSVReader(new InputStreamReader(zipInputStream, UTF_8));
                        List<String[]> csvList = csvFileReader.readAll();
                        assertThat(csvList).contains(new String[]{"Junit 5", "Writing JUnit 5 Tests"});
                        break;
                }
            }
        }
    }
}
