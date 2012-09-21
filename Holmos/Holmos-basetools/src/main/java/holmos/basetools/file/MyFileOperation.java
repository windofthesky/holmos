package holmos.basetools.file;

import java.io.File;
import java.util.ArrayList;

import org.apache.log4j.Logger;
/**
 * @author 吴银龙(15857164387)
 * */
public class MyFileOperation {
	private String absolutePath;
	public String getAbsolutePath() {
		return absolutePath;
	}
	public void setAbsolutePath(String absolutePath) {
		this.absolutePath = absolutePath;
	}
	/**这个页面操作的日志记录器*/
	protected static Logger logger=Logger.getLogger(MyFileOperation.class.getName());
	public MyFileOperation(String absolutePath){
		this.absolutePath=absolutePath;
	}
	public boolean isExist(){
		return new File(absolutePath).exists();
	}
	public static void createFile(String path){
		MyFile.createFile(path);
	}
	public ArrayList<String>getFileContent(){
		return new MyFile(absolutePath).getFileContentByList();
	}
	public void setFileContent(ArrayList<String>fileContent){
		if(fileContent!=null){
			new MyFile(absolutePath).setFileContent(fileContent);
		}
	}
}
