package com.sky.service;

import com.sky.entity.AddressBook;

import java.util.List;

/**
 * @ClassName : AddressBookService
 * @Description : 地址簿service
 * @Author :  CyberCaelum
 * @Date: 2025-07-10 07:24
 */
public interface AddressBookService {

    /**
     * @description: 新增地址
     * @author: CyberAstra
     * @date: 2025/7/10 at 07:26:20
     * @param: addressBook
     **/
    void addAddressBook(AddressBook addressBook);

    /**
     * @description: 查询当前登录用户的所有地址信息
     * @author: CyberAstra
     * @date: 2025/7/10 at 07:35:11
     * @return: com.sky.entity.AddressBook
     **/
    List<AddressBook> listAddressBook();

    /**
     * @description: 查询默认地址
     * @author: CyberAstra
     * @date: 2025/7/10 at 07:43:59
     * @return: com.sky.entity.AddressBook
     **/
    AddressBook defaultlAddressBook();

    /**
     * @description: 根据id修改地址
     * @author: CyberAstra
     * @date: 2025/7/10 at 08:08:38
     * @param: addressBook
     **/
    void updateAddressBook(AddressBook addressBook);

    /**
     * @description: 根据id删除地址
     * @author: CyberAstra
     * @date: 2025/7/10 at 08:24:43
     * @param: id
     **/
    void deleteById(Long id);

    /**
     * @description: 根据id查询地址
     * @author: CyberAstra
     * @date: 2025/7/10 at 08:30:03
     * @param: id
     * @return: com.sky.entity.AddressBook
     **/
    AddressBook getById(Long id);

    /**
     * @description: 设置默认地址
     * @author: CyberAstra
     * @date: 2025/7/10 at 08:34:43
     * @param: addressBook
     **/
    void setDefault(AddressBook addressBook);
}
