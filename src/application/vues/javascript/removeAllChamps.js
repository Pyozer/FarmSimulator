 /** Cache tous les champs **/
function removeAllChamps() {
    if(infowindow) {
        infowindow.close();
    }

    for (var i = 0; i < champs.length; i++)
        champs[i].setMap(null);
    champs = [];
}