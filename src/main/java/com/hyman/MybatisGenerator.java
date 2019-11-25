package com.hyman;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.exception.XMLParserException;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MybatisGenerator {

    public static void main(String[] args) throws IOException, XMLParserException,
            InvalidConfigurationException, SQLException, InterruptedException {

        String classpath = MybatisGenerator.class.getResource("/").getPath();
        String filename = "generatorConfig.xml";
        File file = new File(classpath + filename);
        if(!file.exists()){
            //找不到文件 切换路径
            classpath = classpath.replace("classes", "resources");
            file = new File(classpath + filename);
        }
        List<String> warnings = new ArrayList<>();
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = cp.parseConfiguration(file);
        DefaultShellCallback callback = new DefaultShellCallback(true);
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
        myBatisGenerator.generate(null);
    }
}
