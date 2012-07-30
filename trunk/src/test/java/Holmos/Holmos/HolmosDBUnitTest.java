package Holmos.Holmos;
import java.lang.reflect.Method;

import org.junit.Test;

import Holmos.Holmos.data.database.annotation.HolmosDataSet;

public class HolmosDBUnitTest {
	@Test
	public void testAnnotation(){
		Method[] methodsOfAnnotation=HolmosDataSet.class.getDeclaredMethods();
		for(Method method:methodsOfAnnotation){
			System.out.println(method.getName());
		}
	}
}
