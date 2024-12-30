package Model;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Log {
    private static Log instance;
    private StringBuffer logBuffer;

    private Log() {
        logBuffer = new StringBuffer();
    }

    public static Log getInstance() {
        if (instance == null) {
            instance = new Log();
        }
        return instance;
    }

    public void addLog(String logEntry) {
        logBuffer.append(logEntry).append("\n");
    }

    public String getLogs() {
        return logBuffer.toString();
    }

    public void writeLogToFile(String filePath) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(logBuffer.toString());
        }
    }
}
