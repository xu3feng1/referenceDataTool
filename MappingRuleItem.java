package com.scb.RulesEngine;

import com.scb.DataModel.RefDataDetails;
import com.scb.RefData.DataStore;

/**
 * @Author:Xu Feng
 * @email: fx2004@gmail.com
 * 2019/03/09
 * enum MappingRuleItem implements the rules engine design pattern
 * to add additional mapping rules, add an enum, implement the logic in getMappedKey function
 */

public enum MappingRuleItem implements MappingRule {
	
	DEFAULT_MAPPING() {

	@Override
	public boolean isApplicable(final RefDataDetails rd, final DataStore context) {
		return true;
	}

	@Override
	public String getMappedKey(final String key, final DataStore context) {
		System.out.println("Default mapping rule applied.");
		return context.getMappedKey(key);
	}
	};
}
