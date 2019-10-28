package com.mqc.ioc.bean;

import com.mqc.ioc.annotation.Autowired;
import com.mqc.ioc.annotation.Component;
import com.mqc.ioc.factory.InitializingBean;
import lombok.Getter;
import lombok.Setter;

@Component
@Setter
@Getter
public class User implements InitializingBean {
    @Autowired
    private Address address;

    @Override
    public void afterPropertiesSet() {
        address.setStreet("street");
    }
}
