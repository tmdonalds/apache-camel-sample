package example.config;

import example.aggregate.ItemAggregator;
import example.service.BatchItemGenerator;
import example.service.ItemGenerator;
import org.apache.camel.builder.RouteBuilder;

import javax.enterprise.context.ApplicationScoped;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@ApplicationScoped
public class Routes extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("direct:prepareOrders")
                .split(body(), new ItemAggregator()).parallelProcessing()
                .process("itemQueryProcessor")
                .end()
                .log("${header.ORDER_ID} out of ${header.TOTAL_ITEMS}")
                .to("direct:processQueryItems");

        from("direct:processQueryItems")
                .process("queriedItemsProcessor")
                .log("i'm ready");

        ExecutorService executorService = Executors.newFixedThreadPool(20);

        from("direct:createItems")
                .bean(ItemGenerator.class, "createItems")
                .wireTap("direct:tap")
                .split(body()).streaming().parallelProcessing().executorService(executorService)
                    .bean(BatchItemGenerator.class)
                .end()
                .log("finished processing them all");

        from("direct:tap")
                .log("tapped createItems : ${body.size}");


    }
}
