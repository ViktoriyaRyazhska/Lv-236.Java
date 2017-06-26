/**
 * Created by User on 26.06.2017.
 */
function showDetailModal(id) {
    var detailId = "detail"+id;
    var detailModal = document.getElementById(detailId);
    detailModal.style.display = "block";

}

function removeDetailModal(id) {
    var detailId = "detail"+id;
    var detailModal = document.getElementById(detailId);
    detailModal.style.display = "none";
}
