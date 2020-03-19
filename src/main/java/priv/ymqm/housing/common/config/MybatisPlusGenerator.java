package priv.ymqm.housing.common.config;

import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.io.File;

/**
 * @author chenhonnian
 * @since 2020/03/18
 */
public class MybatisPlusGenerator {

    /**
     * 需要生成的表名
     */
    private static final String[] TABLE_NAME = {"account_role", "admin_account", "common_log", "community",
            "house_picking_tip_basic", "house_picking_tip_tag", "permission", "role",
            "role_permission", "tag"};
    /**
     * 使用的数据源
     */
    private static final DataSourceConfig dataSourceConfig = getHousingDataSourceConfig();

    /**
     * 表前缀
     */
    private static final String TABLE_PREFIX = "";

    private static final String PACKAGE_NAME = "priv.ymqm";

    private static final String AUTHOR = "chenhonnian";


    public static void main(String[] args) {
        generateByTables();
    }


    private static void generateByTables() {
        StrategyConfig strategyConfig = new StrategyConfig();
        strategyConfig.setCapitalMode(true)
                .setEntityLombokModel(true)
                .setRestControllerStyle(true)
                .setEntityColumnConstant(true)
                .setTablePrefix(TABLE_PREFIX)
                .setNaming(NamingStrategy.underline_to_camel)
                .setInclude(TABLE_NAME);

        String projectPath = System.getProperty("user.dir");
        String srcPath = projectPath + File.separator + "src" + File.separator + "main" + File.separator + "java";

        GlobalConfig globalConfig = new GlobalConfig();
        globalConfig.setAuthor(AUTHOR)
                .setOutputDir(srcPath)
                .setEnableCache(false)
                .setOpen(false)
                .setSwagger2(true)
                .setBaseResultMap(true)
                .setBaseColumnList(false)
                .setKotlin(false)
                .setActiveRecord(true)
                .setFileOverride(false)
                .setServiceName("%sService");

        AutoGenerator autoGenerator = new AutoGenerator().setGlobalConfig(globalConfig)
                .setDataSource(dataSourceConfig)
                .setStrategy(strategyConfig)
                .setPackageInfo(new PackageConfig()
                        .setParent(PACKAGE_NAME)
                        .setController("controller")
                        .setEntity("domain.po")
                        .setModuleName("housing")
                        .setMapper("dao")
                        .setService("service")
                );
        autoGenerator.execute();
    }

    private static DataSourceConfig getHousingDataSourceConfig() {
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("chn971229");
        dsc.setUrl("jdbc:mysql://localhost:3306/housing?characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai");
        return dsc;
    }
}
