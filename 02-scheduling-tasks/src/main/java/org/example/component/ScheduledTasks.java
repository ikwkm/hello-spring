package org.example.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class ScheduledTasks {

    private static final Logger logger = LoggerFactory.getLogger(ScheduledTasks.class);
    private static final SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");

    @Scheduled(fixedRate = 2000)
    public void reportCurrentTime() {
        logger.info("The time is {} now.", formatter.format(new Date()));
    }
}
