package cn.catguild.anime.repository.mapper;

import cn.catguild.anime.domain.Anime;
import cn.catguild.anime.domain.query.AnimePageQuery;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * app实体表 Mapper 接口
 * </p>
 *
 * @author xiyan
 * @since 2023-10-22
 */
public interface AnimeMapper extends BaseMapper<Anime> {

	@Override
	int insert(@Param("et") Anime anime);

	void updateByHashId(@Param("hashId") String hashId, @Param("et") Anime anime);

	IPage<Anime> selectCustomPage(IPage<Anime> ipage, @Param("q") AnimePageQuery animePageQuery);

}
