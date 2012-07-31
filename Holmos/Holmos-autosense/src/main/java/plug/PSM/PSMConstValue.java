package Holmos.Holmos.plug.PSM;

public class PSMConstValue {
	/**系统保留字*/
	public static final String[] keywords={"page","subpage","widget","collection","virtualsubpage","frame","element",
		"button","textfield","label","link","alipayelement","checkbox","radiobutton","table"};
	/*系统保留字*/
	/**java保留字*/
	public static final String[] JAVAKEYWORDS=new String[]{"class","extends","implements","interface","import","package",
		"byte","boolean","char","double","int","long","float","short","flase","ture","null","break","case",
		"continue","default","do","else","for","if","return","switch","while","","catch","finally","throw","throws",
		"try","abstract","final","native","private","protected","public","static","synchronilzed","transient",
		"volatitle","instanceof","","new","this","supper","","void","","const","goto"};
	/*java保留字*/
	/**系统保留字*/
	public static final String[] OBIGATECHAR=new String[]{"subpage","page","collection","virtualsubpage","element","variable","frame","folder","widget"};
	/**变量名错误的返回值*/
	public static final int CORRECT=0;
	public static final int NULL=-1;//空
	public static final int BLANK=-2;//空字符窜
	public static final int ILLEGALCHAR=-3;//包含非法字符
	public static final int TOOLONG=-4;//太长
	public static final int TOOSHORT=-5;//太短
	public static final int INCLUDEJAVAKEYWORD=-6;//包含关键字
	public static final int INCLUDEOBIGATEKEYWORD=-7;//包含预留字符窜
	public static final int DULPLICATION=-8;//变量重复
	public static final char[] ILLEGALCHARS=new char[]{'~','`','!','@','#','$','%','^',
		'<','>',',','.','/','.','?',';',':','"','\'','{','}','[',']','\\','|','=','+','-','_',')','(','*','&',
		'！','·','￥','（','）','—','…','【','】','、','‘','”','’','”','；','：','？','、','》','《','，','。'};
}
