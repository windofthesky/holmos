package Holmos.basetools;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.PropertyConfigurator;

import Holmos.constvalue.ConfigConstValue;
import Holmos.constvalue.ConstValue;
import Holmos.exceptions.HolmosFailedError;
import Holmos.webtest.Allocator;
import Holmos.webtest.basetools.HolmosWindow;
import Holmos.webtest.element.Element;
import Holmos.webtest.struct.Collection;
import Holmos.webtest.struct.Frame;
import Holmos.webtest.struct.Page;
import Holmos.webtest.struct.SubPage;
/**
 * @author 吴银龙(15857164387)
 * */
public class HolmosBaseTools {
	/**配置日志文件的地址，打印必要的日志<br>
	 * 如果日志文件地址找不到，那么就在框架里面默认的地址创建日志文件<br>*/
	public static void configLogProperties(){
		String logDirPath=getLogDirPath();
		File file=new File(logDirPath);
		if(!file.exists()){
			try {
				if(file.mkdirs()){
					new File(logDirPath+"\\log.log").createNewFile();
					new File(logDirPath+"\\error.log").createNewFile();
				}
			} catch (Exception e1) {
				System.out.println("发现日志文件不存在，但创建文件失败！请联系黄庭！");
				e1.printStackTrace();
			}
		}
		PropertyConfigurator.configure(ConfigConstValue.LOGCONFIG);
	}
	/**获得日志目录地址
	 * @return 日志目录地址
	 * @throws FileNotFoundException 
	 * */
	private static String getLogDirPath(){
		Properties properties=new Properties();
		try{
			InputStream in = new BufferedInputStream (new FileInputStream(ConfigConstValue.HOLMOSCONFFILE));
			properties.load(in);
			return HolmosPropertiesTool.getValue(properties, "logdir");
		}catch(IOException e){
			throw new HolmosFailedError("holmos框架配置文件找不到!"); 
		}
	}
	/**
	 * 根据配置好的截屏存放地址，如果该地址目录不存在，则创建，如果存在，则不作操作
	 * */
	public static void configScreenShotLocation(){
		String screenShotDir=getScreenShotDirPath();
		ConstValue.screenShotDir=screenShotDir;
		File file=new File(screenShotDir);
		if(!file.exists()){
			try {
				if(file.mkdirs()){
					System.out.println("截屏地址创建成功!");
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * 从Holmos配置文件中取得截屏地址
	 * */
	public static String getScreenShotDirPath(){
		if(ConstValue.screenShotDir!=null)
			return ConstValue.screenShotDir;
		Properties properties=new Properties();
		try{
			InputStream in = new BufferedInputStream (new FileInputStream(ConfigConstValue.HOLMOSCONFFILE));
			properties.load(in);
			return HolmosPropertiesTool.getValue(properties, "screenShotDir");
		}catch(IOException e){
			throw new HolmosFailedError("holmos框架配置文件找不到!"); 
		}
	}
	/**当前线程休息miliSeconds毫秒*/
	public static void sleep(int miliSeconds){
		try {
			Thread.sleep(miliSeconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	/**根据target来打开页面，根据target来判断是否在新页面打开*/
	public static void openByTarget(String url,String target){
		if(target==null){
			Allocator.getInstance().currentWindow.open(url);
		}else if(target.equalsIgnoreCase(ConstValue.BLANK)){
			HolmosWindow.openNewWindow(url);
		}else if(target.equalsIgnoreCase(ConstValue.SELF)){
			Allocator.getInstance().currentWindow.open(url);
		}
	}
	/**变更Element变量的注释说明，在反射调动观察者的时候将这个变量的名字加到说明里面*/
	public static void insertElementName(Element element,String name){
		StringBuilder commentTemp=new StringBuilder(element.getComment());
		commentTemp.insert(1, name);
		element.setComment(commentTemp.toString());
	}
	/**变更SubPage变量的注释说明，在反射调动观察者的时候将这个变量的名字加到说明里面*/
	public static void insertSubPageName(SubPage subpage,String name){
		StringBuilder commentTemp=new StringBuilder(subpage.getComment());
		commentTemp.insert(1, name);
		subpage.setComment(commentTemp.toString());
	}
	/**变更Page变量的注释说明，在反射调动观察者的时候将这个变量的名字加到说明里面*/
	public static void insertPageName(Page page,String name){
		StringBuilder commentTemp=new StringBuilder(page.getComment());
		commentTemp.insert(1, name);
		page.setComment(commentTemp.toString());
	}
	/**变更Frame变量的注释说明，在反射调动观察者的时候将这个变量的名字加到说明里面*/
	public static void insertFrameName(Frame frame,String name){
		StringBuilder commentTemp=new StringBuilder(frame.getComment());
		commentTemp.insert(1, name);
		frame.setComment(commentTemp.toString());
	}
	/**变更Collection变量的注释说明，在反射调动观察者的时候将这个变量的名字加到说明里面*/
	public static void insertCollectionName(Collection collection,String name){
		StringBuilder commentTemp=new StringBuilder(collection.getComment());
		commentTemp.insert(1, name);
		collection.setComment(commentTemp.toString());
	}
	
}
