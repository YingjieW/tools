package com.tools.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 *
 * @author yingjie.wang
 * @since 17/3/23 下午5:21
 */
@Setter
@Getter
@ToString
public class People implements Serializable {

    public static final String TAG = "Test_static_variable";

    public static void main(String[] args) throws Exception {
        People people = null;
        System.out.println(people.TAG);
    }
}

/* -------------------------------------------
 output:
 Test_static_variable
--------------------------------------------*/
