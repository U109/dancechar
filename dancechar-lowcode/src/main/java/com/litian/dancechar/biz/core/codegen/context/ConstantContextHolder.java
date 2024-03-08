package com.litian.dancechar.biz.core.codegen.context;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.log.Log;

/**
 * 系统参数配置获取
 *
 * @author xuyuxiang
 * @date 2020/4/14 15:34
 */
public class ConstantContextHolder {
    private static final Log log = Log.get();

    /**
     * 获取租户功能是否开启
     */
    public static Boolean getTenantOpenFlag() {
        return getSysConfigWithDefault("SNOWY_TENANT_OPEN", Boolean.class, false);
    }

    /**
     * 获取演示环境开关是否开启，默认为false
     */
    public static Boolean getDemoEnvFlag() {
        return getSysConfigWithDefault("SNOWY_DEMO_ENV_FLAG", Boolean.class, false);
    }

    /**
     * 获取config表中的配置，如果为空返回默认值
     */
    public static <T> T getSysConfigWithDefault(String configCode, Class<T> clazz, T defaultValue) {
        String configValue = ConstantContext.me().getStr(configCode);
        if (ObjectUtil.isEmpty(configValue)) {
            // 将默认值加入到缓存常量
            log.warn(">>> 系统配置sys_config表中存在空项，configCode为：{}，系统采用默认值：{}", configCode, defaultValue);
            ConstantContext.me().put(configCode, defaultValue);
            return defaultValue;
        } else {
            try {
                return Convert.convert(clazz, configValue);
            } catch (Exception e) {
                return defaultValue;
            }
        }
    }
}
