package com.maxim.utils;
import java.sql.Date;
import java.util.Calendar;
import java.util.TimerTask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.maxim.contollers.CouponsController;
@Component
public class MyTimerTask extends TimerTask {
	@Autowired
	private CouponsController couponsController;


	
	@Override
    public void run() {
		long now = Calendar.getInstance().getTimeInMillis();
		Date todayDate = new Date(now);
		try {
			couponsController.removeOldCoupons(todayDate);
			//System.out.println("old coupons deleted");
	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    
}