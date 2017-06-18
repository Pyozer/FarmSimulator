var map;
var map_center_pos;
var infowindow;
var markerOrigin;

var champsSelected;
var champs = [];

function defineMapCenter(lat, long) {
    map_center_pos = new google.maps.LatLng(lat, long); // Correspond au coordonnées de l'ETA
}

function initMap() {

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

    jsInterface.askToLoadChamps(); // On demande à Java de setup les champs

    // Rayon de 20km autour du point de départ
    createCircle(map, map_center_pos);

    map.addListener("click", function(){
        for (var i = 0; i < champs.length; i++)
            champs[i].setOptions({
                strokeWeight: 2,
                strokeColor: champs[i].couleur,
                fillColor: champs[i].couleur
            });
        champsSelected = null;
        infowindow.close();
    });

    google.maps.event.addListener(map, "tilesloaded", function() {
        document.getElementById("loader_content").style.display = "none";
    });
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
      couleur: couleur
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
        if(champsSelected != null)
            for (var i = 0; i < champs.length; i++)
                champs[i].setOptions({
                    strokeWeight: 2,
                    strokeColor: champs[i].couleur,
                    fillColor: champs[i].couleur
                });

        champsSelected = polygon.id;
        polygon.setOptions(selectedStyle);

        var contentString = '<strong>INFORMATIONS</strong><br /><br />' +
        'Culture: ' + polygon.culture + '<br />' +
        'Propriétaire: ' + polygon.proprio + '<br />' +
        'Adresse: ' + polygon.adresse + '<br />' +
        'Surface: ' + polygon.surface + ' m²';

        infowindow.setContent(contentString);
        infowindow.setPosition(getPolygonCenter(polygon));
        infowindow.open(map);
    });

    polygon.addListener("mouseover", function(){
        var idChamps = polygon.id;
        if(idChamps != champsSelected)
            polygon.setOptions(selectedOverStyle);
    });

    polygon.addListener("mouseout", function(){
        var idChamps = polygon.id;
        if(idChamps != champsSelected)
            polygon.setOptions(originalStyle);
        else
            polygon.setOptions(selectedStyle);
    });

    champs.push(polygon);
}