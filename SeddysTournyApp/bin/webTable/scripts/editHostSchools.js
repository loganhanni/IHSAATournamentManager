var Tournaments = new Array();
var NonHosts = new Array();
var Hosts = new Array();
var selectedTournament = "";
var selectedNonHost = "";
var selectedHost = "";
var selectedReplacement = "";

$( document ).ready(function() {
    $('#newHostList').val(null);
    $('#newHostList').prop('disabled', 'disabled');
    $('#removeHostList').val(null);
    $('#removeHostList').prop('disabled', 'disabled');
    $('#addHostButton').prop('disabled', 'disabled');
    $('#removeHostButton').prop('disabled', 'disabled');
    onStart();

    $("#inputTournamentSelect").change(function() {
        selectedTournament = $(this).val();
        if(selectedTournament){
            $('#removeHostList').prop('disabled', false);
            $('#newHostList').prop('disabled', false);
            getNonHosts();
            getHosts();
        }
        else{
            $('#newHostList').val(null);
            $('#newHostList').prop('disabled', 'disabled');
            $('#removeHostList').val(null);
            $('#removeHostList').prop('disabled', 'disabled');
        }
    });

    $("#replaceHostList").change(function() {
        selectedReplacement = $(this).val();
        if(selectedReplacement){
            $('#acceptReplaceButton').prop('disabled', false);
        }
        else{
            $('#acceptReplaceButton').prop('disabled', 'disabled');
        }
    });

    $("#newHostList").change(function() {
        selectedNonHost = $(this).val();
        if(selectedNonHost){
            $('#addHostButton').prop('disabled', false);
        }
        else{
            $('#addHostButton').prop('disabled', 'disabled');
        }
    });

    $("#removeHostList").change(function() {
        selectedHost = $(this).val();
        if(selectedHost){
            $('#removeHostButton').prop('disabled', false);
        }
        else{
            $('#removeHostButton').prop('disabled', 'disabled');
        }
    });
});

function onStart(){
    var tournys = SchoolService.getTournaments();
    for(var i=0, ii=tournys.size(); i<ii; i++){
        Tournaments.push(tournys.get(i));
    }
    var options =  '<option selected value=""><strong>Select...</strong></option>'; //create your "title" option
    Tournaments.forEach(function(value){ //loop through your elements
        options += '<option value="'+value.tournamentId+'">'+value.tournamentName+'</option>'; //add the option element as a string
    });
    $('#inputTournamentSelect').html(options); //replace the selection's html with the new options
}

function getHosts(){
    Hosts = new Array();
    var hosts = SchoolService.getHostSchools(selectedTournament);
    for(var i=0, ii=hosts.size(); i<ii; i++){
        Hosts.push(hosts.get(i));
    }
    var options =  '<option selected value=""><strong>Select...</strong></option>'; //create your "title" option
    Hosts.forEach(function(value){ //loop through your elements
        options += '<option value="'+value.schoolId+'">'+value.schoolName+'</option>'; //add the option element as a string
    });
    $('#removeHostList').html(options); //replace the selection's html with the new options
}

function getNonHosts(){
    NonHosts = new Array();
    var nonhosts = SchoolService.getNonHostSchools();
    for(var i=0, ii=nonhosts.size(); i<ii; i++){
        NonHosts.push(nonhosts.get(i));
    }
    var options =  '<option selected value=""><strong>Select...</strong></option>'; //create your "title" option
    NonHosts.forEach(function(value){ //loop through your elements
        options += '<option value="'+value.schoolId+'">'+value.schoolName+'</option>'; //add the option element as a string
    });
    $('#newHostList').html(options); //replace the selection's html with the new options
}

function saveNewHost(){
    SchoolService.addNewHost(selectedNonHost, selectedTournament);
    $('#addHostButton').prop('disabled', 'disabled');
    $('#newHostList').val(null);
    getNonHosts();
    getHosts();
}

function replaceTournament(){
    SchoolService.replaceHost(selectedHost, selectedReplacement, selectedTournament);
    $('#myModal').modal('hide');
    getHosts();
    getNonHosts();
}

function removeHost(){
    if(checkIfHostHasSchools(selectedHost)){
        $('#myModal').modal('show');
        $('#acceptReplaceButton').prop('disabled', 'disabled');
        var options =  '<option selected value=""><strong>Select...</strong></option>'; //create your "title" option
        Hosts.forEach(function(value){ //loop through your elements
            options += '<option value="'+value.schoolId+'">'+value.schoolName+'</option>'; //add the option element as a string
        });
        $('#replaceHostList').html(options); //replace the selection's html with the new options
        var selectedReplacement = "";
    }
    else{
        $('#myModal').modal('hide');
        SchoolService.removeHost(selectedHost, selectedTournament);
        getHosts();
        getNonHosts();
    }
}

function checkIfHostHasSchools(hostId){
    return SchoolService.checkIfHostHasSchools(hostId, selectedTournament);
}