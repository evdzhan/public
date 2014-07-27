


var lotrReady = false;
var lotrImage = new Image();
lotrImage.onload = function () {
    lotrReady = true;
};
lotrImage.src = 'lotr2.png';
var object_hw = 20 ;
var enemy_bps = 40;
var bullet_size = 4;
var hw = 32;
var hh = 48;
var canvas = document.getElementById("canvas");
var ctx = canvas.getContext("2d");

canvas.width = 640;
canvas.height = 480;

var bgReady = false;
var bgImage = new Image();
bgImage.onload = function () {   
bgReady = true;
ctx.drawImage(bgImage,0,0);
ctx.fillStyle = "rgb(0, 0, 0)";
ctx.font = "20px Helvetica";
ctx.textAlign = "left";
ctx.textBaseline = "top";
ctx.fillText("Press enter to start !", 200, 30); 

	ctx.fillText("W - move UP", 120, 80); 
	ctx.fillText("S - move DOWN", 120, 100);
	ctx.fillText("A - move LEFT", 120, 120); 
	ctx.fillText("D - move RIGHT", 120, 140);
	ctx.fillText("LMB - SHOOT", 120, 160);
	ctx.fillText("[SPACE] - Change hero", 120, 180); 
	ctx.fillText("H - help screen",120, 200);
	ctx.fillText("Remember to switch to different heroes.", 120, 240);
	ctx.fillText("They have different attack range, ", 120, 260);
	ctx.fillText("attack per second ratio and move speed.", 120, 280);

};
bgImage.src = 'background.png';

var towerMusic = document.getElementById('tower');
var heroMusic = document.getElementById('hero');
var lifelossMusic = document.getElementById('lifeloss');


var alive=false;
var paused;
var herotype;
var bullet_color;
var bullet_range;
var bullet_ps;
var aliveUnit;
var goldCollected;
var lives;
var level;
var now;
var then;
var timerId;
var mx_; 
var my_; 
var unit ;
var hero ;
var timeGap;
var timeGap2;

function restart() {
paused = false;
alive = true;
herotype = 0 ;
bullet_color = '#FF0000';
bullet_range = 10 ;
bullet_ps = 20;
aliveUnit = 0;
goldCollected = 0;
lives = 5;
level = 1 ;
timeGap = 0;
timeGap2 = 0;
hero = {
    speed: 162,
    x: canvas.width / 2,
    y: canvas.height / 2,
    width: hw,
    height: hh
};
unit = {
    x: 0,
    y: 0,
    hitpoints: 3
}
}





var bullets = [];
function add_bullet(x, y, angl, flg, rng, clr) {
    var bullet = {
        bullet_x: x,
        bullet_y: y,
        angle: angl,
        time_to_live: rng,
        flag: flg,
        color: clr
    };
    bullets.push(bullet);
}


var keysDown = {};

addEventListener("keydown", function (e) {
if(alive) {
	if(72 == e.keyCode) {
	 if(paused) {
	paused=false;
	resume();
	
	} else {
	paused=true;
	pause();
		}
	
	}
    if (32 == e.keyCode) {
	e.preventDefault();
         
        if (++herotype == 3) herotype = 0;
        if (herotype == 2) {
            heroChange(54, 40, 40, '#000000');
        } else if (herotype == 1) {
            heroChange(108, 30, 25, '#00FF00');
        } else {
            heroChange(162, 20, 10, '#FF0000');
        }
    } else if ( e.keyCode > 65 && e.keyCode < 90 ) e.preventDefault();
    
    keysDown[e.keyCode] = true;
} else {
	if(e.keyCode == 13) {
		alive=true;
		restart();
		start();
			}
	}

}, false);


addEventListener("keyup", function (e) {
    delete keysDown[e.keyCode];
}, false);

addEventListener("mousemove", function (e) {
    mx_ = e.clientX;
    my_ = e.clientY;
}, false);

addEventListener("mousedown", function (e) {

    keysDown['mousedown'] = true;
}, false);

addEventListener("mouseup", function (e) {
	
    delete keysDown['mousedown'];
}, false);

function heroChange(a, b, c, d) {
    hero.speed = a;
    bullet_ps = b;
    bullet_range = c;
    bullet_color = d;
}

var update = function (modifier) {

    if (87 in keysDown && hero.y > 30) {
        hero.y -= hero.speed * modifier;
    }
    if (83 in keysDown && hero.y < canvas.height - 80) {
        hero.y += hero.speed * modifier;
    }
    if (65 in keysDown && hero.x > 30) {
        hero.x -= hero.speed * modifier;
    }
    if (68 in keysDown && hero.x < canvas.width - 60) {
        hero.x += hero.speed * modifier;
    }
    if (timeGap <bullet_ps) {
        timeGap++;
    } else if ('mousedown' in keysDown) {
	
	 heroMusic.currentTime = 0;
         heroMusic.play();
        var bounding_box = canvas.getBoundingClientRect();

        var mx = (mx_ - bounding_box.left) * (canvas.width / bounding_box.width);
        var my = (my_ - bounding_box.top) * (canvas.height / bounding_box.height);

        var angle = Math.atan2(mx - hero.x - 15, my - hero.y);
        timeGap = 0;
        add_bullet(hero.x + 15, hero.y, angle, true, bullet_range,bullet_color);
    }
    if (hero.x <= (unit.x + object_hw) &&
 	unit.x <= (hero.x + hw) &&
 	hero.y <= (unit.y + object_hw) &&
	 unit.y <= (hero.y + hh)) {
        if(aliveUnit == 2) { 
	
	if(++goldCollected >= level * 10  ) level ++;

	} else {
	 lives--;
	lifelossMusic.currentTime = 0;
	lifelossMusic.play();
	if(lives < 0 ) endGame();
	}
        aliveUnit = 0;
        reset();
    }
    for (var i = 0; i < bullets.length; i++) {

        --bullets[i].time_to_live > 0 && 
	bullets[i].bullet_x < canvas.width &&
	 bullets[i].bullet_x > 0 && 
	bullets[i].bullet_y < canvas.height 
	&& bullets[i].bullet_y > 0

        ? draw_bullet(bullets[i]) : bullets.splice(i, 1);

    }
    for (var i = 0; i < bullets.length; i++) {
        bullets[i].bullet_x -= 10 * Math.cos(-bullets[i].angle - (Math.PI / 2));
        bullets[i].bullet_y -= 10 * Math.sin(-bullets[i].angle - (Math.PI / 2));

        if (bullets[i].flag && collision(unit.x,unit.y,bullets[i])) {

            bullets.splice(i, 1);

            if (--unit.hitpoints < 1) {
                if (aliveUnit == 1) {
			aliveUnit = 2;
			unit.hitpoints = 30;
				    }
                else {
                    aliveUnit = Math.round(Math.random() * 100) > 50 ? 1 : 0;                  
                    reset();
                }
            }
        } else if (!(bullets[i].flag) && collision(hero.x,hero.y,bullets[i])) {
            lives--;
	    lifelossMusic.currentTime = 0;
	    lifelossMusic.play();
	if(lives < 0 ) endGame();
            bullets.splice(i, 1);


        }
    }

    if (aliveUnit == 0) {
        var unitAngle = Math.atan2(hero.x - unit.x, hero.y - unit.y);
        unit.x -=   Math.cos(-unitAngle - (Math.PI / 2))   ;
        unit.y -=   Math.sin(-unitAngle - (Math.PI / 2))  ;
    } else if (aliveUnit == 1) {

        
        if (++timeGap2 > enemy_bps) {
	    var unitAngle = Math.atan2(hero.x + 15 - unit.x, hero.y + 15 - unit.y);
            add_bullet(unit.x + 10, unit.y, unitAngle, false, 20, '#0000FF');
            timeGap2 = 0;

        }
    }



}
 	 function collision(x,y,bullet) {
	return (bullet.bullet_x <= x + hw &&
	x <= bullet.bullet_x + bullet_size &&
	bullet.bullet_y <= y + hh &&
	y <= bullet.bullet_y + bullet_size);

	}

    function draw_bullet(bullet) {
        ctx.fillStyle = bullet.color;
        ctx.fillRect(bullet.bullet_x, bullet.bullet_y, bullet_size, bullet_size);
    }


function reset() {
  	unit.hitpoints = Math.round(Math.random() * level ) + 1;
	 
	timeGap2 = -20;
    if (hero.x < canvas.width / 2) {

        unit.x = object_hw + canvas.width / 2 + (Math.random() * (canvas.width / 2 - 110));
        unit.y = object_hw + (Math.random() * (canvas.height - 140));

    } else {

        unit.x = object_hw + (Math.random() * (canvas.width / 2 - 110));
        unit.y = object_hw + (Math.random() * (canvas.height - 140));

    }

};

function render() {
    if (bgReady) {
        ctx.drawImage(bgImage, 0, 0);
    }

    if (lotrReady) {
        switch (herotype) {
        case 0:
            ctx.drawImage(lotrImage, 0, 0, 64, 82,
                hero.x, hero.y, hw, hh);
            break;
        case 1:
            ctx.drawImage(lotrImage, 68, 0, 42, 82,
                hero.x, hero.y, hw, hh);
            break;
        case 2:
            ctx.drawImage(lotrImage, 120, 0, 65, 82,
                hero.x, hero.y, hw, hh);
            break;
        }

        switch (aliveUnit) {
        case 0:
            ctx.drawImage(lotrImage, 190, 48, 32, 32,
                unit.x, unit.y, object_hw, object_hw);
            break;

        case 1:
            ctx.drawImage(lotrImage, 222, 0, 50, 82,
                unit.x, unit.y, object_hw, object_hw);
            break;

        case 2:
            ctx.drawImage(lotrImage, 190, 3, 32, 32,
                unit.x, unit.y, object_hw, object_hw);
            break;

        }
    }

document.getElementById("gameinfo").innerHTML =  lives + " lives  |  gold  " + goldCollected +" |  stage " +level;
document.getElementById("tip").innerHTML ="Press H for ingame help"; 



};

function resume() {
then = Date.now();
timerId = setInterval(main, 1000 / 25);
}


function pause() {
clearInterval(timerId);
if (bgReady) {
        ctx.drawImage(bgImage, 0, 0);    }
	ctx.fillStyle = "rgb(0, 0, 0)";
	ctx.font = "20px Helvetica";
	ctx.textAlign = "left";
	ctx.textBaseline = "top";
	ctx.fillText("W - move UP", 120, 80); 
	ctx.fillText("S - move DOWN", 120, 100);
	ctx.fillText("A - move LEFT", 120, 120); 
	ctx.fillText("D - move RIGHT", 120, 140);
	ctx.fillText("LMB - SHOOT", 120, 160);
	ctx.fillText("[SPACE] - Change hero", 120, 180); 
	ctx.fillText("H - this help screen",120, 200);
	ctx.fillText("Remember to switch to different heroes.", 120, 240);
	ctx.fillText("They have different attack range, ", 120, 260);
	ctx.fillText("attack per second ratio and move speed.", 120, 280);
 
	 
}


function supports_html5_storage() {
  try {
    return 'localStorage' in window && window['localStorage'] !== null;
  } catch (e) {
    return false;
  }
}


function endGame() {
alive = false;
clearInterval(timerId);


 
 if (bgReady) {
        ctx.drawImage(bgImage, 0, 0);
    }
	ctx.fillStyle = "rgb(0, 0, 0)";
	ctx.font = "20px Helvetica";
	ctx.textAlign = "left";
	ctx.textBaseline = "top";
	ctx.fillText("You have died ! Press enter to play again.", 80, 60);
	ctx.fillText("You collected : " +goldCollected + " gold.", 80, 80); 
	if(supports_html5_storage()) {
	if (localStorage.getItem("highestScore") === null) {
		localStorage["highestScore"] = goldCollected;	
	} else if ( parseInt(localStorage["highestScore"]) < goldCollected ) {
		localStorage["highestScore"] = goldCollected;
	} 
	ctx.fillText("Your highest score is : " +localStorage["highestScore"] + " gold.", 80,100);

	}

}

function main() {
    now = Date.now();
    var delta = now - then;
    render();
    update(delta / 1000);
    then = now;
};
function start() {

reset();
then = Date.now();
timerId = setInterval(main, 1000 / 25);

}

    


 

