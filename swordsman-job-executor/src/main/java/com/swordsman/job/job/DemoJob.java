package com.swordsman.job.job;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import com.xxl.job.core.log.XxlJobLogger;
import org.springframework.stereotype.Component;

/**
 * @Author DuChao
 * @Date 2019-10-30 14:17
 * 测试 Demo
 */
@JobHandler(value="demoJob")
@Component
public class DemoJob extends IJobHandler {

    @Override
    public ReturnT<String> execute(String s) {
        XxlJobLogger.log("XXL-JOB Demo Test");

        int a = 1/0;

        return SUCCESS;
    }

}
