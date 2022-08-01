package example.aggregate;

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
        Order body = newExchange.getIn().getBody(Order.class);

        Collection<Order> orders;

        if(oldExchange == null){
            orders = new ArrayList<>();
            orders.add(body);
            newExchange.getIn().setBody(orders);

            return newExchange;
        } else {
            orders = oldExchange.getIn().getBody(ArrayList.class);
            orders.add(body);
            return oldExchange;
        }
    }
}
