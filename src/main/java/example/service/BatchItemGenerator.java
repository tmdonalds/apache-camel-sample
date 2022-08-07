package example.service;

import example.domain.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import java.util.List;

@ApplicationScoped
@Named("batchItemGenerator")
public class BatchItemGenerator {
    Logger LOGGER = LoggerFactory.getLogger(BatchItemGenerator.class);

    public void processBatch(List<Item> items){
        LOGGER.info("preparing to process batch of size {}",items.size());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
