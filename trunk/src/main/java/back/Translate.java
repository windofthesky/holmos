package back;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Translate {
	public static void main(String[] args) {
		Translate.multiFile();
	}

	public static void singleFile() {
		// 读取文件内容
		String path = "E:/照片/贵州照片/";
		FileOutputStream fos = null;
		OutputStreamWriter osw = null;

		try {
			File file = new File(path);
			File[] tempList = file.listFiles();
			fos = new FileOutputStream("d:/aa/");
			osw = new OutputStreamWriter(fos, "GB2312");
			System.out.println("该目录下对象个数：" + tempList.length);
			for (int i = 0; i < tempList.length; i++) {
				if (tempList[i].isFile()) {
					System.out.println("文     件：" + tempList[i]);
					// read file
					if (tempList[i].exists()) {
						FileInputStream fi = new FileInputStream(tempList[i]);
						InputStreamReader isr = new InputStreamReader(fi,
								"utf-8");
						BufferedReader bfin = new BufferedReader(isr);
						String rLine = "";
						while ((rLine = bfin.readLine()) != null) {
							// write file
							osw.write(rLine + "\n");
							osw.flush();
						}
						bfin.close();
						isr.close();
					}
				}
				if (tempList[i].isDirectory()) {
					System.out.println("文件夹：" + tempList[i]);
				}
			}
			osw.close();
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void multiFile() {
		// 读取文件内容
		String path = "E:/照片/贵州照片/";
		FileOutputStream fos = null;
		OutputStreamWriter osw = null;

		try {
			File file = new File(path);
			File[] tempList = file.listFiles();
			System.out.println("该目录下对象个数：" + tempList.length);
			for (int i = 0; i < tempList.length; i++) {
				if (tempList[i].isFile()) {
					System.out.println("文     件：" + tempList[i]);
					fos = new FileOutputStream("D:/cmis/result/"
							+ tempList[i].getName());
					osw = new OutputStreamWriter(fos, "GB2312");

					// read file
					if (tempList[i].exists()) {
						FileInputStream fi = new FileInputStream(tempList[i]);
						InputStreamReader isr = new InputStreamReader(fi,
								"utf-8");
						BufferedReader bfin = new BufferedReader(isr);
						String rLine = "";
						while ((rLine = bfin.readLine()) != null) {
							// write file
							osw.write(rLine + "\n");
							osw.flush();
						}
						bfin.close();
						isr.close();
					}
					osw.close();
					fos.close();

				}
				if (tempList[i].isDirectory()) {
					System.out.println("文件夹：" + tempList[i]);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}