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
	
	
}
