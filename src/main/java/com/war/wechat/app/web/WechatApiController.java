/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.war.wechat.app.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.thinkgem.jeesite.common.mapper.JsonMapper;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.vip.service.VipUserBaseService;
import com.war.wechat.app.utils.WeChatUtils;

/**
 * 微信API接口请求端
 * 
 * @author swbssd
 * @version 2017-09-17
 */
@Controller
@RequestMapping(value = "${wechatPath}/api")
public class WechatApiController extends BaseController {

	@Autowired
	private VipUserBaseService vipUserBaseService;


	/**
	 * 获取OpenId
	 * https://api.weixin.qq.com/sns/jscode2session
	 * ?appid=APPID&secret=SECRET&js_code=JSCODE&grant_type=authorization_code
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getOpenId")
	public Map<String, Object> getOpenId(HttpServletRequest request) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		String jsCode = request.getParameter("jsCode");
		try {
			//获取openId
			String loginJson = WeChatUtils.getOpenIdByLoginCode(jsCode);
			returnMap = JsonMapper.getInstance().fromJson(loginJson, HashMap.class);
			returnMap.put("success", true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			returnMap.put("success", false);
		}
		return returnMap;
	}

}