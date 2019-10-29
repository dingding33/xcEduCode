package com.xuecheng.manage_cms.config;

import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.context.annotation.Configuration;

/**
 * Description: Rabbitmq 服务端配置
 * @author yindb
 * @date 2019/10/29
 */
@Configuration
public class RabbitmqConfig
{
    /** 交换机名称 */
    public static final String EX_ROUTING_CMS_POSTPAGE = "ex_routing_cms_postpage";

    /**
     * Description: 交换机配置使用 direct 类型
     * @author yindb
     * @date 2019/10/29
     * @return : org.springframework.amqp.core.Exchange
     */
    public Exchange EXCHANGE_TOPICS_INFROM() {
        return ExchangeBuilder.directExchange((EX_ROUTING_CMS_POSTPAGE)).durable(true).build();
    }

}
