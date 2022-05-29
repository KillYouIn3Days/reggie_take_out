package com.itheima.reggie.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.reggie.common.R;
import com.itheima.reggie.dto.DishDto;
import com.itheima.reggie.entity.Dish;
import com.itheima.reggie.entity.DishFlavor;
import com.itheima.reggie.service.CategoryService;
import com.itheima.reggie.service.DishFlavorService;
import com.itheima.reggie.service.DishService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 菜品管理 前端控制器
 * </p>
 *
 * @author WilsonEdwards
 * @since 2022-05-24
 */
@RestController
@RequestMapping("/dish")
public class DishController {

    @Autowired
    DishService dishService;

    @Autowired
    DishFlavorService dishFlavorService;

    @Autowired
    CategoryService categoryService;

    @GetMapping("/page")
    public R<Page<DishDto>> page(int page, int pageSize, String name) {
        Page<Dish> dishPage = new Page<>(page, pageSize);
        dishService.page(dishPage, new QueryWrapper<Dish>().like(StringUtils.isNotEmpty(name), "name", name).orderByDesc("update_time"));

        Page<DishDto> dishDtoPage = new Page<>();
        /**
         * protected List<T> records;
         * records里保存的就是Dish、DishDTO对象，拷贝的时候，单独处理
         */
        BeanUtils.copyProperties(dishPage, dishDtoPage, "records");
        List<DishDto> dishDtos = dishPage.getRecords().stream().map((item) -> {
            /**
             * 因为Dish里只存了categoryId，没有前端要的categoryName
             * 所以我们根据categoryId查一下categoryName
             */
            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(item, dishDto);
            Long categoryId = item.getCategoryId();
            String categoryName = categoryService.getById(categoryId).getName();
            dishDto.setCategoryName(categoryName);
            return dishDto;
        }).collect(Collectors.toList());
        dishDtoPage.setRecords(dishDtos);
        return R.success(dishDtoPage);
    }

    @PostMapping
    public R<String> save(@RequestBody DishDto dishDto) {
        dishService.saveWithFlavors(dishDto);
        return R.success("新增菜品成功");
    }

    @GetMapping("/{id}")
    public R<DishDto> get(@PathVariable Long id) {
        DishDto dishDto = dishService.getByIdWithFlavors(id);
        return R.success(dishDto);
    }

    @PutMapping
    public R<String> update(@RequestBody DishDto dishDto) {
        dishService.updateWithFlavors(dishDto);
        return R.success("新增菜品成功");
    }

    @DeleteMapping
    public R<String> delete(Long[] ids) {
        dishService.removeByIds(Arrays.asList(ids));
        return R.success("删除成功");
    }

    @PostMapping("/status/{status}")
    public R<String> changeStatus(@PathVariable int status, Long[] ids) {
        dishService.update(new UpdateWrapper<Dish>().in("id", ids).set("status", status));
        return R.success("修改成功");
    }

    @GetMapping("/list")
    public R<List<Dish>> list(Long categoryId) {
        List<Dish> dishList = dishService.list(new QueryWrapper<Dish>().eq("category_id", categoryId).eq("status", 1));
        return R.success(dishList);
    }

}
