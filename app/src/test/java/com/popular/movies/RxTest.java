package com.popular.movies;

import org.junit.Test;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;


public class RxTest {

    @Test
    public void rxTest() {
        Observable<Integer> num = Observable.just(1, 2, 3);
        Observable<String> str = Observable.just("satu", "dua", "tiga");
        CompositeDisposable composite = new CompositeDisposable();

        composite.add(Observable.zip(num, str, (integer, s) -> integer + " : " + s)
                .observeOn(Schedulers.io())
                .subscribe(System.out::println));
    }

}
