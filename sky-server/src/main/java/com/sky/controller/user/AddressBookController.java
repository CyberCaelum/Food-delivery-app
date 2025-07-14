package com.sky.controller.user;

import com.sky.entity.AddressBook;
import com.sky.result.Result;
import com.sky.service.AddressBookService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

/**
 * @ClassName : AddressBookController
 * @Description : C端地址簿接口
 * @Author :  CyberCaelum
 * @Date: 2025-07-10 07:17
 */
@Api(tags = "C端地址簿接口")
@Slf4j
@RestController
@RequestMapping("/user/addressBook")
public class AddressBookController {

    @Autowired
    private AddressBookService addressBookService;

    /**
     * @description: 新增地址
     * @author: CyberAstra
     * @date: 2025/7/10 at 07:26:04
     * @param: addressBook
     * @return: com.sky.result.Result
     **/
    @ApiOperation("新增地址")
    @PostMapping
    public Result addAddressBook(@RequestBody AddressBook addressBook) {
        log.info("新增地址：{}", addressBook);
        addressBookService.addAddressBook(addressBook);
        return Result.success();
    }

    /**
     * @description: 查询当前登录用户的所有地址信息
     * @author: CyberAstra
     * @date: 2025/7/10 at 07:40:34
     * @return: com.sky.result.Result
     **/
    @ApiOperation("查询当前登录用户的所有地址信息")
    @GetMapping("/list")
    public Result listAddressBook() {
        List<AddressBook> list = addressBookService.listAddressBook();
        return Result.success(list);
    }

    /**
     * @description: 查询默认地址
     * @author: CyberAstra
     * @date: 2025/7/10 at 07:42:44
     * @return: com.sky.result.Result
     **/
    @ApiOperation("查询默认地址")
    @GetMapping("/default")
    public Result defaultAddressBook() {
        AddressBook addressBook = addressBookService.defaultlAddressBook();
        return Result.success(addressBook);
    }

    /**
     * @description: 根据id修改地址
     * @author: CyberAstra
     * @date: 2025/7/10 at 08:06:26
     * @param: addressBook
     * @return: com.sky.result.Result
     **/
    @ApiOperation("根据id修改地址")
    @PutMapping
    public Result updateAddressBook(@RequestBody AddressBook addressBook) {
        log.info("修改的地址为{}", addressBook);
        addressBookService.updateAddressBook(addressBook);
        return Result.success();
    }

    /**
     * @description: 根据id删除地址
     * @author: CyberAstra
     * @date: 2025/7/10 at 08:23:43
     * @param: id
     * @return: com.sky.result.Result
     **/
    @ApiOperation("根据id删除地址")
    @DeleteMapping
    public Result deleteAddressBook(Long id) {
        log.info("删除的地址id为：{}",id);
        addressBookService.deleteById(id);
        return Result.success();
    }

    /**
     * @description: 根据id查询地址
     * @author: CyberAstra
     * @date: 2025/7/10 at 08:29:31
     * @param: id
     * @return: com.sky.result.Result
     **/
    @ApiOperation("根据id查询地址")
    @GetMapping("/{id}")
    public Result getAddressBookById(@PathVariable Long id) {
        AddressBook addressBook = addressBookService.getById(id);
        return Result.success(addressBook);
    }

    /**
     * @description: 设置默认地址
     * @author: CyberAstra
     * @date: 2025/7/10 at 08:34:18
     * @param: id
     * @return: com.sky.result.Result
     **/
    @ApiOperation("设置默认地址")
    @PutMapping("/default")
    public Result setDefaultAddressBook(@RequestBody AddressBook addressBook) {
        addressBookService.setDefault(addressBook);
        return Result.success();
    }


}
