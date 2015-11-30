/**
 * Created by Evdzhan Mustafa.
 */
var scene, camera, renderer, light, controls;
var sunMesh, sunGeometry, earthMesh, earthGeometry, moonMesh, moonGeometry;
var earthRotateVector, moonRotateVector, sunRotateVector;
var earthAxialLine, moonAxialLine, sunAxialLine;
var xLine, yLine, zLine;
var WIDTH = window.innerWidth, HEIGHT = window.innerHeight;
var earthOrbitAngle = 0;
var moonOrbitAngle = 0;
var cameraPosition = 0;

main();
render();

function main() {
    setUp();
    addCoordinateSystemLines();
    scene.add(Sun());
    scene.add(Earth());
    scene.add(Moon());
    setDefaultCameraPosition();
    //light.shadowCameraVisible = true; // only for debugging
}
function setUp() {
    scene = new THREE.Scene();
    camera = new THREE.PerspectiveCamera(45, WIDTH / HEIGHT, 0.1, 50);
    scene.add(camera);
    setUpLight();
    setUpRenderer();
    controls = new THREE.OrbitControls(camera, renderer.domElement);
}


function setUpRenderer() {
    renderer = new THREE.WebGLRenderer({antialias: true});
    renderer.setClearColor(0x000000, 1);
    renderer.setSize(WIDTH, HEIGHT);
    document.getElementById("mainDivElement").appendChild(renderer.domElement);
    renderer.shadowMap.enabled = true;
    renderer.shadowMapSoft = true;
    renderer.shadowCameraNear = 1;
    renderer.shadowCameraFar = 15;
    renderer.shadowCameraFov = 45;
    renderer.shadowMapBias = 0.0039;
    renderer.shadowMapDarkness = 0.5;
    renderer.shadowMapWidth = 1024;
    renderer.shadowMapHeight = 1024;
}
function setUpLight() {
    light = new THREE.DirectionalLight(0xFFFFFF, 1);
    light.position.set(3, 0.0, 3);
    light.castShadow = true;
    light.shadowDarkness = 0.75;
    light.shadowCameraLeft = -(2.5);
    light.shadowCameraRight = 2.5;
    light.shadowCameraTop = 1;
    light.shadowCameraBottom = -(1);
    light.shadowCameraNear = 1;
    light.shadowCameraFar = 13;
    scene.add(light);
    var ambientLight = new THREE.AmbientLight(0x222222);
    scene.add(ambientLight);
}


function render() {
    light.target = earthMesh;
    moveEarth();
    moveMoon();
    moveSun();
    handleControls();
    renderer.render(scene, camera);
    requestAnimationFrame(render);
}
function moveMoon() {
    if (moonOrbitAngle == 360) moonOrbitAngle = SCALED.MOON_ORBIT_PERIOD;
    moonOrbitAngle += SCALED.MOON_ORBIT_PERIOD;
    var x = (Math.cos(-moonOrbitAngle * Math.PI / 180) * SCALED.MOON_ORBIT_RADIUS);
    var z = (Math.sin(-moonOrbitAngle * Math.PI / 180) * SCALED.MOON_ORBIT_RADIUS);
    var calculatedPosition = new THREE.Vector4(x, 0, z, 1);
    calculatedPosition.applyMatrix4(getRotateZMatrix(-SCALED.MOON_ORBIT_TILT_ECLIPTIC * Math.PI / 180.0));
    moonMesh.position.copy(earthMesh.position);
    moonMesh.position.add(calculatedPosition);
    moonMesh.rotateOnAxis(moonRotateVector, SCALED.MOON_SPIN_PERIOD * Math.PI / 180);
}
function moveEarth() {
    earthMesh.position.copy(sunMesh.position); // in case we make the sun moving...
    if (earthOrbitAngle == 360) earthOrbitAngle = SCALED.EARTH_ORBIT_PERIOD;
    earthOrbitAngle += SCALED.EARTH_ORBIT_PERIOD;
    earthMesh.position.x -= (Math.cos(-earthOrbitAngle * Math.PI / 180) * SCALED.EARTH_ORBIT_RADIUS);
    earthMesh.position.z -= (Math.sin(-earthOrbitAngle * Math.PI / 180) * SCALED.EARTH_ORBIT_RADIUS);
    earthMesh.rotateOnAxis(earthRotateVector, SCALED.EARTH_SPIN_PERIOD * Math.PI / 180);
}
function moveSun() {
    sunMesh.rotateOnAxis(sunRotateVector, SCALED.SUN_SPIN_PERIOD * Math.PI / 180);
}


function reset() {
    scene.remove(sunMesh);
    scene.remove(earthMesh);
    scene.remove(moonMesh);
    scene.add(Sun());
    scene.add(Earth());
    scene.add(Moon());
}
function setDefaultCameraPosition() {
    camera.position.copy(sunMesh.position);
    camera.position.x += 7;
    camera.position.y += 5;
    camera.lookAt(sunMesh.position);
    controls.target.copy(sunMesh.position);
}





