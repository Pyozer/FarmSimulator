var map;
var map_center_pos;
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
var oms;

var byFlight = true;

function initMap() {

    //map_center_pos = new google.maps.LatLng(jsInterface.getPosEtaX(), jsInterface.getPosEtaY()); // Correspond au coordonnées de l'ETA
    map_center_pos = new google.maps.LatLng(47.96959919884257,-1.4487806226730346); // Correspond au coordonnées de l'ETA

    map = new google.maps.Map(document.getElementById('map'), {
        zoom: 12, // Zoom par défaut
        center: map_center_pos, // Coordonnées de départ de la carte de type latLng
        streetViewControl: false, // On désactive le streetview
        mapTypeId: google.maps.MapTypeId.ROADMAP // Type de carte ( HYBRID, ROADMAP, SATELLITE ou TERRAIN )
    });

    var trafficLayer = new google.maps.TrafficLayer(); // Ajout des données traffic
    trafficLayer.setMap(map);

    infowindow = new google.maps.InfoWindow();

    markerOrigin = createMarker(map, map_center_pos);

    directionsDisplay = new google.maps.DirectionsRenderer({
        map: map,
        suppressMarkers: true,
        polylineOptions: { strokeColor: "#1897cd", strokeOpacity: 0.6, strokeWeight: 5 }
    });
    directionsDisplay.setPanel(document.getElementById('road'));

    directionsDisplay.addListener('directions_changed', function() {
        refresh();
    });

    oms = new OverlappingMarkerSpiderfier(map, {
            markersWontMove: true,
            markersWontHide: true,
            basicFormatEvents: true,
            keepSpiderfied: true
        });

    createCircle(map, map_center_pos);

    map.addListener("click", function() {
        hideFlightPath();
        flightPlanCoordinates = [];
        for (var i = 0; i < champs.length; i++)
            champs[i].setOptions({
                strokeWeight: 2,
                strokeColor: champs[i].couleur,
                fillColor: champs[i].couleur
            });

        champsSelected = [0, 0];
        destSelect = false;
        originSelected = false;

        directionsDisplay.setMap(null);
        document.getElementById("road").style.display = "none";
        document.getElementById("map").className = "fullWidth";

        jsInterface.setFirstChamp("");
        jsInterface.setSecondChamp("");
    });

    google.maps.event.addListener(map, "tilesloaded", function() {
        document.getElementById("loader_content").style.display = "none";
    });

    jsInterface.askToLoadData(); // On demande à Java de setup les champs et véhicules

    byFlight = jsInterface.isToggleButtonSelected();
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
        marker.title + '<br />' +
        'Etat: ' + marker.etat + '<br />';

        infowindow.setContent(contentString);
        infowindow.open(map, marker);
    });
    markers.push(marker);

    oms.addMarker(marker);  // adds the marker to the spiderfier _and_ the map
}

/** Supprime tous les markers sauf un **/
function hideAllExceptOne(id) {
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
    for (var i = 0; i < markers.length; i++) {
        markers[i].setMap(map);
    }
}

/** Ajouter un champ à la Map **/
function addChamp(id, culture, proprio, id_proprio, adresse, surface, coords, couleur) {

    var polygon = new google.maps.Polygon({
      paths: toJavascriptArray(coords),
      id: id,
      culture: culture,
      proprio: proprio,
      id_proprio: id_proprio,
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

        flightPlanCoordinates[0] = getPolygonCenter(polygon);
        if (directionsDisplay != null) {
            directionsDisplay.setMap(null);
        }

        polygon.setOptions(selectedStyle);

        checkIfItinerary();
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
        if (directionsDisplay != null) {
            directionsDisplay.setMap(null);
        }

        polygon.setOptions(selectedStyle);

        checkIfItinerary();
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

function checkIfItinerary() {
    hideFlightPath();
    if (originSelect && destSelect) { // Si origin et dest sélectionnés
        if(byFlight)
            calcFlightPath(flightPlanCoordinates);
    }
}
function hideFlightPath() {
    if (flightPath != null) {
        flightPath.setMap(null);
    }
    infowindow.close();
}

/** Calcule un itinéraire entre 2 points **/
function calculate(origin, destination) {
    if (origin && destination) {
        var request = {
            origin: origin,
            destination: destination,
            travelMode: google.maps.TravelMode.DRIVING,
            region: 'FR',
            optimizeWaypoints: true,
            avoidHighways: true,
            avoidTolls: true,
            unitSystem: google.maps.UnitSystem.METRIC,
            provideRouteAlternatives: true
        };
        var directionsService = new google.maps.DirectionsService(); // Service de calcul d'itinéraire
        directionsService.route(request, function(response, status) { // Envoie de la requête pour calculer le parcours
            if (status == 'OK') {
                if(!byFlight)
                    infowindow.close();
                else {
                    var paths = response.routes[0].overview_path;
                    flightPlanCoordinates[0] = paths[0];
                    flightPlanCoordinates[1] = paths[paths.length - 1];
                    calcFlightPath(flightPlanCoordinates);
                }

                directionsDisplay.setMap(map);
                directionsDisplay.setDirections(response); // Trace l'itinéraire sur la carte et les différentes étapes du parcours
                document.getElementById("road").style.display = "block";
                document.getElementById("map").className = "dividedWidth";
            }
        });
    }
};

/** Récupère le centre entre des points **/
function getLineCenter(linePoints) {
    var bounds = new google.maps.LatLngBounds();
    for (var i = 0; i < linePoints.length; i++) {
        bounds.extend(linePoints[i]);
    }
    return bounds.getCenter();
}

/** Met une ligne entre 2 points **/
function calcFlightPath(coordinates) {
    hideFlightPath();

    var lineSymbol = {
        path: google.maps.SymbolPath.FORWARD_CLOSED_ARROW,
        scale: 4,
        strokeColor: '#FF0000'
      };

    flightPath = new google.maps.Polyline({
        path: coordinates,
        geodesic: true,
        strokeColor: '#FF0000',
        strokeOpacity: 1.0,
        strokeWeight: 2,
        icons: [{
          icon: lineSymbol,
          offset: '100%'
        }],
    });
    flightPath.setMap(map);

    showDist(coordinates);

    animateArrow(flightPath);
}

function animateArrow(line) {
    var count = 0;
    window.setInterval(function() {
        count = (count + 1) % 100;

        var icons = line.get('icons');
        icons[0].offset = count + '%';
        line.set('icons', icons);
    }, 20);
}

/** Affiche l'infowindow sur la ligne **/
function showDist(coordinates) {
    var dist = Math.round(google.maps.geometry.spherical.computeDistanceBetween(coordinates[0], coordinates[1])); // En mètres
    var distStr = dist + "";
    infowindow.setContent("<strong>" + (Math.round(dist / 1000)) + " km " + distStr.substring(distStr.length - 3) + " mètres</strong>");
    infowindow.setPosition(getLineCenter(coordinates));
    infowindow.open(map);
}

function enableFlightItinerary() {
    byFlight = true;
}
function disableFlightItinerary() {
    byFlight = false;
}