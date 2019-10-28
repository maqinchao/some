package com.mqc.ioc.bean;

import com.mqc.ioc.annotation.Component;
import lombok.Getter;
import lombok.Setter;

@Component
@Setter
@Getter
public class Address {
    private String street;
}
