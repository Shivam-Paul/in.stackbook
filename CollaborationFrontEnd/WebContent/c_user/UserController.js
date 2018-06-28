myApp.controller("UserController", function($scope, $http, $rootScope, $location, $cookieStore) {
	
	$rootScope.currentUser;

	$scope.user = {
		email_id : '',
		name : '',
		password : '',
		role : '',
		registered_date : '',
		active : '',
		mobile : '',
		image : ''
	}
	
	$scope.userData;
	$scope.login;
	
	$rootScope.selectedUserFromBlog;
	
	/*$rootScope.friendData = {
			friend_id = 0,
			email_id1 = '',
			email_id2 = '',
			friends = 0
	};*/
	

	$scope.register = function() {

		console.log('Register');

		$http.post('http://localhost:8081/CollaborationRestService/user/register', $scope.user)
		.then(function(response) {
			console.log('Registered Successfully');
			$location.path('/login');
		});
	}
	
	$scope.editProfile = function() {
		
		console.log('Edit profile');
		
		$http.put('http://localhost:8081/CollaborationRestService/user/update', $rootScope.currentUser)
		.then(function(response) {
			console.log('Profile updated successfully');
		});
		
	}
	
	$scope.uploadImage = function() {
		
		console.log('Upload Image');
		
		$http.post('http://localhost:8081/CollaborationRestService/user/upload')
		.then(function(response) {
			$location.path('editProfile');
		});
		
	}
	
	$scope.viewUser = function(email_id) {
		
		console.log('View User');
		
		$http.get('http://localhost:8081/CollaborationRestService/user/get/' + email_id + '.')
		.then(function(response) {
			$rootScope.selectedUserFromBlog = response.data;
			
			/*$http.get('http://localhost:8081/CollaborationRestService/friend/get/usingEmail/' +
					$rootScope.selectedUserFromBlog.email_id + '/' + $rootScope.currentUser.email_id + '.')
					.then(function(response) {
						$rootScope.friendData = response.data;
*/					
					
			$location.path('viewUser');
		//	});
		});
		
	}

	$scope.login = function() {

		console.log('login');

		$http.post('http://localhost:8081/CollaborationRestService/user/validate', $scope.user)
		.then(function(response) {
			
			$rootScope.viewTrackerList = [];
			$cookieStore.put('viewTrackerList', $rootScope.viewTrackerList);

			$scope.user = response.data;
			$rootScope.currentUser = $scope.user;
			console.log($rootScope.currentUser);
			$cookieStore.put('userCookieData', response.data);
			$location.path('/home');
		});

	}
	
	$scope.logout = function() {
		
		console.log('Entering the logout function');
		delete $rootScope.currentUser;
		//$cookieStore.remove('userCookieData');
		delete $rootScope.viewTrackerList;
		$location.path('/login');
		
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
	
	function listAllNotifications() {
		
		console.log('List All Notifications');
		
		if($rootScope.currentUser != null) {
			$http.get('http://localhost:8081/CollaborationRestService/user/notification/list')
			.then(function(response) {
				$scope.userNotificationData = response.data;
			});
		}
	}
	
	$scope.setAllNotificationsAsSeen = function() {
				
		$http.get('http://localhost:8081/CollaborationRestService/user/notification/update/setAllAsSeen')
		.then(function(response) {
			console.log('All Notifications set as seen');
		});
	}
	
	$scope.viewNotification = function(reference_table, reference_id) {
		
		console.log('View Notification');
		
		//TODO
	} 
	
	
	
	listAllNotifications();

});