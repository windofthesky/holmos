package Holmos.Holmos;

import java.util.ArrayList;
import java.util.List;

public class Person {
	private int age;
	private String name;
	private List<String>clothes;
	private Person son;
	public Person(int age,String name,ArrayList<String>clothes,Person son){
		this.age=age;
		this.name=name;
		this.clothes=clothes;
		this.son=son;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<String> getClothes() {
		return clothes;
	}
	public void setClothes(List<String> clothes) {
		this.clothes = clothes;
	}
	public Person getSon() {
		return son;
	}
	public void setSon(Person son) {
		this.son = son;
	}
}
