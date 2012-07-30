package Holmos.Holmos.plug.LIJSM.JAVAM;

import java.util.ArrayList;
import java.util.Map.Entry;

import Holmos.Holmos.innerTools.MyFileOperation;
import Holmos.Holmos.innerTools.MyString;
import Holmos.Holmos.plug.LIJSM.LocatorInfo;
import Holmos.Holmos.plug.LIJSM.LocatorInfoManager;
import Holmos.Holmos.plug.PSM.VariableInfo;
import Holmos.Holmos.plug.PSM.container.ContainerInfo;
import Holmos.Holmos.plug.PSM.container.PageInfo;

public class JavaFileLocatorInfoManager implements LocatorInfoManager{

	
	private VariableInfo variable;
	/** 构造函数:此Locator信息所有者所在的容器,可能为PageInfo,亦可能是ContainerInfo*/
	public JavaFileLocatorInfoManager(VariableInfo variable){
		this.variable=variable;
	}
	@Override
	public void removeLocatorInfo(){
		MyFileOperation fileOperation = null;
		ArrayList<String>parentFileContent=null;
		if(variable.getParentContainer() instanceof PageInfo){
			fileOperation=((PageInfo)variable.getParentContainer()).getFileOperationRobot();
		}else if(variable.getParentContainer() instanceof ContainerInfo){
			fileOperation=((ContainerInfo)variable.getParentContainer()).getFileOperationRobot();
		}
		parentFileContent=fileOperation.getFileContent();
		boolean findElment=false;
		for(int i=0;i<parentFileContent.size();i++){
			while(parentFileContent.get(i).contains(variable.getName()+".")){
				parentFileContent.remove(i);
				findElment=true;
			}
			if(findElment&&parentFileContent.get(i).trim().equalsIgnoreCase("}")){
				parentFileContent.remove(i);
				if(parentFileContent.get(i-1).trim().equalsIgnoreCase("{")){
					parentFileContent.remove(i-1);
				}
				break;
			}
		}
		fileOperation.setFileContent(parentFileContent);
	}
	@Override
	public LocatorInfo getLocatorInfoByContent(ArrayList<String> locatorInfoContent) {
		LocatorInfo locator=new LocatorInfo();
		for(String locatorInfo:locatorInfoContent){
			MyString line=new MyString(locatorInfo);
			if(locatorInfo.contains("addIDLocator")){
				locator.addAttribute("id", line.pickUpStr("\"", "\""));
			}else if(locatorInfo.contains("addNameLocator")){
				locator.addAttribute("name", line.pickUpStr("\"", "\""));
			}else if(locatorInfo.contains("addXpathLocator")){
				locator.addXpath(line.pickUpStr("\"", "\""));
			}else if(locatorInfo.contains("addCSSLocator")){
				locator.addCss(line.pickUpStr("\"", "\""));
			}else if(locatorInfo.contains("addLinkTextLocator")){
				locator.addText(line.pickUpStr("\"", "\""));
			}else if(locatorInfo.contains("addAttributeLocator")){
				locator.addAttribute(line.getSplitPart(1, "\""), line.getSplitPart(3, "\""));
			}
		}
		return locator;
	}
	
	@Override
	public ArrayList<String> createLocatorInfo() {
		LocatorInfo locatorInfo =variable.getLocatorInfo();
		ArrayList<String> locatorInfoContent = new ArrayList<String>();
		if(null != locatorInfo) {
			locatorInfoContent.add("{");
			if(locatorInfo.getAttribute("id")!=null&&locatorInfo.getAttribute("id")!="")
				locatorInfoContent.add(variable.getName()+".addIDLocator(\""+locatorInfo.getAttribute("id")+"\");");
			if(locatorInfo.getAttribute("name")!=null&&locatorInfo.getAttribute("name")!="")
				locatorInfoContent.add(variable.getName()+".addNameLocator(\""+locatorInfo.getAttribute("name")+"\");");
			if(locatorInfo.getXpath()!=null&&locatorInfo.getXpath()!="")
				locatorInfoContent.add(variable.getName()+".addXpathLocator(\""+locatorInfo.getXpath()+"\");");
			if(locatorInfo.getCss()!=null&&locatorInfo.getCss()!="")
				locatorInfoContent.add(variable.getName()+".addCSSLocator(\""+locatorInfo.getCss()+"\");");
			if(locatorInfo.getText()!=null&&locatorInfo.getText()!="")
				locatorInfoContent.add(variable.getName()+".addLinkTextLocator(\""+locatorInfo.getText()+"\");");
			for(Entry<String, String>attribute:locatorInfo.getAttributes().entrySet()){
				if(attribute.getKey()=="id"||attribute.getKey()=="name")
					continue;
				locatorInfoContent.add(variable.getName()+".addAttributeLocator(\""+attribute.getKey().toString()+"\","
						+"\""+attribute.getValue()+"\");");
			}
			locatorInfoContent.add("}");
		}
		return locatorInfoContent;
	}
	private void addLocator(String value,String sign){
		if(value==null)return;
		ArrayList<String>fileContent=null;
		MyFileOperation fileOperation=null;
		if(variable.getParentContainer() instanceof PageInfo){
			fileOperation=((PageInfo)variable.getParentContainer()).getFileOperationRobot();
		}else{
			fileOperation=((ContainerInfo)variable.getParentContainer()).getFileOperationRobot();
		}fileContent=fileOperation.getFileContent();
		int lineNum=getVariableDefineLineNum(fileContent);
		boolean hasLocator=false;
		for(int i=lineNum;i<fileContent.size();i++){
			if(fileContent.get(i).trim().equalsIgnoreCase("}")){
				if(!hasLocator){
					fileContent.add(i, variable.getName()+"."+sign+"(\""+value+"\");");
				}
				break;
			}
			if(fileContent.get(i).contains(sign)){
				hasLocator=true;
				String oldXpath=new MyString(fileContent.get(i)).pickUpStr("\"", "\"");
				if(value.equalsIgnoreCase(oldXpath))
				fileContent.set(i, fileContent.get(i).replace(oldXpath, value));
				break;
			}
		}
		fileOperation.setFileContent(fileContent);
	}
	@Override
	public void addXpathInfo(String xpath) {
		addLocator(xpath, "addXpathLocator");
	}
	/**找到此元素在其父容器文件里面定义的位置(起始位置)*/
	private int getVariableDefineLineNum(ArrayList<String>fileContent){
		int lineNum=0;
		for(String line:fileContent){
			//找到了这个元素的位置
			if(line.contains(" "+variable.getName()+" ")){
				break;
			}
			lineNum++;
		}
		return lineNum;
	}
	@Override
	public void addCSSInfo(String css) {
		addLocator(css, "addCSSLocator");
	}

	@Override
	public void addTextInfo(String text) {
		addLocator(text, "addIDlocator");
	}

	@Override
	public void addIDInfo(String id) {
		addLocator(id, "addLinkIDLocator");
	}

	@Override
	public void addNameInfo(String name) {
		addLocator(name, "addNamelocator");
	}
	@Override
	public void addAttributeInfo(String name, String value) {
		if(name==null||value==null)return;
		ArrayList<String>fileContent=null;
		MyFileOperation fileOperation=null;
		if(variable.getParentContainer() instanceof PageInfo){
			fileOperation=((PageInfo)variable.getParentContainer()).getFileOperationRobot();
		}else{
			fileOperation=((ContainerInfo)variable.getParentContainer()).getFileOperationRobot();
		}
		fileContent=fileOperation.getFileContent();
		int lineNum=getVariableDefineLineNum(fileContent);
		boolean hasThisAttribute=false;
		for(int i=lineNum;i<fileContent.size();i++){
			if(fileContent.get(i).trim().equalsIgnoreCase("}")){
				if(!hasThisAttribute){
					fileContent.add(i, variable.getName()+".addAttributeLocator(\""+name+"\","+"\""+value+"\");");
					break;
				}
			}
			if(new MyString(fileContent.get(i)).includeAnd("addAttributeLocator","\""+name+"\"")){
				hasThisAttribute=true;
				fileContent.set(i, variable.getName()+".addAttributeLocator(\""+name+"\","+"\""+value+"\");");
				break;
			}
		}
		fileOperation.setFileContent(fileContent);
	}
	
	private void removeLocatorInfo(String locatorSign){
		if(locatorSign==null)return;
		ArrayList<String>fileContent=null;
		MyFileOperation fileOperation=null;
		if(variable.getParentContainer() instanceof PageInfo){
			fileOperation=((PageInfo)variable.getParentContainer()).getFileOperationRobot();
		}else{
			fileOperation=((ContainerInfo)variable.getParentContainer()).getFileOperationRobot();
		}fileContent=fileOperation.getFileContent();
		int lineNum=getVariableDefineLineNum(fileContent);
		for(int i=lineNum;i<fileContent.size();i++){
			if(fileContent.get(i).trim().equalsIgnoreCase("}")){
				return;
			}
			if(fileContent.get(i).contains(locatorSign)){
				fileContent.remove(i);
				break;
			}
		}
		fileOperation.setFileContent(fileContent);
	}
	@Override
	public void removeXpathInfo(String xpath) {
		removeLocatorInfo("addXpathLocator");
	}

	@Override
	public void removeIDInfo(String id) {
		removeLocatorInfo("addIDLocator");
	}

	@Override
	public void removeNameInfo(String name) {
		removeLocatorInfo("addNameLocator");
	}
	@Override
	public void removeCSSInfo(String css) {
		removeLocatorInfo("addCSSLocator");
	}

	@Override
	public void removeTextInfo(String text) {
		removeLocatorInfo("addLinkTextLocator");
	}

	@Override
	public void removeAttributeInfo(String name) {
		if(name==null)return;
		ArrayList<String>fileContent=null;
		MyFileOperation fileOperation=null;
		if(variable.getParentContainer() instanceof PageInfo){
			fileOperation=((PageInfo)variable.getParentContainer()).getFileOperationRobot();
		}else{
			fileOperation=((ContainerInfo)variable.getParentContainer()).getFileOperationRobot();
		}fileContent=fileOperation.getFileContent();
		int lineNum=getVariableDefineLineNum(fileContent);
		for(int i=lineNum;i<fileContent.size();i++){
			if(fileContent.get(i).trim().equalsIgnoreCase("}")){
				return;
			}
			if(new MyString(fileContent.get(i)).includeAnd("addAttributeLocator","\""+name+"\"")){
				fileContent.remove(i);
				break;
			}
		}
		fileOperation.setFileContent(fileContent);
	}
	/**更改,没有的话不动*/
	private void changeLocator(String value,String sign){
		if(value==null)return;
		ArrayList<String>fileContent=null;
		MyFileOperation fileOperation=null;
		if(variable.getParentContainer() instanceof PageInfo){
			fileOperation=((PageInfo)variable.getParentContainer()).getFileOperationRobot();
		}else{
			fileOperation=((ContainerInfo)variable.getParentContainer()).getFileOperationRobot();
		}fileContent=fileOperation.getFileContent();
		int lineNum=getVariableDefineLineNum(fileContent);
		for(int i=lineNum;i<fileContent.size();i++){
			if(fileContent.get(i).trim().equalsIgnoreCase("}")){
				break;
			}
			if(fileContent.get(i).contains(sign)){
				String oldXpath=new MyString(fileContent.get(i)).pickUpStr("\"", "\"");
				if(value.equalsIgnoreCase(oldXpath))
				fileContent.set(i, fileContent.get(i).replace(oldXpath, value));
				break;
			}
		}
		fileOperation.setFileContent(fileContent);
	}
	@Override
	public void changeXpathInfo(String xpath) {
		changeLocator(xpath, "addXpathLocator");
	}

	@Override
	public void changeCSSInfo(String css) {
		changeLocator(css, "addCSSLocator");
	}

	@Override
	public void changeTextInfo(String text) {
		changeLocator(text, "addLinkTextLocator");
	}

	@Override
	public void changeAttributeInfo(String name, String value) {
		if(name==null||value==null)return;
		ArrayList<String>fileContent=null;
		MyFileOperation fileOperation=null;
		if(variable.getParentContainer() instanceof PageInfo){
			fileOperation=((PageInfo)variable.getParentContainer()).getFileOperationRobot();
		}else{
			fileOperation=((ContainerInfo)variable.getParentContainer()).getFileOperationRobot();
		}
		fileContent=fileOperation.getFileContent();
		int lineNum=getVariableDefineLineNum(fileContent);
		for(int i=lineNum;i<fileContent.size();i++){
			if(fileContent.get(i).trim().equalsIgnoreCase("}")){
				break;
			}
			if(new MyString(fileContent.get(i)).includeAnd("addAttributeLocator","\""+name+"\"")){
				fileContent.set(i, variable.getName()+".addAttributeLocator(\""+name+"\","+"\""+value+"\");");
				break;
			}
		}
		fileOperation.setFileContent(fileContent);
	}


	@Override
	public void changeIDInfo(String id) {
		changeLocator(id, "addIDLocator");
	}

	@Override
	public void changeNameInfo(String name) {
		changeLocator(name, "addNameLocator");
	}


}
