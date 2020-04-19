package com.meng.configuration.test;

import com.meng.configuration.client.ConfigItemHandler;
import com.meng.configuration.client.Item;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
public class DemoTest implements ConfigItemHandler {


    @Item(key = "spring.key")
    private String key = "3";

    @Item(key = "spring.key.key")
    private String value = "2";


}
