<!DOCTYPE html>
<!--
To change this license header, choose License Headers in Project Properties.
To change this template file, choose Tools | Templates
and open the template in the editor.
-->
<html>
    <head>
        <title>TODO: supply a title</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="assets/css/main.css" media="all">
        <link rel="stylesheet" href="assets/css/demo.css">
        <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    </head>
    <body>
        <div class="col-md-6 col-md-offset-3" ng-app="FaceRecognitionRegisterApp">
            <div ng-controller="RegisterController">
                <h1>Register</h1>

                <div class="demo-container" style = 'float:left'>
                    <video id="video" preload autoplay loop muted></video>
                    <canvas id="canvas" height="500"></canvas>
                </div>
                <div style="float:left; width:350px; padding: 30px 30px">
                    <img id ='capture_1' src='assets/image/anonymous.png' width='60px' height='60px' style = 'margin-bottom: 5px'>
                    <img id ='capture_2' src='assets/image/anonymous.png' width='60px' height='60px' style = 'margin-bottom: 5px'>
                    <img id ='capture_3' src='assets/image/anonymous.png' width='60px' height='60px' style = 'margin-bottom: 5px'>
                    <img id ='capture_4' src='assets/image/anonymous.png' width='60px' height='60px' style = 'margin-bottom: 5px'>
                    <img id ='capture_5' src='assets/image/anonymous.png' width='60px' height='60px' style = 'margin-bottom: 5px'>
                    <img id ='capture_6' src='assets/image/anonymous.png' width='60px' height='60px' style = 'margin-bottom: 5px'>
                    <img id ='capture_7' src='assets/image/anonymous.png' width='60px' height='60px' style = 'margin-bottom: 5px'>
                    <img id ='capture_8' src='assets/image/anonymous.png' width='60px' height='60px' style = 'margin-bottom: 5px'>
                    <img id ='capture_9' src='assets/image/anonymous.png' width='60px' height='60px' style = 'margin-bottom: 5px'>
                    <img id ='capture_10' src='assets/image/anonymous.png' width='60px' height='60px' style = 'margin-bottom: 5px'>
                    
                    
                </div>
                <div style="clear:both"></div>
                <div id="prompt"></div>
                <form name="form" ng-submit="form.$valid && vm.login()" enctype="multipart/form-data" novalidate> 
                    <div class="form-group" ng-class="{ 'has-error': form.$submitted && form.email.$invalid }">
                        <label for="email">Email</label>
                        <input type="text" id="email" name="email" class="form-control" ng-model="vm.email" required />
                        <div ng-messages="form.$submitted && form.email.$error" class="help-block">
                            <div ng-message="required">Email is required</div>
                        </div>
                    </div>
                    <div class="form-group" ng-class="{ 'has-error': form.$submitted && form.text.$invalid }">
                        <label for="username">Username</label>
                        <input type="text" id="username" name="username" class="form-control" ng-model="vm.username" required />
                        <div ng-messages="form.$submitted && form.text.$error" class="help-block">
                            <div ng-message="required">Username is required</div>
                        </div>
                    </div>
                    <div class="form-group" ng-class="{ 'has-error': form.$submitted && form.password.$invalid }">
                        <label for="password">Password</label>
                        <input type="password" id="password" name="password" class="form-control" ng-model="vm.password" required />
                        <div ng-messages="form.$submitted && form.password.$error" class="help-block">
                            <div ng-message="required">Password is required</div>
                        </div>
                    </div>
                    <div class="form-group">
<!--                        <button ng-disabled="vm.loading" class="btn btn-primary" onclick="SendData()">Login</button>-->
                        <button ng-disabled="vm.loading" class="btn btn-primary" ng-click="register()">Register</button>
                        <button ng-disabled="vm.loading" class="btn btn-primary" ng-click="back()">&#x261A;Back</button>
                        <img ng-if="vm.loading" src="data:image/gif;base64,R0lGODlhEAAQAPIAAP///wAAAMLCwkJCQgAAAGJiYoKCgpKSkiH/C05FVFNDQVBFMi4wAwEAAAAh/hpDcmVhdGVkIHdpdGggYWpheGxvYWQuaW5mbwAh+QQJCgAAACwAAAAAEAAQAAADMwi63P4wyklrE2MIOggZnAdOmGYJRbExwroUmcG2LmDEwnHQLVsYOd2mBzkYDAdKa+dIAAAh+QQJCgAAACwAAAAAEAAQAAADNAi63P5OjCEgG4QMu7DmikRxQlFUYDEZIGBMRVsaqHwctXXf7WEYB4Ag1xjihkMZsiUkKhIAIfkECQoAAAAsAAAAABAAEAAAAzYIujIjK8pByJDMlFYvBoVjHA70GU7xSUJhmKtwHPAKzLO9HMaoKwJZ7Rf8AYPDDzKpZBqfvwQAIfkECQoAAAAsAAAAABAAEAAAAzMIumIlK8oyhpHsnFZfhYumCYUhDAQxRIdhHBGqRoKw0R8DYlJd8z0fMDgsGo/IpHI5TAAAIfkECQoAAAAsAAAAABAAEAAAAzIIunInK0rnZBTwGPNMgQwmdsNgXGJUlIWEuR5oWUIpz8pAEAMe6TwfwyYsGo/IpFKSAAAh+QQJCgAAACwAAAAAEAAQAAADMwi6IMKQORfjdOe82p4wGccc4CEuQradylesojEMBgsUc2G7sDX3lQGBMLAJibufbSlKAAAh+QQJCgAAACwAAAAAEAAQAAADMgi63P7wCRHZnFVdmgHu2nFwlWCI3WGc3TSWhUFGxTAUkGCbtgENBMJAEJsxgMLWzpEAACH5BAkKAAAALAAAAAAQABAAAAMyCLrc/jDKSatlQtScKdceCAjDII7HcQ4EMTCpyrCuUBjCYRgHVtqlAiB1YhiCnlsRkAAAOwAAAAAAAAAAAA==" />
                    </div>
                    <div ng-if="vm.error" class="alert alert-danger">{{vm.error}}</div>
                </form>
            </div>
        </div>
         <!-- angular scripts -->
        <script src="//ajax.googleapis.com/ajax/libs/angularjs/1.5.3/angular.min.js"></script>
        <script src="assets/js/custom/register.js"></script>
        <script src="assets/js/custom/custom.js"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
        <script src="assets/js/faceTracking/tracking-min.js"></script>
        <script src="assets/js/faceTracking/face-min.js"></script>
        <script src="assets/js/gui/dat.gui.min.js"></script>
    </body>
</html>

