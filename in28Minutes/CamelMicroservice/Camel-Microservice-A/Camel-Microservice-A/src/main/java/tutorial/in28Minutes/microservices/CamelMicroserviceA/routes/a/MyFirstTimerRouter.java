package tutorial.in28Minutes.microservices.CamelMicroserviceA.routes.a;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Component
public class MyFirstTimerRouter extends RouteBuilder {

    @Autowired
    private GetCurrentTimeBean getCurrentTimeBean;
    @Autowired
    private SimpleLoggingProcessComponent loggingComponent;

    @Override
    public void configure() throws Exception {
        from("timer:first-timer")
                .log("${body}")
                .transform().constant("My Constant Message ")
                .log("${body}")
                //.transform().constant("Current Date/Time: " + LocalDateTime.now())  //It doesn't work because we are using a constant, instead we need to create a bean
                .bean(getCurrentTimeBean)
                .log("${body}")
                .bean(loggingComponent)
                .log("${body}")
                .process(new SimpleLoggingProcessor())
                .to("log:first-timer");
    }


}

@Component
class GetCurrentTimeBean{
    public String getCurrentTime(){
        return "Current Date/Time: " + LocalDateTime.now();
    }
}

@Component
class SimpleLoggingProcessComponent{
    private Logger logger = LoggerFactory.getLogger(SimpleLoggingProcessComponent.class);

    public void process(String message){
        logger.info("SimpleLoggingProcessComponent {}", message);
    }
}

class SimpleLoggingProcessor implements Processor {

    private Logger logger = LoggerFactory.getLogger(SimpleLoggingProcessComponent.class);

    @Override
    public void process(Exchange exchange) throws Exception {
        logger.info("SimpleLoggingProcessor {}", exchange.getMessage().getBody());
    }
}



