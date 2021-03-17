package ee.taltech.iti0200.logfiles.reader;
import java.time.LocalDateTime;

public interface LogFileReader {

    /**
     * Read logs from file as string.
     *
     * @return string of logs
     */
    String readLogs(String path);

    /**
     * Read logs from file and return only lines that match provided log level.
     *
     * @return string of logs
     */
    String readLogsWithLevel(String path, String level);

    /**
     * Read logs from file and return only lines where date is between from and to parameters.
     *
     * @return string of logs
     */
    String readLogsBetween(String path, LocalDateTime from, LocalDateTime to);
}
