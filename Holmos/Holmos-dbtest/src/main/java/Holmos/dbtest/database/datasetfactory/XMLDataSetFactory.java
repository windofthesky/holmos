package Holmos.dbtest.database.datasetfactory;

import java.io.File;
import java.util.Properties;

import Holmos.dbtest.database.dataset.HolmosMultiDataSet;

public class XMLDataSetFactory implements HolmosDataSetFactory{

	private String defaultSchemaName;
	
	public HolmosMultiDataSet createMultiDataSet(File... dataSetFiles) {
		
		return null;
	}

	public boolean isDataSetFileAvilid(File... dataSetFiles) {
		for(File file:dataSetFiles){
			String name=file.getName();
			if(name==null||"".equalsIgnoreCase(name))
				return false;
			if(name.endsWith(".xml"))
				return true;
		}
		return false;
	}

	public String getFactoryType() {
		return "xml";
	}

	public void init(Properties properties, String defaultSchemaName) {
		this.defaultSchemaName=defaultSchemaName;
	}

}
