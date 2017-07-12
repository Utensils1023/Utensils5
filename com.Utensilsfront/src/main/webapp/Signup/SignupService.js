app.factory('signUpService', ['$http', '$q', function($http, $q){
	 

	var BASE_URL = 'http://localhost:8080/com.Utensils1';
	
    return {
         		InsertUser: function(item){
         			
         			console.log( 'Insert User Service:' );
         			console.log(item);
         			
                    return $http({
                    	  method: 'POST',
                    	  url: BASE_URL + '/adduser',
                    	  data:item,
                    	  headers:{'Content-Type': 'application/json'}
                    	})
                            .then(
                                    function(response){
                                        return response.data;
                                    }, 
                                    function(errResponse){
                                        console.error('Error while updating User');
                                        return $q.reject(errResponse);
                                    }
                            );
            		
    		}
    		,
	
    };
      // this api is use to check a email to database
    return {
 		InsertUser: function(item){
 			
 			console.log( 'Insert User Service:' );
 			console.log(item);
 			
            return $http({
            	  method: 'POST',
            	  url: BASE_URL + '/getuserdata',
            	  data:item,
            	  headers:{'Content-Type': 'application/json'}
            	})
                    .then(
                            function(response){
                                return response.data;
                            }, 
                            function(errResponse){
                                console.error('Error while updating User');
                                return $q.reject(errResponse);
                            }
                    );
    		
	}
	,

};    		
		
    		
    
 
}])

