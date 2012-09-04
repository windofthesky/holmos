package holmos.dbtest.database.dataset.filepathanalysis;

import java.io.File;

/**
 * 数据文件，文件路径解析器：<br>
 * <li>根据Holmos框架的配置文件来重新生成数据源文件的路径</li>
 * */
public interface DataFilePathAnalysisRobot {
	/**
	 * 根据给定的测试类和测试类注解里面注明的数据源文件的文件名来获得该文件的真实路径<br>
	 * 并返回由该路径生成的文件File对象,如果File不存在，抛出异常
	 * @param testClass 给定的测试类
	 * @param fileName 该测试类某一测试方法里面注解的用到的数据源的名字
	 * @return 该数据源文件生成的File对象
	 * */
	public File analysis(Class<?>testClass,String fileName);
}
