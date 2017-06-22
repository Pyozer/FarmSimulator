// Rayon de 20km autour du point de départ
function createMarker(map, latLng) {
    var markerOrigin = new google.maps.Marker({
        position: latLng,
        icon: icons['Eta'].icon,
        map: map,
        animation: google.maps.Animation.DROP,
        title: jsInterface.getEtaNom()
    });

    markerOrigin.addListener('click', function(event) {
        var contentString = '<span class="label"><strong>' + markerOrigin.title + '</strong></span>';

        infowindow.setContent(contentString);
        infowindow.open(map, markerOrigin);
    });

    return markerOrigin;
}
