package ee.taltech.iti0200.readingfiles;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

public class DataFileReader implements DataReader {

    private int counter;
    private List<String> lines;
    private FileReader reader;

    public DataFileReader() {
        this.counter = 0;
    }

    @Override
    public void setFileToRead(File file) {
        this.lines = new ArrayList<>();
        try {
            reader = new FileReader(file.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<String> readFile() {
        StringBuilder fileContents = new StringBuilder();
        int valueOfLetter;
        try {
            while ((valueOfLetter = reader.read()) != -1) {
                fileContents.append((char) valueOfLetter);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        String[] newLines = fileContents.toString().split("\n");
        for (String newLine : newLines) {
            if (!newLine.isBlank()) {
                this.lines.add(newLine);
            }
        }
        return this.lines;

    }

    @Override
    public Optional<String> readNextLine() {
        if (this.lines.isEmpty()) {
            this.readFile();
        }
        if (counter >= 0 && counter < this.lines.size() && !lines.get(counter).isBlank()) {
            Optional<String> result = Optional.of(lines.get(counter));
            counter++;
            return result;
        }
        counter++;
        return Optional.empty();
    }
}
