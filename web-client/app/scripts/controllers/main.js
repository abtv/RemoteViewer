'use strict';

angular.module('webRemoteViewerApp')
    .controller('MainCtrl', function($scope) {
        function handler(image) {
            var i = image;
        }

        (function(timeout) {
            var xhr = new XMLHttpRequest();
            xhr.onreadystatechange = function() {
                var self = this;
                if (self.readyState == 4 && self.status == 200) {
                    handler(self.response);
                    console.log(self.response, typeof self.response);
                    var img = document.getElementById('screen');
                    var url = window.URL || window.webkitURL;
                    img.src = url.createObjectURL(self.response);
                }
            }

            function getImage() {
                xhr.open('GET', 'http://localhost:8880/');
                xhr.responseType = 'blob';
                xhr.send();

                setTimeout(getImage, timeout);
            }
            getImage();
        })(200)

    });
