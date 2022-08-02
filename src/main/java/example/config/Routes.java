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
                .log("preparing to aggregate")
                .aggregate(header("ORDER_ID"), new ItemAggregator())
                .executorService(new SynchronousExecutorService())
                .completionSize(header("TOTAL_ITEMS"))
                .to("direct:processQueriedItems2");

        from("direct:processQueriedItems")
                .log("processing queried items")
                .process("queriedItemsProcessor")
                .end();
    }
}
