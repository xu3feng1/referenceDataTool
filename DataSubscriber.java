package com.scb.RefData;

import com.scb.DataModel.ExternalRecvedData;

@FunctionalInterface
public interface DataSubscriber {
	
	void onMessage(ExternalRecvedData eData);

}