package com.hyman.dao;

import com.hyman.entity.User2;
import org.mybatis.dynamic.sql.SqlColumn;
import org.mybatis.dynamic.sql.SqlTable;

import javax.annotation.Generated;
import java.sql.JDBCType;
import java.time.LocalDateTime;

public final class UserInfoDynamicSqlSupport {

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final User2 duser2 = new User2();

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<Long> id = duser2.id;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> realName = duser2.realName;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<LocalDateTime> gmtCreate = duser2.gmtCreate;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<LocalDateTime> gmtModified = duser2.gmtModified;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> hobby = duser2.hobby;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final class User2 extends SqlTable {

        public final SqlColumn<Long> id = column("id", JDBCType.BIGINT);

        public final SqlColumn<String> realName = column("real_name", JDBCType.VARCHAR);

        public final SqlColumn<LocalDateTime> gmtCreate = column("gmt_create", JDBCType.TIMESTAMP);

        public final SqlColumn<LocalDateTime> gmtModified = column("gmt_modified", JDBCType.TIMESTAMP);

        public final SqlColumn<String> hobby = column("hobby", JDBCType.LONGVARCHAR);

        public User2() {
            super("duser");
        }
    }
}