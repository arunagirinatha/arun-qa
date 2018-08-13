<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
		<title>Rabo Reports</title>
		<link rel="stylesheet" type="text/css" href="theme1/css/style.css" >
		<script src="theme1/js/jquery-3.3.1.min.js"></script>
		<script data-require="angular.js@1.4.x" src="theme1/js/angular.js" data-semver="1.4.8"></script>
		<script> var app = angular.module('csvReport', []); </script>
		<script src="theme1/js/upload.js"></script>
		<script src="theme1/js/app.js"></script>
		<script>
			$(document).ready(function(){
				$('#failedReportGenerator a').on('click', function (e) {
				   $.ajax({
				   type:'GET',
				   url :"reports/failedStatementsReport",
				   success: function(data) {
				        console.log('success',data);
				        alert("Reports are successfully generated!");
				   },
				   error:function(exception){
				  	 console.log('Exeption:',exception);
				  	 alert("Error in generating the reports!");
				   }
				}); 
				 e.preventDefault();
				});
			});
		</script>
	</head>
<body>
<h4 align="center" class="title">Rabo Reports</h4>
<div id="failedReportGenerator" style="padding-bottom: 1px;">
	<span class="title">Failed Reports</span>
	<a href="#" >Generate Failed Reports</a>
</div>
		<span class="title">Issues</span>
		<div ng-app="csvReport" ng-controller="UploadController ">
		    <div class="form-elements">
				<input type="file" ng-file-select="onFileSelect($files)" >
		    </div>
			<div id="page-wrapper" ng-init="reportFlag=false" ng-if="reportFlag">
				<div id="search-wrapper">
					<div class="col-6">
						<span class="title">Issues Report</span>					
					</div>
					<div class="col-6">
						<input type="text" placeholder="Search..." ng-model="dataFilter">
					</div>
				</div>
				<div id="table-wrapper">
					<table class="table" cellspacing="0" cellpadding="0">
						<thead>
							<tr ng-repeat="x in headerdata">
								<th ng-repeat="y in x">{{ y }}</td>
							</tr>
						</thead>
						<tbody>
							<tr ng-repeat="x in data | filter:dataFilter">
								<td ng-repeat="y in x">{{ y }}</td>
							</tr>
							<tr ng-if="counted==0">
								<td class="no-data" colspan="{{headerLength}}"> No data found.... </td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>			
		</div>
</body>
</html>
