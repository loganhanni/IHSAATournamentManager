var Tournaments = new Array();
var HostSchools = new Array();
var Schools = new Array();
var selectedTournament = "";
var selectedSectional = "";
var selectedSchool = "";

$(document).ready(function(){
    $("#inputSectionalSelect").prop('disabled', 'disabled');
    $("#inputSchoolSelect").prop('disabled', 'disabled');
    $("#transferSchoolButton").prop('disabled', 'disabled');

    $("#inputTournamentSelect").change(function() {
        selectedTournament = $(this).val();
        if(selectedTournament){
            Bridge.print(selectedTournament);
            populateSectionalsSchoolList();
            $("#inputSectionalSelect").prop('disabled', false);
        }
        else{
            $("#inputSchoolSelect").prop('disabled', 'disabled');
            $("#inputSchoolSelect").val(null);
            $("#inputSectionalSelect").prop('disabled', 'disabled');
            $("#inputSectionalSelect").val(null);
            $("#transferSchoolButton").prop('disabled', 'disabled');
        }
    });

    $("#inputSectionalSelect").change(function() {
        selectedSectional = $(this).val();
        if(selectedSectional){
            populateSchoolList();
            $("#inputSchoolSelect").prop('disabled', false);
        }
        else{
            $("#inputSchoolSelect").prop('disabled', 'disabled');
            $("#inputSchoolSelect").val(null);
            $("#transferSchoolButton").prop('disabled', 'disabled');
        }
    });

    $("#inputSchoolSelect").change(function() {
        selectedSchool = $(this).val();
        if(selectedSchool){
            $("#transferSchoolButton").prop('disabled', false);
        }
        else{
            $("#transferSchoolButton").prop('disabled', 'disabled');
        }
    });
    getTournaments();
});

function populateSchoolList(){
    Schools = new Array();
    var schoolList = SchoolService.getAllSchools();
    for(var i=0, ii=schoolList.size(); i<ii; i++){
        Schools.push(schoolList.get(i));
    }
    var options =  '<option selected value=""><strong>Select...</strong></option>'; //create your "title" option
    Schools.forEach(function(value){
        options += '<option value="'+value.schoolId+'">'+value.schoolName+'</option>';
    });
    $('#inputSchoolSelect').html(options);
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

function populateSectionalsSchoolList(){
    HostSchools = new Array();
    var schoolList = SchoolService.getSectionalsMap(selectedTournament);
    for(var i=0, ii=schoolList.size(); i<ii; i++){
        HostSchools.push(schoolList.get(i));
    }
    var options =  '<option selected value=""><strong>Select...</strong></option>'; //create your "title" option
    HostSchools.forEach(function(value){
        options += '<option value="'+value.schoolId+'">'+value.schoolName+'</option>';
    });
    $('#inputSectionalSelect').html(options);
}

function transferSchool(){
    Bridge.print(selectedTournament);
    SchoolService.transferSchoolSectional(selectedSchool, selectedSectional, selectedTournament);
    $("#inputSectionalSelect").prop('disabled', 'disabled');
    $("#inputSectionalSelect").val(null);
    $("#inputSchoolSelect").prop('disabled', 'disabled');
    $("#inputSchoolSelect").val(null);
    $("#transferSchoolButton").prop('disabled', 'disabled');
    $("#inputTournamentSelect").val(null);
}