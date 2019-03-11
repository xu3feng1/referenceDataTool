package com.scb.RulesEngine;

import com.scb.DataModel.ExternalRecvedData;
import com.scb.DataModel.InternalPublishedData;
import com.scb.RefData.DataStore;

/**
 * @Author:Xu Feng
 * @email: fx2004@gmail.com
 * 2019/03/09
 *
 */

public interface MergeRule {
	
    boolean isApplicable(final ExternalRecvedData refData, final DataStore context);
	
	InternalPublishedData merge(final ExternalRecvedData refData, final DataStore context);

}
