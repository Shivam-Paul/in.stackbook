myApp.controller('FriendController', function($scope, $http, $rootScope, $location) {

	$scope.friend = {
			friend_id : 0,
			email_id1 : '',
			email_id2 : '',
			friends : 0
	}

	$scope.temp;
	$scope.friendData;
	$scope.suggestedList;
	$scope.mutualFriendList;
	$scope.blockedList;
	$scope.friendRequestList;

	$scope.addFriend = function(email_id2, friends) {

		console.log('Add Friend');

		$http.get('http://localhost:8081/CollaborationRestService/friend/save/' + email_id2 + '/' + friends)
		.then(function(response) {
			listAllFriends();
			listSuggestedUsers();
			listBlockedUsers();
			listReceivedRequests();
			$location.path('/suggestedUsers');
		});
	}

	$scope.update = function(friend_id, friends) {

		console.log('Update Friend');

		$http.get('http://localhost:8081/CollaborationRestService/friend/update/' + friend_id + '/' + friends)
		.then(function(response) {
			listAllFriends();
			listSuggestedUsers();
			listBlockedUsers();
			listReceivedRequests();
			$location.path('/friends');
		});
	}
	
	$scope.deleteFriend = function(friend_id) {

		console.log('Delete Friend');

		$http.get('http://localhost:8081/CollaborationRestService/friend/delete/' + friend_id)
		.then(function(response) {
			listAllFriends();
			listSuggestedUsers();
			listBlockedUsers();
			listReceivedRequests();
			$location.path('friends');
		});
	}

	function listAllFriends() {
		
		console.log('List All Friends');
		
		$http.get('http://localhost:8081/CollaborationRestService/friend/list/friends')
		.then(function(response) {
			$scope.allFriendData = response.data;
			/*for(var i = 0; i < $scope.allFriendData.length; i++) {
				$http.get('http://localhost:8081/CollaborationRestService/friend/get/name/' + email_id)
				.then(function(response) {
					$scope.allFriendData[i].name = response.data;
				});
			}*/
		});
	}
	
	function listReceivedRequests() {
		
		console.log('List Received Requests');
		
		$http.get('http://localhost:8081/CollaborationRestService/friend/list/requests')
		.then(function(response) {
			$scope.friendRequestList = response.data;
			console.log($scope.friendRequestList);
		});
		
	}
	
	function listSuggestedUsers() {
		
		console.log('List Suggested Users');
		
		$http.get('http://localhost:8081/CollaborationRestService/user/list/suggestedUsers')
		.then(function(response) {
			$scope.suggestedList = response.data;
		});
	}
	
	$scope.mutualFriends = function(email_id) {
		
		$scope.mutualFriendList = null;
		
		console.log('Mutual Friends');
		
		$http.get('http://localhost:8081/CollaborationRestService/user/list/mutualFriends/' + email_id + '.')
		.then(function(response) {
			$scope.mutualFriendList = response.data;
			console.log($scope.mutualFriendList);
		});
	}
	
	function listBlockedUsers() {
		
		console.log('Blocked Users');
		
		$http.get('http://localhost:8081/CollaborationRestService/user/list/blocked')
		.then(function(response) {
			$scope.blockedList = response.data;
		});
	}

	listAllFriends();
	listSuggestedUsers();
	listBlockedUsers();
	listReceivedRequests();

});