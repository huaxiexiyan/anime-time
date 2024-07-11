package cn.catguild.anime.job;

import cn.catguild.anime.domain.Anime;
import cn.catguild.anime.job.domain.BiliBiliSeasonIndex;
import cn.catguild.anime.service.AnimeService;
import cn.catguild.anime.utils.JSONUtils;
import cn.catguild.anime.utils.cache.CacheClient;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author xiyan
 * @date 2024/7/10 20:33
 */
@Slf4j
@AllArgsConstructor
@Component
public class BiliBiliTask {

	private final CacheClient cacheClient;

	private final AnimeService animeService;

	/**
	 * 初始化番剧索引
	 */
	public void initSeasonIndexTask() {
		List<String> keys = cacheClient.getKeys("bilibili:*");
		keys.forEach(key -> {
			try {
				String jsonValue = cacheClient.getValue(key);
				JsonNode jsonTree = JSONUtils.toJsonTree(jsonValue);
				String jsonList = jsonTree.get("data").get("list").toString();
				List<BiliBiliSeasonIndex> pojo = JSONUtils.toList(jsonList, BiliBiliSeasonIndex.class);
				pojo.forEach(index -> {
					Anime anime = new Anime();
					BeanUtils.copyProperties(index, anime);
					anime.setSeasonId(index.getSeason_id());
					animeService.add(anime);
				});
			} catch (Exception e) {
				log.error("任务执行异常，异常键【{}】", key, e);
			}
		});
	}

}
