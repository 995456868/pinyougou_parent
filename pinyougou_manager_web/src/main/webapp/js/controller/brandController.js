app.controller('brandController',function($scope,$controller,brandService) {

    $controller('baseController',{$scope:$scope});

    $scope.findAll = function () {
        brandService.findAll().success(function (response) {
            $scope.list = response;
        });
    };

    //分页查询的方法
    $scope.findPage=function(pageNum,pageSize){
        brandService.findPage(pageNum,pageSize).success(function(response){
            $scope.list = response.rows;
            //再修改一下分页组件
            $scope.paginationConf.totalItems = response.total;
        });
    };

    //添加品牌
    $scope.save = function () {
        var objectName = "";
        if ($scope.entity.id != null) {
            objectName = brandService.update($scope.entity);
        }else {
            objectName = brandService.add($scope.entity);
        }
        objectName.success(function (response) {
            if (response.success) {
                $scope.reloadList();
            }else{
                alert(response.message);
            }
        })
    };

    //根据id查询品牌
    $scope.findOne = function (id) {
        brandService.findOne(id).success(function (response) {
            $scope.entity=response;
        })
    };

    //删除
    $scope.dele=function () {
        brandService.dele($scope.selectId).success(function (response) {
            if(response.success){
                $scope.reloadList();
                $scope.selectId=[];
            }else {
                alert($scope.message);
            }
        })
    };

    //初始化一个空的对象
    $scope.searchEntity={};
    //搜索
    $scope.search=function(pageNum,pageSize){
        brandService.search(pageNum,pageSize,$scope.searchEntity).success(function(response){
            $scope.list = response.rows;
            //再修改一下分页组件
            $scope.paginationConf.totalItems = response.total;
        });
    }

});