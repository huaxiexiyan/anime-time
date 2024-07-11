package cn.catguild.anime.job;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * 初始化执行
 *
 * @author xiyan
 * @date 2023/8/11 10:48
 */
@Slf4j
@AllArgsConstructor
@Component
public class BiliBiliApplicationRunner implements ApplicationRunner {

	private final BiliBiliTask biliBiliTask;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		// new Thread(biliBiliTask::initSeasonIndexTask).start();
	}


}
