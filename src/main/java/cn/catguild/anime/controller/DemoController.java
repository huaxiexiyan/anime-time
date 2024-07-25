package cn.catguild.anime.controller;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author xiyan
 * @date 2024/7/7 14:44
 */
@RequestMapping("/demo")
@RestController
public class DemoController {

	@Scheduled
	@GetMapping("/test1")
	public Map<String, Object> demo(){
		Map<String,Object> map = new HashMap<>();
		map.put("data",new HashMap<>(){{put("title","测试成功");put("code","200");}});
		map.put("status", 200);
		map.put("isSuccess", true);
		return map;
	}

}
