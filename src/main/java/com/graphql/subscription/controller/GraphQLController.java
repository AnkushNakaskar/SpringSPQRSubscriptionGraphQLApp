package com.graphql.subscription.controller;

import graphql.ExecutionInput;
import graphql.ExecutionResult;
import graphql.GraphQL;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author ankushnakaskar
 */
@RestController
public class GraphQLController {

    @Autowired
    private GraphQL graphQL;

    @PostMapping(value = "/graphql", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public ExecutionResult execute(@RequestBody Map<String, Object> request) {
        return graphQL.execute(ExecutionInput.newExecutionInput()
                .query((String) request.get("query"))
                .operationName((String) request.get("operationName"))
                .variables((Map<String, Object>) request.get("variables"))
                .build());
    }
    @GetMapping(value = "/graphqlSubscription")
    @ResponseBody
    public ExecutionResult executeSubScription() {
        ExecutionResult executionResult = graphQL.execute("subscription Tick { tick }");
        Publisher<ExecutionResult> stream = executionResult.getData();

        handlerStream(stream);

        return  executionResult;
    }

    private void handlerStream(final Publisher<ExecutionResult> stream) {
        AtomicInteger counter = new AtomicInteger(0);
        AtomicBoolean complete = new AtomicBoolean(false);
        stream.subscribe(new Subscriber<ExecutionResult>() {
            @Override
            public void onSubscribe(Subscription subscription) {
                subscription.request(10);
                System.out.println("On onSubscribe Stream  ......."+counter.get());
            }

            @Override
            public void onNext(ExecutionResult executionResult) {
                counter.getAndIncrement();
                System.out.println("On Next Stream  ......."+counter.get());
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println("On Error Stream");
            }

            @Override
            public void onComplete() {
                complete.set(true);
                System.out.println("On onComplete Stream  ......."+counter.get());
            }
        });
    }
}