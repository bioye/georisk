$(document).ready(function () {

    function createEventSource() {
        var eventSource = new EventSource("http://localhost:8080/locations");
        var myStyle = {
            "color": "#ff7800",
            "weight": 5,
            "opacity": 0.65
        };
        eventSource.addEventListener('message', function (e) {
            const body = JSON.parse(e.data);
            
            $("#location").text("Current Location: " + body.longi+"," + body.lati+"," + body.risk);
            let riskColor = 'red';
            switch(body.risk){
            case 1:
            	riskColor = 'red';
            	break;
            case 2:
            	riskColor = 'green';
            	break;
            case 3:
            	riskColor = 'orange';
            	break;
            case 4:
            	riskColor = 'yellow';
            	break;
            }
            var circle = L.circle([body.longi, body.lati], {
                color: riskColor,
                fillColor: '#f03',
                fillOpacity: 0.5,
                radius: 500
            }).addTo(mymap);
        }, false);
        return eventSource;
    }

    var eventSource;

    $("#start").click(function() {
        if (eventSource) {
            eventSource.close();
        }
        eventSource = createEventSource();
    });

    $("#cancel").click(function() {
        eventSource.close();
    });
});