package com.example.RedditClone.Service.Impl;

import com.example.RedditClone.Service.LogService;
import com.example.RedditClone.Util.MessageType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;


@Service
public class LogServiceImpl implements LogService {

    Logger logger = LoggerFactory.getLogger(LogService.class);
    static String fileName = "src/main/resources/log.txt";

    @Override
    public void message(String message, MessageType type) {
        try {
            File myObj = new File(fileName);
            myObj.createNewFile();
            FileWriter fileWriter = new FileWriter(fileName, true);

            PrintWriter printWriter = new PrintWriter(fileWriter);
            switch (type) {
                case INFO -> {
                    printWriter.append("INFO: ");
                    logger.info(message);
                }
                case WARN -> {
                    printWriter.append("WARN: ");
                    logger.warn(message);
                }
                case ERROR -> {
                    printWriter.append("ERROR: ");
                    logger.error(message);
                }
            }
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
            printWriter.append(simpleDateFormat.format(new Date())).append(" ").append(message).append("\n");
            printWriter.close();
        } catch (Exception ignored) {
        }
    }

}
