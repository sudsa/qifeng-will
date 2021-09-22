package com.qifeng.will.inter;

public class UserManagerImpl implements UserManager {
    @Override
    public void addUser(String id, String name) {
        System.out.println(String.format("调用了addUser方法,id=[%s],name=[%s]",id,name));
    }

    @Override
    public void delUser(String id) {
        System.out.println(String.format("调用了delUser方法delete_id=[%s]",id));
    }
}
