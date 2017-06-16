var map;
var drawingManager;
var geocoder;

function initMap() {

    map = new google.maps.Map(document.getElementById('map'), {
        zoom: 14, // Zoom par défaut
        streetViewControl: false, // On désactive le streetview
        mapTypeId: google.maps.MapTypeId.ROADMAP // Type de carte ( HYBRID, ROADMAP, SATELLITE ou TERRAIN )
    });

    geocoder = new google.maps.Geocoder();
    zoomToFrance();

    drawingManager = new google.maps.drawing.DrawingManager({
        drawingMode: google.maps.drawing.OverlayType.MARKER,
        drawingControlOptions: { drawingModes: [google.maps.drawing.OverlayType.MARKER] },
        drawingControl: true
    });
    drawingManager.setMap(map);

    google.maps.event.addListener(drawingManager, 'markercomplete', function(marker) {

        marker.setOptions({
            draggable: true
        });

        google.maps.event.addListener(marker, 'dragend', function (e) {
            map.setCenter(marker.getPosition());
            jsInterface.setMarkerEdited(toJavaArrayMarker(marker));
        });

        drawingManager.setDrawingMode(null);
        drawingManager.setOptions({
            drawingControl: false
        });

        map.setCenter(marker.getPosition());
        map.setZoom(map.getZoom() + 1);

        google.maps.event.addListener(marker, 'rightclick', function(e) {

            marker.setMap(null);
            jsInterface.setMarkerEdited("");

            drawingManager.setDrawingMode(google.maps.drawing.OverlayType.MARKER);
            drawingManager.setOptions({
               drawingControl: true
            });
        });

        jsInterface.setMarkerEdited(toJavaArrayMarker(marker));

    });

    google.maps.event.addListener(map, "tilesloaded", function() {
        document.getElementById("loader_content").style.display = "none";
    });
}

function toJavaArrayMarker(marker) {

    var xy = marker.getPosition();

    var javaArray = "[" + xy.lat() + ',' + xy.lng() + "]";

    return javaArray;
}

function zoomToFrance() {
    geocoder.geocode( { 'address': 'France'}, function(results, status) {
        if (status == google.maps.GeocoderStatus.OK) {
            map.setCenter(results[0].geometry.location);
            map.fitBounds(results[0].geometry.viewport);
        }
    });
}