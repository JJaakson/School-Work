package ee.taltech.iti0200.logfiles.writer;

import ee.taltech.iti0200.logfiles.exception.LogFileWriterException;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LogFileWriter {

    /**
     * Write provided logs content to file.
     *
     * @param path    file saving path
     * @param content the content to write
     */
    public void writeLogs(String path, String content) {
        Path file = Paths.get(path);
        try (BufferedWriter writer = Files.newBufferedWriter(file)) {
            writer.write(content);
        } catch (IOException e) {
            throw new LogFileWriterException("Unable to write file");
        }
    }

}
