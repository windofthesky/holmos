package holmos.webtest.constvalue;

import holmos.webtest.basetools.HolmosPropertiesTool;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

/**
 * Holmos框架配置文件用到的常量
 * @author 吴银龙(15857164387)
 * */
public class ConfigConstValue {
	/**holmos配置文件*/
	public static String HOLMOSCONFFILE=ClassLoader.getSystemResource("holmosConf.properties").getPath();
	/**日志文件配置地址*/
	public static String LOGCONFIG=ClassLoader.getSystemResource("log4j.properties").getPath();
	/**上传autoIt文件*/
	public static String UPLOADFILE=ClassLoader.getSystemResource("autoItScripts\\upload.exe").getPath();
	/**下载autoIt文件*/
	public static String DOWNLOADFILE=ClassLoader.getSystemResource("autoItScripts\\download.exe").getPath();
	/**ieDriverServer地址*/
	public static String IEDRIVER=ClassLoader.getSystemResource("driverServers\\IEDriverServer.exe").getPath();
	/**chromeDriverServer地址*/
	public static String CHROMEDRIVER=ClassLoader.getSystemResource("driverServers\\chromedriver.exe").getPath();
	/**普通元素css属性检查列表文件所在位置*/
	public static String CSSPROPERTIESCONFIG=ClassLoader.getSystemResource("cssProperties\\css_comman_property_config.properties").getPath();
	/**图片元素css属性检查列表文件所在位置*/
	public static String IMGCSSPROPERTIESCONFIG=ClassLoader.getSystemResource("cssProperties\\css_img_property_config.properties").getPath();
	static{
		try {
			HOLMOSCONFFILE = java.net.URLDecoder.decode(HOLMOSCONFFILE,"utf8");
			LOGCONFIG=java.net.URLDecoder.decode(LOGCONFIG,"utf8");
			UPLOADFILE=java.net.URLDecoder.decode(UPLOADFILE,"utf8");
			DOWNLOADFILE=java.net.URLDecoder.decode(DOWNLOADFILE,"utf8");
			IEDRIVER=java.net.URLDecoder.decode(IEDRIVER,"utf8");
			CHROMEDRIVER=java.net.URLDecoder.decode(CHROMEDRIVER,"utf8");
			CSSPROPERTIESCONFIG=java.net.URLDecoder.decode(CSSPROPERTIESCONFIG,"utf8");
			IMGCSSPROPERTIESCONFIG=java.net.URLDecoder.decode(IMGCSSPROPERTIESCONFIG,"utf8");
			System.setProperty("webdriver.ie.driver", IEDRIVER);
			System.setProperty("webdriver.chrome.driver", CHROMEDRIVER);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	/**默认等待一次的时间*/
	public static int defaulwaitMiliSeconds=50;
	/**默认等待次数,默认等待一个元素加载5秒*/
	public static int defaultWaitCount=1;
	/**等待加载次数,默认等待加载30s*/
	public static int waitCount=600;
	/**默认等待页面加载时间,默认10s*/
	public static int waitPageLoadTime=10000;
	static{
		Properties properties=HolmosPropertiesTool.getPropertyInfo(HOLMOSCONFFILE);
		defaulwaitMiliSeconds=HolmosPropertiesTool.getInt(properties, "defaulwaitMiliSeconds");
		defaultWaitCount=HolmosPropertiesTool.getInt(properties, "defaultWaitCount");
		waitCount=HolmosPropertiesTool.getInt(properties, "waitCount");
		waitPageLoadTime=HolmosPropertiesTool.getInt(properties, "waitPageLoadTime");
	}
	/**用来指明Holmos框架的数据源路径前缀配置，是否要添加当前的测试类所在包的包路径*/
	public static final String DATA_SOURCE_PACKAGE_PREFIX_USEABLE="Holmos.data.source.package.prefix.useable";
	/**设定Holmos框架数据源路径的前缀,不设置或设置为""，则认为没有前缀，否则是有前缀的*/
	public static final String DATA_SOURCE_PREFIX_VALUE="Holmos.data.source.prefix.value";
	/**配置的所有的modules的关键字*/
	public static final String HOLMOS_MODULES="holmos.modules";
	/**单个module的引用关键字*/
	public static final String HOLMOS_MODULE="holmos.module";
	/**指示单个module是否被激活的key*/
	public static final String ENABLED=".enable";
	/**Holmos框架HolmosModule类实现的类路径*/
	public static final String HOLMOS_MODULE_CLASS_PATH="Holmos.Holmos.testlistener.modules.imp.";
	/**autoIt3x.dll文件地址*/
	public static String AUTOITDLLPATH;
}
