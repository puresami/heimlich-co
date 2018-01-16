package games.HeimlichUndCo;

public class TableObject {
	private String colour;
	private String belongsTo;
	
	public TableObject(String colour,String belongsTo,int points,String note) {
		this.colour=colour;
		this.belongsTo=belongsTo;

	}
	
	public String getColour() {
		return colour;
	}

	public void setColour(String colour) {
		this.colour = colour;
	}

	public String getBelongsTo() {
		return belongsTo;
	}

	public void setBelongsTo(String belongsTo) {
		this.belongsTo = belongsTo;
	}
}
