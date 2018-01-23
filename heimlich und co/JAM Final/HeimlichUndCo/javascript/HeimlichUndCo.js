var parent1;
var array = [];
var Spieleranzahl = 6;

var zuege;

var prev;

var Spieler1 = 0;
var Spieler2 = 0;
var Spieler3 = 0;
var Spieler4 = 0;
var Spieler5 = 0;
var Spieler6 = 0;

var spieler = [ 0, 0, 0, 0, 0, 0, 0 ];

var Farben = [ "Blau", "Gelb", "Gruen", "Lila", "Rot", "Pink", "Brown" ];
var Spielerpos = [ 0, 0, 0, 0, 0, 0, 0, 0 ];
var Punkte = [ 0, 0, 0, 0, 0, 0, 0 ]
var arrFields = [ 0, 0, 0, 0, 0, 0, 0, 7, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 ];

addListener('standardEvent', function(event) {
	var stringFromServer = event.data;
	var arr = stringFromServer.split(',');
	//console.log(arr);

	if (arr.length == 17) {
		for (var i = 0; i < 15; i++) {
			arrFields[i] = +arr[i];
		}
		playerMessage = arr[15];
		var str = arr[16];
		if (str == "HOST") {
			console.log(arr[16]);
			setVisible();

		}

		sentFields = [ 0, 0, 0, 0, 0, 0, 0, 0, 0 ];
		document.getElementById("status").innerHTML = playerMessage;
		updateBoard();
	}
	statusWait = false;
});
addListener('START', function(event) {
	var stringFromServer = event.data;
	var arr = stringFromServer.split(',');
	playerMessage = arr[15];
	document.getElementById("status").innerHTML = playerMessage;
	if (arr[16] == "HOST")
		setVisible();
	statusWait = false;
});
addListener('PLAYERLEFT', function(event) {
	var stringFromServer = event.data;
	playerMessage = stringFromServer;
	document.getElementById("status").innerHTML = playerMessage;
});
addListener(
		'CLOSE',
		function(event) {
			document.getElementById("status").innerHTML = "Spiel wurde vom Host beendet!";
		});

// Drag and Drop for tresor

function allowDropt(ev) {

	ev.preventDefault();

}

function dragt(ev) {

	ev.dataTransfer.setData("text", ev.target.id);
	document.getElementById("tresor").setAttribute("draggable", "false");

}

function dropt(ev) {

	ev.preventDefault();

}

//Drag and Drop for Gamefigures

function allowDrop(ev) {

	if (zuege > 0) {

		ev.preventDefault();

	}

}

function drag(ev) {
	ev.dataTransfer.setData("text", ev.target.id);

	prev = ev.target.parentElement.getAttribute("number");

}

function drop(ev) {

	console.log(ev.target.getAttribute("number") - prev + " wert: "
			+ ev.target.getAttribute("number") + "minus" + prev + "Zuege "
			+ zuege);

	if ((ev.target.getAttribute("number") - prev) <= zuege
			&& prev < ev.target.getAttribute("number")) {

		ev.preventDefault();
		var data = ev.dataTransfer.getData("text");
		ev.target.appendChild(document.getElementById(data));

		zuege = zuege - (ev.target.getAttribute("number") - prev);
		sendGameData()
	}

	if (zuege == 0) {

		console.log("du bist fertig!");

		arrFields[18] = 0;

		sendGameData();

	}

	console.log(ev.dataTransfer.getData("text"));

	if (ev.dataTransfer.getData("text") != "tresor") {

		if (ev.target.contains(tresor)) {

			Punkte[0] += parseInt(document.getElementById("drop1").parentElement
					.getAttribute("number"));
			Punkte[1] += parseInt(document.getElementById("drop2").parentElement
					.getAttribute("number"));
			Punkte[2] += parseInt(document.getElementById("drop3").parentElement
					.getAttribute("number"));
			Punkte[3] += parseInt(document.getElementById("drop4").parentElement
					.getAttribute("number"));
			Punkte[4] += parseInt(document.getElementById("drop5").parentElement
					.getAttribute("number"));
			Punkte[5] += parseInt(document.getElementById("drop6").parentElement
					.getAttribute("number"));
			Punkte[6] += parseInt(document.getElementById("drop7").parentElement
					.getAttribute("number"));

			fillpoints();

			document.getElementById("tresor").setAttribute("draggable", "true");

			sendGameData()
			console.log("addiert");

		}

	}

	die1.innerHTML = "Zuege: " + zuege;

}

// Cube

function rollDice() {

	var die1 = document.getElementById("die1");

	var status = document.getElementById("status");
	var d1 = Math.floor(Math.random() * 6) + 1;
	document.getElementById("rollimg").src = "wurfel_" + d1 + ".png"
	var diceTotal = d1;
	zuege = d1;
	die1.innerHTML = zuege;

	// getparent(el);
	updateBoard();

	document.getElementById("b4").style.backgroundColor = "green";

}

/*
function getparent(el){






    for(var i = 0; i<array.length ; i++){ 



    }

    var myButton = document.createElement("button");
   document.getElementById("Spielsystem").appendChild(myButton);
    myButton.setAttribute('id', 'neuerButton');


}


 */

// initialise Board

function initBoard(Spieleranzahl) {

}

// Points setting for Colors

function fillpoints() {

	for (var i = 1; i <= Spieleranzahl; i++) {

		document.getElementById("p" + i).innerHTML = Farben[i - 1] + " :   "
				+ Punkte[i - 1]

	}

	if (Spieleranzahl < 7) {

		for (var i = Spieleranzahl + 1; i <= 7; i++) {

			document.getElementById("p" + i).style.visibility("hidden");

		}

	}

	/*
	 document.getElementById("p1").innerHTML = "Green = " + Punkte[0];
	 document.getElementById("p2").innerHTML = "Purple = " + Punkte[1];
	 document.getElementById("p3").innerHTML = "Yellow = " + Punkte[2];
	 document.getElementById("p4").innerHTML = "Red = " + Punkte[3];
	 document.getElementById("p5").innerHTML = "Blue = " + Punkte[4];
	 document.getElementById("p6").innerHTML = "Yellow = " + Punkte[5];

	 */

}

// getting 

function updateBoard() {

	var t = 1;

	for (var i = 0; i < 6; i++) {

		var Drop = document.getElementById("drop" + t);

		if (arrFields[i] >= 0) {

			document.getElementById("Feld" + arrFields[i]).appendChild(Drop);

			t++;

		}

	}

	t = 1;

	for (var i = 6; i < 6 + Spieleranzahl; i++) {

		Punkte[i - 6] = arrFields[i]

	}

	fillpoints();

}
function startscreen() {
	var anzahlPlayer = parseInt(document.getElementById("countPlayer").value);
	var anzahlKi = parseInt(document.getElementById("countKi").value);

	var overall = anzahlPlayer + anzahlKi;
	if (overall <= 7) {
		document.getElementById("Startscreen").style.display = "none";
		document.getElementById("Game").style.display = "contents";
	} else {
		document.getElementById("errorNoP").style.visibility = "visible";
	}
}

function sendGameData() {

	arrFields[0] = Spielerpos[0] = parseInt(document.getElementById("drop1").parentElement
			.getAttribute("number"));
	arrFields[1] = Spielerpos[1] = parseInt(document.getElementById("drop2").parentElement
			.getAttribute("number"));
	arrFields[2] = Spielerpos[2] = parseInt(document.getElementById("drop3").parentElement
			.getAttribute("number"));
	arrFields[3] = Spielerpos[3] = parseInt(document.getElementById("drop4").parentElement
			.getAttribute("number"));
	arrFields[4] = Spielerpos[4] = parseInt(document.getElementById("drop5").parentElement
			.getAttribute("number"));
	arrFields[5] = Spielerpos[5] = parseInt(document.getElementById("drop6").parentElement
			.getAttribute("number"));
	arrFields[6] = Spielerpos[6] = parseInt(document.getElementById("drop7").parentElement
			.getAttribute("number"));
	arrFields[7] = Spielerpos[7] = parseInt(document.getElementById("tresor").parentElement
			.getAttribute("number"));

	sendDataToServer(arrayFields);
        
        

        }