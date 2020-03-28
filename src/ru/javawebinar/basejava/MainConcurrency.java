package ru.javawebinar.basejava;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class MainConcurrency {
    public static final int THREADS_NUMBER = 10000;
    private static int counter;
    private static final AtomicInteger atomicCounter = new AtomicInteger();
    //    private static final Object LOCK = new Object();
//    private static final Lock lock = new ReentrantLock();
    private static final ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
    private static final Lock READ_LOCK = reentrantReadWriteLock.readLock();
    private static final Lock WRITE_LOCK = reentrantReadWriteLock.writeLock();
    private static final ThreadLocal<SimpleDateFormat> threadLocal = new ThreadLocal<SimpleDateFormat>(){

        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat();
        }
    };
    private static final SimpleDateFormat sdf = new SimpleDateFormat();


    public static void main(String[] args) throws InterruptedException {


        System.out.println(Thread.currentThread().getName());

        Thread thread0 = new Thread() {
            @Override
            public void run() {
                System.out.println(getName() + ", " + getState());
                throw new IllegalStateException();
            }
        };
        thread0.start();
        new Thread(() -> System.out.println(Thread.currentThread().getName() + ", " + Thread.currentThread().getState())).start();
        System.out.println(thread0.getState());
        final MainConcurrency mainConcurrency = new MainConcurrency();
        CountDownLatch latch = new CountDownLatch(THREADS_NUMBER);
//        ExecutorService executorService = Executors.newCachedThreadPool();
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
//        CompletionService completionService = new ExecutorCompletionService(executorService);

//        List<Thread> treads = new ArrayList<>();
        for (int i = 0; i < THREADS_NUMBER; i++) {
            Future<Integer> future = executorService.submit(() -> {

//            Thread thread = new Thread(() -> {
                for (int j = 0; j < 100; j++) {
                    mainConcurrency.inc();
                    System.out.println(threadLocal.get().format(new Date()));
                }
                latch.countDown();
                return 5;
            });
//            completionService.poll();
//            System.out.println(future.isDone());
//            System.out.println(future.get());

//            BAD CODE - too much consumption
//            while(future.isDone()){
//                Thread.sleep(100);
//            }
//            future.get();
//            END OF BAD CODE

//            thread.start();
////          treads.add(thread);

        }
//        treads.forEach(t -> {
//            try {
//                t.join();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        });
        latch.await(10, TimeUnit.SECONDS);
        executorService.shutdown();
        System.out.println(atomicCounter.get());
    }

    private void inc() {
        atomicCounter.incrementAndGet();
//        synchronized (this){
//        synchronized (MainConcurrency.class)
//        synchronizeded (thisis) {
//        lock.lock();
//        try {
//            counter++;
//
//        } finally {
//            lock.unlock();
//
//        }
//        }
    }
}
