package com.tools.constant;

import com.tools.constant.dao.ConfigEntityDao;
import com.tools.constant.entity.ConfigEntity;
import com.tools.constant.enums.ConfigTypeEnum;
import com.tools.quartz.task.BaseTaskJobProxy;
import com.tools.quartz.task.jobMannager.Job;
import com.tools.util.CheckUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

//@Component
@Job(cronExpression = "0 0/1  * * * ? ")
public class ConfigurationUtil extends BaseTaskJobProxy implements InitializingBean{


    private static final String NAMESPACE="default";

    private static final Logger LOGGER= LoggerFactory.getLogger(ConfigurationUtil.class);


    @Autowired
    ConfigEntityDao configEntityDao;

    /**
     * 本地缓存通用配置信息
     */
    private  static Map<String,ConfigEntity> configMap=new ConcurrentHashMap<String, ConfigEntity>();


    /**
     * 添加新或更新的配置信息
     * @param key
     * @param value
     * @param configTypeEnum
     */
    public void addOrUpdateConfig(String key, String value, ConfigTypeEnum configTypeEnum){
        ConfigEntity configEntity = configEntityDao.queryConfigEntity(key);
        //数据库检查
        if(CheckUtils.isNull(configEntity)){
            configEntity=new ConfigEntity();
            configEntity.setConfigValue(value);
            configEntity.setConfigKey(key);
            configEntity.setConfigType(configTypeEnum);
            configEntityDao.insertConfigEntity(configEntity);
        }else{
            configEntity.setConfigType(configTypeEnum);
            configEntity .setConfigValue(value);
            configEntityDao.updateConfigEntity(configEntity);
        }

        //本地缓存配置检查
        if(needUpdate(key,value)){
            configMap.put(key,configEntity);
        }
    }

    /**
     * 检查key是否需要更新
     * @param key
     * @param value
     * @return
     */
    private boolean needUpdate(String key,String value){
        return configMap.get(key)==null||(configMap.get(key)!=null&&!configMap.get(key).equals(value));
    }



    public static  <T>  T  getDefault(String key,T defaultValue){
        try {
            ConfigEntity configValue = getConfigValue(key);
            if (null == configValue ) {
                return defaultValue;
            }
            switch (configValue.getConfigType()) {
                case STRING:
                    return (T) configValue.getConfigValue().toString();
                case INT:
                    return (T) new Integer(configValue.getConfigValue().toString());
                default:
                    throw new RuntimeException("unsupport type : " + configValue.getConfigType());
            }
        } catch (Throwable e) {
            return defaultValue;
        }

    }

    /**
     * 获取缓存中配置信息
     * @param key
     * @return
     */
    public static ConfigEntity getConfigValue(String key){
        ConfigEntity configEntity = configMap.get(key);
        if(CheckUtils.isNotNull(configEntity)){
            return configEntity;
        }else{
            return null;
        }

    }

    @Override
    public void afterPropertiesSet() throws Exception {
        //初始化配置数据
        execute();
    }

    /**
     * 定时监听统一配置更新
     */
    @Override
    public void execute() {
        LOGGER.info("监听统一配置更新");
        List<ConfigEntity> configEntities = configEntityDao.queryConfigEntitysByNamespace(NAMESPACE);
        if(CheckUtils.isNotNull(configEntities)) {
            for (ConfigEntity configEntity : configEntities
                    ) {
                ConfigEntity configEntity1 = configMap.get(configEntity.getConfigKey());
                if(CheckUtils.isNull(configEntity1)){
                    configMap.put(configEntity.getConfigKey(),configEntity);
                } else if(configEntity1.getVersion()!=configEntity.getVersion()){
                    configMap.put(configEntity.getConfigKey(),configEntity);
                }
            }
        } else{
                //do nothing
        }
    }
}
