package Holmos.basetools.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
/**
 * @author 吴银龙(15857164387)
 * */
public class MyFile {
	private String filePath;
	File file;
	public MyFile(String filePath){
		this.filePath=filePath;
		this.file=new File(filePath);
		if(!file.exists()){
			createFile(filePath);
		}
	}
	public MyFile(File file){
		this.filePath=file.getAbsolutePath();
		this.file=file;
	}
	/**根据指定文件地址新建文件，如果有多级目录没有建立，那么建立中间目录*/
	public static boolean createDictory(String path){
		if(!new File(path).exists()){
			return new File(path).mkdirs();
		}return true;
	}
	/**
	 * 跨目录级别建立文件
	 * */
	public static boolean createFile(String path){
		if(!new File(path).exists()){
			StringBuilder dictoryPath=new StringBuilder(path);
			dictoryPath.delete(path.lastIndexOf('\\'), path.length());
			createDictory(dictoryPath.toString());
			try {
				new File(path).createNewFile();
			} catch (IOException e) {
				System.out.println("文件创建失败!");
			}
		}return true;
	}
	public static boolean exist(String path){
		return new File(path).exists();
	}
	public String getFileContentByString(){
		List<String>fileContentList=getFileContentByList();
		StringBuilder content=new StringBuilder();
		for(String str:fileContentList){
			content.append(str);
		}return content.toString();
	}
	public ArrayList<String>getFileContentByList(){
		if(!new File(filePath).exists())
			return null;
		ArrayList<String>fileContent=new ArrayList<String>();
		try {
			BufferedReader reader=new BufferedReader(new FileReader(new File(filePath)));
			String temp;
			while((temp=reader.readLine())!=null){
				fileContent.add(temp.trim());
			}
			reader.close();
			return fileContent;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	public void setFileContent(ArrayList<String>content){
		MyWriter writer=new MyWriter(filePath);
		StringBuilder tab=new StringBuilder();
		for(int i=0;i<content.size();i++){
			MyString line=new MyString(content.get(i));
			if(line.includeAnd("}"))
				tab.delete(0, 4);
			writer.writeLine(tab.toString(), line.value);
			if(line.includeAnd("{"))
				tab.append("    ");
		}
		writer.flush();writer.close();
	}
	/**删除目录及目录下所有文件和文件夹*/
	public static void deleteDirectory(String directoryPath){
		File file = new File(directoryPath);
		   if(file.isDirectory()){ //是文件夹
			    File temp = null;
			    String [] childsFile = file.list(); //获得该目录下的子文件及子文件夹
			    for(String s : childsFile){
			     //检查folderPath是不是以"\"节尾
				     if(directoryPath.endsWith(File.separator)){
				    	 temp = new File(directoryPath+s);
				     }else{
				    	 temp = new File(directoryPath+File.separator+s);
				     }
				     //delete file
				     if(temp != null && temp.isFile()){  //是文件
				    	 System.out.println(temp.delete());
				    	 System.out.println("delete of the fileName: " + temp.getAbsolutePath());
				     }else if(temp != null && temp.isDirectory()){  //是文件夹
				    	 deleteDirectory(temp.getAbsolutePath());
				     }
			    }
			    file.delete();
		   }else if(file.isFile()){ //是文件
			   file.delete();
		   }
	}
	public boolean rename(String newName){
		return this.file.renameTo(new File(this.file.getParent()+"\\"+newName));
	}
}
