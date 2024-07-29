package cn.catguild.anime.controller;

import cn.catguild.anime.common.ApiResponse;
import cn.catguild.anime.job.BiliBiliTask;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xiyan
 * @date 2024/7/26 12:36
 */
@Slf4j
@AllArgsConstructor
@RequestMapping("/init")
@RestController
public class InitController {

	private final BiliBiliTask bilibiliTask;

	@GetMapping("/bilibili/season")
	public ApiResponse<Void> initBiliBiliSeason() {
		bilibiliTask.initSeasonIndexDetailsTask();
		return ApiResponse.ok();
	}

	@GetMapping("/bilibili/season/condition")
	public ApiResponse<Void> initBiliBiliCondition() {
		bilibiliTask.initSeasonIndexConditionTask();
		return ApiResponse.ok();
	}

}
