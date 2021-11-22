package com.music.service.servImpl;

import com.music.domain.Consumer;
import com.music.domain.Song;

import java.util.List;

public interface consumerService {
    //增加
    public boolean insert(Consumer consumer);
    //修改
    public boolean update(Consumer consumer);

    //删除
    public boolean delete(Integer id);
    //根据id查询对象
    public Consumer selectConsumerById(Integer id);
    //查询所有客户
    public List<Consumer> allConsumer();
    //根据歌手名字模糊查询
    public List<Consumer> ConsumerOfName(String name);
    //根据性别进行查询
    public List<Consumer> ConsumerOfSex(int sex);
    public Consumer verifyPassword(String username,String password);

}
