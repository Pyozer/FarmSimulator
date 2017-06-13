var map;
var directionsDisplay;
var infowindow;
var markerOrigin;

var flightPath;
var flightPlanCoordinates = [];
var champsSelected = [0,0];
var originSelect = false;
var destSelect = false;

var champs = [];
var markers = [];
var markerCluster;

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

    directionsDisplay = new google.maps.DirectionsRenderer({
        map: map
    });
    directionsDisplay.setPanel(document.getElementById('road'));

    markerCluster = new MarkerClusterer(map, [], {imagePath: 'https://developers.google.com/maps/documentation/javascript/examples/markerclusterer/m'});

    // Rayon de 20km autour du point de départ
    createCircle(map, latLng);

    map.addListener("click", function() {
        if (flightPath != null) {
            flightPath.setMap(null);
        }
        flightPlanCoordinates = [];
        for (var i = 0; i < champs.length; i++)
            champs[i].setOptions({
                strokeWeight: 2,
                strokeColor: champs[i].couleur,
                fillColor: champs[i].couleur
            });

        champsSelected = [0, 0];
        infowindow.close();
        destSelect = false;
        originSelected = false;

        directionsDisplay.setMap(null);
        document.getElementById('road').style.display = 'none';
        document.getElementById("map").className = "fullWidth";

        jsInterface.setFirstChamp("");
        jsInterface.setSecondChamp("");
    });

    jsInterface.askToLoadData(); // On demande à Java de setup les champs et véhicules
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

    marker.addListener('click', function(event) {
        var contentString = '<strong>INFORMATIONS</strong><br /><br />' +
        'Type: ' + marker.type + '<br />' +
        'Etat: ' + marker.etat + '<br />';

        infowindow.setContent(contentString);
        infowindow.open(map, marker);
    });
    markers.push(marker);
    markerCluster.addMarker(marker);
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

/** Réaffiche tous les markers **/
function showAll() {
    markerCluster.clearMarkers();
    for (var i = 0; i < markers.length; i++) {
        markers[i].setMap(map);
        markerCluster.addMarker(markers[i]);
    }
}

/** Ajouter un champ à la Map **/
function addChamp(id, culture, proprio, adresse, surface, coords, couleur) {

    var polygon = new google.maps.Polygon({
      paths: toJavascriptArray(coords),
      id: id,
      culture: culture,
      proprio: proprio,
      adresse: adresse,
      surface: surface,
      couleur
    });

    var selectedStyle = {
            strokeWeight: 2,
            strokeColor: '#FF0000',
            fillColor: '#FF0000'
    };
    var selectedOverStyle = {
            strokeWeight: 3,
            strokeColor: '#FF0000',
            fillColor: '#FF0000'
        };
    var originalStyle = {
            strokeWeight: 2,
            strokeColor: couleur,
            fillColor: couleur
        };

    polygon.setOptions(originalStyle);
    polygon.setMap(map);

    polygon.addListener('click', function(event) {

        jsInterface.setFirstChamp(getPolygonCenter(polygon));

        var contentString = '<strong>INFORMATIONS</strong><br /><br />' +
            'Culture: ' + polygon.culture + '<br />' +
            'Propriétaire: ' + polygon.proprio + '<br />' +
            'Adresse: ' + polygon.adresse + '<br />' +
            'Surface: ' + polygon.surface + ' m²';

        polygon.setOptions(selectedStyle);

        infowindow.setContent(contentString);
        infowindow.setPosition(getPolygonCenter(polygon));
        infowindow.open(map);
    });

    polygon.addListener('click', function(event) {

        champsSelected[0] = polygon.id;
        originSelect = true;

        for (var i = 0; i < champs.length; i++)
            if(champs[i].id != champsSelected[1])
                champs[i].setOptions({
                    strokeWeight: 2,
                    strokeColor: champs[i].couleur,
                    fillColor: champs[i].couleur
                });

        if (flightPath != null) {
            flightPath.setMap(null);
        }
        flightPlanCoordinates[0] = getPolygonCenter(polygon);

        polygon.setOptions(selectedStyle);

        if (originSelect && destSelect) { // Si origin et dest sélectionnés
            calcFlightPath(flightPlanCoordinates);
            showDist(flightPlanCoordinates);
        }
    });

    polygon.addListener('rightclick', function(event) {
        jsInterface.setSecondChamp(getPolygonCenter(polygon));

        champsSelected[1] = polygon.id;
        destSelect = true;

        for (var i = 0; i < champs.length; i++)
            if(champs[i].id != champsSelected[0])
                champs[i].setOptions({
                    strokeWeight: 2,
                    strokeColor: champs[i].couleur,
                    fillColor: champs[i].couleur
                });

        if (flightPath != null) {
            flightPath.setMap(null);
        }
        flightPlanCoordinates[1] = getPolygonCenter(polygon);

        polygon.setOptions(selectedStyle);

        if (originSelect && destSelect) { // Si origin et dest sélectionnés
            calcFlightPath(flightPlanCoordinates);
            showDist(flightPlanCoordinates);
        }
    });

    polygon.addListener("mouseover", function(){
        polygon.setOptions(selectedOverStyle);
    });

    polygon.addListener("mouseout", function(){
        var idChamps = polygon.id;
        if(idChamps == champsSelected[0] || idChamps == champsSelected[1])
            polygon.setOptions(selectedStyle);
        else
            polygon.setOptions(originalStyle);
    });

    champs.push(polygon);

}

/** Calcule un itinéraire entre 2 points **/
function calculate(origin, destination) {
    if (origin && destination) {
        var request = {
            origin: origin,
            destination: destination,
            travelMode: google.maps.TravelMode.DRIVING,
            drivingOptions: {
                departureTime: new Date(Date.now()),  // for the time N milliseconds from now.
                trafficModel: "optimistic"
            },
            region: 'FR',
            optimizeWaypoints: true,
            avoidHighways: true,
            avoidTolls: true,
            unitSystem: google.maps.UnitSystem.METRIC
        };
        var directionsService = new google.maps.DirectionsService(); // Service de calcul d'itinéraire
        directionsService.route(request, function(response, status) { // Envoie de la requête pour calculer le parcours
            if (status == 'OK') {
                directionsDisplay.setMap(map);
                directionsDisplay.setDirections(response); // Trace l'itinéraire sur la carte et les différentes étapes du parcours
                document.getElementById('road').style.display = 'block';
                document.getElementById("map").className = "dividedWidth";
            }
        });
    }
};

/** Récupère le centre entre 2 point **/
function getLineCenter(linePoints) {
    var bounds = new google.maps.LatLngBounds();
    for (var i = 0; i < linePoints.length; i++) {
        bounds.extend(linePoints[i]);
    }
    return bounds.getCenter();
}

/** Met une ligne entre 2 points **/
function calcFlightPath(coordinates) {
    flightPath = new google.maps.Polyline({
        path: coordinates,
        geodesic: true,
        strokeColor: '#FF0000',
        strokeOpacity: 1.0,
        strokeWeight: 2
    });
    flightPath.setMap(map);
}

/** Affiche l'infowindow sur la ligne **/
function showDist(coordinates) {
    var dist = Math.round(google.maps.geometry.spherical.computeDistanceBetween(flightPlanCoordinates[0], flightPlanCoordinates[1])); // En mètres
    var distStr = dist + "";
    infowindow.setContent("<strong>" + (Math.round(dist / 1000)) + " km " + distStr.substring(distStr.length - 3) + " mètres</strong>");
    infowindow.setPosition(getLineCenter(flightPlanCoordinates));
    infowindow.open(map);
}