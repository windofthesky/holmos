package back;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class ReadFile {
	public ReadFile() {
	}

	/**
	 * 读取某个文件夹下的所有文件
	 */
	public static boolean readfile(String filepath)
			throws FileNotFoundException, IOException {
		try {
			File file = new File(filepath);
			if (!file.isDirectory()) {
				System.out.println("文件");
				System.out.println("path=" + file.getPath());
				//System.out.println("absolutepath=" + file.getAbsolutePath());
				//System.out.println("name=" + file.getName());

			} else if (file.isDirectory()) {
				System.out.println("文件夹");
				String[] filelist = file.list();
				for (int i = 0; i < filelist.length; i++) {
					File readfile = new File(filepath + "\\" + filelist[i]);
					if (!readfile.isDirectory()) {
						System.out.println("path=" + readfile.getPath());
						//System.out.println("absolutepath="
						//		+ readfile.getAbsolutePath());
						//System.out.println("name=" + readfile.getName());

					} else if (readfile.isDirectory()) {
						readfile(filepath + "\\" + filelist[i]);
					}
				}

			}

		} catch (FileNotFoundException e) {
			System.out.println("readfile()   Exception:" + e.getMessage());
		}
		return true;
	}
	
	public static List<String> readfileCache(String filepath) {
		List<String> list = new ArrayList<String>();
		File file = new File(filepath);
		if (!file.isDirectory()) {
			list.add(file.getName());
		} else if (file.isDirectory()) {
			String[] filelist = file.list();
			for (String string : filelist) {
				File readfile = new File(filepath + "/" + string);
				list.add(readfile.getName());
			}
		}
		return list;
	}
	
	public static void main(String[] args) throws FileNotFoundException, IOException {
		List<String> list = ReadFile.readfileCache("E:/照片/贵州照片/");
		System.out.println("------------------------------");
		for (String e : list) {
			System.out.println(e);
		}
	}
}