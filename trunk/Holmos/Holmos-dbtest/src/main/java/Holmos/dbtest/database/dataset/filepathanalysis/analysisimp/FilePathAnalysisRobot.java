package Holmos.dbtest.database.dataset.filepathanalysis.analysisimp;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import Holmos.webtest.constvalue.ConfigConstValue;
import Holmos.webtest.exceptions.HolmosFailedError;
/**
 * 框架默认的文件路径分析机器人的委托者:
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
 * <li>(1)file.extention不以‘/’开头,那么文件路径为  当前测试类所在目录(classpath)/prefix/packagePath/file.extention</li>
 * <li>(2)file.extention以‘/’开头,那么文件的路径为 当前测试类所在目录(classpath)/prefix/file.extention</li>
 * <p>
 * Num.5:prefix有正常值(且以'/'开头),并且packageUse的值为false
 * <li>(1)file.extention不以‘/’开头,那么文件路径为  prefix/packagePath/file.extention</li>
 * <li>(2)file.extention以‘/’开头,那么文件的路径为 prefix/file.extention</li>
 * 
 * @author 吴银龙(15857164387)
 * 
 * */
public class FilePathAnalysisRobot {
	/*指示是否用包路径的变量*/
	private boolean usePackagePath;
	/*框架设置的前缀*/
	private String prefixPath;
	/**
	 * 默认的构造函数：
	 * <li>加上包路径</li>
	 * <li>没有特定的前缀设置</li>
	 * */
	public FilePathAnalysisRobot(){
		this(true,null);
	}
	public FilePathAnalysisRobot(boolean usePackagePath,String prefixPath){
		this.usePackagePath=usePackagePath;
		this.prefixPath=prefixPath;
	}
	/**
	 * 获取框架默认的数据源文件的定位符
	 * @param extention 框架设定的数据源文件的扩展名
	 * @param testClass 测试类
	 * @return 框架默认的数据源文件的定位
	 * */
	public URI getDefaultFilePath(String extention,Class<?> testClass){
		String defaultFileName=getDefaultFileName(extention,testClass);
		return getFilePath(defaultFileName,testClass);
	}
	/**
	 * 根据Holmos框架的配置和给定的测试类还有数据源文件的名字获取数据源文件的定位
	 * @param fileName 数据源文件的名字
	 * @param testClass 测试类
	 * @return 获取到的数据源文件的定位
	 * */
	public URI getFilePath(String fileName, Class<?> testClass) {
		String fullFileName=makeFullFileName(fileName,testClass);
		if(fullFileName.startsWith("/")){
			File dataSourceFile=new File(fullFileName);
			if(!dataSourceFile.exists()){
				throw new HolmosFailedError("无法找到数据源文件"+fullFileName);
			}
			return dataSourceFile.toURI();
		}else{
			URL fileFullPath=testClass.getResource("/"+fullFileName);
			if(fileFullPath==null){
				throw new HolmosFailedError("无法找到数据源文件"+fullFileName);
			}
			try {
				return fileFullPath.toURI();
			} catch (URISyntaxException e) {
				throw new HolmosFailedError("无法找到数据源文件"+fullFileName);
			}
		}
	}
	/**
	 * 获取数据源文件的全名，就是由packagePath和prefixPath作用之后的全名
	 * @param fileName 数据源文件的名字(注解中的)
	 * @param testClass 测试类
	 * @return 数据源文件的全名
	 * */
	private String makeFullFileName(String fileName, Class<?> testClass) {
		String fullFileName=fileName;
		if(usePackagePath && !fileName.startsWith("/")){
			fullFileName=getNameWithPackagePath(fileName,testClass);
		}
		if(fileName.startsWith("/")){
			//去掉'/'
			fullFileName=fileName.substring(1);
		}
		if(prefixPath!=null&&prefixPath.trim()!=null){
			fullFileName=prefixPath+"/"+fullFileName;
		}
		return fullFileName;
	}
	/**
	 * 加上包路径
	 * @param fileName 数据源文件的名字(注解中的)
	 * @param testClass 测试类
	 * @return 加上包路径的文件名字
	 * */
	private String getNameWithPackagePath(String fileName, Class<?> testClass) {
		if(testClass.getName().lastIndexOf('.')<0)
			//在没有包路径的情况下
			return fileName;
		String packagePath=testClass.getName().substring(testClass.getName().lastIndexOf('.')+1);
		if(fileName.startsWith("/"))
			return fileName;
		else
			return packagePath.replace('.', '/')+"/"+fileName;
	}
	/**
	 * 获取框架默认的数据源文件的名字:testClass.name+"."+extention
	 * @param extention 框架设定的数据源文件的扩展名
	 * @param testClass 测试类
	 * @return 框架默认的数据源文件的名字
	 * */
	private String getDefaultFileName(String extention, Class<?> testClass) {
		return testClass.getName().substring(testClass.getName().lastIndexOf('.')+1)+"."+extention;
	}
}
