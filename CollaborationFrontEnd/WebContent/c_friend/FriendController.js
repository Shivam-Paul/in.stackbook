myApp.controller("FriendController", function($scope, $http, $rootScope, $location) {

	$scope.friend = {
			friend_id : 0,
			email_id1 : '',
			email_id2 : '',
			description : 0
	} 

	$scope.friendData;
	$scope.allFriendData;

	$scope.addFriend = function() {

		alert('Add Friend');

		console.log('Adding Friend');

		$http.post('http://localhost:8081/CollaborationRestService/friend/save',$scope.friend)
		.then(function(response) {
			$location.path('suggestedUsers');
		});
	}

	$scope.update = function(friend_id) {

		alert('Update Friend');

		$http.get('http://localhost:8081/CollaborationRestService/friend/update'+friend_id)
		.then(function(response) {
			console.log('Update Friend');
		});
	}

	function listAllFriends() {
		console.log('List All Friends');
		$http.get('http://localhost:8081/CollaborationRestService/friend/list')
		.then(function(response) {
			$scope.allFriendData = response.data;
		});
	}

	function listAllBlockedUsers() {
		console.log('List All Blocked Users');
		$http.get('http://localhost:8081/CollaborationRestService/friend/list')
		.then(function(response) {
			$scope.allBlockedUsers = response.data;
		});
	}

	listAllFriends();
	listAllBlockedUsers();

});