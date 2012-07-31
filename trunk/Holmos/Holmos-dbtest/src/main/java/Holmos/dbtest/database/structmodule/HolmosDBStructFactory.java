package Holmos.dbtest.database.structmodule;

import java.util.ArrayList;
import java.util.List;

import org.dbunit.dataset.Column;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.FilteredDataSet;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.ITableIterator;
import org.dbunit.dataset.datatype.DataType;
import org.dbunit.dataset.filter.IncludeTableFilter;

/**
 * {@link HolmosSchema}{@link HolmosTable}{@link HolmosRow}{@link HolmosColumn}
 * 对这些文件的生成和操作
 * */
public class HolmosDBStructFactory {
	/**
	 * 根据schemaName和dataset构建HolmosSchema
	 * */
	public HolmosSchema buildSchema(String schemaName,IDataSet dataSet){
		HolmosSchema schema=new HolmosSchema(schemaName);
		addTables(schema,dataSet);
		return schema;
	}
	public HolmosSchema buildSchemaForIncludedColumns(String schemaName,IDataSet dataSet,List<String>tablesInclude){
		IDataSet filteredDataSet = new FilteredDataSet(new IncludeTableFilter(tablesInclude.toArray(new String[tablesInclude.size()])), dataSet);
		return buildSchema(schemaName, filteredDataSet);
	}
	/**
	 * 将dataSet里面的信息加载进入schema里面
	 * */
	private void addTables(HolmosSchema schema, IDataSet dataSet) {
		try {
			ITableIterator it=dataSet.iterator();
			while(it.next()){
				ITable table=it.getTable();
				String tableName=table.getTableMetaData().getTableName();
				List<String>promaryKeyNames=getPromaryKeyNames(table);
				HolmosTable holmosTable=schema.getTableByName(tableName);
				if(holmosTable==null){
					holmosTable=new HolmosTable(tableName);
					schema.addTable(holmosTable);
				}
				addRows(table,holmosTable,promaryKeyNames);
			}
		} catch (DataSetException e) {
			
		} catch (NameInValidException e) {
			
		}
	}
	/**
	 * 添加行,并添加行中每一列的信息
	 * */
	private void addRows(ITable table, HolmosTable holmosTable,
			List<String> promaryKeyNames) {
		try {
			Column[]columns=table.getTableMetaData().getColumns();
			int rowCount=table.getRowCount();
			for(int index=0;index<rowCount;index++){
				HolmosRow holmosRow=new HolmosRow();
				holmosTable.addRow(holmosRow);
				for(Column column:columns){
					String columnName=column.getColumnName();
					DataType type=column.getDataType();
					Object value=table.getValue(index, columnName);
					HolmosColumn holmosColumn=new HolmosColumn(columnName, value, type);
					if(promaryKeyNames.contains(columnName)){
						holmosRow.addPrimaryKeyColumn(holmosColumn);
					}else{
						holmosRow.addColumn(holmosColumn);
					}
				}
			}
			
		} catch (DataSetException e) {
			
			e.printStackTrace();
		}
		
	}
	/**
	 * 获得table的主键名字信息
	 * */
	private List<String> getPromaryKeyNames(ITable table) {
		try {
			Column[] promaryKeys=table.getTableMetaData().getPrimaryKeys();
			List<String>promaryKeysList=new ArrayList<String>();
			for(Column promaryKey:promaryKeys){
				promaryKeysList.add(promaryKey.getColumnName());
			}
			return promaryKeysList;
		} catch (DataSetException e) {
			
			e.printStackTrace();
		}
		return null;
	}
}
