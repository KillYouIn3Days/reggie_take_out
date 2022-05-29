package com.itheima.reggie.service;

import com.itheima.reggie.dto.DishDto;
import com.itheima.reggie.entity.Dish;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 菜品管理 服务类
 * </p>
 *
 * @author WilsonEdwards
 * @since 2022-05-24
 */
public interface DishService extends IService<Dish> {

    void saveWithFlavors(DishDto dishDto);

    DishDto getByIdWithFlavors(Long id);

    void updateWithFlavors(DishDto dishDto);
}
