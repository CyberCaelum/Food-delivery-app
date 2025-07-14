package com.sky.mapper;

import com.sky.entity.AddressBook;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @ClassName : AddressBookMapper
 * @Description : 地址簿Mapper
 * @Author :  CyberCaelum
 * @Date: 2025-07-10 07:27
 */

@Mapper
public interface AddressBookMapper {

    /**
     * @description: 新增地址簿
     * @author: CyberAstra
     * @date: 2025/7/10 at 07:31:43
     * @param: addressBook
     **/
    @Insert("insert into address_book " +
            "(user_id, consignee, sex, phone, province_code, province_name, city_code, city_name, district_code, district_name, detail, label,is_default) " +
            "value " +
            "(#{userId},#{consignee},#{sex},#{phone},#{provinceCode},#{provinceName},#{cityCode},#{cityName},#{districtCode},#{districtName},#{detail},#{label},#{isDefault})")
    void add(AddressBook addressBook);

    /**
     * @description: 查询当前登录用户的所有地址信息
     * @author: CyberAstra
     * @date: 2025/7/10 at 07:37:01
     * @param: userId
     * @return: java.util.List<com.sky.entity.AddressBook>
     **/
    @Select("select * from address_book where user_id = #{userId}")
    List<AddressBook> list(Long userId);

    /**
     * @description: 根据id修改地址
     * @author: CyberAstra
     * @date: 2025/7/10 at 08:21:01
     * @param: addressBook
     **/
    void update(AddressBook addressBook);

    /**
     * @description: 根据id删除地址
     * @author: CyberAstra
     * @date: 2025/7/10 at 08:27:37
     * @param: id
     **/
    @Delete("delete from address_book where id = #{id}")
    void deleteById(Long id);

    /**
     * @description: 根据id查询地址
     * @author: CyberAstra
     * @date: 2025/7/10 at 08:31:46
     * @param: id
     * @return: com.sky.entity.AddressBook
     **/
    @Select("select * from address_book where id = #{id}")
    AddressBook getById(Long id);
}
