package example;

import example.service.OrderService;
import io.quarkus.arc.Arc;
import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;
import org.apache.camel.quarkus.core.CamelRuntime;

import javax.inject.Inject;

@QuarkusMain
public class Main implements QuarkusApplication {
    @Inject
    OrderService orderService;

    @Override
    public int run(String... args) throws Exception {
        CamelRuntime runtime = Arc.container().instance(CamelRuntime.class).get();
        runtime.start(new String[]{});

        orderService.handleOrder(args[0]);

        return runtime.waitForExit();
    }
}
