package Holmos.webtest.basetools.file;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
/**
 * @author 吴银龙(15857164387)
 * */
public class MyWriter {
	private BufferedWriter writer;
	public MyWriter(String filePath){
		try {
			BufferedWriter writerTemp=new BufferedWriter(new FileWriter(new File(filePath)));
			this.writer=writerTemp;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void writeLine(String tab,String content){
		try {
			this.writer.write(tab+content+"\r\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void flush(){
		try {
			this.writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void close(){
		try {
			this.writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
