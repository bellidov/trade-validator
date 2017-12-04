<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!doctype html>

<head>
    <title>Test</title>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">    
    <script src="<c:url value="/resources/js/main.js" />"></script>
    <link href="<c:url value="/resources/css/main.css" />" rel="stylesheet"></link>
    <!--script src="js/main.js"></script>
    <link href="css/main.css" rel="stylesheet"></link-->
</head>
<body>
    

    <div class="container">
        <div class="row jumbotron text-center">
            <p>Trade Data Validator</p>
        </div>
        <div class="row">
            <div class="col-md-6">
                <div class="form-group">
                    <label for="jsonData">Please, copy your json document here:</label>
                    <textarea class="form-control" rows="30" id="jsonData"></textarea>
                </div>
                <label for="fileUploader">Or upload it:</label>
                <input type="file" id="fileUploader" onchange="getJsonFromFile(this)" />
            </div>

            <div class="col-md-6">
                <div class="row">
                    <div class="col-md-12 text-center"> 
                        <button id="validateBtn" onclick="validate();" class="btn btn-primary">Validate!</button> 
                    </div>
                </div>

                <div class="row">
                    <div id="fail" style="display:none;">
                        <h2 class="text-center" style="color:red;">The validation has failed. Please, check the results below.</h2>
                    </div>
                    <div id="success" style="display:none;">
                        <h2 class="text-center" style="color:green;">The validation was successful.</h2>
                    </div>
                </div>
                
                <div class="card" style="width: 100%; display:none;">
                        <br />
                        <ul class="list-group list-group-flush">
                          <li class="list-group-item">head</li>
                          <li class="list-group-item">body</li>
                        </ul>
                </div>

            </div>
        </div class="row">
    </div>
</body>
</html>
