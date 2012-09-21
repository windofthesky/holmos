package holmos.constvalue;


/**常数仓库，用来集中管理Holmos系统用到的常数
 * @author 吴银龙(15857164387)
 * */
public class ConstValue {
	/**默认的Selenium1 server端口号*/
	public final static int PORT=4444;
	/**默认的Selenium1 的server主机地址*/
	public final static String HOST="localhost";
	/**Holmos 框架运行的当前目录*/
	public final static String CURRENDIR=System.getProperty("user.dir");
	/**打日志的地址*/
	public final static String LOGDIR="D:\\holmos\\logs";
	/**selenium1打开ie标志*/
	public final static String IE="*iexplore";
	/**selenium1打开firefox标志*/
	public final static String FIREFOX="*firefox";
	/**selenium1打开chrome标志*/
	public final static String CHROME="*googlechrome";
	/**selenium1打开safari标志*/
	public final static String SAFARI="*safari";
	/**selenium1打开opera标志*/
	public final static String OPERA="*opera";
	/**新页面打开标志*/
	public static final String BLANK = "_blank";
	/**本页面打开标志*/
	public static final String SELF = "_self";
	/**测试资源的保存目录*/
	public final static String TESTDIR=CURRENDIR+"\\src\\test\\java";
	/**测试用例资源库*/
	public static final String TESTCASESTOREDIR = TESTDIR+"\\Holmos\\Holmos\\testcasestore";
	/**css属性检查列表文件所在目录*/
	public static final String CSSPROPERTIESCONFIGDIR=TESTDIR+"\\src\\main\\java\\Holmos\\Holmos\\css";
	/**holmos框架css校验的时候元素的css属性存放位置*/
	public static final String CSSPROPERTIESDIR="D:\\holmos\\css";
	///**Holmos 框架的page 库的基地址，由配置得来*/
	public static String HOLMOSPAGESTOREBASEDIR=CURRENDIR+"\\src\\test\\java\\Holmos\\Holmos\\pagestore";
	
	public static int nestedFatherObjectModifier=4112;
	/**struct 包信息*/
	public static final String STRUCTPACKAGEINFO="import Holmos.Holmos.struct.*;";
	/**element 包信息*/
	public static final String ELEMENTPACKAGEINFO="import Holmos.Holmos.element.*;";
	/**截图存放地址*/
	public static String screenShotDir;
}
