var map;
var map_center_pos;
var polygon;
var drawingManager;

function initMap() {

    map_center_pos = new google.maps.LatLng(jsInterface.getPosEtaX(), jsInterface.getPosEtaY()); // Correspond au coordonnées de l'ETA

    map = new google.maps.Map(document.getElementById('map'), {
        zoom: 14, // Zoom par défaut
        center: map_center_pos, // Coordonnées de départ de la carte de type latLng
        streetViewControl: false, // On désactive le streetview
        mapTypeId: google.maps.MapTypeId.ROADMAP // Type de carte ( HYBRID, ROADMAP, SATELLITE ou TERRAIN )
    });

    drawingManager = new google.maps.drawing.DrawingManager({
        drawingMode: google.maps.drawing.OverlayType.POLYGON,
        drawingControlOptions: { drawingModes: [google.maps.drawing.OverlayType.POLYGON] },
        drawingControl: true,
        polygonOptions: { editable: true }
    });
    drawingManager.setMap(map);

    google.maps.event.addListener(drawingManager, 'overlaycomplete', function(e) {
        drawingManager.setDrawingMode(null);
        drawingManager.setOptions({
            drawingControl: false
        });

        polygon = e.overlay;
        polygon.type = e.type;

        console.log(polygon);

        google.maps.event.addListener(polygon, 'rightclick', deleteNode);
        jsInterface.setPolygonEdited(toJavaArray(polygon));
    });

    google.maps.event.addListener(map, "tilesloaded", function() {
        document.getElementById("loader_content").style.display = "none";
    });

    jsInterface.askToLoadChamps(); // On demande à Java de setup le champ
}

/** Ajouter un champ à la Map **/
function addChamp(id, culture, proprio, id_proprio, adresse, surface, coords, couleur) {

    polygon = new google.maps.Polygon({
      paths: toJavascriptArray(coords),
      id: id,
      strokeWeight: 3,
      strokeColor: '#000000',
      fillColor: '#333333',
      editable: true
    });

    polygon.setMap(map);
    map.setCenter(getPolygonCenter(polygon));

    polygon.addListener('rightclick', deleteNode);

    polygon.getPaths().forEach(function(path, index){
        google.maps.event.addListener(path, 'insert_at', function(){
            jsInterface.setPolygonEdited(toJavaArray(polygon));
        });

        google.maps.event.addListener(path, 'remove_at', function(){
            jsInterface.setPolygonEdited(toJavaArray(polygon));
        });

        google.maps.event.addListener(path, 'set_at', function(){
            jsInterface.setPolygonEdited(toJavaArray(polygon));
        });
    });

    fitToPolygon(polygon);

    drawingManager.setDrawingMode(null);
    drawingManager.setOptions({
        drawingControl: false
    });
}

function fitToPolygon(polygon) {
    var bounds = new google.maps.LatLngBounds();
    var vertices = polygon.getPath();
    for (var i = 0; i < vertices.getLength(); i++) {
        bounds.extend(vertices.getAt(i));
    }
    map.fitBounds(bounds);
}