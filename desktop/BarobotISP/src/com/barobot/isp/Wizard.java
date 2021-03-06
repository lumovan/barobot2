package com.barobot.isp;

import com.barobot.parser.Queue;
import com.barobot.parser.message.AsyncMessage;
import com.barobot.parser.message.Mainboard;

public class Wizard {

	/*
	0	Upanel numer 0, adres: 12, index 3	0
	1	Upanel numer 1, adres: 19, index 0	2
	2	Upanel numer 2, adres: 17, index 0	4
	3	Upanel numer 3, adres: 20, index 0	6
	4	Upanel numer 4, adres: 22, index 0	8
	5	Upanel numer 5, adres: 14, index 0	10
	6	Upanel numer 0, adres: 16, index 4	1
	7	Upanel numer 1, adres: 23, index 0	3
	8	Upanel numer 2, adres: 18, index 0	5
	9	Upanel numer 3, adres: 15, index 0	7
	10	Upanel numer 4, adres: 21, index 0	9
	11	Upanel numer 5, adres: 13, index 0	11

	0,2,4,6,8,10,1,3,5,7,9,11
	*/


	public void findStops(Hardware hw) {
		Queue q = hw.getQueue();
		hw.connectIfDisconnected();
		hw.barobot.x.moveTo( q, 100 );
		hw.barobot.x.moveTo( q, 1000 );	
		q.addWait(5000 );
	}

	public void mrygaj_po_butelkach(Hardware hw, int time ) {
		hw.getQueue().addWaitThread( Main.main );
		Queue q = hw.getQueue();

		hw.barobot.lightManager.turnOffLeds(q);
	//	get 2 random from array java
		
		for( int i =0; i<11; i++){

			q.addWait(time );
			hw.barobot.lightManager.setLedsByBottle(q, i, "ff", 0, 0, 0, 0);

			q.addWait(time );

			hw.barobot.lightManager.setLedsByBottle(q, i, "ff", 0, 0, 0, 0);
			hw.barobot.lightManager.setLedsByBottle(q, i, "01", 255, 255, 0, 0);
			
			q.addWait(time );

			hw.barobot.lightManager.setLedsByBottle(q, i, "ff", 0, 0, 0, 0);
			hw.barobot.lightManager.setLedsByBottle(q, i, "02", 255, 0, 255, 0);
			q.addWait(time );

			hw.barobot.lightManager.setLedsByBottle(q, i, "ff", 0, 0, 0, 0);
			hw.barobot.lightManager.setLedsByBottle(q, i, "04", 255, 0, 0, 255);
			q.addWait(time );
			
	
			hw.barobot.lightManager.setLedsByBottle(q, i, "ff", 0, 0, 0, 0);
			hw.barobot.lightManager.setLedsByBottle(q, i, "08", 255, 255, 255, 255);
			q.addWait(time );

			hw.barobot.lightManager.setLedsByBottle(q, i, "ff", 0, 0, 0, 0);
		}
	}

	public void mrygaj_grb(Hardware hw, int repeat) {
		hw.getQueue().addWaitThread( Main.main );
		hw.connectIfDisconnected();
		Queue q = hw.getQueue();

		int time = 0;
		while (repeat-- > 0){
			hw.barobot.lightManager.turnOffLeds(q);
			q.addWait(time );
			hw.barobot.lightManager.setAllLeds(q, "07", 255, 255, 255, 255);
			q.addWait(time );
			time--;	
		}
	}

	

	public void illumination1(Hardware hw) {
		hw.connectIfDisconnected();
		hw.getQueue().addWaitThread(Main.main);
		int repeat = 1;
		while(repeat-->0){
	//		
		//	hw.getQueue().addWaitThread( Main.main );
			this.mrygaj_grb( hw, 30 );
			hw.getQueue().addWaitThread( Main.main );
		//	this.fadeAll( hw, 5 )
		//	hw.getQueue().addWaitThread( Main.main );
		}
		System.out.println("koniec illumination1");
	}
	public void ilumination2(Hardware hw) {
		hw.getQueue().addWaitThread( Main.main );
		hw.connectIfDisconnected();
		Queue q = hw.getQueue();
		for (int b = 0;b<4;b++){
			int i=0;
			for (;i<245;i+=5){
				hw.barobot.lightManager.setAllLeds(q, "ff", i, i, i, i);
			}
			for (;i>=0;i-=5){
				hw.barobot.lightManager.setAllLeds(q, "ff", i, i, i, i);
			}
		}

		System.out.println("koniec ilumination2");
	}


	
	public void zamrugaj(Hardware hw, int time, int razy ){
		hw.getQueue().addWaitThread( Main.main );
		hw.connectIfDisconnected();
		int swiec = 255;
		Queue q = hw.getQueue();
		for( int i =0; i<razy;i++){
			hw.barobot.lightManager.setAllLeds(q, "f0", 0, 0, 0, 0);
			hw.barobot.lightManager.setAllLeds(q, "f0", swiec, swiec, swiec, swiec);
		}
		System.out.println("koniec mrygaj");
	}
	public void fadeButelka(final Hardware hw, final int num, final int count) {
		Queue q = hw.getQueue();
		q.add( new AsyncMessage( true ){
			@Override
			public String getName() {
				return "aaaa";
			}
			@Override
			public Queue run(Mainboard dev, Queue queue) {
				Queue q2 = new Queue();
				hw.barobot.lightManager.turnOffLeds( q2 );
				for (int b = 0;b<count;b++){
					int i=0;
					for (;i<255;i+=4){
						hw.barobot.lightManager.setLedsByBottle(q2, num, "ff", i, i, i, i);					
					}
					for (i=255;i>=0;i-=4){
						hw.barobot.lightManager.setLedsByBottle(q2, num, "ff", i, i, i, i);
					}
					hw.barobot.lightManager.turnOffLeds( q2 );
				} 
				System.out.println("koniec fadeButelka1");
				return q2;
			}
		});		
		System.out.println("koniec fadeButelka2");
	}
	
	public void fadeAll(final Hardware hw, final int count) {
		Queue q = hw.getQueue();
		q.add( new AsyncMessage( true ){
			@Override
			public String getName() {
				return "aaaa";
			}
			@Override
			public Queue run(Mainboard dev, Queue queue) {
				Queue q2 = new Queue();
				hw.barobot.lightManager.turnOffLeds( q2 );
				for (int b = 0;b<count;b++){
					int i=0;
					for (;i<255;i+=8){
						hw.barobot.lightManager.setAllLeds(q2, "ff", i, i, i, i);
					}
					for (i=255;i>=0;i-=8){
						hw.barobot.lightManager.setAllLeds(q2, "ff", i, i, i, i);
					}
					hw.barobot.lightManager.turnOffLeds( q2 );
				} 
				System.out.println("koniec fadeButelka");
				return q2;
			}
		});
	}

	public void swing(Hardware hw, int i, int min, int max) {
		hw.connectIfDisconnected();
	}

	public void test_proc(Hardware hw) {
		Queue q = hw.getQueue();
		q.add( "P3", false );
	//	SISP
/*
		q.add(new AsyncMessage( "I", true ){
					@Override
					public boolean isRet(String result, Queue q) {
						if( "RI".equals(result)){
							return true;
						}
						return false;
					}
					public boolean onInput(String command) {
						System.out.println("onInput: " + command);
						return false;
					}
		});*/
		q.add("I", "RI");
		q.add("TEST", "RTEST");
	//	q.addWaitThread( Main.main );
	}

	public void fast_close_test(Hardware hw) {
		Queue q = hw.getQueue();
	//	I2C_Device current_dev	= new BarobotTester();
		hw.connectIfDisconnected();
		q.add("K1","RK1");

		hw.closeNow();
		hw.connectIfDisconnected();
		q.add("K1","RK1");
		hw.closeNow();
		
		hw.connectIfDisconnected();
		q.add("K1","RK1");
		hw.closeNow();

		hw.connectIfDisconnected();
		q.add("K1","RK1");
		hw.closeNow();
	}
	
	public void koniec(Hardware hw) {
		hw.getQueue().addWaitThread( Main.main );
		Queue q = hw.getQueue();
		q.addWait(5000 );
		hw.barobot.lightManager.turnOffLeds( q );
	}
}