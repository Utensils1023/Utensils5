var app = angular.module("myApp", ['ngRoute','ngAnimate','ui.router']);
app.config(function($stateProvider, $routeProvider) {
    
	$stateProvider	
    .state('Signupform', {
        templateUrl: 'Signup/Signupform.html',
        controller: 'signUpController'
    })
    
    // nested states 
    // each of these sections will have their own view
    // url will be nested (/form/profile)
    .state('Signupform.profile', {
        templateUrl: 'Signup/page1.html'
    })
    
    // url will be /form/interests
    .state('Signupform.interests', {
        templateUrl: 'Signup/page2.html'
    })
    
    // url will be /form/payment
    .state('Signupform.payment', {
        templateUrl: 'Signup/page3.html'
    });
	
 /*   // url will be /form/interests
	  .state('Signup', {
         url: '/Signup',
         templateUrl: 'Signup/Signup.html',
         controller: 'signUpController'
     })
     
     // nested states 
     // each of these sections will have their own view
     // url will be nested (/form/profile)
     .state('.profile', {
         url: '/profile',
         templateUrl: 'Signup/page1.html'
     })
     
     // url will be /form/interests  
     .state('.interests', {
         url: '/interests',
         templateUrl: 'Signup/page2.html'
     })
     
     // url will be /form/payment
     .state('.payment', {
         url: '/payment',
         templateUrl: 'Signup/page3.html'
     });*/
    
	
	$routeProvider
    .when("/Product", {
        templateUrl : "Product/Product.html",
        controller: "ProController"
  })
     .when("/Category", {
        templateUrl : "Category/Category.html",
        controller: "CategoryController"
  })
   .when("/home", {
        templateUrl : "home/home.html",
        controller: "homeController"
 
   })
     .when("/ViewProduct", {
        templateUrl : "ViewProduct/ViewProduct.html",
        controller: "ProController"
 
   })
     .when("/UpdateProduct", {
        templateUrl : "UpdateProduct/UpdateProduct.html",
        controller: "ProController"
 
   })
        .when("/ViewCategory", {
        templateUrl : "ViewCategory/ViewCategory.html",
        controller: "CategoryController"
 
   })
     .when("/UpdateCategory", {
        templateUrl : "UpdateCategory/UpdateCategory.html",
        controller: "CategoryController"
 
   })
     .when("/Signup", {
        templateUrl : "Signup/Signup.html",
        controller: "signUpController"
 
   })
       .when("/login", {
        templateUrl : "login/login.html",
        controller: "loginController"
 
   })
   
  
});
/*app.config(['$stateProvider',function($stateProvider){
	
  
	
}]);*/
