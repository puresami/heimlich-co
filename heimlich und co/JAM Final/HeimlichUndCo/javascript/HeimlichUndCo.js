addListener('standardEvent', function(event) {
		var stringFromServer = event.data;
		var arr = stringFromServer.split(',');
		//console.log(arr);
		
		if(arr.length==11){
			for(var i=0; i<9; i++) { arrFields[i] = +arr[i]; }
			playerMessage = arr[9];
			var str = arr[10];
			if(str=="HOST"){
				console.log(arr[10]);
				setVisible();
			}
				
			
			
			// sentFields = [0,0,0,0,0,0,0,0,0];			sentFields muss umgeschrieben werden 		todo
			
			document.getElementById("Player").innerHTML = playerMessage;
			redraw();
		}
		statusWait = false;
	});
addListener('START', function(event){
	var stringFromServer = event.data;
	var arr = stringFromServer.split(',');
	playerMessage = arr[9];
	document.getElementById("Player").innerHTML = playerMessage;
	if(arr[10]=="HOST") setVisible();
	statusWait = false;
});
addListener('PLAYERLEFT', function(event){
	var stringFromServer = event.data;
	playerMessage = stringFromServer;
	document.getElementById("Player").innerHTML = playerMessage;
});
addListener('CLOSE', function(event){
	document.getElementById("Player").innerHTML = "Spiel wurde vom Host beendet!";
});
function allowDrop(ev) {
    ev.preventDefault();
}

function drag(ev) {
    ev.dataTransfer.setData("text", ev.target.id);
}

function drop(ev) {
    ev.preventDefault();
    var data = ev.dataTransfer.getData("text");
   ev.target.appendChild(document.getElementById(data));
}

function rollDice(){
    var die1 = document.getElementById("die1");
    
    var status = document.getElementById("status");
    var d1 = Math.floor(Math.random() * 6) + 1;
    document.getElementById("rollimg").src = "wurfel_"+d1+".png"
    var diceTotal = d1;
    die1.innerHTML = d1;
}