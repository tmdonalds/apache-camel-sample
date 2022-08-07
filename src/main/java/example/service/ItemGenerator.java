package example.service;

import example.domain.Item;
import org.apache.camel.Header;
import org.apache.commons.collections4.ListUtils;

import java.util.*;

public class ItemGenerator {
    public List<List<Item>> createItems(@Header("ITEMS_TO_GENERATE") int itemsToGenerate){
        List<Item> items = new ArrayList<>();

        for(int i = 0; i < itemsToGenerate; i++){
            items.add(new Item(generateRandomAlphabeticString(), UUID.randomUUID().toString()));
        }

        List<List<Item>> chunked = ListUtils.partition(items, 25);

        return chunked;
    }

    private String generateRandomAlphabeticString(){
        int leftLimit = 97;
        int rightLimit = 122;
        int targetStringLength = 10;
        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        return generatedString;
    }
}
