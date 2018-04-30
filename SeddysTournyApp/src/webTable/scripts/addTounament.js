var Tournaments = new Array();
var Hosts = new Array();
var HostSchools = new Array();
var selectedTournament = "";
var selectedStructure = "";
var selectedHost = "";
var selectedReplacement = "";

$(document).ready(function(){
    $("#inputTournamentName").prop('disabled', 'disabled');
    $("#submit").prop('disabled', 'disabled');
    $("#deleteTournyButton").prop('disabled', 'disabled');
    $("#structureList").prop('disabled', 'disabled');
    $("#hostSchoolList").prop('disabled', 'disabled');
    $("#swapTournament").prop('disabled', 'disabled');

    $("#inputTournamentSelect").change(function() {
        selectedTournament = $(this).val();
        if(selectedTournament){
            tourny = SchoolService.getTournament(selectedTournament);
            $("#inputTournamentName").prop('disabled', false);
            $("#submit").prop('disabled', false);
            $("#inputTournamentName").val("Copy of "+tourny.tournamentName);
            $("#deleteTournyButton").prop('disabled', false);
            $("#structureList").prop('disabled', false);
        }else{
            $("#inputTournamentName").prop('disabled', 'disabled');
            $("#submit").prop('disabled', 'disabled');
            $("#deleteTournyButton").prop('disabled', 'disabled');
        }
    });

    $("#structureList").change(function() {
        selectedStructure = $(this).val();
            if(selectedStructure){
                $('#hostSchoolList').prop('disabled', false);
                if(selectedStructure === "SemiStates"){
                    populateSemiStatesSchoolList();
                }
                else if(selectedStructure === "Regionals"){
                    populateRegionalsSchoolList();
                }
                else if(selectedStructure === "Sectionals"){
                    populateSectionalsSchoolList();
                }
            }
            else{
                $('#hostSchoolList').val(null);
                $('#hostSchoolList').prop('disabled', 'disabled');
            }
    });

    $("#hostSchoolList").change(function() {
        selectedHost = $(this).val();
        if(selectedHost){
            $("#replacementSchoolList").prop('disabled', false);
            getHosts();
        }
        else{
            $("#replacementSchoolList").prop('disabled', 'disabled');
        }
    });

    $("#replacementSchoolList").change(function() {
        selectedReplacement = $(this).val();
        if(selectedReplacement){
            $("#swapTournamentButton").prop('disabled', false);
        }
        else{
            $("#swapTournamentButton").prop('disabled', 'disabled');
        }
    });
    getTournaments();
});

function saveReplacementHost(){
    SchoolService.saveReplacementHost(selectedHost, selectedReplacement, selectedTournament, selectedStructure);
    $("#replacementSchoolList").val(null);
    $("#replacementSchoolList").prop('disabled', 'disabled');
    $("#hostSchoolList").val(null);
    $("#hostSchoolList").prop('disabled', 'disabled');
    $("#structureList").val(null);
    selectedStructure = "";
    selectedHost = "";
    selectedReplacement = "";
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
    $('#replacementSchoolList').html(options); //replace the selection's html with the new options
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

function saveTournament(){
    var tourny = SchoolService.getTournament(selectedTournament);
    var tournamentName = $("#inputTournamentName").val();
    if(tourny.tournamentId){
        var returnVal = SchoolService.copyAndSaveTournamentByTournamentId(tourny.tournamentId, tournamentName);
        if(returnVal){
            $("#inputTournamentSelect").val(null);
            $("inputTournamentName").val(null);
            $("#inputTournamentName").prop('disabled', 'disabled');
            $("#submit").prop('disabled', 'disabled');
        }
    }
}

function beginDeleteTournament(){
    $('#myModal').modal('show');
}

function deleteTournament(){
    SchoolService.deleteTournament(selectedTournament);
    $("#inputTournamentName").prop('disabled', 'disabled');
    $("#submit").prop('disabled', 'disabled');
    $('#myModal').modal('hide');
    $("#deleteTournyButton").prop('disabled', 'disabled');
    getTournaments();
}

function getTournaments(){
    Tournaments = new Array();
    var tempTournys = SchoolService.getTournaments();
    for(var i=0, ii=tempTournys.size(); i<ii; i++){
        Tournaments.push(tempTournys.get(i));
    }
    var options =  '<option selected value=""><strong>Select...</strong></option>'; //create your "title" option
    Tournaments.forEach(function(value){ //loop through your elements
        options += '<option value="'+value.tournamentId+'">'+value.tournamentName+'</option>'; //add the option element as a string
    });
    $('#inputTournamentSelect').html(options); //replace the selection's html with the new options
}