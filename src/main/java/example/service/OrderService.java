package example.service;

import example.domain.Job;
import example.domain.Order;
import org.apache.camel.CamelContext;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderService.class);

    public void handleOrder(String jobName){
        LOGGER.info("processing order no : {}", jobName);

        Job job = new Job(jobName);

        while(job.hasNextOrder()){
            Order nextOrder = job.getNextOrder();

            Map<String, Object> headers = new HashMap<>();
            headers.put("ORDER_ID", nextOrder.getOrderId());
            headers.put("TOTAL_ITEMS", nextOrder.getItems());
            camelContext.createProducerTemplate().sendBodyAndHeaders("direct:prepareOrders",job.getOrders(),headers);
        }

        LOGGER.info("finished processing");

        camelContext.stop();

    }
}
