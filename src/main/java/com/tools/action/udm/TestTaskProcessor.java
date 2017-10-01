package com.tools.action.udm;

import open.udm.client.entity.BaseMainTaskEntity;
import open.udm.client.entity.BaseSubTaskEntity;
import open.udm.client.processer.external.TaskProcessor;

import java.util.List;

/**
 * Description:
 *
 * @author yingjie.wang
 * @since 17/9/30 下午3:00
 */
public class TestTaskProcessor implements TaskProcessor {
    @Override
    public BaseMainTaskEntity createMainTaskEntity(String controllerId) {
        return null;
    }

    @Override
    public boolean process(BaseMainTaskEntity mainTask, BaseSubTaskEntity subTask, List datas) {
        System.out.println("TestTaskProcessor...002.");
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
}
