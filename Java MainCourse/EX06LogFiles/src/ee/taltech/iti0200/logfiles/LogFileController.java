package ee.taltech.iti0200.logfiles;
import ee.taltech.iti0200.logfiles.reader.BufferedLogFileReader;
import ee.taltech.iti0200.logfiles.reader.FilesLogFileReader;
import ee.taltech.iti0200.logfiles.writer.LogFileWriter;

public class LogFileController {

    final BufferedLogFileReader bufferedLogFileReader = new BufferedLogFileReader();
    final FilesLogFileReader filesLogFileReader = new FilesLogFileReader();
    final LogFileWriter logFileWriter = new LogFileWriter();
}
