package cn.com.thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPool {
    public static ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(4,10,1, TimeUnit.MINUTES,new ArrayBlockingQueue<>(3));
}
