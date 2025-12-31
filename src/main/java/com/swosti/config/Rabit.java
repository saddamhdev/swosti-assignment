package com.swosti.config;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionBlockedEvent;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionUnblockedEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
 class Rabbit {

   /* // -------------------------------
    // Exchanges (Exactly as per flow)
    // -------------------------------
    public static final String LOGIN_EXCHANGE = "login.topic.exchange";
    public static final String SUBMISSION_EXCHANGE = "submission.fanout.exchange";
    public static final String ANALYTICS_EXCHANGE = "analytics.direct.exchange";

    public static final String DLX = "global.dlx";

    // -------------------------------
    // Queues (Login + Submission)
    // -------------------------------
    public static final String LOGIN_QUEUE = "login.queue";
    public static final String LOGIN_DLQ = "login.dlq";

    public static final String SUBMISSION_QUEUE = "submission.queue";
    public static final String SUBMISSION_DLQ = "submission.dlq";

    public static final String ANALYTICS_QUEUE = "analytics.queue";
    public static final String ANALYTICS_DLQ = "analytics.dlq";

    // Retry settings
    public static final int MAX_RETRY = 5;
    public static final int BASE_DELAY = 2000;

    // -------------------------------
    // Exchange Beans
    // -------------------------------
    @Bean TopicExchange loginExchange() { return new TopicExchange(LOGIN_EXCHANGE,true,false); }
    @Bean FanoutExchange submissionExchange() { return new FanoutExchange(SUBMISSION_EXCHANGE,true,false); }
    @Bean DirectExchange analyticsExchange() { return new DirectExchange(ANALYTICS_EXCHANGE,true,false); }
    @Bean DirectExchange dlx() { return new DirectExchange(DLX,true,false); }

    // -------------------------------
    // Login Queue
    // -------------------------------
    @Bean
    Queue loginQueue() {
        return QueueBuilder.durable(LOGIN_QUEUE)
                .withArgument("x-dead-letter-exchange", DLX)
                .withArgument("x-dead-letter-routing-key", LOGIN_DLQ)
                .withArgument("x-max-priority", 10)
                .withArgument("x-queue-mode", "lazy")
                .build();
    }

    @Bean Queue loginDLQ() { return QueueBuilder.durable(LOGIN_DLQ).build(); }

    // -------------------------------
    // Submission Queue
    // -------------------------------
    @Bean
    Queue submissionQueue() {
        return QueueBuilder.durable(SUBMISSION_QUEUE)
                .withArgument("x-dead-letter-exchange", DLX)
                .withArgument("x-dead-letter-routing-key", SUBMISSION_DLQ)
                .withArgument("x-queue-mode", "lazy")
                .build();
    }

    @Bean Queue submissionDLQ() { return QueueBuilder.durable(SUBMISSION_DLQ).build(); }

    // -------------------------------
    // Analytics Queue
    // -------------------------------
    @Bean
    Queue analyticsQueue() {
        return QueueBuilder.durable(ANALYTICS_QUEUE)
                .withArgument("x-dead-letter-exchange", DLX)
                .withArgument("x-dead-letter-routing-key", ANALYTICS_DLQ)
                .build();
    }

    @Bean Queue analyticsDLQ() { return QueueBuilder.durable(ANALYTICS_DLQ).build(); }

    // -------------------------------
    // Bindings
    // -------------------------------
    @Bean Binding loginBinding() {
        return BindingBuilder.bind(loginQueue()).to(loginExchange()).with("login.event");
    }
    @Bean Binding loginDlqBinding() {
        return BindingBuilder.bind(loginDLQ()).to(dlx()).with(LOGIN_DLQ);
    }

    @Bean Binding submissionBinding() {
        return BindingBuilder.bind(submissionQueue()).to(submissionExchange());
    }
    @Bean Binding submissionDlqBinding() {
        return BindingBuilder.bind(submissionDLQ()).to(dlx()).with(SUBMISSION_DLQ);
    }

    @Bean Binding analyticsBinding() {
        return BindingBuilder.bind(analyticsQueue()).to(analyticsExchange()).with("analytics");
    }
    @Bean Binding analyticsDlqBinding() {
        return BindingBuilder.bind(analyticsDLQ()).to(dlx()).with(ANALYTICS_DLQ);
    }

    // -------------------------------
    // Blocked connection notifier
    // -------------------------------
    @Bean
    ApplicationListener<ConnectionBlockedEvent> blocked() {
        return e -> System.out.println("🚫 Connection BLOCKED: " + e.getReason());
    }

    @Bean
    ApplicationListener<ConnectionUnblockedEvent> unblocked() {
        return e -> System.out.println("✅ Connection UN-BLOCKED");
    }

    // -------------------------------
    // Prefetch Config
    // -------------------------------
    @Bean("prefetch5")
    SimpleRabbitListenerContainerFactory pf5(ConnectionFactory cf) {
        var f = new SimpleRabbitListenerContainerFactory();
        f.setConnectionFactory(cf);
        f.setPrefetchCount(5);
        return f;
    }

    @Bean("prefetch20")
    SimpleRabbitListenerContainerFactory pf20(ConnectionFactory cf) {
        var f = new SimpleRabbitListenerContainerFactory();
        f.setConnectionFactory(cf);
        f.setPrefetchCount(20);
        return f;
    }*/
}

