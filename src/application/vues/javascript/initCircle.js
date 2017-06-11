// Rayon de 20km autour du point de départ
function createCircle(map, latLng) {
    var cityCircle = new google.maps.Circle({
        strokeColor: '#FF0000',
        strokeOpacity: 0.7,
        strokeWeight: 2,
        fillColor: '#FF0000',
        fillOpacity: 0.03,
        map: map,
        center: latLng,
        radius: 20 * 1000,
        clickable: false
    });
}
