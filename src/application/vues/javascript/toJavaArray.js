/** Transforme les coordonnés d'un polygon en String d'un JSONArray Java **/
function toJavaArray(polygon) {
    var javaArray = "[";

    var vertices = polygon.getPath();
    var length = vertices.getLength();

    vertices.forEach(function(xy, i) {
        javaArray += "[" + xy.lat() + ',' + xy.lng() + "]";
        if(i < length - 1)
            javaArray += ",";
    });
    javaArray += "]";

    return javaArray;
}