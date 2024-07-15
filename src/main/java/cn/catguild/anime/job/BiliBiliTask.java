package cn.catguild.anime.job;

import cn.catguild.anime.domain.Anime;
import cn.catguild.anime.domain.AnimeCondition;
import cn.catguild.anime.job.domain.BiliBiliSeasonIndex;
import cn.catguild.anime.job.domain.BiliBiliSeasonIndexCondition;
import cn.catguild.anime.service.AnimeService;
import cn.catguild.anime.utils.JSONUtils;
import cn.catguild.anime.utils.cache.CacheClient;
import cn.catguild.anime.utils.id.IdGenerator;
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

	private final IdGenerator idGenerator;

	/**
	 * 初始化番剧索引
	 */
	public void initSeasonIndexResultTask() {
		List<String> keys = cacheClient.getKeys("bilibili:season:index:result:success:*");
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

	/**
	 * 初始化番剧筛选
	 */
	public void initSeasonIndexConditionTask() {
		log.info("初始化筛选列表任务执行结束");
		try {
			String jsonValue = cacheClient.getValue("bilibili:season:index:condition:success");
			JsonNode jsonTree = JSONUtils.toJsonTree(jsonValue);
			String jsonList = jsonTree.get("data").get("filter").toString();
			List<BiliBiliSeasonIndexCondition> pojo = JSONUtils.toList(jsonList, BiliBiliSeasonIndexCondition.class);
			final int[] sort = {0};
			pojo.forEach(index -> {
				try {
					sort[0]++;
					AnimeCondition animeFilter = new AnimeCondition();
					BeanUtils.copyProperties(index, animeFilter);
					animeFilter.setId(idGenerator.nextId());
					List<BiliBiliSeasonIndexCondition> values = index.getValues();
					final int[] sortChild = {0};
					values.forEach(value -> {
						sortChild[0]++;
						AnimeCondition animeFilterChild = new AnimeCondition();
						animeFilterChild.setId(idGenerator.nextId());
						animeFilterChild.setParentId(animeFilter.getId());
						BeanUtils.copyProperties(value, animeFilterChild);
						animeFilterChild.setSort(sortChild[0]);
						animeService.addCondition(animeFilterChild);
					});
					animeFilter.setSort(sort[0]);
					animeService.addCondition(animeFilter);
				} catch (Exception e) {
					log.error("任务执行异常,filed=【{}】", index.getField(), e);
				}
			});
		} catch (Exception e) {
			log.error("任务执行异常", e);
		}
		log.info("初始化筛选列表任务执行结束");
	}

}
