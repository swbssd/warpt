/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.shop.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.shop.entity.ShopCustomerAccount;
import com.thinkgem.jeesite.modules.shop.service.ShopCustomerAccountService;

/**
 * 客户收款Controller
 * @author swbssd
 * @version 2018-04-20
 */
@Controller
@RequestMapping(value = "${adminPath}/shop/shopCustomerAccount")
public class ShopCustomerAccountController extends BaseController {

	@Autowired
	private ShopCustomerAccountService shopCustomerAccountService;
	
	@ModelAttribute
	public ShopCustomerAccount get(@RequestParam(required=false) String id) {
		ShopCustomerAccount entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = shopCustomerAccountService.get(id);
		}
		if (entity == null){
			entity = new ShopCustomerAccount();
		}
		return entity;
	}
	
	@RequiresPermissions("shop:shopCustomerAccount:view")
	@RequestMapping(value = {"list", ""})
	public String list(ShopCustomerAccount shopCustomerAccount, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ShopCustomerAccount> page = shopCustomerAccountService.findPage(new Page<ShopCustomerAccount>(request, response), shopCustomerAccount); 
		model.addAttribute("page", page);
		return "modules/shop/shopCustomerAccountList";
	}

	@RequiresPermissions("shop:shopCustomerAccount:view")
	@RequestMapping(value = "form")
	public String form(ShopCustomerAccount shopCustomerAccount, Model model) {
		model.addAttribute("shopCustomerAccount", shopCustomerAccount);
		return "modules/shop/shopCustomerAccountForm";
	}

	@RequiresPermissions("shop:shopCustomerAccount:edit")
	@RequestMapping(value = "save")
	public String save(ShopCustomerAccount shopCustomerAccount, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, shopCustomerAccount)){
			return form(shopCustomerAccount, model);
		}
		shopCustomerAccountService.save(shopCustomerAccount);
		addMessage(redirectAttributes, "保存客户收款成功");
		return "redirect:"+Global.getAdminPath()+"/shop/shopCustomerAccount/?repage";
	}
	
	@RequiresPermissions("shop:shopCustomerAccount:edit")
	@RequestMapping(value = "delete")
	public String delete(ShopCustomerAccount shopCustomerAccount, RedirectAttributes redirectAttributes) {
		shopCustomerAccountService.delete(shopCustomerAccount);
		addMessage(redirectAttributes, "删除客户收款成功");
		return "redirect:"+Global.getAdminPath()+"/shop/shopCustomerAccount/?repage";
	}

}