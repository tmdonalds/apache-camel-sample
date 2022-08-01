package example.config;

import example.aggregate.ItemAggregator;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.util.concurrent.SynchronousExecutorService;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class Routes extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("direct:prepareOrders")
                .split(body())
                .parallelProcessing()
                .to("direct:processOrders");

        from("direct:processOrders")
                .process("itemQueryProcessor")
                .to("direct:aggregateItems");

        from("direct:aggregateItems")
                .aggregate(header("ORDER_ID"), new ItemAggregator())
                .completionSize(header("TOTAL_ITEMS"))
                .executorService(new SynchronousExecutorService())
                .to("direct:processQueriedItems");

        from("direct:processQueriedItems")
                .log("processing queried item")
                .process("queriedItemsProcessor")
                .end();
    }
}
