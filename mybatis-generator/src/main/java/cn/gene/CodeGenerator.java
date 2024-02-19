package cn.gene;

import com.baomidou.mybatisplus.annotation.DbType;

import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CodeGenerator {
    public static String sscanner(String tip){
        Scanner sc = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("请输入" + tip + "");
        System.out.println(help.toString());
        if (sc.hasNext()) {
            String ipt = sc.next();
            if (!StringUtils.isEmpty(ipt)){
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "!");
    }
    public static void main(String[] args) {
        // 需要构建一个 代码自动生成器 对象
        AutoGenerator mpg = new AutoGenerator();
        // 1、全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        gc.setOutputDir(projectPath+"/mybatis-generator/src/main/java");
        gc.setOpen(false);
        gc.setBaseColumnList(true);
        gc.setSwagger2(true);
        gc.setAuthor(" MX ");
        mpg.setGlobalConfig(gc);

        //设置数据源
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://127.0.0.1:3306/cloud-mission-seckillgoods?useSSL=false&useUnicode=true&characterEncoding=utf-8&serverTimezone=GMT%2B8");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("ab215687DA.");
        dsc.setDbType(DbType.MYSQL);
        mpg.setDataSource(dsc);

        //3、包的配置
        PackageConfig pc = new PackageConfig();
        pc.setParent("cn.gene");
        pc.setEntity("pojo");
        pc.setMapper("mapper");
        pc.setService("service");
        pc.setServiceImpl("service.impl");
        pc.setController("controller");
        mpg.setPackageInfo(pc);

        //4，自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {

            }
        };

        //如果模板引擎是 freemarker
        String templatePath = "/templates/mapper.xml.ftl";
        //自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return projectPath + "/mybatis-generator/src/main/resources/mapper/" + tableInfo.getEntityName()
                        + "Mapper" + StringPool.DOT_XML;
            }
        });
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);

        //配置模板
        TemplateConfig templateConfig = new TemplateConfig();
        templateConfig.setXml(null);
        mpg.setTemplate(templateConfig);

        //策略配置
        StrategyConfig strategy = new StrategyConfig();

        // 设置要映射的表名
        //数据库表映射到实体的命名策略
        strategy.setNaming(NamingStrategy.underline_to_camel);
        //数据库表字段映射到实体的命名策略
        strategy.setColumnNaming(NamingStrategy.no_change);
        //设置lombok模型
        strategy.setEntityLombokModel(true);
        //设置@RestController控制器
        strategy.setRestControllerStyle(true);
        strategy.setInclude(sscanner("表名，多个英文逗号分隔").split(","));
//        try{
//            strategy.setInclude(getScanner().split(","));
//        }catch(Exception e){
//            System.out.println("数据输入有误");
//        }
        strategy.setControllerMappingHyphenStyle(true);
        //localhost:8080/hello_id_2
        mpg.setStrategy(strategy);
        //表前缀
        strategy.setTablePrefix("t_");
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute(); //执行
    }
}
