package com.leoIt.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by xieyu on 2018/5/14.
 */

@Component("mySimpleJob")
public class MySimpleJob {

    private Logger logger = LoggerFactory.getLogger(MySimpleJob.class);

    private static int times = 0;

    public void execute() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String time = sdf.format(new Date());
        System.out.println("【" + time + "】简单任务第【" + ++times + "】次执行……");
    }
}