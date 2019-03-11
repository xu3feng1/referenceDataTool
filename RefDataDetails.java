package com.scb.DataModel;

import java.util.Date;
import java.util.Optional;

public class RefDataDetails {
	
	private String key;
	private Date lastTradingDate;
	private Date deliveryDate;
	private String label;
	private String exchangeCode;
	@SuppressWarnings("unused")
	protected Optional<Boolean> tradeable;
	
	public RefDataDetails(final String key, final Date ltd, final Date dd, final String label, final String exc) {
		this.setKey(key).setLastTradingDate(ltd).setDeliveryDate(dd).setLabel(label).setExchangeCode(exc);
	}

	public Date getDeliveryDate() {
		return deliveryDate;
	}

	public RefDataDetails setDeliveryDate(final Date deliveryDate) {
		this.deliveryDate = deliveryDate;
		return this;
	}

	public Date getLastTradingDate() {
		return lastTradingDate;
	}

	public RefDataDetails setLastTradingDate(final Date lastTradingDate) {
		this.lastTradingDate = lastTradingDate;
		return this;
	}

	public String getKey() {
		return key;
	}

	public RefDataDetails setKey(final String key) {
		this.key = key;
		return this;
	}

	public String getLabel() {
		return label;
	}

	public RefDataDetails setLabel(final String label) {
		this.label = label;
		return this;
	}

	public String getExchangeCode() {
		return exchangeCode;
	}

	public RefDataDetails setExchangeCode(final String exchangeCode) {
		this.exchangeCode = exchangeCode;
		return this;
	}
	
	public Optional<Boolean> getTradeable() {
		return tradeable;
	}
	
	public RefDataDetails setTradeable(final Optional<Boolean> tradeable) {
		this.tradeable = tradeable;
		return this;
	}
}