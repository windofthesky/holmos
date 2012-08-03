package Holmos.webtest.log;
public class MyLogger{
	private String name;
	private Class clazz;
	protected MyLogger(String name) {
		this.name=name;
	}
	public static MyLogger getLogger(String name){
		return new MyLogger(name);
	}
	public static MyLogger getLogger(Class clazz){
		return new MyLogger(clazz);
	}
	private MyLogger(Class clazz){
		this.clazz=clazz;
	}
	public void error(Object message){
		outputClazzInfo();
		System.out.print("error:");
		System.out.println(message);
	}
	public void info(Object message){
		outputClazzInfo();
		System.out.print("info:");
		System.out.println(message);
	}
	public void warn(Object message){
		outputClazzInfo();
		System.out.print("warn:");
		System.out.println(message);
	}
	public void outputClazzInfo(){
		if(name!=null)
			System.out.print(name+":");
		if(clazz!=null)
			System.out.print(clazz.getName()+":");
	}
}
