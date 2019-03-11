package com.scb.DataModel;

import java.util.Date;
import java.util.Optional;

/**
 * @Author:fxu (Xu Feng)
 * @email: fx2004@gmail.com
 * 2019/03/09
 *
 */

public class InternalPublishedData extends RefDataDetails {

	public InternalPublishedData(final String key, final Date ltd, final Date dd, final String label, final String exchangeCode) {
		super(key, ltd, dd, label, exchangeCode);
	}

	public InternalPublishedData setTradeable(final Optional<Boolean> tradeable) {
		this.tradeable = tradeable;
		return this;
	}
	
	public String toString() {
		return super.getKey() + "|" + super.getLastTradingDate().toString() + "|" + super.getDeliveryDate().toString() + "|" + super.getLabel()
		+ "|" + super.getExchangeCode() + "|" + tradeable.get();
	}
}
