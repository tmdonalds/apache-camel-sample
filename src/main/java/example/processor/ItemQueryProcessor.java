package example.processor;


import example.domain.Item;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

@ApplicationScoped
@Named
public class ItemQueryProcessor implements Processor {
    private static Logger LOGGER = LoggerFactory.getLogger(ItemQueryProcessor.class);

    @Override
    public void process(Exchange exchange) throws Exception {
        LOGGER.info("preparing to make a rest call : {}", exchange.getIn().getBody(Item.class).getItemId());
        Thread.sleep(1000L); //this would do some type of rest query

        exchange.getIn().setBody(exchange.getIn().getBody());
    }
}
