package com.txp.executer_control.executer;

import com.alibaba.fastjson.JSON;
import com.txp.executer_control.domain.RequestAdbDto;
import com.txp.executer_control.domain.ResponseAdbDto;
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

    public Executer_Controller(Environment environment, RestTemplate restTemplate){
        this.restTemplate= restTemplate;
        this.environment = environment;
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
            RequestAdbDto requestAdbDto = new RequestAdbDto();
            requestAdbDto.setUser(environment.getProperty("logicuser.name"));
            requestAdbDto.setPassword(environment.getProperty("logicuser.password"));

            log.info(JSON.toJSONString(requestAdbDto));

            ResponseEntity<ResponseAdbDto> resultAdbDtoResponseEntity =
                    restTemplate.postForEntity(environment.getProperty("logicServer.hostAndPort"), requestAdbDto, ResponseAdbDto.class);

            log.info(JSON.toJSONString(resultAdbDtoResponseEntity.getBody()));

        }catch (Exception e){
            e.printStackTrace();
            log.info("消息发送异常");
        }
    }
}
