myApp.controller("JobController", function($scope, $http, $rootScope, $location) {
	
	$scope.job = {
			jobID : 0,
			title : '',
			salary : 0,
			company_name : '',
			qualification : '',
			date_created : '',
			apply_before : 0,
			status : '',
			openings : 0,
			description : ''
	};
	
	$scope.jobData;
	$scope.allJobData;
	
	$scope.jobApplication = {
			job_application_id : 0,
			job_id : 0,
			email_id : '',
			date_applied : '',
			status : ''
	};
	
	$rootScope.jobApplicationData;
	
	$scope.allJobApplicationData;
	$scope.myJobApplicationData;
	
	var date = new Date();
	var dd = date.getDate();
	var mm = date.getMonth()+1;
	var yyyy = date.getFullYear();
	var yyyy2 = date.getFullYear()+1;
	
	$scope.today = dd + '-' + mm + '-' + yyyy;
	$scope.applyBefore = dd + '-' + mm + '-' + yyyy2;
	
	$scope.today2 = dd + '/' + mm + '/' + yyyy;
	$scope.applyBefore2 = dd + '/' + mm + '/' + yyyy2;
	
	/*console.log($scope.today);
	console.log($scope.applyBefore);*/
	
	$scope.addJob = function() {
				
		console.log('Adding Job');
				
		$http.post('http://localhost:8081/CollaborationRestService/job/save', $scope.job)
		.then(function(response) {
			
			$location.path('showAllJobs');
			
		});
	}
	
	$scope.viewJob = function(job_id) {
		
		console.log('View Job');
		
		$http.get('http://localhost:8081/CollaborationRestService/job/get/' + job_id)
		.then(function(response) {
			
			$scope.viewJobApplication(job_id, $scope.currentUser.email_id);
			
			$rootScope.jobData = response.data;
			$location.path('viewJob');
			
		});
	}
	
	$scope.getJob = function(job_id) {
		
		console.log('Get Job');
		
		$http.get('http://localhost:8081/CollaborationRestService/job/get/' + job_id)
		.then(function(response) {
			
			$scope.jobData = response.data; 
			
		});
		
	}
	
	$scope.updateJob = function(job_id) {
		
		console.log('Update Job');
		
		$http.get('http://localhost:8081/CollaborationRestService/job/update' + job_id)
		.then(function(response) {
			
			console.log('Update Job');
			
			$scope.viewJob(job_id);
		});
	}
	
	function showAllJobs() {
		console.log('List All Jobs');
		$http.get('http://localhost:8081/CollaborationRestService/job/listAllJobs')
		.then(function(response) {
			$scope.allJobData=response.data;
		});
	}
	
	$scope.deleteJob = function(job_id) {
		
		console.log('Delete Job');
		
		$http.get('http://localhost:8081/CollaborationRestService/job/delete/' + job_id)
		.then(function(response) {
			console.log(reponse.data);
		});
		
	}
	
	showAllJobs();
	
	
	//JobApplication 
	
	
	$scope.addJobApplication = function(job_id) {
				
		console.log('Adding Job Application');
				
		$http.get('http://localhost:8081/CollaborationRestService/job/application/save/' + job_id)
		.then(function(response) {
			$location.path('showAllJobs');
		});
	}
	
	$scope.viewJobApplication = function(job_id, email_id) {
		
		console.log('View Job Application');
		
		$http.get('http://localhost:8081/CollaborationRestService/job/application/check/' + job_id)
		.then(function(response) {
			$rootScope.jobApplicationData = response.data;
			console.log(response.data);
		});
	}
	
	$scope.showAllJobApplications = function(job_id) {
		
		console.log('List of Job Applications');
		
		$http.get('http://localhost:8081/CollaborationRestService/job/application/list/' + job_id)
		.then(function(response) {
			$scope.allJobApplicationData = response.data;
		});
	}
	
	function myJobApplications() {
		
		console.log('My Job Applications');
		
		$http.get('http://localhost:8081/CollaborationRestService/job/application/myList')
		.then(function(response) {
			$scope.myJobApplicationData = response.data;
		});
		
	}
	
	$scope.updateJobApplication = function(job_application_id, job_id) {
		
		console.log('Update Job Application');
		
		$http.get('http://localhost:8081/CollaborationRestService/job/application/update' + job_application_id)
		.then(function(response) {
						
			$scope.viewJob(job_id);
		});
	}
	
	$scope.deleteJobApplication = function(job_application_id) {
		
		console.log('Delete Job Application');
		
		$http.get('http://localhost:8081/CollaborationRestService/job/application/delete/' + job_application_id)
		.then(function(response) {
			console.log(reponse.data);
		});
		
	}
	
	myJobApplications();	
	
});