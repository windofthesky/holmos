package back;

import java.io.File;

public class Show {
	public static void show(File dir){
		File [] fs=dir.listFiles();
		for(int i=0;i<fs.length;i++){
			if(fs[i].isFile()){
				System.out.println(fs[i].getAbsolutePath());
			}
			if(fs[i].isDirectory()){
				show(fs[i]);
			}
		}
		
	}
	public static void main(String[] args) {
		File  dir=new File("D:\\������Ӿ�");
		show(dir);
	}
}