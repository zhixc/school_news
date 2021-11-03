package org.zxc.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.zxc.common.lang.Result;
import org.zxc.entity.News;
import org.zxc.service.NewsService;

import java.util.Date;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 知行成
 * @since 2021-07-24
 */
@RestController
@RequestMapping("/news")
public class NewsController {

    @Autowired
    NewsService newsService;

    @GetMapping("/h1")
    public Result hello() {
        return Result.success("OK");
    }

    /**
     * 分页查询新闻
     * @param currentPage 当前页，默认为 1
     * @param pageSize 每页显示多少条数据，默认为3
     * @return
     */
    @GetMapping("/list")
    public Result list(@RequestParam(defaultValue = "1") Integer currentPage,
                       @RequestParam(defaultValue = "3") Integer pageSize) {
        /**
         * 调用page()方法，将 currenPage，pageSize传入
         */
        IPage<News> newsIPage = newsService.page(new Page<News>(currentPage, pageSize));
        /**
         * 返回查询的结果
         */
        return Result.success(200, "查询成功", newsIPage);
    }

    /**
     * 新增新闻
     * @param news
     * @return
     */
    @PostMapping("/save")
    public Result save(@RequestBody News news) {
        System.out.println(news.toString());
        news.setIssueTime(new Date());
        news.setStatus(1);
        news.setUserId(1);
        newsService.save(news);
        return Result.success("新增新闻成功");
    }

    /**
     * 修改新闻
     * @param news
     * @return
     */
    @PutMapping("/update")
    public Result update(@RequestBody News news) {
        news.setUpdateTime(new Date());
        newsService.updateById(news);
        return Result.success("修改新闻成功");
    }

    /**
     * 删除新闻，使用 restful 风格
     * @param id
     * @return
     */
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id) {
        if (id == null) {
            return Result.fail(403, "错误，没有选中要删除的行", null);
        } else {
            newsService.removeById(id);
            return Result.success("删除成功！");
        }
    }

    /**
     * 根据 id 查询新闻
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id) {
        News news = newsService.getById(id);
        return Result.success(news);
    }

}
