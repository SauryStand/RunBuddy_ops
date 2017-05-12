/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.common.web;

import java.beans.PropertyEditorSupport;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import javax.validation.Validator;

import PluSoft.Utils.JSON;
import com.runbuddy.utils.config.FrameConfigKey;
import com.runbuddy.utils.config.FrameParamsDefKey;
import com.runbuddy.utils.tools.RSAUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.beanvalidator.BeanValidators;
import com.thinkgem.jeesite.common.mapper.JsonMapper;
import com.thinkgem.jeesite.common.utils.DateUtils;

/**
 * 控制器支持类
 * @author ThinkGem
 * @version 2013-3-23
 */
public abstract class BaseController {

	/**
	 * 日志对象
	 */
	protected Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 管理基础路径
	 */
	@Value("${adminPath}")
	protected String adminPath;
	
	/**
	 * 前端基础路径
	 */
	@Value("${frontPath}")
	protected String frontPath;
	
	/**
	 * 前端URL后缀
	 */
	@Value("${urlSuffix}")
	protected String urlSuffix;
	
	/**
	 * 验证Bean实例对象
	 */
	@Autowired
	protected Validator validator;

	/**
	 * 服务端参数有效性验证
	 * @param object 验证的实体对象
	 * @param groups 验证组
	 * @return 验证成功：返回true；严重失败：将错误信息添加到 message 中
	 */
	protected boolean beanValidator(Model model, Object object, Class<?>... groups) {
		try{
			BeanValidators.validateWithException(validator, object, groups);
		}catch(ConstraintViolationException ex){
			List<String> list = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
			list.add(0, "数据验证失败：");
			addMessage(model, list.toArray(new String[]{}));
			return false;
		}
		return true;
	}
	
	/**
	 * 服务端参数有效性验证
	 * @param object 验证的实体对象
	 * @param groups 验证组
	 * @return 验证成功：返回true；严重失败：将错误信息添加到 flash message 中
	 */
	protected boolean beanValidator(RedirectAttributes redirectAttributes, Object object, Class<?>... groups) {
		try{
			BeanValidators.validateWithException(validator, object, groups);
		}catch(ConstraintViolationException ex){
			List<String> list = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
			list.add(0, "数据验证失败：");
			addMessage(redirectAttributes, list.toArray(new String[]{}));
			return false;
		}
		return true;
	}
	
	/**
	 * 服务端参数有效性验证
	 * @param object 验证的实体对象
	 * @param groups 验证组，不传入此参数时，同@Valid注解验证
	 * @return 验证成功：继续执行；验证失败：抛出异常跳转400页面。
	 */
	protected void beanValidator(Object object, Class<?>... groups) {
		BeanValidators.validateWithException(validator, object, groups);
	}
	
	/**
	 * 添加Model消息
	 * @param message
	 */
	protected void addMessage(Model model, String... messages) {
		StringBuilder sb = new StringBuilder();
		for (String message : messages){
			sb.append(message).append(messages.length>1?"<br/>":"");
		}
		model.addAttribute("message", sb.toString());
	}
	
	/**
	 * 添加Flash消息
	 * @param message
	 */
	protected void addMessage(RedirectAttributes redirectAttributes, String... messages) {
		StringBuilder sb = new StringBuilder();
		for (String message : messages){
			sb.append(message).append(messages.length>1?"<br/>":"");
		}
		redirectAttributes.addFlashAttribute("message", sb.toString());
	}
	
	/**
	 * 客户端返回JSON字符串
	 * @param response
	 * @param object
	 * @return
	 */
	protected String renderString(HttpServletResponse response, Object object) {
		return renderString(response, JsonMapper.toJsonString(object), "application/json");
	}
	
	/**
	 * 客户端返回字符串
	 * @param response
	 * @param string
	 * @return
	 */
	protected String renderString(HttpServletResponse response, String string, String type) {
		try {
			response.reset();
	        response.setContentType(type);
	        response.setCharacterEncoding("utf-8");
			response.getWriter().print(string);
			return null;
		} catch (IOException e) {
			return null;
		}
	}

	/**
	 * 参数绑定异常
	 */
	@ExceptionHandler({BindException.class, ConstraintViolationException.class, ValidationException.class})
    public String bindException() {  
        return "error/400";
    }
	
	/**
	 * 授权登录异常
	 */
	@ExceptionHandler({AuthenticationException.class})
    public String authenticationException() {  
        return "error/403";
    }
	
	/**
	 * 初始化数据绑定
	 * 1. 将所有传递进来的String进行HTML编码，防止XSS攻击
	 * 2. 将字段中Date类型转换为String类型
	 */
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		// String类型转换，将所有传递进来的String进行HTML编码，防止XSS攻击
		binder.registerCustomEditor(String.class, new PropertyEditorSupport() {
			@Override
			public void setAsText(String text) {
				setValue(text == null ? null : StringEscapeUtils.escapeHtml4(text.trim()));
			}
			@Override
			public String getAsText() {
				Object value = getValue();
				return value != null ? value.toString() : "";
			}
		});
		// Date 类型转换
		binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
			@Override
			public void setAsText(String text) {
				setValue(DateUtils.parseDate(text));
			}
//			@Override
//			public String getAsText() {
//				Object value = getValue();
//				return value != null ? DateUtils.formatDateTime((Date)value) : "";
//			}
		});
	}

	/*******************personal add ,combine with anothor baseController**************************/
	/**
	 * 根据逗号分割的key值获取，request对象中的参数值，并转成map，如果key值 为空，与上一个方法不同的是不抛异常
	 *
	 * @param keys
	 * @param request
	 * @return
	 */
	public Map<String, String> getParams(String keys, HttpServletRequest request) {
		Map mMap = new HashMap();
		String[] keyArray = keys.split(",");
		for (int i = 0; i < keyArray.length; i++) {
			String key = keyArray[i];
			String value = request.getParameter(key);
			if (value != null) {
				mMap.put(key, value);
			} else {
				mMap.put(key, "");
			}
		}
		return mMap;
	}

	/**
	 * 获取数据源Key值
	 *
	 * @param request
	 * @return
	 */
	public String getDbKey(HttpServletRequest request) {
		String dbkey = FrameConfigKey.DEFAULT_DATASOURCE;
		if (request.getParameter(FrameParamsDefKey.DB_KEY) != null) {
			dbkey = request.getParameter(FrameParamsDefKey.DB_KEY);
		}
		logger.debug("param，dbKey ---> " + dbkey);
		return dbkey;
	}

	public String getExecKey(HttpServletRequest request) {
		//获取执行sql语句的key值
		String execKey = request.getParameter(FrameParamsDefKey.EXEC_KEY);
		logger.debug("param，execute Sql Key value is :  " + execKey);
		return execKey;
	}

	/**
	 * 获取分页页码
	 *
	 * @param request
	 * @return
	 */
	public int getPageIndex(HttpServletRequest request) {
		String tmpPageIndex = request.getParameter(FrameParamsDefKey.PAGE_INDEX);
		Integer pageIndex = 0;
		if ((tmpPageIndex != null) && (!tmpPageIndex.equals(""))) {
			pageIndex = Integer.parseInt(tmpPageIndex);
		}
		logger.debug("param, the pading page: " + pageIndex);
		return pageIndex;
	}

	/**
	 * 获取分页大小
	 *
	 * @param request
	 * @return
	 */
	public int getPageSize(HttpServletRequest request) {
		String tmpPageSize = request.getParameter(FrameParamsDefKey.PAGE_SIZE);
		Integer pageSize = 0;
		if ((tmpPageSize != null) && (!tmpPageSize.equals(""))) {
			pageSize = Integer.parseInt(tmpPageSize);
		}
		logger.debug("参数，分页大小 ---> " + pageSize);
		return pageSize;
	}

	/**
	 * @param request
	 * @return
	 */
	public Map<String, String> getParamsMap(HttpServletRequest request) {
		Map<String, String> paramsMap = new HashMap<String, String>();
		String paramsStr = request.getParameter(FrameParamsDefKey.PARAMS);
		logger.debug("参数，转换前数据 ---> " + paramsStr);
		if (paramsStr == null || paramsStr.equals("")) {
			logger.debug("参数，参数为空，不转换");
			return paramsMap;
		}
		if (!paramsStr.startsWith("{") || !paramsStr.endsWith("}")) {
			logger.debug("参数：参数不是Json格式，不转换");
			return paramsMap;
		}
		paramsMap = (Map<String, String>) JSON.Decode(paramsStr);
		logger.debug("参数，参数为 ---> " + paramsMap);
		return paramsMap;


	}

	/**
	 * 获取参数Map，Map类型为Map<String,Object>
	 *
	 * @param request
	 * @return
	 */
	public Map<String, Object> getParamsMapByObject(HttpServletRequest request) {
		//获取参数
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		String paramsStr = request.getParameter(FrameParamsDefKey.PARAMS);
		logger.debug("参数，转换前数据 ---> " + paramsStr);
		if (paramsStr == null || paramsStr.equals("")) {
			logger.debug("参数，参数为空，不转换");
			return paramsMap;
		}
		if (!paramsStr.startsWith("{") || !paramsStr.endsWith("}")) {
			logger.debug("参数：参数不是Json格式，不转换");
			return paramsMap;
		}
		paramsMap = (Map<String, Object>) JSON.Decode(paramsStr);
		logger.debug("参数，参数为 ---> " + paramsMap);
		return paramsMap;
	}


	/**
	 * 获取参数List
	 *
	 * @param request
	 * @return
	 */
	public List<Map<String, String>> getParamsList(HttpServletRequest request) {
		//获取参数
		List<Map<String, String>> paramsList = new ArrayList<Map<String, String>>();
		String paramsStr = request.getParameter(FrameParamsDefKey.PARAMS);
		logger.debug("参数，转换前数据 ---> " + paramsStr);
		if (paramsStr == null || paramsStr.equals("")) {
			logger.debug("参数，参数为空，不转换");
			return paramsList;
		}
		if (!paramsStr.startsWith("[") || !paramsStr.endsWith("]")) {
			logger.debug("参数：参数不是有效的Json格式，不转换");
			return paramsList;
		}
		paramsList = (List<Map<String, String>>) JSON.Decode(paramsStr);
		logger.debug("参数，参数为 ---> " + paramsList);
		return paramsList;
	}

	/**
	 * 获取综合操作的参数
	 *
	 * @param request
	 * @return
	 */
	public List<Map<String, List<Map<String, String>>>> getParamsMutilList(HttpServletRequest request) {
		//获取参数
		List<Map<String, List<Map<String, String>>>> paramsMutilList = new ArrayList<Map<String, List<Map<String, String>>>>();
		String paramsStr = request.getParameter(FrameParamsDefKey.PARAMS);
		logger.debug("参数，转换前数据 ---> " + paramsStr);
		if (paramsStr == null || paramsStr.equals("")) {
			logger.debug("参数，参数为空，不转换");
			return paramsMutilList;
		}
		if (!paramsStr.startsWith("[") || !paramsStr.endsWith("]")) {
			logger.debug("参数：参数不是有效的Json格式，不转换");
			return paramsMutilList;
		}
		paramsMutilList = (List<Map<String, List<Map<String, String>>>>) JSON.Decode(paramsStr);
		logger.debug("参数，参数为 ---> " + paramsMutilList);
		return paramsMutilList;
	}

	/**
	 * 获取excelKeys
	 *
	 * @param request
	 * @return
	 */
	public String getExcelDataKeys(HttpServletRequest request) {
		String excelDataKeys = request.getParameter(FrameParamsDefKey.EXCEL_DATA_KEYS);
		logger.debug("参数，导出excel参数，列名称key值集合 ---> " + excelDataKeys);
		return excelDataKeys;
	}

	/**
	 * 获取excel列中文名
	 *
	 * @param request
	 * @return
	 */
	public String getExcelHeadNames(HttpServletRequest request) {
		String excelHeadNames = request.getParameter(FrameParamsDefKey.EXCEL_HEAD_NAMES);
		logger.debug("参数，导出excel参数，列显示名称集合 ---> " + excelHeadNames);
		return excelHeadNames;
	}

	/**
	 * 获取导出excel的文件名称
	 *
	 * @param request
	 * @return
	 */
	public String getExcelFileName(HttpServletRequest request) {
		String excelFileName = request.getParameter(FrameParamsDefKey.EXCEL_FILE_NAME);
		logger.debug("参数，导出excel参数，导出文件名称 ---> " + excelFileName);
		return excelFileName;
	}


	/**
	 * RSA加密
	 *
	 * @param request
	 * @param text
	 * @return
	 */
	public String encrypt(HttpServletRequest request, String text) {
		ServletContext cxt = request.getSession().getServletContext();
		Map<String, BigInteger> RSAMap = (Map<String, BigInteger>) cxt.getAttribute("RSAMap");
		BigInteger modulus = RSAMap.get("modulus");
		BigInteger public_exponent = RSAMap.get("public_exponent");
//    	 BigInteger private_exponent = RSAMap.get("private_exponent");
		RSAPublicKey pubKey = RSAUtils.getPublicKey(modulus.toString(), public_exponent.toString());
		String ciphertext = null;
		try {
			ciphertext = RSAUtils.encryptByPublicKey(text, pubKey);
		} catch (Exception e) {
			logger.error(text + "，加密失败！：" + e);
			throw new RuntimeException(text + "，加密失败！" + e.getMessage());
		}

		logger.debug("明文：" + text + ",加密结果：" + ciphertext);
		return ciphertext;
	}


	/**
	 * RSA解密
	 *
	 * @param request
	 * @param ciphertext
	 * @return
	 */
	public String decrypt(HttpServletRequest request, String ciphertext) {
		ServletContext cxt = request.getSession().getServletContext();
		Map<String, BigInteger> RSAMap = (Map<String, BigInteger>) cxt.getAttribute("RSAMap");
		BigInteger modulus = RSAMap.get("modulus");
		BigInteger private_exponent = RSAMap.get("private_exponent");
		RSAPrivateKey priKey = RSAUtils.getPrivateKey(modulus.toString(), private_exponent.toString());
		String text = null;
		try {
			text = RSAUtils.decryptByPrivateKey(ciphertext, priKey);
		} catch (Exception e) {
			logger.error(ciphertext + "，解密失败！：" + e);
			throw new RuntimeException(ciphertext + "，解密失败！" + e.getMessage());

		}

		logger.debug("密文：" + ciphertext + ",解密成功");
		return text;
	}

	/**
	 * 该方法需要传入一个DefaultMultipartHttpServletRequest的对象才能处理，
	 * 一般上传文件时，获取的request对象已被spring mvc 包装成此对象，因此使用时
	 * 只需强转为该对象即可。
	 * 获取上传文件（被缓存在一个临时目录中，业务处理完，文件需自行删除，以免占用存储空间）
	 * 考虑文件名重复的可能性，保存的文件名被更改格式为yyyymmddHHmmss_(1000以下整数)_原文件名
	 * 如需取得原文件名需自行截取处理，提供如下示例供参考：
	 * String tmp =  fileName.substring(16);
	 * String originalFilename = tmp.substring(tmp.indexOf("_")+1);
	 *
	 * @param request
	 * @return
	 */
	public List<File> getUploadFiles(DefaultMultipartHttpServletRequest request) {
		ArrayList<File> filesList = new ArrayList<File>();
		Map resultMap = null;
		String rootPath = request.getSession().getServletContext().getRealPath("/")
				+ "/tmpFile";
		logger.info("上传文件临时保存目录：" + rootPath);
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");

		if (!new File(rootPath).exists()) {
			new File(rootPath).mkdirs();
		}
		Iterator<String> formFilesName = request.getFileNames();
		while (formFilesName.hasNext()) {
			String formFileName = formFilesName.next();
			List<MultipartFile> files = request.getFiles(formFileName);
			for (int i = 0; i < files.size(); i++) {
				MultipartFile file = files.get(i);
				String fileName = file.getOriginalFilename();
				System.out.println(fileName);
				String newFileName = df.format(new Date()) + "_" + new Random().nextInt(1000) + "_" + fileName;
				File uploadFile = new File(rootPath + File.separator + newFileName);

				try {
					if (!uploadFile.exists()) {
						logger.info("创建文件：" + uploadFile.getAbsolutePath());
						uploadFile.createNewFile();
					}
					FileCopyUtils.copy(file.getBytes(), uploadFile);
					filesList.add(uploadFile);

				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return filesList;
	}


	public List<File> getOriginalUploadFiles(DefaultMultipartHttpServletRequest request) {
		ArrayList<File> filesList = new ArrayList<File>();
		Map resultMap = null;
		String rootPath = request.getSession().getServletContext().getRealPath("/")
				+ "/tmpFile";
		if (!new File(rootPath).exists()) {
			new File(rootPath).mkdirs();
		}

		logger.info("上传文件临时保存目录：" + rootPath);
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");

		Iterator<String> formFilesName = request.getFileNames();
		while (formFilesName.hasNext()) {
			String formFileName = formFilesName.next();
			List<MultipartFile> files = request.getFiles(formFileName);
			for (int i = 0; i < files.size(); i++) {
				MultipartFile file = files.get(i);
				String fileName = file.getOriginalFilename();
//			   System.out.println(fileName);
				String filePath = df.format(new Date()) + "_" + new Random().nextInt(1000);

				if (!new File(rootPath + "/" + filePath).exists()) {
					new File(rootPath + "/" + filePath).mkdirs();
				}
				File uploadFile = new File(rootPath + "/" + filePath + "/" + fileName);

				try {
					if (!uploadFile.exists()) {
						logger.info("创建文件：" + uploadFile.getAbsolutePath());
						uploadFile.createNewFile();
					}
					FileCopyUtils.copy(file.getBytes(), uploadFile);
					filesList.add(uploadFile);

				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return filesList;
	}

	/**
	 * 考虑到原有getParams方法有限定条件即只能传一个参数params，当前台传参有多个参数时，不能满足获取值的需求，
	 * 因此加入此通用方法，用于获取参数Map,加工原生的request.getParameterMap,取value中的index为0的值，本方法
	 * 不会去对value进行二次解析
	 *
	 * @param request
	 * @return
	 */
	public Map<String, String> getParameterMap(HttpServletRequest request) {
		Map<String, String> paramsMap = new HashMap<String, String>();
		//获取参数
		Map<String, String[]> map = request.getParameterMap();
		Set<String> keys = map.keySet();
		Iterator<String> keysIterator = keys.iterator();
		while (keysIterator.hasNext()) {
			String key = keysIterator.next();
			paramsMap.put(key, (map.get(key))[0]);
		}
		return paramsMap;
	}
	/********************************************************************************************/



	
}
