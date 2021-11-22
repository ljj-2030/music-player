package com.music.service.servImpl.servImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.music.dao.consumerMapper;
import com.music.domain.Consumer;
import com.music.service.servImpl.consumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConsumerServiceImpl extends ServiceImpl<consumerMapper, Consumer> implements consumerService {
    @Autowired
    private consumerMapper consumerMapper;
    @Override
    public boolean insert(Consumer consumer) {
        return consumerMapper.insert(consumer) >0;
    }

    @Override
    public boolean update(Consumer consumer) {
        return consumerMapper.updateById(consumer)>0;
    }

    @Override
    public boolean delete(Integer id) {
        return consumerMapper.deleteById(id)>0;
    }

    @Override
    public Consumer selectConsumerById(Integer id) {
        return consumerMapper.selectById(id);
    }

    @Override
    public List<Consumer> allConsumer() {
        return consumerMapper.selectList(new QueryWrapper<Consumer>());
    }

    @Override
    public List<Consumer> ConsumerOfName(String name) {
        QueryWrapper<Consumer> consumerQueryWrapper = new QueryWrapper<>();
        consumerQueryWrapper.like("username",name);
        return consumerMapper.selectList(consumerQueryWrapper);
    }

    @Override
    public List<Consumer> ConsumerOfSex(int sex) {
        QueryWrapper<Consumer> consumerQueryWrapper = new QueryWrapper<>();
        consumerQueryWrapper.eq("sex",sex);
        return consumerMapper.selectList(consumerQueryWrapper);
    }

    @Override
    public Consumer verifyPassword(String username, String password) {
        QueryWrapper<Consumer> consumerQueryWrapper = new QueryWrapper<>();
        consumerQueryWrapper.eq("username",username).eq("password",password);
        Consumer consumer = consumerMapper.selectOne(consumerQueryWrapper);

        return consumer;
    }
}
