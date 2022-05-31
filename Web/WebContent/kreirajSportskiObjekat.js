
$(document).ready(function() {
	var map = new ol.Map({
	        target: 'map',
	        layers: [
	          new ol.layer.Tile({
	            source: new ol.source.OSM()
	          })
	        ],
	        view: new ol.View({
	          center: ol.proj.fromLonLat([20.457273, 44.78]),
	          zoom: 6
	        })
	      });
      let vectorLayer = new ol.layer.Vector({
    	    source: new ol.source.Vector(),
    	});

    	map.addLayer(vectorLayer);
   

    	map.on('click', function (evt) {
			
    	    let marker = new ol.Feature(new ol.geom.Point(evt.coordinate));
    	    marker.setStyle(
    	        new ol.style.Style({
    	            image: new ol.style.Circle({
    	                radius: 10,
    	                fill: new ol.style.Fill({color: 'red'})
    	            })
    	        })
    	 );
		vectorLayer.getSource().addFeature(marker);
	});
});