package ee.taltech.iti0200.logfiles.reader;

import ee.taltech.iti0200.logfiles.exception.LogFileReaderException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.LinkedList;
import java.util.List;

public class FilesLogFileReader implements LogFileReader {

    private static final int DATE_START = 0;
    private static final int DATE_END = 10;
    private static final int TIME_START = 11;
    private static final int TIME_END = 23;
    private static final int LEVEL_END = 29;

    @Override
    public String readLogs(String path) {
        List<String> allLines = new LinkedList<>();
        try {
            try {
                allLines = Files.readAllLines(Paths.get(path));
            } catch (FileNotFoundException e) {
                throw new LogFileReaderException("File not found");
            }
        } catch (IOException e) {
            throw new LogFileReaderException("File not found");
        }
        StringBuilder result = new StringBuilder();
        for (String line : allLines) {
            result.append(line).append(System.lineSeparator());
        }
        if (result.length() == 0) {
            return "";
        }
        return result.toString().substring(0, result.length() - 1);    }

    @Override
    public String readLogsWithLevel(String path, String level) {
        List<String> allLines = new LinkedList<>();
        try {
            try {
                allLines = Files.readAllLines(Paths.get(path));
            } catch (FileNotFoundException e) {
                throw new LogFileReaderException("File not found");
            }
        } catch (IOException e) {
            throw new LogFileReaderException("File not found");
        }
        StringBuilder result = new StringBuilder();
        String substring;
        for (String line : allLines) {
            substring = line.substring(TIME_END, LEVEL_END);
            if (substring.contains(level)) {
                result.append(line).append(System.lineSeparator());
            }
        }
        if (result.length() == 0) {
            return "";
        }
        return result.toString().substring(0, result.length() - 1);    }

    @Override
    public String readLogsBetween(String path, LocalDateTime from, LocalDateTime to) {
        List<String> allLines = new LinkedList<>();
        try {
            try {
                allLines = Files.readAllLines(Paths.get(path));
            } catch (FileNotFoundException e) {
                throw new LogFileReaderException("File not found");
            }
        } catch (IOException e) {
            throw new LogFileReaderException("File not found");
        }
        StringBuilder result = new StringBuilder();
        for (String line : allLines) {
            LocalDateTime lineTime;
            try {
                lineTime = LocalDateTime.parse(line.substring(DATE_START, DATE_END) + "T"
                        + line.substring(TIME_START, TIME_END));
            } catch (DateTimeParseException e) {
                throw new LogFileReaderException("Unable to parse date");
            }
            if (lineTime.isAfter(from) && lineTime.isBefore(to)) {
                result.append(line).append(System.lineSeparator());
            }
        }
        if (result.length() == 0) {
            return "";
        }
        return result.toString().substring(0, result.length() - 1);    }

}
