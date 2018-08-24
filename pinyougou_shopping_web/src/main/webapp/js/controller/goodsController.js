//控制层
app.controller('goodsController', function ($scope, $controller, itemCatService, typeTemplateService, uploadService, goodsService) {

    $controller('baseController', {$scope: $scope});//继承

    //读取列表数据绑定到表单中  
    $scope.findAll = function () {
        goodsService.findAll().success(
            function (response) {
                $scope.list = response;
            }
        );
    }

    //分页
    $scope.findPage = function (page, rows) {
        goodsService.findPage(page, rows).success(
            function (response) {
                $scope.list = response.rows;
                $scope.paginationConf.totalItems = response.total;//更新总记录数
            }
        );
    }

    //查询实体
    $scope.findOne = function (id) {
        goodsService.findOne(id).success(
            function (response) {
                $scope.entity = response;
            }
        );
    }

    //保存
    $scope.save = function () {
        $scope.entity.goodsDesc.introduction = editor.html();
        var serviceObject;//服务层对象
        if ($scope.entity.id != null) {//如果有ID
            serviceObject = goodsService.update($scope.entity); //修改
        } else {
            serviceObject = goodsService.add($scope.entity);//增加
        }
        serviceObject.success(
            function (response) {
                if (response.success) {
                    alert(response.message);
                    $scope.entity = {};//列表
                    editor.html('');
                } else {
                    alert(response.message);
                }
            }
        );
    }


    //批量删除
    $scope.dele = function () {
        //获取选中的复选框
        goodsService.dele($scope.selectIds).success(
            function (response) {
                if (response.success) {
                    $scope.reloadList();//刷新列表
                }
            }
        );
    }

    $scope.searchEntity = {};//定义搜索对象

    //搜索
    $scope.search = function (page, rows) {
        goodsService.search(page, rows, $scope.searchEntity).success(
            function (response) {
                $scope.list = response.rows;
                $scope.paginationConf.totalItems = response.total;//更新总记录数
            }
        );
    }
    $scope.findItemCatList = function () {
        itemCatService.findByParentId("0").success(function (response) {
            $scope.itemCat1List = response;
        });
    }
    $scope.$watch("entity.goods.category1Id", function (newValue, oldValue) {
        itemCatService.findByParentId(newValue).success(function (response) {
            $scope.itemCat2List = response;
        });
    })
    $scope.$watch("entity.goods.category2Id", function (newValue, oldValue) {
        itemCatService.findByParentId(newValue).success(function (response) {
            $scope.itemCat3List = response;
        })
    })
    $scope.$watch("entity.goods.category3Id", function (newValue, oldValue) {
        itemCatService.findOne(newValue).success(function (response) {
            $scope.entity.goods.typeTemplateId = response.typeId;

        })
    })
    //监听3级目录 获取模板ID
    $scope.$watch("entity.goods.typeTemplateId", function (newValue, oldValue) {
        typeTemplateService.findOne(newValue).success(function (response) {
            $scope.typeTemplate = response;
            $scope.typeTemplate.brandIds = JSON.parse($scope.typeTemplate.brandIds);
            $scope.entity.goodsDesc.customAttributeItems=JSON.parse($scope.typeTemplate.customAttributeItems);
        });
        typeTemplateService.findSpecList(newValue).success(function(response){
            $scope.specList=response;
        });
    })


    //上传图片的方法
    $scope.uploadFile=function(){
        uploadService.uploadFile().success(function(response){
            if(response.success){
                $scope.image_entity.url = response.message;
            } else{
                alert(response.message);
            }
        });
    }
    $scope.entity = {goods: {}, goodsDesc: {itemImages: [],specificationItems: []}}
    $scope.add_image_entity=function(){
        $scope.entity.goodsDesc.itemImages.push($scope.image_entity);
    }
    $scope.remove_image_entity=function(index){
        $scope.entity.goodsDesc.itemImages.splice(index,1);
    }

    //添加规格模块
    $scope.updateSpecAttribute=function($event,name,value){
        //判断specificationItems中attribute属性是否存在，如果存在就返回集合，不存在就返回null;
        var object = $scope.searchObjectByKey($scope.entity.goodsDesc.specificationItems,"attributeName",name);
        if(object!=null){
            if($event.target.checked){
                object.attributeValue.push(value);
            }else{
                object.attributeValue.splice(object.attributeValue.indexOf(value),1);
                if(object.attributeValue.length == 0){
                    $scope.entity.goodsDesc.specificationItems.splice(
                        $scope.entity.goodsDesc.specificationItems.indexOf(object),1)
                }
            }
        }else{
            $scope.entity.goodsDesc.specificationItems.push({"attributeName":name,"attributeValue":[value]});
        }
    }
    //判断集合中是否存在了attribute为value的属性 如果存在返回集合 如果不存在返回null
    $scope.searchObjectByKey=function(list,key,value){
        for (var i = 0;i<list.length; i++){
            if(list[i][key]==value){
                return list[i];
            }
        }
        return null;
    }
    //创建 SKU 列表
    $scope.createItemList=function(){
        $scope.entity.itemList = [{spec:{},price:0,num:99999,status:'0',isDefault:'0' }];//初始
        var items= $scope.entity.goodsDesc.specificationItems;
        for(var i=0;i< items.length;i++){
            $scope.entity.itemList =
                addColumn( $scope.entity.itemList,items[i].attributeName,items[i].attributeValue);
        }
    }

    //添加列值  					"网络"		["移动3G","移动4G"]
    addColumn=function(list,columnName,conlumnValues){
        var newList=[];//新的集合
        for(var i=0;i<list.length;i++){
            var oldRow= list[i];
            for(var j=0;j<conlumnValues.length;j++){
                var newRow= JSON.parse( JSON.stringify( oldRow ) );//深克隆
                newRow.spec[columnName]=conlumnValues[j];
                //newRow={spec:{"网络":"移动3G"},price:0,num:99999,status:'0',isDefault:'0' }
                //newRow={spec:{"网络":"移动4G"},price:0,num:99999,status:'0',isDefault:'0' }
                newList.push(newRow);
            }
        }
        return newList;
        //[{spec:{"网络":"移动3G"},price:0,num:99999,status:'0',isDefault:'0' },{spec:{"网络":"移动4G"},price:0,num:99999,status:'0',isDefault:'0' }]
    }
    $scope.selectStatus=["未审核","已审核","驳回","关闭"];
    $scope.itemCatList=[];
    $scope.findItemCatList1=function () {
        itemCatService.findAll().success(function (response) {
            for (var i = 0;i<response.length;i++){
                $scope.itemCatList[response[i].id]=response[i].name;
            }

        })
    }
    $scope.selectMarketable=['下架','上架'];
    $scope.updateMaketable=function (isMaketable) {
        goodsService.updateMaketable($scope.selectId,isMaketable).success(function (response) {
            if (response.success){
                $scope.reloadList();
                $scope.selectId=[];
            } else{
                alert(response.message);
            }
        })

    }
    $scope.updateStatus=function (status) {
        goodsService.updateStatus($scope.selectId,status).success(function (response) {
            if(response.success){
                $scope.reloadList();
                $scope.selectId=[];
            }else{
                alert(response.message);
            }
        })
    }

});	
