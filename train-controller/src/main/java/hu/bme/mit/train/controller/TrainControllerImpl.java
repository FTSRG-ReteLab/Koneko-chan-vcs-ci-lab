package hu.bme.mit.train.controller;

import java.util.Date;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

import hu.bme.mit.train.interfaces.TrainController;

public class TrainControllerImpl implements TrainController {

	private int step = 0;
	private int referenceSpeed = 0;
	private int speedLimit = 0;
	private Table<Integer, Integer, Integer> tachograph;
	private int id = 0; 
	
	public void tableSave() {
		if (tachograph == null)
			tachograph = HashBasedTable.create();
		tachograph.put(id, 0, (int) (new Date().getTime()/1000));
		tachograph.put(id, 1, step);
		tachograph.put(id++, 2, referenceSpeed);
	}
	
	public int tableSize(){
		return tachograph.size();
	}

	@Override
	public void followSpeed() {
		if (referenceSpeed < 0) {
			referenceSpeed = 0;
		} else {
			if (referenceSpeed + step < 0)
				referenceSpeed = 0;
			else
				referenceSpeed += step;
		}

		enforceSpeedLimit();
	}

	@Override
	public int getReferenceSpeed() {
		return referenceSpeed;
	}

	@Override
	public void setSpeedLimit(int speedLimit) {
		this.speedLimit = speedLimit;
		enforceSpeedLimit();
		
	}

	private void enforceSpeedLimit() {
		if (referenceSpeed > speedLimit) {
			referenceSpeed = speedLimit;
		}
	}

	@Override
	public void setJoystickPosition(int joystickPosition) {
		this.step = joystickPosition;		
	}

}
