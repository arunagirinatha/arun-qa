function myCtrl($scope, $http,$filter) {
	$scope.readCSV = function() {
		
		var app = angular.module('fileDemo', []);

		app.directive('fdInput', [function () {
			return {
				link: function (scope, element, attrs) {
					element.on('change', function  (evt) {
						var files = evt.target.files;
						console.log(files[0].name);
						console.log(files[0].size);
					});
				}
			}
		}]);

		$http.get('assets/issues.csv').success($scope.processData);
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