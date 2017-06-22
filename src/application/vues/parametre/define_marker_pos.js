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

    jsInterface.askToLoadData();

    // Create the search box and link it to the UI element.
    var input = document.getElementById('pac-input');
    var searchBox = new google.maps.places.SearchBox(input);
    map.controls[google.maps.ControlPosition.TOP_LEFT].push(input);

    // Bias the SearchBox results towards current map's viewport.
    map.addListener('bounds_changed', function() {
      searchBox.setBounds(map.getBounds());
    });

    var markers = [];
    // Listen for the event fired when the user selects a prediction and retrieve
    // more details for that place.
    searchBox.addListener('places_changed', function() {
      var places = searchBox.getPlaces();

      if (places.length == 0) {
        return;
      }

      // Clear out the old markers.
      markers.forEach(function(marker) {
        marker.setMap(null);
      });
      markers = [];

      // For each place, get the icon, name and location.
      var bounds = new google.maps.LatLngBounds();
      places.forEach(function(place) {
        if (!place.geometry) {
          console.log("Returned place contains no geometry");
          return;
        }
        var icon = {
          url: place.icon,
          size: new google.maps.Size(71, 71),
          origin: new google.maps.Point(0, 0),
          anchor: new google.maps.Point(17, 34),
          scaledSize: new google.maps.Size(25, 25)
        };

        // Create a marker for each place.
        markers.push(new google.maps.Marker({
          map: map,
          icon: icon,
          title: place.name,
          position: place.geometry.location
        }));

        if (place.geometry.viewport) {
          // Only geocodes have viewport.
          bounds.union(place.geometry.viewport);
        } else {
          bounds.extend(place.geometry.location);
        }
      });
      map.fitBounds(bounds);
    });
}

/** Ajouter un marker à la Map **/
function addMarker(id, latitude, longitude, title, type, etat) {

    var marker = new google.maps.Marker({
        position: new google.maps.LatLng(latitude, longitude),
        map: map,
        id: id,
        animation: google.maps.Animation.DROP,
        draggable: true
    });

    marker.setMap(map);
    map.setCenter(marker.getPosition());

    marker.addListener('rightclick', function(e) {
        marker.setMap(null);
        jsInterface.setMarkerEdited("");

        drawingManager.setDrawingMode(google.maps.drawing.OverlayType.MARKER);
        drawingManager.setOptions({
           drawingControl: true
        });
    });

    google.maps.event.addListener(marker, 'dragend', function (e) {
        map.setCenter(marker.getPosition());
        jsInterface.setMarkerEdited(toJavaArrayMarker(marker));
    });

    drawingManager.setDrawingMode(null);
    drawingManager.setOptions({
        drawingControl: false
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