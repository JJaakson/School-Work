package ee.taltech.iti0200.logfiles;

import ee.taltech.iti0200.logfiles.exception.LogFileReaderException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LogFileControllerTest {

    private LogFileController controller;

    @BeforeEach
    void setUp() {
        controller = new LogFileController();

    }

    @Nested
    @DisplayName("FilesLogReader")
    class FilesLogReader {

        @Test
        void emptyLogs() {
            controller.logFileWriter.writeLogs("text.txt", "");
            LocalDateTime from = LocalDateTime.MIN;
            LocalDateTime to = LocalDateTime.MAX;
            assertEquals("", controller.filesLogFileReader.readLogs("text.txt"));
            assertEquals("", controller.filesLogFileReader.readLogsWithLevel("text.txt", "INFO"));
            assertEquals("", controller.filesLogFileReader.readLogsBetween("text.txt", from, to));
        }

        @Test
        void simpleLogs() {
            controller.logFileWriter.writeLogs("text2.txt", "2019-01-16 17:48:01.049 INFO 60674 --- "
                    + "[nio-8080-exec-1] com.Application :This is an info message");
            LocalDateTime from = LocalDateTime.MIN;
            LocalDateTime to = LocalDateTime.MAX;
            String anwser = "2019-01-16 17:48:01.049 INFO 60674 --- [nio-8080-exec-1] com.Application :"
                    + "This is an info message\r";
            assertEquals(anwser, controller.filesLogFileReader.readLogs("text2.txt"));
            assertEquals(anwser, controller.filesLogFileReader.readLogsWithLevel("text2.txt", "INFO"));
            assertEquals(anwser, controller.filesLogFileReader.readLogsBetween("text2.txt", from, to));
        }
    }

    @Nested
    @DisplayName("BufferedLogFileReader")
    class BufferedLogReader {

        @Test
        void emptyLogs() {
            controller.logFileWriter.writeLogs("text.txt", "");
            LocalDateTime from = LocalDateTime.MIN;
            LocalDateTime to = LocalDateTime.MAX;
            assertEquals("", controller.bufferedLogFileReader.readLogs("text.txt"));
            assertEquals("", controller.bufferedLogFileReader.readLogsWithLevel("text.txt", "INFO"));
            assertEquals("", controller.bufferedLogFileReader.readLogsBetween("text.txt", from, to));
        }

        @Test
        void simpleLogs() {
            controller.logFileWriter.writeLogs("text2.txt", "2019-01-16 17:48:01.049 INFO 60674 --- "
                    + "[nio-8080-exec-1] com.Application :This is an info message");
            LocalDateTime from = LocalDateTime.MIN;
            LocalDateTime to = LocalDateTime.MAX;
            String anwser = "2019-01-16 17:48:01.049 INFO 60674 --- [nio-8080-exec-1] com.Application :"
                    + "This is an info message\r";
            assertEquals(anwser, controller.bufferedLogFileReader.readLogs("text2.txt"));
            assertEquals(anwser, controller.bufferedLogFileReader.readLogsWithLevel("text2.txt", "INFO"));
            assertEquals(anwser, controller.bufferedLogFileReader.readLogsBetween("text2.txt", from, to));
        }

        @Test
        void exceptionsTest() {
            Exception exception = assertThrows(LogFileReaderException.class, () -> {
                controller.bufferedLogFileReader.readLogs("text");
            });

            String expectedMessage = "File not found";
            String actualMessage = exception.getMessage();

            assertTrue(actualMessage.contains(expectedMessage));

            Exception exceptionSecond = assertThrows(LogFileReaderException.class, () -> {
                controller.filesLogFileReader.readLogs("test");
            });

            String actualMessageSecond = exceptionSecond.getMessage();

            assertTrue(actualMessageSecond.contains(expectedMessage));

            Exception exceptionThird = assertThrows(LogFileReaderException.class, () -> {
                controller.filesLogFileReader.readLogsWithLevel("test", "level");
            });

            String actualMessageThird = exceptionThird.getMessage();

            assertTrue(actualMessageThird.contains(expectedMessage));

            Exception exceptionFourth = assertThrows(LogFileReaderException.class, () -> {
                controller.bufferedLogFileReader.readLogsWithLevel("text", "level");
            });

            String actualMessageFourth = exceptionFourth.getMessage();

            assertTrue(actualMessageFourth.contains(expectedMessage));
        }
    }
}
