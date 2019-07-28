package cn.techaction.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.techaction.common.SverResponse;
import cn.techaction.pojo.ActionParam;
import cn.techaction.service.ActionParamsService;
import jdk.internal.org.objectweb.asm.tree.analysis.Value;

@Controller
@RequestMapping("/param")
public class ActionParamController {
	
	@Autowired
	private ActionParamsService actionParamsService;
	
	/**
	 * 查找所有商品类型
	 * @param httpSession
	 * @return
	 */
	@RequestMapping(value = "/findallparams.do", method = RequestMethod.GET)
	@ResponseBody
	public SverResponse<List<ActionParam>> findAllParams(HttpSession httpSession){
		return actionParamsService.findAllParams();
	}
}
