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
	private int humanPlayers=0;
	private int safePosition;
	private ArrayList<User> playerList = new ArrayList<User>();
	private ArrayList<User> spectatorList = new ArrayList<User>();
	private ArrayList<Agent> agentList = new ArrayList<Agent>();
	private String playerLeft = null;
	private User playerTurn = null;
	private int[] dataArray;
	private int playerAmount=0;

	private ArrayList<Integer> colourList = new ArrayList<Integer>();
	private int aufruf =0;
	private boolean alreadyScored=false;
	Iterator<User> it = playerList.iterator();
	private boolean aiIsDone=true;

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

		 
		
		

		
			if (playerAmount >= 5) {
				for (int i = 0; i < 7; i++) {
				dataArr[i] = 0;
				}
			} else if (playerAmount ==4) { // Spieleranzahl bestimmt die Arraysumme, da -1
																		 // oder 0
					agent = Agent.nextInt(7);
					for (int i = 0; i < 7; i++) {
					if(i!=agent)
					dataArr[i] = 0;
					}
				}
			else if (playerAmount ==3) {
					agent = Agent.nextInt(7);
					int agent2= Agent.nextInt(7);
					if (agent==agent2) {
						agent2=(agent2+1)%7;
					}
					for (int i = 0; i < 7; i++) {
						if(i!=agent&&i!=agent2) {
							dataArr[i] = 0;
					}
				}
			}
			else if (playerAmount ==2) {
				  ArrayList<Integer> list = new ArrayList<Integer>();
				 for (int i=0; i<7; i++) {
					    list.add(new Integer(i));
					}
					Collections.shuffle(list);
					for(int i=0;i<7;i++) {
						if(list.get(i)<=3) {
							dataArr[i]=0;
						}
					}
					
				
			}
		
			for (int i=0;i<dataArr.length;i++) {
				
			}
	
		setDataArray(dataArr);
		instantiateAgents(dataArr);
		playerTurn = getGameCreator();
	}
    
	public void AITurn() {
		aiIsDone=false;
		Random agent = new Random();
		int fieldsToGo = rollDice();
		int rolled = fieldsToGo;
		System.out.println("------------------------------------------------");
		System.out.println("rolled :" +rolled);
		
		
		Random fieldsgone = new Random();
		System.out.println("Ai- turn begonnen");
		// yellow=0,red=1,purple=2,blue=3,green=4,orange=5,grey=6
		while (fieldsToGo > 0) {
			int fieldsGone = fieldsgone.nextInt(6) + 1;  
			int agentToMove = agent.nextInt(7);			

			if (fieldsGone > fieldsToGo) {
				fieldsGone = Math.abs(fieldsGone - fieldsToGo);
				System.out.println(" betrag");
			}
		
			if (agentList.get(agentToMove) != null) {
				
					if (rolled >= fieldsGone) {
					switch (agentToMove) {
					case 0: {
						System.out.println("Case 0,yellow, bewegt sich um " + fieldsGone);
						agentList.get(0).setAgentPosition(agentList.get(0).getAgentPosition() + fieldsGone);
						dataArray[0]=agentList.get(0).getAgentPosition();
						for (User u : playerList) {
							if (!u.getName().contains("KI-")) {
								sendGameDataToUser(u, "standardEvent");
							}
						}
						fieldsToGo -= fieldsGone;
						try {
							TimeUnit.SECONDS.sleep(2);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						break;
					}
					case 1: {
						System.out.println("Case 1,red bewegt sich um " + fieldsGone);
						agentList.get(1).setAgentPosition(agentList.get(1).getAgentPosition() + fieldsGone);
						dataArray[1]=agentList.get(1).getAgentPosition();
						fieldsToGo -= fieldsGone;
						for (User u : playerList) {
							if (!u.getName().contains("KI-")) {
								sendGameDataToUser(u, "standardEvent");
							}
						}
						try {
							TimeUnit.SECONDS.sleep(2);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						break;
					}
					case 2: {
						System.out.println("Case 2,purple bewegt sich um " + fieldsGone);
						agentList.get(2).setAgentPosition(agentList.get(2).getAgentPosition() + fieldsGone);
						dataArray[2]=agentList.get(2).getAgentPosition();
						fieldsToGo -= fieldsGone;
						for (User u : playerList) {
							if (!u.getName().contains("KI-")) {
								sendGameDataToUser(u, "standardEvent");
							}
						}
						try {
							TimeUnit.SECONDS.sleep(2);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						break;
					}
					case 3: {
						System.out.println("Case 3, blue bewegt sich um " + fieldsGone);
						agentList.get(3).setAgentPosition(agentList.get(3).getAgentPosition() + fieldsGone);
						dataArray[3]=agentList.get(3).getAgentPosition();
						fieldsToGo -= fieldsGone;	
						for (User u : playerList) {
							if (!u.getName().contains("KI-")) {
								sendGameDataToUser(u, "standardEvent");
							}
						}
						try {
							TimeUnit.SECONDS.sleep(2);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						break;
					}
					case 4: {
						System.out.println("Case 4,green bewegt sich um " + fieldsGone);
						agentList.get(4).setAgentPosition(agentList.get(4).getAgentPosition() + fieldsGone);
						dataArray[4]=agentList.get(4).getAgentPosition();
						fieldsToGo -= fieldsGone;
						for (User u : playerList) {
							if (!u.getName().contains("KI-")) {
								sendGameDataToUser(u, "standardEvent");
							}
						}
						try {
							TimeUnit.SECONDS.sleep(2);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						break;
					}
					case 5: {
						System.out.println("Case 5,orange bewegt sich um " + fieldsGone);
						agentList.get(5).setAgentPosition(agentList.get(5).getAgentPosition() + fieldsGone);
						dataArray[5]=agentList.get(5).getAgentPosition();
						fieldsToGo -= fieldsGone;
						for (User u : playerList) {
							if (!u.getName().contains("KI-")) {
								sendGameDataToUser(u, "standardEvent");
							}
						}
						try {
							TimeUnit.SECONDS.sleep(2);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						break;
					}
					case 6: {
						System.out.println("Case 6,grey bewegt sich um " + fieldsGone);
						agentList.get(6).setAgentPosition(agentList.get(6).getAgentPosition() + fieldsGone);
						dataArray[6]=agentList.get(6).getAgentPosition();
						fieldsToGo -= fieldsGone;
						for (User u : playerList) {
							if (!u.getName().contains("KI-")) {
								sendGameDataToUser(u, "standardEvent");
							}
						}
						try {
							TimeUnit.SECONDS.sleep(2);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						break;
					}
					default: {
						System.out.println("default");
						throw new IllegalArgumentException("Error while AI-Turn");
					}
					}

				} else {
					System.out.println("else teil der whileschleife");
					System.out.println("Agent moving:"+ agentToMove + "bewegen um" + fieldsGone);
					agentList.get(agentToMove).setAgentPosition(agentList.get(agentToMove).getAgentPosition() + rolled);
					dataArray[agentToMove]=agentList.get(agentToMove).getAgentPosition();
					for (User u : playerList) {
						if (!u.getName().contains("KI-")) {
							sendGameDataToUser(u, "standardEvent");
						}
					}
					try {
						TimeUnit.SECONDS.sleep(2);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					break;
				}
			
			}
			else {
				System.out.println("neuaufruf der while-schleife");
				continue;
				
			}
		}
		System.out.println("while schleife durchlaufen");
			for (int i=0;i<dataArray.length;i++) {
				System.out.print(dataArray[i]);
				System.out.print(",");
			
			}
			System.out.println("\n");
			alreadyScored=false;
		for (int i = 0; i < agentList.size(); i++) {
			if (agentList.get(i)!= null)
				if (agentList.get(i).getAgentPosition() == dataArray[7]&& alreadyScored==false) {
					System.out.println("Agent auf tresor");
					scoring();
					alreadyScored=true;
			}
		}
//		System.out.println("setze array auf neue werte");
//		int[] dataarray = new int[15];
//		for (int i = 0; i < 7; i++) {
//			if (agentList.get(i)!=null) {
//				dataarray[i] = agentList.get(i).getAgentPosition();
//				//dataarray[i + 8] = agentList.get(i).getMarkerPosition();
//			}
//		}
		Random tresor = new Random();
		int safe = tresor.nextInt(12);

		ArrayList<Integer> excluded = new ArrayList<Integer>();
		for (int i = 0; i < agentList.size(); i++) {
			if (agentList.get(i)!= null) {
				excluded.add(agentList.get(i).getAgentPosition());
			}
		}
		setSafePosition(generateSafePosition(safe, excluded));
		System.out.println("setting dataarray end");
		setDataArray(dataArray);
		System.out.println("Array: ");
		for (int i=0;i<dataArray.length;i++) {
			System.out.print(dataArray[i]);
			System.out.print(",");
		
		}
		System.out.println("\n");
		System.out.println("playerturn hochsetzen");
//		incrementPlayerTurn();
		System.out.println("-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-");
		aiIsDone=true;
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
			else {
				newAgentList.add(null);
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
		System.out.println("scoring begins"); 
		for (int i = 0; i < agentList.size(); i++) {
			if (agentList.get(i) != null) {
				switch (agentList.get(i).getAgentPosition()) {
				case 0: {
					break;
				}
				case 1: {
					agentList.get(i).setMarkerPosition(agentList.get(i).getMarkerPosition() + 1);
					dataArray[i+8]=agentList.get(i).getMarkerPosition();
					break;
				}
				case 2: {
					agentList.get(i).setMarkerPosition(agentList.get(i).getMarkerPosition() + 2);
					dataArray[i+8]=agentList.get(i).getMarkerPosition();
					break;
				}
				case 3: {
					agentList.get(i).setMarkerPosition(agentList.get(i).getMarkerPosition() + 3);
					dataArray[i+8]=agentList.get(i).getMarkerPosition();
					break;
				}
				case 4: {
					agentList.get(i).setMarkerPosition(agentList.get(i).getMarkerPosition() + 4);
					dataArray[i+8]=agentList.get(i).getMarkerPosition();
					break;
				}
				case 5: {
					agentList.get(i).setMarkerPosition(agentList.get(i).getMarkerPosition() + 5);
					dataArray[i+8]=agentList.get(i).getMarkerPosition();
					break;
				}
				case 6: {
					agentList.get(i).setMarkerPosition(agentList.get(i).getMarkerPosition() + 6);
					dataArray[i+8]=agentList.get(i).getMarkerPosition();
					break;
				}
				case 7: {
					agentList.get(i).setMarkerPosition(agentList.get(i).getMarkerPosition() + 7);
					dataArray[i+8]=agentList.get(i).getMarkerPosition();
					break;
				}
				case 8: {
					agentList.get(i).setMarkerPosition(agentList.get(i).getMarkerPosition() + 8);
					dataArray[i+8]=agentList.get(i).getMarkerPosition();
					break;
				}
				case 9: {
					agentList.get(i).setMarkerPosition(agentList.get(i).getMarkerPosition() + 9);
					dataArray[i+8]=agentList.get(i).getMarkerPosition();
					break;
				}
				case 10: {
					agentList.get(i).setMarkerPosition(agentList.get(i).getMarkerPosition() + 10);
					dataArray[i+8]=agentList.get(i).getMarkerPosition();
					break;
				}
				case 11: {
					if (agentList.get(i).getMarkerPosition() >= 3) {
						agentList.get(i).setMarkerPosition(agentList.get(i).getMarkerPosition() - 3);
						dataArray[i+8]=agentList.get(i).getMarkerPosition();
					}
					else {agentList.get(i).setMarkerPosition(0);
					dataArray[i+8]=agentList.get(i).getMarkerPosition();
					}
					break;
				}
				default: {
					throw new IllegalArgumentException("Kein gültiges Feld");
				}
				}
			}
		}
		System.out.println("scoring ends:");
		for (int i =0;i<dataArray.length;i++) {
			System.out.print(dataArray[i]);
			System.out.print(",");
		
		}
		System.out.println("\n");
	}

	public boolean gameOver() {
		boolean gameOver = false;
		for (int i = 0; i < agentList.size(); i++) {
			if (agentList.get(i) != null) {
				if (agentList.get(i).getMarkerPosition() >= 42) {
					gameOver = true;
				}
			}
		}
		return gameOver;
	}

	public void shuffleArray(int[] array)
	{
	    int index;
	    Random random = new Random();
	    for (int i = array.length - 1; i > 0; i--)
	    {
	        index = random.nextInt(i + 1);
	        if (index != i)
	        {
	            array[index] ^= array[i];
	            array[i] ^= array[index];
	            array[index] ^= array[i];
	        }
	    }
	}
	
	public HashMap<String,String> assignColour() {
		
		
		HashMap<String, String> hash = new HashMap<String, String>();
	
		for (int i = 0; i < playerList.size(); i++) {

			String name = playerList.get(i).getName();
			
			int[] colourarr= {0,1,2,3,4,5,6};
			shuffleArray(colourarr);
			
			int colourInt = colourarr[i];
			
			String colour;
			switch (colourInt) {
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
			if (agentList.get(i) != null) {
				if (agentList.get(i).getAgentPosition() == this.safePosition) {
					throw new IllegalArgumentException("House is already occupied. Choose another one.");
				} else if (safePosition < 0 || safePosition >= 12) {
					throw new IllegalArgumentException("Invalid Position");
				} else
					this.safePosition = safePosition;
				dataArray[7] = safePosition;
			}
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
		if (playerList.size() <= playerAmount && !playerList.contains(user)) {
			playerList.add(user);
				
			System.out.println("created" + user.getName());
			
		}
		/*if (playerAmount>7){//Nötig..bzw funktionierts?
			addSpectator(user);
		}*/
		
		if (playerList.size() == playerAmount && aufruf == playerAmount ) {
			
			
	
			
			this.gState = GameState.RUNNING;
			for(User u: playerList) {
				if (!u.getName().contains("KI-")) {
					sendGameDataToUser(u,"START1");
				
					sendGameDataToUser(u,"standardEvent");
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
		for(User u: playerList) {
			if (!u.getName().contains("KI-")) {
				sendGameDataToUser(u,"PLAYERLEFT");
			
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

	public void incrementPlayerTurn(){

		System.out.println("playerTurn zuvor: " + playerTurn.getName());
		int x = playerList.indexOf(playerTurn);

		System.out.println("index of playerturn " + playerList.indexOf(playerTurn));
		System.out.println("playerList size " + playerList.size());
		x++;

		if (playerList.size() > (x)) {

			playerTurn = playerList.get(x);
			System.out.println("increment, Turn: " + playerTurn.getName());

		} else {

			playerTurn = playerList.get(0);
			System.out.println("neudurchlauf der playerlist, Turn: " + playerTurn.getName());
		}
	}
	
	@Override
	public void execute(User user, String gsonString) {
		System.out.println("gsonString received: "+gsonString);
		if (gsonString.equals("HI")) {
			sendGameDataToUser(user, "CREATE");

			aufruf++;

			return;
		}

		if (this.gState == GameState.CLOSED)
			return;
		if (!aiIsDone) {
			return;
		}
		if (gsonString.equals("CLOSE")) {
			for (User u : playerList) {
				if (!u.getName().contains("KI-")) {
					sendGameDataToUser(u, "CLOSE");

				}
			}
			closeGame();
			return;
		}
		if (gsonString.equals("OK")) {
			System.out.println("Anzahl menschl. Spieler: " + humanPlayers);
			System.out.println("aufruf= " + aufruf);
			if (humanPlayers == aufruf) {
				this.gState = GameState.RUNNING;
				for (User u : playerList) {
					if (!u.getName().contains("KI-")) {
						sendGameDataToUser(u, "START1");

						sendGameDataToUser(u, "standardEvent");
					}
				}
			}
			return;
		}

		if (gsonString.equals("RESTART")) {
			initializeGame();
			this.gState = GameState.RUNNING;
			
				for (User u : playerList) {
					if (!user.getName().contains("KI-")) {
						sendGameDataToUser(u, "standardEvent");
					}
				}
				return;
			
		}
		if (gsonString.contains("HUMANPLAYERCOUNT")) {
			String[] strArray = gsonString.split(",");

			humanPlayers = Integer.parseInt(strArray[1]);

		}

		if (gsonString.contains("INITIALIZE")) {// sollte aufgerufen werden sobald alle menschlichen spieler gejoint
												// sind

			for (int i = 0; i < 7; i++) {
				colourList.add(i);
			}
			Collections.shuffle(colourList);

			String[] strArray = gsonString.split(",");

			humanPlayers = Integer.parseInt(strArray[1]);
			int aiPlayers = Integer.parseInt(strArray[2]);
			// TODO erst irgendwie menschliche Spieler in playerlist.. oder funktioniert das
			// automatisch?

			for (int i = 1; i <= aiPlayers; i++) {
				User AI = new User("KI-" + i, "0000");
				playerList.add(AI);

			}
			playerAmount = humanPlayers + aiPlayers;

			initializeGame();

			// for(int i=0;i<playerList.size();i++) {
			// if (!user.getName().contains("KI-")) {
			//
			// sendGameDataToClients("START");
			// System.out.println("Start gesendet");
			// }
			// }
			return;
		}
	
		if (gState != GameState.RUNNING) {
			return;
		}
		/*
		 * PRO-Version if (gsonString.contains("NOTES")) { String[]
		 * strArray=gsonString.split(","); for (int i=1;i<=7;i++) {
		 * notes[i-1]=strArray[i]; } }
		 */
		
		if (!user.equals(playerTurn)) {

			return;
		}


		String[] strArray = gsonString.split(",");
		int[] receiveddataArray = new int[16]; // 0-6:Positionen der Agenten, 7 Tresorposition, 8-14 punktzahlen der
												// agenten,
		for (int i = 0; i < 15; i++) { // 15 playermessage(hier obsolet), 16 Host, 17 Zug vorbei(0) oder noch zuege
										// übrig(1)
			receiveddataArray[i] = Integer.parseInt(strArray[i]);
		}
		// receiveddataArray[15]=Integer.parseInt(strArray[17]);
		boolean changed = false;
		int[] actualdataArray = getDataArray();

		for (int i = 0; i < 15; i++) {
			if (actualdataArray[i] != receiveddataArray[i]) {
				changed = true;
			}
		}
		// System.out.println(changed);

		if (changed == true) {

			for (int i = 0; i < 7; i++) {
				if (agentList.get(i) != null) {
					agentList.get(i).setAgentPosition(receiveddataArray[i]);
					agentList.get(i).setMarkerPosition(receiveddataArray[i + 8]);
				}
			}
			alreadyScored=false;
			for (int i = 8; i < 15; i++) {
				if (receiveddataArray[i] == dataArray[7]) {
					// System.out.println("Größe agentList: " + agentList.size());
					if (!alreadyScored) {
						scoring();
						alreadyScored = true;
					}
				}
			}
			setDataArray(receiveddataArray);
			if (gameOver()) {
				System.out.println("The game is over.");
				/*
				 * PRO-Version for(int i=1;i<=7;i++) { if (notes[i-1].equals(strArray[i])){
				 * agentList.get(i-1).setMarkerPosition(agentList.get(i-1).getMarkerPosition()+5)
				 * ; } }
				 */
				this.gState = GameState.FINISHED;
			}

			// playerTurn um eins nach vorn verschieben
			if (strArray[17].equals("0")) {// keine zuege übrig
				incrementPlayerTurn();
			
			}
			
			if (playerTurn.getName().contains("KI-")){
				try {
					TimeUnit.SECONDS.sleep(2);
				} catch (InterruptedException e) {
					
					e.printStackTrace();
				}
				System.out.println("KI zieht");
					AITurn();
					System.out.println("KI-Zug fertig");
					incrementPlayerTurn();
//				for (User u : playerList) {
//					if (!u.getName().contains("KI-")) {
//						sendGameDataToUser(u, "standardEvent");
//					}
//				}
			}
			
	
			for (User u : playerList) {
				if (!u.getName().contains("KI-")) {
					sendGameDataToUser(u, "standardEvent");
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
			}
			else if(!user.equals(host)) {
				isHost ="C";
			}
			return isHost;
		}
		if (eventName.equals("PLAYERLEFT")) {
			return playerLeft + " hat das Spiel verlassen!";
		}
		if (eventName.equals("CLOSE")) {
			return "CLOSE";
		}
		
		if (eventName.equals("START1")) {
			
			
			HashMap<String,String> hash=assignColour();
			
			//System.out.println("3");
			
//			String [] test = {"yellow","red","purple","blue","green","orange","gray"};
//			
//			String[] test2 = new String[8];
//			
//			
//			for(int i =0; i<test2.length-1; i++) {
//				
//				
//						
//					int h	= (int)((Math.random()) * 2);
//					test2[i] = Integer.toString(h);
//				
//			}
//			
			
			
			
			
			for (int i=0;i<7;i++) {
				if(dataArray[i]!=-1) {
					gameData+=1+",";
				}
				else gameData+=0+",";
			}
			
			//System.out.println("4");
			gameData+=hash.get(user.getName());
			
			
			
			//gameData ="1,0,0,1,0,1,0,green";
			
			
			
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