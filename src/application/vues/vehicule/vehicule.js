var map;
var infowindow;
var markerOrigin;
var markers = [];
var markerCluster;
var oms;

function initMap() {
    var latLng = new google.maps.LatLng(47.970787, -1.448450); // Correspond au coordonnées de Les rivière, 35000 Janzé

    map = new google.maps.Map(document.getElementById('map'), {
        zoom: 12, // Zoom par défaut
        center: latLng, // Coordonnées de départ de la carte de type latLng
        streetViewControl: false, // On désactive le streetview
        mapTypeId: google.maps.MapTypeId.ROADMAP // Type de carte ( HYBRID, ROADMAP, SATELLITE ou TERRAIN )
    });

    var trafficLayer = new google.maps.TrafficLayer(); // Ajout des données traffic
    trafficLayer.setMap(map);

    infowindow = new google.maps.InfoWindow();

    markerOrigin = createMarker(map, latLng);

    markerCluster = new MarkerClusterer(map, [], {imagePath: 'https://developers.google.com/maps/documentation/javascript/examples/markerclusterer/m'});

    oms = new OverlappingMarkerSpiderfier(map, {
        markersWontMove: true,
        markersWontHide: true,
        basicFormatEvents: true
    });

    // Rayon de 20km autour du point de départ
    createCircle(map, latLng);

    map.addListener('click', function(event) {
        infowindow.close();
    });

    jsInterface.askToLoadMarkers(); // On demande à Java de setup les véhicules
}

/** Ajouter un marker à la Map **/
function addMarker(id, latitude, longitude, title, type, etat) {

    var marker = new google.maps.Marker({
        position: new google.maps.LatLng(latitude, longitude),
        icon: icons[type].icon,
        map: map,
        id: id,
        title: title,
        type: type,
        etat: etat,
        animation: google.maps.Animation.DROP
    });

    var contentString = '<span class="label"><strong>INFORMATIONS</strong></span><br /><br />' +
            'Type: ' + marker.type + '<br />' +
            'Etat: ' + marker.etat;

    marker.addListener('click', function(event) {
        infowindow.setContent(contentString);
        infowindow.open(map, marker);
    });

    oms.addMarker(marker);  // adds the marker to the spiderfier _and_ the map

    markers.push(marker);
    //markerCluster.addMarker(marker);
}

/** Supprime tous les markers sauf un **/
function hideAllExceptOne(id) {
    markerCluster.clearMarkers();

    for (var i = 0; i < markers.length; i++) {
        if(markers[i].id != id) {
            markers[i].setMap(null);
        } else {
            markers[i].setMap(map);
            map.panTo(markers[i].position);
        }
    }
    refresh();
}