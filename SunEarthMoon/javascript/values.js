/**
 * Created by Evdzhan Mustafa.
 * This js file contains various variables used in the Solar system.
 */
const REAL = {
    EARTH_RADIUS: 1.0,                         // earth radii
    EARTH_ORBIT_RADIUS: 23500.0,               // earth radii
    EARTH_SPIN_PERIOD: 0.9972,                 // earth days
    EARTH_ORBIT_PERIOD: 365.26,                // earth days
    EARTH_AXIAL_TILT: 23.44,                   // degrees
    EARTH_ORBIT_ECCENTRICITY: 0.0167,          // ?

    MOON_RADIUS: 0.277,                        // earth radii
    MOON_ORBIT_RADIUS: 60.3,                   // earth radii
    MOON_ORBIT_PERIOD: 27.32,                  // earth days
    MOON_SPIN_PERIOD: 27.32,                   // earth days
    MOON_AXIAL_TILT: 1.543,                    // degrees
    MOON_ORBIT_TILT_ECLIPTIC: 5.145,           // degrees
    MOON_ORBIT_ECCENTRICITY: 0.0549,           // ?

    SUN_RADIUS: 109,                           // earth radii
    SUN_SPIN_PERIOD_EQUATOR: 25,               // earth days
    SUN_SPIN_PERIOD_POLES: 27,                 // earth days
    SUN_AXIAL_TILT: 7.5                        // degrees
};

var SCALED = {

    EARTH_RADIUS: 0.3,                         // earth radii
    EARTH_ORBIT_RADIUS: 4,                     // earth radii
    EARTH_SPIN_PERIOD: 1,                      // earth days
    EARTH_ORBIT_PERIOD: 0.1,                   // earth days
    EARTH_AXIAL_TILT: REAL.EARTH_AXIAL_TILT,   // degrees
    EARTH_ORBIT_ECCENTRICITY: REAL.EARTH_ORBIT_ECCENTRICITY, // ?
    EARTH_SCALE: 1,

    MOON_RADIUS: 0.3 * REAL.MOON_RADIUS,       // earth radii
    MOON_ORBIT_RADIUS: 1,                      // earth radii
    MOON_ORBIT_PERIOD: 1,                      // earth days
    MOON_SPIN_PERIOD: 1,                       // earth days
    MOON_AXIAL_TILT: REAL.MOON_AXIAL_TILT,     // degrees
    MOON_ORBIT_TILT_ECLIPTIC:REAL.MOON_ORBIT_TILT_ECLIPTIC,// degrees
    MOON_ORBIT_ECCENTRICITY: REAL.MOON_ORBIT_ECCENTRICITY, // ?
    MOON_SCALE: 1,

    SUN_RADIUS: 1,                              // earth radii
    SUN_SPIN_PERIOD: 1,                         // earth days
    SUN_AXIAL_TILT: REAL.SUN_AXIAL_TILT,        // degrees
    SUN_SCALE: 1,

    CLOUD_SIZE: 0.31,
    CLOUD_OPACITY: 0.3
};

const FILES = {
    SUN_MAP: 'images/sunmap.jpg',
    MOON_MAP: 'images/moonmap1k.jpg',
    EARTH_MAP: 'images/earthmap1k.jpg',
    EARTH_BUMP: 'images/earthbump1k.jpg',
    EARTH_SPECULAR: 'images/earthspec1k.jpg',
    EARTH_CLOUD: 'images/earthcloudmap.jpg'
};

const MATERIALS = {
    BLUE: new THREE.LineBasicMaterial({color: 0x0000ff}),
    BLACK: new THREE.LineBasicMaterial({color: 0xffffff}),
    RED: new THREE.LineBasicMaterial({color: 0xff0000}),
    GREEN: new THREE.LineBasicMaterial({color: 0x00ff00})
};




