package com.scb.RulesEngine;

import java.util.Optional;

import com.scb.DataModel.DataSource;
import com.scb.DataModel.ExternalRecvedData;
import com.scb.DataModel.InternalPublishedData;
import com.scb.DataModel.RefDataDetails;
import com.scb.RefData.DataStore;

/**
 * @Author:Xu Feng
 * @Email: fx2004@gmail.com
 * 2019/03/09
 * enum MergeRuleItem implements the rules engine design pattern
 * to add additional merge rules for received ref data, add an enum, implement the logic in merge function
 * @Field: isActive can be set to false to turn off the rule at run time
 */

public enum MergeRuleItem implements MergeRule {
	
	LME("LME Merge Rule", true) {
	@Override
	public boolean isApplicable(final ExternalRecvedData refData, final DataStore context) {
		if (refData.getSource() == DataSource.LME && this.isActive) 
			return true; 
		else 
			return false;
	   }

	@Override
	public InternalPublishedData merge(final ExternalRecvedData refData, final DataStore context) {
		System.out.println("LME Merge Rule applied.");
		context.storeRefData(refData);
		return new InternalPublishedData(refData.getKey(), refData.getLastTradingDate(), refData.getDeliveryDate(), refData.getLabel(), refData.getExchangeCode()).setTradeable(Optional.of(true));
	   }
	},

	PB("Prime Broker Merge Rule", true) {
	@Override
	public boolean isApplicable(final ExternalRecvedData refData, final DataStore context) {
		if (refData.getSource() == DataSource.PRIME && this.isActive) 
			return true; 
		else 
			return false;
	   }

	@Override
	public InternalPublishedData merge(final ExternalRecvedData refData, final DataStore context) {
		System.out.println("Prime Broker Merge Rule applied.");
		String mappedKey = null;
		for (MappingRuleItem mri : MappingRuleItem.values()) {
			if (mri.isApplicable(refData, context)) {
				mappedKey = mri.getMappedKey(refData.getKey(), context);
				break;
			}
		}
		if (mappedKey == null) {
			context.addMappedKeyEntry(refData.getKey(), refData.getExchangeCode());
			mappedKey = refData.getExchangeCode();
		}
		RefDataDetails mdd = context.retrieveRefData(mappedKey);
		if (mdd != null) 
			return new InternalPublishedData(refData.getKey(), mdd.getLastTradingDate(), mdd.getDeliveryDate(), refData.getLabel(), refData.getExchangeCode()).setTradeable(refData.getTradeable());
		else
			return new InternalPublishedData(refData.getKey(), refData.getLastTradingDate(), refData.getDeliveryDate(), refData.getLabel(), refData.getExchangeCode()).setTradeable(refData.getTradeable());
	   }
	},
	
	AS_IS("AS_IS Default Merge Rule", true) {
	@Override
	public boolean isApplicable(final ExternalRecvedData refData, final DataStore context) {
		if ((refData.getSource() != DataSource.PRIME || refData.getSource() != DataSource.PRIME) && this.isActive) 
			return true;
		else 
			return false;
	   }

	@Override
	public InternalPublishedData merge(final ExternalRecvedData refData, final DataStore context) {
		System.out.println("AS_IS Default Merge Rule applied.");
		return new InternalPublishedData(refData.getKey(), refData.getLastTradingDate(), refData.getDeliveryDate(), refData.getLabel(), refData.getKey()).setTradeable(Optional.of(true));
	   }
	};
	
	private final String descriptiveName;
	protected boolean isActive; 
	
	MergeRuleItem(final String descriptiveName, final boolean isActive){
		this.descriptiveName = descriptiveName;
		this.isActive = isActive;
	}

	public String getDescriptiveName() {
		return descriptiveName;
	}

	public boolean isActive() {
		return isActive;
	}
	
	public void setActive(final boolean isActive) {
	      this.isActive = isActive;
	}
	
}
