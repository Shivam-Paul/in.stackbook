myApp.controller("BlogController", function($scope, $http, $rootScope, $location, $cookieStore) {
	
	$scope.blog = {
		blog_id : 0,
		title : '',
		content : '',
		email_id : '',
		approved : '',
		date_created : '',
		views : 0
	};
	
	$scope.viewTracker;
	
	$scope.blogData;
	$scope.allBlogData;
	$scope.approvalBlogData;
	$scope.myBlogData;
	$rootScope.selectedBlog;
	
	$scope.addBlog = function() {
		
		console.log('Add Blog');
		
		$scope.blog.email_id = $rootScope.currentUser.email_id;
		
		$http.post('http://localhost:8081/CollaborationRestService/blog/save', $scope.blog)
		.then(function(response) {
			$scope.blog.title = '';
			$scope.blog.content = '';
			$location.path('addBlog');
		});
		
	}
	
	$scope.editBlog = function(blog_id) {
		
		$http.get('http://localhost:8081/CollaborationRestService/blog/get/' + blog_id)
		.then(function(response) {
			$rootScope.selectedBlog = response.data;
			console.log($rootScope.selectedBlog);
			$location.path('editBlog');
		});
		
	}
	
	$scope.update = function() {
		
		console.log('Update Blog');
		
		/*$http.get('http://localhost:8081/CollaborationRestService/blog/get/' + blog_id)
		.then(function(response) {
		
		$scope.temp = response.data;
		
		
		$scope.blog.blog_id = $scope.temp.blog_id;
		$scope.blog.email_id = $scope.temp.email_id;
		$scope.blog.approved = $scope.temp.approved;
		$scope.blog.date_created = $scope.temp.date_created;
		$scope.blog.views = $scope.temp.views;*/
		
		$http.post('http://localhost:8081/CollaborationRestService/blog/update', $rootScope.selectedBlog)
		.then(function(response) {
			$location.path('myBlog')
		});
		
	}
	
	$scope.viewBlog = function(blog_id) {
		
		console.log('View Blog');
		
		$http.get('http://localhost:8081/CollaborationRestService/blog/get/' + blog_id)
		.then(function(response) {
			
			var tempList = $cookieStore.get('viewTrackerList');
			var tempVar = null;
			var temp = 0;
			for(var i = 0, size = tempList.length; i < size; i++) {
				temp = tempList[i];
				if(temp == blog_id) {
					tempVar = temp;
					break;
				}
			}
			console.log(tempList);
			console.log(tempVar);
			if(tempVar == null) {
				
				console.log('Add Views');
				
				$http.get('http://localhost:8081/CollaborationRestService/blog/addView/' + blog_id)
				.then(function(response) {
					$scope.viewTracker = blog_id;
					$rootScope.viewTrackerList.push($scope.viewTracker);
					$cookieStore.put('viewTrackerList', $rootScope.viewTrackerList);
					console.log($cookieStore.get('viewTrackerList'));
				});
			}
			
			$scope.listAllBlogComment(blog_id);
						
			$rootScope.blogData = response.data;
			$location.path('viewBlog');
			
		});
	}
	
	$scope.getBlog = function(blog_id) {
		
		console.log('Get Blog');
		
		$http.get('http://localhost:8081/CollaborationRestService/blog/get/' + blog_id)
		.then(function(response) {
			$scope.temp = response.data;
		});
		
	}
	
	function showAllBlogs() {
		console.log('List All Blogs');
		$http.get('http://localhost:8081/CollaborationRestService/blog/list')
		.then(function(response) {
			$scope.allBlogData=response.data;
		});
	}
	
	$scope.showBlogsByApproval = function(approved) {
		
		console.log('List By Approval');
		
		$http.get('http://localhost:8081/CollaborationRestService/blog/listByApproval/' + approved)
		.then(function(response) {
			$scope.approvalBlogData = response.data;
			$location.path('approvalBlog');
		});
	}
	
	$scope.myBlog = function() {
		
		console.log('My blogs');
		
		$http.get('http://localhost:8081/CollaborationRestService/blog/listUserBlogs')
		.then(function(response) {
			$scope.myBlogData = response.data;
			$location.path('myBlog');
		});
	}
	
	$scope.updateBlog = function(blog_id, approved) {
		
		console.log('Approve Blog');
		
		$http.post('http://localhost:8081/CollaborationRestService/blog/update/' + blog_id + '/' + approved)
		.then(function(response) {
			$location.path('viewBlog');
		});
	}
	
	$scope.deleteBlog = function(blog_id) {
		
		console.log('Delete Blog');
		
		$http.get('http://localhost:8081/CollaborationRestService/blog/delete/' + blog_id)
		.then(function(response) {
			//$scope.myBlog();
			$location.path('showAllBlogs');
		});
	}
	
	showAllBlogs();
	
	
	// Blog Comment
	
	
	$scope.blogCommentData;
	$scope.commentData;
	
	$scope.blogComment = {
			b_comment_id : 0,
			blog_id : 0,
			blog_comment : '',
			comment_date : '',
			email_id : '',
	}
	
	$scope.addComment = function(blog_id) {
		
		$scope.blogComment.email_id = $rootScope.currentUser.email_id;
		$scope.blogComment.blog_id = blog_id;
		
		console.log('Add Blog Comment');
		
		$http.post('http://localhost:8081/CollaborationRestService/blog/comment/save', $scope.blogComment)
		.then(function(response) {
			$scope.viewBlog(blog_id);	
		});
	}
	
	$scope.updateComment = function(b_comment_id) {
		
		console.log('Update Blog Comment');
		
		$http.post('http://localhost:8081/CollaborationRestService/blog/comment/update', $scope.blogComment)
		.then(function(response) {
			$scope.viewBlog(blog_id);
		});
		
	}
	
	$scope.getComment = function(b_comment_id) {
		
		console.log('Get Blog Comment');
		
		$http.get('http://localhost:8081/CollaborationRestService/blog/comment/get/' + b_comment_id)
		.then(function(response) {
			$scope.commentData = response.data;
		})
	}

	$scope.listAllBlogComment = function(blog_id) {
		
		console.log('List All Blog Comment');

		
		$http.get('http://localhost:8081/CollaborationRestService/blog/comment/list/on/' + blog_id)
		.then(function(response) {
			$rootScope.blogCommentData = response.data;
		});
	}
	
	$scope.deleteComment = function(b_comment_id, blog_id) {
		
		console.log('Delete Blog Comment');
		
		$http.get('http://localhost:8081/CollaborationRestService/blog/comment/delete/' + b_comment_id)
		.then(function(response) {
			
			$scope.viewBlog(blog_id);
			
		});
	}
	
});



