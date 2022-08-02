package example.aggregate;

import example.domain.Item;
import example.domain.Order;
import org.apache.camel.AggregationStrategy;
import org.apache.camel.Exchange;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.Collection;

@ApplicationScoped
@Named
public class ItemAggregator implements AggregationStrategy {
    @Override
    public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
        Item body = newExchange.getIn().getBody(Item.class);

        Collection<Item> items;

        if(oldExchange == null){
            items = new ArrayList<>();
            items.add(body);
            newExchange.getIn().setBody(items);

            return newExchange;
        } else {
            items = oldExchange.getIn().getBody(ArrayList.class);
            items.add(body);
            return oldExchange;
        }
    }
}
