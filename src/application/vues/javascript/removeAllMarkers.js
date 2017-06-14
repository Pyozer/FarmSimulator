 /** Cache tous les markers **/
function removeAllMarkers() {
    if(infowindow) {
        infowindow.close();
    }

    for (var i = 0; i < markers.length; i++)
         markers[i].setMap(null);
     markers = [];
}