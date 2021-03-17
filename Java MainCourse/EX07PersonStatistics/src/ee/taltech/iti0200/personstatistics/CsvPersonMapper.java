package ee.taltech.iti0200.personstatistics;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CsvPersonMapper {

    private static final int FIRST_NAME_CHAR_AT = 0;
    private static final int LAST_NAME_CHAR_AT = 1;
    private static final int AGE_CHAR_AT = 2;
    private static final int GENDER_CHAR_AT = 3;
    private static final int HEIGHT_CHAR_AT = 4;
    private static final int OCCUPATION_CHAR_AT = 5;
    private static final int NATIONALITY_CHAR_AT = 6;

    public List<Person> mapToPersons(String path) {
        List<Person> list = new ArrayList<>();
        try (Stream<String> stream = Files.lines(Paths.get(path))) {
            list = stream.map(line -> {
                String[] str = line.split(",");
                return new PersonBuilder().
                        withFirstName(str[FIRST_NAME_CHAR_AT]).
                        withLastName(str[LAST_NAME_CHAR_AT]).
                        withAge(Integer.parseInt(str[AGE_CHAR_AT])).
                        withGender(Gender.valueOf(str[GENDER_CHAR_AT])).
                        withHeightInMeters(Double.parseDouble(str[HEIGHT_CHAR_AT])).
                        withOccupation(str[OCCUPATION_CHAR_AT]).
                        withNationality(str[NATIONALITY_CHAR_AT]).
                        build();
            }).collect(Collectors.toList());
        } catch (IOException e) {
            throw new CsvToPersonMappingException("Error creating Person!");
        }
        return list;
    }
}
