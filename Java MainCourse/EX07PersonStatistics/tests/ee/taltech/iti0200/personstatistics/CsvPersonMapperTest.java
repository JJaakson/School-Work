package ee.taltech.iti0200.personstatistics;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CsvPersonMapperTest {

    public String escapeSpecialCharacters(String data) {
        String escapedData = data.replaceAll("\\R", " ");
        if (data.contains(",") || data.contains("\"") || data.contains("'")) {
            data = data.replace("\"", "\"\"");
            escapedData = "\"" + data + "\"";
        }
        return escapedData;
    }

    public void converter(List<String[]> dataLines) throws IOException {
        File csvOutputFile = new File("test.csv");
        try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
            dataLines.stream()
                    .map(this::convertToCSV)
                    .forEach(pw::println);
        }
    }

    public String convertToCSV(String[] data) {
        return Stream.of(data)
                .map(this::escapeSpecialCharacters)
                .collect(Collectors.joining(","));
    }

        @BeforeEach
    void setUp() throws IOException {
        List<String[]> data = new ArrayList<>();
        data.add(new String[]
                {"james", "bond", "32", "MALE", "1.9", "truckdriver", "usa"});
        data.add(new String[]
                {"mari", "bond", "22", "FEMALE", "1.7", "secretagent", "antarctica"});
        converter(data);
    }

    @Test
    void mapToPersons() {
        CsvPersonMapper converter = new CsvPersonMapper();
        assertEquals(2, converter.mapToPersons("test.csv").size());
    }
}
