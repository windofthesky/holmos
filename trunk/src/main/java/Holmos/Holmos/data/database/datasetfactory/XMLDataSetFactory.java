package Holmos.Holmos.data.database.datasetfactory;

import java.io.File;
import java.util.Properties;

import Holmos.Holmos.data.database.dataset.HolmosMultiDataSet;

public class XMLDataSetFactory implements HolmosDataSetFactory{

	private String defaultSchemaName;
	
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
			if(name.endsWith(".xml"))
				return true;
		}
		return false;
	}

	@Override
	public String getFactoryType() {
		return "xml";
	}

	@Override
	public void init(Properties properties, String defaultSchemaName) {
		this.defaultSchemaName=defaultSchemaName;
	}

}
