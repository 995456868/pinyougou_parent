app.controller('specificationController',function ($scope, $controller, specificationService) {

    $controller('baseController',{$scope:$scope});

    $scope.searchEntity={};
    $scope.search = function (pageNum,pageSize) {
        specificationService.search(pageNum,pageSize,$scope.searchEntity).success(function (response) {
            $scope.list = response.rows;
            //给我们的分页总记录数赋值
            $scope.paginationConf.totalItems = response.total;
        })
    };

    //添加行
    $scope.addTableRow=function(){
        $scope.entity.specificationOptionList.push({});
    };

    //删除行
    //删除行
    $scope.deleTableRow=function(index){
        $scope.entity.specificationOptionList.splice(index,1);
    };

    $scope.save=function () {
        var objectName = "";
        if($scope.entity.specification.id != null) {
            objectName = specificationService.update($scope.entity);
        }else {
            objectName = specificationService.add($scope.entity);
        }
        objectName.success(function (response) {
            if(response.success) {
                $scope.reloadList();
            }else {
                alert(response.message);
            }
        })
    };

    $scope.findOne=function(id){
        specificationService.findOne(id).success(function(response){
            $scope.entity = response;
        });
    };

    $scope.dele=function () {
        specificationService.dele($scope.selectId).success(function (response) {
            if(response.success){
                $scope.reloadList();
                $scope.selectId=[];
            }else {
                alert($scope.message);
            }
        })
    };


});