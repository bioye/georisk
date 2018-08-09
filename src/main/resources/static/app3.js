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
            
            //$("#location").text("Current Location: " + body.longi+"," + body.lati+"," + body.risk);
            $("#location").text("Current Location: " + e.data);
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