package com.txp.executer_control.domain;

import lombok.Data;

@Data
public class ResponseAdbDto {
    //响应状态
    Integer code;

    //需要执行的Adb命令
    String AdbCommand;

    //当前调度是否需要暂停几秒钟(当服务器压力大时, 根据这个值设定单个客户端休眠时长, 以减小服务端压力)
    Integer threadSleepTime;

    //当前命令执行后，是否需要将消息推送到消息队列
    boolean toMq;
}
