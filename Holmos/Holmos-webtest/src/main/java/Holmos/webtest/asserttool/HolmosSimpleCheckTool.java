package Holmos.webtest.asserttool;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import Holmos.webtest.basetools.HolmosBaseTools;
import Holmos.webtest.exceptions.HolmosFailedError;
/**
 * @author 吴银龙(15857164387)
 * */
public class HolmosSimpleCheckTool {
	private static Logger logger=Logger.getLogger(HolmosSimpleCheckTool.class.getName());
	static{
		HolmosBaseTools.configLogProperties();
	}
	
	public static void assertTrue(boolean condition)throws HolmosFailedError{
		StringBuilder msg=new StringBuilder("assertTrue(boolean condition):\n");
		if(condition){
			msg.append("条件校验正确！实际值为true！");
			logger.info(msg);
		}else{
			msg.append("条件校验错误！实际值为false!");
			logger.error(msg);
			throw new HolmosFailedError(msg.toString());
		}
	}
	public static void assertTrue(boolean condition,String message)throws HolmosFailedError{
		StringBuilder msg=new StringBuilder("assertTrue(boolean condition,String message):\n");
		if(condition){
			msg.append("条件校验正确！实际值为true！");
			logger.info(msg);
		}else{
			msg.append(message+":");
			msg.append("条件校验错误！实际值为false!");
			logger.error(msg);
			throw new HolmosFailedError(msg.toString());
		}
	}
	public static void assertFalse(boolean condition)throws HolmosFailedError{
		StringBuilder msg=new StringBuilder("assertFalse(boolean condition):\n");
		if(!condition){
			msg.append("条件校验正确！实际值为false！");
			logger.info(msg);
		}else{
			msg.append("条件校验错误！实际值为true!");
			logger.error(msg);
			throw new HolmosFailedError(msg.toString());
		}
	}
	public static void assertFalse(boolean condition,String message)throws HolmosFailedError{
		StringBuilder msg=new StringBuilder("assertFalse(boolean condition,String message):\n");
		if(!condition){
			msg.append("条件校验正确！实际值为false！");
			logger.info(msg);
		}else{
			msg.append(message+":");
			msg.append("条件校验错误！实际值为true!");
			logger.error(msg);
			throw new HolmosFailedError(msg.toString());
		}
	}
	public static void assertEqual(boolean actual,boolean expect)throws HolmosFailedError{
		StringBuilder msg=new StringBuilder("assertEqual(boolean actual,boolean expect):\n");
		if(actual==expect){
			msg.append("校验正确！预期值和实际值均为:"+actual);
			logger.info(msg);
		}else{
			msg.append("校验失败！预期值为："+expect+"实际值为："+actual);
			logger.error(msg);
			throw new HolmosFailedError(msg.toString());
		}
	}
	public static void assertEqual(boolean actual,boolean expect,String message)throws HolmosFailedError{
		StringBuilder msg=new StringBuilder("assertEqual(boolean actual,boolean expect,String message):\n");
		if(actual==expect){
			msg.append("校验正确！预期值和实际值均为:"+actual);
			logger.info(msg);
		}else{
			msg.append(message+":");
			msg.append("校验失败！预期值为："+expect+"实际值为："+actual);
			logger.error(msg);
			throw new HolmosFailedError(msg.toString());
		}
	}
	public static void assertEqual(double actual,double expect)throws HolmosFailedError{
		StringBuilder msg=new StringBuilder("assertEqual(double actual,double expect):\n");
		if(actual==expect){
			msg.append("校验正确！预期值和实际值均为:"+actual);
			logger.info(msg);
		}else{
			msg.append("校验失败！预期值为："+expect+"实际值为："+actual);
			logger.error(msg);
			throw new HolmosFailedError(msg.toString());
		}
	}
	public static void assertEqual(double actual,double expect,String message)throws HolmosFailedError{
		StringBuilder msg=new StringBuilder("assertEqual(double actual,double expect,String message):\n");
		if(actual==expect){
			msg.append("校验正确！预期值和实际值均为:"+actual);
			logger.info(msg);
		}else{
			msg.append(message+":");
			msg.append("校验失败！预期值为："+expect+"实际值为："+actual);
			logger.error(msg);
			throw new HolmosFailedError(msg.toString());
		}
	}
    public static void assertEqual(long actual,long expect)throws HolmosFailedError{
    	StringBuilder msg=new StringBuilder("assertEqual(long actual,long expect):\n");
    	if(actual==expect){
			msg.append("校验正确！预期值和实际值均为:"+actual);
			logger.info(msg);
		}else{
			msg.append("校验失败！预期值为："+expect+"实际值为："+actual);
			logger.error(msg);
			throw new HolmosFailedError(msg.toString());
		}
	}
	public static void assertEqual(long actual,long expect,String message)throws HolmosFailedError{
		StringBuilder msg=new StringBuilder("assertEqual(long actual,long expect,String message):\n");
		if(actual==expect){
			msg.append("校验正确！预期值和实际值均为:"+actual);
			logger.info(msg);
		}else{
			msg.append(message+":");
			msg.append("校验失败！预期值为："+expect+"实际值为："+actual);
			logger.error(msg);
			throw new HolmosFailedError(msg.toString());
		}
	}
	public static void assertEqual(char actual,char expect)throws HolmosFailedError{
		StringBuilder msg=new StringBuilder("assertEqual(char actual,char expect):\n");
		if(actual==expect){
			msg.append("校验正确！预期值和实际值均为:"+actual);
			logger.info(msg);
		}else{
			msg.append("校验失败！预期值为："+expect+"实际值为："+actual);
			logger.error(msg);
			throw new HolmosFailedError(msg.toString());
		}
	}
	public static void assertEqual(char actual,char expect,String message)throws HolmosFailedError{
		StringBuilder msg=new StringBuilder(":\n");
		if(actual==expect){
			msg.append("校验正确！预期值和实际值均为:"+actual);
			logger.info(msg);
		}else{
			msg.append(message+":");
			msg.append("校验失败！预期值为："+expect+"实际值为："+actual);
			logger.error(msg);
			throw new HolmosFailedError(msg.toString());
		}
	}
	public static void assertEqual(String actual,String expect)throws HolmosFailedError{
		StringBuilder msg=new StringBuilder("assertEqual(String actual,String expect):\n");
		if(actual.equalsIgnoreCase(expect)){
			msg.append("校验正确！预期值和实际值均为:"+actual);
			logger.info(msg);
		}else{
			msg.append("校验失败！预期值为："+expect+"实际值为："+actual);
			logger.error(msg);
			throw new HolmosFailedError(msg.toString());
		}
	}
	public static void assertEqual(String actual,String expect,String message)throws HolmosFailedError{
		StringBuilder msg=new StringBuilder("assertEqual(String actual,String expect,String message):\n");
		if(actual.equalsIgnoreCase(expect)){
			msg.append("校验正确！预期值和实际值均为:"+actual);
			logger.info(msg);
		}else{
			msg.append(message+":");
			msg.append("校验失败！预期值为："+expect+"实际值为："+actual);
			logger.error(msg);
			throw new HolmosFailedError(msg.toString());
		}
	}
	public static void assertNotEqual(boolean actual,boolean expect)throws HolmosFailedError{
		StringBuilder msg=new StringBuilder("assertNotEqual(boolean actual,boolean expect):\n");
		if(actual==expect){
			msg.append("校验失败！预期值和实际值均为:"+actual);
			logger.error(msg);
			throw new HolmosFailedError(msg.toString());
		}else{
			msg.append("校验成功！预期值为："+expect+"实际值为："+actual);
			logger.info(msg);
		}
	}
	public static void assertNotEqual(boolean actual,boolean expect,String message)throws HolmosFailedError{
		StringBuilder msg=new StringBuilder("assertNotEqual(boolean actual,boolean expect,String message):\n");
		if(actual==expect){
			msg.append(message+":");
			msg.append("校验失败！预期值和实际值均为:"+actual);
			logger.error(msg);
			throw new HolmosFailedError(msg.toString());
		}else{
			msg.append("校验成功！预期值为："+expect+"实际值为："+actual);
			logger.info(msg);
		}
	}
	public static void assertNotEqual(double actual,double expect)throws HolmosFailedError{
		StringBuilder msg=new StringBuilder("assertNotEqual(double actual,double expect):\n");
		if(actual==expect){
			msg.append("校验失败！预期值和实际值均为:"+actual);
			logger.error(msg);
			throw new HolmosFailedError(msg.toString());
		}else{
			msg.append("校验成功！预期值为："+expect+"实际值为："+actual);
			logger.info(msg);
		}
	}
	public static void assertNotEqual(double actual,double expect,String message)throws HolmosFailedError{
		StringBuilder msg=new StringBuilder("assertNotEqual(double actual,double expect,String message):\n");
		if(actual==expect){
			msg.append(message+":");
			msg.append("校验失败！预期值和实际值均为:"+actual);
			logger.error(msg);
			throw new HolmosFailedError(msg.toString());
		}else{
			msg.append("校验成功！预期值为："+expect+"实际值为："+actual);
			logger.info(msg);
		}
	}
    public static void assertNotEqual(long actual,long expect)throws HolmosFailedError{
    	StringBuilder msg=new StringBuilder("assertNotEqual(long actual,long expect):\n");
    	if(actual==expect){
			msg.append("校验失败！预期值和实际值均为:"+actual);
			logger.error(msg);
			throw new HolmosFailedError(msg.toString());
		}else{
			msg.append("校验成功！预期值为："+expect+"实际值为："+actual);
			logger.info(msg);
		}
	}
	public static void assertNotEqual(long actual,long expect,String message)throws HolmosFailedError{
		StringBuilder msg=new StringBuilder("assertNotEqual(long actual,long expect,String message):\n");
		if(actual==expect){
			msg.append(message+":");
			msg.append("校验失败！预期值和实际值均为:"+actual);
			logger.error(msg);
			throw new HolmosFailedError(msg.toString());
		}else{
			msg.append("校验成功！预期值为："+expect+"实际值为："+actual);
			logger.info(msg);
		}
	}
	public static void assertNotEqual(char actual,char expect)throws HolmosFailedError{
		StringBuilder msg=new StringBuilder("assertNotEqual(char actual,char expect):\n");
		if(actual==expect){
			msg.append("校验失败！预期值和实际值均为:"+actual);
			logger.error(msg);
			throw new HolmosFailedError(msg.toString());
		}else{
			msg.append("校验成功！预期值为："+expect+"实际值为："+actual);
			logger.info(msg);
		}
	}
	public static void assertNotEqual(char actual,char expect,String message)throws HolmosFailedError{
		StringBuilder msg=new StringBuilder("assertNotEqual(char actual,char expect,String message):\n");
		if(actual==expect){
			msg.append(message+":");
			msg.append("校验失败！预期值和实际值均为:"+actual);
			logger.error(msg);
			throw new HolmosFailedError(msg.toString());
		}else{
			msg.append("校验成功！预期值为："+expect+"实际值为："+actual);
			logger.info(msg);
		}
	}
	public static void assertNotEqual(String actual,String expect)throws HolmosFailedError{
		StringBuilder msg=new StringBuilder("assertNotEqual(String actual,String expect):\n");
		if(actual.equalsIgnoreCase(expect)){
			msg.append("校验失败！预期值和实际值均为:"+actual);
			logger.error(msg);
			throw new HolmosFailedError(msg.toString());
		}else{
			msg.append("校验成功！预期值为："+expect+"实际值为："+actual);
			logger.info(msg);
		}
	}
	public static void assertNotEqual(String actual,String expect,String message)throws HolmosFailedError{
		StringBuilder msg=new StringBuilder("assertNotEqual(String actual,String expect,String message):\n");
		if(actual.equalsIgnoreCase(expect)){
			msg.append(message+":");
			msg.append("校验失败！预期值和实际值均为:"+actual);
			logger.error(msg);
			throw new HolmosFailedError(msg.toString());
		}else{
			msg.append("校验成功！预期值为："+expect+"实际值为："+actual);
			logger.info(msg);
		}
	}
	public static void assertInclude(String content,String included)throws HolmosFailedError{
		StringBuilder msg=new StringBuilder("assertInclude(String content,String included):\n");
		if(content.contains(included)){
			msg.append("包含校验正确，校验字符窜为："+content+"\n包含字符窜为："+included);
			logger.info(msg);
		}else{
			msg.append("包含校验失败，校验字符窜为："+content+"\n包含字符窜为："+included);
			logger.error(msg);
			throw new HolmosFailedError(msg.toString());
		}
	}
	public static void assertInclude(String content,String included,String message){
		StringBuilder msg=new StringBuilder("assertInclude(String content,String included,String message):\n");
		if(content.contains(included)){
			msg.append("包含校验正确，校验字符窜为："+content+"\n包含字符窜为："+included);
			logger.info(msg);
		}else{
			msg.append(message+":");
			msg.append("包含校验失败，校验字符窜为："+content+"\n包含字符窜为："+included);
			logger.error(msg);
			throw new HolmosFailedError(msg.toString());
		}
	}
	public static void assertNotInclude(String content,String included){
		StringBuilder msg=new StringBuilder("assertNotInclude(String content,String included):\n");
		if(!content.contains(included)){
			msg.append("包含校验正确，校验字符窜为："+content+"\n包含字符窜为："+included);
			logger.info(msg);
		}else{
			msg.append("包含校验失败，校验字符窜为："+content+"\n包含字符窜为："+included);
			logger.error(msg);
			throw new HolmosFailedError(msg.toString());
		}
	}
	public static void assertNotInclude(String content,String included,String message){
		StringBuilder msg=new StringBuilder("assertNotInclude(String content,String included,String message):\n");
		if(!content.contains(included)){
			msg.append("包含校验正确，校验字符窜为："+content+"\n包含字符窜为："+included);
			logger.info(msg);
		}else{
			msg.append(message+":");
			msg.append("包含校验失败，校验字符窜为："+content+"\n包含字符窜为："+included);
			logger.error(msg);
			throw new HolmosFailedError(msg.toString());
		}
	}
	public static void assertMatch(String regex,String matcher)throws HolmosFailedError{
		StringBuilder msg=new StringBuilder("assertMatch(String actual,String expect):\n");
		if(Pattern.matches(regex, matcher)){
			msg.append("匹配校验成功！待校验的字符窜为:"+matcher+"校验的正则表达式为:"+regex);
			logger.info(msg);
		}else{
			msg.append("匹配校验失败！待校验的字符窜为:"+matcher+"校验的正则表达式为:"+regex);
			logger.error(msg);
			throw new HolmosFailedError(msg.toString());
		}
	}
	public static void assertMatch(String regex,String matcher,String message)throws HolmosFailedError{
		StringBuilder msg=new StringBuilder("assertMatch(String actual,String expect,String message):\n");
		if(Pattern.matches(regex, matcher)){
			msg.append("匹配校验成功！待校验的字符窜为:"+matcher+"校验的正则表达式为:"+regex);
			logger.info(msg);
		}else{
			msg.append(message+":");
			msg.append("匹配校验失败！待校验的字符窜为:"+matcher+"校验的正则表达式为:"+regex);
			logger.error(msg);
			throw new HolmosFailedError(msg.toString());
		}
	}
	public static void assertNotMatch(String regex,String matcher)throws HolmosFailedError{
		StringBuilder msg=new StringBuilder("assertNotMatch(String actual,String expect):\n");
		if(!Pattern.matches(regex, matcher)){
			msg.append("匹配校验成功！待校验的字符窜为:"+matcher+"校验的正则表达式为:"+regex);
			logger.info(msg);
		}else{
			msg.append("匹配校验失败！待校验的字符窜为:"+matcher+"校验的正则表达式为:"+regex);
			logger.error(msg);
			throw new HolmosFailedError(msg.toString());
		}
	}
	public static void assertNotMatch(String regex,String matcher,String message)throws HolmosFailedError{
		StringBuilder msg=new StringBuilder("assertNotMatch(String actual,String expect,String message):\n");
		if(Pattern.matches(regex, matcher)){
			msg.append("匹配校验成功！待校验的字符窜为:"+matcher+"校验的正则表达式为:"+regex);
			logger.info(msg);
		}else{
			msg.append(message+":");
			msg.append("匹配校验失败！待校验的字符窜为:"+matcher+"校验的正则表达式为:"+regex);
			logger.error(msg);
			throw new HolmosFailedError(msg.toString());
		}
	}
	
	public static void assertStartWith(String content,String prefix)throws HolmosFailedError{
		StringBuilder msg=new StringBuilder("verifyStartWith(String content,String prefix)");
		if(content.startsWith(prefix)){
			msg.append("前缀匹配校验成功！待校验的字符窜为:"+content+"校验的前缀表达式为:"+prefix);
			logger.info(msg);
		}else{
			msg.append("前缀匹配校验失败！待校验的字符窜为:"+content+"校验的前缀表达式为:"+prefix);
			throw new HolmosFailedError(msg.toString());
		}
	}
	public static void assertEndWith(String content,String endfix)throws HolmosFailedError{
		StringBuilder msg=new StringBuilder("verifyStartWith(String content,String prefix)");
		if(content.startsWith(content)){
			msg.append("后缀匹配校验成功！待校验的字符窜为:"+content+"校验的后缀表达式为:"+content);
			logger.info(msg);
		}else{
			msg.append("后缀匹配校验失败！待校验的字符窜为:"+content+"校验的后缀表达式为:"+content);
			throw new HolmosFailedError(msg.toString());
		}
	}
	
	public static void verifyTrue(boolean condition){
		StringBuilder msg=new StringBuilder("verifyTrue(boolean condition):\n");
		if(condition){
			msg.append("条件校验正确！实际值为true！");
			logger.info(msg);
		}else{
			msg.append("条件校验错误！实际值为false!");
			logger.error(msg);
			
		}
	}
	public static void verifyTrue(boolean condition,String message){
		StringBuilder msg=new StringBuilder("verifyTrue(boolean condition,String message):\n");
		if(condition){
			msg.append("条件校验正确！实际值为true！");
			logger.info(msg);
		}else{
			msg.append(message+":");
			msg.append("条件校验错误！实际值为false!");
			logger.error(msg);
			
		}
	}
	public static void verifyFalse(boolean condition){
		StringBuilder msg=new StringBuilder("verifyFalse(boolean condition):\n");
		if(!condition){
			msg.append("条件校验正确！实际值为false！");
			logger.info(msg);
		}else{
			msg.append("条件校验错误！实际值为true!");
			logger.error(msg);
			
		}
	}
	public static void verifyFalse(boolean condition,String message){
		StringBuilder msg=new StringBuilder("verifyFalse(boolean condition,String message):\n");
		if(!condition){
			msg.append("条件校验正确！实际值为false！");
			logger.info(msg);
		}else{
			msg.append(message+":");
			msg.append("条件校验错误！实际值为true!");
			logger.error(msg);
			
		}
	}
	public static void verifyEqual(boolean actual,boolean expect){
		StringBuilder msg=new StringBuilder("verifyEqual(boolean actual,boolean expect):\n");
		if(actual==expect){
			msg.append("校验正确！预期值和实际值均为:"+actual);
			logger.info(msg);
		}else{
			msg.append("校验失败！预期值为："+expect+"实际值为："+actual);
			logger.error(msg);
			
		}
	}
	public static void verifyEqual(boolean actual,boolean expect,String message){
		StringBuilder msg=new StringBuilder("verifyEqual(boolean actual,boolean expect,String message):\n");
		if(actual==expect){
			msg.append("校验正确！预期值和实际值均为:"+actual);
			logger.info(msg);
		}else{
			msg.append(message+":");
			msg.append("校验失败！预期值为："+expect+"实际值为："+actual);
			logger.error(msg);
			
		}
	}
	public static void verifyEqual(double actual,double expect){
		StringBuilder msg=new StringBuilder("verifyEqual(double actual,double expect):\n");
		if(actual==expect){
			msg.append("校验正确！预期值和实际值均为:"+actual);
			logger.info(msg);
		}else{
			msg.append("校验失败！预期值为："+expect+"实际值为："+actual);
			logger.error(msg);
			
		}
	}
	public static void verifyEqual(double actual,double expect,String message){
		StringBuilder msg=new StringBuilder("verifyEqual(double actual,double expect,String message):\n");
		if(actual==expect){
			msg.append("校验正确！预期值和实际值均为:"+actual);
			logger.info(msg);
		}else{
			msg.append(message+":");
			msg.append("校验失败！预期值为："+expect+"实际值为："+actual);
			logger.error(msg);
			
		}
	}
    public static void verifyEqual(long actual,long expect){
    	StringBuilder msg=new StringBuilder("verifyEqual(long actual,long expect):\n");
    	if(actual==expect){
			msg.append("校验正确！预期值和实际值均为:"+actual);
			logger.info(msg);
		}else{
			msg.append("校验失败！预期值为："+expect+"实际值为："+actual);
			logger.error(msg);
			
		}
	}
	public static void verifyEqual(long actual,long expect,String message){
		StringBuilder msg=new StringBuilder("verifyEqual(long actual,long expect,String message):\n");
		if(actual==expect){
			msg.append("校验正确！预期值和实际值均为:"+actual);
			logger.info(msg);
		}else{
			msg.append(message+":");
			msg.append("校验失败！预期值为："+expect+"实际值为："+actual);
			logger.error(msg);
			
		}
	}
	public static void verifyEqual(char actual,char expect){
		StringBuilder msg=new StringBuilder("verifyEqual(char actual,char expect):\n");
		if(actual==expect){
			msg.append("校验正确！预期值和实际值均为:"+actual);
			logger.info(msg);
		}else{
			msg.append("校验失败！预期值为："+expect+"实际值为："+actual);
			logger.error(msg);
			
		}
	}
	public static void verifyEqual(char actual,char expect,String message){
		StringBuilder msg=new StringBuilder(":\n");
		if(actual==expect){
			msg.append("校验正确！预期值和实际值均为:"+actual);
			logger.info(msg);
		}else{
			msg.append(message+":");
			msg.append("校验失败！预期值为："+expect+"实际值为："+actual);
			logger.error(msg);
			
		}
	}
	public static void verifyEqual(String actual,String expect){
		StringBuilder msg=new StringBuilder("verifyEqual(String actual,String expect):\n");
		if(actual.equalsIgnoreCase(expect)){
			msg.append("校验正确！预期值和实际值均为:"+actual);
			logger.info(msg);
		}else{
			msg.append("校验失败！预期值为："+expect+"实际值为："+actual);
			logger.error(msg);
			
		}
	}
	public static void verifyEqual(String actual,String expect,String message){
		StringBuilder msg=new StringBuilder("verifyEqual(String actual,String expect,String message):\n");
		if(actual.equalsIgnoreCase(expect)){
			msg.append("校验正确！预期值和实际值均为:"+actual);
			logger.info(msg);
		}else{
			msg.append(message+":");
			msg.append("校验失败！预期值为："+expect+"实际值为："+actual);
			logger.error(msg);
			
		}
	}
	public static void verifyNotEqual(boolean actual,boolean expect){
		StringBuilder msg=new StringBuilder("verifyNotEqual(boolean actual,boolean expect):\n");
		if(actual==expect){
			msg.append("校验失败！预期值和实际值均为:"+actual);
			logger.error(msg);
			
		}else{
			msg.append("校验成功！预期值为："+expect+"实际值为："+actual);
			logger.info(msg);
		}
	}
	public static void verifyNotEqual(boolean actual,boolean expect,String message){
		StringBuilder msg=new StringBuilder("verifyNotEqual(boolean actual,boolean expect,String message):\n");
		if(actual==expect){
			msg.append(message+":");
			msg.append("校验失败！预期值和实际值均为:"+actual);
			logger.error(msg);
			
		}else{
			msg.append("校验成功！预期值为："+expect+"实际值为："+actual);
			logger.info(msg);
		}
	}
	public static void verifyNotEqual(double actual,double expect){
		StringBuilder msg=new StringBuilder("verifyNotEqual(double actual,double expect):\n");
		if(actual==expect){
			msg.append("校验失败！预期值和实际值均为:"+actual);
			logger.error(msg);
			
		}else{
			msg.append("校验成功！预期值为："+expect+"实际值为："+actual);
			logger.info(msg);
		}
	}
	public static void verifyNotEqual(double actual,double expect,String message){
		StringBuilder msg=new StringBuilder("verifyNotEqual(double actual,double expect,String message):\n");
		if(actual==expect){
			msg.append(message+":");
			msg.append("校验失败！预期值和实际值均为:"+actual);
			logger.error(msg);
			
		}else{
			msg.append("校验成功！预期值为："+expect+"实际值为："+actual);
			logger.info(msg);
		}
	}
    public static void verifyNotEqual(long actual,long expect){
    	StringBuilder msg=new StringBuilder("verifyNotEqual(long actual,long expect):\n");
    	if(actual==expect){
			msg.append("校验失败！预期值和实际值均为:"+actual);
			logger.error(msg);
			
		}else{
			msg.append("校验成功！预期值为："+expect+"实际值为："+actual);
			logger.info(msg);
		}
	}
	public static void verifyNotEqual(long actual,long expect,String message){
		StringBuilder msg=new StringBuilder("verifyNotEqual(long actual,long expect,String message):\n");
		if(actual==expect){
			msg.append(message+":");
			msg.append("校验失败！预期值和实际值均为:"+actual);
			logger.error(msg);
			
		}else{
			msg.append("校验成功！预期值为："+expect+"实际值为："+actual);
			logger.info(msg);
		}
	}
	public static void verifyNotEqual(char actual,char expect){
		StringBuilder msg=new StringBuilder("verifyNotEqual(char actual,char expect):\n");
		if(actual==expect){
			msg.append("校验失败！预期值和实际值均为:"+actual);
			logger.error(msg);
			
		}else{
			msg.append("校验成功！预期值为："+expect+"实际值为："+actual);
			logger.info(msg);
		}
	}
	public static void verifyNotEqual(char actual,char expect,String message){
		StringBuilder msg=new StringBuilder("verifyNotEqual(char actual,char expect,String message):\n");
		if(actual==expect){
			msg.append(message+":");
			msg.append("校验失败！预期值和实际值均为:"+actual);
			logger.error(msg);
			
		}else{
			msg.append("校验成功！预期值为："+expect+"实际值为："+actual);
			logger.info(msg);
		}
	}
	public static void verifyNotEqual(String actual,String expect){
		StringBuilder msg=new StringBuilder("verifyNotEqual(String actual,String expect):\n");
		if(actual.equalsIgnoreCase(expect)){
			msg.append("校验失败！预期值和实际值均为:"+actual);
			logger.error(msg);
			
		}else{
			msg.append("校验成功！预期值为："+expect+"实际值为："+actual);
			logger.info(msg);
		}
	}
	public static void verifyNotEqual(String actual,String expect,String message){
		StringBuilder msg=new StringBuilder("verifyNotEqual(String actual,String expect,String message):\n");
		if(actual.equalsIgnoreCase(expect)){
			msg.append(message+":");
			msg.append("校验失败！预期值和实际值均为:"+actual);
			logger.error(msg);
			
		}else{
			msg.append("校验成功！预期值为："+expect+"实际值为："+actual);
			logger.info(msg);
		}
	}
	public static void verifyInclude(String content,String included){
		StringBuilder msg=new StringBuilder("verifyInclude(String content,String included):\n");
		if(content.contains(included)){
			msg.append("包含校验正确，校验字符窜为："+content+"\n包含字符窜为："+included);
			logger.info(msg);
		}else{
			msg.append("包含校验失败，校验字符窜为："+content+"\n包含字符窜为："+included);
			logger.error(msg);
			
		}
	}
	public static void verifyInclude(String content,String included,String message){
		StringBuilder msg=new StringBuilder("verifyInclude(String content,String included,String message):\n");
		if(content.contains(included)){
			msg.append("包含校验正确，校验字符窜为："+content+"\n包含字符窜为："+included);
			logger.info(msg);
		}else{
			msg.append(message+":");
			msg.append("包含校验失败，校验字符窜为："+content+"\n包含字符窜为："+included);
			logger.error(msg);
			
		}
	}
	public static void verifyNotInclude(String content,String included){
		StringBuilder msg=new StringBuilder("verifyNotInclude(String content,String included):\n");
		if(!content.contains(included)){
			msg.append("包含校验正确，校验字符窜为："+content+"\n包含字符窜为："+included);
			logger.info(msg);
		}else{
			msg.append("包含校验失败，校验字符窜为："+content+"\n包含字符窜为："+included);
			logger.error(msg);
			
		}
	}
	public static void verifyNotInclude(String content,String included,String message){
		StringBuilder msg=new StringBuilder("verifyNotInclude(String content,String included,String message):\n");
		if(!content.contains(included)){
			msg.append("包含校验正确，校验字符窜为："+content+"\n包含字符窜为："+included);
			logger.info(msg);
		}else{
			msg.append(message+":");
			msg.append("包含校验失败，校验字符窜为："+content+"\n包含字符窜为："+included);
			logger.error(msg);
			
		}
	}
	public static void verifyMatch(String regex,String matcher){
		StringBuilder msg=new StringBuilder("verifyMatch(String actual,String expect):\n");
		if(Pattern.matches(regex, matcher)){
			msg.append("匹配校验成功！待校验的字符窜为:"+matcher+"校验的正则表达式为:"+regex);
			logger.info(msg);
		}else{
			msg.append("匹配校验失败！待校验的字符窜为:"+matcher+"校验的正则表达式为:"+regex);
			logger.error(msg);
			
		}
	}
	public static void verifyMatch(String regex,String matcher,String message){
		StringBuilder msg=new StringBuilder("verifyMatch(String actual,String expect,String message):\n");
		if(Pattern.matches(regex, matcher)){
			msg.append("匹配校验成功！待校验的字符窜为:"+matcher+"校验的正则表达式为:"+regex);
			logger.info(msg);
		}else{
			msg.append(message+":");
			msg.append("匹配校验失败！待校验的字符窜为:"+matcher+"校验的正则表达式为:"+regex);
			logger.error(msg);
			
		}
	}
	public static void verifyNotMatch(String regex,String matcher){
		StringBuilder msg=new StringBuilder("verifyNotMatch(String actual,String expect):\n");
		if(!Pattern.matches(regex, matcher)){
			msg.append("匹配校验成功！待校验的字符窜为:"+matcher+"校验的正则表达式为:"+regex);
			logger.info(msg);
		}else{
			msg.append("匹配校验失败！待校验的字符窜为:"+matcher+"校验的正则表达式为:"+regex);
			logger.error(msg);
			
		}
	}
	public static void verifyNotMatch(String regex,String matcher,String message){
		StringBuilder msg=new StringBuilder("verifyNotMatch(String actual,String expect,String message):\n");
		if(Pattern.matches(regex, matcher)){
			msg.append("匹配校验成功！待校验的字符窜为:"+matcher+"校验的正则表达式为:"+regex);
			logger.info(msg);
		}else{
			msg.append(message+":");
			msg.append("匹配校验失败！待校验的字符窜为:"+matcher+"校验的正则表达式为:"+regex);
			logger.error(msg);
			
		}
	}
	public static void verifyStartWith(String content,String prefix){
		StringBuilder msg=new StringBuilder("verifyStartWith(String content,String prefix)");
		if(content.startsWith(prefix)){
			msg.append("前缀匹配校验成功！待校验的字符窜为:"+content+"校验的前缀表达式为:"+prefix);
			logger.info(msg);
		}else{
			msg.append("前缀匹配校验失败！待校验的字符窜为:"+content+"校验的前缀表达式为:"+prefix);
			logger.error(msg);
		}
	}
	public static void verifyEndWith(String content,String endfix){
		StringBuilder msg=new StringBuilder("verifyStartWith(String content,String prefix)");
		if(content.startsWith(content)){
			msg.append("后缀匹配校验成功！待校验的字符窜为:"+content+"校验的后缀表达式为:"+content);
			logger.info(msg);
		}else{
			msg.append("后缀匹配校验失败！待校验的字符窜为:"+content+"校验的后缀表达式为:"+content);
			logger.error(msg);
		}
	}
}
