package Holmos.Holmos.plug;

public class IdCreater {
	/**元素的id 就是其创建时间*/
	public static long getId(){
		return System.currentTimeMillis();
	}
}
