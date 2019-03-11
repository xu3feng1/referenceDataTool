package com.scb.EventBus;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Before;
import org.junit.Test;

/**
 * @Author:Xu Feng
 * @email: fx2004@gmail.com
 * 2019/03/10
 *
 */

public class ConcurrentEventBusTest {
	
	
	private ConcurrentEventBus conEvntBus;
	private static int concurrentLevel = 200;
	private static CountDownLatch doneSignal = new CountDownLatch(concurrentLevel);

    @Before
    public void setUp() {
        conEvntBus = new ConcurrentEventBus();
    }
    
    @Test
    public void testConcurrentRegister() throws InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
    	Class<?> eventType = MarketEvent.class;
        Class<?> eventType2 = TradeEvent.class;
        for (int i = 0; i < concurrentLevel; i++) {
            executorService.submit(() -> {
                    conEvntBus.addSubscriber(eventType,new MarketEventListener());
                    conEvntBus.addSubscriber(eventType2,new TradeEventListener());
            });
        }
        
        Thread.sleep(1000); //ensure all registering threads complete
        Map<Class<?>, List<EventListener>> listenersMap = conEvntBus.getRegisteredListeners();
    	assertEquals(listenersMap.keySet().size(), 2);
    	assertEquals(listenersMap.get(eventType).size(), concurrentLevel);
    	assertEquals(listenersMap.get(eventType).size(), concurrentLevel);
    }
    
    public void registerListeners() {
    	Class<?> eventType = MarketEvent.class;
    	MarketEventListener listener = new MarketEventListener();
    	conEvntBus.addSubscriber(eventType, listener);
    }
    
    @Test
    public void testConcurrentPublish() throws InterruptedException {
    	registerListeners();
        ExecutorService executorService = Executors.newCachedThreadPool();
    	Random rndGen = new Random();
        for (int i = 0; i < concurrentLevel; i++) {
            executorService.submit(() -> {
                {
                    conEvntBus.publishEvent(new MarketEvent(rndGen.nextInt(100)));
                }
            });
        }
        doneSignal.await();
    	assertEquals(MarketEventListener.count.intValue(), concurrentLevel);
    }
    
    @Test(expected = NullPointerException.class)
    public void testNullPosting() {
        conEvntBus.publishEvent(null);
    }
    
    private class MarketEvent {
    	int data;
    	public MarketEvent(int data) {
    		this.data = data;
    	}
    }
    
    private  static class MarketEventListener implements EventListener {
        public static AtomicInteger count = new AtomicInteger(0);
    	public void onEvent(Object e) {
    		MarketEvent me = (MarketEvent) e;
    		System.out.println("received market event " + me);
    		count.getAndIncrement();
    		doneSignal.countDown();
    	}
    	
    }
    
    private class TradeEvent {
    	double data;
    	public TradeEvent(int data) {
    		this.data = data;
    	}
    }
    
    private  static class TradeEventListener implements EventListener {
        public static AtomicInteger count = new AtomicInteger(0);
    	public void onEvent(Object e) {
    		TradeEvent te = (TradeEvent) e;
    		System.out.println("received trade event " + te);
    		count.getAndIncrement();
    	}
    	
    }

}
