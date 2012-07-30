package Holmos.Holmos;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.junit.Test;

import Holmos.Holmos.innerTools.MyFile;

public class HolmosPropertyTest {

	@Test
	public void testPropertyStore(){
		Properties properties=new Properties();
		properties.put("asdfa", "adsfasdf");
		MyFile.createFile("C:\\pro.properties");
		try {
			properties.store(new FileOutputStream(new File("C:\\pro.properties")), "adsfasd");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
