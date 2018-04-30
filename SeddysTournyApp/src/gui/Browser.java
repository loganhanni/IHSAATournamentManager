package gui;


import java.net.URL;

import bridge.JavascriptBridge;
import databaseservices.SchoolService;
import javafx.concurrent.Worker.State;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.layout.Region;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import netscape.javascript.JSObject;

public class Browser extends Region {

	final WebView browser = new WebView();
	final WebEngine webEngine = browser.getEngine();
	JavascriptBridge bridge = new JavascriptBridge();
	SchoolService ss = new SchoolService();

	public Browser() {
		// apply the styles
		getStyleClass().add("browser");

		// A test bridge to javascript
		JSObject jsobj = (JSObject) webEngine.executeScript("window");
		jsobj.setMember("Bridge", bridge);

		// Javascript connection to the SchoolService via the Browser
		JSObject service = (JSObject) webEngine.executeScript("window");
		service.setMember("SchoolService", ss);

		webEngine.getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {
			if (newState == State.SUCCEEDED) {
				JSObject jsobj2 = (JSObject) webEngine.executeScript("window");
				jsobj2.setMember("Bridge", bridge);

				// Javascript connection to the SchoolService via the Browser
				JSObject service2 = (JSObject) webEngine.executeScript("window");
				service2.setMember("SchoolService", ss);
			}
		});

		// load the web page
		URL url = this.getClass().getResource("../webTable/index.html");
		webEngine.load(url.toString());
		// add the web view to the scene
		getChildren().add(browser);
	}

	// Center the window and set width and height
	@Override
	protected void layoutChildren() {
		double w = getWidth();
		double h = getHeight();
		layoutInArea(browser, 0, 0, w, h, 0, HPos.CENTER, VPos.CENTER);
	}

	// Set the preferred window width
	@Override
	protected double computePrefWidth(double height) {
		return 950;
	}

	// Set the preferred window height
	@Override
	protected double computePrefHeight(double width) {
		return 950;
	}
}
