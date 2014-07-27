var canvas =  document.getElementById("canvas");
var ctx = canvas.getContext("2d");
canvas.width =  480;
canvas.height = 450;
var angle;
var bullets=[];
var ringsCollected = 0;
var mx;
var my;
var bullet_range = 30;
var hero_speed = 128;
var herow = 32;
var heroh = 48;
var dimension=20;
var heroIndex = 1;
var ring = true ;
var monsterAlive = true ;




var bgReady = false;
var bgImage = new Image();
bgImage.onload = function () {
	bgReady = true;
};
bgImage.src = 'background.png';


var lotrReady = false;
var lotrImage = new Image();
lotrImage.onload = function() {
lotrReady=true ;
};
lotrImage.src ='lotr.png';


 

function add_bullet(x,y,r) { 
var bullet = {
bullet_x : x ,
bullet_y : y ,
angle : r ,
time_to_live : bullet_range 
};
bullets.push(bullet);
}


var ring = {
ringx : 0 ,
ringy : 0 ,

};

var tower = {
towerx : 0 ,
towery : 0
};



var hero = {	speed: hero_speed, 	x: canvas.width / 2,	y: canvas.height / 2,       width : herow,        height : heroh};
 
var monster = {	x: 0,	y: 0};


var keysDown = {};

addEventListener("keydown", function (e) {
     if(e.keyCode >= 37 && e.keyCode <=40)   e.preventDefault();
	keysDown[e.keyCode] = true;
}, false); 
addEventListener("keyup", function (e) {
	delete keysDown[e.keyCode];
}, false);
 
addEventListener("mousemove",function(e) {
 
var bounding_box=canvas.getBoundingClientRect();
 mx=(e.clientX-bounding_box.left) * (canvas.width/bounding_box.width);	
 my=(e.clientY-bounding_box.top) * (canvas.height/bounding_box.height); 
 
},false);
addEventListener("mousedown",function(e) {
keysDown['mousedown'] = true ;
},false);
addEventListener("mouseup",function(e) {
delete keysDown['mousedown'];
},false);
var reset = function () {
 monster.x = dimension + (Math.random() * (canvas.width - 110));
 monster.y = dimension + (Math.random() * (canvas.height - 140));
};

 
var timeGap = 0;
var update = function (modifier) {
	if (87 in keysDown && hero.y > 30 ) {   
		hero.y -= hero.speed * modifier;
	}
	if (83 in keysDown && hero.y < canvas.height - 80 ) {  
		hero.y += hero.speed * modifier;
	}
	if (65 in keysDown && hero.x > 30) {  
		hero.x -= hero.speed * modifier;
	}
	if (68 in keysDown && hero.x < canvas.width - 60) {  
		hero.x += hero.speed * modifier; 
	}             
         if(timeGap < 11) {
	 	timeGap++;
	 } else if('mousedown' in keysDown) { 
                angle = Math.atan2(mx - hero.x,my - hero.y); 
  	 	timeGap = 0;
	 	add_bullet(hero.x+20,hero.y,angle);
         }    	
	if (	hero.x <= (monster.x + dimension)
		&& monster.x <= (hero.x + herow)
		&& hero.y <= (monster.y + dimension)
		&& monster.y <= (hero.y + heroh)) 
	{	reset();	}   
	 
 	 
	 
	 
	for(var i = 0 ; i < bullets.length ; i++) { 

	--bullets[i].time_to_live > 0
	&& bullets[i].bullet_x < canvas.width
	&& bullets[i].bullet_x > 0 
	&& bullets[i].bullet_y < canvas.height
	&& bullets[i].bullet_y > 0  
	
	? draw_bullet(bullets[i].bullet_x,bullets[i].bullet_y) : bullets.splice(i,1);
	
 	}   
      
        for(var i = 0 ; i < bullets.length ; i++) {   
	var vx = 10 * Math.cos(-bullets[i].angle - (Math.PI / 2));
	var vy = 10 * Math.sin(-bullets[i].angle - (Math.PI / 2));
	bullets[i].bullet_x -= vx;
	bullets[i].bullet_y -= vy; 

	if(bullets[i].bullet_x <= (monster.x + dimension)
		&& monster.x <= (bullets[i].bullet_x + 5)
		&& bullets[i].bullet_y <= (monster.y + dimension)
		&& monster.y <= (bullets[i].bullet_y + 5)) {
        bullets.splice(i,1);
        var randomness = Math.random() * 100 ;
        if(randomness >= 95 ) {
	ring.ringx = monster.x;
        ring.ringy = monster.y; 
        ring=true;         	
	
        } else {
        tower.towerx = monster.x;
        tower.towery = monster.y;
        ring = false;         
        monsterAlive=false;
        }
	}
	
	}
 	ring ?  
 	draw_ring(ring.ringx,ring.ringy) :
  	draw_tower(tower.towerx,tower.towery);
	


};
function draw_tower(x,y) {
ctx.drawImage(lotrImage ,222,0,50,82,  x,  y,dimension,dimension ); 
}
function draw_bullet(x,y) {   
    ctx.fillStyle="#FFFF00";
    ctx.fillRect(x,y,5,5);  
}
function draw_ring(x,y) {    
    ctx.drawImage(lotrImage ,190,3,32,32,  x,  y,dimension,dimension ); 
}



var render = function () {
	if (bgReady) {
		ctx.drawImage(bgImage, 0, 0);
	}

	if (lotrReady) {
switch(heroIndex) {
	case 0 :	ctx.drawImage(lotrImage, 0, 0, 45, 82,
                      hero.x, hero.y, herow,heroh); break;
        case 1 : 	ctx.drawImage(lotrImage, 50, 0, 70, 82,
                      hero.x, hero.y, herow,heroh); break;
	case 2 : 	ctx.drawImage(lotrImage, 125, 0, 65, 82,
                      hero.x, hero.y, herow,heroh); break;
	}

	if(monsterAlive) ctx.drawImage(lotrImage ,190,48,32,32, monster.x, monster.y,dimension,dimension );
			
	}
	
	 
	
	ctx.fillStyle = "rgb(250, 250, 250)";
	ctx.font = "15px Helvetica";
	ctx.textAlign = "left";
	ctx.textBaseline = "top";
	ctx.fillText("Rings collected : " + ringsCollected, 20, 5); 
};


var main = function () {
	var now = Date.now();
	var delta = now - then;
        render();
	update(delta / 1000);
	then = now;
};
reset();
var then = Date.now();
setInterval(main,1000/23);

 

