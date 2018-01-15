package games.HeimlichUndCo;

public class Agent implements Comparable<Agent>{
	private int colour;
	//yellow=0,red=1,purple=2,blue=3,green=4,orange=5,grey=6
	private int agentPosition;
	private int markerPosition;

	public Agent(int colour, int agentPosition, int markerPosition) {
			this.colour = colour;
			this.agentPosition = agentPosition;
			this.markerPosition = markerPosition;
	}

	public Agent(int colour) {
		colour=this.colour;
		agentPosition=0;
		markerPosition=0;
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

	@Override
	public int compareTo(Agent arg0) {
		
		return Integer.compare(this.getMarkerPosition(), arg0.getMarkerPosition());
	}

	public String getColourString() {
		String colour = "";
		switch (getColour()) {
		case 0:
			colour = "yellow";
		case 1:
			colour = "red";
		case 2:
			colour = "purple";
		case 3:
			colour = "blue";
		case 4:
			colour = "green";
		case 5:
			colour = "orange";
		case 6:
			colour = "grey";
		default: 
			colour="Error while getting a String of the colour";
		}
		return colour;
	}
}