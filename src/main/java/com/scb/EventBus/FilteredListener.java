package com.scb.EventBus;

/**
 * @Author:Xu Feng
 * @email: fx2004@gmail.com
 * 2019/03/10
 *
 */

public interface FilteredListener {
	
    public boolean acceptEvent (Object o);
    
	public void onEvent (Object o);

}
