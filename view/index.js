var testModule=angular.module('testApp',[]);
var serverIpAddress="54.149.195.217";



testModule.controller('testCtrl',function($scope,$http,$window,$attrs)	
{


	var queryParam=$window.location.search;
	console.log("Received query param => "+queryParam);
	var tempArr=queryParam.split("&");
	var orderId = tempArr[0].split('=')[1];
	console.log('Fetching order details');
	var restaruentId=tempArr[1].split('=')[1];
	console.log('restaruentId is=>'+restaruentId);
	var config = {headers:  {
    	    					'Accept': 'application/json',
        						'Content-Type':'application/json',
    						}
  				};


  				//demo-0.0.1-SNAPSHOT
	$http.get("http://"+serverIpAddress+":8080/demo-0.0.1-SNAPSHOT/feedbackReview/getOrderDetail/"+orderId+"/"+restaruentId,config).success(function(response)
					{
						
						if(response.status=='ERROR')
						{
							alert(response.message);
							return;
						}
						if(response.message=='Feedback already received for this order')
						{
							alert(response.message);
							return;
						}
						if(response.message=='Feedback already received for this order')
						{
							alert(response.message);
							return;
						}
						if(response.message=='No matching records found in db.')
						{
							alert(response.message);
							return;
						}
						console.log('Order id is '+response["id"]);
						$scope.order_id=response.key.id;
						var recipesJson=response.recipeJson;
						recipesJson=JSON.parse(recipesJson);
						console.log('Recipes are =>'+recipesJson.length);
						$scope.items=recipesJson;
					}).
					error(function(status)
					{
						console.log("error occured");	
					});


	$scope.createRatingBox=function()
					{
						
					}
	$scope.addStarBox=function()
					{
						console.log("Item has changed=>"+$scope.selectedData);
						var br = document.createElement("br");
						var br1 = document.createElement("br");
						var input = document.createElement("input");
						var label = document.createElement("label");
						label.innerHTML=$scope.selectedData;
						input.setAttribute("id",$scope.selectedData);
						input.setAttribute("onblur","roundRating(this)");
						input.setAttribute("onkeypress","return isNumberKey(event,this)")
						input.type = "number";
						document.getElementById('content').appendChild(br);
						document.getElementById('content').appendChild(label);
						document.getElementById('content').appendChild(input);
						document.getElementById('content').appendChild(br1);

					}

	

	function roundRating(stars)
	{
		console.log('economy is '+stars);
		stars=parseFloat(stars);
		stars=Math.round( stars * 10 ) / 10;
		console.log('------------'+stars);
		if(stars >5 )
		{
			return null;
		}
		var rate=Math.round(stars/0.5);
		console.log('rating is '+rate);
		return rate/2;
	}

	$scope.test=function()
	{
		console.log('test');
	}
	$scope.roundUp=function(val,modelName)
	{
		console.log('in round up');
		var ret=roundRating(val);
		if(ret!=null)
		{
			$scope[modelName]=roundRating(val);
		}
		else
		{
			$scope[modelName]=null;	
		}

	}

	$scope.submitFeedback=function()
	{
		var inputElements=document.getElementById('content').getElementsByTagName('input');
		console.log(inputElements.length);
		var recipeMap=[];
		var ratingMap={};
		var tempMap=null;
		var recipeJson="["
		for(var k=0;k<inputElements.length;k++)
		{
			var element=inputElements[k];
			//recipeMap[element.id]=element.value;
			var key=element.id;
			var value=element.value.toString();
			value=parseFloat(value);
			recipeJson=recipeJson+"\""+key+":"+value+"\",";
			if(k==inputElements.length-1)
			{
				recipeJson=recipeJson+"\""+key+":"+value+"\"";
			}
		}

		recipeJson=recipeJson+"]";
		console.log('Recipe json ------------>>> '+recipeJson);
		ratingMap["order_id"]=orderId;
		ratingMap["restaruent_id"]=restaruentId;
		ratingMap["economy"]=$scope.economy;
		ratingMap["ambience"]=$scope.ambience;
		ratingMap["qos"]=$scope.service;
		ratingMap["feedback"]=$scope.feedback;
		
		ratingMap["recipe_rating"]='recipes_data';
		ratingMap=JSON.stringify(ratingMap);
		ratingMap=ratingMap.replace("\"recipes_data\"",recipeJson)
		console.log("final recipe ratings =>"+ratingMap);

		//Calling rest api to save rating and feedback.
		//demo-0.0.1-SNAPSHOT
		$http.post("http://"+serverIpAddress+":8080/demo-0.0.1-SNAPSHOT/feedbackReview/postFeedback",ratingMap,config).success(function(response)
					{
						console.log('Response is '+response);
						console.log("response=>"+response.message);
						alert(response.message);
					}).
					error(function(status)
					{
						console.log("error occured");	
					});

	}

});