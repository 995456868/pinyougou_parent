app.service('brandService',function ($http) {

    this.findAll = function () {
        return $http.get('../brand/findAll');
    }

    this.findPage=function(pageNum,pageSize) {
        return $http.get('../brand/findPage?pageNum='+pageNum+'&pageSize='+pageSize);
    }

    this.findOne = function (id) {
        return $http.get('../brand/findOne?id='+id);
    }

    this.dele=function (ids) {
        return $http.get('../brand/dele?ids='+ids);
    }

    this.search=function(pageNum,pageSize,searchEntity) {
        return $http.post('../brand/search?pageNum='+pageNum+'&pageSize='+pageSize,searchEntity);
    }

    this.update=function(entity){
        return $http.post('../brand/update',entity);
    }

    this.add=function(entity){
        return $http.post('../brand/add',entity);
    }
    
    this.findBrandList=function () {
        return $http.get('../brand/findBrandList');
    }

});