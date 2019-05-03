package com.fleet.telemetry.driver.service.flux;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import java.time.Duration;
import java.util.List;

import reactor.core.publisher.Flux;
import reactor.core.publisher.UnicastProcessor;

@Component
@Scope("prototype")
public class Batcher<T> {

    @Value("${size.to.send.messages.in.batch:10}")
    private Integer batchSize;

    @Value("${duration.milis.to.send.messages.in.batch:500}")
    private Integer duration;

    final UnicastProcessor<T> processor = UnicastProcessor.create();

    public void addToBatch(T element) {
        processor.sink().next(element);
    }

    public Flux<List<T>> listen() {
        return processor.bufferTimeout(batchSize, Duration.ofMillis(duration));
    }

}
