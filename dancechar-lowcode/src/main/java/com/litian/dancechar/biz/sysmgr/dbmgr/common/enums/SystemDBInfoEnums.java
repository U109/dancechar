package com.litian.dancechar.biz.sysmgr.dbmgr.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class SystemDBInfoEnums {


    @Getter
    @AllArgsConstructor
    public enum DriverEnum{

        /**
         * mysql
         */
        MYSQL("mysql", "mysql"),

        /**
         * pgsql
         */
        PG_SQL("pgsql", "postgresql"),

        /**
         * oracle
         */
        ORACLE("oracle", "oracle"),

        /**
         * mssql
         */
        MS_SQL("mssql", "sqlserver"),

        /**
         * 达梦数据库
         */
        DM_SQL("dm", "dm"),

        /**
         * 人大金仓数据库
         */
        KINGBASE_ES("kingbase", "kingbasees"),
        ;

        private String code;

        private String desc;

        public DriverEnum getByCode(String code){
            for(DriverEnum driverEnum : values()){
                if(code.equals(driverEnum.getCode())){
                    return driverEnum;
                }
            }
            return null;
        }
    }
}
