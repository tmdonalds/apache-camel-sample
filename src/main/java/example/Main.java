package example;

import example.domain.Order;
import example.service.OrderService;
import io.quarkus.arc.Arc;
import io.quarkus.arc.ArcContainer;
import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;
import org.apache.camel.quarkus.core.CamelRuntime;
import org.apache.camel.quarkus.main.CamelMainApplication;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import java.lang.reflect.ParameterizedType;

@QuarkusMain
public class Main implements QuarkusApplication {

    @Inject
    OrderService orderService;
    private static int totalItems;

    @Override
    public int run(String... args) throws Exception {
        CamelRuntime runtime = Arc.container().instance(CamelRuntime.class).get();
        runtime.start(new String[]{});

        totalItems = Integer.parseInt(args[0]);
        orderService.processItems();
        return 0;
    }

    @ApplicationScoped
    public static class TotalItemsProducer{

        @Produces
        @Named("totalItems")
        public int totalItems(){
            return totalItems;
        }
    }
}
