package games.HeimlichUndCo;

public class Agent  implements Comparable<Agent>{
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
	public String toString() {
		return "colour: "+ this.getColourString() + ", Position: "+ this.getAgentPosition()+",Points"+this.getMarkerPosition();
		
	}

	public String getColourString() {
	
		String colour = "";
		switch (getColour()) {
		
		case 0:
			colour = "yellow";
			break;
		case 1:
			colour = "red";
			break;
		case 2:
			colour = "purple";
			break;
		case 3:
			colour = "blue";
			break;
		case 4:
			colour = "green";
			break;
		case 5:
			colour = "orange";
			break;
		case 6:
			colour = "gray";
			break;
		default: 
			colour="Error while getting a String of the colour";
		}
		return colour;
	}
	
public String getColourStringGerman() {
		
		String colour = "";
		switch (getColour()) {
		
		case 0:
			colour = "Gelb";
			break;
		case 1:
			colour = "Rot";
			break;
		case 2:
			colour = "Lila";
			break;
		case 3:
			colour = "Blau";
			break;
		case 4:
			colour = "Grün";
			break;
		case 5:
			colour = "Orange";
			break;
		case 6:
			colour = "Grau";
			break;
		default: 
			colour="Error while getting a String of the colour";
		}
		return colour;
	}

	@Override
	public int compareTo(Agent o) {
		
		return Integer.compare(this.getMarkerPosition(), o.getMarkerPosition());
	}

}
