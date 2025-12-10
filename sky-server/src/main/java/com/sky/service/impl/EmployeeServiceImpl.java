package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.PasswordConstant;
import com.sky.constant.StatusConstant;
import com.sky.context.BaseContext;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.exception.AccountLockedException;
import com.sky.exception.AccountNotFoundException;
import com.sky.exception.PasswordErrorException;
import com.sky.mapper.EmployeeMapper;
import com.sky.result.PageResult;
import com.sky.service.EmployeeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    /**
     * 员工登录
     *
     * @param employeeLoginDTO
     * @return
     */
    public Employee login(EmployeeLoginDTO employeeLoginDTO) {
        String username = employeeLoginDTO.getUsername();
        String password = employeeLoginDTO.getPassword();

        //1、根据用户名查询数据库中的数据
        Employee employee = employeeMapper.getByUsername(username);

        //2、处理各种异常情况（用户名不存在、密码不对、账号被锁定）
        if (employee == null) {
            //账号不存在
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }

        //密码比对
        //对前端的密码进行md5加密
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!password.equals(employee.getPassword())) {
            //密码错误
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }

//        if (employee.getStatus() == StatusConstant.DISABLE) {
//            //账号被锁定
//            throw new AccountLockedException(MessageConstant.ACCOUNT_LOCKED);
//        }

        //3、返回实体对象
        return employee;
    }

    /**
     * @description: 添加员工
     * @author: CyberCaelum
     * @date: 2025/5/10 at 16:38:37
     * @param: employeeLoginDTO
     **/
    @Override
    public void save(EmployeeDTO employeeDTO) {
        //将employeeDTO前端传入的数据，复制给employee
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO, employee);

        //设置账号状态
        employee.setStatus(StatusConstant.ENABLE);

        //设置账号的默认密码，md5加密
        employee.setPassword(DigestUtils.md5DigestAsHex(PasswordConstant.DEFAULT_PASSWORD.getBytes()));

        //设置账号的创建时间和修改时间
//        employee.setCreateTime(LocalDateTime.now());
//        employee.setUpdateTime(LocalDateTime.now());

        //设置账号的添加人和账号的修改人，取出tread中存储的操作人ID
        employee.setCreateUser(BaseContext.getCurrentId());
        employee.setUpdateUser(BaseContext.getCurrentId());

        employeeMapper.insert(employee);
    }

    /**
     * @description: 分页查询
     * @author: CyberAstra
     * @date: 2025/5/11 at 15:40:14
     * @param: employeePageQueryDTO
     * @return: com.sky.result.PageResult
     **/
    @Override
    public PageResult pageQuery(EmployeePageQueryDTO employeePageQueryDTO) {
        //使用PageHelpper进行分页查询
        PageHelper.startPage(employeePageQueryDTO.getPage(),employeePageQueryDTO.getPageSize());
        Page<Employee> page = employeeMapper.pageQuery(employeePageQueryDTO);

        return new PageResult(page.getTotal(),page.getResult());
    }

    /**
     * @description: 启用禁用员工
     * @author: CyberAstra
     * @date: 2025/5/12 at 19:56:55
     * @param: status
     * @param: id
     **/
    @Override
    public void startOrStop(Integer status, Long id) {
        Employee employee = Employee.builder()
                .id(id)
                .status(status)
//                .updateTime(LocalDateTime.now())
//                .updateUser(BaseContext.getCurrentId())
                .build();

        employeeMapper.update(employee);
    }

    /**
     * @description: 通过id查找员工
     * @author: CyberAstra
     * @date: 2025/5/12 at 21:18:41
     * @param: id
     * @return: com.sky.entity.Employee
     **/
    @Override
    public Employee selectById(Long id) {
        Employee employee = employeeMapper.selectByid(id);
        employee.setPassword("***");
        return employee;
    }

    /**
     * @description: 修改员工信息
     * @author: CyberAstra
     * @date: 2025/5/12 at 21:46:11
     * @param: employeeDTO
     **/
    @Override
    public void update(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO, employee);
//        employee.setUpdateTime(LocalDateTime.now());
//        employee.setUpdateUser(BaseContext.getCurrentId());
        employeeMapper.update(employee);
    }
}
