package Holmos.Holmos.plug.PSM;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Holmos.Holmos.innerTools.MyFileOperation;
import Holmos.Holmos.innerTools.MyString;
import Holmos.Holmos.plug.LIJSM.LocatorInfo;
import Holmos.Holmos.plug.PSM.folder.FolderInfo;

/**Page Store Manager 的工具类 
 * @author 吴银龙(15857164387)
 * */
public class PSMTool {
	private static String pageStoreBasePath="D:\\workspaces\\personal\\Holmos\\src\\test\\java\\Holmos\\Holmos\\pagestore";

	/**如果absolutePath指示的是Page,SubPage,Collection,Frame类型的文件,那么就获得这个<br>
	 * 指示的文件中名字为name的变量信息,返回与之对应的类型; 如果absolutePath指示的是目录,<br>
	 * 并且是Holmos 框架page store用到的目录,那么返回此目录的信息,类型为FolderInfo; <br>
	 * 如果absolutePath指示的不是Holmos 框架page store 用到的目录,返回null <br>
	 * 如果absolutePath指示的不是Page,SubPage,Collection,Frame类型的文件,返回null <br>
	 * 如果absolutePath指示的文件中没有名字为name的变量信息,返回null 对于Page类型,name传null即可*/
	public static Object getObject(String absolutePath,String name){
		return null;
	}
	public final static Map<String, INFOTYPE>typeSearchList=new HashMap<String, INFOTYPE>();
	public final static Map<INFOTYPE,String>typeSearchListReverse=new HashMap<INFOTYPE, String>();
	static{
		typeSearchList.put("SubPage", INFOTYPE.SUBPAGE);
		typeSearchList.put("Collection", INFOTYPE.COLLECTION);
		typeSearchList.put("Frame", INFOTYPE.FRAME);
		typeSearchList.put("Button", INFOTYPE.ELEMENT);
		typeSearchList.put("TextField", INFOTYPE.ELEMENT);
		typeSearchList.put("CheckBox", INFOTYPE.ELEMENT);
		typeSearchList.put("Comobobox", INFOTYPE.ELEMENT);
		typeSearchList.put("Element", INFOTYPE.ELEMENT);
		typeSearchList.put("Label", INFOTYPE.ELEMENT);
		typeSearchList.put("Link", INFOTYPE.ELEMENT);
		typeSearchList.put("RadioButton", INFOTYPE.ELEMENT);
		typeSearchList.put("RichTextField", INFOTYPE.ELEMENT);
		typeSearchList.put("Table", INFOTYPE.ELEMENT);
	}
	static{
		typeSearchListReverse.put(INFOTYPE.BUTTON, "Button");
		typeSearchListReverse.put(INFOTYPE.CHECKBOX, "CheckBox");
		typeSearchListReverse.put(INFOTYPE.COLLECTION, "Collection");
		typeSearchListReverse.put(INFOTYPE.COMOBOBOX, "Combobox");
		typeSearchListReverse.put(INFOTYPE.ELEMENT, "Element");
		typeSearchListReverse.put(INFOTYPE.FRAME, "Frame");
		typeSearchListReverse.put(INFOTYPE.LABEL, "Lable");
		typeSearchListReverse.put(INFOTYPE.LINK, "Link");
		typeSearchListReverse.put(INFOTYPE.RADIOBUTTON, "RadioButton");
		typeSearchListReverse.put(INFOTYPE.RICHTEXTFIELD, "RichTextField");
		typeSearchListReverse.put(INFOTYPE.SUBPAGE, "SubPage");
		typeSearchListReverse.put(INFOTYPE.TABLE, "Table");
		typeSearchListReverse.put(INFOTYPE.TEXTFIELD, "TextField");
	}
	/**
	 * 根据变量的类型type获得此变量对应的类名<br>
	 * @param type 变量的类型
	 * @return 此类型对应的类名
	 * */
	public static String getInfoTypeClassName(INFOTYPE type){
		return typeSearchListReverse.get(type);
	}
	public static INFOTYPE getTypeOfString(String type){
		return typeSearchList.get(type);
	}
	/**将name转化为Clasname，就是确保第一个字母大写*/
	public static String toClassName(String name){
		StringBuilder nameTemp=new StringBuilder(name);
		nameTemp.setCharAt(0, Character.toUpperCase(name.charAt(0)));
		return nameTemp.toString();
	}
	/**将name转化为variablename,就是确保第一个字母小写*/
	public static String toVariableName(String name){
		StringBuilder nameTemp=new StringBuilder(name);
		nameTemp.setCharAt(0, Character.toLowerCase(name.charAt(0)));
		return nameTemp.toString();
	}
	/**获得这个filePath指代的文件文件类型,判断依据,看这个类继承的是哪个
	 * <li>没有这个文件存在,那么就是Elment类型</li>
	 * <li>如果继承了SubPage,那么就是SubPage类型</li>
	 * <li>如果继承了Collection,那么就是Collection类型</li>
	 * <li>如果继承了Frame,那么就是Frame类型</li>
	 * 否则的话,这个文件只是一般的Java文件,不是任何类型
	 * @param filePath java文件的文件路径
	 * @return Java文件所属的类型
	 * */
	public static INFOTYPE getType(String filePath){
		MyFileOperation file=new MyFileOperation(filePath);
		if(!file.isExist()){
			return INFOTYPE.ELEMENT;
		}
		ArrayList<String>fileContent=file.getFileContent();
		for(String line:fileContent){
			if(new MyString(line).includeAnd(" extends ","{")){
				if(line.contains(" SubPage")){
					return INFOTYPE.SUBPAGE;
				}else if(line.contains(" Collection")){
					return INFOTYPE.COLLECTION;
				}else if(line.contains(" Frame")){
					return INFOTYPE.FRAME;
				}
				break;
			}
		}
		return INFOTYPE.UNKNOWN;
	}
	/**获得一个容器文件里面的变量定义的列表，获得的是定义的内容列表*/
	public static ArrayList<ArrayList<String>> getElementContent(ArrayList<String>fileContent){
		ArrayList<ArrayList<String>>results=new ArrayList<ArrayList<String>>();
		for(int i=0;i<fileContent.size();i++){
			MyString lineInfo=new MyString(fileContent.get(i));
			if(lineInfo.includeAnd("/** ","类型")){
				ArrayList<String>elementContent=new ArrayList<String>();
				while(i<fileContent.size()){
					elementContent.add(fileContent.get(i));
					i++;
					lineInfo=new MyString(fileContent.get(i));
					if(lineInfo.value.trim().equalsIgnoreCase("}")){
						results.add(elementContent);
						break;
					}
				}
			}
		}
		return results;
	}
	public static boolean isFolderNameValid(String name){
		if(name==null||name=="")
			return false;
		if(name.charAt(0)>='0'&&name.charAt(0)<='9')
			return false;
		for(char c:name.toCharArray()){
			for(int i=0;i<PSMConstValue.ILLEGALCHARS.length;i++){
				if(c==PSMConstValue.ILLEGALCHARS[i])
					return false;
			}
		}
		String nameTemp=name.toLowerCase();
		for(String value:PSMConstValue.OBIGATECHAR){
			if(value.equals(nameTemp)){
				return false;
			}
		}
		for(String value:PSMConstValue.JAVAKEYWORDS){
			if(value.equals(nameTemp)){
				return false;
			}
		}
		return true;
	}
	/**判断名字是否合法*/
	public static int checkName(String name){
		if(name==null){//如果为空,或者是空字符窜，那么名字不合法
			return PSMConstValue.NULL;
		}
		String nameTemp=name.toLowerCase();
		if(nameTemp.equalsIgnoreCase("")){
			return PSMConstValue.BLANK;
		}else if(nameTemp.length()<=3){
			return PSMConstValue.TOOSHORT;
		}else if(nameTemp.length()>=30){
			return PSMConstValue.TOOLONG;
		}else if(true){
			for(String value:PSMConstValue.OBIGATECHAR){
				if(value.equals(nameTemp)){
					return PSMConstValue.INCLUDEOBIGATEKEYWORD;
				}
			}
			for(String value:PSMConstValue.JAVAKEYWORDS){
				if(value.equals(nameTemp)){
					return PSMConstValue.INCLUDEJAVAKEYWORD;
				}
			}
			char firstCharacter=name.charAt(0);
			if(!(firstCharacter>='a'&&firstCharacter<='z'||firstCharacter>='A'&&firstCharacter<='Z')){
				//如果第一个字母不是_或者字母，则不合法
				return PSMConstValue.ILLEGALCHAR;
			}
			for(int i=1;i<name.length();i++){
				firstCharacter=name.charAt(i);
				if(!(firstCharacter=='_'||firstCharacter>='a'&&firstCharacter<='z'||firstCharacter>='A'
						&&firstCharacter<='Z'||firstCharacter>='0'&&firstCharacter<='9')){
					//如果变量名字中含有字母，数字，_之外的任何一个字符，则不合法
					return PSMConstValue.ILLEGALCHAR;
				}
			}
		}
		return PSMConstValue.CORRECT;
	}
	/**检查变量的名字，按照java变量名的要求进行要求，即：大小写字母，数字，下划线，第一个字符不能使数字*/
	public static boolean isValid(String name){
		if(checkName(name)==PSMConstValue.CORRECT)
			return true;
		return false;
	}
	/**在读取文件的时候需要根据文件中的一段信息建立的变量
	 * 需要在这里读取文件中的locator定位信息，如果没有就不进行读取*/
	public static VariableInfo getInfoFromContent(ArrayList<String>variableContent){
		INFOTYPE type = null;
		String comment = null;
		String name = null;
		for(String line:variableContent){
			MyString lineInfo=new MyString(line);
			if(lineInfo.includeAnd("*","类型:")){
				type=PSMTool.getTypeOfString(lineInfo.pickUpStr("类型:", "说明:").trim());
				comment=lineInfo.pickUpStr("说明:", "*/").trim();
			}else if(lineInfo.includeAnd("public ","new ")){
				name=lineInfo.getSplitPart(2, " ");
				if(name.endsWith("=")){
					name=name.substring(0, name.length()-1);
				}
			}
		}
		VariableInfo variable=VariableInfo.createVariableInfo(name, comment, type);
		LocatorInfo locatorInfo=variable.getJavalocatorManager().getLocatorInfoByContent(variableContent);
		variable.setLocatorInfo(locatorInfo);
		return variable;
	}
	/**获得PageStore*/
	public static FolderInfo getPageStore(){
		FolderInfo folder=new FolderInfo("pagestore");
		folder.setParentFolder(null);
		folder.setDirectoryPath(pageStoreBasePath.substring(0, pageStoreBasePath.lastIndexOf("\\")));
		folder.setAbsolutePath(pageStoreBasePath);
		return folder;
	}
	/**添加新的importPath*/
	public static void addImportPath(ArrayList<String>fileContent,String importPath){
		importPath="import "+importPath+";";
		for(int i=0;i<fileContent.size();i++){
			if(fileContent.get(i).trim().startsWith("import ")){
				fileContent.add(i, importPath);
				break;
			}
		}
	}
	/**删除importPath*/
	public static void removeImportPath(ArrayList<String>fileContent,String importName){
		for(int i=0;i<fileContent.size();i++){
			if(fileContent.get(i).contains(PSMTool.toVariableName(importName)+"."+PSMTool.toClassName(importName))){
				fileContent.remove(i);
				break;
			}
			if(fileContent.get(i).contains("public ")){
				break;
			}
		}
	}
	/**修改importPath和package信息*/
	public static void changePackageAndImportPath(ArrayList<String>fileContent,String oldPackagePath,String newPackagePath){
		for(int i=0;i<fileContent.size();i++){
			if(fileContent.get(i).contains("package ")||fileContent.get(i).contains("import ")){
				if(fileContent.get(i).contains(oldPackagePath)){
					fileContent.set(i, fileContent.get(i).replace(oldPackagePath, newPackagePath));
				}
			}
			if(fileContent.get(i).contains(" class ")){
				break;
			}
		}
	}
	/**修改本文件中的packagePath和importPath信息*/
	public static void changePackageAndImportPath(ArrayList<String>fileContent,String newName){
		String oldPackagePath = null;
		String newPackagePath = null;
		for(int i=0;i<fileContent.size();i++){
			if(fileContent.get(i).trim().startsWith("package ")){
				oldPackagePath=new MyString(fileContent.get(i).trim()).pickUpStr("package ", ";");
				newPackagePath=oldPackagePath.substring(0, oldPackagePath.lastIndexOf('.')+1)+PSMTool.toVariableName(newName);
				break;
			}
		}
		for(int i=0;i<fileContent.size();i++ ){
			if(fileContent.get(i).contains("package ")||fileContent.get(i).contains("import ")){
				if(fileContent.get(i).contains(oldPackagePath)){
					fileContent.set(i, fileContent.get(i).replace(oldPackagePath, newPackagePath));
				}
			}
			if(fileContent.get(i).contains(" class ")){
				break;
			}
		}
	}
}
