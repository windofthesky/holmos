package back;

import java.io.File;
import java.util.ArrayList;

public class ListAll {
	// �����ж�Ŀ¼���ļ���Ĳ��
	private static int	time;

	// �ݹ�ķ���
	public static void deepList(File file) {
		if (file.isFile() || 0 == file.listFiles().length) {
			return;
		} else {
			File[] files = file.listFiles();

			files = sort(files);

			for (File f : files) {
				StringBuffer output = new StringBuffer();

				if (f.isFile()) {
					output.append(getTabs(time));
					output.append(f.getName());
				} else {
					output.append(getTabs(time));
					output.append(f.getName());
					output.append("\\");
				}

				System.out.println(output);

				if (f.isDirectory()) {
					time++;

					deepList(f);

					time--;
				}
			}
		}
	}

	// �����ļ����飬ʹ��Ŀ¼�����ļ�֮ǰ
	private static File[] sort(File[] files) {
		ArrayList<File> sorted = new ArrayList<File>();

		// Ѱ�ҵ����е�Ŀ¼
		for (File f : files) {
			if (f.isDirectory()) {
				sorted.add(f);
			}
		}
		// Ѱ�ҵ����е��ļ�
		for (File f : files) {
			if (f.isFile()) {
				sorted.add(f);
			}
		}

		return sorted.toArray(new File[files.length]);
	}

	// �ж���Ҫ�Ӷ��� tab�ķ���
	private static String getTabs(int time) {
		StringBuffer buffer = new StringBuffer();

		for (int i = 0; i < time; i++) {
			buffer.append("\t");
		}

		return buffer.toString();
	}

	public static void main(String[] args) {
		File file = new File("D:\\������Ӿ�");

		deepList(file);
	}
}