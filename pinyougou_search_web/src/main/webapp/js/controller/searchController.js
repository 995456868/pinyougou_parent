app.controller("searchController",function($scope,$controller,$location,searchService){

    $controller("baseController",{$scope:$scope});

    $scope.loadSearchList=function(){
        var keywords = $location.search()["keywords"];
        if (keywords!=null){
            // 把关键词参数放入参数对象
            $scope.searchMap.keywords = keywords;

        } else{
            $scope.searchMap.keywords="苹果";
        }
        // 调用搜索方法
        $scope.search();

    }

    $scope.searchMap={
        "keywords" : "",
        "category" : "",
        "brand" : "",
        "spec" : {},
        "price" : "",
        "sort" : "ASC",
        "sortField" : "price",
        "page" : 1,
        "pageSize" : 30
    };
    //搜索方法
    $scope.search=function () {
        searchService.search($scope.searchMap).success(function (response) {
            $scope.searchResult=response;
        })
    }

    // 定义条件过滤搜索参数封装方法
    $scope.addFilterCondition=function (key,value) {
        if(key=="category"||key=="brand"||key=="price"){
            $scope.searchMap.keywords="";
            $scope.searchMap[key]=value;
        }else{
            $scope.searchMap.spec[key]=value;
        }
        $scope.search();
    }

    //删除searchMap中spec的属性
    $scope.removeSearchItem=function (key) {
        if (key=='brand'||key=='category'||key=="price"){
            $scope.searchMap[key]="";
        } else{
            delete $scope.searchMap.spec[key];
        }
        $scope.search();
    }

})