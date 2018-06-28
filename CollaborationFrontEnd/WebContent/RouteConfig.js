var myApp = angular.module('myApp',['ngRoute','ngCookies']);

myApp.config(function($routeProvider) {

	console.log('Route provider');

	$routeProvider
				//User
  				.when( '/',				{ templateUrl : 'c_user/register.html' })
				.when( '/home',			{ templateUrl : 'c_user/home.html' })
				.when( '/login',		{ templateUrl : 'c_user/login.html' })
				.when( '/register',		{ templateUrl : 'c_user/register.html' })
				.when( '/logout',		{ templateUrl : 'c_user/home.html' })
				.when( '/editProfile',	{ templateUrl : 'c_user/editProfile.html' })
				.when( '/viewUser', 	{ templateUrl : 'c_user/viewUser.html' })
				.when( 'http://localhost:8081/CollaborationRestService/user/upload', { templateUrl : 'c_user/editProfile.html' })
				//Friend
				.when( '/friends',			{ templateUrl : 'c_friend/friends.html' })
				.when( '/suggestedUsers',	{ templateUrl : 'c_friend/suggestedUsers.html' })
				.when( '/blockedUsers',		{ templateUrl : 'c_friend/blockedUsers.html'})
				//Blog
				.when( '/addBlog',		{ templateUrl : 'c_blog/addBlog.html' })
				.when( '/showAllBlogs',	{ templateUrl : 'c_blog/showAllBlogs.html' })
				.when( '/myBlog',		{ templateUrl : 'c_blog/myBlogs.html' })
				.when( '/viewBlog',		{ templateUrl : 'c_blog/viewBlog.html' })
				.when( '/editBlog',		{ templateUrl : 'c_blog/editBlog.html' })
				//Job
				.when( '/showAllJobs',			{ templateUrl : 'c_job/showAllJobs.html' })
				.when( '/myJobApplications',	{ templateUrl : 'c_job/myJobApplications.html' })
				.when( '/addJob',				{ templateUrl : 'c_job/addJob.html' })
				.when( '/viewJob',				{ templateUrl : 'c_job/viewJob.html' })
				//Bulletin
				.when( '/addBulletin',		{ templateUrl : 'c_bulletin/addBulletin.html' })
				.when( '/showAllBulletins',	{ templateUrl : 'c_bulletin/showAllBulletins.html' })
				.when( '/viewBulletin',		{ templateUrl : 'c_bulletin/viewBulletin.html'})
				//Chat
				.when( '/chat',	{ templateUrl : 'c_chat/chat.html', controller : 'ChatCtrl' })
				//Forum
				.when( '/forum',			{ templateUrl : 'c_forum/forum.html' })
				.when( '/addForum',			{ templateUrl : 'c_forum/addForum.html' })
				.when( '/editForum',		{ templateUrl : 'c_forum/editForum.html' })
				.when( '/viewForum',		{ templateUrl : 'c_forum/viewForum.html' })
				.when( '/showAllForums',	{ templateUrl : 'c_forum/showAllForums.html' })
				.when(' /manageForum',		{ templateUrl : 'c_forum/manageForum.html '})
				.when(' /news',				{ templateUrl : 'https://www.indiatoday.in/news.html '})
				.otherwise( {templateUrl : 'c_user/home.html'} )
				;
	  //.when('',{templateUrl:'.html'})


});

myApp.run(function($rootScope, $cookieStore) {

	console.log('I am in run function');

	if($rootScope.currentUser == undefined) {

		$rootScope.currentUser = $cookieStore.get('userCookieData');
		$rootScope.viewTrackerList = $cookieStore.get('viewTrackerList');
		
		console.log($rootScope.currentUser);

	}

});