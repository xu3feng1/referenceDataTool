package com.scb;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Optional;
import java.util.Random;

import com.scb.DataModel.DataSource;
import com.scb.DataModel.ExternalRecvedData;
import com.scb.DataModel.InternalPublishedData;
import com.scb.EventBus.ConcurrentEventBus;
import com.scb.EventBus.EventBus;
import com.scb.EventBus.EventListener;
import com.scb.RefData.DataStore;
import com.scb.RefData.DataSubscriber;
import com.scb.RefData.InMemoryDataStore;
import com.scb.RulesEngine.MergeRuleItem;

/**
 * @Author:Xu Feng
 * @email: fx2004@gmail.com
 * 2019/03/09
 *
 */

public class ReferenceDataMain 
{
	private final static DataStore memoryDataStore = new InMemoryDataStore();
	private final static EventBus eventBus = new ConcurrentEventBus();
	private final static Random rndGen = new Random();
	private final static DataSubscriber DataSub = ReferenceDataMain::onData;
	static {
		eventBus.addSubscriber(ExternalRecvedData.class, ReferenceDataMain::onMessage);
	}
    
	public static void main( String[] args )
    {
       System.out.println("Listening for external data.\n");
       System.out.println("Typing \"exit\" to quit.");
       //Simulate LME
    	new Thread(() -> {
    		while (true) { 
    			try {
    			Thread.sleep(2000);
    		} catch (InterruptedException ie) {
    		System.err.println("LME simulator thread throws exception " + ie);	
    		}
              if (rndGen.nextInt(100) < 20 ) {  
				LocalDate localdate = LocalDate.now();
                int days = rndGen.nextInt(180);
				Date ltd = Date.valueOf(localdate.plusDays(days));
				Date dd = Date.valueOf(localdate.plusDays(days+2));
                int id = rndGen.nextInt(1000);
                ExternalRecvedData lmeData = new ExternalRecvedData("LME_" + id, ltd, dd, "LEAD " + ltd.toString(), "LME_" + id, DataSource.LME);	
                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                System.out.println(timestamp + " LME publsihed data: " +lmeData.toString());
                eventBus.publishEvent(lmeData);
                }
    		}
       }).start();
    	
    	//Simulate Prime Broker
    	new Thread(() -> {
    		while (true) { 
    			try {
    			Thread.sleep(5000);
    		} catch (InterruptedException ie) {
    		System.err.println("Prime Broker thread simulator thread throws exception " + ie);	
    		}
              if (rndGen.nextInt(100) < 20 ) {  
				LocalDate localdate = LocalDate.now();
                int days = rndGen.nextInt(180);
				Date ltd = Date.valueOf(localdate.plusDays(days));
				Date dd = Date.valueOf(localdate.plusDays(days+2));
                int id = rndGen.nextInt(1000);
                ExternalRecvedData pbData = new ExternalRecvedData("PB_" + id, ltd, dd, "LEAD " + ltd.toString(), "LME_" + id, DataSource.PRIME);
                if (id < 500) pbData.setTradeable(Optional.of(false));
                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                System.out.println(timestamp + " PB publsihed data: " +pbData.toString());
                eventBus.publishEvent(pbData);
                }
    		}
       }).start();
    	
       try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))){ 
          while (true) {
    	    if (reader.ready()) { 
               final  String input = reader.readLine();
               if (input.equalsIgnoreCase("exit")) {
            	 System.out.println("User command \"exit\" received");
            	 System.exit(0);
               }
    	    }
          }
       } catch (IOException ioe) {
    	   System.out.println("IOException when reading user command: " + ioe);
         }
    }
   
    public static void onMessage(Object o) {
    	if (!(o instanceof ExternalRecvedData)) throw new RuntimeException("Unexpected event type received: " + o.toString());
        DataSub.onMessage((ExternalRecvedData)o);  	
    }
    
    public static void onData(ExternalRecvedData recvData) {
 	   for (MergeRuleItem mri : MergeRuleItem.values()) {
 		  if(mri.isApplicable(recvData, memoryDataStore)) { 
 		    InternalPublishedData idata = mri.merge(recvData, memoryDataStore);
 		    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
 		    if (idata !=null) System.out.println(timestamp + " Publsihed data internally: " + idata.toString());
 		    break;
 		  }
 	   }
    	
    }
}