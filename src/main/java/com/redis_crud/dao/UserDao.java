package com.redis_crud.dao;

import com.redis_crud.RedisCrudConfiguration;
import com.redis_crud.dto.UserDto;
import redis.clients.jedis.Jedis;

/**
 * Created by kasper on 2/14/14.
 */
public class UserDao {
    final RedisCrudConfiguration configuration = new RedisCrudConfiguration();
    final Jedis jedis = new Jedis(configuration.getRedis().getHostname(),
            configuration.getRedis().getPort());

    public UserDto create (UserDto user) {
        jedis.set(user.getEmail(), user.getName());
        return user;
    }

    public UserDto read (String email) {
        String name = jedis.get(email);
        UserDto user = new UserDto();
        if (!name.isEmpty()) {
            user.setName(name);
            user.setEmail(email);
        }
        return user;
    }

    public UserDto update (String email, UserDto user) {
        if (user.getEmail() != null) {
            jedis.rename(email, user.getEmail());
            email = user.getEmail();
        } else {
            user.setEmail(email);
        }
        jedis.set(email, user.getName());
        return user;
    }

    public Boolean delete (String email) {
        return jedis.del(email) != 0;
    }
}
