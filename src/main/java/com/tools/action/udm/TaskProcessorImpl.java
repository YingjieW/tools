package com.tools.action.udm;

import open.udm.client.entity.BaseMainTaskEntity;
import open.udm.client.processer.external.TaskProcessor;

import java.util.List;

/**
 * Description:
 *
 * @author yingjie.wang
 * @since 17/9/5 下午6:41
 */
//@Component
public class TaskProcessorImpl implements TaskProcessor {

    @Override
    public Class getMainTaskClass() {
        return UdmMainTaskEntity.class;
    }

    @Override
    public BaseMainTaskEntity createMainTaskEntity() {
        UdmMainTaskEntity udmMainTaskEntity = new UdmMainTaskEntity();
        udmMainTaskEntity.setControllerId("test_controller_id");
        udmMainTaskEntity.setBizType("test_biz_type");
        return udmMainTaskEntity;
    }

    @Override
    public <T> boolean process(List<T> list) {
        return false;
    }

    @Override
    public boolean postProcess(BaseMainTaskEntity baseMainTaskEntity) {
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
}
