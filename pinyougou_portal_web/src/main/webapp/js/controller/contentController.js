app.controller("contentController",function($scope,contentService){

    $scope.contentList=[];
    //根据categoryId查询内容集合
    $scope.findContentListByCategoryId=function(categoryId){
        contentService.findContentListByCategoryId(categoryId).success(function (response) {
            $scope.contentList[categoryId]=response;
        })
    }
    
    $scope.search=function () {
        var keywords = $scope.keywords;
        location.href="http://pinyougou.search.com/search.html#?keywords="+keywords;
    }
});