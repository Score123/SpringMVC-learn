package com.bee.util;

	public class PrintUtil {
	/**
	 * DEBUG = 1 debug模式
	 * DEBUG = 0 正常模式
	 */
	private static int DEBUG = 1; 
	
	/**
	 * @param object 打印内容
	 */
	public static void println(Object object){
		if(DEBUG==1){
			if(object!=null){
				System.out.println("===============>"+object.toString());
			}else{
				System.out.println("===============>"+null);
			}
		}
	}
}
