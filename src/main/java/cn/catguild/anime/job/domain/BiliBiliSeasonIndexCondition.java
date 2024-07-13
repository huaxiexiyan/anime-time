package cn.catguild.anime.job.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author xiyan
 * @date 2024/7/10 21:18
 */
@NoArgsConstructor
@Data
public class BiliBiliSeasonIndexCondition {


	private String name;


	private String field;


	private String keyword;


	private List<BiliBiliSeasonIndexCondition> values;

}
