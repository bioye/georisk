$(document).ready(function () {

    function createEventSource() {
        /*var eventSource;
        if (!!window.EventSource) {
        	eventSource = new EventSource('locations');
        } else {
            print("YOUR BROWSER DOES NOT SUPPORT SSE");
        }*/
        var eventSource = new EventSource("locations");
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
            	riskColor = 'yellow';//
            	break;
            }
            var circle = L.circle([body.longi, body.lati], {
                color: riskColor,
                fillColor: riskColor,
                fillOpacity: 0.5,
                radius: 100
            }).addTo(mymap);
        }, {passive: false});
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
