package com.tools.action.udm;

import com.alibaba.fastjson.JSON;
import open.udm.client.entity.BaseMainTaskEntity;
import open.udm.client.entity.BaseSubTaskEntity;
import open.udm.client.processer.external.TaskProcessor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Description:
 *
 * @author yingjie.wang
 * @since 17/9/5 下午6:41
 */
@Component("taskProcessor")
public class TaskProcessorImpl implements TaskProcessor<String> {
    @Override
    public BaseMainTaskEntity createMainTaskEntity(String controllerId) {
        return null;
    }

    @Override
    public boolean process(BaseMainTaskEntity mainTask, BaseSubTaskEntity subTask, List<String> datas) {
        System.out.println("************ datas:" + JSON.toJSONString(datas));
        try {
//            TimeUnit.MINUTES.sleep(3);
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("************ Sleeping is over");
        return false;
    }

    @Override
    public boolean postProcess(BaseMainTaskEntity mainTask) {
        return false;
    }

    @Override
    public int getFileHeaderCount() {
        return 0;
    }

    @Override
    public int getFileFooterCount() {
        return 0;
    }
//    public class TaskProcessorImpl {
//
//    @Override
//    public BaseMainTaskEntity createMainTaskEntity() {
//        UdmMainTaskEntity udmMainTaskEntity = new UdmMainTaskEntity();
//        udmMainTaskEntity.setControllerId("test_controller_id");
//        udmMainTaskEntity.setBizType("test_biz_type");
//        return udmMainTaskEntity;
//    }
//
//    @Override
//    public boolean process(BaseMainTaskEntity baseMainTaskEntity, BaseSubTaskEntity baseSubTaskEntity, List list) {
//        return false;
//    }
//
//    @Override
//    public boolean postProcess(BaseMainTaskEntity baseMainTaskEntity) {
//        return false;
//    }
//
//
//    @Override
//    public int getFileHeaderCount() {
//        return 0;
//    }
//
//    @Override
//    public int getFileFooterCount() {
//        return 0;
//    }
}
