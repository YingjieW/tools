package com.tools.constant.dao;

import com.tools.constant.entity.ConfigEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConfigEntityDao {

    /**
     * 查询配置
     * @return
     */
     ConfigEntity queryConfigEntity(String configKey);

    /**
     * 添加配置
     */
    Long insertConfigEntity(ConfigEntity configEntity);

    /**
     * 更新配置
     * @return
     */
    Long updateConfigEntity(ConfigEntity configEntity);

    /**
     * 查询命名空间下所有配置
     * @param namespace
     * @return
     */
    List<ConfigEntity> queryConfigEntitysByNamespace(String namespace);
}
