
function initMap() {
  var uluru1 = {lat: -17.8258702, lng: 31.05069354};
  var uluru2 = {lat: -20.0806978, lng: 30.8419189};
  var uluru3 = {lat: -20.15326471, lng: 28.58275222};
  var map = new google.maps.Map(document.getElementById('map'), {
    zoom: 6,
    center: uluru1
  });
  var marker1 = new google.maps.Marker({
    position: uluru1,
    map: map
  });
  var marker2 = new google.maps.Marker({
    position: uluru2,
    map: map
  });
  var marker3 = new google.maps.Marker({
    position: uluru3,
    map: map
  });
}
