package org.zxc.service.impl;

import org.zxc.entity.News;
import org.zxc.mapper.NewsMapper;
import org.zxc.service.NewsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 知行成
 * @since 2021-07-24
 */
@Service
public class NewsServiceImpl extends ServiceImpl<NewsMapper, News> implements NewsService {

}
