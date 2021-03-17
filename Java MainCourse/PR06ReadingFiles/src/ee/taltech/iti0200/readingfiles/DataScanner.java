package ee.taltech.iti0200.readingfiles;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.util.List;
import java.util.Optional;

public class DataScanner implements DataReader {
    private File file;
    private int counter;
    private List<String> lines;
    private Scanner scanner;

    public DataScanner() {
        this.counter = 0;
    }

    @Override
    public void setFileToRead(File file) {
        this.lines = new ArrayList<>();
        this.file = new File(file.toString());
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<String> readFile() {
        try {
            scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (!line.isBlank()) {
                    this.lines.add(line.replace("\n", ""));
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return this.lines;
    }

    @Override
    public Optional<String> readNextLine() {
        if (this.lines.isEmpty()) {
            this.readFile();
        }
        if (counter >= 0 && counter < this.lines.size() && !lines.get(counter).isBlank()
                && !lines.get(counter).isEmpty()) {
            Optional<String> result = Optional.of(lines.get(counter));
            counter++;
            return result;
        } else {
            counter++;
            return Optional.empty();
        }
    }
}
