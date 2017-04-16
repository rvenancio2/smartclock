// app.js
// criar angular app
var validationApp = angular.module('validationApp', []);

// criar angular controller
validationApp.controller('mainController', function($scope) {
    // função para enviar o formulário depois que a validação estiver ok           
    $scope.submitForm = function(isValid, user) {
        // verifica se o formulário é válido
        if (isValid) {        
            alert('Formulário OK: '+ user.hour );
        }
    };    
});

app.factory('NameService', function($http, $q) {

	  //  Create a class that represents our name 
	  service.
	    function NameService() {

	      //  getName returns a promise which when 
	      //  fulfilled returns the name.
	      self.getName = function() {
	        return $http.get('/api/my/name');
	      };
	    }

	  return new NameService();
	});


