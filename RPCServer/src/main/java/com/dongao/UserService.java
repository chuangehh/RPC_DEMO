package com.dongao;

/**
 * Created by lcc on 2017/6/24.
 */
public class UserService implements IUser {
    @Override
    public String getUserName(String name) {
        return name +"，你好啊";
    }

    @Override
    public User getUser(Long id) {
        return new User(id,"东奥："+id);
    }
}
