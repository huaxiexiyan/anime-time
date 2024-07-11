package cn.catguild.anime.controller;

import cn.catguild.anime.common.ApiPage;
import cn.catguild.anime.common.ApiResponse;
import cn.catguild.anime.domain.Anime;
import cn.catguild.anime.domain.query.AnimePageQuery;
import cn.catguild.anime.service.AnimeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

	@GetMapping("")
	public ApiResponse<ApiPage<Anime>> page(@ModelAttribute AnimePageQuery animePageQuery){
		return ApiResponse.ok(animeService.page(animePageQuery));
	}

}
