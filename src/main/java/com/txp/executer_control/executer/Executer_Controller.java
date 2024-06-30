package com.txp.executer_control.executer;

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
     */
    @Scheduled(fixedRate = 2000)
    public void Executer(){
        log.info("定时器执行");
        try{
            ResponseEntity<ResponseAdbDto> resultAdbDtoResponseEntity =
                    restTemplate.postForEntity(environment.getProperty("logicServer.hostAndPort"), "", ResponseAdbDto.class);

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
