package cn.catguild.anime.job;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 初始化执行
 * public class BiliBiliApplicationRunner implements ApplicationRunner
 *
 * @author xiyan
 * @date 2023/8/11 10:48
 */
@Slf4j
@AllArgsConstructor
@Component
public class BiliBiliApplicationRunner {

	private final BiliBiliTask biliBiliTask;


	@Async
	public void run()  {
		// new Thread(biliBiliTask::initSeasonIndexResultTask).start();
		new Thread(biliBiliTask::initSeasonIndexDetailsTask).start();
	}


}
