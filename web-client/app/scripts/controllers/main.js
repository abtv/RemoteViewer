'use strict';

angular.module('webRemoteViewerApp')
    .controller('MainCtrl', function($scope) {
    	function handler(image){
    		var i = image;
    	}

        var xhr = new XMLHttpRequest();
        xhr.onreadystatechange = function() {
            if (this.readyState == 4 && this.status == 200) {
                handler(this.response);
                console.log(this.response, typeof this.response);
                var img = document.getElementById('screen');
                var url = window.URL || window.webkitURL;
                img.src = url.createObjectURL(this.response);
            }
        }
        xhr.open('GET', 'http://localhost:8880/');
        xhr.responseType = 'blob';
        xhr.send();
    });
