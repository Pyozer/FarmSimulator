// Rayon de 20km autour du point de départ
function createMarker(map, latLng) {

    return new google.maps.Marker({
        position: latLng,
        icon: icons['Eta'].icon,
        map: map,
        animation: google.maps.Animation.DROP,
        title: 'Les rivières, Janzé'
    });
}
