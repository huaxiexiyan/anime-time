package cn.catguild.anime.controller;

import cn.catguild.anime.common.ApiPage;
import cn.catguild.anime.common.ApiResponse;
import cn.catguild.anime.paramsresolver.ParameterConvertUnderlineToHump;
import cn.catguild.anime.domain.Anime;
import cn.catguild.anime.domain.AnimeCondition;
import cn.catguild.anime.domain.query.AnimePageQuery;
import cn.catguild.anime.job.BiliBiliApplicationRunner;
import cn.catguild.anime.service.AnimeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author xiyan
 * @date 2024/7/7 20:44
 */
@Slf4j
@RequestMapping("/anime/index")
@RestController
@AllArgsConstructor
public class AnimeController {

	private final AnimeService animeService;

	private final BiliBiliApplicationRunner biliBiliApplicationRunner;

	@GetMapping("/init")
	public ApiResponse<Void> init() {
		biliBiliApplicationRunner.run();
		return ApiResponse.ok();
	}

	@GetMapping("")
	public ApiResponse<ApiPage<Anime>> page(@ParameterConvertUnderlineToHump AnimePageQuery animePageQuery) {
		return ApiResponse.ok(animeService.page(animePageQuery));
	}

	@GetMapping("/condition")
	public ApiResponse<List<AnimeCondition>> condition() {
		return ApiResponse.ok(animeService.condition());
	}

}
