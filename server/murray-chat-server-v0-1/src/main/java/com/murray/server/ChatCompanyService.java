package com.murray.server;

import com.murray.dao.ChatCompanyDao;
import com.murray.entity.example.ChatCompanyExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Murray Law
 * @describe 公司服务
 * @createTime 2020/12/8
 */
@Service
public class ChatCompanyService {
    @Autowired
    private ChatCompanyDao chatCompanyDao;
    private ChatCompanyExample companyExample;

}
