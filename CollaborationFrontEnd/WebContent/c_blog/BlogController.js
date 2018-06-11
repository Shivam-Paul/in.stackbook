myApp.controller("BlogController", function($scope, $http, $rootScope, $location) {
	
	$scope.blog = {'blogID':0, 'title':'', 'content':'', 'email_id':'', 'approved':'', 'date_created':'', 'views':0};
	
	$scope.blogData;
	$scope.allBlogData;
	
	$scope.addBlog = function() {
		
		alert('Add Blog');
		
		console.log('Adding Blog');
		
		$scope.blog.email_id = $rootScope.currentUser.email_id;
		
		$http.post('http://localhost:8081/CollaborationRestService/blog/save', $scope.blog)
		.then(function(response) {
			$scope.viewBlog($scope.blog.blog_id);
		});
		
	}
	
	$scope.viewBlog = function(blog_id) {
		
		alert('View Blog');
		
		$http.get('http://localhost:8081/CollaborationRestService/blog/get/' + blog_id)
		.then(function(response) {
			
			$scope.listAllBlogComment(blog_id);
			
			console.log(response.data);
			
			$rootScope.blogData = response.data;
			$location.path('viewBlog');
			
		});
	}
	
	function showAllBlogs() {
		console.log('List All Blogs');
		$http.get('http://localhost:8081/CollaborationRestService/blog/list')
		.then(function(response) {
			$scope.allBlogData=response.data;
			//console.log(response.data)
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
			console.log(reponse.data);
		});
	}
	
	showAllBlogs();
	
	
	// Blog Comment
	
	
	$scope.blogCommentData;
	$scope.blogData;
	
	$scope.blogComment = {
			b_comment_id : 0,
			blog_id : 0,
			blog_comment : '',
			comment_date : '',
			email_id : '',
			score : 0
	}
	
	$scope.addComment = function(blog_id) {
		
		$scope.blogComment.email_id = $rootScope.currentUser.email_id;
		$scope.blogComment.blog_id = blog_id;
		
		console.log($scope.blogComment);
		
		$http.post('http://localhost:8081/CollaborationRestService/blog/comment/save', $scope.blogComment)
		.then(function(response) {
			
			alert('Add Comment');
			$scope.viewBlog(blog_id);
			
		});
	}

	$scope.listAllBlogComment = function(blog_id) {
		
		$http.get('http://localhost:8081/CollaborationRestService/blog/comment/list/' + blog_id)
		.then(function(response) {
			
			console.log(response.data);
		
			$rootScope.blogCommentData = response.data;
			
		});
	}
	
	$scope.deleteComment = function(b_comment_id, blog_id) {
		
		alert('Delete Comment');
		
		$http.delete('http://localhost:8081/CollaborationRestService/blog/comment/delete/' + b_comment_id)
		.then(function(response) {
			
			$scope.viewBlog(blog_id);
			
		});
	}
	
});



