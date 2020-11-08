package com.junit5;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class junit5demo {

    int a=1;
    int b=2;
    @Test
    void junitcase(){

        Assert.assertEquals(a,b);

        System.out.println("第一个case测试");
    }
}
