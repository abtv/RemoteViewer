'use strict';

angular.module('webRemoteViewerApp')
    .controller('MainCtrl', function($scope) {

        $scope.url = 'http://localhost:8880/';
        $scope.speed = '24';

        (function(timeout) {
            var xhr = new XMLHttpRequest();
            xhr.onreadystatechange = function() {
                var self = this;
                if (self.readyState == 4 && self.status == 200) {
                    var img = document.getElementById('screen');
                    var url = window.URL || window.webkitURL;
                    img.src = url.createObjectURL(self.response);
                }
            }

            function getImage() {
                xhr.open('GET', $scope.url);
                xhr.responseType = 'blob';
                xhr.send();

                setTimeout(getImage, timeout);
            }
            getImage();
        })(1000 / +$scope.speed)
    });
