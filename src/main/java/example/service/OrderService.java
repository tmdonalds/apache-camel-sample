package example.service;

import example.domain.Job;
import example.domain.Order;
import io.quarkus.runtime.Startup;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.builder.ExchangeBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.HashMap;
import java.util.Map;


@ApplicationScoped
@Named
public class OrderService {
    @Inject
    CamelContext camelContext;

    @Inject
    @Named("totalItems")
    private int totalItems;

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderService.class);

    public void handleOrder(String jobName){
        LOGGER.info("processing order no : {}", jobName);

        Job job = new Job(jobName);

        while(job.hasNextOrder()){
            Order nextOrder = job.getNextOrder();

            Map<String, Object> headers = new HashMap<>();
            headers.put("ORDER_ID", nextOrder.getOrderId());
            headers.put("TOTAL_ITEMS", nextOrder.getItems());
            camelContext.createProducerTemplate().sendBodyAndHeaders("direct:prepareOrders",nextOrder.getItems(),headers);
        }

        LOGGER.info("finished processing");

        camelContext.stop();
    }

    @Startup
    public void processItems(){
        Exchange exchange = ExchangeBuilder.anExchange(camelContext).withHeader("ITEMS_TO_GENERATE", totalItems).build();
        camelContext.createProducerTemplate().send("direct:createItems",exchange);
    }
}
