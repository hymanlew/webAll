package com.hyman.dao;

import com.hyman.entity.User2;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.dynamic.sql.SqlBuilder;
import org.mybatis.dynamic.sql.delete.DeleteDSL;
import org.mybatis.dynamic.sql.delete.MyBatis3DeleteModelAdapter;
import org.mybatis.dynamic.sql.delete.render.DeleteStatementProvider;
import org.mybatis.dynamic.sql.insert.render.InsertStatementProvider;
import org.mybatis.dynamic.sql.render.RenderingStrategy;
import org.mybatis.dynamic.sql.select.MyBatis3SelectModelAdapter;
import org.mybatis.dynamic.sql.select.QueryExpressionDSL;
import org.mybatis.dynamic.sql.select.SelectDSL;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;
import org.mybatis.dynamic.sql.update.MyBatis3UpdateModelAdapter;
import org.mybatis.dynamic.sql.update.UpdateDSL;
import org.mybatis.dynamic.sql.update.render.UpdateStatementProvider;
import org.mybatis.dynamic.sql.util.SqlProviderAdapter;

import javax.annotation.Generated;
import java.util.List;

@Mapper
public interface UserInfoMapper {

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    long count(SelectStatementProvider selectStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @DeleteProvider(type=SqlProviderAdapter.class, method="delete")
    int delete(DeleteStatementProvider deleteStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @InsertProvider(type=SqlProviderAdapter.class, method="insert")
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="record.id", before=false, resultType=Long.class)
    int insert(InsertStatementProvider<User2> insertStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @Results(id="UserInfoResult", value = {
        @Result(column="id", property="id", jdbcType=JdbcType.BIGINT),
        @Result(column="real_name", property="realName", jdbcType=JdbcType.VARCHAR),
        @Result(column="gmt_create", property="gmtCreate", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="gmt_modified", property="gmtModified", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="hobby", property="hobby", jdbcType=JdbcType.LONGVARCHAR)
    })
    List<User2> selectMany(SelectStatementProvider selectStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @UpdateProvider(type=SqlProviderAdapter.class, method="update")
    int update(UpdateStatementProvider updateStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default QueryExpressionDSL<MyBatis3SelectModelAdapter<Long>> countByExample() {
        return SelectDSL.selectWithMapper(this::count, SqlBuilder.count())
                .from(new UserInfoDynamicSqlSupport.User2());
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default DeleteDSL<MyBatis3DeleteModelAdapter<Integer>> deleteByExample() {
        return DeleteDSL.deleteFromWithMapper(this::delete, UserInfoDynamicSqlSupport.duser2);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int insert(User2 user2) {
        return insert(SqlBuilder.insert(user2)
                .into(UserInfoDynamicSqlSupport.duser2)
                .map(UserInfoDynamicSqlSupport.realName).toProperty("realName")
                .map(UserInfoDynamicSqlSupport.gmtCreate).toProperty("gmtCreate")
                .map(UserInfoDynamicSqlSupport.gmtModified).toProperty("gmtModified")
                .map(UserInfoDynamicSqlSupport.hobby).toProperty("hobby")
                .build()
                .render(RenderingStrategy.MYBATIS3));
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int insertSelective(User2 record) {
        return insert(SqlBuilder.insert(record)
                .into(UserInfoDynamicSqlSupport.duser2)
                .map(UserInfoDynamicSqlSupport.realName).toPropertyWhenPresent("realName", record::getRealName)
                .map(UserInfoDynamicSqlSupport.gmtCreate).toPropertyWhenPresent("gmtCreate", record::getGmtCreate)
                .map(UserInfoDynamicSqlSupport.gmtModified).toPropertyWhenPresent("gmtModified", record::getGmtModified)
                .map(UserInfoDynamicSqlSupport.hobby).toPropertyWhenPresent("hobby", record::getHobby)
                .build()
                .render(RenderingStrategy.MYBATIS3));
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default QueryExpressionDSL<MyBatis3SelectModelAdapter<List<User2>>> selectByExample() {
        return SelectDSL.selectWithMapper(this::selectMany, UserInfoDynamicSqlSupport.id, UserInfoDynamicSqlSupport.realName,
                UserInfoDynamicSqlSupport.gmtCreate, UserInfoDynamicSqlSupport.gmtModified, UserInfoDynamicSqlSupport.hobby)
                .from(UserInfoDynamicSqlSupport.duser2);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default QueryExpressionDSL<MyBatis3SelectModelAdapter<List<User2>>> selectDistinctByExample() {
        return SelectDSL.selectDistinctWithMapper(this::selectMany, UserInfoDynamicSqlSupport.id, UserInfoDynamicSqlSupport.realName,
                UserInfoDynamicSqlSupport.gmtCreate, UserInfoDynamicSqlSupport.gmtModified, UserInfoDynamicSqlSupport.hobby)
                .from(UserInfoDynamicSqlSupport.duser2);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default UpdateDSL<MyBatis3UpdateModelAdapter<Integer>> updateByExample(User2 record) {
        return UpdateDSL.updateWithMapper(this::update, UserInfoDynamicSqlSupport.duser2)
                .set(UserInfoDynamicSqlSupport.realName).equalTo(record::getRealName)
                .set(UserInfoDynamicSqlSupport.gmtCreate).equalTo(record::getGmtCreate)
                .set(UserInfoDynamicSqlSupport.gmtModified).equalTo(record::getGmtModified)
                .set(UserInfoDynamicSqlSupport.hobby).equalTo(record::getHobby);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default UpdateDSL<MyBatis3UpdateModelAdapter<Integer>> updateByExampleSelective(User2 record) {
        return UpdateDSL.updateWithMapper(this::update, UserInfoDynamicSqlSupport.duser2)
                .set(UserInfoDynamicSqlSupport.realName).equalToWhenPresent(record::getRealName)
                .set(UserInfoDynamicSqlSupport.gmtCreate).equalToWhenPresent(record::getGmtCreate)
                .set(UserInfoDynamicSqlSupport.gmtModified).equalToWhenPresent(record::getGmtModified)
                .set(UserInfoDynamicSqlSupport.hobby).equalToWhenPresent(record::getHobby);
    }
}