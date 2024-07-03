package com.txp.executer_control.executer;

import com.alibaba.fastjson.JSON;
import com.txp.executer_control.domain.RequestAdb;
import com.txp.executer_control.domain.ResponseAdb;
import com.txp.executer_control.util.ADBExecuterUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * 任务执行器
 */
@Component
@Slf4j
public class Executer_Controller {

    private final Environment environment;

    private final RestTemplate restTemplate;

    //用户名
    private String user;

    //密码
    private String password;

    //请求地址
    private String hostAndPort;

    public Executer_Controller(Environment environment, RestTemplate restTemplate){
        this.restTemplate= restTemplate;
        this.environment = environment;
        this.user = environment.getProperty("logicuser.name");
        this.password = environment.getProperty("logicuser.password");
        this.hostAndPort = environment.getProperty("logicServer.hostAndPort");
    }

    /**
     * 执行主逻辑
     * 每两秒执行询问一次
     *
     * 1.发送请求
     * 2.执行命令 -> 推送消息
     */
    @Scheduled(fixedRate = 2000)
    public void Executer(){
        log.info("定时器执行");
        try{
            RequestAdb requestAdb = new RequestAdb();
            requestAdb.setUser(user);
            requestAdb.setPassword(password);
            log.info(JSON.toJSONString(requestAdb));

            ResponseEntity<ResponseAdb> resultAdbDtoResponseEntity =
                    restTemplate.postForEntity(hostAndPort, requestAdb, ResponseAdb.class);

            log.info(JSON.toJSONString(resultAdbDtoResponseEntity.getBody()));

            if (null != resultAdbDtoResponseEntity.getBody().getCode() &&resultAdbDtoResponseEntity.getBody().getCode() == 200){
                if (resultAdbDtoResponseEntity.getBody().getThreadSleepTime() > 0){
                    log.info("服务器繁忙，下次请求需要的延时时长为：{}毫秒",resultAdbDtoResponseEntity.getBody().getThreadSleepTime());
                    Thread.sleep(resultAdbDtoResponseEntity.getBody().getThreadSleepTime());
                }
                //开始执行ADB命令
                ADBExecuterUtil.execute("","");
                log.info("需要执行的ADB命令为：{}",resultAdbDtoResponseEntity.getBody().getAdbCommand());
            }

        }catch (Exception e){
            e.printStackTrace();
            log.info("消息发送异常，开始重试， 异常消息:{}",e.getMessage());
        }
    }
}
