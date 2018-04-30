var MapPopulate = (function(map){
    "use strict";
    var Tournaments = new Array();
    var HostSchools = new Array();
    var Markers = new Array();
    var Routes = new Array();
    var Distances = new Array();
    var selectedTournament = null;
    var selectedStructure = null;
    var selectedHostSchool = null;
    var tournyMaxDistance = 0;
    var tournyAverageDistance = 0;
   
    function drawMarkers(){
        Markers.forEach(function(value, index){
        	setTimeout(function(){
        	 	map.addMarker(value);
        	}, index*500);    
        });
    }
    
    /*function drawRoutes(){
        Routes.forEach(function(value, index){
        	setTimeout(function(){
                map.drawRoute(value);
            }, index*500);
        });
    }*/

    function getDistances(){
        tournyAverageDistance =0;
        tournyMaxDistance =0;
		Routes.forEach(function(value, index){
			var obj = {
				origin: value.origin,
				destination: value.destination,
				travelMode: 'driving',
				callback: addRoutes
			}
			setTimeout(function(){
				map.getRoutes(obj);
			},index*500);
			
		});
    }

    function addRoutes(route, err){
        try{
            var distance = route[0].legs[0].distance.text;
            var duration = route[0].legs[0].distance.duration;
            distance = parseInt(distance);
            if(distance > tournyMaxDistance){
                tournyMaxDistance = distance;
            }
            if(distance > 1){
                tournyAverageDistance += distance;
            }
        }
        catch(ex){
            Bridge.print(ex);
        }
        $("#travelDistances").show();
        document.getElementById("max").innerHTML = (tournyMaxDistance*0.62137).toFixed(3).toString() + " mi";
        document.getElementById("average").innerHTML = ((tournyAverageDistance/(Routes.length-1))*0.62137).toFixed(3).toString() + " mi";
    }

    ///////////////////////
    // Rendering the map //
    //////////////////////
    function updateMapSchools(host){
        var tourny = SchoolService.getSchool(host);
        try{
            Markers.push({
                lng: parseFloat(tourny.latitude),
                lat: parseFloat(tourny.longitude),
                zIndex: 5.0,
                label: "H"
            });
            drawMarkers();
            map.refresh();
        }
        catch(ex){
            Bridge.print(ex);
        }
    }

    function updateMapSectionals(host){
        var tourny = SchoolService.getSchool(host);
        var arr = SchoolService.getSchoolSectionals(selectedTournament, host);
        var Feeders = new Array();
        for(var i=0, ii=arr.size(); i<ii; i++){
            Feeders.push(arr.get(i));
        }
        try{
            Markers.push({
                lng: parseFloat(tourny.latitude),
                lat: parseFloat(tourny.longitude),
                zIndex: 5.0,
                label: "H"
            });
            Feeders.forEach(function(value){
                Markers.push({
                    lng: parseFloat(value.latitude),
                    lat: parseFloat(value.longitude)
                });
                Routes.push({
                    origin: [tourny.longitude, tourny.latitude],
                    destination: [parseFloat(value.longitude), parseFloat(value.latitude)],
                    travelMode: 'driving',
                    strokeColor: "#2980B9",
                    strokeWeight: 6
                });
            });
            getDistances();
            drawMarkers();
            //drawRoutes();
            map.refresh();
        }
        catch(ex){
            Bridge.print(ex);
        }
    }
    
    function updateMapRegionals(host){
        var tourny = SchoolService.getSchool(host);
        var arr = SchoolService.getSectionalHosts(selectedTournament, host);
        var Feeders = new Array();
        for(var i=0, ii=arr.size(); i<ii; i++){
            Feeders.push(arr.get(i));
        }
        try{
            Markers.push({
                lng: parseFloat(tourny.latitude),
                lat: parseFloat(tourny.longitude),
                zIndex: 5.0,
                label: "H"
            });
            Feeders.forEach(function(value){
                Markers.push({
                    lng: parseFloat(value.latitude),
                    lat: parseFloat(value.longitude)
                });
                Routes.push({
                    origin: [tourny.longitude, tourny.latitude],
                    destination: [parseFloat(value.longitude), parseFloat(value.latitude)],
                    travelMode: 'driving',
                    strokeColor: "#2980B9",
                    strokeWeight: 6
                });
            });
            getDistances();
            drawMarkers();
            //drawRoutes();
            map.refresh();
        }
        catch(ex){
            Bridge.print(ex);
        }
    }
    
    function updateMapSemiStates(host){
        var tourny = SchoolService.getSchool(host);
        var arr = SchoolService.getRegionalHosts(selectedTournament, host);
        var Feeders = new Array();
        for(var i=0, ii=arr.size(); i<ii; i++){
            Feeders.push(arr.get(i));
        }
        try{
            Markers.push({
                lng: parseFloat(tourny.latitude),
                lat: parseFloat(tourny.longitude),
                zIndex: 5.0,
                label: "H"
            });
            Feeders.forEach(function(value){
                Markers.push({
                    lng: parseFloat(value.latitude),
                    lat: parseFloat(value.longitude)
                });
                Routes.push({
                    origin: [tourny.longitude, tourny.latitude],
                    destination: [parseFloat(value.longitude), parseFloat(value.latitude)],
                    travelMode: 'driving',
                    strokeColor: "#2980B9",
                    strokeWeight: 6
                });
            });
            getDistances();
            drawMarkers();
            //drawRoutes();
            map.refresh();
        }
        catch(ex){
            Bridge.print(ex);
        }
    }
    
    function updateMapStates(host){
        var tourny = SchoolService.getTournament(host);
        try{
            Markers.push({
                lat: parseFloat(tourny.latitude),
                lng: parseFloat(tourny.longitude),
                label: "H"
            });
            HostSchools.forEach(function(value){ 
                Markers.push({
                    lng: parseFloat(value.latitude),
                    lat: parseFloat(value.longitude)
                });
                Routes.push({
                    origin: [tourny.latitude, tourny.longitude],
                    destination: [parseFloat(value.longitude), parseFloat(value.latitude)],
                    travelMode: 'driving',
                    strokeColor: "#2980B9",
                    strokeWeight: 6
                });
            });
            getDistances();
            drawMarkers();
            drawRoutes();
            map.refresh();
        }
        catch(ex){
            Bridge.print(ex);
        }
    }

    ////////////////////////////////
    // Populating Host School List//
    ////////////////////////////////
    function populateSchoolList(){
        HostSchools = new Array();
        var schoolList = SchoolService.getAllSchools();
        for(var i=0, ii=schoolList.size(); i<ii; i++){
            HostSchools.push(schoolList.get(i));
        }
        var options =  '';
        HostSchools.forEach(function(value){
            options += '<option value="'+value.schoolId+'">'+value.schoolName+'</option>';
        });
        $('#hostSchoolList').html(options);
    }

    function populateSectionalsSchoolList(){
        HostSchools = new Array();
        var schoolList = SchoolService.getSectionalsMap(selectedTournament);
        for(var i=0, ii=schoolList.size(); i<ii; i++){
            HostSchools.push(schoolList.get(i));
        }
        var options =  '';
        HostSchools.forEach(function(value){
            options += '<option value="'+value.schoolId+'">'+value.schoolName+'</option>';
        });
        $('#hostSchoolList').html(options);
    }

    function populateRegionalsSchoolList(){
        HostSchools = new Array();
        var schoolList = SchoolService.getRegionalsMap(selectedTournament);
        for(var i=0, ii=schoolList.size(); i<ii; i++){
            HostSchools.push(schoolList.get(i));
        }
        var options =  '<option selected value=""><strong>Select...</strong></option>';
        HostSchools.forEach(function(value){
            options += '<option value="'+value.schoolId+'">'+value.schoolName+'</option>';
        });
        $('#hostSchoolList').html(options);
    }
    
    function populateSemiStatesSchoolList(){
        HostSchools = new Array();
        var schoolList = SchoolService.getSemiStateHosts(selectedTournament);
        for(var i=0, ii=schoolList.size(); i<ii; i++){
            HostSchools.push(schoolList.get(i));
        }
        var options =  '<option selected value=""><strong>Select...</strong></option>';
        HostSchools.forEach(function(value){
            options += '<option value="'+value.schoolId+'">'+value.schoolName+'</option>';
        });
        $('#hostSchoolList').html(options);
    }
    
    function populateStatesSchoolList(){
        HostSchools = new Array();
        var schoolList = SchoolService.getSemiStateHosts(selectedTournament);
        for(var i=0, ii=schoolList.size(); i<ii; i++){
            HostSchools.push(schoolList.get(i));
        }
        var options =  '<option selected value=""><strong>Select...</strong></option>';
        Tournaments.forEach(function(value){
            if(value.tournamentId = selectedTournament){
                options += '<option value="'+value.tournamentId+'">'+value.tournamentName+'</option>';
            }
        });
        $('#hostSchoolList').html(options);
    }
    
    function getTournaments(){
        var tempTournys = SchoolService.getTournaments();
        for(var i=0, ii=tempTournys.size(); i<ii; i++){
            Tournaments.push(tempTournys.get(i));
        }
        var options =  '<option selected value=""><strong>Select...</strong></option>'; //create your "title" option
        Tournaments.forEach(function(value){ //loop through your elements
            options += '<option value="'+value.tournamentId+'">'+value.tournamentName+'</option>'; //add the option element as a string
        });
        $('#tournamentList').html(options); //replace the selection's html with the new options
    }

    function clearMap(){
        map.removeMarkers();
        map.removePolylines();
        Markers = new Array();
        Routes = new Array();
    }

    function setSelectedStructure(value){
        selectedStructure = value;
    }

    function setSelectedTournament(value){
        selectedTournament = value;
    }

    function setSelectedHostSchool(value){
        selectedHostSchool = value;
    }

    function getSelectedHostSchool(){
        return selectedHostSchool;
    }

    function getSelectedTournament(){
        return selectedTournament;
    }

    function getSelectedStructure(){
        return selectedStructure;
    }

    return {
        updateMapRegionals: updateMapRegionals,
        updateMapSemiStates: updateMapSemiStates,
        updateMapStates: updateMapStates,
        populateRegionalsSchoolList: populateRegionalsSchoolList,
        populateSemiStatesSchoolList: populateSemiStatesSchoolList,
        populateStatesSchoolList: populateStatesSchoolList,
        getTournaments: getTournaments,
        clearMap: clearMap,
        setSelectedStructure: setSelectedStructure,
        setSelectedTournament: setSelectedTournament,
        setSelectedHostSchool: setSelectedHostSchool,
        getSelectedHostSchool: getSelectedHostSchool,
        getSelectedTournament: getSelectedTournament,
        getSelectedStructure: getSelectedStructure,
        populateSectionalsSchoolList: populateSectionalsSchoolList,
        updateMapSectionals: updateMapSectionals,
        populateSchoolList: populateSchoolList,
        updateMapSchools: updateMapSchools
    };
});