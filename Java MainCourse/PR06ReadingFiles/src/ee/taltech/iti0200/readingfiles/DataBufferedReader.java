package ee.taltech.iti0200.readingfiles;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DataBufferedReader implements DataReader {
    private int counter;
    private ArrayList<String> lines;
    private BufferedReader reader;

    public DataBufferedReader() {
        this.counter = 0;
        this.reader = null;
    }

    @Override
    public void setFileToRead(File file) {
        this.lines = new ArrayList<>();
        Path path = Paths.get(String.valueOf(file));
        try {
            this.reader = Files.newBufferedReader(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<String> readFile() {
            while (true) {
                String line = null;
                try {
                    line = this.reader.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (line != null && !line.isBlank()) {
                    this.lines.add(line.replace("\n", ""));
                } else if (line == null) {
                    break;
                }
            }
        return this.lines;
    }

    @Override
    public Optional<String> readNextLine() {
        if (this.lines.isEmpty()) {
            this.readFile();
        }
        if (counter >= 0 && counter < this.lines.size() && !lines.get(counter).isEmpty()) {
            Optional<String> result = Optional.of(lines.get(counter));
            counter++;
            return result;
        }
        counter++;
        return Optional.empty();
    }
}
