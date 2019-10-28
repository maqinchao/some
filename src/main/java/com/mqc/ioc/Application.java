package com.mqc.ioc;


import com.mqc.ioc.annotation.Autowired;
import com.mqc.ioc.annotation.IocStarter;
import com.mqc.ioc.annotation.PackageScan;
import com.mqc.ioc.bean.User;
import com.mqc.ioc.command.Command;
import com.mqc.ioc.run.IocUtil;


@IocStarter
@PackageScan("com.mqc.ioc")
public class Application implements Command {
    public static void main(String[] args){
        IocUtil.run(Application.class);
    }

    @Autowired
    private User user;
    @Override
    public void command() {
        System.out.println(user.getAddress());
    }
}
