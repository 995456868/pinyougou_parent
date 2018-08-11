 //控制层 
app.controller('goodsController' ,function($scope,$controller,itemCatService,typeTemplateService   ,goodsService){
	
	$controller('baseController',{$scope:$scope});//继承
	
    //读取列表数据绑定到表单中  
	$scope.findAll=function(){
		goodsService.findAll().success(
			function(response){
				$scope.list=response;
			}			
		);
	}    
	
	//分页
	$scope.findPage=function(page,rows){			
		goodsService.findPage(page,rows).success(
			function(response){
				$scope.list=response.rows;	
				$scope.paginationConf.totalItems=response.total;//更新总记录数
			}			
		);
	}
	
	//查询实体 
	$scope.findOne=function(id){				
		goodsService.findOne(id).success(
			function(response){
				$scope.entity= response;					
			}
		);				
	}
	
	//保存 
	$scope.save=function(){
        $scope.entity.goodsDesc.introduction = editor.html();
		var serviceObject;//服务层对象  				
		if($scope.entity.id!=null){//如果有ID
			serviceObject=goodsService.update( $scope.entity ); //修改  
		}else{
			serviceObject=goodsService.add( $scope.entity  );//增加 
		}				
		serviceObject.success(
			function(response){
				if(response.success){
					alert(response.message);
		        	$scope.entity={};//列表
					editor.html('');
				}else{
					alert(response.message);
				}
			}		
		);				
	}
	
	 
	//批量删除 
	$scope.dele=function(){			
		//获取选中的复选框			
		goodsService.dele( $scope.selectIds ).success(
			function(response){
				if(response.success){
					$scope.reloadList();//刷新列表
				}						
			}		
		);				
	}
	
	$scope.searchEntity={};//定义搜索对象 
	
	//搜索
	$scope.search=function(page,rows){			
		goodsService.search(page,rows,$scope.searchEntity).success(
			function(response){
				$scope.list=response.rows;	
				$scope.paginationConf.totalItems=response.total;//更新总记录数
			}			
		);
	}
    $scope.findItemCatList=function(){
		itemCatService.findByParentId("0").success(function(response){
			$scope.itemCat1List=response;
		});
	}
	$scope.$watch("entity.goods.category1Id",function(newValue,oldValue){
		itemCatService.findByParentId(newValue).success(function(response){
			$scope.itemCat2List=response;
		});
	})
	$scope.$watch("entity.goods.category2Id",function(newValue,oldValue){
		itemCatService.findByParentId(newValue).success(function (response) {
			$scope.itemCat3List=response;
        })
	})
	$scope.$watch("entity.goods.category3Id",function (newValue,oldValue) {
		itemCatService.findOne(newValue).success(function (response) {
			$scope.entity.goods.typeTemplateId=response.typeId;
        })
    })
	$scope.$watch("entity.goods.typeTemplateId",function(newValue,oldValue){
		typeTemplateService.findOne(newValue).success(function (response) {
			$scope.typeTemplate = response;
			$scope.typeTemplate.brandIds=JSON.parse($scope.typeTemplate.brandIds);
        })
	})

});	