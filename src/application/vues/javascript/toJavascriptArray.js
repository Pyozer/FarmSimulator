/** Transforme les coordonnés java vers Javascript array **/
function toJavascriptArray(coords) {
    var path = [];
    var coordsParse = JSON.parse(coords);

    coordsParse.forEach(function(point){
        var split = point.toString().split(",");
        path.push(new google.maps.LatLng(split[0], split[1]));

    });

    return path;
}