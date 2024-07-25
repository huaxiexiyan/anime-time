package cn.catguild.anime.domain.type;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author xiyan
 * @date 2024/7/24 23:53
 */
@Data
public class PublishInfo {

	private Integer isFinish;

	private Integer isStarted;

	private LocalDateTime pubTime;

	private String pubTimeShow;

	private Integer unknowPubDate;

	private Integer weekday;

}
