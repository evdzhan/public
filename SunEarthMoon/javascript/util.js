/**
 * Created by Evdzhan Mustafa.
 */

//addEventListener("mousewheel", handleScroll, false);
//addEventListener("DOMMouseScroll", handleScroll, false);
//
//const KEYBOARD_MOVE_VAL = 0.05;
//const camMax_X = 15;
//const camMin_X = -15;
//const canMax_Z = 15;
//const camMin_Z = -15;
//const camMax_Y = 15;
//const canMin_Y = -15;
//
//function handleScroll(e) {
//    var scrollDetail = (e.wheelDelta || -e.detail );// works both in Chrome and FFox
//    if (scrollDetail > 0 && camera.position.y <= canMin_Y) return;
//    else if (scrollDetail < 0 && camera.position.y >= camMax_Y) return;
//    camera.position.y -= scrollDetail / 10;
//}
//
var keysDown = {};
addEventListener("keydown", function (e) {
    if (e.keyCode == 32) {
        e.preventDefault();
    }
    keysDown[e.keyCode] = true;
}, false);
addEventListener("keyup", function (e) {
    delete keysDown[e.keyCode];
}, false);

function handleControls() {
    if (keysDown[32]) {
        camera.lookAt(earthMesh.position);
    }
    if (keysDown[49]) {
        cameraPosition = 1;
        setDefaultCameraPosition();
    }
    if (keysDown[50]) {
        camera.position.copy(sunMesh.position);
        camera.position.y += 1.5;
        camera.lookAt(earthMesh.position);
        controls.target.copy(earthMesh.position);
        cameraPosition = 2;
    }
    if (keysDown[51]) {
        camera.position.copy(earthMesh.position);
        camera.position.y += 3.5;
        camera.lookAt(earthMesh.position);
        controls.target.copy(earthMesh.position);
        cameraPosition = 3;
    }
    if (keysDown[52]) {
        camera.position.copy(sunMesh.position);
        camera.position.y += 17.5;
        camera.lookAt(sunMesh.position);
        controls.target.copy(sunMesh.position);
        cameraPosition = 4;
    }
    if (keysDown[53]) {
        camera.position.copy(earthMesh.position);
        camera.position.y += 0.5;
        camera.lookAt(moonMesh.position);
        controls.target.copy(moonMesh.position);
        cameraPosition = 5;
    }
}

function updateScale(spinner, mesh) {
    var value = Number(spinner.value);
    mesh.scale.set(value, value, value);
    if (mesh === sunMesh) {
        SCALED.SUN_SCALE = value;
    } else if (mesh === earthMesh) {
        SCALED.EARTH_SCALE = value;
    } else if (mesh === moonMesh) {
        SCALED.MOON_SCALE = value;
    }
}
function updateSpinSpeed(spinner, mesh) {
    var value = Number(spinner.value);
    if (mesh === moonMesh) {
        SCALED.MOON_SPIN_PERIOD = value;
    } else if (mesh === earthMesh) {
        SCALED.EARTH_SPIN_PERIOD = value;
    } else if (mesh === sunMesh) {
        SCALED.SUN_SPIN_PERIOD = value;
    }
}
function updateRotateSpeed(spinner, mesh) {
    var value = Number(spinner.value);
    if (mesh === moonMesh) SCALED.MOON_ORBIT_PERIOD = value;
    else if (mesh === earthMesh)  SCALED.EARTH_ORBIT_PERIOD = value;
}
function updateAxialTilt(spinner, mesh) {
    var value = Number(spinner.value);
    if (mesh === moonMesh) SCALED.MOON_AXIAL_TILT = value;
    else if (mesh === earthMesh)  SCALED.EARTH_AXIAL_TILT = value;
    else if (mesh === sunMesh)  SCALED.SUN_AXIAL_TILT = value;
    reset();
}
function updateMoonOrbitTilt(spinner) {
    var value = Number(spinner.value);
    SCALED.MOON_ORBIT_TILT_ECLIPTIC = value;
    reset();
}
function updateMoonEarthSunDistance(spinner, mesh) {
    var value = Number(spinner.value);
    if (mesh === moonMesh) SCALED.MOON_ORBIT_RADIUS = value;
    else if (mesh === earthMesh)  SCALED.EARTH_ORBIT_RADIUS = value;
    //reset(); not needed !
}
function coordinateLinesOnOff(checkbox) {
    if (checkbox.checked == true) {
        scene.add(xLine);
        scene.add(yLine);
        scene.add(zLine);
    } else {
        scene.remove(xLine);
        scene.remove(yLine);
        scene.remove(zLine);
    }

}
function axialLinesOnOff(checkbox) {
    if (checkbox.checked == true) {
        moonMesh.add(moonAxialLine);
        earthMesh.add(earthAxialLine);
        sunMesh.add(sunAxialLine);
        addAxialLines = true;
    } else {
        moonMesh.remove(moonAxialLine);
        earthMesh.remove(earthAxialLine);
        sunMesh.remove(sunAxialLine);
        addAxialLines = false;
    }

}

function addCoordinateSystemLines() {
    const drawDistance = 10;
    var xCoordinate = new THREE.Geometry();
    xCoordinate.vertices.push(new THREE.Vector3(0, 0, 0));
    xCoordinate.vertices.push(new THREE.Vector3(drawDistance, 0, 0));
    xCoordinate.vertices.push(new THREE.Vector3(0, 0, 0));
    xLine = new THREE.Line(xCoordinate, MATERIALS.RED);
    scene.add(xLine);

    var yCoordinate = new THREE.Geometry();
    yCoordinate.vertices.push(new THREE.Vector3(0, drawDistance, 0));
    yCoordinate.vertices.push(new THREE.Vector3(0, -drawDistance, 0));
    yCoordinate.vertices.push(new THREE.Vector3(0, drawDistance, 0));
    yLine = new THREE.Line(yCoordinate, MATERIALS.GREEN);
    scene.add(yLine);

    var zCoordinate = new THREE.Geometry();
    zCoordinate.vertices.push(new THREE.Vector3(0, 0, 0));
    zCoordinate.vertices.push(new THREE.Vector3(0, 0, drawDistance));
    zCoordinate.vertices.push(new THREE.Vector3(0, 0, 0));
    zLine = new THREE.Line(zCoordinate, MATERIALS.BLUE);
    scene.add(zLine);
}

