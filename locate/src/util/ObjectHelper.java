package util;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.hibernate.proxy.HibernateProxy;

public class ObjectHelper {
	public static boolean isEquals(Object object1, Object object2) {
		boolean ret = false;
		try {
			if ((object1 == null) && (object2 == null)) {
				return true;
			}
			
			ret = object1.equals(object2);
		} catch (NullPointerException e) {
			ret = false;
		}
		return ret;
	}
	
	public static boolean equalsIgnorecase(String s1, String s2) {
		if ((s1 == null) && (s2 == null)) return true;
		if ((s1 != null) && (s2 != null) && (s1.toLowerCase().equals(s2.toLowerCase()))) {
			return true;
		}
		return false;
	}
	
	@SuppressWarnings("rawtypes")
	public static boolean isEmpty(Object obj) {
		if (obj == null) {
			return true;
		}
		if ((obj instanceof Map)) {
			return ((Map) obj).entrySet().isEmpty();
		}
		
		if ((obj instanceof Collection)) {
			return ((Collection) obj).isEmpty();
		}
		
		if ((obj instanceof String)) {
			return ((String) obj).equalsIgnoreCase("null") | ((String) obj).trim().isEmpty();
		}
		
		if ((obj instanceof StringBuffer)) {
			return ((StringBuffer) obj).length() == 0;
		}
		
		if (obj.getClass().isArray()) try {
			Object[] a = (Object[]) obj;
			
			boolean b = true;
			for (Object o : a) {
				b &= isEmpty(o);
				
				if (!b) {
					break;
				}
			}
			return b;
		} catch (ClassCastException e) {
		}
		return false;
	}
	
	public static boolean isNotEmpty(Object obj) {
		return !isEmpty(obj);
	}
	/**
	 * 多个判断条件时
	 * @param objs
	 * @return
	 */
	public static boolean isNotEmptyAll(Object...objs) {
		for(Object obj:objs){
			if(!isEmpty(obj)){
			}else{
				return false;
			}
		}
		return true;
	}
	
	public static boolean isEmailFormat(String email) {
		Pattern pattern = Pattern
				.compile("^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$");
		
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}
	
	public static boolean isEmailAdressFormat(String email) {
		boolean isExist = false;
		if (isEmpty(email)) {
			return isExist;
		}
		Pattern p = Pattern.compile("\\w+@(\\w+\\.)+[a-z]{2,3}");
		Matcher m = p.matcher(email);
		boolean b = m.matches();
		if (b) {
			isExist = true;
		}
		return isExist;
	}
	
	public static String objectToJson(String str, String jsonCallBack) {
		return jsonCallBack + "('" + str + "')";
	}
	
	public static String objectToJson(Boolean flag, String jsonCallBack) {
		return jsonCallBack + "(" + flag + ")";
	}
	
	public static Object initializeProxy(Object obj) {
		if ((obj instanceof HibernateProxy)) {
			obj = ((HibernateProxy) obj).getHibernateLazyInitializer().getImplementation();
		}
		
		return obj;
	}
	
	@SuppressWarnings("rawtypes")
	public static boolean isImplement(Object obj, Class<?> type) {
		if (!type.isInterface()) {
			return false;
		}
		
		Class[] clzz = obj.getClass().getInterfaces();
		
		for (Class clz : clzz) {
			if (clz.getName().equals(type.getName())) {
				return true;
			}
		}
		
		return false;
	}
	/**
	 * 判断某个字符串是否存在于数组中
	 * 
	 * @param stringArray
	 *            原数组
	 * @param source
	 *            查找的字符串
	 * @return 是否找到
	 */
	public static boolean contains(String[] stringArray, String source) {
		if(isEmpty(stringArray) || isEmpty(source)){
			return false;
		}
		// 转换为list
		List<String> tempList = Arrays.asList(stringArray);
		// 利用list的包含方法,进行判断
		if (tempList.contains(source)) {
			return true;
		} else {
			return false;
		}
	}
	/**
	 * 数组转为String
	 * 分隔符为  ,
	 * @param args
	 * @return
	 */
	public static String stringsTString(String[] args){
		return stringsTString(args, ",");
	}
	public static String stringsTString(String[] args,String separate){
		String r="";
		if(ObjectHelper.isNotEmpty(args)){
			for(String s:args){
				if(ObjectHelper.isEmpty(r)){
					r = s;
				}else{
					r =r +","+s;
				}
			}
		}
		return r;
	}
	/**
	 * 转为sql中in的拼接ids使用
	 * @param orderIds
	 * @param separate
	 * @return
	 */
	public static String ssTsForSql(String[] orderIds,String separate){
		StringBuffer sb = new StringBuffer();
		for(String s: orderIds){
			if(ObjectHelper.isEmpty(sb)){
				sb.append("'"+s+"'");
			}else{
				sb.append(separate+"'"+s+"'");
			}
		}
		return sb.toString();
	}
}

