package com.beat.back.mapper;

import com.beat.back.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper {
	 @Select("select * from users where username = '${name}';")
	 public User selectByName(@Param("name") String name);
	 @Insert("INSERT INTO users (user_id,username,pw) values(null,#{user.username},#{user.password})")
	 public  int insertUsers(@Param("user") User user);

}
