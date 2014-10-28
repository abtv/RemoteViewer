'use strict';

angular.module('webRemoteViewerApp')
    .controller('MainCtrl', function($scope) {

        $scope.url = 'http://localhost:8880/';
        $scope.speed = '10';
        $scope.timeout = 1000 / +$scope.speed;

        (function() {
            function getImage() {
                try {
                    var xhr = new XMLHttpRequest();
                    xhr.onreadystatechange = function() {
                        var self = this;
                        if (self.readyState == 4 && self.status == 200) {
                            var img = document.getElementById('screen');
                            var url = window.URL || window.webkitURL;
                            img.baseURI = $scope.url;
                            img.src = url.createObjectURL(self.response);
                            setTimeout(getImage, $scope.timeout);
                        }
                    }
                    xhr.open('GET', $scope.url, true);
                    xhr.responseType = 'blob';
                    xhr.send();
                } catch (ex) {

                }
            }
            getImage();
        })()
    });
