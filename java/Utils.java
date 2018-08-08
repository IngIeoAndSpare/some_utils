import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
	

			
	/**
	 * String Null Check 
	 */
	public static boolean checkNullStr(String str) {
		
		if(str != null && !"".equals(str) && !"null".equals(str)) {
			return true;
		}
		return false;
	}
	
	/**
	 * 특수문자 체크
	 * @param str
	 * @return 있음 -> true , 없음 -> false 
	 */
	public static boolean checkCharacter(String str) {
		if(str == null || str.trim().isEmpty()) {
			return true;
		}
		Pattern pattern = Pattern.compile("[^A-Za-z0-9]");
		Matcher metcher = pattern.matcher(str);
		
		if(metcher.find()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 배열 문자 null check (null 있으면 false , 없으면 true)
	 * @param str
	 * @return
	 */
	public static boolean checkNullStr(String[] str) {
		for(String item : str) {
			if(!checkNullStr(item)){
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 예외 -> String 변환 
	 * @param e
	 * @return
	 */
	public static String getAsString(Throwable e) {
        StringWriter writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        e.printStackTrace(printWriter);
        return writer.toString();
  }
	
		/**
	 * url 커팅
	 * @param uriPath
	 * @return
	 */
	public static String[] getUri(String uriPath, String context) {
		
		Pattern pattern = Pattern.compile(context);
		Matcher match = pattern.matcher(uriPath);
		
		return match.replaceAll("").split("/");
	}
	
	/**
	 * url 재조립 (context 형태는 앞에 '/' 없어야 함)
	 * @param cutUri
	 * @param context
	 * @return
	 */
	public static String getUrl(String[] cutUri, String context) {
		int index = 0;
		StringBuffer url = new StringBuffer();
		
		for(String item : cutUri) {
			if(index == 1) {
				url.append(context);
			}
			url.append(item).append("/");
			index++;
		}
		return url.toString();
	}
	
	/**
	 * 사이값 체크 (사이값에 있으면 true 아니면 false)
	 * @param inputNum
	 * @param minValue
	 * @param maxValue
	 * @return
	 */
	public static boolean betweenNumCheck(int inputNum, int minValue, int maxValue) {
		if (inputNum >= minValue && inputNum <= maxValue) {
			return true;
		} else {
			return false;
		}
	}


	public static String getQueryString(Map<String, String[]> params) {
		StringBuffer result = new StringBuffer("?");
		for(Map.Entry<String, String[]> item : params.entrySet()) {
			result.append(Utils.getAppendParam(item.getKey(), item.getValue()[0]));
			result.append("&");
		}
		return result.toString().substring(0, result.length() - 1);
	}
	
	public static String getAppendParam(String key, String value) {
		return String.format("%s=%s", key, value);
	}

}
