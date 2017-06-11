function getPolygonCenter(polygon) {
    var bounds = new google.maps.LatLngBounds();
    var vertices = polygon.getPath();
    for (var i = 0; i < vertices.getLength(); i++) {
        bounds.extend(vertices.getAt(i));
    }
    return bounds.getCenter();
}