package ee.taltech.iti0200.logfiles.reader;

import ee.taltech.iti0200.logfiles.exception.LogFileReaderException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

public class BufferedLogFileReader implements LogFileReader {

    BufferedReader reader = null;
    private static final int DATE_START = 0;
    private static final int DATE_END = 10;
    private static final int TIME_START = 11;
    private static final int TIME_END = 23;
    private static final int LEVEL_END = 29;

    @Override
    public String readLogs(String path) {
        StringBuilder result = new StringBuilder();
        FileReader file;
        try {
            try {
                file = new FileReader(path);
            } catch (FileNotFoundException e) {
                throw new LogFileReaderException("File not found");
            }
            reader = new BufferedReader(file);
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
                result.append(System.lineSeparator());
            }
        } catch (IOException e) {
            throw new LogFileReaderException("Unable to read file");
        }
        if (result.length() == 0) {
            return "";
        }
        return result.toString().substring(0, result.length() - 1);
    }

    @Override
    public String readLogsWithLevel(String path, String level) {
        StringBuilder result = new StringBuilder();
        FileReader file;
        try {
            try {
                file = new FileReader(path);
            } catch (FileNotFoundException e) {
                throw new LogFileReaderException("File not found");
            }
            reader = new BufferedReader(file);
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains(level)) {
                    result.append(line);
                    result.append(System.lineSeparator());
                }
            }
        } catch (IOException e) {
            throw new LogFileReaderException("Unable to read file");
        }
        if (result.length() == 0) {
            return "";
        }
        return result.toString().substring(0, result.length() - 1);
    }

    @Override
    public String readLogsBetween(String path, LocalDateTime from, LocalDateTime to) {
        StringBuilder result = new StringBuilder();
        FileReader file;
        try {
            try {
                file = new FileReader(path);
            } catch (FileNotFoundException e) {
                throw new LogFileReaderException("File not found");
            }
            reader = new BufferedReader(file);
            String line;
            while ((line = reader.readLine()) != null) {
                LocalDateTime lineTime;
                try {
                    lineTime = LocalDateTime.parse(line.substring(DATE_START, DATE_END) + "T"
                            + line.substring(TIME_START, TIME_END));
                } catch (DateTimeParseException e) {
                    throw new LogFileReaderException("Unable to parse date");
                }
                if (lineTime.isAfter(from) && lineTime.isBefore(to)) {
                    result.append(line);
                    result.append(System.lineSeparator());
                }
            }
        } catch (IOException e) {
            throw new LogFileReaderException("Unable to read file");
        }
        if (result.length() == 0) {
            return "";
        }
        return result.toString().substring(0, result.length() - 1);
    }

}
