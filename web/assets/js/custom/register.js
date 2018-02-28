var app = angular.module("FaceRecognitionRegisterApp", []);
app.controller("RegisterController", function ($scope, $http, $window) {
    $scope.register = function () {
       // use $.param jQuery function to serialize data from JSON 
        var data = $.param({
            email : $scope.vm.email,
            username : $scope.vm.username,
            password : $scope.vm.password,
            faces : capture_faces
        });

        var config = {
            headers : {
                'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8;'
            }
        };
        $http.post('register', data, config)
        .success(function (data, status, headers, config) {
            if(data === "Registration completed successfully")
                $window.location = "/FaceRecognition1";
            else {
                $window.alert(data);
            }
        })
        .error(function (data, status, header, config) {
        });
    };
    
    $scope.back = function () {
       // use $.param jQuery function to serialize data from JSON 
        $window.location = "/FaceRecognition1";
    };
});