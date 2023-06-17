package com.just.study;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class StudyApplicationTests {

    /**
     * 代码生成器
     */

    //@Test
    void contextLoads() {
        String finalProjectPath = "F:\\notes\\api\\study\\";
        FastAutoGenerator.create("jdbc:mysql://127.0.0.1:3306/notes", "root", "123456")
                .globalConfig(builder -> {
                    builder.author("abc") // 设置作者
//                            .enableSwagger() // 开启 swagger 模式
//                            .fileOverride() // 覆盖已生成文件
                            .disableOpenDir() //禁止打开输出目录
                            .outputDir(finalProjectPath + "/src/main/java"); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("com.just") // 设置父包名
                            .moduleName("study") // 设置父包模块名
                            .entity("entity") //设置entity包名
                            .other("dto") // 设置dto包名
                            .pathInfo(Collections.singletonMap(OutputFile.mapperXml, finalProjectPath + "/src/main/resources/mapper")); // 设置mapperXml生成路径

                })
                .strategyConfig(builder -> {
                    builder.addInclude("menu","role","user_role","menu_role");
                })
                .templateEngine(new FreemarkerTemplateEngine()).execute();
    }

}
