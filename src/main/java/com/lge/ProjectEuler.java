package com.lge;

/**
 * Created by soohyun.baik on 2015-04-15.
 */

import rx.Observable;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

public class ProjectEuler {
    public static void fibonacci() {
        fibos()
                .filter(n -> n % 2 == 0)
                .takeWhile(n -> n < 4_000_000)
                .reduce((a, b) -> a + b)
                .subscribe(n -> System.out.println("Fibonacci: " + n));
    }

    private static Observable<Integer> fibos() {
        return Observable.<Integer>create(obs -> {
            int a = 0, b = 1;
            while (!obs.isUnsubscribed()) {
                obs.onNext(a);
                b = a + b;
                a = b - a;
            }
        });
    }

    public static void palindrome() {
        fromTo(100, 999)
                .flatMap(n -> fromTo(n, 999).map(m -> n * m))
                .filter(n -> isPalindrome(n))
                .reduce(Math::max)
                .subscribe(s -> System.out.println("Palindrome: " + s));
    }

    private static Observable<Integer> fromTo(int begin, int end) {
        return Observable.range(begin, end - begin + 1);
    }

    private static boolean isPalindrome(int n) {
        String s = String.valueOf(n);
        String rs = new StringBuffer(s).reverse().toString();
        return s.equals(rs);
    }

    public static void interval() {
        Observable.interval(1, TimeUnit.SECONDS)
                .take(10)
                .toBlocking() // no subscribe, wait until complete
                .forEach(s -> System.out.println("Interval: " + s));
    }

    public static void timer() {
        System.out.println("Timer - Wait 5 seconds, then execute interval");
        Observable.timer(5, 1, TimeUnit.SECONDS)
                .take(10)
                .toBlocking()
                .forEach(s -> System.out.println("Timer: " + s));
    }

    public static void delay() {
        Observable.just(1, 2, 3, 4)
                .zipWith(Observable.interval(1, TimeUnit.SECONDS), (a, b) -> a)
                .toBlocking()
                .forEach(s -> System.out.println("Delay: " + s));
    }

    public static void bufferWithTime() {
        Observable
                .interval(200, TimeUnit.MILLISECONDS)
                .buffer(410, TimeUnit.MILLISECONDS)
                .take(10)
                .toBlocking()
                .forEach(ints -> System.out.println("buffer: " + ints));
    }

    public static void main(String[] args) {
//        fibonacci();
//        palindrome();
//        interval();
//        timer();
//        delay();
        bufferWithTime();
    }
}
