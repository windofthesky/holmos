package pagestore.tianya;

import java.io.File;
import java.util.ArrayList;

import Holmos.webtest.basetools.file.MyFileOperation;

public class HuitieContent {
	private String dirPath=null;
	public HuitieContent(String dirPath){
		this.dirPath=dirPath;
	}
	public ArrayList<ArrayList<String>>getContents(){
		File dir=new File(dirPath);
		File[]huitiefiles=dir.listFiles();
		ArrayList<ArrayList<String>>contents=new ArrayList<ArrayList<String>>();
		for(File file:huitiefiles){
			MyFileOperation operation=new MyFileOperation(file.getAbsolutePath());
			contents.add(operation.getFileContent());
		}
		return contents;
	}
	public void outPutContent(ArrayList<String>content){
		for(String str:content){
			System.out.println(str);
		}
	}
}
