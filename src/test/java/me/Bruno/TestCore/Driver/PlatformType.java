package me.Bruno.TestCore.Driver;

public enum PlatformType {

	WINIUM("webdriver.winium.driver.desktop"),
	OPERA("webdriver.opera.driver"),
	CHROME("webdriver.chrome.driver"),
	FIREFOX("webdriver.gecko.driver"),
	EDGE("webdriver.edge.driver"),
	ANDROID("http://127.0.0.1:4723/wd/hub");
	
	private String property;
	
	private PlatformType(String property) {
		this.property = property;
	}
	
	public String getProperty() {
		return this.property;
	}
	
}
