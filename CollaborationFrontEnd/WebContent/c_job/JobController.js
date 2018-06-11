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
	
	$scope.jobApplicationData;
	$scope.allJobApplicationData;
	$scope.myJobApplicationData;
	
	$scope.addJob = function() {
		
		alert('Add Job');
		
		console.log('Adding Job');
				
		$http.post('http://localhost:8081/CollaborationRestService/job/save',$scope.job)
		.then(function(response) {
			
			$scope.viewJob($scope.job.job_id);
		});
		
	}
	
	$scope.viewJob = function(job_id) {
		
		alert('View Job');
		
		$http.get('http://localhost:8081/CollaborationRestService/job/get/'+job_id)
		.then(function(response) {
			
			viewJobApplication(job_id, $scope.currentUser.email_id);
						
			console.log(response.data);
			
			$rootScope.jobData = response.data;
			$location.path('viewJob');
			
		});
	}
	
	$scope.getJob = function(job_id) {
		
		alert('Get Job');
		
		$http.get('http://localhost:8081/CollaborationRestService/job/get/'+job_id)
		.then(function(response) {
			
			$scope.jobData = response.data; 
			
		});
		
	}
	
	$scope.updateJob = function(job_id) {
		
		alert('Update Job');
		
		$http.get('http://localhost:8081/CollaborationRestService/job/update'+job_id)
		.then(function(response) {
			
			console.log('Update Job');
			
			$scope.viewJob(job_id);
		});
	}
	
	function showAllJobs() {
		console.log('List All Jobs');
		$http.get('http://localhost:8081/CollaborationRestService/job/list')
		.then(function(response) {
			$scope.allJobData=response.data;
		});
	}
	
	$scope.deleteJob = function(job_id) {
		
		alert('Delete Job');
		
		$http.get('http://localhost:8081/CollaborationRestService/job/delete/'+job_id)
		.then(function(response) {
			console.log(reponse.data);
		});
		
	}
	
	showAllJobs();
	
	
	//JobApplication 
	
	
	$scope.addJobApplication = function(job_id) {
		
		alert('Add Job Application');
		
		console.log('Adding Job Application');
				
		$http.post('http://localhost:8081/CollaborationRestService/job/application/save',$scope.jobApplication)
		.then(function(response) {
			$scope.viewJob($scope.jobApplication.job_id);
		});
	}
	
	$scope.viewJobApplication = function(job_id, email_id) {
		
		alert('View Job Application');
		
		$http.get('http://localhost:8081/CollaborationRestService/job/application/check/'+job_id+'/'+email_id)
		.then(function(response) {
			$rootScope.jobApplicationData = response.data;
			console.log(response.data);
		});
	}
	
	function showAllJobApplications(job_id) {
		
		alert('List of Job Applications');
		
		$http.get('http://localhost:8081/CollaborationRestService/job/application/list/'+job_id)
		.then(function(response) {
			$scope.allJobApplicationData = response.data;
		});
	}
	
	function myJobApplications(email_id) {
		
		alert('My Job Applications');
		
		$http.get('http://localhost:8081/CollaborationRestService/job/application/myList'+email_id)
		.then(function(response) {
			$scope.myJobApplicationData = response.data;
		});
		
	}
	
	$scope.updateJobApplication = function(job_application_id, job_id) {
		
		alert('Update Job Application');
		
		$http.get('http://localhost:8081/CollaborationRestService/job/application/update'+job_application_id)
		.then(function(response) {
			
			console.log('Update Job Application');
			
			$scope.viewJob(job_id);
		});
	}
	
	$scope.deleteJobApplication = function(job_application_id) {
		
		alert('Delete Job Application');
		
		$http.get('http://localhost:8081/CollaborationRestService/job/application/delete/'+job_application_id)
		.then(function(response) {
			console.log(reponse.data);
		});
		
	}
	
	myJobApplications(currentUser.email_id);
	
	
	
});