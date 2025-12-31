
package com.swosti.config;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionBlockedEvent;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionUnblockedEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
public class RabbitMQConfig {

    public static final String LOGIN_FANOUT_EXCHANGE = "Login_fanout_exchange";
    public static final String QUESTION_UPLOAD_FANOUT_EXCHANGE = "question_upload_fanout_exchange";
    public static final String SHUFFLE_DIRECT_EXCHANGE = "shuffle_direct_exchange";

    public static final String LOGIN_DB_QUEUE = "Login_db_queue";
    public static final String LOGIN_ADMIN_CACHE_QUEUE = "Login_admin_cache_queue";
    public static final String LOGIN_BRANCH_CACHE_QUEUE = "Login_branch_cache_queue";
    public static final String LOGIN_USER_CACHE_REDIS_QUEUE = "Login_user_cache_redis_queue";


    public static final String QUESTION_UPLOAD_DB_QUEUE = "question_upload_db_queue";
    public static final String SHUFFLE_DB_QUEUE = "shuffle_db_queue";
    public static final String SHUFFLE_CACHE_QUEUE = "shuffle_cache_queue";


    @Bean
    public FanoutExchange loginFanoutExchange() {

        return new FanoutExchange(LOGIN_FANOUT_EXCHANGE, true, false);
    }
    // -------- Question Upload Exchange--------
    @Bean
    public FanoutExchange questionUploadFanoutExchange() {
        return new FanoutExchange(QUESTION_UPLOAD_FANOUT_EXCHANGE, true, false);
    }
    // shuffle exchange
    @Bean
    public DirectExchange shuffleDirectExchange() {
        return new DirectExchange(SHUFFLE_DIRECT_EXCHANGE, true, false);
    }



    // 🔥 HIGH PRIORITY (DB writes)
    @Bean
    public Queue loginDbQueue() {
        return QueueBuilder.durable(LOGIN_DB_QUEUE)
                .withArgument("x-max-priority", 10)
                .build();
    }

    // 🔥 MEDIUM PRIORITY (Admin analytics)
    @Bean
    public Queue loginAdminCacheQueue() {
        return QueueBuilder.durable(LOGIN_ADMIN_CACHE_QUEUE)
                .withArgument("x-max-priority", 5)
                .build();
    }

    // 🔥 MEDIUM PRIORITY (Branch analytics)
    @Bean
    public Queue loginBranchCacheQueue() {
        return QueueBuilder.durable(LOGIN_BRANCH_CACHE_QUEUE)
                .withArgument("x-max-priority", 5)
                .build();
    }

    // 🔥 High PRIORITY (Redis cache)
    @Bean
    public Queue loginUserCacheRedisQueue() {
        return QueueBuilder.durable(LOGIN_USER_CACHE_REDIS_QUEUE)
                .withArgument("x-max-priority", 9)
                .build();
    }

    @Bean
    public Queue questionUploadDbQueue() {
        return QueueBuilder.durable(QUESTION_UPLOAD_DB_QUEUE)
                .withArgument("x-max-priority", 10)
                .build();
    }

    @Bean
    public Queue shuffleDbQueue() {
        return QueueBuilder.durable(SHUFFLE_DB_QUEUE)
                .withArgument("x-max-priority", 10)
                .build();
    }
    @Bean
    public Queue shuffleCacheQueue() {
        return QueueBuilder.durable(SHUFFLE_CACHE_QUEUE)
                .withArgument("x-max-priority", 5)
                .build();
    }




    // -------- Bindings --------

    @Bean
    public Binding loginDbQueueBinding(
            @Qualifier("loginFanoutExchange") FanoutExchange exchange,
            Queue loginDbQueue
    ) {
        return BindingBuilder.bind(loginDbQueue).to(exchange);
    }

    @Bean
    public Binding loginAdminCacheQueueBinding(
            @Qualifier("loginFanoutExchange") FanoutExchange exchange,
            Queue loginAdminCacheQueue
    ) {
        return BindingBuilder.bind(loginAdminCacheQueue).to(exchange);
    }

    @Bean
    public Binding loginBranchCacheQueueBinding(
            @Qualifier("loginFanoutExchange") FanoutExchange exchange,
            Queue loginBranchCacheQueue
    ) {
        return BindingBuilder.bind(loginBranchCacheQueue).to(exchange);
    }

    @Bean
    public Binding loginUserCacheRedisQueueBinding(
            @Qualifier("loginFanoutExchange") FanoutExchange exchange,
            Queue loginUserCacheRedisQueue
    ) {
        return BindingBuilder.bind(loginUserCacheRedisQueue).to(exchange);
    }

    @Bean
    public Binding questionUploadDbQueueBinding(
            @Qualifier("questionUploadFanoutExchange") FanoutExchange exchange,
            Queue questionUploadDbQueue
    ) {
        return BindingBuilder.bind(questionUploadDbQueue).to(exchange);
    }

    @Bean
    public Binding shuffleDbQueueBinding(
            @Qualifier("shuffleDirectExchange") DirectExchange exchange,
            Queue shuffleDbQueue
    ) {
        return BindingBuilder.bind(shuffleDbQueue)
                .to(exchange)
                .with(SHUFFLE_DB_QUEUE);
    }
    @Bean
    public Binding shuffleCacheQueueBinding(
            @Qualifier("shuffleDirectExchange") DirectExchange exchange,
            Queue shuffleCacheQueue
    ) {
        return BindingBuilder.bind(shuffleCacheQueue)
                .to(exchange)
                .with(SHUFFLE_CACHE_QUEUE);
    }


    // -------- Serialization --------

    @Bean
    public MessageConverter gsonMessageConverter() {
        return new GsonAmqpMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(
            ConnectionFactory connectionFactory,
            MessageConverter gsonMessageConverter) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(gsonMessageConverter);
        return template;
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
            ConnectionFactory connectionFactory,
            MessageConverter gsonMessageConverter) {
        SimpleRabbitListenerContainerFactory factory =
                new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(gsonMessageConverter);
        return factory;
    }
}
