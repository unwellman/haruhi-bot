package net.ddns.haruhionly.testHaruhi;

import net.ddns.haruhionly.haruhi.Haruhi;

public class TestHaruhi {
	public Haruhi haruhi;

	public TestHaruhi() {
		this.haruhi = new Haruhi();
	}

	public void testEnable() {
		this.haruhi.onEnable();
		// Need to provide a getConfig() that works
	}

	public void testConfigure() {
		// How do you test a private method?
		// Assert that Spigot got the request for config file
		// Assert that config file has expected contents
	}
}

