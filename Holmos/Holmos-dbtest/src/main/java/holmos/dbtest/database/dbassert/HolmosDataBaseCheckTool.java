package holmos.dbtest.database.dbassert;

import holmos.dbtest.database.datadifference.HolmosColumnDifference;
import holmos.dbtest.database.datadifference.HolmosRowDifference;
import holmos.dbtest.database.datadifference.HolmosSchemaDifference;
import holmos.dbtest.database.datadifference.HolmosTableDifference;
import holmos.dbtest.database.structmodule.HolmosColumn;
import holmos.dbtest.database.structmodule.HolmosDBStructFactory;
import holmos.dbtest.database.structmodule.HolmosRow;
import holmos.dbtest.database.structmodule.HolmosSchema;
import holmos.dbtest.database.structmodule.HolmosTable;
import holmos.webtest.asserttool.HolmosSimpleCheckTool;
import holmos.webtest.basetools.HolmosBaseTools;
import holmos.webtest.exceptions.HolmosFailedError;

import java.util.List;

import org.apache.log4j.Logger;
import org.dbunit.dataset.IDataSet;

/**数据库的校验类,提供数据库列，行，表的校验
 * @author 吴银龙(15857164387)
 * */
public class HolmosDataBaseCheckTool {
	/**校验数据库的两个列的值<br>
	 * <li>如果value相同，并且类型相同，校验正确</li>
	 * <li>如果value都不为null，那么正常返回差异值</li>
	 * <li>如果value相同，类型不同，那么先进行类型转换,如果转换后的结果相同，那么校验成功，否则，则同第二步</li>
	 * <li>如果两列都为null，则校验通过</li>
	 * */
	private static Logger logger=Logger.getLogger(HolmosSimpleCheckTool.class.getName());
	static{
		HolmosBaseTools.configLogProperties();
	}
	/**校验两个列的值是否相同，如果相同，那么校验成功，程序继续执行<br>
	 * 校验失败，打出失败信息，程序退出*/
	public static void assertColumn(HolmosColumn actualColumn,HolmosColumn expectedColumn)
	throws HolmosFailedError{
		StringBuilder message=new StringBuilder();
		if(actualColumn==null&&expectedColumn==null){
			message.append("数据库列校验通过!两列的值均为null!");
			logger.info(message);
		}else if(actualColumn !=null&&expectedColumn!=null){
			HolmosColumnDifference columnDifference=actualColumn.compare(expectedColumn);
			if(columnDifference!=null){
				message.append("数据库列校验失败!\n"+columnDifference.toString());
				logger.error(message);
				throw new HolmosFailedError(message.toString());
			}else{
				message.append("数据库列校验成功!");
				logger.info(message);
			}
		}else{
			message.append("数据库列校验失败");
			if(actualColumn!=null){
				message.append("actualColumn:"+actualColumn.toString());
				message.append("expectedColumn:null");
			}else{
				message.append("actualColumn:null\n");
				message.append("expectedColumn:"+expectedColumn.toString());
			}
			logger.error(message);
			throw new HolmosFailedError(message.toString());
		}
	}
	/**校验两个行的值是否相同，如果相同，那么校验成功，程序继续执行<br>
	 * 校验失败，打出失败信息，程序退出*/
	public static void assertRow(HolmosRow actualRow,HolmosRow expectedRow)throws HolmosFailedError{
		StringBuilder message=new StringBuilder();
		if(actualRow==null&&expectedRow==null){
			message.append("数据库行校验通过!两行的值均为null!");
			logger.info(message);
		}else if(actualRow !=null&&expectedRow!=null){
			HolmosRowDifference rowDifference=actualRow.compare(expectedRow);
			if(rowDifference!=null){
				message.append("数据库行校验失败!\n"+rowDifference.toString());
				logger.error(message);
				throw new HolmosFailedError(message.toString());
			}else{
				message.append("数据库行校验成功!");
				logger.info(message);
			}
		}else{
			message.append("数据库行校验失败");
			if(actualRow!=null){
				message.append("actualRow:"+actualRow.toString());
				message.append("expectedRow:null");
			}else{
				message.append("actualRow:null\n");
				message.append("expectedRow:"+expectedRow.toString());
			}
			logger.error(message);
			throw new HolmosFailedError(message.toString());
		}
	}
	/**校验两个表的值是否相同，如果相同，那么校验成功，程序继续执行<br>
	 * 校验失败，打出失败信息，程序退出*/
	public static void assertTable(HolmosTable actualTable,HolmosTable expectedTable)throws HolmosFailedError{
		StringBuilder message=new StringBuilder();
		if(actualTable==null&&expectedTable==null){
			message.append("数据库表校验通过!两行的值均为null!");
			logger.info(message);
			throw new HolmosFailedError(message.toString());
		}else if(actualTable !=null&&expectedTable!=null){
			HolmosTableDifference tableDifference=actualTable.compare(expectedTable);
			if(tableDifference!=null){
				message.append("数据库表校验失败!\n"+tableDifference.toString());
				logger.error(message);
				throw new HolmosFailedError(message.toString());
			}else{
				message.append("数据库表校验成功!");
				logger.info(message);
			}
		}else{
			message.append("数据库表校验失败");
			if(actualTable!=null){
				message.append("actualTable:"+actualTable.toString());
				message.append("expectedTable:null");
			}else{
				message.append("actualTable:null\n");
				message.append("expectedTable:"+expectedTable.toString());
			}
			logger.error(message);
			throw new HolmosFailedError(message.toString());
		}
	}
	/**比较两个Schema的信息是否相同,比较规则如下<br>
	 * <li>Shema的name不做比较</li>
	 * <li>比较里面的所有的table,对于actualSchema里面没有的table，忽略不做比较;对于存在的table的不存在的column也不做比较</li>
	 * */
	public static void assertSchema(HolmosSchema actualSchema,HolmosSchema expectedSchema){
		StringBuilder message=new StringBuilder();
		if(actualSchema==null&&expectedSchema==null){
			message.append("Schema校验失败!两个Schema均为null!");
			logger.error(message);
			throw new HolmosFailedError(message.toString());
		}else if(actualSchema!=null&&expectedSchema!=null){
			HolmosSchemaDifference schemaDifference=actualSchema.compare(expectedSchema);
			if(schemaDifference!=null){
				message.append("Schema校验失败!"+schemaDifference.toString());
				logger.error(message);
				throw new HolmosFailedError(message.toString());
			}else{
				message.append("Schema校验成功!");
				logger.info(message);
			}
		}else{
			message.append("Schema校验失败");
			if(actualSchema!=null){
				message.append("actualSchema:"+actualSchema.toString());
				message.append("expectedSchema:null");
			}else{
				message.append("actualSchema:\n");
				message.append("expectedSchema:"+expectedSchema.toString());
			}
			logger.error(message);
			throw new HolmosFailedError(message.toString());
		}
	}
	public static void assertEqualDbUnitDataSets(String schemaName,IDataSet actualDataSet,IDataSet expectedDataSet){
		HolmosDBStructFactory factory=new HolmosDBStructFactory();
		HolmosSchema expectedSchema=factory.buildSchema(schemaName, expectedDataSet);
		List<String> expectedTableNames = expectedSchema.getTableNames();
		HolmosSchema actualSchema=factory.buildSchemaForIncludedColumns(schemaName, actualDataSet, expectedTableNames);
		assertSchema(actualSchema, expectedSchema);
	}
}
