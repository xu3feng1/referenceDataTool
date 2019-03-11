package com.scb.RefData;

import java.util.Set;

import com.scb.DataModel.RefDataDetails;

/**
 * @Author:Xu Feng
 * @email: fx2004@gmail.com
 * 2019/03/09
 *
 */

public interface DataStore {
	
	boolean containsRefKey(final String key);
	
	Set<String> refDataKeySet();
	
	void storeRefData(final RefDataDetails refData);
	
	RefDataDetails retrieveRefData(final String refDataKey);
	
    String getMappedKey(String key);
    
    void addMappedKeyEntry(String key, String mappedKey);	
}
