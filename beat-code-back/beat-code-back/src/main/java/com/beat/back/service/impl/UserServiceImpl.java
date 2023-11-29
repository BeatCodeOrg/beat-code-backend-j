package com.beat.back.service.impl;

import com.beat.back.mapper.UserMapper;
import com.beat.back.pojo.ResponseData;
import com.beat.back.pojo.User;
import com.beat.back.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl  implements UserService {
 	 @Autowired
	UserMapper userMapper;
	// send back the userID from database
	public ResponseData createUser(User user) {
	   boolean ifAccountExit = userMapper.selectByName(user.getUsername()) != null;
		if(ifAccountExit){
			return  ResponseData.repeatDataResponse("账号已经存在");
		}
		 userMapper.insertUsers(user);
		return  ResponseData.successResponse("添加账号"+user.getUsername()+"成功",null);
	}

	 public  ResponseData loginUser(User user) {
		//查询数据库用户的data
		 User userInDb =  userMapper.selectByName(user.getUsername());
		 System.out.println(userInDb.toString());
		 boolean loginFailed = userInDb == null || !(userInDb.getPassword().equals(user.getPassword()));
		 if(loginFailed)  return  ResponseData.failedResponse("登录失败!请检查账户或密码!",userInDb);
		 ResponseData responseData = ResponseData.successResponse("登录成功",null);
		 return responseData;
	 }





	// Other user-related methods can be added here
}