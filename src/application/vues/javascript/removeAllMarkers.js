 /** Cache tous les markers **/
function removeAllMarkers() {
    infowindow.close();

    for (var i = 0; i < markers.length; i++)
         markers[i].setMap(null);
     markers = [];
     markerCluster.clearMarkers();
}