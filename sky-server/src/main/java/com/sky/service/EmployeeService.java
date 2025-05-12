package com.sky.service;

import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.result.PageResult;

public interface EmployeeService {

    /**
     * 员工登录
     * @param employeeLoginDTO
     * @return
     */
    Employee login(EmployeeLoginDTO employeeLoginDTO);

    /**
     * @description: 添加员工
     * @author: CyberCaelum
     * @date: 2025/5/10 at 16:37:07
     * @param: employeeLoginDTO
     **/
    void save(EmployeeDTO employeeDTO);

    /**
     * @description: 分页查询
     * @author: CyberAstra
     * @date: 2025/5/11 at 15:39:45
     * @param: employeePageQueryDTO
     * @return: com.sky.result.PageResult
     **/
    PageResult pageQuery(EmployeePageQueryDTO employeePageQueryDTO);

    /**
     * @description: 启用禁用员工
     * @author: CyberAstra
     * @date: 2025/5/12 at 19:56:27
     * @param: status
     * @param: id
     **/
    void startOrStop(Integer status, Long id);

    /**
     * @description: 通过id查找员工
     * @author: CyberAstra
     * @date: 2025/5/12 at 21:18:08
     * @param: id
     * @return: com.sky.entity.Employee
     **/
    Employee selectById(Long id);

    /**
     * @description: 修改员工信息
     * @author: CyberAstra
     * @date: 2025/5/12 at 21:45:45
     * @param: employeeDTO
     **/
    void update(EmployeeDTO employeeDTO);
}
