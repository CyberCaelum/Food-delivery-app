package com.sky.service.impl;

import com.sky.context.BaseContext;
import com.sky.entity.AddressBook;
import com.sky.mapper.AddressBookMapper;
import com.sky.service.AddressBookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName : AddressBookServiceImpl
 * @Description : 地址簿service
 * @Author :  CyberCaelum
 * @Date: 2025-07-10 07:24
 */
@Service
@Slf4j
public class AddressBookServiceImpl implements AddressBookService {

    @Autowired
    private AddressBookMapper addressBookMapper;

    /**
     * @description: 新增地址
     * @author: CyberAstra
     * @date: 2025/7/10 at 07:26:34
     * @param: addressBook
     **/
    @Override
    public void addAddressBook(AddressBook addressBook) {
        Long userId = BaseContext.getCurrentId();
        addressBook.setUserId(userId);
        addressBook.setIsDefault(0);
        addressBookMapper.add(addressBook);

    }

    /**
     * @description: 查询当前登录用户的所有地址信息
     * @author: CyberAstra
     * @date: 2025/7/10 at 07:39:59
     * @return: java.util.List<com.sky.entity.AddressBook>
     **/
    @Override
    public List<AddressBook> listAddressBook() {
        Long userId = BaseContext.getCurrentId();
        List<AddressBook> list = addressBookMapper.list(userId);
        return list;
    }

    /**
     * @description: 查询默认地址
     * @author: CyberAstra
     * @date: 2025/7/10 at 07:44:16
     * @return: com.sky.entity.AddressBook
     **/
    @Override
    public AddressBook defaultlAddressBook() {
        Long userId = BaseContext.getCurrentId();
        List<AddressBook> list = addressBookMapper.list(userId);
        for (AddressBook addressBook : list) {
            if (addressBook.getIsDefault() == 1) {
                return addressBook;
            }
        }
        return null;
    }

    /**
     * @description: 根据id修改地址
     * @author: CyberAstra
     * @date: 2025/7/10 at 08:08:53
     * @param: addressBook
     **/
    @Override
    public void updateAddressBook(AddressBook addressBook) {
        addressBookMapper.update(addressBook);
    }

    /**
     * @description: 根据id删除地址
     * @author: CyberAstra
     * @date: 2025/7/10 at 08:24:53
     * @param: id
     **/
    @Override
    public void deleteById(Long id) {
        addressBookMapper.deleteById(id);
    }

    /**
     * @description: 根据id查询地址
     * @author: CyberAstra
     * @date: 2025/7/10 at 08:30:15
     * @param: id
     * @return: com.sky.entity.AddressBook
     **/
    @Override
    public AddressBook getById(Long id) {
        return addressBookMapper.getById(id);
    }

    /**
     * @description: 设置默认地址
     * @author: CyberAstra
     * @date: 2025/7/10 at 08:34:54
     * @param: id
     **/
    @Override
    public void setDefault(AddressBook addressBook) {
        addressBook.setIsDefault(1);
        addressBookMapper.update(addressBook);
    }
}
