/**
 * Created by Evdzhan Mustafa.
 * Matrix stuff.
 */

function getRotateXMatrix(radians) {
    var rotateMatrix = new THREE.Matrix4();
    rotateMatrix.set(
        1, 0, 0, 0,
        0, Math.cos(radians), -(Math.sin(radians)), 0,
        0, Math.sin(radians), Math.cos(radians), 0,
        0, 0, 0, 1);
    return rotateMatrix;
}

function getRotateYMatrix(radians) {
    var rotateMatrix = new THREE.Matrix4();
    rotateMatrix.set(
        Math.cos(radians), 0, Math.sin(radians), 0,
        0, 1, 0, 0,
        -(Math.sin(radians)), 0, Math.cos(radians), 0,
        0, 0, 0, 1);
    return rotateMatrix;
}

function getRotateZMatrix(radians) {
    var rotateMatrix = new THREE.Matrix4();
    rotateMatrix.set(
        Math.cos(radians), -(Math.sin(radians)), 0, 0,
        Math.sin(radians), Math.cos(radians), 0, 0,
        0, 0, 1, 0,
        0, 0, 0, 1);
    return rotateMatrix;
}

function getShiftZMatrix(shift) {
    var shiftMatrix = new THREE.Matrix4();
    shiftMatrix.set(
        1, 0, 0, 0,
        0, 1, 0, 0,
        0, 0, 1, shift,
        0, 0, 0, 1);
    return shiftMatrix;

}





