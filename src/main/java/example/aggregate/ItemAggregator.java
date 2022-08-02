package example.aggregate;

import example.domain.Item;
import example.domain.Order;
import org.apache.camel.AggregationStrategy;
import org.apache.camel.Exchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.Collection;

@ApplicationScoped
@Named
public class ItemAggregator implements AggregationStrategy {
    private static final Logger LOGGER = LoggerFactory.getLogger(ItemAggregator.class);

    @Override
    public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
        Item body = newExchange.getIn().getBody(Item.class);

        Collection<Item> items;

        if(oldExchange == null){
            items = new ArrayList<>();
            items.add(body);
            newExchange.getIn().setBody(items);
            LOGGER.info("item old");
            return newExchange;
        } else {
            items = oldExchange.getIn().getBody(ArrayList.class);
            items.add(body);
            LOGGER.info("item new");
            return oldExchange;
        }
    }
}
