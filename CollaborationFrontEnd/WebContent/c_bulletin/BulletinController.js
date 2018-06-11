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
		
		alert('Add Bulletin');
		
		console.log('Adding Bulletin');
		
		$http.post('http://localhost:8081/CollaborationRestService/bulletin/save',$scope.bulletin)
		.then(function(response) {
			$scope.viewBulletin($scope.bulletin.bulletin_id);
		});
	}
	
	$scope.viewBulletin = function(bulletin_id) {
		
		alert('View Bulletin');
		
		$http.get('http://localhost:8081/CollaborationRestService/bulletin/get/'+bulletin_id)
		.then(function(response) {
					
			console.log(response.data);
			
			$rootScope.bulletinData = response.data;
			$location.path('viewBulletin');
			
		});
	}
	
	$scope.update = function(bulletin_id) {
		
		alert('Update Bulletin');
		
		$http.get('http://localhost:8081/CollaborationRestService/bulletin/update'+bulletin_id)
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
	
	showAllBulletins();
	
});