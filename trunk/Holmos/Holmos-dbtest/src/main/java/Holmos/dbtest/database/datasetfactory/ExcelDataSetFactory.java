package Holmos.dbtest.database.datasetfactory;

import java.io.File;
import java.util.Properties;

import Holmos.dbtest.database.dataset.HolmosMultiDataSet;

public class ExcelDataSetFactory implements HolmosDataSetFactory{
	String defaultSchemaName;

	public HolmosMultiDataSet createMultiDataSet(File... dataSetFiles) {
		
		return null;
	}

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

	

	public void init(Properties properties, String defaultSchemaName) {
		this.defaultSchemaName=defaultSchemaName;
	}

	public String getFactoryType() {
		// TODO Auto-generated method stub
		return null;
	}

}
