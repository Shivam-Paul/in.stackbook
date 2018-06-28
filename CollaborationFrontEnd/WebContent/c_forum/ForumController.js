myApp.controller('ForumController', function($scope, $http, $rootScope, $location, $cookieStore) {
	
	$scope.forum = {
			forum_id : 0,
			parent_id : 0,
			approved : '',
			email_id : '',
			content : '',
			forum_level : 0,
			new_post_access : 0,
			view_access : 0,
			archived : '',
			date_created : '',
			comment_access : 0,
			title : ''
	};
	
	$scope.roles = [
			{value : '1', role : 'Anyone'},
			{value : '2', role : 'Student or higher'},
			{value : '3', role : 'Moderator or higher'},
			{value : '4', role : 'Teacher or higher'},
			{value : '5', role : 'Admins only'}
	];
	
	$rootScope.forumData = {
			forum_id : 0,
			parent_id : 0,
			approved : '',
			email_id : '',
			content : '',
			forum_level : 0,
			new_post_access : 0,
			view_access : 0,
			archived : '',
			date_created : '',
			comment_access : 0,
			title : ''
	};
	$rootScope.parentForum;
	$rootScope.currentLevel;
	$scope.pendingApprovalForums;
	$scope.listApprovedForums;
	$scope.approvedForumsInCurrentLevelByParentID;
	
		
	$scope.addForum = function() {
		
		console.log('Add Forum');
		
		$scope.forum.parent_id = $rootScope.parentForum;
		$scope.forum.forum_level = $rootScope.currentLevel + 1;
		$scope.forum.email_id = $rootScope.currentUser.email_id;
		
		$http.post('http://localhost:8081/CollaborationRestService/forum/save', $scope.forum)
		.then(function(response) {
			$scope.viewForum($scope.forum.forum_id);
			$location.path('forum');
		});
	}
	
	/*$scope.updateForum = function(forum_id) {
		
		console.log('Update Forum');
		
		$scope.getForum(forum_id);
		
		$scope.forum.forum_id = forum_id;
		$scope.forum.parent_id = $rootScope.forumData.parent_id;
		$scope.forum.email_id = $scope.forumData.email_id;
		$scope.forum.forum_level = $scope.forumData.forum_level;
		$scope.forum.date_created = $scope.forumData.date_created;
		
		$http.post('http://localhost:8081/CollaborationRestService/forum/update', $scope.forum)
		.then(function(response) {
			$scope.viewForum($scope.forum.forum_id);
		});
	}*/
	
	$scope.approveForum = function(forum_id) {
		
		console.log('Approve Forum');
		
		$http.get('http://localhost:8081/CollaborationRestService/forum/approveForum/' + forum_id)
		.then(function(response) {
			$location.path('manageForum');
		});
		
	}
	
	$scope.viewForum = function(forum_id) {
		
		console.log('View Forum');
		
		$http.get('http://localhost:8081/CollaborationRestService/forum/get/' + forum_id)
		.then(function(response) {
			$scope.listForumComments(forum_id);
			$rootScope.parentForum = forum_id;
			$rootScope.forumData = response.data;
			$rootScope.currentLevel = forumData.forum_level;
			$scope.listForumComments(forum_id);
			$location.path('/viewForum');
		});
	}
	
	$scope.deleteForum = function(forum_id) {
		
		console.log('Delete Forum');
		
		$http.get('http://localhost:8081/CollaborationRestService/forum/delete/' + forum_id)
		.then(function(response) {
			console.log('Deleted Forum with ID: ' + forum_id);
		});
	}
	
	$scope.listByParentID = function(parent_id) {
		
		$http.get('http://localhost:8081/CollaborationRestService/forum/list/parentID/' + parent_id)
		.then(function(response) {
			$scope.parentIDList = response.data;
			$location.path('forumList');
		});
	}
	
	$scope.listApprovedForumsInCurrentLevelByParentID = function(forum_level, parent_id) {
		
		$http.get('http://localhost:8081/CollaborationRestService/forum/list/approvedForumsInCurrentLevelByParentID/'
				+ forum_level + '/' + parent_id)
		.then(function(response) {
			$scope.approvedForumsInCurrentLevelByParentID = response.data;
			$location.path('forum');
		});
	}
	
	$scope.getForum = function(forum_id) {
		
		console.log('Get Forum');
		
		$http.get('http://localhost:8081/CollaborationRestService/forum/get/' + forum_id)
		.then(function(response) {
			$scope.forumData = response.data;
			listForumComments(forum_id);
		});
	}
	
	function listPendingApprovalForums() {
		
		if($rootScope.currentUser.role==5) {
			console.log('List not approved Forum');
			
			$http.get('http://localhost:8081/CollaborationRestService/forum/list/pendingApproval')
			.then(function(response) {
				$scope.pendingApprovalForums = response.data;
				console.log($scope.pendingApprovalForums);
			});
		}
		
	}
	
	function listAllApprovedForums() {
		
		var approved = 'A';
		
		$http.get('http://localhost:8081/CollaborationRestService/forum/list/approved/' + approved)
		.then(function(response) {
			$scope.listApprovedForums = response.data;
		});
		
	}
	
	listPendingApprovalForums();
	listAllApprovedForums();
	
	//Forum Comment
	
	
	$scope.forumCommentData;
	$scope.commentData;
	$scope.listForumComments;
	
	$scope.forumComment = {
			f_comment_id : 0,
			forum_id : 0,
			forum_comment : '',
			email_id : '',
			comment_date : ''
	}
	
	$scope.addComment = function(forum_id) {
		
		console.log('Add Forum Comment');
		
		$scope.forumComment.email_id = $rootScope.currentUser.email_id;
		$scope.forumComment.forum_id = forum_id;
		
		$http.post('http://localhost:8081/CollaborationRestService/forum/comment/save', $scope.forumComment)
		.then(function(response) {
			$scope.viewForum(forum_id);
		});
		
	}
	
	$scope.updateComment = function(f_comment_id) {
		
		console.log('Update Forum Comment');
		
		$scope.get(f_comment_id);
		
		$scope.forumComment.f_comment_id = f_comment_id;
		$scope.forumComment.forum_id = $scope.commentData.forum_id;
		$scope.forumComment.email_id = $scope.commentData.email_id;
		$scope.forumComment.comment_date = $scope.commentData.comment_date;
		
		$http.post('http://localhost:8081/CollaborationRestService/forum/comment/save', $scope.forumComment)
		.then(function(response) {
			$scope.viewForum($scope.commentData.forum_id);
		});
		
	}
	
	$scope.getComment = function(f_comment_id) {
		
		console.log('Get Forum Comment');
		
		$http.get('http://localhost:8081/CollaborationRestService/forum/comment/get/' + f_comment_id)
		.then(function(response) {
			$scope.commentData = response.data;
		});
		
	}
	
	$scope.listForumComments = function(forum_id) {
		
		console.log('List Forum Comments');
		
		$http.get('http://localhost:8081/CollaborationRestService/forum/comment/list/' + forum_id)
		.then(function(response) {
			$scope.forumCommentData = response.data;
		});
		
	}
	
	$scope.deleteComment = function(b_comment_id, forum_id) {
		
		console.log('Delete Forum Comment');
		
		$http.get('http://localhost:8081/CollaborationRestService/forum/comment/delete' + b_comment_id)
		.then(function(response) {
			$scope.viewForum(forum_id);
		});
		
	}
	
	
});