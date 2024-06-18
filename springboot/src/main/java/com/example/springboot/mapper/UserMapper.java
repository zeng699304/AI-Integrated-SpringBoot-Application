package com.example.springboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.mapper.Mapper;
import com.example.springboot.entity.User;
import lombok.Data;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface UserMapper extends BaseMapper<User> {






//    @Insert("insert into user(username, password, name, phone, email, address, avatar,role) " +
//            "values(#{username}, #{password}, #{name}, #{phone}, #{email}, #{address}, #{avatar})")
//    void insert(User user);
//
//    @Update("update user set username=#{username}, password=#{password}, name=#{name}," +
//            " phone=#{phone}, email=#{email}, address=#{address}, avatar=#{avatar} where id=#{id}")
//    void update(User user);
//
//    @Delete("delete from user where id=#{id}")
//    void delete(Integer id);
//
//    @Select("select * from user")
//    List<User> selectAll();
//
//    @Select("select * from user where id=#{id}")
//    User selectById(Integer id);
//
//    @Select("select * from user where username like concat('%', #{username}, '%') and name like concat('%', #{name}, '%')")
//    List<User> selectByMo(@Param("username") String username, @Param("name") String name);
//
//    @Select("select * from user where username like concat('%', #{username}, '%') and name like concat('%', #{name}, '%') limit #{skipNum}, #{pageSize}")
//    List<User> selectByPage(@Param("skipNum") Integer skipNum, @Param("pageSize") Integer pageSize, @Param("username") String username, @Param("name") String name);
//
//    @Select("select count(id) from user where username like concat('%', #{username}, '%') and name like concat('%', #{name}, '%')")
//    Integer selectCountByPage(@Param("username") String username, @Param("name") String name);
//
//    @Select("select * from user where username=#{username}")
//    User selectByUsername(String username);
}
