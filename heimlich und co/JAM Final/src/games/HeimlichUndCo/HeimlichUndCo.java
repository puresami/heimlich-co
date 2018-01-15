	package games.HeimlichUndCo;

	import java.io.IOException;
	import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.NoSuchElementException;
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
		private ArrayList<Agent> agentList =new ArrayList<Agent>();
		private String playerLeft=null;
		private User playerTurn= null;
		Random card= new Random();
		
		private static HeimlichUndCo instance;
		
		public static HeimlichUndCo getInstance() {
			if (instance == null) 
			{
				instance = new HeimlichUndCo();
			}
			return instance;
		}
		
		public void initializeGame() {
			setSafePosition(7);
		agentList=null;
		
		switch (playerList.size()) {
		case 5:
		case 6:
		case 7: {
			agentList.add(takeCard());
			agentList.add(takeCard());
			agentList.add(takeCard());
			agentList.add(takeCard());
			agentList.add(takeCard());
			agentList.add(takeCard());
			agentList.add(takeCard());
			break;
		}
		case 4: {
			agentList.add(takeCard());
			agentList.add(takeCard());
			agentList.add(takeCard());
			agentList.add(takeCard());
			agentList.add(takeCard());
			agentList.add(takeCard());
			break;
		}
		case 3: {
			agentList.add(takeCard());
			agentList.add(takeCard());
			agentList.add(takeCard());
			agentList.add(takeCard());
			agentList.add(takeCard());
			break;
		}
		case 2: {
			agentList.add(takeCard());
			agentList.add(takeCard());
			agentList.add(takeCard());
			agentList.add(takeCard());
			break;
		}
		default:{
			throw new IllegalArgumentException("Ungültige Spieleranzahl!");
		}
		}
	}
		
		
	public void AITurn() {

		Random agent = new Random();
		int fieldsToGo = rollDice();
		Random fieldsgone = new Random();

		// yellow=0,red=1,purple=2,blue=3,green=4,orange=5,grey=6
		while (fieldsToGo > 0) {
			int fieldsGone = fieldsgone.nextInt(6) + 1;
			int agentToMove = agent.nextInt(agentList.size());
			if (fieldsGone > fieldsToGo) {
				fieldsGone = Math.abs(fieldsGone - fieldsToGo);
			}
			switch (agentToMove) {
			case 0: {
				System.out.println("Move Yellow Agent");
				agentList.get(0).setAgentPosition(agentList.get(0).getAgentPosition() + fieldsGone);
				fieldsToGo -= fieldsGone;
				break;
			}
			case 1: {
				System.out.println("Move red agent");
				agentList.get(1).setAgentPosition(agentList.get(1).getAgentPosition() + fieldsGone);
				fieldsToGo -= fieldsGone;
				break;
			}
			case 2: {
				System.out.println("Move purple agent");
				agentList.get(2).setAgentPosition(agentList.get(2).getAgentPosition() + fieldsGone);
				fieldsToGo -= fieldsGone;
				break;
			}
			case 3: {
				System.out.println("Move blue agent");
				agentList.get(3).setAgentPosition(agentList.get(3).getAgentPosition() + fieldsGone);
				fieldsToGo -= fieldsGone;
				break;
			}
			case 4: {
				System.out.println("Move green agent");
				agentList.get(4).setAgentPosition(agentList.get(4).getAgentPosition() + fieldsGone);
				fieldsToGo -= fieldsGone;
				break;
			}
			case 5: {
				System.out.println("Move orange agent");
				agentList.get(5).setAgentPosition(agentList.get(5).getAgentPosition() + fieldsGone);
				fieldsToGo -= fieldsGone;
				break;
			}
			case 6: {
				System.out.println("Move grey agent");
				agentList.get(6).setAgentPosition(agentList.get(6).getAgentPosition() + fieldsGone);
				fieldsToGo -= fieldsGone;
				break;
			}
			default: {
				throw new IllegalArgumentException("Error while AI-Turn");
			}
			}
		}

	}

		public void turn() {
			//moveAgents(); von javascript irgendwie übernehmen(?)
			for(int i=0;i<agentList.size();i++) {
				if (agentList.get(i).getAgentPosition()==getSafePosition()) {
				scoring();
				}
			}
			if (gameOver()) {
			System.out.println("The game is over!");
			//TODO
			}
		}
		public Agent takeCard() {

			int cardTaken = -1;
			// yellow=0,red=1,purple=2,blue=3,green=4,orange=5,grey=6
			cardTaken = card.nextInt(7);
			System.out.println("Card Taken:" + cardTaken);
			for (int i = 0; i <= agentList.size(); i++) {
				if (!(agentList.get(i).getColour() == cardTaken)) {
					switch (cardTaken) {
					case 0: {
						System.out.println("You took the yellow Agent!");
						Agent yellowAgent = new Agent(0);
						return yellowAgent;
					}
					case 1: {
						System.out.println("You took the red Agent!");
						Agent redAgent = new Agent(1);
						return redAgent;
					}
					case 2: {
						System.out.println("You took the purple Agent!");
						Agent purpleAgent = new Agent(2);
						return purpleAgent;
					}
					case 3: {
						System.out.println("You took the blue Agent!");
						Agent blueAgent = new Agent(3);
						return blueAgent;
					}
					case 4: {
						System.out.println("You took the green Agent!");
						Agent greenAgent = new Agent(4);
						return greenAgent;
					}
					case 5: {
						System.out.println("You took the orange Agent!");
						Agent orangeAgent = new Agent(5);
						return orangeAgent;
					}
					case 6: {
						System.out.println("You took the grey Agent!");
						Agent greyAgent = new Agent(6);
						return greyAgent;
					}
					default: {
						System.out.println("Error while taking a card!");
						return null;
					}
					}
				}
			}
			return takeCard();
		}
		public int rollDice(){

			Random dice = new Random();
			int numberRolled = -1;

				numberRolled = 1 + dice.nextInt(6);
				System.out.println("Rolled number:" + numberRolled );
				
			return numberRolled;
		}
		
		private void scoring() {

			for (int i = 0; i < agentList.size(); i++) {
				switch (agentList.get(i).getAgentPosition()) {
				case 0: {
					break;
				}
				case 1: {
					agentList.get(i).setMarkerPosition(agentList.get(i).getMarkerPosition() + 1);
					break;
				}
				case 2: {
					agentList.get(i).setMarkerPosition(agentList.get(i).getMarkerPosition() + 2);
					break;
				}
				case 3: {
					agentList.get(i).setMarkerPosition(agentList.get(i).getMarkerPosition() + 3);
					break;
				}
				case 4: {
					agentList.get(i).setMarkerPosition(agentList.get(i).getMarkerPosition() + 4);
					break;
				}
				case 5: {
					agentList.get(i).setMarkerPosition(agentList.get(i).getMarkerPosition() + 5);
					break;
				}
				case 6: {
					agentList.get(i).setMarkerPosition(agentList.get(i).getMarkerPosition() + 6);
					break;
				}
				case 7: {
					agentList.get(i).setMarkerPosition(agentList.get(i).getMarkerPosition() + 7);
					break;
				}
				case 8: {
					agentList.get(i).setMarkerPosition(agentList.get(i).getMarkerPosition() + 8);
					break;
				}
				case 9: {
					agentList.get(i).setMarkerPosition(agentList.get(i).getMarkerPosition() + 9);
					break;
				}
				case 10: {
					agentList.get(i).setMarkerPosition(agentList.get(i).getMarkerPosition() + 10);
					break;
				}
				case 11: {
					agentList.get(i).setMarkerPosition(agentList.get(i).getMarkerPosition() -3);
					break;
				}
				default: {
					throw new IllegalArgumentException("Kein gültiges Feld");
				}
				}
			}

		}

		public boolean gameOver() {
			for (int i = 0; i < agentList.size(); i++) {
				if (agentList.get(i).getMarkerPosition() >= 42) {
					return true;
				}
			}
			return false;
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
			for(int i=0;i<agentList.size();i++ ) {
				if (agentList.get(i).getAgentPosition()==this.safePosition) {
					throw new IllegalArgumentException("House is already occupied. Choose another one.");
				}
				else if(safePosition<0 || safePosition >=12) {
					throw new IllegalArgumentException("Invalid Position");
				}
				else this.safePosition = safePosition;
			}
		}

		public ArrayList<Agent> getAgentList() {
			return agentList;
		}

		public void setAgentList(ArrayList<Agent> agentList) {
			this.agentList = agentList;
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
		
		public String getPlayerLeft() {
			return playerLeft;
		}

		public void setPlayerLeft(String playerLeft) {
			this.playerLeft = playerLeft;
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

		@Override //von TicTacToe übernommen
		public String getCSS() {
			try {
				return global.FileHelper.getFile("HeimlichUndCo/css/HeimlichUndCo.css");
			} catch (IOException e) {
				System.err
						.println("Loading of file HeimlichUndCo/css/HeimlichUndCo.css failed");
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

		@Override //von TicTacToe übernommen, javascript-listener fehlen noch
		public void addUser(User user) {
			if (playerList.size() < 7 && !playerList.contains(user)) {
				playerList.add(user);

				if (playerTurn == null) {
					playerTurn = user;
				}
				sendGameDataToClients("START");
			}
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
				return true;
			} else {
				return false;
			}
		}

		@Override//von TicTacToe übernommen
		public void playerLeft(User user) {
			playerList.remove(user);
			setPlayerLeft(user.getName());
			sendGameDataToClients("PLAYERLEFT");
			//TODO javascript-listener, siehe seite 44 in der entwicklerdokumentation
		}

		@Override
		public GameState getGameState() {
			return this.gState;
		}
		
		private String isHost(User user) {
			if(user==creator) return ",HOST";
			else return ",NOTTHEHOST";
		}
		
		
	@Override
	public void execute(User user, String gsonString) {
		if (this.gState == GameState.CLOSED)
			return;

		if (gsonString.equals("CLOSE")) {
			sendGameDataToClients("CLOSE");
			closeGame();
			return;
		}

		if (gsonString.equals("RESTART")) {
			initializeGame();
			this.gState = GameState.RUNNING;
			sendGameDataToClients("standardEvent");
			return;
		}
		if (gState != GameState.RUNNING) {
			return;
		}

		/*
		 * String[] strArray = gsonString.split(","); int[] receivedArray = new int[9];
		 * for (int i = 0; i < 9; i++) { receivedArray[i] =
		 * Integer.parseInt(strArray[i]); } int[] gridStatus = getGridStatus();
		 */
		ArrayList<Agent> actualAgentList = new ArrayList<Agent>();

		boolean changed = false;

		if (!actualAgentList.equals(agentList)) {
			changed = true;
		}
		// TODO abgleich von erhaltenem string mit zurzeit gezeigten spieldaten

		if (changed == true) {
			Iterator<User> it = playerList.iterator();
			// playerTurn um eins nach vorn verschieben

			if (it.hasNext()) {
				setPlayerTurn(it.next());
			} else // wenn playerlist durchlaufen wieder bei 0 beginnen
				it = playerList.iterator();
			setPlayerTurn(it.next());

			for (int i = 0; i < agentList.size(); i++) {
				agentList.get(i).setAgentPosition(actualAgentList.get(i).getAgentPosition());
				agentList.get(i).setMarkerPosition(actualAgentList.get(i).getMarkerPosition());
			}
		}
		if (gameOver()) {
			this.gState = GameState.FINISHED;
		}

		sendGameDataToClients("standardEvent");
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
			if(playerList.size()<2){
				gameData += "Warte auf 2ten Spieler oder KI-Spieler...";
				gameData += isHost(user);
				return gameData;
			}
			if (this.gState == GameState.FINISHED) {
				//Ausgabe, welcher Agent auf welchem Platz ist
				Collections.sort(agentList);
				for(Agent a: agentList)
			     System.out.print(a.getColourString() +"  : "+
			     a.getMarkerPosition() + ", ");
				for (int i=0;i<agentList.size();i++) {
					gameData+= agentList.get(i)+" ist mit " 
							+ agentList.get(i).getMarkerPosition()+ " Punkten auf Platz " 
							+ (agentList.indexOf(agentList.get(i))+1);
				}
				//TODO PlayerList / AgentList, wer ist wer
				
			}
			else if (playerTurn.equals(user)) {
				gameData += "Du bist dran!";
			} else
				gameData += playerTurn.getName() + " ist dran!";
			return gameData;
		}
	}
	/*for (int i=0;i<agentList.size();i++) {
		if (eventName.equals("EVENTNAME")) {
			
		}
		else if (eventName.equals("")) {
			
		}
		else {
			throw new ILLegalArgumentException("Ungültiger Eventname")
		}
	}
	*/
	
