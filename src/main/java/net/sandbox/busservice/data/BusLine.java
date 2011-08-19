package net.sandbox.busservice.data;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class BusLine {
	private String name;
	private String lineNumber;
	
	public BusLine(){}
	
	public BusLine(String name, String lineNumber) {
		this.name = name;
		this.lineNumber = lineNumber;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLineNumber() {
		return lineNumber;
	}
	public void setLineNumber(String lineNumber) {
		this.lineNumber = lineNumber;
	}
}
