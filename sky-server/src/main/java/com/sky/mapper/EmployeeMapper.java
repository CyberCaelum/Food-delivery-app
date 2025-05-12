package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface EmployeeMapper {

    /**
     * 根据用户名查询员工
     * @param username
     * @return
     */
    @Select("select * from employee where username = #{username}")
    Employee getByUsername(String username);

    /**
     * @description: 添加员工
     * @author: CyberCaelum
     * @date: 2025/5/10 at 16:55:23
     * @param: employee
     **/
    @Insert("insert into employee (name, username, password, phone, sex, id_number, status, create_time, update_time, create_user, update_user) " +
            "VALUE (#{name},#{username},#{password},#{phone},#{sex},#{idNumber},#{status},#{createTime},#{updateTime},#{createUser},#{updateUser})")
    void insert(Employee employee);

    /**
     * @description: 分页查找员工
     * @author: CyberAstra
     * @date: 2025/5/12 at 21:31:23
     * @param: employeePageQueryDTO
     * @return: com.github.pagehelper.Page<com.sky.entity.Employee>
     **/
    Page<Employee> pageQuery(EmployeePageQueryDTO employeePageQueryDTO);

    /**
     * @description: 更新员工
     * @author: CyberAstra
     * @date: 2025/5/12 at 21:31:45
     * @param: employee
     **/
    void update(Employee employee);

    /**
     * @description: 通过id查找员工
     * @author: CyberAstra
     * @date: 2025/5/12 at 21:32:10
     * @param: id
     * @return: com.sky.entity.Employee
     **/
    @Select("select * from employee where id = #{id}")
    Employee selectByid(Long id);
}
