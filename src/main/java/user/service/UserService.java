package user.service;

/**
 * @author Jingzhou Ou
 * Created on 17-11-3
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import user.dao.UserMapper;
import user.model.User;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    @Cacheable("device")
    public User getUserById(Integer id) {
        return userMapper.selectByPrimaryKey(id);
    }

    public User getUserByName(String name) {
        return userMapper.selectByName(name);
    }

    @CacheEvict(value = "device", allEntries = true)
    public int updateUser(User user) {
        return userMapper.updateByPrimaryKeySelective(user);
    }

    public int deleteUserById(Integer id) {
        return userMapper.deleteByPrimaryKey(id);
    }

    public int insertUser(User user) {
        return userMapper.insertSelective(user);
    }
}
