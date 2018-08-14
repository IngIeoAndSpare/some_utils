import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;


public class FileUtils {

	private static final Logger logger = LoggerFactory.getLogger(FileUtils.class);
	
	public static String saveFile(MultipartFile file, String path) throws IOException {
		String convertName = convertFileName(file);
		
		File saveFile = getFile(path, convertName);
		int result = 0;
		
		isExistPath(path);
		logger.debug("FileUtils -----------------> 파일 저장 시작 : " + file.getOriginalFilename());
		result = multipartToFile(file, saveFile);
		if(result > 0) 
			logger.debug("FileUtils -----------------> 파일 저장 완료. 변환 이름  => " + convertName);	
		return convertName;
	}
	
	/**
	 * 파일 디렉토리 확인 및 생성
	 * @param path 검사 파일 path
	 */
	public static void isExistPath(String path) {
		File file = getFile(path);
		if(!file.exists()) {
			logger.debug("FileUtils -----------------> 파일 디렉토리 생성 :" + path);
			file.mkdirs();
		} else {
			logger.debug("FileUtils -----------------> 파일 디렉토리 있음 => " + path);
		}
	}
	
	/**
	 * 폴더 삭제
	 * @param path
	 * @return
	 */
	public static int deleteDirectory(String path) {
		File file = getFile(path);
		int result = 0;
		if(file.delete()) {
			result = 1;
		} else {
			logger.debug("FileUtils -----------------> [deleteDirectory] delete Directory fail (SomeWrong)" +
		"\n please check Directory path => " + path);
			throw new IOException("responseData.message.exception.io.fail.delete");
		}
		return result;
	}
	
	
	
	/**
	 * 파일 삭제
	 * @param filePath
	 * @param fileName
	 * @return
	 */
	public static int deleteFile(String filePath, String fileName) {
		File file = getFile(filePath, fileName);
		int result = 0;
		
		if(file.exists()) {
			logger.debug("FileUtils -----------------> 파일 탐색 성공 :" + fileName);
			if(file.delete()) {
				logger.debug("FileUtils -----------------> 파일 삭제 성공");
				result = 1;
			} else {
				logger.debug("FileUtils -----------------> [deleteFile] delete File fail (SomeWrong) \n " + 
						"please check file path => " + file.getPath() +
						"\n please check input Param =>  path :" + filePath + "  ||  fileName =>" + fileName +
						"\n please check file parent => " + file.getParent());
				throw new IOException("responseData.message.exception.io.fail.delete");
			}
		} else {
			logger.debug("FileUtils -----------------> [deleteFile] delete File fail (NotFound) \n " + 
					"please check file path => " + file.getPath());
			throw new IOException("responseData.message.exception.not.found.file");
		}
		return result;
	}
	
	/**
	 * 이미지 base64 형태로 치환
	 * @param filePath
	 * @param fileName
	 * @return
	 */
	public static String convertBase64Image(String filePath, String fileName) {
		
		StringBuffer result = new StringBuffer();
		File target = getFile(filePath, fileName);
		result.append(encoderBase64(target));
		return result.toString();
	}
	
	/**
	 * file 객체 리턴
	 * @param filePath
	 * @param fileName
	 * @return
	 */
	public static File getFile(String filePath, String fileName){
		return new File(filePath, fileName);
	}
	
	public static File getFile(String filePath) {
		return new File(filePath);
	}
	
	/**
	 * 파일 저장
	 * @param file
	 * @param targetFile
	 * @return
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	public static int multipartToFile (MultipartFile file, File targetFile){
		try {
			file.transferTo(targetFile);
		} catch (IllegalStateException e) {
			// TODO : 에러 알아보고 처리하기
			e.printStackTrace();
		} catch (IOException e) {
			logger.debug("FileUtils -----------------> [multipartToFile] save File fail \n " + 
		"please check file path => " + targetFile.getPath() +
		"\n please check file size => " + file.getSize() + "\n please check file contentType => " + file.getContentType());
			
			throw new IOException("responseData.message.exception.io.fail.save");
		}
		return 1;
	}
	
	/**
	 * 파일 이름 변환
	 * @param file
	 * @return
	 */
	public static String convertFileName(MultipartFile file) {
		StringBuffer convertName = new StringBuffer(UUID.randomUUID().toString());
		convertName.append(file.getOriginalFilename());
		return convertName.toString();
	}
	
	/**
	 * base64 리턴
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static String encoderBase64(File file){
		byte[] fileContent = null;
		try {
			fileContent = org.apache.commons.io.FileUtils.readFileToByteArray(file);
		} catch (IOException e) {
			logger.debug("FileUtils -----------------> [encoderBase64] base64 encoding fail \n " + 
					"please check file path => " + file.getPath());
			
			throw new IOException("responseData.message.exception.not.found.file");
		}
		StringBuffer result = new StringBuffer(Base64.getEncoder().encodeToString(fileContent));
		return result.toString();
	}

	/**
	 * multipartfile -> file convert
	 * 이 유틸을 사용하기 위해선 application.properties 에 spring.http.multipart.enabled = true 를 추가해야함.
	 * @param file muiltifile
	 * @return
	 */
	public static File convertFile(MultipartFile file) {
		File convertFile = new File(file.getOriginalFilename());
		try{
			file.transferTo(convertFile);		
		} catch(IllegalStateException e) {
			// TODO : 에러 알아보고 처리하기
			e.printStackTrace();
		} catch (IOException e) {
			logger.debug("FileUtils -----------------> [convertFile] convert File fail \n " + 
		"\n please check file size => " + file.getSize() + "\n please check file contentType => " + file.getContentType());
		}
		return convertFile;
	}
}
