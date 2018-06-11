myApp.controller("UserController", function($scope, $http, $rootScope, $location) {
	
	$rootScope.currentUser;
	$rootScope.userImage;

	$scope.user = {
		email_id : '',
		name : '',
		password : '',
		role : '',
		registered_date : '',
		active : '',
		mobile : ''
	}
	
	$scope.profilePicture = {
			image : '',
			email_id : ''
	}

	$scope.register = function() {

		alert("register");

		$http.post('http://localhost:8081/CollaborationRestService/user/register', $scope.user)
		.then(function(response) {
			$scope.profilePicture.email_id = $scope.user.email_id;
			$http.post('http://localhost:8081/CollaborationRestService/picture/save', $scope.profilePicture)
			.then(function(response){
				console.log('Image uploaded successfully');
			});
			console.log('The user registered successfully');
			console.log(response.statusText);
		});
	}

	$scope.login = function() {

		alert("login");

		$http.post('http://localhost:8081/CollaborationRestService/user/validate',$scope.user)
		.then(function(response) {
			
			console.log('Email ID: '+$scope.user.email_id);
			console.log('Password: '+$scope.user.password);

			$scope.user=response.data;
			console.log($scope.user);
			$rootScope.currentUser=$scope.user;
			
			$location.path("/home");
		});

	}
	
	
	//User Notification 
	
	$scope.userNotificationData;
	
	$scope.userNotification = {
			notification_id : 0,
			email_id : '',
			notification : '',
			seen : '',
			received_date : '',
			reference_table : '',
			reference_id : ''
	}
	
	/*function listAllNotifications(email_id) {
		
		alert('List All Notifications');
		
		$http.get('http://localhost:8081/CollaborationRestService/user/notification/list/'+email_id)
		.then(function(response) {
			$scope.userNotificationData = response.data;
		});
		
	}*/
	
	//listAllNotifications($rootScope.currentUser.email_id);

});