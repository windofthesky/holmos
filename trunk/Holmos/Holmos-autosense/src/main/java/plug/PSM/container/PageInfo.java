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
import Holmos.Holmos.plug.PSM.NetPageInfo;
import Holmos.Holmos.plug.PSM.PSMTool;
import Holmos.Holmos.plug.PSM.VariableInfo;
import Holmos.Holmos.plug.PSM.element.ElementInfo;
import Holmos.Holmos.plug.PSM.folder.FolderInfo;

public class PageInfo implements NetPageInfo {
	/*此变量的名字*/
	private String name;
	/*这个变量的说明，框架执行的时候用到*/
	private String comment;
	private FolderInfo parentFolder;
	private String absolutePath;
	private String directoryPath;
	private String packagePath;
	
	private List<SubPageInfo>subpages=new ArrayList<SubPageInfo>();
	private List<CollectionInfo>collections=new ArrayList<CollectionInfo>();
	private List<ElementInfo>elements=new ArrayList<ElementInfo>();
	private List<FrameInfo>frames=new ArrayList<FrameInfo>();
	public List<FrameInfo> getFrames() {
		return frames;
	}
	protected MyFileOperation fileOperationRobot;
	public PageInfo(String pageName,String comment){
		this.name=PSMTool.toVariableName(pageName);
		this.comment=comment;
	}
	/**如果此PageInfo里面没有变量则返回true*/
	public boolean isEmpty(){
		if(subpages.isEmpty()&&collections.isEmpty()&&frames.isEmpty()&&elements.isEmpty())
			return true;
		return false;
	}
	/**设置父目录,并设置此PageInfo的directoryPath和absolutePath信息
	 * 还有packagePath信息
	 * */
	public void setParentFolder(FolderInfo parentFolder){
		this.parentFolder=parentFolder;
		this.directoryPath=parentFolder.getAbsolutePath()+"\\"+name;
		this.absolutePath=this.directoryPath+"\\"+PSMTool.toClassName(name)+".java";
		this.fileOperationRobot=new MyFileOperation(absolutePath);
		this.setPackagePath(this.directoryPath.substring(ConstValue.HOLMOSPAGESTOREBASEDIR.length()+1).replace("\\", "."));
	}
	public MyFileOperation getFileOperationRobot() {
		return fileOperationRobot;
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
	/**获得此变量的名字*/
	public String getName() {
		return name;
	}
	/**获得此变量的父容器对象 */
	public FolderInfo getParentContainer(){
		return parentFolder;
	}
	/**获得这个变量的说明，框架执行的时候用到 */
	public String getComment() {
		return comment;
	}
	/**
	 * 在此PageInfo里面添加一个变量,名字为subPageName,然后并且在框架默认的位置新建一个名字为subPageName的subpage.java文件,里面没有包括任何元素: 
	 * <li>检查subpageName是否合法,创建一个新的subpage.java文件,在框架指定的位置</li>
	 * <li>检查是否已经存在</li>
	 * <li>更新当前PageInfo的Page.java文件里面的内容</li>
	 * <li>更新当前PageInfo对象里面的subpages信息</li>
	 * @param subpage 添加的subpage变量
	 * @return
	 * */
	public CreateReturnInfo addSubPageInfo(SubPageInfo subpage){
		if(isSubPageInfoExist(subpage.getName())){
			// --TODO 给出已经存在提示 @Mash
			System.out.println("此SubPage已经存在,请重新命名!");
			return new CreateReturnInfo(ErrorType.HAS_EXIT);
		}
		if(!PSMTool.isValid(subpage.getName())){
			return new CreateReturnInfo(ErrorType.NAME_ILLEGAL);
		}
		//新建文件
		subpage.setParentContainer(this);
		ArrayList<String>containerFileContent=new ArrayList<String>();
		containerFileContent.add(subpage.getPackagePath());
		containerFileContent.add("");
		containerFileContent.addAll(createNewContainerFile(subpage.getName(), VarType.SUBPAGE));
		subpage.fileOperationRobot.setFileContent(containerFileContent);
		//更新本容器内容
		this.createNewVariable(subpage);
		//修改subpages信息
		this.subpages.add(subpage);
		return new CreateReturnInfo(subpage);
	}
	/**在此Page.java文件内部新建名字为name的变量*/
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
		containerFileContent.addAll(index, variable.getJavalocatorManager().createLocatorInfo());
		this.fileOperationRobot.setFileContent(containerFileContent);
	}
	/**获得一个新的container Java文件的内容*/
	protected ArrayList<String>createNewContainerFile(String name,VarType type){
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
	/**在此PageInfo里面添加一个变量,名字为collectionName,然后并且在框架默认的位置新建一个名字为subPageName的Collection.java文件,里面没有包括任何元素: 
	 * <li>检查collectionName是否合法,创建一个新的Collection.java文件,在框架指定的位置</li>
	 * <li>检查是否已经存在</li>
	 * <li>更新当前PageInfo的Page.java文件里面的内容</li>
	 * <li>更新当前PageInfo对象里面的colletions信息</li>
	 * @param collection 添加的collection变量
	 * @return
	 * */
	public CreateReturnInfo addCollectionInfo(CollectionInfo collection){
		if(isColleticonInfoExist(collection.getName())){
			// --TODO 给出已经存在提示 @Mash
			System.out.println("此Collection已经存在,请重新命名!");
			return new CreateReturnInfo(ErrorType.HAS_EXIT);
		}
		if(!PSMTool.isValid(collection.getName())){
			return new CreateReturnInfo(ErrorType.NAME_ILLEGAL);
		}
		collection.setParentContainer(this);
		ArrayList<String>containerFileContent=new ArrayList<String>();
		containerFileContent.add(collection.getPackagePath());
		containerFileContent.add("");
		containerFileContent.addAll(createNewContainerFile(collection.getName(), VarType.SUBPAGE));
		collection.fileOperationRobot.setFileContent(containerFileContent);
		//更新本容器内容
		createNewVariable(collection);
		//修改collections信息
		this.collections.add(collection);
		return new CreateReturnInfo(collection);
	}
	/**
	 * 在此PageInfo里面添加一个元素实例,名字为elementName:
	 * <li>检查type是否是元素类型</li> 
	 * <li>检查是否已经存在</li> 
	 * <li>更新当前PageInfo的Page.java文件里面的内容</li> 
	 * <li>更新当前PageInfo对象里面的elements信息</li> 
	 * @param element 要添加的元素
	 * @return
	 * */
	public CreateReturnInfo addElementInfo(ElementInfo element){
		if(isElementInfoExist(element.getName())){
			// --TODO 给出已经存在提示 @Mash
			System.out.println("此元素Element已经存在，请重新命名!");
			return new CreateReturnInfo(ErrorType.HAS_EXIT);
		}
		if(!PSMTool.isValid(element.getName())){
			return new CreateReturnInfo(ErrorType.NAME_ILLEGAL);
		}
		element.setParentContainer(this);
		createNewVariable(element);
		this.elements.add(element);
		return new CreateReturnInfo(element);
	}
	/**在此PageInfo里面添加一个容器实例,名字为containerName
	 * <li>检查type是否是容器类型</li>
	 * <li>检查是否已经存在</li>
	 * <li>在框架规定位置新建Container.java文件</li>
	 * <li>更新当前PageInfo的Page.java文件里面的内容</li>
	 * <li>更新当前congtaienrs的信息</li>
	 * @param container 要添加的container
	 * @return
	 * */
	public CreateReturnInfo addContainerInfo(ContainerInfo container){
		switch (container.getType()) {
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
	/**检查该PageInfo对象里面是否具有名字为subPageName的subpage
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
	/**检查该PageInfo对象里面是否具有名字为collectionName的collection 
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
	/**检查该PageInfo对象里面是否具有名字为containerName的container 
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
	/**检查该PageInfo对象里面是否具有名字为variableName的variable
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
	/**检查该PageInfo对象里面是否具有名字为variableName的variable 
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
	/**移除此PageInfo实例下的名字为subPageName的subPageInfo实例
	 * <li>检查名字是否合法</li>
	 * <li>扫描subpages,找到匹配项了之后删除subPage.java文件，一并删除它的子信息</li>
	 * <li>更新此PageInfo的subpages信息</li>
	 * @param subpageName 待移除的subPageInfo的名字
	 * */
	public void removeSubPage(String subPageName){
		boolean findSubPage=false;
		if(!isSubPageInfoExist(subPageName)){
			return;
		}
		//删文件
		for(SubPageInfo subpage:subpages){
			if(subpage.getName().equalsIgnoreCase(subPageName)){
				findSubPage=true;
				MyFile.deleteDirectory(subpage.directoryPath);
				subpage.getJavalocatorManager().removeLocatorInfo();
				this.subpages.remove(subpage);
				break;
			}
		}
		//删除本文件信息
		if(findSubPage)
			deleteInfo(subPageName);
	}
	/**删除本文件与name对应的变量信息*/
	protected void deleteInfo(String name){
		ArrayList<String>pageFileContent=this.fileOperationRobot.getFileContent();
		for(int i=0;i<pageFileContent.size();i++){
			MyString lineInfo=new MyString(pageFileContent.get(i));
			if(lineInfo.includeAnd("import ",PSMTool.toClassName(name),name))
				pageFileContent.remove(i);
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
	/**移除此PageInfo实例下的名字为subPageName的collectionInfo实例
	 * <li>检查名字是否合法</li>
	 * <li>扫描collections,找到匹配项了之后删除Collection.java文件，一并删除它的子信息</li>
	 * <li>更新此PageInfo的collections信息</li>
	 * @param collectionName 待移除的collectionInfo的名字
	 * */
	public void removeCollection(String collectionName){
		boolean findCollection=false;
		if(!isColleticonInfoExist(collectionName)){
			return ;
		}
		//删文件
		for(CollectionInfo collection:collections){
			if(collection.getName().equalsIgnoreCase(collectionName)){
				findCollection=true;
				MyFile.deleteDirectory(collection.directoryPath);
				collection.getJavalocatorManager().removeLocatorInfo();
				this.collections.remove(collection);
				break;
			}
		}
		//删除本文件信息
		if(findCollection)
			deleteInfo(collectionName);
	}
	/**移除此PageInfo实例下的名字为containerName的ContainerInfo实例
	 * <li>检查名字是否合法</li>
	 * <li>扫描subpages,找到匹配项了之后删除Container.java文件，一并删除它的子信息</li>
	 * <li>更新此PageInfo的containers以及与之匹配的container信息</li>
	 * @param collectionName 待移除的containerInfo的名字
	 * */
	public void removeContainer(String containerName){
		removeSubPage(containerName);
		removeCollection(containerName);
		removeFrame(containerName);
		
	}
	/**移除此PageInfo实例下的名字为elementName的ElementInfo实例
	 * <li>检查名字是否合法</li>
	 * <li>扫描elements，删除匹配项</li>
	 * <li>更新此PageInfo的elements信息</li>
	 * @param elementName 待移除的elementInfo的名字
	 * */
	public void removeElement(String elementName){
		boolean findElement=false;
		for(ElementInfo element:this.elements){
			if(element.getName().equalsIgnoreCase(elementName)){
				findElement=true;
				element.getJavalocatorManager().removeLocatorInfo();
				this.elements.remove(element);
				break;
			}
		}
		//删除本文件信息
		if(findElement)
			deleteInfo(elementName);
	}
	/**根据variableName来判断是哪种变量，然后根据这个来调用定义好的remove操作来完成*/
	public void removeVariable(String variableName){
		removeContainer(variableName);
		removeElement(variableName);
	}
	/**获取此PageInfo下与subPageName匹配的subPageInfo实例
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
	/**获取此PageInfo下与CollectionName匹配的CollectionInfo实例
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
	/**获取此PageInfo下与containerName匹配的containerInfo实例
	 * <li>检查名字是否合法，并将其第一个字母大写</li>
	 * <li>判断是否存在,扫描</li>
	 * @param subPageName 要获取的subPageInfo实例的名字
	 * @return 获取到的实例，如果没有匹配项，返回null
	 * */
	public ContainerInfo getContainer(String containerName){
		ContainerInfo container=getSubPage(containerName);
		if(container!=null)return container;
		container=getCollection(containerName);
		if(container!=null)return container;
		return getFrame(containerName);
	}
	/**获取此PageInfo下与elementName匹配的elementInfo实例
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
	/**获取此PageInfo下与subPageName匹配的variableInfo实例
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
	/**将subpage对象拷贝到当前PageInfo下:
	 * <li>实现硬盘信息拷贝,并将subpage实例里面所有的已有的信息全部拷贝</li>
	 * <li>递归修改此目录下所有的实例对象的包信息,absolutePath,directoryPath信息</li>
	 * <li>修改subpage对象的信息absolutePath,directoryPath等信息</li>
	 * <li>修改当前对象的信息</li>
	 * @param subPage 要拷贝的subpage对象
	 * */
	public void pasteSubPageInfo(SubPageInfo subPage){
		MyDirectoryOperation.copyTo(new File(directoryPath+"\\"+subPage.getName()), new File(subPage.directoryPath));
		SubPageInfo subPageClone=(SubPageInfo) subPage.clone();
		//修改本文件的信息
		addNewContainerInfo(subPageClone);
		//递归修改子对象信息和文件内容中的import信息
		String oldPackagePath=subPage.packagePath;
		String newPackagePath=packagePath+"."+subPage.getName();
		subPageClone.changeInfo(this,oldPackagePath,newPackagePath);
		this.subpages.add(subPageClone);
	}
	/**修改本文件信息，添加了新的容器对象*/
	private void addNewContainerInfo(ContainerInfo container){
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
	/**将collection对象拷贝到当前PageInfo下:
	 * <li>实现硬盘信息拷贝,并将collection实例里面所有的已有的信息全部拷贝</li>
	 * <li>递归修改此目录下所有的实例对象的包信息,absolutePath,directoryPath信息</li>
	 * <li>修改collection对象的信息absolutePath,directoryPath等信息</li>
	 * <li>修改当前对象的信息</li>
	 * @param collection 要拷贝的subpage对象 
	 * */
	public void pasteCollectionInfo(CollectionInfo collection){
		MyDirectoryOperation.copyTo(new File(directoryPath+"\\"+collection.getName()), new File(collection.directoryPath));
		CollectionInfo collectionClone=(CollectionInfo) collection.clone();
		addNewContainerInfo(collectionClone);
		String oldPackagePath=collection.packagePath;
		String newPackagePath=packagePath+"."+collection.getName();
		collectionClone.changeInfo(this,oldPackagePath,newPackagePath);
		this.collections.add(collectionClone);
	}
	/**将container对象拷贝到当前PageInfo下:
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
		}else if(container instanceof FrameInfo){
			pasteFrameInfo((FrameInfo) container);
		}
	}
	/**将element对象拷贝到当前PageInfo下:
	 * <li>修改container对象的信息</li>
	 * <li>修改当前对象的信息</li>
	 * @param container 要拷贝的container对象 
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
	/**重命名当前PageInfo实例:
	 * <li>检查新的名字是否合法,合法继续,在磁盘里面重命名此Page.java</li>
	 * <li>递归修改此PageInfo下所有的java文件的包信息,absolutePath,directoryPath信息</li>
	 * <li>修改此PageInfo实例中的信息,absolutePath,name等信息</li>
	 * @param newPageName 要新修改的ContainerInfo实例的名字
	 * */
	public void rename(String newPageName){
		if(!baseCheckForRename(newPageName)){
			return;
		}
		String oldName=name;
		String oldPackagePath=this.packagePath;
		//修改本对象信息，文件物理重命名
		renameFile(newPageName);
		//修改本文件的定义部分的内容
		changeDefineInfo(oldName,name);
		//递归修改子对象信息(packagePath,importPath,directoryPath,absolutePath)
		//和子容器文件信息(包括packagePath和importPath)，并且修改此文件信息中的packagePath和importPath
		changeInfo(oldPackagePath,packagePath);
	}
	/**递归修改子对象信息(packagePath,importPath,directoryPath,absolutePath)和子容器文件信息(包括packagePath和importPath)
	 * rename 的时候用到
		*/
	public void changeInfo(String oldPackagePath, String newPackagePath) {
		if(!isEmpty())
			this.getInfoFromDisk();
		ArrayList<String>fileContent=fileOperationRobot.getFileContent();
		PSMTool.changePackageAndImportPath(fileContent, oldPackagePath, newPackagePath);
		fileOperationRobot.setFileContent(fileContent);
		for(SubPageInfo subpage:subpages){
			subpage.setParentContainer(this);
			subpage.changeInfo(oldPackagePath,newPackagePath);
		}for(CollectionInfo collection:collections){
			collection.setParentContainer(this);
			collection.changeInfo(oldPackagePath,newPackagePath);
		}for(FrameInfo frame:frames){
			frame.setParentContainer(this);
			frame.changeInfo(oldPackagePath,newPackagePath);
		}for(ElementInfo element:elements){
			element.setParentContainer(this);
		}
	}
	
	/**修改该Page.java文件的信息,rename时候用到*/
	private void changeDefineInfo(String oldName, String newName) {
		String oldClass=PSMTool.toClassName(oldName);
		String newClass=PSMTool.toClassName(newName);
		ArrayList<String>fileContent=fileOperationRobot.getFileContent();
		for(int i=0;i<fileContent.size();i++){
			if(new MyString(fileContent.get(i)).includeAnd(" extends")){
				String newDefineInfo=fileContent.get(i).replace(newClass, oldClass);
				fileContent.set(i, newDefineInfo);
			}
			if(new MyString(fileContent.get(i)).includeOr("public "+oldClass+" ")){
				String newDefineInfo=fileContent.get(i).replace(newClass, oldClass);
				fileContent.set(i, newDefineInfo);
				fileOperationRobot.setFileContent(fileContent);
				break;
			}
		}
	}
	/**重命名文件,并修改directoryPath，absolutePath，name*/
	private void renameFile(String newPageName){
		File oldDir=new File(getDirectoryPath());
		newPageName=PSMTool.toVariableName(newPageName);
		directoryPath=directoryPath.substring(0, directoryPath.length()-1-getName().length())+"\\"+newPageName;
		File oldFile=new File(directoryPath+"\\"+PSMTool.toClassName(name)+".java");
		absolutePath=directoryPath+"\\"+PSMTool.toClassName(newPageName)+".java";
		name=newPageName;
		File newDir=new File(directoryPath);
		File newFile=new File(absolutePath);
		System.out.println(oldDir.renameTo(newDir));
		System.out.println(oldFile.renameTo(newFile));
		this.fileOperationRobot=new MyFileOperation(absolutePath);
		this.setPackagePath(this.directoryPath.substring(ConstValue.HOLMOSPAGESTOREBASEDIR.length()+1).replace("\\", "."));
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
		if(this.getParentContainer() instanceof FolderInfo){
			if(((FolderInfo)getParentContainer()).isPageExist((newName)))
				return false;
		}
		return true;
	}
	/**已知该对象,从磁盘中获得该PageInfo下的所有containers和elements信息,从locator存放地获取locator信息:
	 * <li>只获得此PageInfo下一级信息,不会递归获取,主要是为了实现懒加载功能</li>
	 * <li>此方法调用后更新了此PageInfo下所有containers和elements信息,从locator存放地获取locator信息</li>
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
			case FRAME:
				this.frames.add((FrameInfo) variable);
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

	/**在此PageInfo里面添加一个变量,名字为frameName,然后并且在框架默认的位置新建一个名字为frameName的Frame.java文件,里面没有包括任何元素:
	 * <li>检查frameName是否合法,创建一个新的Frame.java文件,在框架指定的位置</li>
	 * <li>检查是否已经存在</li>
	 * <li>更新当前PageInfo的Page.java文件里面的内容</li>
	 * <li>更新当前PageInfo对象里面的frames信息</li>
	 * @param frameName 在当前pageInfo下面新建Frame的名字
	 * @return
	 * */
	public CreateReturnInfo addFrameInfo(String frameName){
		if(isFrameInfoExist(frameName)){
			// --TODO 给出已经存在提示 @Mash
			System.out.println("此Frame已经存在,请重新命名!");
			return new CreateReturnInfo(ErrorType.HAS_EXIT);
		}
		//新建文件
		FrameInfo frame=new FrameInfo(frameName, comment);
		frame.setParentContainer(this);
		ArrayList<String>containerFileContent=new ArrayList<String>();
		containerFileContent.add(frame.getPackagePath());
		containerFileContent.add("");
		containerFileContent.addAll(createNewContainerFile(frameName, VarType.SUBPAGE));
		frame.fileOperationRobot.setFileContent(containerFileContent);
		//更新本容器内容
		this.createNewVariable(frame);
		//修改subpages信息
		this.frames.add(frame);
		return new CreateReturnInfo(frame);
	}
	/**检查该PageInfo对象里面是否具有名字为frameName的frame
	 * <li>检查frameName是否合法,并将frameName的第一个字母大写</li>
	 * <li>扫描frames</li>
	 * @param frameName 在当前PageInfo下面新建Frame的名字
	 * */
	public boolean isFrameInfoExist(java.lang.String frameName){
		for(FrameInfo frame:this.frames){
			if(frame.getName().equalsIgnoreCase(frameName)){
				return true;
			}
		}
		return false;
	}
	/**移除此PageInfo实例下的名字为frameName的frameInfo实例
	 * <li>检查名字是否合法</li>
	 * <li>扫描frames,找到匹配项了之后删除Frame.java文件，一并删除它的子信息</li>
	 * <li>更新此PageInfo的frames信息</li>
	 * @param frameName 待移除的frameInfo的名字
	 * */
	public void removeFrame(java.lang.String frameName){
		if(!isFrameInfoExist((frameName))){
			return;
		}
		//删文件
		for(FrameInfo frame:frames){
			if(frame.getName().equalsIgnoreCase(frameName)){
				MyFile.deleteDirectory(frame.directoryPath);
				this.frames.remove(frame);
				break;
			}
		}
		//删除本文件信息
		deleteInfo(frameName);
	}
	/**获取此PageInfo下与frameName匹配的frameInfo实例
	 * <li>检查名字是否合法，并将其第一个字母大写</li>
	 * <li>判断是否存在,扫描</li>
	 * @param frameName 要获取的frameInfo实例的名字
	 * @return 获取到的实例，如果没有匹配项，返回null
	 * */
	public FrameInfo getFrame(java.lang.String frameName){
		for(FrameInfo frame:frames){
			if(frame.getName().equalsIgnoreCase(frameName)){
				return frame;
			}
		}
		return null;
	}
	/**将frame对象拷贝到当前PageInfo下:
	 * <li>实现硬盘信息拷贝,并将frame实例里面所有的已有的信息全部拷贝</li>
	 * <li>递归修改此目录下所有的实例对象的包信息,absolutePath,directoryPath等信息</li>
	 * <li>修改frame对象的信息absolutePath,directoryPath信息</li>
	 * <li>修改当前对象的信息</li>
	 * @param frame 要拷贝的frame对象
	 * */
	public void pasteFrameInfo(FrameInfo frame){
		MyDirectoryOperation.copyTo(new File(directoryPath+"\\"+frame.getName()), new File(frame.directoryPath));
		FrameInfo frameClone=(FrameInfo) frame.clone();
		addNewContainerInfo(frameClone);
		String oldPackagePath=frame.packagePath;
		String newPackagePath=packagePath+"."+frame.getName();
		frameClone.changeInfo(this,oldPackagePath,newPackagePath);
		this.frames.add(frameClone);
	}
	public String getPackagePath() {
		return "package "+packagePath+";";
	}
	public void setPackagePath(String packagePath) {
		this.packagePath = packagePath;
	}
	/**在粘贴Page的时候用到*/
	public void changeInfo(FolderInfo folderInfo) {
		String oldPackagePath=packagePath;
		this.setParentFolder(folderInfo);
		String newPackagePath=packagePath;
		for(SubPageInfo subpage:subpages){
			subpage.changeInfo(this,oldPackagePath,newPackagePath);
		}for(CollectionInfo collection:collections){
			collection.changeInfo(this,oldPackagePath,newPackagePath);
		}for(FrameInfo frame:frames){
			frame.changeInfo(this,oldPackagePath,newPackagePath);
		}for(ElementInfo element:elements){
			element.setParentContainer(this);
		}
	}
	
	/**调试的时候用到*/
	public void outPutInfo() {
		System.out.println("name:"+name);
		System.out.println("absolutePath:"+absolutePath);
		System.out.println("directoryPath:"+directoryPath);
		System.out.println("parentFolder:"+parentFolder==null?null:parentFolder.getName());
		for(SubPageInfo subpage:subpages){
			subpage.outPutInfo();
		}for(CollectionInfo collection:collections){
			collection.outPutInfo();
		}for(FrameInfo frame:frames){
			frame.outPutInfo();
		}for(ElementInfo element:elements){
			element.outPutInfo();
		}
	}
	/**修改Page对象，能修改的地方只有两个,改变都是针对其自身
	 * <li>name:</li>
	 * <li>comment:</li>
	 * */
	public void changePage(PageInfo newPage){
		if(!name.equalsIgnoreCase(newPage.name)){
			rename(newPage.name);
		}
		if(comment==null&&newPage.comment!=null||comment.equalsIgnoreCase(newPage.comment)){
			ArrayList<String>fileContent=fileOperationRobot.getFileContent();
			for(int i=0;i<fileContent.size();i++){
				if(new MyString(fileContent.get(i)).includeAnd("comment","=",";")){
					fileContent.set(i, fileContent.get(i).replace(comment, newPage.comment));
					break;
				}
			}
			fileOperationRobot.setFileContent(fileContent);
		}
	}
	public PageInfo clone(){
		PageInfo clone=new PageInfo(name, comment);
		clone.setParentFolder(parentFolder);
		for(SubPageInfo subpage:subpages){
			clone.subpages.add((SubPageInfo) subpage.clone());
		}for(CollectionInfo collection:collections){
			clone.collections.add((CollectionInfo) collection.clone());
		}
		for(FrameInfo frame:frames){
			clone.frames.add((FrameInfo) frame.clone());
		}
		for(ElementInfo element:elements){
			clone.elements.add(element.clone());
		}
		return clone;
	}
	
}
