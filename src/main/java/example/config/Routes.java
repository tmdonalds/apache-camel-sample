package example.config;

import example.aggregate.ItemAggregator;
import org.apache.camel.builder.RouteBuilder;

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
                .completionTimeout(500L)
                .to("direct:processQueriedItems");

        from("direct:processQueriedItems")
                .process("queriedItemsProcessor")
                .end();
    }
}
