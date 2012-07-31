package Holmos.webtest.basetools.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**目录操作类
 * @author 吴银龙(15857164387)
 * */
public class MyDirectoryOperation {
	private String absolutePath;
	public MyDirectoryOperation(String absolutePath){
		this.absolutePath=absolutePath;
	}
	public ArrayList<File> getChildFiles() {
		ArrayList<File>childFiles=new ArrayList<File>();
		for(File file:new File(absolutePath).listFiles()){
			if(file.isFile())
				childFiles.add(file);
		}
		return childFiles;
	}
	public ArrayList<File> getDirectorys() {
		ArrayList<File>childFolders=new ArrayList<File>();
		for(File file:new File(absolutePath).listFiles()){
			if(file.isDirectory())
				childFolders.add(file);
		}
		return childFolders;
	}
	public String getAbsolutePath() {
		return absolutePath;
	}
	/**递归深度copy
	 * @param sourceDir 目标待拷贝的目录
	 * @param destinationDir 要拷贝到的地方，也是一个目录，此目录将作为目标拷贝目录的父目录
	 * @throws IOException 
	 * */
	public static void copyTo(File destination,File source){
		if(!destination.exists()){
			destination.mkdir();
		}
		for(File file:source.listFiles()){
			if(file.isFile()){
				copyFile(file, new File(destination.getAbsolutePath()+"\\"+file.getName()));
			}else{
				copyTo(new File(destination.getAbsolutePath()+"\\"+file.getName()),file);
			}
		}
	}
	
	/**拷贝文件的工具类
	 * @param sourceFile 源文件
	 * @param destinationFile 目标文件，拷贝到的文件
	 * */
	private static void copyFile(File sourceFile,File destinationFile){
		try {
			if(sourceFile.isDirectory())
				return;
			FileInputStream finput=new FileInputStream(sourceFile);
			if(!destinationFile.exists())
				destinationFile.createNewFile();
			FileOutputStream foutput=new FileOutputStream(destinationFile);
			byte[]buffered=new byte[1024];
			while(finput.read(buffered)!=-1){
				foutput.write(buffered);
			}
			finput.close();
			foutput.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
