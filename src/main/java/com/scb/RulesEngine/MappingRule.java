package com.scb.RulesEngine;

import com.scb.DataModel.RefDataDetails;
import com.scb.RefData.DataStore;

/**
 * @Author:Xu Feng
 * @email: fx2004@gmail.com
 * 2019/03/09
 *
 */

public interface MappingRule {
	
	boolean isApplicable(final RefDataDetails rfd, final DataStore context);
	String getMappedKey(final String key, final DataStore context);

}
