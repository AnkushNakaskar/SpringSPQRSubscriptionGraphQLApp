package com.graphql.subscription.service;

import io.leangen.graphql.annotations.GraphQLSubscription;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Observable;
import org.reactivestreams.Publisher;
import org.springframework.stereotype.Service;


/**
 * @author ankushnakaskar
 */
@Service
public class GraphQLSunscriptionService {

    @GraphQLSubscription
    public Publisher<Integer> tick() {
        Observable<Integer> observable = Observable.create(emitter -> {
            emitter.onNext(1);
            Thread.sleep(1000);
            emitter.onNext(2);
            Thread.sleep(1000);
            emitter.onComplete();
        });
        return observable.toFlowable(BackpressureStrategy.BUFFER);
    }
}
