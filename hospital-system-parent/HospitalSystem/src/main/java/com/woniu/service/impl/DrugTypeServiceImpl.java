package com.woniu.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.woniu.entity.DrugType;
import com.woniu.mapper.DrugTypeMapper;
import com.woniu.service.DrugTypeService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service("drugTypeService")
public class DrugTypeServiceImpl implements DrugTypeService {

    @Autowired
    private DrugTypeMapper drugTypeMapper;
    @Autowired
    private StringRedisTemplate redisTemplate;


    @Override
    public List<DrugType> findAll() {
        //先去redis缓存中取
        ObjectMapper obj = new ObjectMapper();
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        String str = ops.get("typeList");
        List<DrugType> drugTypes = null;
        try {
            if (!StringUtils.isEmpty(str)){
                //从缓存中取数据并且转换成List
                drugTypes = obj.readValue(str, new TypeReference<List>() {});
                return drugTypes;
            }
            //去数据库中查
            drugTypes = drugTypeMapper.selectByExample(null);
            //存入redis数据库
            ops.set("typeList",obj.writeValueAsString(drugTypes));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return drugTypes;
    }


    //添加类别
    public void add(DrugType drugType) {
        //添加之前先判断redis有没有
        ObjectMapper obj = new ObjectMapper();
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        String str = ops.get("typeList");
        try {
            if (!StringUtils.isEmpty(str)){
                //从缓存中取数据并且转换成List
                redisTemplate.delete(str);
                drugTypeMapper.insert(drugType);
                List<DrugType> drugTypes = drugTypeMapper.selectByExample(null);
                //存入redis数据库
                ops.set("typeList",obj.writeValueAsString(drugTypes));
            }else {
                drugTypeMapper.insert(drugType);
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }
}
