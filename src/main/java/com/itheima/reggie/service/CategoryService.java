package com.itheima.reggie.service;

import com.itheima.reggie.entity.Category;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 菜品及套餐分类 服务类
 * </p>
 *
 * @author WilsonEdwards
 * @since 2022-05-24
 */
public interface CategoryService extends IService<Category> {
    void remove(Long id);
}
