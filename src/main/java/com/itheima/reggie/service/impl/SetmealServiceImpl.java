package com.itheima.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.itheima.reggie.common.CustomException;
import com.itheima.reggie.dto.SetmealDto;
import com.itheima.reggie.entity.Setmeal;
import com.itheima.reggie.entity.SetmealDish;
import com.itheima.reggie.mapper.SetmealMapper;
import com.itheima.reggie.service.SetmealDishService;
import com.itheima.reggie.service.SetmealService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 套餐 服务实现类
 * </p>
 *
 * @author WilsonEdwards
 * @since 2022-05-24
 */
@Service
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {

    @Autowired
    SetmealService setmealService;

    @Autowired
    SetmealDishService setmealDishService;

    @Override
    public void updateWithDish(SetmealDto setmealDto) {
        setmealService.updateById(setmealDto);

        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes().stream().map(item -> item.setSetmealId(setmealDto.getId() + "")).collect(Collectors.toList());

        setmealDishService.remove(new QueryWrapper<SetmealDish>().eq("setmeal_id", setmealDto.getId()));
        setmealDishService.saveBatch(setmealDishes);
    }

    @Override
    public SetmealDto getWithDish(Long id) {
        Setmeal setmeal = setmealService.getById(id);
        SetmealDto setmealDto = new SetmealDto();
        BeanUtils.copyProperties(setmeal, setmealDto);
        List<SetmealDish> setmealDishes = setmealDishService.list(new QueryWrapper<SetmealDish>().eq("setmeal_id", id));
        setmealDto.setSetmealDishes(setmealDishes);
        return setmealDto;
    }

    @Override
    public void saveWithDish(SetmealDto dto) {
        setmealService.save(dto);
        List<SetmealDish> setmealDishes = dto.getSetmealDishes();
        //TODO 为什么setmealId是String
        List<SetmealDish> collect = setmealDishes.stream().map(item -> item.setSetmealId(dto.getId() + "")).collect(Collectors.toList());
        setmealDishService.saveBatch(collect);
    }

    @Override
    public void removeWithDish(Long[] ids) {
        int count = this.count(new QueryWrapper<Setmeal>().in("id", ids).eq("status", 1));
        if (count > 0) {
            throw new CustomException("套餐正在售卖中,不能删除");
        }
        this.removeByIds(Arrays.asList(ids));
        setmealDishService.remove(new QueryWrapper<SetmealDish>().in("setmeal_id", ids));

    }
}
