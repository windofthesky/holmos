package Holmos.Holmos.plug.PSM.container;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.autosense.browser.exchange.util.CreateReturnInfo;
import cn.autosense.browser.exchange.util.ErrorType;

import Holmos.Holmos.innerTools.MyDirectoryOperation;
import Holmos.Holmos.innerTools.MyFile;
import Holmos.Holmos.plug.PSM.INFOTYPE;
import Holmos.Holmos.plug.PSM.PSMTool;
import Holmos.Holmos.plug.PSM.VariableInfo;
import Holmos.Holmos.plug.PSM.element.ElementInfo;

public class FrameInfo extends ContainerInfo{
	protected List<FrameInfo>frames=new ArrayList<FrameInfo>();
	public FrameInfo(String name, String comment) {
		super(name, comment, VarType.FRAME);
	}
	/**在此FrameInfo里面添加一个变量,名字为frameName,然后并且在框架默认的位置新建一个名字为frameName的Frame.java文件,里面没有包括任何元素:
	 * <li>检查frameName是否合法,创建一个新的Frame.java文件,在框架指定的位置</li>
	 * <li>检查是否已经存在</li>
	 * <li>更新当前FrameInfo的Page.java文件里面的内容</li>
	 * <li>更新当前FrameInfo对象里面的frames信息</li>
	 * @param frameName 在当前pageInfo下面新建Frame的名字
	 * @return
	 * */
	public CreateReturnInfo addFrameInfo(String frameName,String comment){
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
	/**检查该FrameInfo对象里面是否具有名字为frameName的frame
	 * <li>检查frameName是否合法,并将frameName的第一个字母大写</li>
	 * <li>扫描frames</li>
	 * @param frameName 在当前FrameInfo下面新建Frame的名字
	 * */
	public boolean isFrameInfoExist(String frameName){
		for(FrameInfo frame:this.frames){
			if(frame.getName().equalsIgnoreCase(frameName)){
				return true;
			}
		}
		return false;
	}
	/**移除此FrameInfo实例下的名字为frameName的frameInfo实例
	 * <li>检查名字是否合法</li>
	 * <li>扫描frames,找到匹配项了之后删除Frame.java文件，一并删除它的子信息</li>
	 * <li>更新此FrameInfo的frames信息</li>
	 * @param frameName 待移除的frameInfo的名字
	 * */
	public void removeFrame(String frameName){
		boolean findFrame=false;
		if(!isFrameInfoExist((frameName))){
			return;
		}
		
		//删文件
		for(FrameInfo frame:frames){
			if(frame.getName().equalsIgnoreCase(frameName)){
				findFrame=true;
				frame.getJavalocatorManager().removeLocatorInfo();
				MyFile.deleteDirectory(frame.directoryPath);
				this.frames.remove(frame);
				break;
			}
		}
		//删除本文件信息
		if(findFrame)
			deleteInfo(frameName);
	}
	/**获取此FrameInfo下与frameName匹配的frameInfo实例
	 * <li>检查名字是否合法，并将其第一个字母大写</li>
	 * <li>判断是否存在,扫描</li>
	 * @param frameName 要获取的frameInfo实例的名字
	 * @return 获取到的实例，如果没有匹配项，返回null
	 * */
	public FrameInfo getFrame(String frameName){
		for(FrameInfo frame:frames){
			if(frame.getName().equalsIgnoreCase(frameName)){
				return frame;
			}
		}
		return null;
	}
	/**将frame对象拷贝到当前FrameInfo下:
	 * <li>实现硬盘信息拷贝,并将frame实例里面所有的已有的信息全部拷贝</li>
	 * <li>递归修改此目录下所有的实例对象的包信息,absolutePath,directoryPath等信息</li>
	 * <li>修改frame对象的信息absolutePath,directoryPath信息</li>
	 * <li>修改当前对象的信息</li>
	 * @param frame 要拷贝的frame对象
	 * */
	public void pasteFrameInfo(FrameInfo frame){
		//文件拷贝
		MyDirectoryOperation.copyTo(new File(directoryPath+"//"+frame.getName()), new File(directoryPath));
		FrameInfo frameClone=(FrameInfo) frame.clone();
		addNewContainerInfo(frameClone);
		String oldPackagePath=frame.packagePath;
		String newPackagePath=packagePath+"."+frame.getName();
		frameClone.changeInfo(this,oldPackagePath,newPackagePath);
		this.frames.add(frameClone);
	}
	/**已知该对象,从磁盘中获得该ContainerInfo下的所有containers和elements信息,从locator存放地获取locator信息:
	 * <li>只获得此ContainerInfo下一级信息,不会递归获取,主要是为了实现懒加载功能</li>
	 * <li>此方法调用后更新了此ContainerInfo下所有containers和elements信息,从locator存放地获取locator信息</li>
	 * */
	public void getInfoFromDisk(){
		ArrayList<String>pageContent=fileOperationRobot.getFileContent();
		ArrayList<ArrayList<String>>variablesContent=PSMTool.getElementContent(pageContent);
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
	public List<FrameInfo> getFrames() {
		return frames;
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
		}for(FrameInfo frame:frames){
			frame.outPutInfo();
		}for(ElementInfo element:elements){
			element.outPutInfo();
		}
	}
}
