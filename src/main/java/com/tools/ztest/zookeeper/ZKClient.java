package com.tools.ztest.zookeeper;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;

import java.util.List;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 17/1/9 下午4:23
 */
public class ZKClient extends ZKBase {
    static final String ROOT_PATH = "/testRootPath";

    public static void main(String[] args) throws Throwable {
        // 删除父目录
        if (zk.exists(ROOT_PATH, null) != null) {
            List<String> tmpNodeList = zk.getChildren(ROOT_PATH,true);
            for (String node : tmpNodeList) {
                // 删除子目录节点
                zk.delete("/testRootPath/"+node,-1);
            }
            zk.delete(ROOT_PATH, -1);
        }

        // 创建一个目录节点
        zk.create("/testRootPath", "testRootData".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

        // 取出一个目录节点中的数据
        System.out.println("-----> " + new String(zk.getData(ROOT_PATH,false,null)));

        // 创建一个子目录节点, PERSISTENT: 子目录节点名称保持不变
        zk.create("/testRootPath/testChildPath01", "testChildData01".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

        /**
         * 创建一个子目录节点, PERSISTENT_SEQUENTIAL: 每次都会在子目录后面自动添加序列号
         * 如:
         * testChildPath0000000003 - testChildData_modified
         * testChildPath0000000002 - testChildData_modified
         * testChildPath0000000001 - testChildData_modified
         */
        zk.create("/testRootPath/testChildPath", "testChildData".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT_SEQUENTIAL);
        zk.create("/testRootPath/testChildPath", "testChildData".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT_SEQUENTIAL);
        zk.create("/testRootPath/testChildPath", "testChildData".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT_SEQUENTIAL);
        zk.create("/testRootPath/testChildPath", "testChildData".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT_SEQUENTIAL);
        zk.create("/testRootPath/testChildPath", "testChildData".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT_SEQUENTIAL);

        // 取出子目录节点列表
        List<String> childNodeList = zk.getChildren(ROOT_PATH,true);

        // 打印输出子目录中数据
        printNodeListData(ROOT_PATH, childNodeList);
        // 修改目录节点中数据
        for (String childNode : childNodeList) {
            zk.setData("/testRootPath/"+childNode,(getNodeData(ROOT_PATH, childNode) + "_modified").getBytes(),-1);
        }
        // 打印输出子目录中修改后的数据
        printNodeListData(ROOT_PATH, childNodeList);

        // 打印输出ROOT_PATH及其子目录的状态
        printNodeStatus(ROOT_PATH);
    }
}
