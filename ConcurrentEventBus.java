package com.scb.EventBus;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.scb.EventBus.EventBus;
import com.scb.EventBus.EventListener;
import com.scb.EventBus.FilteredListener;

/**
 * @Author:Xu Feng
 * @email: fx2004@gmail.com
 * 2019/03/10
 *
 */

public class ConcurrentEventBus implements EventBus {
	//mapping from event type to registered subscribers
	//using CopyOnWriteArrayList to collect subscribers for specific evnetType 
	private Map<Class<?>, List<EventListener>> listenersRegistry =
            new ConcurrentHashMap<Class<?>, List<EventListener>>();
	//the set of filtered event listeners
	private List<FilteredListener> filteredListeners = new CopyOnWriteArrayList<FilteredListener>();
	//executor for the subscribe callback
	private final ExecutorService callbackExctor = Executors.newCachedThreadPool();
	
	
	@Override
	public void publishEvent(Object o) {
		if (o == null) {
            System.err.println("Null event can not be posted.");
            throw new NullPointerException("Null event can not be posted.");
        }
		
		List<EventListener> listeners = listenersRegistry.get(o.getClass());
		if (listeners != null) {
		  for (EventListener lstnr : listeners) {		  
			    callbackExctor.submit(
		             ()  ->   {
			             lstnr.onEvent(o);		             
		              }
		        );
			    System.out.println("onEvent method called for listener " + lstnr + " by " + Thread.currentThread());
		  }
		}
		
		for (FilteredListener fltlsnr : filteredListeners) {		
		  callbackExctor.submit(
			  () -> { 
				if(fltlsnr.acceptEvent(o)) {
					fltlsnr.onEvent(o);
					                        
				 }
			   }
			);
		     System.out.println("onEvent method called for listener " + fltlsnr + " by " + Thread.currentThread());   
		  }
	}
	
	@Override
	public void addSubscriber(Class<?> eventType, EventListener listener) {
		if (eventType == null) {
            System.err.println("Null event type can not be registered.");
            throw new NullPointerException("Null event type can not be registered.");
        }
		if (listener == null) {
            System.err.println("Null listener can not be registered.");
            throw new NullPointerException("Null event can not be registered.");
        }
		
		if (listenersRegistry.containsKey(eventType)) {
			List<EventListener> lsnrs = listenersRegistry.get(eventType);
			lsnrs.add(listener);
			System.out.println("event type " + eventType + " alreaday exisited in registry, "+ "registered one more listener " + listener);
			System.out.println("listener count for event type " + eventType + " "+ lsnrs.size());
		}
		else {
			listenersRegistry.putIfAbsent(eventType, new CopyOnWriteArrayList<EventListener>());
			List<EventListener> lsnrs = listenersRegistry.get(eventType);
			lsnrs.add(listener);
			System.out.println("registered listener " + listener + "with event type " + eventType);
			System.out.println("listener count for event type " + eventType + " " + lsnrs.size());
		}
	}
	
	@Override
	public void addSubscriberForFilteredEvents(FilteredListener filterListener) {
		if (filterListener == null) {
            System.err.println("Null filered listener can not be registered.");
            throw new NullPointerException("Null filtered listener can not be registered.");
        }
		filteredListeners.add(filterListener);
		System.out.println("registered filtered listerner " + filterListener);
		System.out.println("Filtered listener count " + filteredListeners.size());
	}
	
	public Map<Class<?>, List<EventListener>> getRegisteredListeners() {
		 Map<Class<?>, List<EventListener>> copy = new ConcurrentHashMap<Class<?>, List<EventListener>>();
	     copy.putAll(listenersRegistry);
	     return copy;
	}	

}
