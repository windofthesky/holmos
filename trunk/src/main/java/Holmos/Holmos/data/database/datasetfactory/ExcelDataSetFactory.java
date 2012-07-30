package Holmos.Holmos.data.database.datasetfactory;

import java.io.File;
import java.util.Properties;

import Holmos.Holmos.data.database.dataset.HolmosMultiDataSet;

public class ExcelDataSetFactory implements HolmosDataSetFactory{
	String defaultSchemaName;

	@Override
	public HolmosMultiDataSet createMultiDataSet(File... dataSetFiles) {
		
		return null;
	}

	@Override
	public boolean isDataSetFileAvilid(File... dataSetFiles) {
		for(File file:dataSetFiles){
			String name=file.getName();
			if(name==null||"".equalsIgnoreCase(name))
				return false;
			if(name.endsWith(".xls")||name.endsWith(".xlsx"))
				return true;
		}
		return false;
	}

	@Override
	public String getFactoryType() {
		return "xlsx";
	}

	@Override
	public void init(Properties properties, String defaultSchemaName) {
		this.defaultSchemaName=defaultSchemaName;
	}

}
