package com.txp.executer_control.domain;

import lombok.Data;

@Data
public class RequestAdb {
    //执行的命令代码(对应数据库中的命令ID)
    String commandCode;

    //用户名
    String user;

    //密码
    String password;

    //图片
    String imageBase64;

}
