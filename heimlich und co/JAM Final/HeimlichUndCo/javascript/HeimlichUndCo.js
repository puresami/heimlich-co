
    var parent1;
    var array = [];
    var Spieleranzahl =0;
    var arr;
    var zuege ;

    var prev;

    var Spieler1 = 0;
    var Spieler2 = 0;
    var Spieler3 = 0;
    var Spieler4 = 0;
    var Spieler5 = 0;
    var Spieler6 = 0;
    var Agentenanzahl = 0;

    var spieler= [0,0,0,0,0,0,0];

    
    var Farben = ["yellow", "red", "purple", "blue", "green", "orange", "gray"];
    var Spielerpos = [0,0,0,0,0,0,0,0];
    var Punkte = [0,0,0,0,0,0,0]
    var arrFields = [0,0,0,0,0,0,0,7,0,0,0,0,0,0,0,0,0];


 

    window.onload = function () {
    	
    	sendDataToServer("HI");
    	console.log("geladen");
    	
    }
    	
    	
    	
    
   addListener('START1', function(event){
        var stringFromServer = event.data;
        arr = stringFromServer.split(',');
         
        console.log("start erhalten");
        console.log(arr);
        
        startGame();
        starting(arr);
        
        document.getElementById("status").innerHTML = "Alle Spieler sind nun im Spiel, warte auf Spielstart!";
        
    });


  addListener('standardEvent', function(event){
            var stringFromServer = event.data;
            var arr = stringFromServer.split(',');
            console.log(arr);

            if(arr.length==17){
                for(var i=0; i<15; i++) { arrFields[i] = +arr[i]; }
                playerMessage = arr[15];
                var str = arr[16];
                arrFields = arr;
                
                
                
                if(playerMessage.includes("Du ")){
                          
                	console.log("hallo");
                	
                          document.getElementById("wuerfel").style.visibility= "visible";
                 
                          }

                document.getElementById("status").innerHTML = playerMessage;
                //updateBoard();
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
        
	
	//console.log(event.data);
    
    if (event.data == "H"){
        
        
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


        if(zuege > 0 ){

            ev.preventDefault();

        }


    }


    function drag(ev) {
        ev.dataTransfer.setData("text", ev.target.id);

        prev = ev.target.parentElement.getAttribute("number");

    }

    function drop(ev) {

        console.log(ev.target.getAttribute("number") - prev +" wert: "+ ev.target.getAttribute("number") + "minus" + prev + "Zuege " + zuege);

        if((ev.target.getAttribute("number") - prev) <= zuege && prev < ev.target.getAttribute("number")  ){



            ev.preventDefault();
            var data = ev.dataTransfer.getData("text");
            ev.target.appendChild(document.getElementById(data));

            

            zuege = zuege - (ev.target.getAttribute("number")-prev);
            sendGameData()
        }


        if(zuege == 0){
            
            
            arrFields[17] = 0;
            
            console.log("du bist fertig!");

            sendGameData();

        }

        console.log(ev.dataTransfer.getData("text"));

        if(ev.dataTransfer.getData("text") != "tresor"){

        if(ev.target.contains(tresor)){

            arrFields[8] += parseInt(document.getElementById("drop1").parentElement.getAttribute("number"));
            arrFields[9] += parseInt(document.getElementById("drop2").parentElement.getAttribute("number"));
            arrFields[10] += parseInt(document.getElementById("drop3").parentElement.getAttribute("number"));
            arrFields[11] += parseInt(document.getElementById("drop4").parentElement.getAttribute("number"));
            arrFields[12] += parseInt(document.getElementById("drop5").parentElement.getAttribute("number"));
            arrFields[13] += parseInt(document.getElementById("drop6").parentElement.getAttribute("number"));
            arrFields[14] += parseInt(document.getElementById("drop7").parentElement.getAttribute("number"));

            fillpoints();

            document.getElementById("tresor").setAttribute("draggable", "true");

           sendGameData()
            console.log(arrFields);

        }

    }


        die1.innerHTML = "Zuege: " + zuege;


    } 

//initialisieren der Spielerfiguren und der individuellen Spielerkarte

 function starting(arr){
     
     for(var i =0; i<= arr.length-1 ; i++ ){
            
            if(arr[i] == 1){
               
               document.getElementById("drop"+(i+1) ).style.visibility= "visible";
               
               }
        }
        
     var color = arr[8];
        document.getElementById("Karte").style.backgroundImage = "url(/HeimlichUndCo/images/" + arr[7] + ".png)";
      
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
        
        
        //arr = [1,1,1,0,0,0,0,"red"]
        //starting(arr);
        
        //document.getElementById("wuerfel").style.visibility = "hidden";
        
       // getparent(el);
         //updateBoard();

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


    function initBoard (Spieleranzahl){


    }



    // Points setting for Colors

function fillpoints(){


    
    
    for(var i = 0; i<arr.length; i++ ){
        
       
        
        if (arr[i] == 1){
            
            Spieleranzahl += 1
            
            
            document.getElementById("p"+ (1+i)).style.visibility = "visible";
            document.getElementById("p"+(i+1)).innerHTML = Farben[i] + " = " + arrFields[8+(i)];
            
            
        }
        
        
        
    }
    

           

    }



    // getting 

    function updateBoard(){

        var t =1;

        for(var i = 0; i<6; i++){

     
            var Drop =document.getElementById("drop"+t );
               
               document.getElementById("Feld"+arrFields[i]).appendChild(Drop);

            t++;
               
  
        }


        t=1;


        for(var i =6; i<6+Spieleranzahl; i++){


            Punkte[i-6] = arrFields[i]


        }

        fillpoints();

    }


    function sendGameData(){

       arrFields[0] = Spielerpos[0] = parseInt(document.getElementById("drop1").parentElement.getAttribute("number"));
       arrFields[1] = Spielerpos[1] = parseInt(document.getElementById("drop2").parentElement.getAttribute("number"));
       arrFields[2] = Spielerpos[2] = parseInt(document.getElementById("drop3").parentElement.getAttribute("number"));
       arrFields[3] = Spielerpos[3] = parseInt(document.getElementById("drop4").parentElement.getAttribute("number"));
       arrFields[4] = Spielerpos[4] = parseInt(document.getElementById("drop5").parentElement.getAttribute("number"));
       arrFields[5] = Spielerpos[5] = parseInt(document.getElementById("drop6").parentElement.getAttribute("number"));
       arrFields[6] = Spielerpos[6] = parseInt(document.getElementById("drop7").parentElement.getAttribute("number"));
       arrFields[7] = Spielerpos[7] = parseInt(document.getElementById("tresor").parentElement.getAttribute("number"));

        sendDataToServer(arrFields);
  
        }


//gets Player- and KI-amount and sends it to server

function startscreen(){
	var anzahlPlayer = parseInt(document.getElementById("countPlayer").value);
	var anzahlKi = parseInt(document.getElementById("countKi").value);
	
	var overall = anzahlPlayer + anzahlKi;
	
	if(overall <= 5)
	{
		document.getElementById("Startscreen").style.display = "none";
		document.getElementById("Game").style.visibility = "hidden";
		document.getElementById("Lobby").style.visibility = "visible";
        
        var arri=[anzahlPlayer, anzahlKi];
        
        initialize(arri);
	}
	else
	{
		document.getElementById("errorNoP").style.visibility = "visible";	
	}
}


    function initialize(arri){
      
        sendDataToServer("INITIALIZE,"+arri);
        
  
    }
    
    
    function startGame(){
	//soll ausgefuehrt werden, sobald die vorgegebene Spieleranzal erreicht wurde
	document.getElementById("Startscreen").style.visibility = "hidden";
	document.getElementById("Game").style.visibility = "visible";
	document.getElementById("Lobby").style.visibility = "hidden";
}
