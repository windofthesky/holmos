package Holmos.webtest.css;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import Holmos.webtest.asserttool.HolmosSimpleCheckTool;
import Holmos.webtest.basetools.HolmosBaseTools;
import Holmos.webtest.basetools.HolmosPropertiesTool;
import Holmos.webtest.constvalue.ConstValue;
import Holmos.webtest.element.Element;
import Holmos.webtest.element.Image;
import Holmos.webtest.exceptions.HolmosFailedError;
/**页面样式校验工具*/
public class HolmosCSSTool {
	private static Logger logger=Logger.getLogger(HolmosBaseTools.class);
	private static String commProConf="css_comman_property_config.properties";
	private static String imgProConf="css_img_property_config.properties";
	private static Properties cssCommanPropertiesConfig;
	private static Properties cssImgPropertiesConfig;
	/**初始化css配置文件*/
	private static void initCssConfigs(){
		cssCommanPropertiesConfig=HolmosPropertiesTool.getPropertyInfo(ConstValue.CSSPROPERTIESCONFIGDIR+"\\"+commProConf);
		cssImgPropertiesConfig=HolmosPropertiesTool.getPropertyInfo(ConstValue.CSSPROPERTIESCONFIGDIR+"\\"+imgProConf);
	}
	static {
		initCssConfigs();
		HolmosBaseTools.configLogProperties();
	}
	/**
	 * 指定元素本地存储的css属性信息，如果存在返回property文件信息，如果不存在返回null
	 * @param element 指定的holmos元素
	 * @return 指定元素本地存储的css属性信息
	 * */
	private static Properties getCSSValueFromLocal(Element element){
		String cssFilePath=ConstValue.CSSPROPERTIESDIR+"\\"+element.getFullName()+".properties";
		return HolmosPropertiesTool.getPropertyInfo(cssFilePath);
	}
	/**
	 * 在本地没有css属性文件的情况下，利用页面测试引擎来获取Img类型元素属性信息
	 * */
	private static Properties getImgCssValues(Element element){
		Properties imgCssProperties=new Properties();
		Iterator<Object> imgCssKeyIterator=cssImgPropertiesConfig.keySet().iterator();
		if(element.isExist()){
			while(imgCssKeyIterator.hasNext()){
				String cssKey=(String) imgCssKeyIterator.next();
				if(HolmosPropertiesTool.getBoolean(cssImgPropertiesConfig, cssKey)){
					imgCssProperties.put(cssKey,element.getElement().getCssValue(cssKey));
				}
			}
		}
		return imgCssProperties;
	}
	/**
	 * 在本地没有css属性文件的情况下，利用页面测试引擎来获取普通类型元素属性信息
	 * */
	private static Properties getCommCssValues(Element element){
		Properties comCssProperties=new Properties();
		Iterator<Object> imgCssKeyIterator=cssCommanPropertiesConfig.keySet().iterator();
		if(element.isExist()){
			while(imgCssKeyIterator.hasNext()){
				String cssKey=(String) imgCssKeyIterator.next();
				if(HolmosPropertiesTool.getBoolean(cssCommanPropertiesConfig, cssKey)){
					comCssProperties.put(cssKey,element.getElement().getCssValue(cssKey));
				}
			}
		}
		return comCssProperties;
	}
	/**
	 * 在本地没有css属性文件的情况下，利用页面测试引擎来获取元素属性信息
	 * */
	private static Properties getCssValuesFromEngine(Element element){
		if(element instanceof Image){
			return getImgCssValues(element);
		}return getCommCssValues(element);
	}
	/**
	 * 从本机获元素element的所有的存储的css属性，如果没有的话，那么重新从界面上获取，<br>
	 * 然后生成文件，并且将此文件存储到holmos框架指定的位置,如果存在，就直接获取
	 * @param element holmos框架指定的元素
	 * */
	public static Properties getCSSValues(Element element){
		Properties cssProperties=getCSSValueFromLocal(element);
		if(cssProperties!=null)return cssProperties;
		cssProperties=getCssValuesFromEngine(element);
		HolmosPropertiesTool.savePropertiesFile(cssProperties, ConstValue.CSSPROPERTIESDIR+"\\"+element.getFullName(), element.getFullName()+"css属性信息");
		return cssProperties;
	}
	/**
	 * 获取指定元素的指定css属性，从引擎中获取
	 * @param element 指定的元素
	 * @param 指定的属性名字
	 * @return 获取到得属性信息
	 * */
	public static String getCssValue(Element element,String cssValueKey){
		if(element.isExist()){
			return element.getCSSValue(cssValueKey);
		}
		return null;
	}
	/**
	 * 获取所有指定元素列表的位置信息，左上角的位置
	 * */
	private ArrayList<Point> getLocations(ArrayList<Element>elements){
		ArrayList<Point> locations=new ArrayList<Point>();
		for(Element element:elements){
			locations.add(element.getLocation());
		}
		return locations;
	}
	/**
	 * 获取所有指定元素列表的大小信息
	 * */
	private ArrayList<Dimension> getSizes(ArrayList<Element>elements){
		ArrayList<Dimension> sizes=new ArrayList<Dimension>();
		for(Element element:elements){
			sizes.add(element.getSize());
		}
		return sizes;
	}
	/**
	 * 校验给定元素列表是否上边界对其
	 * @param elements 待校验的元素
	 * */
	public void assertTopAlign(ArrayList<Element>elements){
		ArrayList<Point>locations=getLocations(elements);
		for(int i=1;i<locations.size();i++){
			if(locations.get(i).x!=locations.get(0).x){
				StringBuilder message=new StringBuilder("上边界对齐校验失败:\n");
				for(int j=0;j<locations.size();j++){
					message.append(elements.get(j).getComment()+"的上边界位置:"+locations.get(j).x+"\n");
				}
				throw new HolmosFailedError(message.toString());
			}
		}
		logger.info("上边界校验成功!");
	}
	/**
	 * 校验给定元素列表是否左边界对其
	 * @param elements 待校验的元素
	 * */
	public void assertLeftAlign(ArrayList<Element>elements){
		ArrayList<Point>locations=getLocations(elements);
		for(int i=1;i<locations.size();i++){
			if(locations.get(i).y!=locations.get(0).y){
				StringBuilder message=new StringBuilder("左边界对齐校验失败:\n");
				for(int j=0;j<locations.size();j++){
					message.append(elements.get(j).getComment()+"的做边界位置:"+locations.get(j).y+"\n");
				}
				throw new HolmosFailedError(message.toString());
			}
		}
		logger.info("左边界校验成功!");
	}
	/**
	 * 校验给定元素列表是否右边界对其
	 * @param elements 待校验的元素
	 * */
	public void assertRightAlign(ArrayList<Element>elements){
		ArrayList<Point>locations=getLocations(elements);
		ArrayList<Dimension>sizes=getSizes(elements);
		for(int i=1;i<locations.size();i++){
			if(locations.get(i).x+sizes.get(i).width!=locations.get(0).x+sizes.get(0).width){
				StringBuilder message=new StringBuilder("右边界对齐校验失败:\n");
				for(int j=0;j<locations.size();j++){
					message.append(elements.get(j).getComment()+"的右边界位置:"+locations.get(j).x+sizes.get(j).width+"\n");
				}
				throw new HolmosFailedError(message.toString());
			}
		}
		logger.info("右边界校验成功!");
	}
	/**
	 * 校验给定元素列表是否下边界对其
	 * @param elements 待校验的元素
	 * */
	public void assertBottomAlign(ArrayList<Element>elements){
		ArrayList<Point>locations=getLocations(elements);
		ArrayList<Dimension>sizes=getSizes(elements);
		for(int i=1;i<locations.size();i++){
			if(locations.get(i).y+sizes.get(i).height!=locations.get(0).y+sizes.get(0).height){
				StringBuilder message=new StringBuilder("下边界对齐校验失败:\n");
				for(int j=0;j<locations.size();j++){
					message.append(elements.get(j).getComment()+"的下边界位置:"+locations.get(j).y+sizes.get(j).height+"\n");
				}
				throw new HolmosFailedError(message.toString());
			}
		}
		logger.info("下边界校验成功!");
	}
	/**
	 * 比较元素的前景色
	 * */
	public void assertColorEquals(ArrayList<Element>elements){
		ArrayList<String> colors=new ArrayList<String>();
		for(Element element:elements){
			colors.add(element.getCSSValue("color"));
		}
		StringBuilder message=new StringBuilder();
		if(colors.get(0)==null){
			throw new HolmosFailedError(elements.get(0).getComment()+"color属性获取不到~");
		}
		for(int i=0;i<colors.size();i++){
			if(!colors.get(i).equalsIgnoreCase(colors.get(0))){
				for(int j=0;j<colors.size();j++){
					message.append(elements.get(j).getComment()+"的颜色:"+colors.get(j)+"\n");
				}
				throw new HolmosFailedError(message.toString());
			}
		}
		logger.info("颜色校验成功~!");
	}
	/**
	 * 校验元素的左上角横坐标依次递增
	 * */
	public void assertHorizonBigger(ArrayList<Element>elements){
		ArrayList<Point>locations=getLocations(elements);
		StringBuilder message=new StringBuilder();
		for(int i=1;i<locations.size();i++){
			if(locations.get(i).x<locations.get(i-1).x){
				message.append("元素横坐标依次递增校验失败!\n");
				for(int j=0;j<locations.size();j++){
					message.append(elements.get(j).getComment()+"的横坐标为:"+locations.get(j).x+"\n");
				}
				throw new HolmosFailedError(message.toString());
			}
		}
		logger.info("元素横坐标依次递增校验成功~!");
	}
	/**
	 * 校验元素的左上角横坐标依次递减
	 * */
	public void assertHorizonSmaller(ArrayList<Element>elements){
		ArrayList<Point>locations=getLocations(elements);
		StringBuilder message=new StringBuilder();
		for(int i=1;i<locations.size();i++){
			if(locations.get(i).x>locations.get(i-1).x){
				message.append("元素横坐标依次递减校验失败!\n");
				for(int j=0;j<locations.size();j++){
					message.append(elements.get(j).getComment()+"的横坐标为:"+locations.get(j).x+"\n");
				}
				throw new HolmosFailedError(message.toString());
			}
		}
		logger.info("元素横坐标依次递减校验成功~!");
	}
	/**
	 * 校验元素的左上角纵坐标依次递增
	 * */
	public void assertVerticalBigger(ArrayList<Element>elements){
		ArrayList<Point>locations=getLocations(elements);
		StringBuilder message=new StringBuilder();
		for(int i=1;i<locations.size();i++){
			if(locations.get(i).y<locations.get(i-1).y){
				message.append("元素横坐标依次递增校验失败!\n");
				for(int j=0;j<locations.size();j++){
					message.append(elements.get(j).getComment()+"的纵坐标为:"+locations.get(j).y+"\n");
				}
				throw new HolmosFailedError(message.toString());
			}
		}
		logger.info("元素横坐标依次递增校验成功~!");
	}
	/**
	 * 校验元素的左上角纵坐标依次递减
	 * */
	public void assertVerticalSmaller(ArrayList<Element>elements){
		ArrayList<Point>locations=getLocations(elements);
		StringBuilder message=new StringBuilder();
		for(int i=1;i<locations.size();i++){
			if(locations.get(i).y>locations.get(i-1).y){
				message.append("元素横坐标依次递减校验失败!\n");
				for(int j=0;j<locations.size();j++){
					message.append(elements.get(j).getComment()+"的纵坐标为:"+locations.get(j).y+"\n");
				}
				throw new HolmosFailedError(message.toString());
			}
		}
		logger.info("元素横坐标依次递减校验成功~!");
	}
	/**
	 * 校验本地文件中的对应的css属性与现在由引擎得到的css属性是否一致，如果该属性在社会自里面为不做校验，则直接返回，不抛异常
	 * */
	public void assertCssValue(Element element,String cssKey){
		Properties localProperties=getCSSValueFromLocal(element);
		if(element instanceof Image){
			if(!HolmosPropertiesTool.getBoolean(cssImgPropertiesConfig, cssKey)){
				logger.warn(cssKey+"在设置中不做校验,在本地的css属性文件中没有该属性信息");
				return;
			}
			String engineCssValue=getCssValue(element, cssKey);
			String localCssValue=HolmosPropertiesTool.getValue(localProperties, cssKey);
			HolmosSimpleCheckTool.assertEqual(engineCssValue, localCssValue);
		}else{
			if(!HolmosPropertiesTool.getBoolean(cssCommanPropertiesConfig, cssKey)){
				logger.warn(cssKey+"在设置中不做校验,在本地的css属性文件中没有该属性信息");
				return;
			}
			String engineCssValue=getCssValue(element, cssKey);
			String localCssValue=HolmosPropertiesTool.getValue(localProperties, cssKey);
			HolmosSimpleCheckTool.assertEqual(engineCssValue, localCssValue);
		}
	}
	/**
	 * 校验该元素所有属性值
	 * */
	public void assertAllCssValues(Element element){
		Properties localProperties=getCSSValueFromLocal(element);
		Properties engineProperties=getCssValuesFromEngine(element);
		//HolmosReflectCheck.assertEquals(localProperties, engineProperties, HolmosRefectionComparatorMode.IGNORE_COLLECTION_ORDER);
	}
	/**
	 * 校验元素的大小属性是否相同
	 * */
	public void assertSizesEquals(ArrayList<Element>elements){
		ArrayList<Dimension> sizes=getSizes(elements);
		StringBuilder message=new StringBuilder();
		for(int i=1;i<sizes.size();i++){
			if(!sizes.get(i).equals(sizes.get(0))){
				message.append("元素大小校验失败:\n");
				for(int j=0;j<sizes.size();j++){
					message.append(elements.get(j).getComment()+"的小小为:"+sizes.get(j).toString());
				}
				logger.error(message);
				throw new HolmosFailedError(message.toString());
			}
		}
		message.append("元素大小校验成功!");
	}
}
