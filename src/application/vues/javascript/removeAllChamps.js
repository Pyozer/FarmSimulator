 /** Cache tous les champs **/
function removeAllChamps() {
    infowindow.close();

    for (var i = 0; i < champs.length; i++)
        champs[i].setMap(null);
    champs = [];
}