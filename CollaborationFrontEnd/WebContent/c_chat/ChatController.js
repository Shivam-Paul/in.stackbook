myApp.controller("ChatController", function($scope, $rootScope, $location, chatService) {
	
	$scope.messages = [];
	$scope.message = '';
	$scope.max = 140;
	
	$scope.addMessage = function() {
		chatService.send($rootScope.currentUser.email_id+":"+$scope.message);
		$scope.message='';
	}
	
	chatService.receice().then(null, null, function(message) {
		$scope.messages.push(message);
	});
	
});