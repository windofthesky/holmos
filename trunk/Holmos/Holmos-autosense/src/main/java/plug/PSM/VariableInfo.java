package Holmos.Holmos.plug.PSM;

import java.util.ArrayList;
import java.util.Map;

import Holmos.Holmos.plug.LIJSM.LocatorInfo;
import Holmos.Holmos.plug.LIJSM.LocatorInfoManager;
import Holmos.Holmos.plug.LIJSM.JAVAM.JavaFileLocatorInfoManager;
import Holmos.Holmos.plug.PSM.container.CollectionInfo;
import Holmos.Holmos.plug.PSM.container.FrameInfo;
import Holmos.Holmos.plug.PSM.container.SubPageInfo;
import Holmos.Holmos.plug.PSM.element.ElementInfo;

/**变量类,PSM的变量基础类，所有类型的元素都是此类的子类 
 * @author 吴银龙(15857164387)
 * */
public class VariableInfo implements NetPageInfo {
	/*此变量的名字*/
	protected String name;
	/*这个变量的说明，框架执行的时候用到*/
	protected String comment;
	/*此变量的类型*/
	protected INFOTYPE type;
	/*这个变量的locator定位信息*/
	protected LocatorInfo locatorInfo;
	/*locator信息管理器，负责locator信息读写*/
	protected LocatorInfoManager javalocatorManager;
	public void setLocatorInfo(LocatorInfo locatorInfo) {
		this.locatorInfo = locatorInfo;
	}
	public LocatorInfoManager getJavalocatorManager() {
		return javalocatorManager;
	}
	/*这个变量的父容器信息*/
	protected Object parentContainer;
	
	/**设置父容器信息
	 * @param parentContainer */
	public void setParentContainer(Object parentContainer) {
		this.parentContainer = parentContainer;
	}
	/**获得此变量的名字*/
	public String getName() {
		return name;
	}
	/**获得此变量的父容器对象 */
	public Object getParentContainer(){
		return this.parentContainer;
	}
	/**获得这个变量的说明，框架执行的时候用到 */
	public String getComment() {
		return comment;
	}
	/**获得此变量的类型*/
	public INFOTYPE getType() {
		return type;
	}
	/**获得这个变量的locator定位信息 */
	public LocatorInfo getLocatorInfo() {
		return locatorInfo;
	}
	/**变量的构造函数*/
	public VariableInfo(String name,String comment,INFOTYPE type){
		this.name=name;
		this.comment=comment;
		this.type=type;
		this.javalocatorManager=new JavaFileLocatorInfoManager(this);
	}
	public static VariableInfo createVariableInfo(String name,String comment,INFOTYPE type){
		switch (type) {
		case SUBPAGE:
			return new SubPageInfo(name, comment);
		case COLLECTION:
			return new CollectionInfo(name, comment);
		case FRAME:
			return new FrameInfo(name, comment);
		case BUTTON:
		case CHECKBOX:
		case COMOBOBOX:
		case ELEMENT:
		case LABEL:
		case LINK:
		case RADIOBUTTON:
		case RICHTEXTFIELD:
		case TABLE:
		case TEXTFIELD:
			return new ElementInfo(name, comment, type);
		default:
			break;
		}return null;
	}
	
	/**根据现在的信息更新java文件和locator存储地的信息 */
	public void update(){
		
	}
	/**
	 * 重写toString()方法，返回这个变量的信息 
	 * @return 变量信息
	 * */
	public String toString(){
		StringBuilder variableInfo=new StringBuilder();
		variableInfo.append("/** 类型:"+PSMTool.getInfoTypeClassName(type)+"  说明:"+comment+" */");
		return variableInfo.toString();
	}
	/**
	 * 判断此变量信息是否是容器类型的信息
	 * @return true 是的<br> false 不是的
	 * */
	public boolean isContainer(){
		switch (type) {
		case SUBPAGE:
		case COLLECTION:
			return true;
		default:
			return false;
		}
	}
	/**判断此变量信息是否是元素类型的信息 
	 * @return true 是的 <br> false 不是的
	 * */
	public boolean isElement(){
		return !isContainer();
	}
	/**获得信息的描述,就是变量信息的List,信息格式为:<br>
	 * javadoc注释符号:类型:xxxxxx  说明:xxxxxxx<br>
 	 * type variable=new type(comment);<br>
     * @return 返回的变量信息*/
	public ArrayList<String>getInfoDescription(){
		ArrayList<String>infoDescription=new ArrayList<String>();
		infoDescription.add(toString());
		infoDescription.add("public "+PSMTool.getInfoTypeClassName(type)+" "+name+"= new "+PSMTool.getInfoTypeClassName(type)+" (\"" +
				comment+"\");");
		return infoDescription;
	}
	/**判断此Variable与另外一个Variable是否相同
	 * @return true 相同 <br> false 不相同
	 * */
	public boolean isSameAs(VariableInfo anotherVariable){
		if(name==null||comment==null||type==null)
			return false;
		if(!name.equalsIgnoreCase(anotherVariable.name))
			return false;
		if(!comment.equalsIgnoreCase(anotherVariable.comment))
			return false;
		if(!type.equals(anotherVariable.type))
			return false;
		if(!locatorInfo.equals(anotherVariable.locatorInfo)){
			return false;
		}return true;
	}
	public Map<String, String> getAttributes() {
		return locatorInfo.getAttributes();
	}
	public void addAttribute(String attributeName, String attributeValue){
		locatorInfo.addAttribute(attributeName, attributeValue);
		this.javalocatorManager.addAttributeInfo(attributeName, attributeValue);
	}
	public void addCss(String css){
		this.locatorInfo.addCss(css);
		javalocatorManager.addCSSInfo(css);
	}
	public void addText(String text){
		this.locatorInfo.addText(text);
		javalocatorManager.addTextInfo(text);
	}
	public void addXpath(String xpath){
		this.locatorInfo.addXpath(xpath);
		javalocatorManager.addXpathInfo(xpath);
	}
	public String getCss(){
		return this.locatorInfo.getCss();
	}
	public String getText(){
		return locatorInfo.getText();
	}
	public String getXpath(){
		return locatorInfo.getXpath();
	}  
	public String getAttribute(String attributeName){
		return locatorInfo.getAttribute(attributeName);
	}
}
