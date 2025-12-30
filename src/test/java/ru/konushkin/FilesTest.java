package ru.konushkin;

import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVReader;
import models.Person;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

public class FilesTest {
    private ClassLoader cl = FilesTest.class.getClassLoader();
    private static final ObjectMapper mapper = new ObjectMapper();


    @Test
    void zipTestXlsx() throws Exception {
        ZipFile zf = new ZipFile(new File("src/test/resources/zip/zip-file.zip"));
        ZipInputStream is = new ZipInputStream(cl.getResourceAsStream("zip/zip-file.zip"));
        ZipEntry entry;
        while((entry = is.getNextEntry()) != null) {
            org.assertj.core.api.Assertions.assertThat(entry.getName()).isEqualTo("1.xlsx");
            try (InputStream inputStream = zf.getInputStream(entry)) {
                XLS xls = new XLS(inputStream);
                String actualValue = xls.excel.getSheetAt(0).getRow(0).getCell(0).getStringCellValue();
                Assertions.assertTrue(actualValue.contains("Килограмм картошки"));

            }
        }
    }

    @Test
    void zipTestPdf() throws Exception {
        ZipFile zf = new ZipFile(new File("src/test/resources/zip/zip-file2.zip"));
        ZipInputStream is = new ZipInputStream(cl.getResourceAsStream("zip/zip-file2.zip"));
        ZipEntry entry;
        while((entry = is.getNextEntry()) != null) {
            org.assertj.core.api.Assertions.assertThat(entry.getName()).isEqualTo("check.pdf");
            try (InputStream inputStream = zf.getInputStream(entry)) {
                PDF pdf = new PDF(inputStream);
                Assertions.assertEquals(1,pdf.numberOfPages);
            }
        }
    }

    @Test
    void zipTestCsv() throws Exception {
        ZipFile zf = new ZipFile(new File("src/test/resources/zip/zip-file3.zip"));
        ZipInputStream is = new ZipInputStream(cl.getResourceAsStream("zip/zip-file3.zip"));
        ZipEntry entry;
        while ((entry = is.getNextEntry()) != null) {
            org.assertj.core.api.Assertions.assertThat(entry.getName()).isEqualTo("1.csv");
            try (InputStream inputStream = zf.getInputStream(entry)) {
                     CSVReader csvReader = new CSVReader(new InputStreamReader(inputStream));

                    List<String[]> data = csvReader.readAll();
                    Assertions.assertEquals(2, data.size());
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

    @Test
    void jsonFileParsingImprovedTestJackson2() throws Exception {
        try (Reader reader = new InputStreamReader(
                cl.getResourceAsStream("ivan.json")
        )) {
            Person actual = mapper.readValue(reader, Person.class);
            Assertions.assertEquals("Иванов Иван", actual.getValue());
            Assertions.assertEquals("MALE", actual.getData().getGender());
        }
    }
}


