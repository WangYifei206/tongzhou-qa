package com.ruoyi.web.controller.tongzhou;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.system.domain.tongzhou.TzCategory;
import com.ruoyi.system.service.tongzhou.ITzCommunityService;

@RestController
@RequestMapping("/tongzhou/category")
public class TzCategoryController extends BaseController
{
    @Autowired
    private ITzCommunityService communityService;

    @PreAuthorize("@ss.hasPermi('tongzhou:category:list')")
    @GetMapping("/list")
    public TableDataInfo list(TzCategory category)
    {
        startPage();
        List<TzCategory> list = communityService.selectCategoryList(category);
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('tongzhou:category:query')")
    @GetMapping("/{categoryId}")
    public AjaxResult getInfo(@PathVariable Long categoryId)
    {
        return AjaxResult.success(communityService.selectCategoryById(categoryId));
    }

    @PreAuthorize("@ss.hasPermi('tongzhou:category:add')")
    @PostMapping
    public AjaxResult add(@RequestBody TzCategory category)
    {
        category.setCreateBy(SecurityUtils.getUsername());
        return toAjax(communityService.insertCategory(category));
    }

    @PreAuthorize("@ss.hasPermi('tongzhou:category:edit')")
    @PutMapping
    public AjaxResult edit(@RequestBody TzCategory category)
    {
        category.setUpdateBy(SecurityUtils.getUsername());
        return toAjax(communityService.updateCategory(category));
    }

    @PreAuthorize("@ss.hasPermi('tongzhou:category:remove')")
    @DeleteMapping("/{categoryIds}")
    public AjaxResult remove(@PathVariable Long[] categoryIds)
    {
        return toAjax(communityService.deleteCategoryByIds(categoryIds));
    }
}
