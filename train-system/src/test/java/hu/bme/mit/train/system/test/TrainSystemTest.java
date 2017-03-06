package hu.bme.mit.train.system.test;

import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import hu.bme.mit.train.interfaces.TrainController;
import hu.bme.mit.train.interfaces.TrainSensor;
import hu.bme.mit.train.interfaces.TrainUser;
import hu.bme.mit.train.system.TrainSystem;

public class TrainSystemTest {

	TrainController controller;
	TrainSensor sensor;
	TrainUser user;
	
	@Before
	public void before() {
		TrainSystem system = new TrainSystem();
		controller = system.getController();
		sensor = system.getSensor();
		user = system.getUser();

		sensor.overrideSpeedLimit(50);
	}
	
	@Test
	public void test1() {
		sensor.overrideSpeedLimit(10);

		Assert.assertEquals(0, controller.getReferenceSpeed());
		
		user.overrideJoystickPosition(5);

		controller.followSpeed();
		Assert.assertEquals(5, controller.getReferenceSpeed());
		controller.followSpeed();
		Assert.assertEquals(10, controller.getReferenceSpeed());
		controller.followSpeed();
		Assert.assertEquals(10, controller.getReferenceSpeed());
	}

	@Test
	public void test2() {
		user.overrideJoystickPosition(4);
		controller.followSpeed();
		user.overrideJoystickPosition(-5);
		controller.followSpeed();
		Assert.assertEquals(0, controller.getReferenceSpeed());
	}
	
	@Test
	public void test3() {
		user.overrideJoystickPosition(6);
		Assert.assertEquals(6, user.getJoystickPosition());
	}
	
	@Test
	public void test4() {
		controller.tableSave();
		try {
			TimeUnit.SECONDS.sleep(2);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		controller.tableSave();
		controller.tableSave();
		Assert.assertNotEquals(0, controller.tableSize());
	}

	
}
