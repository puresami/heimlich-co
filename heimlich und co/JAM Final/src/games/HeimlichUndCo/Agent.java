package games.HeimlichUndCo;

public class Agent {
	private int colour;
	//red=0,green=1,yellow=2,blue=3,black=4,orange=5,brown=6
	private int agentPosition;
	private int markerPosition;

	public Agent(int colour, int agentPosition, int markerPosition) {
		this.colour = colour;
		this.agentPosition = agentPosition;
		this.markerPosition = markerPosition;
	}

	
	public int getColour() {
		return colour;
	}

	public void setColour(int colour) {
		this.colour = colour;
	}

	public int getMarkerPosition() {
		return markerPosition;
	}

	public void setMarkerPosition(int markerPosition) {
		this.markerPosition = markerPosition;
	}

	public int getAgentPosition() {
		return agentPosition;
	}

	public void setAgentPosition(int agentPosition) {
		this.agentPosition = agentPosition;
	}
}

