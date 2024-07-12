package cn.catguild.anime.job;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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


	public void run()  {
		new Thread(biliBiliTask::initSeasonIndexTask).start();
	}


}
