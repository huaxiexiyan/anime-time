package cn.catguild.anime.job;

import cn.catguild.anime.domain.Anime;
import cn.catguild.anime.domain.AnimeCondition;
import cn.catguild.anime.domain.type.AnimeStyle;
import cn.catguild.anime.domain.type.AnimeTypeEnum;
import cn.catguild.anime.domain.type.AreaEnum;
import cn.catguild.anime.domain.type.PublishInfo;
import cn.catguild.anime.job.domain.BiliBiliSeason;
import cn.catguild.anime.job.domain.BiliBiliSeasonIndexCondition;
import cn.catguild.anime.service.AnimeService;
import cn.catguild.anime.utils.JSONUtils;
import cn.catguild.anime.utils.cache.CacheClient;
import cn.catguild.anime.utils.id.IdGenerator;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.hash.Hashing;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
	 * 初始化番剧详情
	 */
	@Async
	public void initSeasonIndexDetailsTask() {
		log.info("<<<<<<============ 初始化任务【哔哩哔哩番剧详情】执行【开始】 ============>>>>>>");
		// 1、从redis获取数据
		try {
			List<String> keys = cacheClient.getKeys("bilibili:season:details:*");
			int total = keys.size();
			final int[] current = {0};
			keys.forEach(key -> {
				try {
					log.info("<<<<<<============ 初始化任务【哔哩哔哩番剧详情】执行进度【{}%】 ============>>>>>>",
						new BigDecimal(current[0]).divide(new BigDecimal(total), 4, RoundingMode.HALF_UP).multiply(new BigDecimal("100")).setScale(2, RoundingMode.HALF_UP));
					// String jsonValue = cacheClient.getValue("bilibili:season:details:1342");
					String jsonValue = cacheClient.getValue(key);
					JsonNode jsonTree = JSONUtils.toJsonTree(jsonValue);
					JsonNode resultNode = jsonTree.get("result");
					BiliBiliSeason season = JSONUtils.toPojo(resultNode.toString(), BiliBiliSeason.class);
					Anime anime = convertAnime(season);
					// 依据标题计算出唯一
					if (animeService.isExistByHashId(anime.getHashId())) {
						animeService.updateByHashId(anime.getHashId(), anime);
					} else {
						animeService.add(anime);
					}
				} catch (Exception e) {
					log.error("任务执行异常,redis_key=【{}】", key, e);
				} finally {
					current[0]++;
				}
			});
		} catch (Exception e) {
			log.error("任务执行异常", e);
		}
		log.info("<<<<<<============ 初始化任务【哔哩哔哩番剧详情】执行【结束】 ============>>>>>>");
	}

	private Anime convertAnime(BiliBiliSeason season) {
		Anime anime = new Anime();
		BeanUtils.copyProperties(season, anime);
		String hashId = Hashing.murmur3_128().hashBytes(anime.getTitle().getBytes()).toString();
		anime.setHashId(hashId);
		anime.setSeasonId(season.getSeason_id());
		anime.setHorizontalPicture(season.getHorizontal_picture());
		anime.setType(AnimeTypeEnum.parse(season.getType()));
		if (!CollectionUtils.isEmpty(season.getAreas())) {
			BiliBiliSeason.BiliBiliSeasonArea biliBiliSeasonArea = season.getAreas().get(0);
			AreaEnum areaEnum = AreaEnum.parse(biliBiliSeasonArea.getId());
			if (!areaEnum.getDesc().equals(biliBiliSeasonArea.getName())) {
				throw new RuntimeException("地区枚举错误");
			}
			anime.setArea(areaEnum);
		}
		BiliBiliSeason.BiliBiliSeasonPublishInfo publish = season.getPublish();
		// 风格信息
		String[] styles = season.getStyles();
		if (styles != null && styles.length > 0) {
			List<AnimeStyle> styleList = new ArrayList<>();
			for (String style : styles) {
				AnimeStyle animeStyle = new AnimeStyle();
				animeStyle.setAnimeHashId(anime.getHashId());
				animeStyle.setKeyword(animeService.getStyleKeywordByName(style));
				styleList.add(animeStyle);
			}
			anime.setStyles(styleList);
		}
		// 发布信息
		PublishInfo publishInfo = new PublishInfo();
		publishInfo.setIsStarted(publish.getIs_started());
		publishInfo.setIsFinish(publish.getIs_finish());
		publishInfo.setPubTime(LocalDateTime.parse(publish.getPub_time(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
		publishInfo.setPubTimeShow(publish.getPub_time_show());
		publishInfo.setUnknowPubDate(publish.getUnknow_pub_date());
		publishInfo.setWeekday(publish.getWeekday());
		anime.setPublish(publishInfo);
		return anime;
	}

	/**
	 * 初始化番剧筛选
	 */
	@Async
	public void initSeasonIndexConditionTask() {
		log.info("初始化筛选列表任务执行结束");
		try {
			String jsonValue = cacheClient.getValue("bilibili:season:condition");
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
