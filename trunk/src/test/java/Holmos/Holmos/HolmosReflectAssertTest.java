package Holmos.Holmos;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import Holmos.Holmos.assertVerify.HolmosReflectCheck;
import Holmos.Holmos.assertVerify.reflectCheck.HolmosRefectionComparatorMode;

public class HolmosReflectAssertTest {

	Person friend=new Person("friend", 10, 142.2);
	Person friend1=new Person("friend1", 10, 142.2);
	Person friend2=new Person("friend2", 10, 142.2);
	Person friend3=new Person("friend3", 10, 142.2);
	Person friend4=new Person("friend4", 10, 142.2);
	Person friend5=new Person("friend5", 10, 142.2);
	Person friend6=new Person("friend6", 10, 142.2);
	Person friend7=new Person("friend7", 10, 142.2);
	Person friend8=new Person("friend8", 10, 142.2);
	Person friend9=new Person("friend9", 10, 142.2);
	
	Cloth cloth1=new Cloth(Color.black, "cloth1");
	Cloth cloth2=new Cloth(Color.black, "cloth2");
	Cloth cloth3=new Cloth(Color.black, "cloth3");
	Cloth cloth4=new Cloth(Color.black, "cloth4");
	Cloth cloth5=new Cloth(Color.black, "cloth5");
	Cloth cloth6=new Cloth(Color.black, "cloth6");
	Cloth cloth7=new Cloth(Color.black, "cloth7");
	Cloth cloth8=new Cloth(Color.black, "cloth8");
	Cloth cloth9=new Cloth(Color.black, "cloth9");
	
	Person xiaohong=new Person("xiaohong", 10, 142.2);
	Person xiaoming=new Person("xiaoming", 10, 111.3);
	class Cloth{
		private Color color;
		private String brand;
		public Cloth(Color color,String brand){
			this.color=color;
			this.brand=brand;
		}
	}
	class Person{
		private String name;
		private int age;
		private double high;
		private ArrayList<String> fullName=new ArrayList<String>();
		private ArrayList<Person> friends=new ArrayList<HolmosReflectAssertTest.Person>();
		private ArrayList<Cloth> cloths=new ArrayList<Cloth>();
		private Map<String,Cloth> clothsInfo=new HashMap<String, Cloth>();
		public Person(String name,int age,double high){
			this.name=name;
			this.age=age;
			this.high=high;
		}
		public void addFullName(String partName){
			this.fullName.add(partName);
		}
		public void addFriend(Person person){
			this.friends.add(person);
		}
		public void addCloth(Cloth cloth){
			this.cloths.add(cloth);
			this.clothsInfo.put(cloth.brand, cloth);
		}
		public Person clone(){
			Person person=new Person(name, age, high);
			person.cloths=new ArrayList<HolmosReflectAssertTest.Cloth>(cloths);
			person.friends=new ArrayList<HolmosReflectAssertTest.Person>(friends);
			person.fullName=new ArrayList<String>(fullName);
			person.clothsInfo=new HashMap<String, HolmosReflectAssertTest.Cloth>(clothsInfo);
			return person;
		}
	}
	@Test
	public void testBaseData(){
		//int expected=1,actual=1;
		//HolmosReflectCheck.assertEquals(expected, actual);
		//HolmosReflectCheck.assertEquals(1,0);
		//HolmosReflectCheck.assertEquals("expected","actual");
		//HolmosReflectCheck.assertEquals("haha","haha");
		//HolmosReflectCheck.assertEquals(1.12,1);
		//HolmosReflectCheck.assertEquals(1.12,1.12);
		List expectedList=new ArrayList<String>();
		List actualList= new ArrayList<String>();
		expectedList.add("haha");
		expectedList.add("haha1");
		actualList.add("haha1");
		actualList.add("haha");
		HolmosReflectCheck.assertEquals(expectedList.toArray(), actualList, HolmosRefectionComparatorMode.IGNORE_COLLECTION_ORDER);
	}
	@Test
	public void testObject(){
		
		
		xiaohong.addCloth(cloth9);
		xiaohong.addCloth(cloth8);
		xiaoming.addCloth(cloth9);
		xiaoming.addCloth(cloth6);
		
		xiaoming.addFriend(friend1);
		xiaoming.addFriend(friend9);
		xiaohong.addFriend(friend8);
		
		HolmosReflectCheck.assertEquals(xiaohong, xiaohong.clone());
	}
	@Test
	public void assertFieldValue(){
		HolmosReflectCheck.assertFieldValueEquals("name", "xiaohng", xiaohong);
	}
}
