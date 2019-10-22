package com.kyrie.controller;

import com.alibaba.fastjson.JSON;
import com.kyrie.service.KyrieGeneratorService;
import com.kyrie.util.BaseResult;
import com.kyrie.util.PageUtils;
import com.kyrie.util.Query;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/kyrie/generator")
public class KyrieGeneratorController {

	@Autowired
	private KyrieGeneratorService kyrieGeneratorService;
	
	/**
	 * 列表
	 */
	@ResponseBody
	@RequestMapping("/list")
	public BaseResult list(@RequestParam Map<String, Object> params){
		//查询列表数据
		Query query = new Query(params);
		List<Map<String, Object>> list = kyrieGeneratorService.queryList(query);
		int total = kyrieGeneratorService.queryTotal(query);
		PageUtils pageUtil = new PageUtils(list, total, query.getLimit(), query.getPage());
		return BaseResult.ok().put("page", pageUtil);
	}

	/**
	 * 生成代码
	 */
	@RequestMapping("/code")
	public void code(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String[] tableNames = new String[]{};
		String tables = request.getParameter("tables");
		tableNames = JSON.parseArray(tables).toArray(tableNames);
		byte[] data = kyrieGeneratorService.generatorCode(tableNames);
		response.reset();  
        response.setHeader("Content-Disposition", "attachment; filename=\"kyrie.zip\"");
        response.addHeader("Content-Length", "" + data.length);  
        response.setContentType("application/octet-stream; charset=UTF-8");
        IOUtils.write(data, response.getOutputStream());
	}
	
	/**
	 * 更新全部后端代码
	 */
	@RequestMapping("/allcode")
	@ResponseBody
	public BaseResult allcode(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String[] tableNames = new String[]{};
		String tables = request.getParameter("tables");
		tableNames = JSON.parseArray(tables).toArray(tableNames);
		kyrieGeneratorService.generatorAllCode(tableNames);
		return BaseResult.ok("后端代码全部更新成功，请刷新IDE");
	}
	
	/**
	 * 更新全部api接口代码
	 */
	@RequestMapping("/apicode")
	@ResponseBody
	public BaseResult apicode(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String[] tableNames = new String[]{};
		String tables = request.getParameter("tables");
		tableNames = JSON.parseArray(tables).toArray(tableNames);
		kyrieGeneratorService.generatorApiCode(tableNames);
		return BaseResult.ok("移动端接口全部更新成功，请刷新IDE");
	}
	
	
	/**
	 * 更新代码
	 */
	@ResponseBody
	@RequestMapping("/update")
	public BaseResult update(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String[] tableNames = new String[]{};
		String tables = request.getParameter("tables");
		tableNames = JSON.parseArray(tables).toArray(tableNames);
		kyrieGeneratorService.updateCode(tableNames);
		return BaseResult.ok("代码更新成功，请刷新IDE");
		 
	}
	@GetMapping("/city")
	public String city(){
		return  "city";
	}

	@GetMapping("/qrcode")
	public String qrcode(){
		return "qrcode";
	}

}
