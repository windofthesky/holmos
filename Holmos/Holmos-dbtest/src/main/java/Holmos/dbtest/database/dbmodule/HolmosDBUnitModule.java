package Holmos.dbtest.database.dbmodule;

import static org.dbunit.database.DatabaseConfig.FEATURE_BATCHED_STATEMENTS;
import static org.dbunit.database.DatabaseConfig.PROPERTY_DATATYPE_FACTORY;
import static org.dbunit.database.DatabaseConfig.PROPERTY_ESCAPE_PATTERN;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.datatype.IDataTypeFactory;

import Holmos.dbtest.database.DBToolBoxFactory;
import Holmos.dbtest.database.HolmosDataBaseTools;
import Holmos.dbtest.database.annotation.HolmosDataSet;
import Holmos.dbtest.database.annotation.HolmosExpectedDataSet;
import Holmos.dbtest.database.connecion.HolmosDataBaseConnection;
import Holmos.dbtest.database.dataset.HolmosMultiDataSet;
import Holmos.dbtest.database.dataset.filepathanalysis.DataFilePathAnalysisRobot;
import Holmos.dbtest.database.dataset.filepathanalysis.analysisimp.DefaultDataFilePathAnalysisRobot;
import Holmos.dbtest.database.datasetfactory.HolmosDataSetFactory;
import Holmos.dbtest.database.datasetloadstrategy.HolmosDataSetLoadStrategy;
import Holmos.dbtest.database.dbtool.DBToolBox;
import Holmos.dbtest.database.operation.DefaultSQLOperation;
import Holmos.dbtest.database.operation.SQLOperation;
import Holmos.testlistener.HolmosTestListener;
import Holmos.testlistener.modules.HolmosModule;
import Holmos.testlistener.modules.HolmosModuleTool;
import Holmos.webtest.basetools.HolmosConfTool;
import Holmos.webtest.basetools.HolmosReflectionTool;
import Holmos.webtest.exceptions.HolmosFailedError;
/**
 * 对DbUnit数据库单元测试框架提供支持,作为{@link HolmosDBUnitTestListener}的功能提供者
 * @author 吴银龙(15857164387)
 * */
public class HolmosDBUnitModule implements HolmosModule{
	/**该类的日志工具*/
	private static Logger logger=Logger.getLogger(HolmosDBUnitModule.class);
	/**数据库测试的配置信息*/
	private Properties properties;
	/**
	 * 默认的注解信息,这里面定义了所有的可能用到的注解和注解方法，以及这些注解方法对应的操作（完成这些操作的配置）
	 * 比如：
	 * <li>数据及工厂</li>
	 * <li>xxxx 暂时只有1，为了以后扩展</li>
	 * */
	private Map<Class<? extends Annotation>,Map<String,String>> defaultAnnotationValues;
	
	/**
	 * properties配置文件里面配置的数据库连接，这个是一个连接池，每一次调用{@link HolmosDataBaseConnection #close()}方法的时候，并没有将此连接<br>
	 * 关闭，而是将此连接回收入连接池，再次需要此数据库的连接的时候，直接从这个连接池里面连接就行了，利用Schema的Name作为key索引，由于底层Dbunit<br>
	 * 的一个Schema表只支持一个连接，这个类就实现了多数据库连接
	 * */
	private Map<String,HolmosDataBaseConnection>connections=new HashMap<String, HolmosDataBaseConnection>();
	/**设定配置文件，初始化每个case的默认数据源DataSet和校验数据源expectedDataSet，初始化数据源加载工厂和校验工厂
	 * @param properties 数据源配置文件*/
	@SuppressWarnings("unchecked")
	@Override
	public void init(Properties properties) {
		this.properties=properties;
		defaultAnnotationValues=HolmosDataBaseTools.getDefaultAnnotationValues(HolmosDBUnitModule.class,properties,HolmosDataSet.class,HolmosExpectedDataSet.class);
	}

	@Override
	public HolmosTestListener createListener() {
		return new HolmosDBUnitTestListener();
	}
	/**根据SchemaName来获得{@link HolmosDataBaseConnection},获取过程，先从数据库连接池 connections里面获取，如果没有的话，创建一个新的连接，并将此创建的连接加入连接池
	 * @param schemaName 数据库连接池的schemaName索引
	 * @return 得到的与SchemaName匹配的connection
	 * */
	public HolmosDataBaseConnection getConnection(String schemaName){
		HolmosDataBaseConnection connection=connections.get(schemaName);
		if(connection==null){
			connection=createNewHolmosDataBaseConnection(schemaName);
			connections.put(schemaName, connection);
		}return connection;
	}

	/**新建一个Dbunit的数据库连接
	 * @param schemaName
	 * @return
	 * */
	private HolmosDataBaseConnection createNewHolmosDataBaseConnection(
			String schemaName) {
		DataSource dataSource=Holmos.getInstance().getModuleStore().
				getModuleOfType(HolmosDBModule.class).getDataSource();
		SQLOperation operation=new DefaultSQLOperation(dataSource);
		DBToolBox toolBox=DBToolBoxFactory.getToolBox(properties, operation, schemaName);
		
		HolmosDataBaseConnection connection=new HolmosDataBaseConnection(dataSource, schemaName);
		DatabaseConfig config=connection.getConfig();
		
		IDataTypeFactory dataTypeFactory=HolmosConfTool.getInstanceOf(IDataTypeFactory.class, properties, toolBox.getDatabaseDialect());
		config.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, dataTypeFactory);
		
		if(toolBox.getIdentifierQuoteString()!=null){
			config.setProperty(DatabaseConfig.PROPERTY_ESCAPE_PATTERN, toolBox.getIdentifierQuoteString() + '?' + toolBox.getIdentifierQuoteString());
		}
		config.setProperty(DatabaseConfig.FEATURE_BATCHED_STATEMENTS, "true");
		config.setProperty("http://www.dbunit.org/features/skipOracleRecycleBinTables", "true");
		return connection;
	}
	/**
	 * 根据在方法上设置的数据加载策略进行数据的加载，采用dbunit作为底层实现对数据的加载操作<br>
	 * <p>
	 * 如果用户没有指定文件的位置：那么按照下面顺序进行数据文件的加载<br>
	 * <li>文件名字是以方法级别的形式命名</li>
	 * <li>文件名字是以类级别的形式命名</li>
	 * <li>如果这两种形式的文件都不存在，那么就什么也不做</li>
	 * <p>
	 * 如果用户指定了文件的位置(用@HolmosDataSet)
	 * <li>加载这个位置的文件</li>
	 * <li>如果文件不存在，抛出异常</li>
	 * */
	public void insertDataSet(Method testMethod, Object testObject) {
		try{
			HolmosMultiDataSet multiDataSet=getDataSet(testMethod, testObject);
			if(multiDataSet==null){
				//由于没有配置相应的数据集，不做数据的插入操作，直接返回
				return;
			}
			//加载数据集的策略，分为7种
			HolmosDataSetLoadStrategy dataSetLoadStrategy = getDataSetLoadStrategy(HolmosDataSet.class,testMethod, testObject.getClass());
			insertDataSet(multiDataSet, dataSetLoadStrategy);
		}catch (Exception e) {
			throw new HolmosFailedError("在执行方法"+testMethod.getName()+"进行数据插入的时候出错!");
		}finally{
			closeJdbcConnection();
		}
	}
	/**
	 * 采用Holmos框架配置的默认的数据加载策略对数据进行加载，此加载只会加载类级别的数据文件，文件找不到的话，关闭数据库连接<br>
	 * <li>数据文件的名字为testClass.getName().extention</li>
	 * <li>数据加载器在Holmos框架配置文件里面配好</li>
	 * <li>数据的加载策略在Holmos框架配置文件中配好</li>
	 * <li></li>
	 * */
	public void inserDefaultDataSet(Class<?>testClass){
		HolmosDataSetFactory dataSetFactory=getDefaultDataSetFactory();
		String dataFileName=getDefaultDataSetFileName(testClass,dataSetFactory.getFactoryType());
		inserDataSet(testClass,dataFileName);
	}
	
	/**
	 * 获取指定的测试对象的测试方法上给出的数据集，通过获取这个测试方法上@HolmosMultiDataSet注解的value来获取文件的位置<br>
	 * 如果有值的话，那么就用这个文件，如果没有的话，首先查看方法级别的文件有没有，再检查类级别的数据集文件，如果都没有的话，返回null<br>
	 * @param testObject 待测试对象
	 * @param testMethod 待测试方法
	 * */
	public HolmosMultiDataSet getDataSet(Object testObject,Method testMethod){
		Class<?>testClass=testObject.getClass();
		HolmosDataSet dataSetAnnotation=HolmosAnnotationTool.getMethodOrClassLevelAnnotation(testClass, testMethod, HolmosDataSet.class);
		if(dataSetAnnotation==null)
			return null;
		HolmosDataSetFactory dataSetFactory=getDataSetFactory(HolmosDataSet.class, testClass, testMethod);
		String[] dataFileNames=dataSetAnnotation.value();
		if(dataFileNames.length!=0){
			dataFileNames=new String[]{getDefaultDataSetFileName(testClass, dataSetFactory.getFactoryType())};
		}
		return getDataSet(testClass, dataFileNames, dataSetFactory);
	}
	
	/**
	 * 获取指定的测试对象的测试方法上给出的测试后的数据集，通过获取这个测试方法上@HolmosExpectedDataSet注解的value来获取文件的位置<br>
	 * 如果有值的话，那么就用这个文件，如果没有的话，首先查看方法级别的文件有没有，再检查类级别的数据集文件，如果都没有的话，返回null<br>
	 * @param testObject 待测试对象
	 * @param testMethod 待测试方法
	 * */
	public HolmosMultiDataSet getExpectedDataSet(Object testObject,Method testMethod){
		Class<?>testClass=testObject.getClass();
		HolmosExpectedDataSet dataSetAnnotation=HolmosAnnotationTool.getMethodOrClassLevelAnnotation(testClass, testMethod, HolmosExpectedDataSet.class);
		if(dataSetAnnotation==null)
			return null;
		HolmosDataSetFactory dataSetFactory=getDataSetFactory(HolmosExpectedDataSet.class, testClass, testMethod);
		String[] dataFileNames=dataSetAnnotation.value();
		if(dataFileNames.length!=0){
			dataFileNames=new String[]{getDefaultDataSetFileName(testClass, dataSetFactory.getFactoryType())};
		}
		return getDataSet(testClass, dataFileNames, dataSetFactory);
	}
	
	/**
	 * 将指定测试对象的测试方法上指定的数据集和实际的数据库中的内容进行比较，只比较两种都含有的列的值
	 * 
	 * */
	public void assertDbContentAsExpected(Object testObject,Method testMethod){
		HolmosMultiDataSet expectedDataSet=getExpectedDataSet(testObject,testMethod);
		if(expectedDataSet==null){
			return ;
		}
		try{
			Holmos.getInstance().getModuleStore().getModuleOfType(HolmosDBModule.class).flushDatabaseUpdates(testObject);
			for(String schemaName:expectedDataSet.getAllSchemaNames()){
				IDataSet expectedData=expectedDataSet.getDataSetBySchemaName(schemaName);
				IDataSet actualData=getActualData(schemaName);
				HolmosDataBaseCheckTool.assertEqualDbUnitDataSets(schemaName,actualData, expectedData);
			}
		}catch (Exception e) {
			closeJdbcConnection();
		}
	}
	
	/**
	 * 获取实际的数据库数据，根据指定的schemaName
	 * */
	private IDataSet getActualData(String schemaName) {
		try {
			return getConnection(schemaName).createDataSet();
		} catch (SQLException e) {
			throw new HolmosFailedError("从实际的数据库中获取IDataSet失败!");
		}
	}

	private void inserDataSet(Class<?>testClass,
			String... dataFileNames) {
		HolmosDataSetFactory dataSetFactory=getDefaultDataSetFactory();
		HolmosDataSetLoadStrategy dataSetLoadStrategy=getDefaultDataSetLoadStrategy();
		HolmosMultiDataSet dataSet=getDataSet(testClass,dataFileNames,dataSetFactory);
		insertDataSet(dataSet, dataSetLoadStrategy);
	}
	/**
	 * 根据给定的testClass和数据集的文件名字获取数据集
	 * */
	private HolmosMultiDataSet getDataSet(Class<?> testClass,
			String[] dataFileNames, HolmosDataSetFactory dataSetFactory) {
		List<File> dataFiles=new ArrayList<File>();
		DataFilePathAnalysisRobot dataFilePathAnalysisRobot=new DefaultDataFilePathAnalysisRobot();
		for(String dataFileName:dataFileNames){
			File file=dataFilePathAnalysisRobot.analysis(testClass, dataFileName);
			dataFiles.add(file);
		}
		return dataSetFactory.createMultiDataSet(dataFiles.toArray(new File[dataFiles.size()]));
	}

	/**
	 * 获取默认的数据加载策略
	 * */
	private HolmosDataSetLoadStrategy getDefaultDataSetLoadStrategy() {
		Class<? extends HolmosDataSetLoadStrategy>dataSetLoadStrategyClass=HolmosReflectionTool.getClassWithName(
				HolmosModuleTool.getDefaultAnnotationProperty(HolmosDBUnitModule.class, HolmosDataSet.class, "loadstrategy", properties));
		return HolmosReflectionTool.createInstanceAsType(dataSetLoadStrategyClass, false);
	}

	/**
	 * 获取默认的待加载的数据文件的名字
	 * */
	private String getDefaultDataSetFileName(Class<?> testClass,
			String factoryType) {
		return testClass.getName().substring(testClass.getName().lastIndexOf('.')+1)+"."+factoryType;
	}

	/**
	 * 从Holmos框架的配置文件里面获取数据加载器
	 * */
	private HolmosDataSetFactory getDefaultDataSetFactory() {
		Class<? extends HolmosDataSetFactory>dataSetFactoryClass=HolmosReflectionTool.getClassWithName(
				HolmosModuleTool.getDefaultAnnotationProperty(HolmosDBUnitModule.class, HolmosDataSet.class, "factory", properties));
		HolmosDataSetFactory dataSetFactory=HolmosReflectionTool.createInstanceAsType(dataSetFactoryClass, false);
		dataSetFactory.init(properties,getDefaultDBToolBox().getSchemaName());
		return dataSetFactory;
	}
	/**
	 * 获得默认的数据库支持操作工具箱
	 * */
	private DBToolBox getDefaultDBToolBox() {
		DataSource dataSource=Holmos.getInstance().getModuleStore().getModuleOfType(HolmosDBModule.class).getDataSource();
		SQLOperation operation=new DefaultSQLOperation(dataSource);
		return DBToolBoxFactory.getDefaultDBToolBox(properties, operation);
	}

	/**
	 * 关闭当前正在用的数据库JDBC连接，将其放回数据库连接池
	 * */
	private void closeJdbcConnection() {
		for(HolmosDataBaseConnection connection:connections.values()){
			connection.closeJDBCConnection();
		}
	}
	/**
	 * 根据给定的数据加载策略来加载指定数据集
	 * @param multiDataSet 指定的数据集
	 * @param dataSetLoadStrategy 给定的数据加载策略
	 * */
	private void insertDataSet(HolmosMultiDataSet multiDataSet,
			HolmosDataSetLoadStrategy dataSetLoadStrategy) {
		try{
			for(String schemaName:multiDataSet.getAllSchemaNames()){
				IDataSet dataSet=multiDataSet.getDataSetBySchemaName(schemaName);
				dataSetLoadStrategy.excute(getDbUnitDatabaseConnection(schemaName), dataSet);
			}
		}catch (Exception e) {
			throw new HolmosFailedError("insertDataSet 操作失败!");
		}
	}
	/**
	 * 根据给定的schema配置获取数据连接，先从数据库连接池里面寻找，找不到，再重新建立
	 * */
	private HolmosDataBaseConnection getDbUnitDatabaseConnection(
			String schemaName) {
		HolmosDataBaseConnection connection=connections.get(schemaName);
		if(connection==null){
			connection=createDbUnitConnection(schemaName);
			connections.put(schemaName, connection);
		}
		return connection;
	}
	/**建立新的数据库连接*/
	private HolmosDataBaseConnection createDbUnitConnection(String schemaName) {
		/*获取数据源，并对数据源配置，看是否将一个操作当成一个事务*/
		DataSource dataSource=Holmos.getInstance().getModuleStore().getModuleOfType(HolmosDBModule.class).getDataSource();
		/*初始化变量*/
		SQLOperation operation=new DefaultSQLOperation(dataSource);
		DBToolBox toolBox=DBToolBoxFactory.getDBToolBox(properties, operation, schemaName);
		HolmosDataBaseConnection connection=new HolmosDataBaseConnection(dataSource, schemaName);
		DatabaseConfig config = connection.getConfig();
		/*DBUnit的数据库数据类型的工厂，利用这个工厂来处理DBMS的不同的数据类型*/
        IDataTypeFactory dataTypeFactory = HolmosConfTool.getInstanceOf(IDataTypeFactory.class, properties, toolBox.getDatabaseDialect());
        config.setProperty(PROPERTY_DATATYPE_FACTORY, dataTypeFactory);
        /*这个设置能确保数据库的表名和列名等标识符能被标识符引用字符窜引用*/
		if (toolBox.getIdentifierQuoteString() != null)
			config.setProperty(PROPERTY_ESCAPE_PATTERN, toolBox.getIdentifierQuoteString() + '?' + toolBox.getIdentifierQuoteString());
		/*确保SQL语句能批量执行，比如批量插入*/
        config.setProperty(FEATURE_BATCHED_STATEMENTS, "true");
        /*让Oracle系统里面的回收表系统不起作用*/
        config.setProperty("http://www.dbunit.org/features/skipOracleRecycleBinTables", "true");
		return connection;
	}

	/**
	 * 获取给定类给定方法上的数据加载策略的实现类
	 * <li>如果在此方法上找到了配置的数据加载策略，则用此策略，如果这个指定的策略不存在，则抛出异常</li>
	 * <li>如果没有配置策略，则采用框架默认的数据加载策略</li>
	 * */
	@SuppressWarnings("unchecked")
	private HolmosDataSetLoadStrategy getDataSetLoadStrategy(Class<? extends Annotation> annotationClass,Method testMethod,
			Class<? extends Object> testClass) {
		Class<? extends HolmosDataSetLoadStrategy> strategy=HolmosAnnotationTool.getMethodOrClassLevelAnnotationPropertyValue( testClass,
				testMethod, "loadStrategy", annotationClass, HolmosDataSetLoadStrategy.class);
		strategy=(Class<? extends HolmosDataSetLoadStrategy>) HolmosModuleTool.getClassValueReplaceDefault(annotationClass, "loadStrategy", strategy, defaultAnnotationValues,
				HolmosDataSetLoadStrategy.class);
		return HolmosReflectionTool.createInstanceAsType(strategy, false);
	}

	private HolmosMultiDataSet getDataSet(Method testMethod, Object testObject) {
		Class<?> testClass=testMethod.getClass();
		HolmosDataSet dataSet=HolmosAnnotationTool.getMethodOrClassLevelAnnotation(testClass, testMethod, HolmosDataSet.class);
		if(dataSet==null){
			//如果没有设置@HolmosDataSet则直接返回，不进行数据库的校验
			return null;
		}
		HolmosDataSetFactory dataSetFactory=getDataSetFactory(HolmosDataSet.class,testClass,testMethod);
		String[]dataFileNames=dataSet.value();
		if(0==dataFileNames.length){
			//如果没有指定数据的位置和名字，那么这个时候用框架默认的名字 即：类名+扩展名
			String fileName=testClass.getName().substring(testClass.getName().lastIndexOf('.')+1)+"."+dataSetFactory.getFactoryType();
			dataFileNames=new String[]{fileName};
		}
		return getDataSet(dataFileNames,testClass,dataSetFactory);
	}
	/**
	 * 从文件中加载数据，数据文件的名字规则详见
	 * {@link DefaultDataFilePathAnalysisRobot}
	 * @param dataFileNames 文件名字(注解中的)
	 * @param testClass 测试类
	 * @param dataSetFactory 数据加载工厂
	 * @return 加载到的数据集
	 * */
	private HolmosMultiDataSet getDataSet(String[] dataFileNames, Class<?> testClass,
			HolmosDataSetFactory dataSetFactory) {
		List<File>dataSetFiles=new ArrayList<File>();
		DataFilePathAnalysisRobot filePathAnalysisRobot=new DefaultDataFilePathAnalysisRobot(properties);
		for(String dataFileName:dataFileNames){
			dataSetFiles.add(filePathAnalysisRobot.analysis(testClass, dataFileName));
		}
		return dataSetFactory.createMultiDataSet(dataSetFiles.toArray(new File[dataSetFiles.size()]));
	}

	/**
	 * 获取给定测试类的测试方法上，数据准备注解的数据导入工厂(现在支持两种格式:xml+excel)
	 * 如果指定了是那种方法，那么就按照这种方法进行获取，如果没有指定，但是对这种指定的方法不进行正确性的检查,如果没有指定，则采取框架默认的工厂——excel
	 * */
	private HolmosDataSetFactory getDataSetFactory(Class<? extends Annotation> annotationClass,
			Class<?> testClass, Method testMethod) {
		Class<? extends HolmosDataSetFactory>factoryClass=HolmosAnnotationTool.getMethodOrClassLevelAnnotationPropertyValue(testClass, null, "factory", annotationClass, HolmosDataSetFactory.class);
		factoryClass=(Class<? extends HolmosDataSetFactory>) HolmosModuleTool.getClassValueReplaceDefault(annotationClass, "factory", factoryClass, defaultAnnotationValues, HolmosDataSetFactory.class);
		return HolmosReflectionTool.createInstanceAsType(factoryClass, false);
	}

	public void assertDbContentAsExpected(Method testMethod, Object testObject) {
		
	}
	/**{@link HolmosTestListener}的实现，该实现定义了进行dbunit数据库测试的时候在不同的阶段Holmos框架<br>
	 * 采取的动作，如下:
	 * <li>1:在每个测试方法执行前执行操作</li>
	 * <li>2:在每个测试方法执行后执行操作</li>
	 * @author 吴银龙(15857164387)
	 * */
	protected class HolmosDBUnitTestListener extends HolmosTestListener{

		/**框架默认的处理是:在每个方法执行之前，会进行插入insert操作；要求这个方法必须打上了@DataSet标记
		 * @param testObject 待测试的类对象
		 * @param testMethod 待测试的方法对象
		 * */
		@Override
		public void beforeTestMethod(Object testObject, Method testMethod) {
			// TODO Auto-generated method stub
			insertDataSet(testMethod, testObject);
		}
		/**框架默认的处理是:在每个测试方法执行完毕之后，进行数据验证操作；要求这个方法必须打上了@ExpecteDataSet标记
		 * @param testObject 待测试的类对象
		 * @param testMethod 待测试的方法对象
		 * */
		@Override
		public void afterTestMethod(Object testObject, Method testMethod) {
			// TODO Auto-generated method stub
            assertDbContentAsExpected(testMethod, testObject);
		}
	}
	@Override
	public void afterInit() {
		// TODO Auto-generated method stub
		// do nothing here now~ if needs someday,please just add the function you needed~~
		// if you don't know how to do this work,please connect me(吴银龙)
	}

}
