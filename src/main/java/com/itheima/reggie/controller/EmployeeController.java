package com.itheima.reggie.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.reggie.common.R;
import com.itheima.reggie.entity.Employee;
import com.itheima.reggie.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 员工信息 前端控制器
 * </p>
 *
 * @author WilsonEdwards
 * @since 2022-05-24
 */
@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    /**
     * 员工登录
     *
     * @param request  为了存入session
     * @param employee 把传来的参数封装成employee对象，
     *                 username，
     *                 password，
     *                 传入的参数名必须和Employee类里的属性名一致，才能匹配上
     * @return
     */
    @PostMapping("/login")
    public R<Employee> login(HttpServletRequest request, @RequestBody Employee employee) {
        String username = employee.getUsername();
        String password = employee.getPassword();

        Employee emp = employeeService.getOne(new QueryWrapper<Employee>().eq("username", username));

        if (emp == null) {
            return R.error("用户名或密码错误");
        }

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        boolean matches = passwordEncoder.matches(password, emp.getPassword());
        if (!matches) {
            return R.error("用户名或密码错误");
        }
        if (emp.getStatus() == 0) {
            return R.error("账号已禁用");
        }
        request.getSession().setAttribute("employee", emp.getId());
        return R.success(emp);
    }

    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request) {
        request.getSession().removeAttribute("employee");
        return R.success("退出成功");
    }

    @PostMapping
    public R<String> save(@RequestBody Employee employee) {
        log.info("新增员工：员工信息：{}", employee.toString());

        //设置初始密码
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encode = passwordEncoder.encode("123456");
        employee.setPassword(encode);
//        employee.setCreateTime(LocalDateTime.now());
//        employee.setUpdateTime(LocalDateTime.now());
//
//        Long empId = (Long) request.getSession().getAttribute("employee");
//        employee.setCreateUser(empId);
//        employee.setUpdateUser(empId);

        employeeService.save(employee);
        return R.success("添加成功");
    }

    @GetMapping("/page")
    public R<Page<Employee>> page(int page, int pageSize, String name) {
        log.info("page:{},pageSize:{},name:{}", page, pageSize, name);
        Page<Employee> pageInfo = new Page<>(page, pageSize);
        QueryWrapper<Employee> query = new QueryWrapper<Employee>().like(StringUtils.isNotEmpty(name), "name", name);
        query.orderByDesc("update_time");
        employeeService.page(pageInfo, query);
        return R.success(pageInfo);
    }

    @PutMapping
    public R<String> update(@RequestBody Employee employee) {
//        Long emp = (Long) request.getSession().getAttribute("employee");
//        employee.setUpdateUser(emp);
//        employee.setUpdateTime(LocalDateTime.now());
        employeeService.updateById(employee);
        return R.success("员工信息修改成功");
    }

    @GetMapping("/{id}")
    public R<Employee> getById(@PathVariable Long id) {
        Employee emp = employeeService.getOne(new QueryWrapper<Employee>().eq("id", id));
        if (emp != null) {
            return R.success(emp);
        }
        return R.error("用户不存在");
    }
}
