package example.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

@ApplicationScoped
@Named
public class QueriedItemsProcessor implements Processor {
    private static Logger LOGGER = LoggerFactory.getLogger(QueriedItemsProcessor.class);

    @Override
    public void process(Exchange exchange) throws Exception {
        int cnt = 0;

        while(cnt < 10){
            //this would mimic polling a queue
            LOGGER.info("polling message queue : {}", cnt);
            Thread.sleep(1000L);
            cnt++;
        }
    }
}
