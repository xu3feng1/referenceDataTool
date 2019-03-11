package com.scb.EventBus;


/**
 * @Author:Xu Feng
 * @email: fx2004@gmail.com
 * 2019/03/10
 *
 */

@FunctionalInterface
public interface EventListener {
	/**event listeners should cast the event (o) to the type of event they are interested in, 
	then register with EventBus with the interested event type **/
   public void onEvent (Object o);
}
