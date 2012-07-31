package Holmos.Holmos.plug.PSM.element;

import java.util.ArrayList;

import Holmos.Holmos.innerTools.MyFileOperation;
import Holmos.Holmos.plug.PSM.INFOTYPE;
import Holmos.Holmos.plug.PSM.PSMTool;
import Holmos.Holmos.plug.PSM.VariableInfo;
import Holmos.Holmos.plug.PSM.container.ContainerInfo;
import Holmos.Holmos.plug.PSM.container.PageInfo;

public class ElementInfo extends VariableInfo{

	public ElementInfo(String name, String comment, INFOTYPE type) {
		super(name, comment, type);
	}
	public void rename(String newName){
		MyFileOperation fileOperation=null;
		if(parentContainer instanceof PageInfo){
			fileOperation=((PageInfo)parentContainer).getFileOperationRobot();
		}else if(parentContainer instanceof ContainerInfo){
			fileOperation=((ContainerInfo)parentContainer).getFileOperationRobot();
		}
		ArrayList<String>fileContent=fileOperation.getFileContent();
		boolean findElement=false;
		for(int i=0;i<fileContent.size();i++){
			if(fileContent.get(i).contains(" "+name+" ")){
				fileContent.set(i, fileContent.get(i).replace(" "+name+" ", " "+newName+" "));
				findElement=true;
			}
			if(findElement&&fileContent.get(i).contains(name+".")){
				fileContent.set(i, fileContent.get(i).replace(name+".", newName+"."));
			}
			if(findElement&&fileContent.get(i).contains("}")){
				break;
			}
		}
	}
	/**更改此Element 更改为newElement
	 * <li>改名</li>
	 * <li>该类型</li>
	 * <li>改comment</li>
	 * <li>修改locator信息</li>
	 * 修改的步骤如下:
	 * <li>检查newElement名字是否合法</li>
	 * <li>检查修改后的名字是否在其父容器中出现(在名字和原来的元素不一样的情况才做这个检查)</li>
	 * <li>修改其父容器中的内容信息</li>
	 * <li>修改其父容器中的elements信息</li>
	 * 注意:
	 * <li>不能将元素类型修改为Container类型，不提供支持</li>
	 * */
	public void changeToNewElement(ElementInfo newElement){
		if(!PSMTool.isValid(newElement.name)){
			return ;
		}
		if(isSameAs(newElement))return;
		if(parentContainer instanceof PageInfo){
			((PageInfo)parentContainer).removeElement(name);
			((PageInfo)parentContainer).addElementInfo(newElement);
		}else{
			((ContainerInfo)parentContainer).removeElement(name);
			((ContainerInfo)parentContainer).addElementInfo(newElement);
		}

	}
	/**调试的时候用到*/
	public void outPutInfo() {
		System.out.println("name:"+name);
		System.out.println("comment:"+comment);
		if(parentContainer instanceof PageInfo){
			System.out.println("parentFolder:"+parentContainer==null?null:((PageInfo) parentContainer).getName());
		}else{
			System.out.println("parentFolder:"+parentContainer==null?null:((ContainerInfo) parentContainer).getName());
			
		}
	}
	@Override
	public ElementInfo clone(){
		ElementInfo clone=new ElementInfo(name, comment, type);
		clone.setParentContainer(parentContainer);
		clone.setLocatorInfo(locatorInfo);
		return clone;
	}
}
