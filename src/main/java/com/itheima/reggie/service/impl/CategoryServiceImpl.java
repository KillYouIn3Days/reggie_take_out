package com.itheima.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.itheima.reggie.common.CustomException;
import com.itheima.reggie.entity.Category;
import com.itheima.reggie.entity.Dish;
import com.itheima.reggie.entity.Setmeal;
import com.itheima.reggie.mapper.CategoryMapper;
import com.itheima.reggie.service.CategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.reggie.service.DishService;
import com.itheima.reggie.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 菜品及套餐分类 服务实现类
 * </p>
 *
 * @author WilsonEdwards
 * @since 2022-05-24
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    DishService dishService;
    @Autowired
    SetmealService setmealService;

    /**
     * 删除前判断：当前Category是否已关联菜品、套餐
     *
     * @param id
     */
    @Override
    public void remove(Long id) {
        int dish = dishService.count(new QueryWrapper<Dish>().eq("category_id", id));
        if (dish != 0) {
            throw new CustomException("当前分类下关联了菜品，不能删除");
        }
        int setMeal = setmealService.count(new QueryWrapper<Setmeal>().eq("category_id", id));
        if (setMeal != 0) {
            throw new CustomException("当前分类下关联了套餐，不能删除");
        }
        this.removeById(id);
    }
}
