function myCtrl($scope, $http,$filter) {
	$scope.getLatLonGFile = function () {
        var FileUpload = document.getElementsByClassName('AddLocationUpload');
        $scope.file = FileUpload[0].files[0];
        for (var i = 0; i < FileUpload.length; i++) {
            FileUpload[i] = function () {
                $scope.file = this.files[0];
            }
        }
		
    } 
	
	$scope.readCSV = function() {
	
		$http.get('C:/xampp/htdocs/ang/assets/issues2.csv').success($scope.processData);
	};
	
	$scope.processData = function(allText) {
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
		$scope.$watch("dataFilter", function(query){
			$scope.counted = $filter("filter")(contentData, query).length;
		});		
	};
}