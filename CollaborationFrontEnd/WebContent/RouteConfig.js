var myApp = angular.module("myApp",['ngRoute','ngCookies']);

	myApp.config(function($routeProvider) {
		
		alert("route provider");

		$routeProvider.when("/",{templateUrl:"c_user/home.html"})
					  .when("/login",{templateUrl:"c_user/login.html"})
					  .when("/register",{templateUrl:"c_user/register.html"})
					  .when("/addBlog",{templateUrl:"c_blog/addBlog.html"})
					  .when("/showAllBlogs",{templateUrl:"c_blog/showAllBlogs.html"})
					  .when("/home",{templateUrl:"c_user/home.html"})
					  .when("/myBlog",{templateUrl:"c_blog/myBlogs.html"})
					  .when("/viewBlog",{templateUrl:"c_blog/viewBlog.html"})
					  .when("/addBulletin",{templateUrl:"c_bulletin/addBulletin.html"})
					  .when("/showAllBulletins",{templateUrl:"c_bulletin/showAllBulletins.html"})
					  .when("viewBulletin",{templateUrl:"c_bulletin/viewBulletin.html"})
					  ;
		//.when("",{templateUrl:".html"})
		  

	});