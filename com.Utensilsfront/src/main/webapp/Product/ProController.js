app.service('fileUpload', [ '$http', function($http) {
		this.uploadFileToUrl = function(file, paramuser, uploadUrl) {
			var fd = new FormData();
			fd.append('file', file);
			//fd.append('user','vasudev89');
			return $http.post(uploadUrl, fd, {
				transformRequest : angular.identity,
				headers : {
					'Content-Type' : undefined,
					user : paramuser
				}
			}).then(function(response) {
				return response.data;
			}, function(errResponse) {
				console.error('Error while updating User');
				return "error";
			});
		}
	} ]);

app.controller('ProController', [
		'$scope',
		'$location',
		'ProductService',
		'fileUpload',
		function($scope, $location, $ProductService,$fileUpload) {
			console.log('ProController');
			$scope.ProductID = '';
			$scope.ProductName = '';
			$scope.ProductPrice = '';
			$scope.ProductDesc = '';
			$scope.ProductQuant = '';
			$scope.ProductCategory = '';
			$scope.ProductImage = '';

			console.log('ProController');
			$scope.CreateProduct = function() {

				{

					var json = {

						"ProductName" : $scope.ProductName,
						"ProductPrice" : $scope.ProductPrice,
						"ProductDesc" : $scope.ProductDesc,
						"ProductCategory" : $scope.ProductCategory,
						"ProductQuant" : $scope.ProductQuant,

					};

					console.log(json);

					$ProductService.InsertProduct(json).then(
							function(response) {
								console.log(response);

								$scope.ServerResponse = response.msg;
								$location.path('/home');
								window.setTimeout(function() {
									$scope.$apply($scope.ServerResponse = '');
								}, 5000);
							});
				}
			}

			$scope.data = [];

			$ProductService.ViewProduct().then(function(response) {
				console.log(response);

				$scope.data = response.data;
			});

			$scope.editedItem = {};
			$scope.editrow = function($index) {
				$scope.istrue = true;
				$scope.$index = $index;
				angular.copy($scope.data[$index], $scope.editedItem);
				$scope.editedItem.ProductId;
				$scope.editedItem.ProductName;
				$scope.editedItem.Productategory;
				$scope.editedItem.ProductDescription;
				$scope.editedItem.ProductPrice;
				$scope.editedItem.ProductQuantity
				
				console.log($scope.editedItem);
			}

			$scope.DeleteProduct = function(arg) {

				var json = {

					"ProductID" : arg,

				};
				console.log(json);
				$ProductService.DeleteProduct(json).then(function(response) {
					$ProductService.ViewProduct().then(function(response) {
						console.log(response);

						$scope.data = response.data;
					});
					console.log(response);
				});
			}

			$scope.UpdateProduct = function() {
				console.log("inside update");

				{
					var json = {
						"ProductID" : $scope.editedItem.ProductId,
						"ProductName" : $scope.editedItem.ProductName,
						"ProductPrice" : $scope.editedItem.ProductPrice,
						"ProductDesc" : $scope.editedItem.ProductDescription,
						"ProductCategory" : $scope.editedItem.Productategory,
						"ProductQuant" : $scope.editedItem.ProductQuantity,

					};

					$ProductService.UpdateProduct(json).then(function(response) {
						$ProductService.ViewProduct().then(function(response) {
							console.log(response);

							$scope.data = response.data;
						});
						console.log(response);

					});
				}
			}
			$scope.uploadedFile = function(element) {
				 $scope.$apply(function($scope) {
				   $scope.files = element.files;         
				 });
				}
			
			$scope.addFile = function() {
				 UploadService.uploadfile($scope.files,
				   function( msg ) // success
				   {
				    console.log('uploaded');
				   },
				   function( msg ) // error
				   {
				    console.log('error');
				   });
				}
			
			// open File Explorer for seleting file/image
			$scope.openFileChooser = function() {
				$('#trigger').trigger('click');
			};
			$scope.picUpdated = false;
			$scope.picUpdatedWithError = false;
			$scope.invalidPicType = false;
			/* $scope.picDeleted = false;  */
			
			$scope.setFile = function(element) {
				$scope.currentFile = element.files[0];
				var reader = new FileReader();
				reader.onload = function(event) {
					$scope.ProductImage = event.target.result
					$scope.$apply()
				};
				// when the file is read it triggers the onload event above.
				reader.readAsDataURL($scope.currentFile);
				var file = $scope.currentFile;
				console.log('file is :');
				console.dir(file);
				var uploadUrl = "http://localhost:8080/com.Utensils1/upload";
				// calling uploadFileToUrl function of $fileUpload
				var res = $fileUpload
						.uploadFileToUrl(file,
								$scope.ProductID,
								uploadUrl)
						.then(
								function(response) {
									$scope.status = response.status;
									$scope.imagesrc = response.imagesrc;
									$scope.picDeleted = false;
									//console.log( $scope.response );
									//console.log( $scope.imagesrc );
									$scope.currentImage = '/'
											+ $scope.imagesrc;
									$scope.stateDisabled = false;
								},
								function(errResponse) {
									console
											.error('Error while Updating User.');
								});
			};
			
			
	} ]);


