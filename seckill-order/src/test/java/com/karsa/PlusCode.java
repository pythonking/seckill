package com.karsa;


import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.google.common.collect.Lists;

import java.util.List;


/**
 * 代码自动生成器
 */
public class PlusCode {
    public static void main(String[] args) {
        //构建代码生成器对象
        AutoGenerator mpg = new AutoGenerator();
        //配置策略
        //1.配置 GlobalConfig
        GlobalConfig gc = new GlobalConfig();
        gc.setOutputDir(System.getProperty("user.dir") + "/seckill-order/src/main/java");
        gc.setAuthor("karsa");
        gc.setOpen(false);
        gc.setFileOverride(false);//是否覆盖
        // gc.setServiceName("%sService");//接口名称:是否加I
        gc.setIdType(IdType.ASSIGN_ID);
        gc.setDateType(DateType.ONLY_DATE);
//        gc.setSwagger2(true);
        //2.配置 数据源
        DataSourceConfig dc = new DataSourceConfig();
        dc.setUrl("jdbc:mysql://122.112.248.215:3306/seckill?useUnicode=true&useSSL=false&characterEncoding=utf8");
        dc.setDriverName("com.mysql.cj.jdbc.Driver");
        dc.setUsername("kc_user");
        dc.setPassword("PassWo0d#==");
        //3.配置包
        PackageConfig pc = new PackageConfig();
        pc.setModuleName("");
        pc.setParent("com.karsa");
        pc.setEntity("entity");
        pc.setMapper("mapper");
        pc.setXml("mappers");
        pc.setService("service");
        pc.setController("controller");
        //4.策略配置
        StrategyConfig sc = new StrategyConfig();
        sc.setNaming(NamingStrategy.underline_to_camel);
        sc.setColumnNaming(NamingStrategy.underline_to_camel);
        sc.setEntityLombokModel(true);
        sc.setRestControllerStyle(true);
        //5.设置映射表名
        sc.setInclude("order_info");
        sc.setLogicDeleteFieldName("delete_flag");//逻辑删除标志
        //6.自动填充配置
        TableFill createTime = new TableFill("create_time", FieldFill.INSERT);
        TableFill updateTime = new TableFill("update_time", FieldFill.INSERT_UPDATE);
        List<TableFill> tfs = Lists.newArrayList();
        tfs.add(createTime);
        tfs.add(updateTime);
        sc.setTableFillList(tfs);
        //7.乐观锁
        sc.setVersionFieldName("version");
        //8.下划线命名
        sc.setControllerMappingHyphenStyle(true);//localhost:8080/hollo_id_1

        mpg.setGlobalConfig(gc);
        mpg.setDataSource(dc);
        mpg.setPackageInfo(pc);
        mpg.setStrategy(sc);

        mpg.execute();
    }
}
