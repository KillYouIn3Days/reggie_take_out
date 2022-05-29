package com.itheima.reggie.service;

import com.itheima.reggie.dto.SetmealDto;
import com.itheima.reggie.entity.Setmeal;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 套餐 服务类
 * </p>
 *
 * @author WilsonEdwards
 * @since 2022-05-24
 */
public interface SetmealService extends IService<Setmeal> {

    void updateWithDish(SetmealDto setmealDto);

    SetmealDto getWithDish(Long id);

    void saveWithDish(SetmealDto dto);

    void removeWithDish(Long[] ids);
}
