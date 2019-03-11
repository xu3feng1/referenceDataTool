package com.scb.RulesEngine;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

import com.scb.DataModel.DataSource;
import com.scb.DataModel.ExternalRecvedData;
import com.scb.DataModel.InternalPublishedData;
import com.scb.DataModel.RefDataDetails;
import com.scb.RefData.InMemoryDataStore;
import java.util.Date;
import java.util.Optional;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * @Author:Xu Feng
 * @email: fx2004@gmail.com
 * 2019/03/10
 *
 */

public class MergeRuleItemTest {
	
	private static SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
	private InMemoryDataStore testRefStore = new InMemoryDataStore();
	private Date ltd;
	private Date dd;
	
    @Before
    public void setUp() throws ParseException {
        ltd = formatter.parse("15-Mar-2019");
    	dd = formatter.parse("17-Mar-2019");
    	RefDataDetails refData1 = new RefDataDetails("LME_123", ltd, dd, "Lead 03-15-2019", "LME_123");
    	testRefStore.storeRefData(refData1);
    	
    }
    
    @Test
    public void testLMEMergeRule() throws ParseException {
    	ExternalRecvedData extData = new ExternalRecvedData("LME_123", ltd, dd, "Lead 03-15-2019", "LME_123", DataSource.LME);
    	boolean isLMERule = MergeRuleItem.LME.isApplicable(extData, testRefStore);
    	assertEquals(isLMERule, true);
    	InternalPublishedData inLmeData = MergeRuleItem.LME.merge(extData, testRefStore);
    	assertEquals(inLmeData.getDeliveryDate(), dd);
    	assertEquals(inLmeData.getLastTradingDate(), ltd);
    	assertEquals(inLmeData.getTradeable().get(), true);
    }
    
    @Test
    public void testPBMergeRule() throws ParseException {
    	Date pb_ltd = formatter.parse("18-Mar-2019");
    	Date pb_dd = formatter.parse("20-Mar-2019");
    	ExternalRecvedData extData = new ExternalRecvedData("PB_LME_123", pb_ltd, pb_dd, "Lead 03-15-2019", "LME_123", DataSource.PRIME);
    	extData.setTradeable(Optional.of(false));
    	boolean isPBRule = MergeRuleItem.PB.isApplicable(extData, testRefStore);
    	assertEquals(isPBRule, true);
    	InternalPublishedData inPBData = MergeRuleItem.PB.merge(extData, testRefStore);
    	assertEquals(inPBData.getTradeable().get(), false);
    	assertEquals(inPBData.getDeliveryDate(), dd);
    	assertEquals(inPBData.getLastTradingDate(), ltd);
    }

}
