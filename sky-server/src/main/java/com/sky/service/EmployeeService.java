package com.sky.service;

import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.entity.Employee;

public interface EmployeeService {

    /**
     * 员工登录
     * @param employeeLoginDTO
     * @return
     */
    Employee login(EmployeeLoginDTO employeeLoginDTO);

    /**
     * @description: 添加员工
     * @author: CyberAstra
     * @date: 2025/5/10 at 16:37:07
     * @param: employeeLoginDTO
     **/
    void save(EmployeeDTO employeeDTO);
}
