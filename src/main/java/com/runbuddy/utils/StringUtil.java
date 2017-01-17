package com.runbuddy.utils;


/**
 * ������
 * @author DY1101shaoyuxian
 *
 */
public class StringUtil {

	public static boolean isEmpty(String str){
		if("".equals(str)||str==null){
			return true;
		}else{
			return false;
		}
	}
	
	public static boolean isNotEmpty(String str){
		if(!"".equals(str)&&str!=null){
			return true;
		}else{
			return false;
		}
	}

	public static boolean isEmpty(long num) {
		if(num < 0)
			return true;
		else
			return false;
	}
	
	public static boolean isEmpty(int age) {
		if(age <= 0 || age > 100)
			return true;
		else
			return false;
	}


}
