package com.murray.dao;

import com.murray.entity.ChatUserDepartment;
import com.murray.entity.example.ChatUserDepartmentExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface ChatUserDepartmentDao extends MyBatisBaseDao<ChatUserDepartment,Long, ChatUserDepartmentExample>{
    @Select("SELECT chat_department.`name` FROM `chat_department` INNER JOIN chat_user_department ON chat_user_department.user_no = #{userNo} and chat_department.department_no = chat_user_department.department_no;")
    String selectDepartmentNameByUserNo(@Param("userNo")String userNo);
}