var app = angular.module("FaceRecognitionLoginApp", []);
app.controller("LoginController", function ($scope, $http, $window) {

    $scope.login = function () {
       // use $.param jQuery function to serialize data from JSON 
        var data = $.param({
            email : $scope.vm.email,
            password : $scope.vm.password,
            faces : capture_faces
        });

        var config = {
            headers : {
                'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8;'
            }
        };

        $http.post('login', data, config)
        .success(function (data, status, headers, config) {
            if(data.substring(0, 30) === "Email and Password are correct")
            {
                $window.alert(data);
                $window.location = "/FaceRecognition1/welcome";
            }
            else {
                $window.alert(data);
                $window.location = "/FaceRecognition1";
            }
        })
        .error(function (data, status, header, config) {
        });
    };
    
    $scope.register = function () {
       // use $.param jQuery function to serialize data from JSON 
        $window.location = "/FaceRecognition1/register";
    };
});