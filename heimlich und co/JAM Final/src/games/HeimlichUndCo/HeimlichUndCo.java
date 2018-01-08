package games.HeimlichUndCo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import gameClasses.Game;
import gameClasses.GameState;
import global.FileHelper;
import userManagement.User;

public class HeimlichUndCo extends Game {
	private int safePosition;
	//private int houseNumber; //0=Church, 1-10=houses, 11=ruin
	private ArrayList<User> playerList = new ArrayList<User>();
	private ArrayList<User> spectatorList = new ArrayList<User>();
	private ArrayList<Agent> AgentList;
	private String playerLeft=null;
	private User playerTurn= null;
	
	//bei tictactoe gucken was so fehlt
	public int takeCard() {
		Random card= new Random();
		int cardTaken = -1;
		//red=0,green=1,yellow=2,blue=3,black=4,orange=5,brown=6
		cardTaken = card.nextInt(7);
		System.out.println("Card Taken:" + cardTaken );
		if(cardTaken==0) {
			System.out.println("You took the red Agent!");
		}
		if(cardTaken==1) {
			System.out.println("You took the green Agent!");
		}
		if(cardTaken==2) {
			System.out.println("You took the yellow Agent!");
		}
		if(cardTaken==3) {
			System.out.println("You took the blue Agent!");
		}
		if(cardTaken==4) {
			System.out.println("You took the black Agent!");
		}
		if(cardTaken==5) {
			System.out.println("You took the orange Agent!");
		}
		if(cardTaken==6) {
			System.out.println("You took the brown Agent!");
		}
		if(cardTaken==-1) {
			System.out.println("Error while taking a card!");
		}
		return cardTaken;
	}
	
	public void turn() {
		rollDice();
		move();
		for(int i=0;i<AgentList.size();i++) {
			if (AgentList.get(i).getAgentPosition()==getSafePosition()) {
			scoring();
			}
		}
	}
	
	private void move() {
		// TODO Auto-generated method stub
		
	}

	private void scoring() {
		// TODO Auto-generated method stub

		for (int i = 0; i < AgentList.size(); i++) {
			switch (AgentList.get(i).getAgentPosition()) {
			case 0: {
				break;
			}
			case 1: {
				AgentList.get(i).setMarkerPosition(AgentList.get(i).getMarkerPosition() + 1);
				break;
			}
			case 2: {
				AgentList.get(i).setMarkerPosition(AgentList.get(i).getMarkerPosition() + 2);
				break;
			}
			case 3: {
				AgentList.get(i).setMarkerPosition(AgentList.get(i).getMarkerPosition() + 3);
				break;
			}
			case 4: {
				AgentList.get(i).setMarkerPosition(AgentList.get(i).getMarkerPosition() + 4);
				break;
			}
			case 5: {
				AgentList.get(i).setMarkerPosition(AgentList.get(i).getMarkerPosition() + 5);
				break;
			}
			case 6: {
				AgentList.get(i).setMarkerPosition(AgentList.get(i).getMarkerPosition() + 6);
				break;
			}
			case 7: {
				AgentList.get(i).setMarkerPosition(AgentList.get(i).getMarkerPosition() + 7);
				break;
			}
			case 8: {
				AgentList.get(i).setMarkerPosition(AgentList.get(i).getMarkerPosition() + 8);
				break;
			}
			case 9: {
				AgentList.get(i).setMarkerPosition(AgentList.get(i).getMarkerPosition() + 9);
				break;
			}
			case 10: {
				AgentList.get(i).setMarkerPosition(AgentList.get(i).getMarkerPosition() + 10);
				break;
			}
			case 11: {
				AgentList.get(i).setMarkerPosition(AgentList.get(i).getMarkerPosition() - 3);
				break;
			}
			default: {
				throw new IllegalArgumentException("Kein gültiges Feld");
			}
			}
		}

		for (int i = 0; i < AgentList.size(); i++) {
			if (AgentList.get(i).getMarkerPosition() >= 42) {
				System.out.println("Game over.");
			}
		}
	}

	public int rollDice(){

		Random dice = new Random();
		int numberRolled = -1;

			numberRolled = 1 + dice.nextInt(6);
			System.out.println("Rolled number:" + numberRolled );
			
		return numberRolled;
	}
	
	public void initializeGame() {
		for(int i=0;i<AgentList.size();i++) {
			AgentList.get(i).setAgentPosition(0);
		}
		setSafePosition(7);
	}
	
	@Override
	public void execute(User user, String gsonString) {
		
		if(this.gState==GameState.CLOSED) return;
		
		if(gsonString.equals("CLOSE")){
			sendGameDataToClients("CLOSE");
			closeGame();
			return;
		}
		
		if (gsonString.equals("RESTART")) {
			/*
			 * Restart muss noch implementiert werden	todo
			 * 
			 */
		}
		
		
	}
	
	@Override
	public int getMaxPlayerAmount() {
		return 7;
	}

	@Override
	public int getCurrentPlayerAmount() {
		return playerList.size();
	}

	public int getSafePosition() {
		return safePosition;
	}

	public void setSafePosition(int safePosition) {
		this.safePosition = safePosition;
	}

	public ArrayList<Agent> getAgentList() {
		return AgentList;
	}

	public void setAgentList(ArrayList<Agent> agentList) {
		AgentList = agentList;
	}

	/*public int getHouseNumber() {
		return houseNumber;
	}

	public void setHouseNumber(int houseNumber) {
		this.houseNumber = houseNumber;
	}*/
	public User getPlayerTurn() {
		return playerTurn;
	}

	public void setPlayerTurn(User playerTurn) {
		this.playerTurn = playerTurn;
	}
	@Override
	public String getSite() {
		try {
			return FileHelper.getFile("HeimlichUndCo/HeimlichUndCo.html");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String getCSS() {
		try {
			return global.FileHelper.getFile("HeimlichUndCo/css/HeimlichUndCo.css");
		} catch (IOException e) {
			System.err
					.println("Loading of file HeimlichUndCo/HeimlichUndCo.css failed");
		}
		return null;
	}

	@Override
	public String getJavaScript() {
		return "<script src=\"javascript/HeimlichUndCo.js\"></script>";
	}

	

	@Override
	public ArrayList<User> getPlayerList() {
		return this.playerList;
	}


	@Override
	public ArrayList<User> getSpectatorList() {
		return this.spectatorList;
	}

	@Override
	public String getGameData(String eventName, User user) {
		
		String gameData = "";
		if(eventName.equals("PLAYERLEFT")){
			return playerLeft + " hat das Spiel verlassen!";
		}
		if(eventName.equals("CLOSE")){
			return "CLOSE";
		}
		return null;
	}

	@Override
	public void addUser(User user) {
		if (playerList.size() < 7 && !playerList.contains(user)) {
			playerList.add(user);

			if (playerTurn == null) {
				playerTurn = user;
			}
			sendGameDataToClients("START");
		}
		/*
		 * Hier muss die Spieleranzahl variabel sein!	todo
		 * 
		 * 
		 */
		if (playerList.size() == 7) {
			this.gState = GameState.RUNNING;
			sendGameDataToClients("START");
		}

	}

	@Override
	public void addSpectator(User user) {
		this.spectatorList.add(user);
	}

	@Override
	public boolean isJoinable() {
		if (playerList.size() < 7) {
			/*
			 * max Spielerzahl einer Session auf 7 begrenzt		todo
			 * 
			 * 
			 */
			return true;
		} else {
			return false;
		}

	}

	@Override
	public void playerLeft(User user) {
		playerList.remove(user);
		playerLeft = user.getName();
		sendGameDataToClients("PLAYERLEFT");
	}

	@Override
	public GameState getGameState() {
		return this.gState;
	}
	
	private String isHost(User user) {
		if(user==creator) return ",HOST";
		else return ",NOTTHEHOST";
	}

	
}
