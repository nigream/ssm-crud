new Vue({
    el: "#app",
    data: {
        app_path: "",
        pageInfo: "",
        empList: "",
        departmentList:""

    },
    methods: {
        getAllEmps: function(navigatepageNum) {
            var me = this;
            axios.get("/ssm-crud/employee/findAll", {
                params: {
                    pn: navigatepageNum
                }
            }).then(function(response) {
                me.pageInfo = response.data.dataMap.pageInfo;
                me.empList = response.data.dataMap.pageInfo.list;
            }).catch(function(err) {
                console.log(err);
            });
        },
        getAllDepartments: function(navigatepageNum) {
            var me = this;
            axios.get("/ssm-crud/department/findAll").then(function(response) {
                me.departmentList = response.data.dataMap.departments;
            }).catch(function(err) {
                console.log(err);
            });
        },
        getAppPath: function() {
            var pathName = document.location.pathname;
            var index = pathName.substr(1).indexOf("/");
            var result = pathName.substr(0, index + 1);
            this.app_path = result;
        },
        showEmpAddModal: function() {
        	var me = this;
        	$("#empAddModal").modal({
        		keyboard: true
        	});
        	me.getAllDepartments();
        },
        submitAddEmpForm:function() {
        	var me = this;
        	/* alert($("#addEmpForm").serialize()); */
        	if(!me.validate_add_form()){
        		return false;
        	}
            axios.post("/ssm-crud/employee/emp", $("#addEmpForm").serialize())
        	  .then(function (response) {
        		  $("#empAddModal").modal("hide");
        		  axios.get("/ssm-crud/employee/findAll")
        		  .then(function(response) {
                      me.pageInfo = response.data.dataMap.pageInfo;
                      me.empList = response.data.dataMap.pageInfo.list;
                      me.getAllEmps(me.pageInfo.pages);
                  }).catch(function(err) {
                      console.log(err);
                  });
        	  })
        	  .catch(function (error) {
        	    console.log(error);
        	  });
        },
        validate_add_form:function() {
        	var me = this;
        	/* return me.empNameOnBlur()&&me.empEmailOnBlur(); */
        	return false;
        },
        empNameOnBlur:function() {
        	var empName = $("#inputEmpName").val();
        	var regEmpName = /(^[a-zA-Z0-9\u2E80-\u9FFF_-]{2,16}$)/;
        	/* alert(regEmpName.test(empName)); */
        	if(!regEmpName.test(empName)) {
        		/* alert("姓名是2-16位【大写字母、小写字母、数字、中文汉字、下划线'_'、减号'-'】的任意组合"); */
        		$("#inputEmpName").parent().removeClass("has-success");
        		$("#empNameSuccessStatus").removeClass("glyphicon-ok");
        		
        		$("#inputEmpName").parent().addClass("has-error");
        		$("#empNameSuccessStatus").addClass("glyphicon-remove");
        		$("#inputEmpName").next("span").text("必须为2-16位【大小写字母、数字、中文汉字、_、-】的组合");
        	} else {
        		/* $("#inputEmpName").next("span").text("【姓名】有效！"); */
        		$("#empNameSuccessStatus").removeClass("glyphicon-remove");
        		$("#inputEmpName").parent().removeClass("has-error");
        		
        		$("#inputEmpName").parent().addClass("has-success");
        		$("#empNameSuccessStatus").addClass("glyphicon-ok");
        		$("#inputEmpName").next("span").text("");
        	}
        	return regEmpName.test(empName);
        },
        empEmailOnBlur:function() {
        	var empEmail = $("#inputEmpEmail").val();
        	var regEmpEmail = /^([a-z0-9_\.-]+)@([\da-z\.-]+)\.([a-z\.]{2,6})$/;
        	if(!regEmpEmail.test(empEmail)) {
        		/* alert("邮箱格式不正确！"); */
        		$("#inputEmpEmail").parent().removeClass("has-success");
        		$("#inputEmpEmail").parent().addClass("has-error");
        		$("#inputEmpEmail").next("span").text("邮箱格式不正确！");
        	} else{
        		$("#inputEmpEmail").parent().removeClass("has-error");
        		$("#inputEmpEmail").parent().addClass("has-success");
        		$("#inputEmpEmail").next("span").text("【邮箱】有效！");
        	}
        	return regEmpEmail.test(empEmail);
        },

    },
    created: function() {
        /* this.getAppPath(); */
        this.getAllEmps();
    },

});