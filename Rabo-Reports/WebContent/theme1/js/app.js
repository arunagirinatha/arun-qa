var UploadController = function ($scope, fileReader, $filter) {
    $scope.getFile = function () {
        $scope.progress = 0;
        fileReader.readAsDataUrl($scope.file, $scope)
                      .then(function(result) {
							$scope.imageSrc = result;
							var allText = atob(result.replace("data:;base64,", ""));
							var allTextLines = allText.split(/\r\n|\n/);
							var contentData = [];
							var headers = allTextLines[0].split(',');
							var headerData = [];					
							for ( var i = 0; i < allTextLines.length; i++) {
								var data = allTextLines[i].split(',');
								if (data.length == headers.length) {
									var tarr = [];
									for ( var j = 0; j < headers.length; j++) {
										tarr.push(data[j].replace(/(^"|"$)/g, ''));
									}
									(i==0) ? headerData.push(tarr) : contentData.push(tarr);
								}						
							}
							$scope.headerdata = headerData;
							$scope.headerLength = headers.length;
							$scope.data = contentData;
							$scope.reportFlag = true;
							$scope.$watch("dataFilter", function(query){
								$scope.counted = $filter("filter")(contentData, query).length;
							});	    
                      });
    };
 
    $scope.$on("fileProgress", function(e, progress) {
        $scope.progress = progress.loaded / progress.total;
    });
 
};

app.directive("ngFileSelect",function(){
  return {
    link: function($scope,el){      
      el.bind("change", function(e){      
        $scope.file = (e.srcElement || e.target).files[0];
        $scope.getFile();
      });      
    }    
  }  
});