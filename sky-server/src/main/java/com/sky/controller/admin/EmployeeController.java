package com.sky.controller.admin;

import com.sky.constant.JwtClaimsConstant;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.properties.JwtProperties;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.EmployeeService;
import com.sky.utils.JwtUtil;
import com.sky.vo.EmployeeLoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 员工管理
 */
@RestController
@RequestMapping("/admin/employee")
@Slf4j
@Api(tags = "员工相关接口")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 登录
     *
     * @param employeeLoginDTO
     * @return
     */
    @ApiOperation("员工登录")
    @PostMapping("/login")
    public Result<EmployeeLoginVO> login(@RequestBody EmployeeLoginDTO employeeLoginDTO) {
        log.info("员工登录：{}", employeeLoginDTO);

        Employee employee = employeeService.login(employeeLoginDTO);

        //登录成功后，生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.EMP_ID, employee.getId());
        String token = JwtUtil.createJWT(
                jwtProperties.getAdminSecretKey(),
                jwtProperties.getAdminTtl(),
                claims);

        EmployeeLoginVO employeeLoginVO = EmployeeLoginVO.builder()
                .id(employee.getId())
                .userName(employee.getUsername())
                .name(employee.getName())
                .token(token)
                .build();

        return Result.success(employeeLoginVO);
    }

    /**
     * 退出
     *
     * @return
     */
    @ApiOperation("员工退出登录")
    @PostMapping("/logout")
    public Result<String> logout() {
        return Result.success();
    }

    /**
     * @description: 添加员工
     * @author: CyberCaelum
     * @date: 2025/5/10 at 16:35:25
     * @param: employeeDTO
     * @return: com.sky.result.Result
     **/
    @ApiOperation("添加员工")
    @PostMapping
    public Result save(@RequestBody EmployeeDTO employeeDTO) {
        log.info("添加员工：{}", employeeDTO);
        employeeService.save(employeeDTO);

        return Result.success();
    }

    /**
     * @description: 分页查询
     * @author: CyberAstra
     * @date: 2025/5/11 at 15:38:23
     * @param: employeePageQueryDTO
     * @return: com.sky.result.Result<com.sky.result.PageResult>
     **/
    @GetMapping("/page")
    @ApiOperation("分页查询")
    public Result<PageResult> page(EmployeePageQueryDTO employeePageQueryDTO) {
        log.info("分页查询参数:{}", employeePageQueryDTO);
        PageResult pageResult = employeeService.pageQuery(employeePageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * @description: 启用禁用员工
     * @author: CyberAstra
     * @date: 2025/5/12 at 19:54:15
     * @param: status
     * @param: id
     * @return: com.sky.result.Result
     **/
    @ApiOperation("启用禁用员工")
    @PostMapping("/status/{status}")
    public Result startOrStop(@PathVariable Integer status,Long id){
        log.info("启用禁用员工：状态{},员工id{}", status, id);
        employeeService.startOrStop(status,id);
        return Result.success();
    }

    /**
     * @description: 通过id查找员工
     * @author: CyberAstra
     * @date: 2025/5/12 at 21:15:47
     * @param: id
     * @return: com.sky.result.Result<com.sky.entity.Employee>
     **/
    @ApiOperation("通过id查找员工")
    @GetMapping("/{id}")
    public Result<Employee> selectById(@PathVariable Long id){
        log.info("查找员工：id：{}",id);
        Employee employee = employeeService.selectById(id);
        return Result.success(employee);
    }

    /**
     * @description: 修改员工
     * @author: CyberAstra
     * @date: 2025/5/12 at 21:53:44
     * @param: employeeDTO
     * @return: com.sky.result.Result
     **/
    @ApiOperation("修改员工")
    @PutMapping
    public Result update(@RequestBody EmployeeDTO employeeDTO) {
        log.info("修改员工：{}", employeeDTO);
        employeeService.update(employeeDTO);
        return Result.success();
    }
}
