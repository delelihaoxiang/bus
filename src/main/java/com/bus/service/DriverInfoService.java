package com.bus.service;
import java.util.Map;
import com.bus.controller.LoginModel;
import com.bus.pojo.*;
import java.io.InputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
public interface DriverInfoService{
/**
  #chinessCode0##chinessCode1##chinessCode2##chinessCode3##chinessCode4##chinessCode5##chinessCode6##chinessCode7##chinessCode8##chinessCode9#
*/
		public Map<String,Object> getDataList(DriverInfo queryModel, Integer page, Integer pageSize, LoginModel login) ;

/**
  #chinessCode10##chinessCode11##chinessCode4##chinessCode5##chinessCode14##chinessCode15##chinessCode16##chinessCode17##chinessCode18##chinessCode19##chinessCode6##chinessCode7##chinessCode22##chinessCode23#
*/
		public Map<String,Object> getDriverInfoModel(DriverInfo model, LoginModel login);

	/**
	* #chinessCode24##chinessCode25##chinessCode6##chinessCode7#
	*/
		public void delete(Integer id);

/**
  #chinessCode28##chinessCode29# 
*/
		public String add(DriverInfo model, LoginModel login);
/**
  #chinessCode30##chinessCode31# 
*/
		public String update(DriverInfo model, LoginModel login);
}
