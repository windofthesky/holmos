package Holmos.Holmos.plug.PSM.container;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.autosense.browser.exchange.util.CreateReturnInfo;
import cn.autosense.browser.exchange.util.ErrorType;

import Holmos.Holmos.constvalue.ConstValue;
import Holmos.Holmos.innerTools.MyDirectoryOperation;
import Holmos.Holmos.innerTools.MyFile;
import Holmos.Holmos.innerTools.MyFileOperation;
import Holmos.Holmos.innerTools.MyString;
import Holmos.Holmos.plug.PSM.INFOTYPE;
import Holmos.Holmos.plug.PSM.PSMTool;
import Holmos.Holmos.plug.PSM.VariableInfo;
import Holmos.Holmos.plug.PSM.element.ElementInfo;

public class ContainerInfo extends VariableInfo{

	public ContainerInfo(String name, String comment, INFOTYPE type) {
		super(name, comment, type);
	}
	protected String importPath;
	/**获取此容器文件的impot引用路径*/
	public String getImportPath() {
		return "import "+importPath+";";
	}
	protected String absolutePath;
	protected String directoryPath;
	/**此容器对象的Java文件对应的包路径*/
	protected String packagePath;
	public String getPackagePath() {
		return "package "+packagePath+";";
	}
	/**文件操作机器人*/
	protected MyFileOperation fileOperationRobot;
	public MyFileOperation getFileOperationRobot() {
		return fileOperationRobot;
	}
	protected List<SubPageInfo>subpages=new ArrayList<SubPageInfo>();
	protected List<CollectionInfo>collections=new ArrayList<CollectionInfo>();
	protected List<ElementInfo>elements=new ArrayList<ElementInfo>();
	
	/**如果此PageInfo里面没有变量则返回true*/
	public boolean isEmpty(){
		if(subpages.isEmpty()&&collections.isEmpty()&&elements.isEmpty()){
			if(this instanceof FrameInfo)
				if(((FrameInfo)this).frames.isEmpty())
					return true;
				else 
					return false;
			else 
				return true;
		}
		return false;
	}
	/**获得此containerInfo对象的绝对路径*/
	public String getAbsolutePath() {
		return absolutePath;
	}
	/**获得此containerInfo对象的所在目录路径 */
	public String getDirectoryPath() {
		return directoryPath;
	}
	/**获得此containerInfo对象的所有的SubPageInfo实例 */
	public List<SubPageInfo> getSubpages() {
		return subpages;
	}
	/**获得此containerInfo对象的所有的collectionInfo实例 */
	public List<CollectionInfo> getCollections() {
		return collections;
	}
	/**获得此containerInfo对象的所有的ElementInfo实例 */
	public List<ElementInfo> getElements() {
		return elements;
	}
	/**获得信息的描述,就是变量信息的List,信息格式为:<br>
	 * javadoc注释符号:类型:xxxxxx  说明:xxxxxxx<br>
 	 * type variable=new type(comment);<br>
     * @return 返回的变量信息*/
	@Override
	public ArrayList<String>getInfoDescription(){
		ArrayList<String>infoDescription=new ArrayList<String>();
		infoDescription.add(toString());
		infoDescription.add("public "+PSMTool.toClassName(name)+" "+name+"= new "+PSMTool.toClassName(name)+" (\"" +
				comment+"\");");
		return infoDescription;
	}
	/**设置父容器信息，更新本容器的absolutePath和directoryPath
	 * @param parentContainer 要设置的父容器信息
	 * */
	public void setParentContainer(Object parentContainer){
		this.parentContainer=parentContainer;
		if(parentContainer instanceof PageInfo){
			this.directoryPath=((PageInfo)parentContainer).getDirectoryPath()+"\\"+getName();
		}else if(parentContainer instanceof ContainerInfo){
			this.directoryPath=((ContainerInfo)parentContainer).getDirectoryPath()+"\\"+getName();
		}
		this.absolutePath=this.directoryPath+"\\"+PSMTool.toClassName(name)+".java";
		this.fileOperationRobot=new MyFileOperation(absolutePath);
		this.packagePath=this.directoryPath.substring(ConstValue.HOLMOSPAGESTOREBASEDIR.length()+1).replace("\\", ".");
		this.importPath=this.packagePath+"."+PSMTool.toClassName(name);
	}
	/**
	 * 在此ContainerInfo里面添加一个变量,名字为subPageName,然后并且在框架默认的位置新建一个名字为subPageName的subpage.java文件,里面没有包括任何元素: 
	 * <li>检查subpageName是否合法,创建一个新的subpage.java文件,在框架指定的位置--留作外层检查</li>
	 * <li>检查是否已经存在</li>
	 * <li>更新当前ContainerInfo的Container.java文件里面的内容</li>
	 * <li>更新当前ContainerInfo对象里面的subpages信息</li>
	 * @param 添加的subpage变量
	 * @return
	 * */
	public CreateReturnInfo addSubPageInfo(SubPageInfo subpage){
		if(isSubPageInfoExist(subpage.name)){
			// --TODO 给出已经存在提示 @Mash
			System.out.println("此SubPage已经存在,请重新命名!");
			return new CreateReturnInfo(ErrorType.HAS_EXIT);
		}
		subpage.setParentContainer(this);
		//新建文件
		subpage.setParentContainer(this);
		ArrayList<String>containerFileContent=new ArrayList<String>();
		containerFileContent.add(subpage.getPackagePath());
		containerFileContent.add("");
		containerFileContent.addAll(createNewContainerFile(subpage.name, INFOTYPE.SUBPAGE));
		subpage.fileOperationRobot.setFileContent(containerFileContent);
		//更新本容器内容
		this.createNewVariable(subpage);
		//修改subpages信息
		this.subpages.add(subpage);
		return new CreateReturnInfo(subpage);
	}
	
	/**获得一个新的container Java文件的内容*/
	protected ArrayList<String>createNewContainerFile(String name,INFOTYPE type){
		ArrayList<String>containerFileContent=new ArrayList<String>();
		containerFileContent.add(ConstValue.STRUCTPACKAGEINFO);
		containerFileContent.add(ConstValue.ELEMENTPACKAGEINFO);
		containerFileContent.add("");
		containerFileContent.add("public class "+PSMTool.toClassName(name)+" extends "+PSMTool.getInfoTypeClassName(type)+"{");
		containerFileContent.add("");
		containerFileContent.add("public "+PSMTool.toClassName(name)+"(String comment){");
		containerFileContent.add("super(comment);");
		containerFileContent.add("}");
		containerFileContent.add("");
		containerFileContent.add("}");
		return containerFileContent;
	}

	/**在此Container.java文件内部新建名字为name的变量*/
	protected void createNewVariable(VariableInfo variable){
		ArrayList<String>containerFileContent=this.fileOperationRobot.getFileContent();
		if(variable instanceof ContainerInfo){
			PSMTool.addImportPath(containerFileContent, ((ContainerInfo)variable).getImportPath());
		}
		int index=containerFileContent.size()-1;
		while(index>0&&!containerFileContent.get(index).equalsIgnoreCase("}")){
			index--;
		}
		containerFileContent.addAll(index, variable.getInfoDescription());
		index+=variable.getInfoDescription().size();
		containerFileContent.addAll(index,variable.getJavalocatorManager().createLocatorInfo());
		this.fileOperationRobot.setFileContent(containerFileContent);
	}
	/**在此ContainerInfo里面添加一个变量,名字为collectionName,然后并且在框架默认的位置新建一个名字为subPageName的Collection.java文件,里面没有包括任何元素: 
	 * <li>检查collectionName是否合法,创建一个新的Collection.java文件,在框架指定的位置</li>
	 * <li>检查是否已经存在</li>
	 * <li>更新当前ContainerInfo的Page.java文件里面的内容</li>
	 * <li>更新当前ContainerInfo对象里面的colletions信息</li>
	 * @param collection 添加的collection变量
	 * @return
	 * */
	public CreateReturnInfo addCollectionInfo(CollectionInfo collection){
		if(isColleticonInfoExist(collection.name)){
			// --TODO 给出已经存在提示 @Mash
			System.out.println("此Collection已经存在,请重新命名!");
			return new CreateReturnInfo(ErrorType.HAS_EXIT);
		}collection.setParentContainer(this);
		ArrayList<String>containerFileContent=new ArrayList<String>();
		containerFileContent.add(collection.getPackagePath());
		containerFileContent.add("");
		containerFileContent.addAll(createNewContainerFile(collection.name, INFOTYPE.COLLECTION));
		//更新本容器内容
		createNewVariable(collection);
		//修改collections信息
		this.collections.add(collection);
		return new CreateReturnInfo(collection);
	}
	/**
	 * 在此ContainerInfo里面添加一个元素实例,名字为elementName:
	 * <li>检查type是否是元素类型</li> 
	 * <li>检查是否已经存在</li> 
	 * <li>更新当前ContainerInfo的Container.java文件里面的内容</li> 
	 * <li>更新当前ContainerInfo对象里面的elements信息</li> 
	 * @param element 添加的元素
	 * @return
	 * */
	public CreateReturnInfo addElementInfo(ElementInfo element){
		if(isElementInfoExist(element.getName())){
			// --TODO 给出已经存在提示 @Mash
			System.out.println("此元素Element已经存在，请重新命名!");
			return new CreateReturnInfo(ErrorType.HAS_EXIT);
		}
		element.setParentContainer(this);
		createNewVariable(element);
		this.elements.add(element);
		return new CreateReturnInfo(element);
	}
	/**在此ContainerInfo里面添加一个容器实例,名字为containerName
	 * <li>检查type是否是容器类型</li>
	 * <li>检查是否已经存在</li>
	 * <li>在框架规定位置新建Container.java文件</li>
	 * <li>更新当前ContainerInfo的Page.java文件里面的内容</li>
	 * <li>更新当前congtaienrs的信息</li>
	 * @param container 添加的容器变量
	 * @return
	 * */
	public CreateReturnInfo addContainerInfo(ContainerInfo container){
		switch (container.type) {
		case SUBPAGE:
			return addSubPageInfo((SubPageInfo) container);
		case COLLECTION:
			return addCollectionInfo((CollectionInfo) container);
		default:
			return new CreateReturnInfo(ErrorType.UNKNOWN);
		}
	}
	/**根据type确定要添加变量的类型,然后来选择已经有的add操作 */
	public CreateReturnInfo addVariableInfo(VariableInfo variable){
		switch (variable.getType()) {
		case SUBPAGE:
			return addSubPageInfo((SubPageInfo) variable);
		case COLLECTION:
			return addCollectionInfo((CollectionInfo) variable);
		default:
			return addElementInfo((ElementInfo) variable);
		}
	}
	/**检查该ContainerInfo对象里面是否具有名字为subPageName的subpage
	 * <li>检查subpageName是否合法,并将subpageName的第一个字母大写</li>
	 * <li>扫描subpages</li>
	 * @return true 存在 <br> false不存在
	 * */
	public boolean isSubPageInfoExist(String subPageName){
		for(SubPageInfo subpage:this.subpages){
			if(subpage.getName().equalsIgnoreCase(subPageName)){
				return true;
			}
		}
		return false;
	}
	/**检查该ContainerInfo对象里面是否具有名字为collectionName的collection 
	 * <li>检查collectionName是否合法,并将collectionName的第一个字母大写</li>
	 * <li>扫描collections</li>
	 * @return true 存在 <br> false不存在
	 * */
	public boolean isColleticonInfoExist(String collectionName){
		for(CollectionInfo collection:this.collections){
			if(collection.getName().equalsIgnoreCase(collectionName)){
				return true;
			}
		}
		return false;
	}
	/**检查该ContainerInfo对象里面是否具有名字为containerName的container 
	 * <li>检查containerName是否合法,并将containerName的第一个字母大写</li>
	 * <li>扫描containers</li>
	 * @return true 存在 <br> false不存在
	 * */
	public boolean isContainerInfoExist(String containerName){
		if(isSubPageInfoExist(containerName))
			return true;
		if(isColleticonInfoExist(containerName))
			return true;
		return false;
	}
	/**检查该ContainerInfo对象里面是否具有名字为variableName的variable
	 * <li>检查elementName是否合法</li>
	 * <li>扫描elements</li>
	 * @return true 存在 <br> false不存在
	 * */
	public boolean isElementInfoExist(String elementName){
		for(ElementInfo element:elements){
			if(element.getName().equalsIgnoreCase(elementName))
				return true;
		}
		return false;
	}
	/**检查该ContainerInfo对象里面是否具有名字为variableName的variable 
	 * <li>检查variableName是否合法,但不对variableName的第一个字母大写处理</li>
	 * <li>扫描所有的containers和elements</li>
	 * @return true 存在 <br> false不存在*/
	public boolean isVariableExist(String variableName){
		if(isContainerInfoExist(variableName))
			return true;
		if(isElementInfoExist(variableName))
			return true;
		return false;
	}
	/**移除此ContainerInfo实例下的名字为subPageName的subPageInfo实例
	 * <li>检查名字是否合法</li>
	 * <li>扫描subpages,找到匹配项了之后删除subPage.java文件，一并删除它的子信息</li>
	 * <li>更新此ContainerInfo的subpages信息</li>
	 * @param subpageName 待移除的subPageInfo的名字
	 * */
	public void removeSubPage(String subPageName){
		if(!isSubPageInfoExist(subPageName)){
			return;
		}
		//删文件
		for(SubPageInfo subpage:subpages){
			if(subpage.getName().equalsIgnoreCase(subPageName)){
				subpage.getJavalocatorManager().removeLocatorInfo();
				MyFile.deleteDirectory(subpage.directoryPath);
				this.subpages.remove(subpage);
				break;
			}
		}
		//删除本文件信息
		deleteInfo(subPageName);
	}
	/**删除本文件与name对应的变量信息*/
	protected void deleteInfo(String name){
		ArrayList<String>pageFileContent=this.fileOperationRobot.getFileContent();
		for(int i=0;i<pageFileContent.size();i++){
			MyString lineInfo=new MyString(pageFileContent.get(i));
			if(lineInfo.includeAnd("new "," "+name)){
				while(!pageFileContent.get(i).trim().startsWith("/** ")){
					pageFileContent.remove(i);i--;
				}pageFileContent.remove(i);
				break;
			}
		}
		PSMTool.removeImportPath(pageFileContent, name);
		fileOperationRobot.setFileContent(pageFileContent);
	}
	/**移除此ContainerInfo实例下的名字为subPageName的collectionInfo实例
	 * <li>检查名字是否合法</li>
	 * <li>扫描collections,找到匹配项了之后删除Collection.java文件，一并删除它的子信息</li>
	 * <li>更新此ContainerInfo的collections信息</li>
	 * @param collectionName 待移除的collectionInfo的名字
	 * */
	public void removeCollection(String collectionName){
		if(!isColleticonInfoExist(collectionName)){
			return ;
		}
		//删文件
		for(CollectionInfo collection:collections){
			if(collection.getName().equalsIgnoreCase(collectionName)){
				collection.getJavalocatorManager().removeLocatorInfo();
				MyFile.deleteDirectory(collection.directoryPath);
				this.collections.remove(collection);
				break;
			}
		}
		//删除本文件信息
		deleteInfo(collectionName);
	}
	/**移除此ContainerInfo实例下的名字为containerName的ContainerInfo实例
	 * <li>检查名字是否合法</li>
	 * <li>扫描subpages,找到匹配项了之后删除Container.java文件，一并删除它的子信息</li>
	 * <li>更新此ContainerInfo的containers以及与之匹配的container信息</li>
	 * @param collectionName 待移除的containerInfo的名字
	 * */
	public void removeContainer(String containerName){
		removeSubPage(containerName);
		removeCollection(containerName);
		if(this instanceof FrameInfo){
			((FrameInfo)this).removeFrame(containerName);
		}
	}
	/**移除此ContainerInfo实例下的名字为elementName的ElementInfo实例
	 * <li>检查名字是否合法</li>
	 * <li>扫描elements，删除匹配项</li>
	 * <li>更新此ContainerInfo的elements信息</li>
	 * @param elementName 待移除的elementInfo的名字
	 * */
	public void removeElement(String elementName){
		for(ElementInfo element:this.elements){
			if(element.getName().equalsIgnoreCase(elementName)){
				element.getJavalocatorManager().removeLocatorInfo();
				this.elements.remove(element);
				break;
			}
		}
		//删除本文件信息
		deleteInfo(elementName);
	}
	/**根据variableName来判断是哪种变量，然后根据这个来调用定义好的remove操作来完成*/
	public void removeVariable(String variableName){
		removeContainer(variableName);
		removeElement(variableName);
	}
	/**获取此ContainerInfo下与subPageName匹配的subPageInfo实例
	 * <li>检查名字是否合法，并将其第一个字母大写</li>
	 * <li>判断是否存在,扫描</li>
	 * @param subPageName 要获取的subPageInfo实例的名字
	 * @return 获取到的实例，如果没有匹配项，返回null
	 * */
	public SubPageInfo getSubPage(String subPageName){
		for(SubPageInfo subpage:subpages){
			if(subpage.getName().equalsIgnoreCase(subPageName)){
				return subpage;
			}
		}
		return null;
	}
	/**获取此ContainerInfo下与CollectionName匹配的CollectionInfo实例
	 * <li>检查名字是否合法，并将其第一个字母大写</li>
	 * <li>判断是否存在,扫描</li>
	 * @param subPageName 要获取的subPageInfo实例的名字
	 * @return 获取到的实例，如果没有匹配项，返回null
	 * */
	public CollectionInfo getCollection(String collectionName){
		for(CollectionInfo collection:collections){
			if(collection.getName().equalsIgnoreCase(collectionName)){
				return collection;
			}
		}
		return null;
	}
	/**获取此ContainerInfo下与containerName匹配的containerInfo实例
	 * <li>检查名字是否合法，并将其第一个字母大写</li>
	 * <li>判断是否存在,扫描</li>
	 * @param subPageName 要获取的subPageInfo实例的名字
	 * @return 获取到的实例，如果没有匹配项，返回null
	 * */
	public ContainerInfo getContainer(String containerName){
		ContainerInfo container=getSubPage(containerName);
		if(container!=null)return container;
		return getCollection(containerName);
	}
	/**获取此ContainerInfo下与elementName匹配的elementInfo实例
	 * <li>检查名字是否合法，不做大写处理</li>
	 * <li>判断是否存在,扫描</li>
	 * @param subPageName 要获取的subPageInfo实例的名字
	 * @return 获取到的实例，如果没有匹配项，返回null
	 * */
	public ElementInfo getElement(String elementName){
		for(ElementInfo element:elements){
			if(element.getName().equalsIgnoreCase(elementName)){
				return element;
			}
		}
		return null;
	}
	/**获取此ContainerInfo下与subPageName匹配的variableInfo实例
	 * <li>检查名字是否合法，不做大写处理</li>
	 * <li>判断是否存在,扫描</li>
	 * @param subPageName 要获取的subPageInfo实例的名字
	 * @return 获取到的实例，如果没有匹配项，返回null
	 * */
	public VariableInfo getVariable(String variableName){
		VariableInfo variable=getContainer(variableName);
		if(variable!=null){
			return variable;
		}return getElement(variableName);
	}
	/**将subpage对象拷贝到当前ContainerInfo下:
	 * <li>实现硬盘信息拷贝,并将subpage实例里面所有的已有的信息全部拷贝</li>
	 * <li>递归修改此目录下所有的实例对象的包信息,absolutePath,directoryPath信息</li>
	 * <li>修改subpage对象的信息absolutePath,directoryPath等信息</li>
	 * <li>修改当前对象的信息</li>
	 * @param subPage 要拷贝的subpage对象
	 * */
	public void pasteSubPageInfo(SubPageInfo subPage){
		//文件拷贝
		MyDirectoryOperation.copyTo(new File(directoryPath+"\\"+subPage.getName()), new File(subPage.directoryPath));
		SubPageInfo subPageClone=(SubPageInfo) subPage.clone();
		addNewContainerInfo(subPageClone);
		String oldPackagePath=subPage.packagePath;
		String newPackagePath=packagePath+"."+subPage.getName();
		subPageClone.changeInfo(this,oldPackagePath,newPackagePath);
		this.subpages.add(subPageClone);
	}
	/**修改本文件信息，添加了新的容器对象*/
	protected void addNewContainerInfo(ContainerInfo container){
		ArrayList<String>fileContent=fileOperationRobot.getFileContent();
		String importPath=packagePath+"."+container.getName()+"."+PSMTool.toClassName(container.getName());
		PSMTool.addImportPath(fileContent, importPath);
		int index=fileContent.size()-1;
		while(index>0&&!fileContent.get(index).equalsIgnoreCase("}")){
			index--;
		}
		fileContent.addAll(index, container.getInfoDescription());
		index+=container.getInfoDescription().size();
		fileContent.addAll(index, container.getJavalocatorManager().createLocatorInfo());
		this.fileOperationRobot.setFileContent(fileContent);
	}
	/**修改变量信息，用来在copy的时候用到
	 * <li>修改parentContainer</li>
	 * <li>修改absolutePath</li>
	 * <li>修改directoryPath</li>
	 * <li>对于container，修改里面变量的id，并且修改package信息</li>
	 **/
	public void changeInfo(Object parentContainer,String oldPackagePath,String newPackagePath){
		this.setParentContainer(parentContainer);
		this.changePackageAndImprtInfo(oldPackagePath,newPackagePath);
		for(SubPageInfo subpage:subpages){
			subpage.changeInfo(this,oldPackagePath,newPackagePath);
		}for(CollectionInfo collection:collections){
			collection.changeInfo(this,oldPackagePath,newPackagePath);
		}for(ElementInfo element:elements){
			element.setParentContainer(this);
		}
	}
	/**修改包信息*/
	protected void changePackageAndImprtInfo(String oldPackagePath,String newPackagePath){
		ArrayList<String>fileContent=fileOperationRobot.getFileContent();
		PSMTool.changePackageAndImportPath(fileContent, oldPackagePath, newPackagePath);
		fileOperationRobot.setFileContent(fileContent);
	}
	/**将collection对象拷贝到当前ContainerInfo下:
	 * <li>实现硬盘信息拷贝,并将collection实例里面所有的已有的信息全部拷贝</li>
	 * <li>递归修改此目录下所有的实例对象的包信息,absolutePath,directoryPath信息</li>
	 * <li>修改collection对象的信息absolutePath,directoryPath等信息</li>
	 * <li>修改当前对象的信息</li>
	 * @param collection 要拷贝的subpage对象 
	 * */
	public void pasteCollectionInfo(CollectionInfo collection){
		//文件拷贝
		MyDirectoryOperation.copyTo(new File(directoryPath+"\\"+collection.getName()), new File(collection.directoryPath));
		CollectionInfo CollectionClone=(CollectionInfo) collection.clone();
		addNewContainerInfo(CollectionClone);
		String oldPackagePath=collection.packagePath;
		String newPackagePath=packagePath+"."+collection.getName();
		CollectionClone.changeInfo(this,oldPackagePath,newPackagePath);
		this.collections.add(CollectionClone);
	}
	/**将container对象拷贝到当前ContainerInfo下:
	 * <li>实现硬盘信息拷贝,并将container实例里面所有的已有的信息全部拷贝</li>
	 * <li>递归修改此目录下所有的实例对象的包信息,absolutePath,directoryPath信息</li>
	 * <li>修改container对象的信息absolutePath,directoryPath等信息</li>
	 * <li>修改当前对象的信息</li>
	 * @param container 要拷贝的container对象 
	 * */
	public void pasteContainerInfo(ContainerInfo container){
		if(container instanceof SubPageInfo){
			pasteSubPageInfo((SubPageInfo) container);
		}else if(container instanceof CollectionInfo){
			pasteCollectionInfo((CollectionInfo) container);
		}
	}
	/**将element对象拷贝到当前ContainerInfo下:
	 * <li>修改当前对象的信息</li>
	 * @param element 要拷贝的element对象 
	 * */
	public void pasteElementInfo(ElementInfo element){
		addElementInfo(element);
	}
	/**根据已有的接口实现粘贴
	 * */
	public void pasteVariableInfo(VariableInfo variable){
		if(variable instanceof ContainerInfo){
			pasteContainerInfo((ContainerInfo) variable);
		}else{
			pasteElementInfo((ElementInfo) variable);
		}
	}
	/**重命名当前ContainerInfo实例:
	 * <li>检查新的名字是否合法,合法继续,在磁盘里面重命名此Container.java</li>
	 * <li>递归修改此ContainerInfo下所有的java文件的包信息,absolutePath,directoryPath信息</li>
	 * <li>修改此ContainerInfo实例中的信息,absolutePath,name等信息</li>
	 * @param newPageName 要新修改的ContainerInfo实例的名字
	 * */
	public void rename(String newContainerName){
		if(!baseCheckForRename(newContainerName)){
			return;
		}
		String oldName=name;
		String oldPackagePath=this.packagePath;
		//在磁盘中重命名文件
		renameFile(newContainerName);
		//修改该容器下Java文件的包信息，absolutePath，directoryPath信息
		changeDefineInfo(oldName,name);
		//修改此容器文件定义信息
		changeInfo(oldPackagePath,packagePath);
		//修改父容器文件定义信息
		changeParentInfo(oldName,name);
	}
	/**修改此文件定义信息*/
	private void changeDefineInfo(String oldName,String newName) {
		String oldClass=PSMTool.toClassName(oldName);
		String newClass=PSMTool.toClassName(newName);
		ArrayList<String>fileContent=fileOperationRobot.getFileContent();
		for(int i=0;i<fileContent.size();i++){
			if(new MyString(fileContent.get(i)).includeAnd(" extends")){
				String newDefineInfo=fileContent.get(i).replace(oldClass, newClass);
				fileContent.set(i, newDefineInfo);
			}
			if(new MyString(fileContent.get(i)).includeOr("public "+oldClass)){
				String newDefineInfo=fileContent.get(i).replace(oldClass, newClass);
				fileContent.set(i, newDefineInfo);
				fileOperationRobot.setFileContent(fileContent);
				break;
			}
		}
	}
	/**根据此容器现在的信息修改其在父容器的信息*/
	private void changeParentInfo(String oldName,String newName){
		String oldClass=PSMTool.toClassName(oldName);
		String newClass=PSMTool.toClassName(newName);
		ArrayList<String>parentFileInfo;
		MyFileOperation parentFileOperation;
		if(this.parentContainer instanceof PageInfo){
			parentFileOperation=((PageInfo)parentContainer).fileOperationRobot;
		}else{
			parentFileOperation=((ContainerInfo)parentContainer).fileOperationRobot;
		}
		parentFileInfo=parentFileOperation.getFileContent();
		int index=0;
		for(;index<parentFileInfo.size();index++){
			if(parentFileInfo.get(index).contains("import ")){
				if(parentFileInfo.get(index).endsWith(PSMTool.toVariableName(oldName)+"."+
						PSMTool.toClassName(oldName)+";")){
					parentFileInfo.set(index, parentFileInfo.get(index).replace(PSMTool.toVariableName(oldName)+"."+PSMTool.toClassName(oldName), 
							PSMTool.toVariableName(newName)+"."+PSMTool.toClassName(newName)));
				}
			}if(parentFileInfo.get(index).contains(" class ")){
				break;
			}
		}
		for(;index<parentFileInfo.size();index++){
			if(parentFileInfo.get(index).contains(" "+oldClass+" ")){
				String newDefineInfo=parentFileInfo.get(index).replace(oldClass, newClass);
				parentFileInfo.set(index, newDefineInfo);
			}
			if(parentFileInfo.get(index).contains(" "+oldName+" ")){
				String newDefineInfo=parentFileInfo.get(index).replace(oldName, newName);
				parentFileInfo.set(index, newDefineInfo);
			}
		}
		parentFileOperation.setFileContent(parentFileInfo);
	}
	/**重命名文件,并修改directoryPath，absolutePath，name*/
	private void renameFile(String newContainerName){
		File oldDir=new File(getDirectoryPath());
		newContainerName=PSMTool.toVariableName(newContainerName);
		directoryPath=directoryPath.substring(0, directoryPath.length()-1-getName().length())+"\\"+newContainerName;
		File oldFile=new File(directoryPath+"\\"+PSMTool.toClassName(getName())+".java");
		name=newContainerName;
		absolutePath=directoryPath+"\\"+PSMTool.toClassName(name)+".java";
		File newDir=new File(directoryPath);
		File newFile=new File(absolutePath);
		oldDir.renameTo(newDir);
		oldFile.renameTo(newFile);
		this.fileOperationRobot=new MyFileOperation(absolutePath);
		this.packagePath=this.directoryPath.substring(ConstValue.HOLMOSPAGESTOREBASEDIR.length()+1).replace("\\", ".");
		this.importPath=this.packagePath+"."+PSMTool.toClassName(name);
	}
	/**重命名的时候的基本检查，检查的点有:
	 * <li>新名字是否合法</li>
	 * <li>是否和老名字一样</li>
	 * <li>在其父容器中是否存在名字一直的变量</li>
	 * @param newName 带检查的新名字
	 * @return true 合法<br>false 不合法
	 * */
	public boolean baseCheckForRename(String newName){
		if(!PSMTool.isValid(newName)){
			return false;
		}
		String oldName=getName();
		if(newName.equalsIgnoreCase(oldName))
			return false;
		if(this.getParentContainer() instanceof ContainerInfo){
			if(((ContainerInfo)getParentContainer()).isVariableExist((newName)))
				return false;
		}
		return true;
	}
	/**已知该对象,从磁盘中获得该ContainerInfo下的所有containers和elements信息,从locator存放地获取locator信息:
	 * <li>只获得此ContainerInfo下一级信息,不会递归获取,主要是为了实现懒加载功能</li>
	 * <li>此方法调用后更新了此ContainerInfo下所有containers和elements信息,从locator存放地获取locator信息</li>
	 * */
	public void getInfoFromDisk(){
		ArrayList<String>pageContent=fileOperationRobot.getFileContent();
		ArrayList<ArrayList<String>>variablesContent=PSMTool.getElementContent(pageContent);
		if(!isEmpty())
			return;
		for(ArrayList<String> variableContent:variablesContent){
			VariableInfo variable=PSMTool.getInfoFromContent(variableContent);
			switch (variable.getType()) {
			case SUBPAGE:
				this.subpages.add((SubPageInfo) variable);
				break;
			case COLLECTION:
				this.collections.add((CollectionInfo) variable);
				break;
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
				this.elements.add((ElementInfo) variable);
				break;
			default:
				break;
			}
			variable.setParentContainer(this);
		}
	}
	
	/**变成新的容器类型，修改的步骤如下
	 * <li>检查新变量名字是否合法</li>
	 * <li>检查新变量名字在其父容器中是否存在</li>
	 * <li>修改其父容器里面的内容</li>
	 * <li>修改本容器文件中的内容</li>
	 * <li>如果是FrameInfo类型改成其他容器类型，需要删除父容器下的FrameInfo类型的变量</li>
	 * <li>递归修改这个容器下子容器的包名信息</li>
	 * */
	public void changeToNewContainer(ContainerInfo newContainer){
		if(!PSMTool.isValid(newContainer.name)){
			return;
		}
		if(isSameAs(newContainer))
			return;
		if((type.equals(INFOTYPE.FRAME)&&!newContainer.type.equals(INFOTYPE.FRAME)&&
				((FrameInfo)this).frames.size()!=0)){
			for(FrameInfo frame:((FrameInfo)this).frames){
				((FrameInfo)this).removeFrame(frame.name);
			}
		}
		if(comment!=null&&newContainer.comment==null||!comment.equalsIgnoreCase(newContainer.comment)){
			MyFileOperation fileOperation=null;
			ArrayList<String>parentFileContent=null;
			if(parentContainer instanceof PageInfo)
				fileOperation=((PageInfo)parentContainer).getFileOperationRobot();
			else if(parentContainer instanceof ContainerInfo)
				fileOperation=((ContainerInfo)parentContainer).getFileOperationRobot();
			parentFileContent=fileOperationRobot.getFileContent();
			for(int i=0;i<parentFileContent.size();i++){
				if(new MyString(parentFileContent.get(i)).includeAnd("new ","\""+comment+"\"")){
					parentFileContent.set(i, parentFileContent.get(i).replace(comment, newContainer.comment));
				}
			}
			fileOperation.setFileContent(parentFileContent);
			this.comment=newContainer.comment;
		}
		if(!type.equals(newContainer.type)){
			changeParentContainerVariableOnly(newContainer.type);
			ArrayList<String>fileContent=fileOperationRobot.getFileContent();
			for(int i=0;i<fileContent.size();i++){
				if(fileContent.get(i).contains(" extends ")){
					fileContent.set(i, fileContent.get(i).replace(PSMTool.getInfoTypeClassName(type), PSMTool.getInfoTypeClassName(newContainer.type)));
					break;
				}
			}
			this.fileOperationRobot.setFileContent(fileContent);
			this.type=newContainer.type;
		}
		if(!name.equalsIgnoreCase(newContainer.name)){
			this.rename(newContainer.name);
		}
	}
	private void changeParentContainerVariableOnly(INFOTYPE newType){
		if(parentContainer instanceof PageInfo){
			if(type.equals(INFOTYPE.SUBPAGE)){
				((PageInfo)parentContainer).getSubpages().remove(this);
			}else if(type.equals(INFOTYPE.COLLECTION)){
				((PageInfo)parentContainer).getCollections().remove(this);
			}else if(type.equals(INFOTYPE.FRAME)){
				((PageInfo)parentContainer).getFrames().remove(this);
			}
			
			if(newType.equals(INFOTYPE.SUBPAGE)){
				((PageInfo)parentContainer).getSubpages().add((SubPageInfo) this);
			}else if(newType.equals(INFOTYPE.COLLECTION)){
				((PageInfo)parentContainer).getCollections().add((CollectionInfo) this);
			}else if(newType.equals(INFOTYPE.FRAME)){
				((PageInfo)parentContainer).getFrames().add((FrameInfo) this);
			}
		}else if(parentContainer instanceof ContainerInfo){
			if(type.equals(INFOTYPE.SUBPAGE)){
				((PageInfo)parentContainer).getSubpages().remove(this);
			}else if(type.equals(INFOTYPE.COLLECTION)){
				((ContainerInfo)parentContainer).getCollections().remove(this);
			}else if(type.equals(INFOTYPE.FRAME)){
				((FrameInfo)parentContainer).getFrames().remove(this);
			}
			
			if(newType.equals(INFOTYPE.SUBPAGE)){
				((ContainerInfo)parentContainer).getSubpages().add((SubPageInfo) this);
			}else if(newType.equals(INFOTYPE.COLLECTION)){
				((ContainerInfo)parentContainer).getCollections().add((CollectionInfo) this);
			}else if(newType.equals(INFOTYPE.FRAME)){
				((FrameInfo)parentContainer).getFrames().add((FrameInfo) this);
			}
		}
	}
	/**修改此容器的包信息和import信息，递归修改此容器下的子容器的信息*/
	public void changeInfo(String oldPackagePath,String newPackagePath){
		if(isEmpty())
			this.getInfoFromDisk();
		ArrayList<String>containerContent=fileOperationRobot.getFileContent();
		PSMTool.changePackageAndImportPath(containerContent, oldPackagePath, newPackagePath);
		fileOperationRobot.setFileContent(containerContent);
		for(SubPageInfo subpage:subpages){
			subpage.setParentContainer(this);
			subpage.changeInfo(oldPackagePath, newPackagePath);
		}for(CollectionInfo collection:collections){
			collection.setParentContainer(this);
			collection.changeInfo(oldPackagePath, newPackagePath);
		}
		if(this instanceof FrameInfo){
			for(FrameInfo frame:((FrameInfo)this).frames){
				frame.changeInfo(oldPackagePath, newPackagePath);
				frame.setParentContainer(this);
			}
		}for(ElementInfo element:elements){
			element.setParentContainer(this);
		}
	}
	/**调试的时候用到*/
	public void outPutInfo() {
		System.out.println("name:"+name);
		System.out.println("absolutePath:"+absolutePath);
		System.out.println("directoryPath:"+directoryPath);
		if(parentContainer instanceof PageInfo){
			System.out.println("parentFolder:"+parentContainer==null?null:((PageInfo) parentContainer).getName());
		}else{
			System.out.println("parentFolder:"+parentContainer==null?null:((ContainerInfo) parentContainer).getName());
			
		}
		for(SubPageInfo subpage:subpages){
			subpage.outPutInfo();
		}for(CollectionInfo collection:collections){
			collection.outPutInfo();
		}for(ElementInfo element:elements){
			element.outPutInfo();
		}
	}
	@Override
	public ContainerInfo clone(){
		ContainerInfo clone;
		if(this instanceof SubPageInfo){
			clone=new SubPageInfo(name, comment);
		}else if(this instanceof CollectionInfo){
			clone=new CollectionInfo(name, comment);
		}else{
			clone=new FrameInfo(name, comment);
		}
		clone.setParentContainer(parentContainer);
		clone.setLocatorInfo(locatorInfo);
		for(SubPageInfo subpage:subpages){
			clone.subpages.add((SubPageInfo) subpage.clone());
		}for(CollectionInfo collection:collections){
			clone.collections.add((CollectionInfo) collection.clone());
		}if(this instanceof FrameInfo){
			for(FrameInfo frame:((FrameInfo)this).frames){
				((FrameInfo)clone).frames.add((FrameInfo) frame.clone());
			}
		}for(ElementInfo element:elements){
			clone.elements.add(element.clone());
		}
		return clone;
	}
}
