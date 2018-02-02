var parent1;
var array = [];
var Spieleranzahl =0;
var arr;
var zuege ;
var prev;
var Agentenanzahl = 0;
var spieler= [0,0,0,0,0,0,0];
var Farben = ["yellow", "red", "purple", "blue", "green", "orange", "gray"];
var Spielerpos = [0,0,0,0,0,0,0,0];
var Punkte = [0,0,0,0,0,0,0]
var arrFields = [0,0,0,0,0,0,0,7,0,0,0,0,0,0,0,0,0];
var version =0;
var FarbenGerman = ["Gelb", "Rot", "Lila", "Blau", "Gruen", "Orange", "Grau"];
var tresPruef=0;

window.onload = function () {
	sendDataToServer("HI");
    //	console.log("geladen");
}
    	
addListener('FINISHED', function(event){
    	var stringFromServer = event.data;
	var tmp = stringFromServer.split("###");
    var tmp2 = tmp[0].split(',')
	var i = 0;
    var text = "Das Spiel ist vorbei! <br> <br>";
    
    while (tmp2[i]) 
    {
		text += tmp2[i] + "<br>";
   		i++;
	}
	
	document.getElementById("Game").style.opacity = "0.3";
    document.getElementById("Spielsystem").style.opacity = "0.3";
    document.getElementById("GameoverScreen").style.visibility= "visible";
	document.getElementById("Game").style.pointerEvents = "none";
	document.getElementById("goScoreboard").innerHTML = text;
    document.getElementById("status").innerHTML = "";
});
    	
addListener('START1', function(event){
	var stringFromServer = event.data;
    arr = stringFromServer.split(',');
    version=arr[10];
	console.log(arr[10]);
	arraySpieler = arr[8].split(';');
	
	
    //console.log(arraySpieler);
    Spieleranzahl = arr[9];
    initNotizen(Spieleranzahl);
    startGame();
    starting(arr);
    fillpoints();
    
	

	document.getElementById("status").innerHTML = "Alle Spieler sind nun im Spiel, warte auf Spielstart!";      
});
   
function initNotizen(value){
	console.log("Spielerzahl " + value);
	erstDrop(value);
}

addListener('standardEvent', function(event){
	var stringFromServer = event.data;
	var arrx = stringFromServer.split(',');
    console.log(arr);
	
	if(arrx.length==17)
	{
		for(var i=0; i<15; i++)
		{
			arrFields[i] = +arrx[i]; 
		}
        playerMessage = arrx[15];
        var str = arrx[16];
        
		console.log("standardevent erhalten:   " + arrFields);
		if(playerMessage.includes("Du "))
		{
			document.getElementById("wuerfel").style.visibility= "visible";
		}
		if(zuege > 0)
		{
			document.getElementById("wuerfel").style.visibility= "hidden";
		}
		
		document.getElementById("status").innerHTML = playerMessage;
		updateBoard();
	}
});

addListener('PLAYERLEFT', function(event){
	var stringFromServer = event.data;
    playerMessage = stringFromServer;
    document.getElementById("status").innerHTML = playerMessage;
});

addListener('CLOSE', function(event){
        document.getElementById("status").innerHTML = "Spiel wurde vom Host beendet!";
});

addListener('CREATE', function(event){ 
    if (event.data == "H")
    {
    		document.getElementById("Startscreen").style.visibility = "visible";
        document.getElementById("Game").style.visibility = "hidden";
        document.getElementById("Lobby").style.visibility = "hidden";
        console.log("H bekommen");
	} else if(event.data == "C"){
		console.log("C bekommen");
        document.getElementById("Game").style.visibility = "hidden";
        document.getElementById("Startscreen").style.visibility = "hidden"; 
        document.getElementById("Lobby").style.visibility = "visible";   
	}
    sendDataToServer("OK");
});


// Drag and Drop for tresor
//function allowDropt(ev) {
//	ev.preventDefault();
//}
function allowDropt(ev) {
	if((zuege > 0 && (ev.dataTransfer.getData("text") == "tresor")) && tresPruef==1 )
	{
		ev.preventDefault();
	}
	
	
}
function dragt(ev) {
	
	if(zuege > 0 && (ev.dataTransfer.getData("text") == "tresor") ){
	ev.dataTransfer.setData("text", ev.target.id);
    document.getElementById("tresor").setAttribute("draggable", "true");
	}
}

function dropt(ev) {
	if(zuege > 0 && (ev.dataTransfer.getData("text") == "tresor") ){
	ev.preventDefault();
	}
}

//Drag and Drop for Gamefigures
function allowDrop(ev) {
	if((zuege > 0 && (ev.dataTransfer.getData("text") != "tresor")) || ((ev.dataTransfer.getData("text") == "tresor") && tresPruef==1 ))
	{
		ev.preventDefault();
	}
	
	
	
	
	
}

function drag(ev) {
	
	if(zuege > 0 && (ev.dataTransfer.getData("text") != "tresor") ){
	ev.dataTransfer.setData("text", ev.target.id);
	prev = parseInt(ev.target.parentElement.getAttribute("position"));
	}
}

function drop(ev) {
	if(ev.dataTransfer.getData("text") != "tresor")
	{
		if(ev.target.contains(tresor) )
		{
			
			//tresPruef=1;
			arrFields[8] = parseInt(arrFields[8]) + parseInt(document.getElementById("drop1").parentElement.getAttribute("number"));
            arrFields[9] = parseInt(arrFields[9]) + parseInt(document.getElementById("drop2").parentElement.getAttribute("number"));
        	arrFields[10] = parseInt(arrFields[10]) + parseInt(document.getElementById("drop3").parentElement.getAttribute("number"));
    	    arrFields[11] = parseInt(arrFields[11]) + parseInt(document.getElementById("drop4").parentElement.getAttribute("number"));
    	    arrFields[12] = parseInt(arrFields[12]) + parseInt(document.getElementById("drop5").parentElement.getAttribute("number"));
    	    arrFields[13] = parseInt(arrFields[13]) + parseInt(document.getElementById("drop6").parentElement.getAttribute("number"));
            arrFields[14] = parseInt(arrFields[14]) + parseInt(document.getElementById("drop7").parentElement.getAttribute("number"));
            
            tresPruef=1;
            
            for(var i= 8; i<15;i++){
        		if(arrFields[i]<0){
        			
        			arrFields[i]=1;
        			
        		}
        		
        	}
			fillpoints();
			document.getElementById("tresor").setAttribute("draggable", "true");
			sendGameData();
			console.log(arrFields);
		}

	}
	
	if((ev.dataTransfer.getData("text") == "tresor") && tresPruef==1){
		
		console.log("tresorios");
		ev.preventDefault();
    	var data = ev.dataTransfer.getData("text");
    	ev.target.appendChild(document.getElementById(data));
		tresPruef=0;
		sendGameData();
		
		
	}
	
	
	
	
	console.log(ev.target.getAttribute("number") + "minus" + prev + " = " + (ev.target.getAttribute("number") - prev)+ "<=" + zuege +"&&"+ prev +"<"+ ev.target.getAttribute("position") );
    console.log("drop aufgerufen" );
	var target = parseInt(ev.target.getAttribute("position"));
	var schritte =0;
	if (prev < target)
	{
    		schritte = target - prev;
    	}else if (prev > target)
    	{
		for (var i = prev; i <= 11; i++)
		{
    			schritte++;
    		}    		
    		for (var i = 0; i < target; i++)
    		{
    			schritte++;
    		}
    	}
    	
    	if(schritte<=zuege && (ev.dataTransfer.getData("text") != "tresor")  )
    	{
		console.log("reingesprungen");
        	ev.preventDefault();
        	var data = ev.dataTransfer.getData("text");
        	ev.target.appendChild(document.getElementById(data));
		zuege = zuege - schritte;
 		if(playerMessage.includes("Du "))
 		{
 			
			sendGameData();
		}
	} 
	
	if(zuege == 0 )
	{
		console.log("du bist fertig!");
        document.getElementById("wuerfel").style.visibility = "hidden";
        sendGameData();
	}
	console.log(ev.dataTransfer.getData("text"));
	die1.innerHTML = "Zuege: " + zuege;
} 

//initialisieren der Spielerfiguren und der individuellen Spielerkarte
function starting(arr){
	for(var i =0; i<= 6 ; i++ )
	{
		console.log(arr[i]);
		if(arr[i] == 1)
		{
			document.getElementById("drop"+(i+1) ).style.visibility= "visible";
		}
	}
	var color = arr[8];
    document.getElementById("roll").src = "/HeimlichUndCo/images/" + arr[7] + ".png";
   
 }

// Cube
function rollDice(){
	var die1 = document.getElementById("die1");
	var status = document.getElementById("status");
    var d1 = Math.floor(Math.random() * 6) + 1;
    document.getElementById("rollimg").src = "/HeimlichUndCo/images/wurfel_"+d1+".png"
    	var diceTotal = d1;
	zuege = d1;
	die1.innerHTML = zuege;
	document.getElementById("wuerfel").style.visibility = "hidden";
}

// initialise Board
function initBoard (Spieleranzahl){

}

// Points setting for Colors
function fillpoints(){
	
	for(var i = 0; i<=6; i++ )
	{
		if (arr[i] == 1)
		{
			
			console.log("p"+ (1+i));
			document.getElementById("p"+ (1+i)).style.visibility = "visible";
            document.getElementById("p"+(i+1)).innerHTML = FarbenGerman[i] + " = " + arrFields[8+(i)];
		}
	}
}

// getting 
function updateBoard(){
	var t =1;
	for(var i = 0; i<=6; i++)
	{
		if(arrFields[i]>=0)
		{
			var Drop =document.getElementById("drop"+(i+1));
           	console.log("Feld  "+arrFields[i] +"  drop  " +(i+1));
			if(arrFields[i]==0)
			{
				document.getElementById("Feld12").appendChild(Drop);
			} else 
			{
				document.getElementById("Feld"+arrFields[i]).appendChild(Drop);
			}
			t++;
		}   
	}
	var tres =document.getElementById("tresor");
   	document.getElementById("Feld"+arrFields[7]).appendChild(tres);
	fillpoints();
}

function sendGameData(){
	for(var i=0; i<=6; i++)
	{
		if(document.getElementById("drop"+(i+1)).style.visibility === "visible")
		{
			arrFields[i] =  parseInt(document.getElementById("drop"+(i+1)).parentElement.getAttribute("position"));
		}
	}
	arrFields[7] =  parseInt(document.getElementById("tresor").parentElement.getAttribute("position"));
	
	for(var i= 8; i<15;i++){
		if(arrFields[i]<0){
			
			arrFields[i]=0;
			
		}
		
		
	}
	
	if(zuege==0 )
	{
		sendDataToServer(arrFields+","+zuege);
		console.log("gesendet : "+arrFields+","+zuege);
	}else 
	{
	sendDataToServer(arrFields+","+1)
	console.log("gesendet : "+arrFields+","+1);	
	}
}

//gets Player- and KI-amount and sends it to server
function startscreen(){
	var anzahlPlayer = parseInt(document.getElementById("countPlayer").value);
	var anzahlKi = parseInt(document.getElementById("countKi").value);
	var overall = anzahlPlayer + anzahlKi;
	if(document.getElementById("cbPro").checked)
	{
		version = 1;
	}
	
	if(overall > 1 && overall <= 7)
	{
		document.getElementById("Startscreen").style.display = "none";
		document.getElementById("Game").style.visibility = "hidden";
		document.getElementById("Lobby").style.visibility = "visible";        
        var arri=[anzahlPlayer, anzahlKi, version];       
        sendDataToServer("HUMANPLAYERCOUNT," + anzahlPlayer)
        initialize(arri);
	}
	else
	{
		document.getElementById("errorNoP").style.visibility = "visible";	
	}
}

function initialize(arri){
	sendDataToServer("INITIALIZE,"+arri);
    	sendDataToServer("OK");
}
    
function startGame(){
	//soll ausgefuehrt werden, sobald die vorgegebene Spieleranzal erreicht wurde
	document.getElementById("Startscreen").style.visibility = "hidden";
	document.getElementById("Game").style.visibility = "visible";
	document.getElementById("Lobby").style.visibility = "hidden";
	document.getElementById("Spielsystem").style.visibility = "visible";
}
    
var arraySpieler = [];
var arrayFarben = ["Null", "", "", "", "", "", "", ""];
var arrayTempSpieler = ["Null"];
var arrayTempFarben = ["Null"];
var arrayNotiz = [];

erstDrop = function(value) {
	if(version ==1)
	{
    		console.log("version: " + version);
    		document.getElementById("speichernB").style.visibility = "visible";
    	}
	var gh = 1;
    	for(var i =0; i<=6;i++)
    	{
		if(arrFields[i]>-1)
		{
			arrayFarben[gh]=FarbenGerman[i];
			gh++;
    		}
	}
    	
    	console.log(arrayFarben);
	var eingabe = value;
	var selectWert = 1;
	if (eingabe >= 8 || eingabe == 0)  
	{
		alert("Fehlerhafte Spielerzahl");
	}
	else 
	{
		//uebertragung aller Farben da 7 Spieler
		if (eingabe == 7) 
		{
			for (var zlA = 1; zlA<= value; zlA++) 
			{
				arrayTempSpieler.push(arraySpieler[zlA]);
				arrayTempFarben.push(arrayFarben[zlA]);
			}
		}
		else 
		{
			if (eingabe == 6) 
			{
				//uebertragen von 6 + 1 Farben
				for (var zlB = 1; zlB<= value; zlB++) 
				{
					arrayTempSpieler.push(arraySpieler[zlB]);
					arrayTempFarben.push(arrayFarben[zlB]);
				}
				arrayTempFarben.push(arrayFarben[zlB]);
			}
			else 
			{
				//uebertragung der Farben + 2 da 5 oder weniger Spieler
				for (var zlC = 1; zlC<= value; zlC++) 
				{
					arrayTempSpieler.push(arraySpieler[zlC]);
					arrayTempFarben.push(arrayFarben[zlC]);
				}
				arrayTempFarben.push(arrayFarben[zlC]);
				zlC++;
				arrayTempFarben.push(arrayFarben[zlC]);
			}
		}
		for (var zlD = 1; zlD<= value; zlD++) 
		{
			//Erstellen Combobox Spieler
			var select1 = document.createElement("select");
            select1.setAttribute('name', 'combo1');
            select1.setAttribute('class', 'combospieler');
            document.getElementById("NotizCombo").appendChild(select1);
		
			//Erstellen Combobox Farben
			var select2 = document.createElement("select");
            select2.setAttribute('name', 'combo2');
            select2.setAttribute('class', 'combofarbe');
            document.getElementById("NotizCombo").appendChild(select2);
			
			//Einfuegen der Optionen in die Comboboxen
			anzFarben = arrayFarben.length;

			//Hinzufuegen der Spieler
			for (var zlE = 1; zlE<= value; zlE++) 
			{
				select1.options[zlE] = new Option (arrayTempSpieler[zlE]);
				select1.setAttribute('id', 's1');
            }
			//Hinzufuegen der Farben
			for (var zlF = 1; zlF<= anzFarben-1; zlF++) 
			{
				select2.options[zlF] = new Option (arrayFarben[zlF]);
                select2.options.selectedIndex = 1;
			}

			//Auswahl des Spielers
            select1.options.selectedIndex = selectWert;
            selectWert++;
            document.getElementById("NotizCombo").appendChild(document.createElement("br"));
		}
	}
}

function saveNotizen() {
	spielerInfo = document.getElementsByClassName("combospieler");
	farbInfo = document.getElementsByClassName("combofarbe");
	var x = spielerInfo.length;

	for (var i = 0; i < x; i++)
	{
		arrayNotiz[i] = [spielerInfo[i].value + ";" + farbInfo[i].value];
	}
	document.getElementById("Notizblock").disabled = true;
	console.log("NOTIZ"+","+arrayNotiz);
	sendDataToServer("NOTIZ"+","+arrayNotiz);
}
