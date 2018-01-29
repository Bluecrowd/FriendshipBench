//https://hpa-api.figueus.com/api/benches
var loc;

function setLoc() {
  var ourRequest = new XMLHttpRequest();
  ourRequest.open('GET', 'https://storage.googleapis.com/mapsdevsite/json/google.json', true);
  ourRequest.onload = function () {
    var ourData = ourRequest.responseText;
    console.log(ourData.features[0]);
    //loc = ourData[1].foods.dislikes[0];

  };

  ourRequest.send();
}

function initMap() {
  var uluru1 = {lat: -17.8258702, lng: 31.05069354};
  var uluru2 = {lat: -20.0806978, lng: 30.8419189};
  var uluru3 = {lat: -20.15326471, lng: 28.58275222};
  var text;
  var geocoder = new google.maps.Geocoder();

  //var geocode = {lat: latitude, lng: longitude};

  var map = new google.maps.Map(document.getElementById('map'), {
    zoom: 2,
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


  loc1 = "Groningen";
  loc2 = "Assen";
  loc3 = "Leeuwarden";
  geocodeAddress(geocoder, map, loc1);
  geocodeAddress(geocoder, map, loc2);
  geocodeAddress(geocoder, map, loc3);

  document.getElementById('submit').addEventListener('click', function () {
    var location = document.getElementById('address').value;
    geocodeAddress(geocoder, map, location);
  });

  var script = document.createElement('script');
  // This example uses a local copy of the GeoJSON stored at
  // http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/2.5_week.geojsonp
  script.src = 'https://storage.googleapis.com/mapsdevsite/json/google.json';
  document.getElementsByTagName('head')[0].appendChild(script);

  var show = function(results) {
    for (var i = 0; i < results.features.length; i++) {
      var coords = results.features[i].geometry.coordinates;
      var latLng = new google.maps.LatLng(coords[1],coords[0]);
      var marker = new google.maps.Marker({
        position: latLng,
        map: map
      });
    }
  }
}



function geocodeAddress(geocoder, resultsMap, loc) {
  var address = loc;//document.getElementById('address').value;
  geocoder.geocode({'address': address}, function(results, status) {
    if (status === 'OK') {
      resultsMap.setCenter(results[0].geometry.location);
      var marker = new google.maps.Marker({
        map: resultsMap,
        position: results[0].geometry.location
      });
    } else {
      alert('Geocode was not successful for the following reason: ' + status);
    }
  });
}

