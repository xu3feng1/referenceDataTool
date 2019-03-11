package com.scb.EventBus;

import com.scb.EventBus.EventListener;
import com.scb.EventBus.FilteredListener;

/**
 * @Author:Xu Feng
 * @email: fx2004@gmail.com
 * 2019/03/10
 *
 */

public interface EventBus {

	public void publishEvent(Object o);
	
	/* for simplification, using interface type for the listeners, instead of using annotation to mark the subscriber method */
	
	public void addSubscriber(Class<?> eventType, EventListener listener);
	
	/* the FilteredListener interface will have a method to filter events for listeners */
	
	public void addSubscriberForFilteredEvents(FilteredListener filterListener);
}
