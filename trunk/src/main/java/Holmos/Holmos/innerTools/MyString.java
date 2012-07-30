package Holmos.Holmos.innerTools;
/**分析文件的时候对String的分析功能
 * @author 吴银龙(15857164387)
 * 
 * */
public class MyString {
	public String value;
	public MyString(String value){
		this.value=value.trim();
	}
	/**提取从start开始到end-1的字符窜*/
	public String pickUpStr(int start,int end){
		StringBuilder val=new StringBuilder(this.value);
		if(start<0)return null;
		if(start>=val.length()||end<=0)return null;
		if(end>val.length())return null;
		if(start>end)return null;
		return val.substring(start, end);
	}
	/**抽取从startSuffix开始到endSuffix的字符窜，都是第一次出现的字符窜
	 * 如果startSuffix=endSuffix，那么endSuffix就是第二次出现的这些字符
	 * 如果startSuffix=null,那么返回从开始到第一个endSuffix字符窜位置的所有字符
	 * 如果endSuffix=null,那么返回从第一个startSuffix到最后的所有字符*/
	public String pickUpStr(String startSuffix,String endSuffix){
		int start=0,end=0;
		if(startSuffix==null){
			start=0;
		}else{
			start=value.indexOf(startSuffix)+startSuffix.length();
		}
		if(endSuffix==null){
			end=this.value.length();
		}else{
			end=start+value.substring(start).indexOf(endSuffix);
		}
		return pickUpStr(start, end);
	}
	
	public boolean includeAnd(String... args){
		for(String arg:args){
			if(value.contains(arg))
				continue;
			return false;
		}
		return true;
	}
	public boolean includeOr(String...args){
		for(String arg:args){
			if(value.contains(arg))
				return true;
			continue;
		}
		return false;
	}
	/**不含其中任何一个*/
	public boolean notIncludeAnd(String...args){
		for(String arg:args){
			if(value.contains(arg))
				return false;
			continue;
		}
		return true;
	}
	/**不含有其中任意一个就行*/
	public boolean notIncludeOr(String...args){
		for(String arg:args){
			if(value.contains(arg))
				continue;
			return true;
		}
		return false;
	}
	/***取出此对象里面还有的args字符窜，如果没有，则不去除，有的话，则去除,如果含有多个，那么一并去除*/
	public String subString(String ...args){
		for(String arg:args){
			while(value.contains(arg)){
				int start=value.indexOf(arg);
				value=value.substring(0, start)+value.substring(start+arg.length(), value.length());
			}
		}return value;
	}
	/**覆盖父类toString方法*/
	public String toString(){
		return this.value;
	}
	/**打印出当前的value*/
	public void outValue(){
		System.out.println(this.toString());
	}
	/**与其中一个相同，则返回true*/
	public boolean equalsOr(String ...args){
		for(String arg:args){
			if(this.value.equalsIgnoreCase(arg))
				return true;
		}return false;
	}
	/**以其中任何一个结尾，返回true*/
	public boolean endsWithOr(String ...args){
		for(String arg:args){
			if(this.value.endsWith(arg))
				return true;
		}return false;
	}
	public void replace(String oldSuffix,String newSuffix){
		this.value=this.value.replace(oldSuffix, newSuffix);
	}
	public String[] split(String splitStr){
		return this.value.split(splitStr);
	}
	public String getSplitPart(int index,String splitStr){
		String[] splitResult=split(splitStr);
		if(index>=splitResult.length){
			return null;
		}return splitResult[index];
	}
}
