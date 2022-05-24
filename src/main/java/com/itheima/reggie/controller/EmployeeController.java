package com.itheima.reggie.controller;


import com.itheima.reggie.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 员工信息 前端控制器
 * </p>
 *
 * @author WilsonEdwards
 * @since 2022-05-24
 */
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
}
