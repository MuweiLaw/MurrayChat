package com.murray.dao;

import com.murray.entity.ChatCompany;
import com.murray.entity.example.ChatCompanyExample;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface ChatCompanyDao extends MyBatisBaseDao<ChatCompany,Long, ChatCompanyExample>{
}