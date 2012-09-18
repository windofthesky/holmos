package holmos.dbtest.database;

import holmos.testlistener.modules.HolmosModule;
import holmos.webtest.basetools.HolmosPropertiesTool;
import holmos.webtest.exceptions.HolmosFailedError;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.lang.ClassUtils;
import org.dom4j.Document;
import org.springframework.jdbc.datasource.DataSourceUtils;

/**Holmos框架数据库支持的工具类
 * @author 吴银龙(15857164387)
 * */
public class HolmosDataBaseTools {
	/**根据propertyName从properties文件里面获得属性值
	 * @param properties 属性文件
	 * @param propertyName 属性名字
	 * @return properties文件里面获得的属性值*/
	public static String getPropertiesValue(Properties properties,String propertyName){
		String value=properties.getProperty(propertyName);
		if(value==null||"".equalsIgnoreCase(value)){
			throw new HolmosFailedError("获取属性"+propertyName+"失败!");
		}return value;
	}
	/**根据xmlNodeXpath从xml文件中获得属性值
	 * @param document 由xml文件生成的document文档对象
	 * @param xmlNodeXpath 指示该节点的xpath
	 * @return xml文件中获得属性值*/
	public static String getXMLValue(Document document,String xmlNodeXpath){
		String value=document.selectSingleNode(xmlNodeXpath).getText();
		if(value==null||"".equalsIgnoreCase(value)){
			throw new HolmosFailedError("获取属性"+xmlNodeXpath+"失败!");
		}return value;
	}
	/**根据DataSoure对象获取数据库连接对象
	 * @param dataSource 数据源对象*/
	public static Connection getConnection(DataSource dataSource){
		return DataSourceUtils.getConnection(dataSource);
	}
	/**释放连接，并解除connection和dataSource的绑定*/
	public static void releaseConnection(Connection connection,DataSource dataSource){
		DataSourceUtils.releaseConnection(connection, dataSource);
	}
	
	/**针对每个case获得默认的数据源配置，这个应该叫做生成，因为这个方法并没有关心这样的生成的数据源是否存在<br>
	 * 这个规则是框架本身制定的规则，如需改动，就在此处改动数据源文件的命名规则<br><p>
	 * 由于数据库数据源管理模块可能会有其他扩展，这个方法作为一个工具方法，那么需要将管理模块类信息传入，方便以后扩展<br>
	 * 返回的数据内容为:(Annotation的子类,(Annotation子类的方法的名字,moduleClass.AnnotationName.methodName.default))
	 * @param moduleClass 给定的数据源管理模块，依据这个的不同，采取不同的默认方案
	 * @param properties 数据库配置属性文件
	 * @param annotations 指示不同类别的数据源用处(判别是用来初始化的还是用来校验的，以后可能会扩展其他功能)
	 * */
	public static Map<Class<? extends Annotation>, Map<String, String>> getDefaultAnnotationValues(
			Class<? extends HolmosModule> moduleClass, Properties properties,
			Class<? extends Annotation>... annotations) {
		Map<Class<? extends Annotation>, Map<String, String>>result=new HashMap<Class<? extends Annotation>, Map<String,String>>();
		//循环扫描所有的注解，得到被这个注解包含的方法列表
		for(Class<? extends Annotation> annotation:annotations){
			Method[] methods=annotation.getDeclaredMethods();
			//处理单个注解的所有的方法，为此方法获得数据源和验证数据源的工厂方法
			for(Method method:methods){
				String defaultDataSourcePropertyValue=getdefaultDataSourcePropertyValue(moduleClass,annotation,method.getName(),properties);
				Map<String,String>defaultDataSourceProperty=result.get(annotation);
				if(defaultDataSourceProperty==null){
					//常用的缓存cache方法
					defaultDataSourceProperty=new HashMap<String, String>();
					result.put(annotation, defaultDataSourceProperty);
				}
				defaultDataSourceProperty.put(method.getName(), defaultDataSourcePropertyValue);
			}
		}
		return result;
	}
	/**生成并获得默认的properties文件的默认配置数据源工厂的属性*/
	private static String getdefaultDataSourcePropertyValue(
			Class<? extends HolmosModule> moduleClass,
			Class<? extends Annotation> annotation, String methodName,
			Properties properties) {
		//properties文件里面的这个属性的内容类似为:HolmosDBModule.DataSet.HolmosDataSet.factory.default
		String propertyName=ClassUtils.getShortClassName(moduleClass)+"."+ClassUtils.getShortClassName(annotation)+"."+methodName+".default";
		if(!HolmosPropertiesTool.isPropertyInFile(properties, propertyName)){
			return null;
		}
		return HolmosPropertiesTool.getValue(properties, propertyName);
	}
	/**
	 * 关闭connection,statement,resultset,由此方法捕获所有的异常
	 * */
	public static void closeWithoutException(Connection connection,Statement statement,ResultSet resultSet){
		try{
			resultSet.close();
		}catch (SQLException e) {
			
		}finally{
			try {
				statement.close();
			} catch (SQLException e) {
				
			}finally{
				try {
					connection.close();
				} catch (SQLException e) {
					
				}
			}
		}
	}
}
