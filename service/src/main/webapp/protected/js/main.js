

function validate() {
    //first validate and format
    var result = formatJson();
    if(!result) {
        return;
    }
    
    post();
}


function formatJson() {
    try{
        var jsonStr = $("#jsonData").val();
        var jsonObj = JSON.parse(jsonStr);
        jsonPretty = JSON.stringify(jsonObj, null, '\t');
        $("#jsonData").val(jsonPretty);
        return jsonStr;
    } catch (e) {
        alert("The provided json data is malformed, please fix it.")
        return false;
    }    
}

function getJsonFromFile(fileInput){
	var file = fileInput.files[0];

    if(file.size > 2000000) {
        alert("The size of the file should be less than 2MB")
        return;
    }
    var reader = new FileReader();
    reader.onload = function(e) {
        $("#jsonData").val(reader.result);
       }
    reader.readAsText(file);	
}

function post(){
    var urlx = window.location.origin + "/" + window.location.pathname.split("/")[1]
    if(urlx.substr(-1) != '/'){
        urlx += '/';
    }
    urlx += 'v1/validator';

    $.ajax({
        type : "POST",
        contentType : "application/json",
        url : urlx,
        data : jsonPretty,
        dataType : 'json',
        timeout : 100000,
        success : function(data) {
            $("#success").show();
            $("#fail").hide();
        },
        error : function(e) {
            $("#fail").show();
            $("#success").hide();
        				console.log("ERROR: ", e);
        },
        done : function(e) {
        				console.log("DONE");
        }
    });
}