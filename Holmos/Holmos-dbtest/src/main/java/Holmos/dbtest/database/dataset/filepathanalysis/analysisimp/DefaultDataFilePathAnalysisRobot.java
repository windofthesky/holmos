package holmos.dbtest.database.dataset.filepathanalysis.analysisimp;

import holmos.dbtest.database.dataset.filepathanalysis.DataFilePathAnalysisRobot;

import java.io.File;
import java.util.Properties;

import Holmos.webtest.basetools.HolmosPropertiesTool;
import Holmos.webtest.constvalue.ConfigConstValue;
/**
 * 框架默认的文件路径分析机器人:
 * 文件路径生成规则如下：(文件名字为file.extention,package路径为 packagePath,<br>
 * 配置文件中的前缀为prefix({@link ConfigConstValue #DATA_SOURCE_PREFIX_VALUE}的值))<br>
 * 配置文件中的包名设置为packageUse({@link ConfigConstValue #DATA_SOURCE_PACKAGE_PREFIX_USEABLE}的值)<br>
 * <p>
 * Num.1:默认情况：prefix没有设置或者为"",并且packageUse的值为true
 * <p>
 * <li>(1)file.extention不以‘/’开头,那么文件的路径为packagePath/file.extention</li>
 * <li>(2)file.extention以‘/’开头，那么packagePath失效,文件的路径为file.extention</li>
 * <p>
 * Num.2:prefix没有设置或者为"",并且packageUse的值为false
 * <li>(1)file.extention无论怎样，文件的路径都是file.extention</li>
 * <p>
 * Num.3:prefix有正常值(且不以'/'开头),并且packageUse的值为true
 * <li>(1)file.extention不以‘/’开头,那么文件路径为prefix/packagePath/file.extention</li>
 * <li>(2)file.extention以‘/’开头,那么文件的路径为prefix/file.extention</li>
 * <p>
 * Num.4:prefix有正常值(且不以'/'开头),并且packageUse的值为false
 * <li>(1)file.extention不以‘/’开头,那么文件路径为  当前测试类所在目录/prefix/packagePath/file.extention</li>
 * <li>(2)file.extention以‘/’开头,那么文件的路径为 当前测试类所在目录/prefix/file.extention</li>
 * <p>
 * Num.5:prefix有正常值(且以'/'开头),并且packageUse的值为false
 * <li>(1)file.extention不以‘/’开头,那么文件路径为  prefix/packagePath/file.extention</li>
 * <li>(2)file.extention以‘/’开头,那么文件的路径为 prefix/file.extention</li>
 * 
 * @author 吴银龙(15857164387)
 * 
 * */
public class DefaultDataFilePathAnalysisRobot implements DataFilePathAnalysisRobot{
	/*实际的路径分析机器人，这个是为了扩展，开发者可以更改此机器人来设置不同的 DefaultDataFilePathAnalysisRobot*/
	protected FilePathAnalysisRobot filePathAnalysisRobot;
	
	public DefaultDataFilePathAnalysisRobot(){
		this.filePathAnalysisRobot=new FilePathAnalysisRobot();
	}
	public DefaultDataFilePathAnalysisRobot(Properties properties){
		init(properties);
	}
	public void init(Properties properties){
		boolean usePackagePath=HolmosPropertiesTool.getBoolean(properties, ConfigConstValue.DATA_SOURCE_PACKAGE_PREFIX_USEABLE);
		String prefixPath=HolmosPropertiesTool.getValue(properties, ConfigConstValue.DATA_SOURCE_PREFIX_VALUE);
		filePathAnalysisRobot=new FilePathAnalysisRobot(usePackagePath, prefixPath);
	}
	public File analysis(Class<?> testClass, String fileName) {
		return new File(filePathAnalysisRobot.getFilePath(fileName, testClass));
	}
	 
}
