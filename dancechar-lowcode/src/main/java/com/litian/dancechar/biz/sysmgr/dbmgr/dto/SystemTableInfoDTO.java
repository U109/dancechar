package com.litian.dancechar.biz.sysmgr.dbmgr.dto;

import com.litian.dancechar.framework.common.base.BasePage;
import lombok.Data;

import java.io.Serializable;

/**
 * 类描述：数据库表信息
 *
 * @author 01410001
 * @date 2021/08/05 17:00
 */
@Data
public class SystemTableInfoDTO extends BasePage implements Serializable {

    private String tableName;

    private Long systemDbId;

}
