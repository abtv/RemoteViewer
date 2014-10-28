'use strict';

angular.module('webRemoteViewerApp')
    .controller('MainCtrl', function($scope) {

        $scope.url = 'http://192.168.0.101:8880/';

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
                            requestComplete = true;
                        }
                    }
                    xhr.open('GET', $scope.url);
                    xhr.responseType = 'blob';
                    xhr.send();
                    setTimeout(getImage, 200);
                } catch (ex) {
                }
            }
            getImage();
        })();
    });
