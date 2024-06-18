package com.example.springboot.service;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springboot.common.Result;
import com.example.springboot.entity.User;
import com.example.springboot.exception.ServiceException;
import com.example.springboot.mapper.UserMapper;
import com.example.springboot.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 功能：
 * 作者：小曾曾
 * 日期：2024/5/8 23:15
 */
@Service
public class UserService extends ServiceImpl<UserMapper, User> {
//    @Resource
//    UserMapper userMapper;

//    public void insertUser(User user) {
//        userMapper.insert(user);
//    }
//
//    public void updateUser(User user) {
//        userMapper.update(user);
//    }
//
//    public void deleteUser(Integer id) {
//        userMapper.delete(id);
//    }
//
//    public void batchDeleteUser(List<Integer> ids) {
//        for (Integer id : ids) {
//            userMapper.delete(id);
//        }
//    }
//
//    public List<User> selectAll() {
//        return userMapper.selectAll();
//    }
//
//    public User selectById(Integer id) {
//        return userMapper.selectById(id);
//    }
//
//    public List<User> selectByMo(String username, String name) {
//        return userMapper.selectByMo(username, name);
//    }
//
//    public Map<String, Object> selectByPage(Integer pageNum, Integer pageSize, String username, String name) {
//        Integer skipNum = (pageNum - 1) * pageSize;
//        Map<String, Object> result = new HashMap<>();
//        List<User> users = userMapper.selectByPage(skipNum, pageSize, username, name);
//        Integer total = userMapper.selectCountByPage(username, name);
//        result.put("users", users);
//        result.put("total", total);
//        return result;
//    }

    public User selectByUsername(String username) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);//eq是等于的意思  where username = #{username}
        return getOne(queryWrapper); //select * from user where username = #{username}
    }

    public User login(User user) {
        User dbUser = selectByUsername(user.getUsername());
        if(dbUser==null){
            //抛出一个自定义的异常
            throw new ServiceException("用户或密码错误");
        }
        if(!user.getPassword().equals(dbUser.getPassword())){
            throw new ServiceException("用户或密码错误");
        }
        String token = TokenUtils.createToken(dbUser.getId().toString(), dbUser.getPassword());
        dbUser.setToken(token);
        return dbUser;
    }

    public User register(User user) {
        User dbUser = selectByUsername(user.getUsername());
        if(dbUser!=null){
            //抛出一个自定义的异常
            throw new ServiceException("用户名已存在");
        }
        save(user);
        return user;
    }

    public void resetPassword(User user) {
        User dbUser = selectByUsername(user.getUsername());
        if(dbUser==null){
            //抛出一个自定义的异常
            throw new ServiceException("用户不存在");
        }
        if (!user.getPhone().equals(dbUser.getPhone())){
            throw new ServiceException("手机号错误");
        }
        dbUser.setPassword("123");
        updateById(dbUser);
    }
}