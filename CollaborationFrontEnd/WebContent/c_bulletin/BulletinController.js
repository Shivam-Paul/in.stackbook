myApp.controller("BulletinController", function($scope, $http, $rootScope, $location) {
	
	$scope.bulletin = {
			bulletin_id : 0,
			title : '',
			event_date : '',
			description : ''
	} 
	$scope.bulletinData;
	$scope.allBulletinData;
	
	$scope.addBulletin = function() {
		
		console.log('Add Bulletin');
		
		console.log('Adding Bulletin');
		
		$http.post('http://localhost:8081/CollaborationRestService/bulletin/save', $scope.bulletin)
		.then(function(response) {
			$scope.viewBulletin($scope.bulletin.bulletin_id);
		});
	}
	
	$scope.viewBulletin = function(bulletin_id) {
		
		console.log('View Bulletin');
		
		$scope.get(bulletin_id);
		$location.path('viewBulletin');
	}
	
	$scope.update = function(bulletin_id) {
		
		console.log('Update Bulletin');
		
		$http.get('http://localhost:8081/CollaborationRestService/bulletin/update' + bulletin_id)
		.then(function(response) {
			console.log('Update Bulletin');
			$scope.viewBulletin(bulletin_id);
		});
	}
	
	function showAllBulletins() {
		console.log('List All Bulletins');
		$http.get('http://localhost:8081/CollaborationRestService/bulletin/list')
		.then(function(response) {
			$scope.allBulletinData = response.data;
		});
	}
	
	$scope.get = function(bulletin_id) {
		
		console.log('Get Bulletin');
		
		$http.get('http://localhost:8081/CollaborationRestService/bulletin/get' + bulletin_id)
		.then(function(response) {
			$scope.bulletinData = response.data;
		});
		
	}
	
	showAllBulletins();
	
});