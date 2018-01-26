package games.HeimlichUndCo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import gameClasses.Game;
import gameClasses.GameState;
import global.FileHelper;

import userManagement.User;

public class HeimlichUndCo extends Game {
	/*
	 * dataArray contains the gameData
	 */
	private int safePosition;
	private ArrayList<User> playerList = new ArrayList<User>();
	private ArrayList<User> spectatorList = new ArrayList<User>();
	private ArrayList<Agent> agentList = new ArrayList<Agent>();
	private String playerLeft = null;
	private User playerTurn = null;
	private int[] dataArray;
	private int playerAmount=0;
	private boolean p=false;

	// PRO-Version
	// private String[] notes;

	private static HeimlichUndCo instance;

	public static HeimlichUndCo getInstance() {
		if (instance == null) {
			instance = new HeimlichUndCo();
		}
		return instance;
	}

	public void initializeGame() {
		Random Agent = new Random();
		agentList = null;
		int agent = 0;
		int[] dataArr = { -1, -1, -1, -1, -1, -1, -1, 7, 0, 0, 0, 0, 0, 0, 0 };

		for (int i = 0; i < 7; i++) {
			if (getCurrentPlayerAmount() >= 5) {
				dataArr[i] = 0;
			} else {
				while (getSum(dataArr) < getCurrentPlayerAmount() - 5) { // Spieleranzahl bestimmt die Arraysumme, da -1
																		 // oder 0
					agent = Agent.nextInt(getCurrentPlayerAmount() + 2);
					if (dataArr[agent] == -1) {
						dataArr[agent] = 0;
					}
				}
			}
		}
		setDataArray(dataArr);
		instantiateAgents(dataArr);
		playerTurn = getGameCreator();
	}
    
	public void AITurn() {
		Random agent = new Random();
		int fieldsToGo = rollDice();
		int rolled = fieldsToGo;
		Random fieldsgone = new Random();

		// yellow=0,red=1,purple=2,blue=3,green=4,orange=5,grey=6
		while (fieldsToGo > 0) {
			int fieldsGone = fieldsgone.nextInt(6) + 1;
			int agentToMove = agent.nextInt(agentList.size());

			if (fieldsGone > fieldsToGo) {
				fieldsGone = Math.abs(fieldsGone - fieldsToGo);
			}

			if (rolled < fieldsToGo) {
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

			} else {
				agentList.get(agentToMove).setAgentPosition(agentList.get(agentToMove).getAgentPosition() + rolled);
			}
			
			
		}
		int[] dataarray=new int[15];
		for(int i=0;i<7;i++) {
			dataarray[i]=agentList.get(i).getAgentPosition();
			dataarray[i+8]=agentList.get(i).getMarkerPosition();
		}
		for (int i = 0; i < agentList.size(); i++) {
			if (agentList.get(i).getAgentPosition() == getSafePosition()) {
				scoring();
			}
		}
		Random tresor = new Random();
		int safe=tresor.nextInt(12);
		
		ArrayList<Integer> excluded = new ArrayList<Integer>();
		for(int i=0;i<agentList.size();i++) {
			excluded.add(agentList.get(i).getAgentPosition());	
		}
		setSafePosition(generateSafePosition(safe,excluded));
		setDataArray(dataarray);
	}

	public int generateSafePosition(int safe ,ArrayList<Integer> excluded) {
	    Random rand = new Random();
	    int random = rand.nextInt(12);

	    if(!excluded.contains(safe)) {
	    	return safe;
	    }
	    else {
	    	safe=(safe+random)%12;
	    	return generateSafePosition(safe,excluded);
	    }
	}

	public ArrayList<Agent> instantiateAgents(int[] data) {
		ArrayList<Agent> newAgentList = new ArrayList<Agent>();

		for (int i = 0; i < 7; i++) {
			if (data[i] != -1) {
				switch (i) {
				case 0: {
					Agent yellowAgent = new Agent(0);
					newAgentList.add(yellowAgent);
					break;
				}
				case 1: {
					Agent redAgent = new Agent(1);
					newAgentList.add(redAgent);
					break;
				}
				case 2: {
					Agent purpleAgent = new Agent(2);
					newAgentList.add(purpleAgent);
					break;
				}
				case 3: {
					Agent blueAgent = new Agent(3);
					newAgentList.add(blueAgent);
					break;
				}
				case 4: {
					Agent greenAgent = new Agent(4);
					newAgentList.add(greenAgent);
					break;
				}
				case 5: {
					Agent orangeAgent = new Agent(5);
					newAgentList.add(orangeAgent);
					break;
				}
				case 6: {
					Agent greyAgent = new Agent(6);
					newAgentList.add(greyAgent);
					break;
				}
				default: {
					System.out.println("No Agents instantiated");
				}
				}
			}
		}
		setAgentList(newAgentList);
		return agentList;
	}

	public int getSum(int[] array) {
		int sum = 0;
		for (int i : array) {
			sum += array[i];
		}
		return sum;

	}

	public int rollDice() {

		Random dice = new Random();
		int numberRolled = -1;

		numberRolled = 1 + dice.nextInt(6);
		System.out.println("Rolled number:" + numberRolled);

		return numberRolled;
	}

	public void scoring() {

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
				if (agentList.get(i).getMarkerPosition() >= 3) {
					agentList.get(i).setMarkerPosition(agentList.get(i).getMarkerPosition() - 3);
				}
				break;
			}
			default: {
				throw new IllegalArgumentException("Kein gültiges Feld");
			}
			}
		}

	}

	public boolean gameOver() {
		boolean gameOver = false;
		for (int i = 0; i < agentList.size(); i++) {
			if (agentList.get(i).getMarkerPosition() >= 42) {
				gameOver = true;
			}
		}
		return gameOver;
	}

	public HashMap<String,String> assignColour() {
		HashMap<String, String> hash = new HashMap<String, String>(7);
		ArrayList<Integer> colourList = new ArrayList<Integer>(7);
		for (int i = 0; i < 7; i++) {
			colourList.add(i);
		}
		Collections.shuffle(colourList);

		for (int i = 1; i <= playerList.size(); i++) {

			String name = playerList.get(i).getName();
			int colourInt = colourList.get(i);
			String colour;
			switch (colourInt) {
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
				colour = "Error while getting a String of the colour";
			}

			hash.put(name, colour);
			
		}
		return hash; 
	}
	
	public String intArrayToString(int[] intArr) {
		String data = Arrays.toString(intArr);// .replaceAll("\\[|\\]|,|\\s", ""); für andere form, nötig?
		return data;

	}

	public String hashmapToString(HashMap<String, String> hashmap) {
		String hashToString =  "";
		for(int i = 0; i<playerList.size();i++) {
			
			
			hashToString += playerList.get(i).getName();
			hashToString += " : ";
			hashToString +=  hashmap.get(playerList.get(i).getName());
			hashToString += ",";
			
			
		}

		return hashToString;

	}

	@Override
	public int getMaxPlayerAmount() {
		return playerAmount;
	}

	@Override
	public int getCurrentPlayerAmount() {
		return playerList.size();
	}

	public int getCurrentAgentAmount(){
		int agentAmount=0;
		if (getCurrentPlayerAmount()<5) {
			agentAmount=getCurrentPlayerAmount()+2;
		}
		else {
			agentAmount=7;
		}
		
		return agentAmount;
	}
	public int getSafePosition() {
		return safePosition;
	}

	public void setSafePosition(int safePosition) {
		for (int i = 0; i < agentList.size(); i++) {
			if (agentList.get(i).getAgentPosition() == this.safePosition) {
				throw new IllegalArgumentException("House is already occupied. Choose another one.");
			} else if (safePosition < 0 || safePosition >= 12) {
				throw new IllegalArgumentException("Invalid Position");
			} else
				this.safePosition = safePosition;
			dataArray[7] = safePosition;
		}
	}

	public ArrayList<Agent> getAgentList() {
		return agentList;
	}

	public void setAgentList(ArrayList<Agent> agentList) {
		this.agentList = agentList;
	}

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

	@Override
	public String getCSS() {
		try {
			return global.FileHelper.getFile("HeimlichUndCo/css/HeimlichUndCo.css");
		} catch (IOException e) {
			System.err.println("Loading of file HeimlichUndCo/css/HeimlichUndCo.css failed");
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
	public void addUser(User user) {
		if (playerList.size() < playerAmount && !playerList.contains(user)) {
			playerList.add(user);
				
			System.out.println("creating");

		}
		/*if (playerAmount>7){//Nötig..bzw funktionierts?
			addSpectator(user);
		}*/
		
		if (playerList.size() == playerAmount) {
			this.gState = GameState.RUNNING;
			for(int i=0;i<playerList.size();i++) {
				if (!user.getName().contains("KI-")) {
			sendGameDataToClients("START");
				}
			}
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

	@Override 
	public void playerLeft(User user) {
		playerList.remove(user);
		setPlayerLeft(user.getName());
		for(int i=0;i<playerList.size();i++) {
			if (!user.getName().contains("KI-")) {
		sendGameDataToClients("PLAYERLEFT");
			}
		}
	}

	@Override
	public GameState getGameState() {
		return this.gState;
	}

	public int[] getDataArray() {
		return dataArray;
	}

	public void setDataArray(int[] dataArray) {
		this.dataArray = dataArray;
	}

	private String isHost(User user) {
		if (user == creator)
			return ",HOST";
		else
			return ",NOTTHEHOST";
	}

	@Override
	public void execute(User user, String gsonString) {
		if (gsonString.equals("HI")) {
		  sendGameDataToUser(user,"CREATE");
		  return;
		}
		if (this.gState == GameState.CLOSED)
			return;
		System.out.println(gsonString);

		if (gsonString.equals("CLOSE")) {
			for(int i=0;i<playerList.size();i++) {
				if (!user.getName().contains("KI-")) {
			sendGameDataToClients("CLOSE");
				}
			}
			closeGame();
			return;
		}

		if (gsonString.equals("RESTART")) {
			initializeGame();
			this.gState = GameState.RUNNING;
			for(int i=0;i<playerList.size();i++) {
				if (!user.getName().contains("KI-")) {
			sendGameDataToClients("standardEvent");
				}
			}
			return;
		}
	
		if (gsonString.contains("INITIALIZE")) {//sollte aufgerufen werden sobald alle menschlichen spieler gejoint sind
			String[] strArray=gsonString.split(",");
			
			 int humanPlayers=Integer.parseInt(strArray[1]);
			 int aiPlayers=Integer.parseInt(strArray[2]);
			 //TODO erst irgendwie menschliche Spieler in playerlist.. oder funktioniert das automatisch?
			 if (p==false) {
			 for(int i=1;i<=aiPlayers;i++) {
				 User AI= new User("KI-"+i,"0000");
				 playerList.add(AI);
				 System.out.println("1111");
			 	}
			 playerAmount=humanPlayers+aiPlayers;
			 }
			 p=true;
			initializeGame();
			this.gState = GameState.RUNNING;
			for(int i=0;i<playerList.size();i++) {
					if (!user.getName().contains("KI-")) {
						sendGameDataToClients("standardEvent");
					}
			}
			return;
		}

		if (gState != GameState.RUNNING) {
			return;
		}
		/* PRO-Version
		 * if (gsonString.contains("NOTES")) {
			String[] strArray=gsonString.split(",");
			for (int i=1;i<=7;i++) {
				notes[i-1]=strArray[i];
			}
		}*/
		if (!user.equals(playerTurn)) {
			return;
		}
		if(user.getName().contains("KI-")) {
			AITurn();
			for(int i=0;i<playerList.size();i++) {
				if (!user.getName().contains("KI-")) {
					sendGameDataToClients("standardEvent");
				}
			}
			return;
		}
		String[] strArray = gsonString.split(",");
		int[] receiveddataArray = new int[18];	//0-6:Positionen der Agenten, 7 Tresorposition, 8-14 punktzahlen der agenten,
		for (int i = 0; i < 15; i++) {			//15 playermessage(hier obsolet), 16 Host, 17 Zug vorbei(0) oder noch zuege übrig(1)
			receiveddataArray[i] = Integer.parseInt(strArray[i]);
		}
		boolean changed = false;
		int[] actualdataArray = getDataArray();

		if (!actualdataArray.equals(receiveddataArray)) {
			changed = true;
		}

		if (changed == true) {
			for (int i = 0; i < 7; i++) {
				agentList.get(i).setAgentPosition(receiveddataArray[i]);
				agentList.get(i).setMarkerPosition(receiveddataArray[i + 8]);
			}

			for (int i = 0; i < agentList.size(); i++) {
				if (agentList.get(i).getAgentPosition() == getSafePosition()) {
					scoring();
				}
			}
			if (gameOver()) {
				System.out.println("The game is over.");
				/*PRO-Version
				 * for(int i=1;i<=7;i++) {
					if (notes[i-1].equals(strArray[i])){
						agentList.get(i-1).setMarkerPosition(agentList.get(-1).getMarkerPosition()+5);
					}
				}*/
				this.gState = GameState.FINISHED;
			}
			// playerTurn um eins nach vorn verschieben
			if (receiveddataArray[17]==0) {//keine zuege übrig
				Iterator<User> it = playerList.iterator();

				if (it.hasNext()) {
					setPlayerTurn(it.next());
				} else {// wenn playerlist durchlaufen wieder bei 0/Spieler 1 beginnen
					it = playerList.iterator();
					setPlayerTurn(it.next());
				}
			}
			setDataArray(actualdataArray);

			for(int i=0;i<playerList.size();i++) {
				if (!user.getName().contains("KI-")) {
					sendGameDataToClients("standardEvent");
				}
			}
		}
	}

	@Override
	public String getGameData(String eventName, User user) {
		String gameData = "";
		
		if (eventName.equals("CREATE")) {// für chris' seite direkt nach Spielerstellung
			
			String isHost="";
			User host= getGameCreator();
			if (user.equals(host)) {
				isHost ="H";

				System.out.println("an host geschickt "+ user.getName());


			}
			else if(!user.equals(host)) {
				isHost ="C";

				System.out.println("an client geschickt "+user.getName());

			}
			return isHost;
		}
		if (eventName.equals("PLAYERLEFT")) {
			return playerLeft + " hat das Spiel verlassen!";
		}
		if (eventName.equals("CLOSE")) {
			return "CLOSE";
		}
		
		if (eventName.equals("START")) {
			HashMap<String,String> hash=assignColour();
			for (int i=0;i<7;i++) {
				if(dataArray[i]!=-1) {
					gameData+=1+",";
				}
				else gameData+=0+",";
			}
			gameData+=hash.get(user.getName());
			return gameData;
		}

		int[] actualDataArray = getDataArray();
		for (int i = 0; i < 15; i++) {
			gameData += String.valueOf(actualDataArray[i]);
			gameData += ",";
		}

		if (playerList.size() < 2) {
			gameData += "Warte auf 2ten Spieler oder KI-Spieler..."; // Arrayelem 15
			gameData += isHost(user); // Arrayelem16
			return gameData;
		}
		if (this.gState == GameState.FINISHED) {
			// Ausgabe, welcher Agent auf welchem Platz ist
			Collections.sort(agentList);
			for (Agent a : agentList)
				System.out.print( a.getColourString() + "  : " + a.getMarkerPosition() + ", ");
			for (int i = 0; i < agentList.size(); i++) {
				gameData += agentList.get(i) + " ist mit " + agentList.get(i).getMarkerPosition()
						+ " Punkten auf Platz " + (agentList.indexOf(agentList.get(i)) + 1) + "\n";// Arrayelem 15
			}

		} else if (playerTurn.equals(user)) {
			gameData += "Du bist dran!"; // arrayelem 15
		} else
			gameData += playerTurn.getName() + " ist dran!";// arrayelem 15

		gameData += isHost(user);// Arrayelem16

		return gameData;
	}
}