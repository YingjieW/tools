package com.tools.ztest.zookeeper;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.util.List;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 17/1/9 下午7:00
 */
public class ZKBase {

    public static ZooKeeper zk = null;

    static {
        // 创建一个与服务器的连接
        try {
            zk = new ZooKeeper("localhost:2181", 10 * 1000, new Watcher() {
                // 监控所有被触发的事件
                public void process(WatchedEvent event) {
                    System.out.println("已经触发了" + event.getType() + "事件！");
                }
            });
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    // 取出目录节点中数据
    public static String getNodeData(String parentPath, String childNode) throws Exception {
        if (StringUtils.isBlank(childNode) || StringUtils.isBlank(parentPath)) {
            return null;
        }
        if (parentPath.endsWith("/")) {
            parentPath = parentPath.substring(0, parentPath.length()-1);
        }
        String childNodePath = parentPath + "/" + childNode;
        return new String (zk.getData(childNodePath, false, null));
    }

    // 打印输出子目录中数据
    public static void printNodeListData(String parentPath, List<String> childNodeList) throws Exception {
        if (CollectionUtils.isNotEmpty(childNodeList)) {
            for (String childNode : childNodeList) {
                System.out.println("-----> " + childNode + " : " + getNodeData(parentPath, childNode));
            }
        }
    }

    // 输出path及path下childNode的状态
    public static void printNodeStatus(String path) throws Exception {
        if (StringUtils.isNotBlank(path) && zk.exists(path, null) != null) {
            System.out.println("-----> " + path + " : [" + zk.exists(path, true) + "]");
            List<String> childNodeList = zk.getChildren(path, true);
            if (CollectionUtils.isNotEmpty(childNodeList)) {
                for (String childNode : childNodeList) {
                    System.out.println("-----> " + childNode + " : [" + zk.exists((path+"/"+childNode), true) + "]");
                }
            }
        }
    }
}
