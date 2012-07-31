package Holmos.Holmos.plug.PSM.folder;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import Holmos.Holmos.constvalue.ConstValue;
import Holmos.Holmos.innerTools.MyDirectoryOperation;
import Holmos.Holmos.innerTools.MyFile;
import Holmos.Holmos.plug.PSM.NetPageInfo;
import Holmos.Holmos.plug.PSM.PSMTool;
import Holmos.Holmos.plug.PSM.container.PageInfo;
import cn.autosense.browser.exchange.util.CreateReturnInfo;
import cn.autosense.browser.exchange.util.ErrorType;

/**文件夹目录信息,只能包含子目录和PageInfo类型的.java文件
 * @author 吴银龙(15857164387)
 * */
public class FolderInfo implements NetPageInfo {
	private String name;
	private String absolutePath;
	private String directoryPath;
	private String packagePath;
	private List<FolderInfo>childFolders=new ArrayList<FolderInfo>();
	private List<PageInfo>pages=new ArrayList<PageInfo>();
    private FolderInfo parentFolder;
	public FolderInfo(String folderName){
		this.name=folderName;
	}
	public void setParentFolder(FolderInfo parentFolder){
		this.parentFolder=parentFolder;
		if(parentFolder!=null){
			this.directoryPath=parentFolder.absolutePath;
			this.absolutePath=this.directoryPath+"\\"+name;
			this.packagePath=this.absolutePath.substring(ConstValue.HOLMOSPAGESTOREBASEDIR.length()+1).replace("\\", ".");
		}
	}
	/**获得当前目录的目录名字*/
	public String getName() {
		return name;
	}
	/**获得当前目录的绝对路径*/
	public String getAbsolutePath() {
		return absolutePath;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setAbsolutePath(String absolutePath) {
		this.absolutePath = absolutePath;
	}
	public void setDirectoryPath(String directoryPath) {
		this.directoryPath = directoryPath;
	}
	/**获得当前目录所在目录路径*/
	public String getDirectoryPath() {
		return directoryPath;
	}
	/**获得当前目录的所有子目录*/
	public List<FolderInfo> getChildFolders() {
		return childFolders;
	}
	/**获得当前目录下所有PageInfo实例*/
	public List<PageInfo> getPages() {
		return pages;
	}
	public boolean isEmpty(){
		return (pages.isEmpty()&&childFolders.isEmpty());
	}
	/**在当前目录下新建名字为childFolderName的子目录实例;
	 * <li>检查childFolderName的合法性,在磁盘上新建目录</li>
	 * <li>更新当前目录对象childFolders信息</li>
	 * <li>新建的子目录为一个空目录</li>
	 * @param childFolderName
	 * @return 添加的FolderInfo
	 * */
	public CreateReturnInfo addChildFolder(String childFolderName){
		if(!PSMTool.isFolderNameValid(childFolderName)){
			return new CreateReturnInfo(ErrorType.NAME_ILLEGAL);
		}
		FolderInfo childFolder=new FolderInfo(childFolderName);
		childFolder.setParentFolder(this);
		MyFile.createDictory(childFolder.absolutePath);
		this.childFolders.add(childFolder);
		return new CreateReturnInfo(childFolder);
	}
	/**
	 * 在当前目录下新建名字为pageName的pageInfo实例:
	 * <li>检查pageName的合法性,并将pageName的第一个字母大写,在磁盘上新建pageInfo的java文件</li>
	 * <li>更新当前目录对象的pages信息</li>
	 * <li>新建的这个pageInfo对象里面没有一个元素</li>
	 * @param pageName 在此目录下新建的pageInfo实例的名字
	 * @return
	 * */
	public CreateReturnInfo addPageInfo(String pageName,String comment){
		if(!PSMTool.isValid(pageName)){
			return new CreateReturnInfo(ErrorType.NAME_ILLEGAL);
		}
		PageInfo page=new PageInfo(pageName, comment);
		page.setParentFolder(this);
		//建立pageInfo文件
		ArrayList<String>pageContent=new ArrayList<String>();
		pageContent.add(page.getPackagePath());
		pageContent.add("");
		pageContent.addAll(createNewPageFile(page));
		page.getFileOperationRobot().setFileContent(pageContent);
		//修改subpages信息
		this.pages.add(page);
		return new CreateReturnInfo(page);
	}
	private Collection<String> createNewPageFile(PageInfo page) {
		ArrayList<String> pageFileContent=new ArrayList<String>();
		pageFileContent.add(ConstValue.STRUCTPACKAGEINFO);
		pageFileContent.add(ConstValue.ELEMENTPACKAGEINFO);
		pageFileContent.add("");
		pageFileContent.add("public class "+PSMTool.toClassName(page.getName())+" extends Page{");
		pageFileContent.add("");
		pageFileContent.add("public "+PSMTool.toClassName(page.getName())+"(){");
		pageFileContent.add("super();");
		pageFileContent.add("this.comment=\""+page.getComment()+"\";");
		pageFileContent.add("init();");
		pageFileContent.add("}");
		pageFileContent.add("");
		pageFileContent.add("}");
		return pageFileContent;
	}
	/**删除此目录下名字为childFolderName的子目录信息:
	 * <li>查找名字为childFolderName的子目录,如果找到,删除磁盘上的目录信息,递归删除此目录下所有文件和目录信息</li>
	 * <li>更新当前目录对象的childFolders信息</li>
	 * @param childFolderName 要删除的子目录的名字
	 * */
	public void removeChildFolder(String childFolderName){
		if(!isChildFolderExist(childFolderName)){
			return;
		}
		for(FolderInfo folder:this.childFolders){
			if(folder.getName().equalsIgnoreCase(childFolderName)){
				MyFile.deleteDirectory(folder.absolutePath);
				this.childFolders.remove(folder);
				break;
			}
		}
	}
	/**删除当前目录下名字为pageName的pageInfo实例信息:
	 * <li>将pageName的第一个字母大写,查找当前目录下名字为pageName的pageInfo实例,如果找到,在磁盘上删除此Page,并且一起删除此Page里面定义的所有元素信息</li>
	 * <li>更新当前目录的pages信息</li>
	 * @param pageName 要删除的pageInfo的名字
	 * */
	public void removePageInfo(String pageName){
		if(!isPageExist(pageName)){
			return;
		}
		for(PageInfo page:this.pages){
			if(page.getName().equalsIgnoreCase(pageName)){
				MyFile.deleteDirectory(page.getDirectoryPath());
				this.pages.remove(page);
				break;
			}
		}
	}
	/**将folder对象拷贝到当前目录下:
	 * <li>实现硬盘信息拷贝,并将folder实例里面所有的已有的信息全部拷贝</li>
	 * <li>递归修改此目录下所有的实例对象的包信息,absolutePath,directoryPath等信息</li>
	 * <li>修改folder对象的信息absolutePath,directoryPath信息</li>
	 * <li>修改当前对象的信息</li>
	 * @param folder */
	public void pasteFolder(FolderInfo folder){
		MyDirectoryOperation.copyTo(new File(absolutePath+"\\"+folder.getName()), new File(folder.absolutePath));
		FolderInfo folderClone=(FolderInfo) folder.clone();
		folderClone.changeInfo(this);
		this.childFolders.add(folderClone);
	}
	
	private void changeInfo(FolderInfo folderInfo) {
		this.setParentFolder(folderInfo);
		for(FolderInfo folder:this.childFolders){
			folder.changeInfo(this);
		}for(PageInfo page:this.pages){
			page.changeInfo(this);
		}
	}
	/**将page对象拷贝到当前目录下:
	 * <li>实现硬盘信息拷贝,并将page实例里面所有的已有的信息全部拷贝</li>
	 * <li>递归修改此目录下所有的实例对象的包信息,absolutePath,directoryPath信息</li>
	 * <li>修改page对象的信息absolutePath,directoryPath信息</li>
	 * <li>修改当前对象的信息</li>
	 * @param page 要拷贝的page对象
	 * */
	public void pastePageInfo(PageInfo page){
		MyDirectoryOperation.copyTo(new File(absolutePath+"\\"+page.getName()), new File(page.getDirectoryPath()));
		PageInfo pageClone=(PageInfo) page.clone();
		pageClone.changeInfo(this);
		this.pages.add(pageClone);
	}
	/**重命名当前目录:
	 * <li>检查新的名字是否合法,合法继续,在磁盘里面重命名此目录</li>
	 * <li>递归修改此目录下所有的java文件的包信息,absolutePath,directoryPath等信息</li>
	 * <li>修改此目录中的信息,absolutePath,name等信息</li>
	 * @param newName 要新修改的目录名字
	 * */
	public void rename(String newFolderName){
		if(!baseCheckForRename(newFolderName)){
			return;
		}
		String oldPackagePath=packagePath;
		//在磁盘中重命名文件
		renameFile(newFolderName);
		String newPackagePath=packagePath;
		//修改该容器下Java文件的包信息，absolutePath，directoryPath信息
		changeInfo(oldPackagePath,newPackagePath);
	}
	/**修改该容器下Java文件的包信息，absolutePath，directoryPath信息*/
	private void changeInfo(String oldPackagePath,String newPackagePath) {
		if(!isEmpty())
			this.getInfoFromDisk();
		for(PageInfo page:pages){
			page.changeInfo(oldPackagePath,newPackagePath);
		}for(FolderInfo folder:childFolders){
			folder.changeInfo(oldPackagePath,newPackagePath);
		}
	}
	/**在磁盘中重命名文件*/
	private void renameFile(String newFolderName) {
		File oldDir=new File(absolutePath);
		File newDir=new File(directoryPath+"\\"+newFolderName);
		absolutePath=directoryPath+"\\"+newFolderName;
		packagePath=absolutePath.substring(ConstValue.HOLMOSPAGESTOREBASEDIR.length()+1).replace("\\", ".");
		oldDir.renameTo(newDir);
	}
	private boolean baseCheckForRename(String name) {
		if(!PSMTool.isValid(name)){
			return false;
		}
		String oldName=getName();
		if(name.equalsIgnoreCase(oldName))
			return false;
		if(this.parentFolder!=null&&this.parentFolder.isChildFolderExist(name)){
			return false;
		}
		return true;
	}
	
	/**
	 * 判断此目录下面是否存在名字为pageName的pageInfo实例
	 * <li>检查pageName是否合法,并将第一个字母变为大写</li>
	 * <li>扫描pages</li>
	 * @param pageName 待检查的pageInfo名字
	 * @return true 存在 <br> false 不存在
	 * */
	public boolean isPageExist(String pageName){
		for(PageInfo page:pages){
			if(page.getName().equalsIgnoreCase(pageName)){
				return true;
			}
		}
		return false;
	}
	/**判断此目录下面是否存在名字为childFolderName的子目录FolderInfo实例
	 * <li>检查childFolderName是否合法</li>
	 * <li>扫描childFolders</li>
	 * @param 待检查的子目录名字
	 * @return true 存在 <br> false 不存在
	 * */
	public boolean isChildFolderExist(String childFolderName){
		for(FolderInfo folder:childFolders){
			if(folder.getName().equalsIgnoreCase(childFolderName))
				return true;
		}
		return false;
	}
	/**通过pageName来获取此FolderInfo实例下的pageInfo对象
	 * <li>检查pageName是否合法,并将第一个字母变为大写</li>
	 * <li>调用isPageExist(pageName)检查是否存在</li>
	 * <li>如果存在,返回查到的PageInfo对象,如果不存在返回null</li>
	 * @param pageName 要获取的pageInfo对象的名字
	 * @return 获取到的pageInfo对象,如果没有找到返回null
	 * */
	public PageInfo getPageInfoByName(String pageName){
		for(PageInfo page:pages){
			if(page.getName().equalsIgnoreCase(pageName)){
				return page;
			}
		}
		return null;
	}
	/**
	 * 通过childFolderName来获取此FolderInfo实例下的FolderInfo 子目录对象
	 * <li>检查childFolderName是否合法</li>
	 * <li>调用isChildFolderExist(childFolderName)检查是否存在</li>
	 * <li>如果存在,返回查到的FolderInfo 子目录对象,如果不存在返回null</li>
	 * @param childFolderName 要获取的FolderInfo 子目录对象的名字
	 * @return 获取到的FolderInfo对象,如果没有找到返回null
	 * */
	public FolderInfo getChildFolderByName(String childFolderName){
		for(FolderInfo folder:childFolders){
			if(folder.getName().equalsIgnoreCase(childFolderName))
				return folder;
		}
		return null;
	}
	/**读取该目录的子目录和page文件信息
	 * <li>读取子目录信息和page信息</li>
	 * <li></li>
	 * <><>
	 * */
	public void getInfoFromDisk(){
		MyDirectoryOperation directoryOperation=new MyDirectoryOperation(absolutePath);
		ArrayList<File>directories=directoryOperation.getDirectorys();
		if(!isEmpty())
			return;
		for(File directory:directories){
			if(isFolder(directory.getAbsolutePath())){
				FolderInfo folder=new FolderInfo(directory.getName());
				folder.setParentFolder(this);
				this.childFolders.add(folder);
			}else{
				PageInfo page=new PageInfo(directory.getName(), directory.getName());
				page.setParentFolder(this);
				this.pages.add(page);
			}
		}
	}
	/**判断此File对象是否是FolderInfo对象代表的目录，判断的依据是：
	 * <li>该目录下是否有.java文件，如果有的话，则不是目录文件，是page文件</li>
	 * <li>如果没有.java文件则是目录文件</li>
	 * */
	private boolean isFolder(String filePath){
		MyDirectoryOperation directory=new MyDirectoryOperation(filePath);
		ArrayList<File>files=directory.getChildFiles();
		if(files.size()==0)return true;
		else{
			for(File file:files){
				if(file.getAbsolutePath().endsWith(".java"))
					return false;
			}
		}
		return true;
	}
	/**调试的时候用到，打印信息*/
	public void outPutInfo(){
		System.out.println("name:"+name);
		System.out.println("absolutePath:"+absolutePath);
		System.out.println("directoryPath:"+directoryPath);
		if(parentFolder==null)
			System.out.println("为null，应该为pageStore!");
		else
			System.out.println(parentFolder.name);
		for(FolderInfo folder:childFolders){
			folder.outPutInfo();
		}for(PageInfo page:pages){
			page.outPutInfo();
		}
	}
	public FolderInfo clone(){
		FolderInfo clone=new FolderInfo(name);
		clone.setParentFolder(parentFolder);
		for(FolderInfo folder:childFolders){
			clone.childFolders.add(folder.clone());
		}for(PageInfo page:pages){
			clone.pages.add(page.clone());
		}
		return clone;
	}
}
