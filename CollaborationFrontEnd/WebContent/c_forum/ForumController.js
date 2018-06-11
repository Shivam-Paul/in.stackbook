myApp.controller("ForumController", function($scope, $rootScope) {
	
	$scope.Forum = {
			'forum_id' : 0,
			'parent_id' : 0,
			'approved' : '',
			'email_id' : '',
			'content' : '',
			'forum_level' : 0,
			'new_post_access' : 0,
			'view_access' : 0,
			'archived' : '',
			'score' : 0,
			'date_created' : '',
			'comment_access' : 0,
			'title' : '',
	};
	
	$scope.addForum = function() {
		
		alert('Add Forum');
		
	}
	
});