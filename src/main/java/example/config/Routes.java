package example.config;

import example.aggregate.ItemAggregator;
import org.apache.camel.builder.RouteBuilder;

import javax.enterprise.context.ApplicationScoped;

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

    }
}
