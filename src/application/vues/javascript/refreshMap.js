/** Refresh la map **/
function refresh() {
    map.setZoom(map.getZoom() - 1);
    map.setZoom(map.getZoom() + 1);
}