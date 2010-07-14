package me.galkin.eclipse.php.domain;

import static org.junit.Assert.*;

import org.junit.Test;

public class ResultsCountTest {

	@Test
	public void getPercentage() {
		assertEquals(0.5, ResultsCount.running(1, 2).getPercentage(), 0.01);
	}
	
	@Test
	public void getPercentage_started() {
		assertEquals(0, ResultsCount.running(0, 0).getPercentage(), 0.01);
	}
	
	@Test
	public void getPercentage_finished() {
		assertEquals(1, ResultsCount.running(11, 11).getPercentage(), 0.01);
	}
	
}
