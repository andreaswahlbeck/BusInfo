package net.sandbox.busservice.data;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class BusStop {
	private String name;
	private Position position;

	public BusStop(){}
	
	public BusStop(String name, Position position) {
		super();
		this.name = name;
		this.position = position;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

}
