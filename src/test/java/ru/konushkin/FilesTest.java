package ru.konushkin;

import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVReader;
import models.Person;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class FilesTest {
    private ClassLoader cl = FilesTest.class.getClassLoader();
    private static final ObjectMapper mapper = new ObjectMapper();


    @Test
    void xlsxFileInZipParsingTest() throws Exception {
        try (ZipInputStream inputStream = new ZipInputStream(
                cl.getResourceAsStream("zip/zip-file4.zip")
        )) {
            ZipEntry entry;
            while ((entry = inputStream.getNextEntry()) != null) {
                if (entry.getName().endsWith(".xlsx")) {
                    XLS xls = new XLS(inputStream);
                    String actualValue = xls.excel.getSheetAt(0).getRow(0).getCell(0).getStringCellValue();
                    assertTrue(actualValue.contains("Килограмм картошки"));
                }
            }
        }
    }


    @Test
    void pdfFileInZipParsingTest() throws Exception {
        try (ZipInputStream inputStream = new ZipInputStream(
                cl.getResourceAsStream("zip/zip-file4.zip")
        )) {
            ZipEntry entry;
            while ((entry = inputStream.getNextEntry()) != null) {
                if (entry.getName().endsWith(".pdf")) {
                    PDF pdf = new PDF(inputStream);
                    assertEquals(1,pdf.numberOfPages);
                }
            }
        }
    }


    @Test
    void csvFileInZipParsingTest() throws Exception {
        try (ZipInputStream inputStream = new ZipInputStream(
                cl.getResourceAsStream("zip/zip-file4.zip")
        )) {
            ZipEntry entry;
            while ((entry = inputStream.getNextEntry()) != null) {
                if (entry.getName().endsWith(".csv")) {
                    CSVReader csvReader = new CSVReader(new InputStreamReader(inputStream));

                    List<String[]> data = csvReader.readAll();
                    assertEquals(2, data.size());
                    Assertions.assertArrayEquals(
                            new String[] {"Selenide", "https://selenide.org"},
                            data.get(0)
                    );
                    Assertions.assertArrayEquals(
                            new String[] {"JUnit 5","https://junit.org"},
                            data.get(1)
                    );
                }
            }
        }
    }


    @Test
    void jsonFileParsingImprovedTestJackson2() throws Exception {
        try (Reader reader = new InputStreamReader(
                cl.getResourceAsStream("ivan.json")
        )) {
            Person actual = mapper.readValue(reader, Person.class);
            assertEquals("Иванов Иван", actual.getValue());
            assertEquals("MALE", actual.getData().getGender());
        }
    }
}


