package com.scb.RefData;

import com.scb.DataModel.InternalPublishedData;

/**
 * @Author:Xu Feng
 * @email: fx2004@gmail.com
 * 2019/03/09
 *
 */

@FunctionalInterface
public interface DataPublsiher {
	void publish(InternalPublishedData ipd);

}