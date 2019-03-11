package com.scb.RefData;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.scb.DataModel.RefDataDetails;

/**
 * @Author:Xu Feng
 * @email: fx2004@gmail.com
 * 2019/03/09
 *
 */

public class InMemoryDataStore implements DataStore {
	
	private ConcurrentHashMap<String, RefDataDetails> refDataMap = new ConcurrentHashMap<>();
	private ConcurrentHashMap<String, String> keyMap = new ConcurrentHashMap<>();

	@Override
	public void storeRefData(RefDataDetails refData) {
	   	refDataMap.put(refData.getKey(), refData);
	}

	@Override
	public RefDataDetails retrieveRefData(String refDataKey) {
		return refDataMap.get(refDataKey);
	}

	@Override
	public boolean containsRefKey(String key) {
		return refDataMap.containsKey(key);
	}

	@Override
	public Set<String> refDataKeySet() {
		return refDataMap.keySet();
	}

	@Override
	public String getMappedKey(String key) {
		return keyMap.get(key);
	}

	@Override
	public void addMappedKeyEntry(String key, String mappedKey) {
		keyMap.put(key, mappedKey);
	}

}
