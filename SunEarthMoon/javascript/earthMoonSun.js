/**
 * Created by Evdzhan Mustafa.
 * Source containing the setup for Earth, Moon and Sun meshes
 */

var addAxialLines = true;
const ROT_AXIS_SIZE = 0.35;
const SPHERE_SEGMENTS = 32;
const BUMP_SCALE = 0.05;

function Sun() {
    sunGeometry = new THREE.SphereGeometry(SCALED.SUN_RADIUS, SPHERE_SEGMENTS, SPHERE_SEGMENTS);
    var sunMat = new THREE.MeshBasicMaterial();
    sunMat.map = THREE.ImageUtils.loadTexture(FILES.SUN_MAP);
    sunMat.map.minFilter = THREE.NearestFilter;
    sunMesh = new THREE.Mesh(sunGeometry, sunMat);

    var sunTiltMatrix = getRotateZMatrix(SCALED.SUN_AXIAL_TILT * Math.PI / 180.0);
    sunGeometry.applyMatrix(sunTiltMatrix);
    sunRotateVector = new THREE.Vector3(0.0, 1.0, 0.0).applyMatrix4(sunTiltMatrix).normalize();

    if (addAxialLines) sunMesh.add(getAxialLineSun());
    sunMesh.position.set(3, 0, 3);
    sunMesh.scale.set(SCALED.SUN_SCALE, SCALED.SUN_SCALE, SCALED.SUN_SCALE);
    return sunMesh;
}
function getAxialLineSun() {
    var sunAxialLineGeom = new THREE.Geometry();
    sunAxialLineGeom.vertices.push(new THREE.Vector3(0, -(SCALED.SUN_RADIUS + ROT_AXIS_SIZE), 0));
    sunAxialLineGeom.vertices.push(new THREE.Vector3(0, SCALED.SUN_RADIUS + ROT_AXIS_SIZE, 0));
    sunAxialLineGeom.vertices.push(new THREE.Vector3(0, -(SCALED.SUN_RADIUS + ROT_AXIS_SIZE), 0));
    sunAxialLine = new THREE.Line(sunAxialLineGeom, MATERIALS.RED);
    sunAxialLineGeom.applyMatrix(getRotateZMatrix(SCALED.SUN_AXIAL_TILT * Math.PI / 180.0));
    sunAxialLine.position.copy(sunMesh.position);
    return sunAxialLine;
}

function Earth() {
    earthGeometry = new THREE.SphereGeometry(SCALED.EARTH_RADIUS, SPHERE_SEGMENTS, SPHERE_SEGMENTS);
    earthMesh = new THREE.Mesh(earthGeometry, getEarthMaterial());
    earthMesh.receiveShadow = true;
    earthMesh.castShadow = true;

    var earthTiltMatrix = getRotateZMatrix(SCALED.EARTH_AXIAL_TILT * Math.PI / 180.0);
    earthGeometry.applyMatrix(earthTiltMatrix);
    earthRotateVector = new THREE.Vector3(0.0, 1.0, 0.0).applyMatrix4(earthTiltMatrix);

    earthMesh.add(getCloudMesh());
    if (addAxialLines) earthMesh.add(getAxialLineEarth());
    earthMesh.position.set(3, 0, 5);
    earthMesh.scale.set(SCALED.EARTH_SCALE, SCALED.EARTH_SCALE, SCALED.EARTH_SCALE);
    return earthMesh;
}
function getAxialLineEarth() {
    var earthAxialLineGeom = new THREE.Geometry();
    earthAxialLineGeom.vertices.push(new THREE.Vector3(0, -(SCALED.EARTH_RADIUS + ROT_AXIS_SIZE), 0));
    earthAxialLineGeom.vertices.push(new THREE.Vector3(0, SCALED.EARTH_RADIUS + ROT_AXIS_SIZE, 0));
    earthAxialLineGeom.vertices.push(new THREE.Vector3(0, -(SCALED.EARTH_RADIUS + ROT_AXIS_SIZE), 0));
    earthAxialLine = new THREE.Line(earthAxialLineGeom, MATERIALS.RED);
    earthAxialLineGeom.applyMatrix(getRotateZMatrix(SCALED.EARTH_AXIAL_TILT * Math.PI / 180.0));
    earthAxialLine.position.copy(earthMesh.position);
    earthAxialLine.castShadow = true;
    earthAxialLine.receiveShadow = true;
    return earthAxialLine;
}

function Moon() {
    moonGeometry = new THREE.SphereGeometry(SCALED.MOON_RADIUS, SPHERE_SEGMENTS, SPHERE_SEGMENTS);
    var moonMat = new THREE.MeshPhongMaterial();
    moonMat.map = THREE.ImageUtils.loadTexture(FILES.MOON_MAP);
    moonMat.map.minFilter = THREE.NearestFilter;

    moonMesh = new THREE.Mesh(moonGeometry, moonMat);
    moonMesh.receiveShadow = true;
    moonMesh.castShadow = true;
    var moonTiltMatrix = getRotateZMatrix(SCALED.MOON_AXIAL_TILT * Math.PI / 180.0);
    moonRotateVector = new THREE.Vector3(0.0, 1.0, 0.0).applyMatrix4(moonTiltMatrix);
    moonGeometry.applyMatrix(moonTiltMatrix);
    if (addAxialLines)moonMesh.add(getAxialLineMoon());
    moonMesh.position.set(3, 0, 5.5);
    moonMesh.scale.set(SCALED.MOON_SCALE, SCALED.MOON_SCALE, SCALED.MOON_SCALE);
    return moonMesh;
}
function getAxialLineMoon() {
    var moonAxialLineGeom = new THREE.Geometry();
    moonAxialLineGeom.vertices.push(new THREE.Vector3(0, -(SCALED.MOON_RADIUS + ROT_AXIS_SIZE), 0));
    moonAxialLineGeom.vertices.push(new THREE.Vector3(0, SCALED.MOON_RADIUS + ROT_AXIS_SIZE, 0));
    moonAxialLineGeom.vertices.push(new THREE.Vector3(0, -(SCALED.MOON_RADIUS + ROT_AXIS_SIZE), 0));
    moonAxialLine = new THREE.Line(moonAxialLineGeom, MATERIALS.RED);
    moonAxialLineGeom.applyMatrix(getRotateZMatrix(SCALED.MOON_AXIAL_TILT * Math.PI / 180.0));
    moonAxialLine.position.copy(moonMesh.position);
    moonAxialLine.castShadow = true;
    moonAxialLine.receiveShadow = true;
    return moonAxialLine;
}

function getCloudMesh() {
    var geometry = new THREE.SphereGeometry(SCALED.CLOUD_SIZE, SPHERE_SEGMENTS, SPHERE_SEGMENTS);
    var material = new THREE.MeshPhongMaterial({
        map: THREE.ImageUtils.loadTexture(FILES.EARTH_CLOUD),
        side: THREE.DoubleSide,
        opacity: SCALED.CLOUD_OPACITY,
        transparent: true,
        depthWrite: false
    });
    var cloudMesh = new THREE.Mesh(geometry, material);
    cloudMesh.castShadow = true;
    cloudMesh.receiveShadow = true;
    return cloudMesh;
}
function getEarthMaterial() {
    var earthMat = new THREE.MeshPhongMaterial();
    earthMat.map = THREE.ImageUtils.loadTexture(FILES.EARTH_MAP);
    earthMat.map.minFilter = THREE.NearestFilter;
    earthMat.bumpMap = THREE.ImageUtils.loadTexture(FILES.EARTH_BUMP);
    earthMat.bumpMap.minFilter = THREE.NearestFilter;
    earthMat.bumpScale = BUMP_SCALE;
    earthMat.specularMap = THREE.ImageUtils.loadTexture(FILES.EARTH_SPECULAR);
    earthMat.specularMap.minFilter = THREE.NearestFilter;
    earthMat.specular = new THREE.Color('grey');
    return earthMat;
}