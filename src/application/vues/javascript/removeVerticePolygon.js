/** Supprime une arrête du polygon dessiné **/

var deleteNode = function deleteNode(mev) {
    if (mev.vertex != null) {
        polygon.getPath().removeAt(mev.vertex);
    }
    if(polygon.getPath().getLength() < 2) {
        polygon.setMap(null);
        drawingManager.setDrawingMode(google.maps.drawing.OverlayType.POLYGON);
        drawingManager.setOptions({
           drawingControl: true
        });
    }
}