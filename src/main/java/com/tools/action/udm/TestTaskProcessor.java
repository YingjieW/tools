package com.tools.action.udm;

import open.udm.client.entity.BaseMainTaskEntity;
import open.udm.client.entity.BaseSubTaskEntity;
import open.udm.client.processer.external.adapter.AbstractDbTaskProcessor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Description:
 *
 * @author yingjie.wang
 * @since 17/9/30 下午3:00
 */
@Component("testTaskProcessor")
public class TestTaskProcessor extends AbstractDbTaskProcessor<String> {
    @Override
    public BaseMainTaskEntity createMainTaskEntity(String controllerId) {
        return null;
    }

    @Override
    public boolean process(BaseMainTaskEntity mainTask, BaseSubTaskEntity subTask, List<String> datas) {
//        System.out.println("________TestTaskProcessor_datas: " + JSON.toJSONString(datas));
        int sleepSeconds = 5;
        System.out.println("[" + subTask.getId() + "]________TestTaskProcessor sleep_seconds: " + sleepSeconds);
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public boolean postProcess(BaseMainTaskEntity mainTask) {
        System.out.println("________TestTaskProcessor_postProcess.");
        return true;
    }
}
