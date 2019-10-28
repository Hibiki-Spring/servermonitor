package com.huiliyi.common;

import com.huiliyi.service.MailService;
import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.lang.management.ManagementFactory;
import com.sun.management.OperatingSystemMXBean;


import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class MailTask {
    private static int count = 0;
    private Logger logger = Logger.getLogger(getClass());
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    private static OperatingSystemMXBean osmxb = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();

    @Resource
    private MailService mailService;

    @Scheduled(fixedRate = 1000)
    public void reportMemory() {
        int memoryLoad = memoryLoad();
        if (memoryLoad < 30) {
            count = 0;
            logger.info("现在的内存占用为："+memoryLoad+"%。");
        }
        if (memoryLoad > 80) {
            if (count == 60){
                mailService.sendSimpleMail("1058669634@qq.com", "内存告警邮件", dateFormat.format(new Date())+"----内存占用过高：" + memoryLoad + "%");
            }
            if (count != 0 && count % 300 == 0) {
                logger.info("内存占用过高");
                mailService.sendSimpleMail("1058669634@qq.com", "内存告警邮件", dateFormat.format(new Date())+"----内存占用过高：" + memoryLoad + "%");
            }
            count++;
        }
    }


    public static int memoryLoad() {
        double totalvirtualMemory = osmxb.getTotalPhysicalMemorySize();
        double freePhysicalMemorySize = osmxb.getFreePhysicalMemorySize();

        double value = freePhysicalMemorySize / totalvirtualMemory;
        int percentMemoryLoad = (int) ((1 - value) * 100);
        return percentMemoryLoad;
    }
}