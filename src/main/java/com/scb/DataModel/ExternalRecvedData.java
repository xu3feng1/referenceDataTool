package com.scb.DataModel;

import java.util.Date;

/**
 * @Author:Xu Feng
 * @email: fx2004@gmail.com
 * 2019/03/09
 *
 */

public class ExternalRecvedData extends RefDataDetails {
	private final DataSource source;

	public ExternalRecvedData(final String key, final Date ltd, final Date dd, final String label, final String exchangeCode, final DataSource ds) {
		super(key, ltd, dd, label, exchangeCode);
		this.source = ds;	
	}

	public DataSource getSource() {
		return source;
	}
	
	public String toString() {
		return super.getKey() + "|" + super.getLastTradingDate().toString() + "|" + super.getDeliveryDate().toString() + "|" + super.getLabel()
		+ "|" + super.getExchangeCode() + "|" + source;
	}

}
