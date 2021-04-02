new Vue({
	el : "#app",
	data : {
		app_path : "",
		pageInfo : "",
		empList : "",
		departmentList : "",
		empName_add : "",
		isEmpNameUnique : "",
		isEmpNameLegal : "",
		isEmpEmailLegal : "",
		emp : {
			department:""
		},
		allChecked: false,
		checkedIds:[]

	},
	methods : {
		getAllEmps : function(navigatepageNum) {
			var me = this;
			me.checkedIds = [];
			axios.get("/ssm-crud/employee/findAll", {
						params : {
							pn : navigatepageNum
						}
					}).then(function(response) {
						me.pageInfo = response.data.dataMap.pageInfo;
						me.empList = response.data.dataMap.pageInfo.list;
					}).catch(function(err) {
						console.log(err);
					});
		},
		getAllDepartments : function(navigatepageNum) {
			var me = this;
			axios.get("/ssm-crud/department/findAll").then(function(response) {
						me.departmentList = response.data.dataMap.departments;
					}).catch(function(err) {
						console.log(err);
					});
		},
		getAppPath : function() {
			var pathName = document.location.pathname;
			var index = pathName.substr(1).indexOf("/");
			var result = pathName.substr(0, index + 1);
			this.app_path = result;
		},
		showEmpAddModal : function() {
			var me = this;
			$("#empAddModal").modal({
						keyboard : true
					});

			/*
			 * $("#inputEmpName").parent().removeClass("has-success");
			 * $("#inputEmpName").parent().removeClass("has-error");
			 * $("#empNameSuccessStatus").removeClass("glyphicon-ok");
			 * $("#empNameSuccessStatus").removeClass("glyphicon-remove");
			 * $("#inputEmpName").next("span").text("");
			 * 
			 * $("#inputEmpEmail").parent().removeClass("has-success");
			 * $("#inputEmpEmail").parent().removeClass("has-error");
			 * $("#empEmailSuccessStatus").removeClass("glyphicon-ok");
			 * $("#empEmailSuccessStatus").removeClass("glyphicon-remove");
			 * $("#inputEmpEmail").next("span").text("");
			 */

			$("#empAddModal form")
					.find("*")
					.removeClass("has-success has-error glyphicon-ok glyphicon-remove");
			$("#empAddModal form").find(".help-block").text("");
			$("#empAddModal form")[0].reset();

			me.getAllDepartments();
		},
		submitAddEmpForm : function() {
			var me = this;
			/* alert($("#addEmpForm").serialize()); */
			if (!me.validate_add_form()) {
				return false;
			}
			axios.post("/ssm-crud/employee/emp", $("#addEmpForm").serialize())
					.then(function(response) {
						if (response.data.code == 100) {
							$("#empAddModal").modal("hide");
							axios.get("/ssm-crud/employee/findAll").then(
									function(response) {
										me.pageInfo = response.data.dataMap.pageInfo;
										me.empList = response.data.dataMap.pageInfo.list;
										me.getAllEmps(me.pageInfo.pages);
									}).catch(function(err) {
										console.log(err);
									});
							/* console.log(response.data); */
						} else {
							if (undefined != response.data.dataMap.fieldErrorMap.empName) {
								me.empNameOnBlur();
							}
							if (undefined != response.data.dataMap.fieldErrorMap.email) {
								me.empEmailOnBlur();
							}
							if (undefined != response.data.dataMap.isUnique) {
								me.empNameOnBlur();
							}
						}

					}).catch(function(error) {
								console.log(error);
							});
		},
		validate_add_form : function() {
			var me = this;
			/* return me.empNameOnBlur()&&me.empEmailOnBlur(); */
			me.empNameOnBlur();
			me.empEmailOnBlur();
			return (me.isEmpNameLegal && me.isEmpEmailLegal && me.isEmpNameUnique);
		},
		validateEmpName : function() {
			var me = this;
			var empName = $("#inputEmpName").val();
			var regEmpName = /(^[a-zA-Z0-9\u2E80-\u9FFF_-]{2,16}$)/;
			/* alert(regEmpName.test(empName)); */
			me.isEmpNameLegal = regEmpName.test(empName);
			return regEmpName.test(empName);
		},
		empNameOnBlur : function() {
			var me = this;
			if (!me.validateEmpName()) {
				/* alert("用户名是2-16位【大写字母、小写字母、数字、中文汉字、下划线'_'、减号'-'】的任意组合"); */
				$("#inputEmpName").parent().removeClass("has-success");
				$("#empNameSuccessStatus").removeClass("glyphicon-ok");

				$("#inputEmpName").parent().addClass("has-error");
				$("#empNameSuccessStatus").addClass("glyphicon-remove");
				$("#inputEmpName").next("span")
						.text("必须为2-16位【大小写字母、数字、中文汉字、_、-】的组合");
			} else {
				// 判断用户名是否已经存在
				axios.get("/ssm-crud/employee/checkEmpName", {
							params : {
								empName : $("#inputEmpName").val()
							}
						}).then(function(response) {
					me.isEmpNameUnique = response.data.dataMap.isUnique;
					if (me.isEmpNameUnique) {
						/* $("#inputEmpName").next("span").text("【用户名】有效！"); */
						$("#empNameSuccessStatus")
								.removeClass("glyphicon-remove");
						$("#inputEmpName").parent().removeClass("has-error");

						$("#inputEmpName").parent().addClass("has-success");
						$("#empNameSuccessStatus").addClass("glyphicon-ok");
						$("#inputEmpName").next("span").text("");
					} else {
						/* alert("用户名是2-16位【大写字母、小写字母、数字、中文汉字、下划线'_'、减号'-'】的任意组合"); */
						$("#inputEmpName").parent().removeClass("has-success");
						$("#empNameSuccessStatus").removeClass("glyphicon-ok");

						$("#inputEmpName").parent().addClass("has-error");
						$("#empNameSuccessStatus").addClass("glyphicon-remove");
						$("#inputEmpName").next("span").text("用户名已存在！");
					}
				}).catch(function(err) {
							console.log(err);
						});

			}
		},
		validateEmpEmail : function() {
			var me = this;
			var empEmail = $("#inputEmpEmail").val();
			var regEmpEmail = /^([A-Za-z0-9_\.-]+)@([\dA-Za-z\.-]+)\.([A-Za-z\.]{2,6})$/;
			/* alert(regEmpName.test(empName)); */
			me.isEmpEmailLegal = regEmpEmail.test(empEmail);
			return regEmpEmail.test(empEmail);
		},
		empEmailOnBlur : function() {
			var me = this;
			if (!me.validateEmpEmail()) {
				/* alert("邮箱格式不正确！"); */
				$("#inputEmpEmail").parent().removeClass("has-success");
				$("#empEmailSuccessStatus").removeClass("glyphicon-ok");

				$("#inputEmpEmail").parent().addClass("has-error");
				$("#empEmailSuccessStatus").addClass("glyphicon-remove");
				$("#inputEmpEmail").next("span").text("邮箱格式不正确！");
			} else {
				$("#empEmailSuccessStatus").removeClass("glyphicon-remove");
				$("#inputEmpEmail").parent().removeClass("has-error");

				$("#inputEmpEmail").parent().addClass("has-success");
				$("#empEmailSuccessStatus").addClass("glyphicon-ok");
				$("#inputEmpEmail").next("span").text("");
			}
		},
		showEmpUpdateModal : function(empId) {
			var me = this;
			$("#empUpdateModal").modal({
						keyboard : true
					});

			axios.get("/ssm-crud/employee/emp/" + empId).then(
					function(response) {
						me.emp = response.data.dataMap.employee;
					}).catch(function(err) {
						console.log(err);
					});

			me.getAllDepartments();

		},
		submitUpdateEmpForm : function(empId) {
			var me = this;
			/* alert($("#addEmpForm").serialize()); */
			if (!me.validate_add_form2()) {
				return false;
			}
			// alert($("#updateEmpForm").serialize());
			axios.put("/ssm-crud/employee/emp/" + empId,
					$("#updateEmpForm").serialize()).then(function(response) {
				if (response.data.code == 100) {
					$("#empUpdateModal").modal("hide");
					me.getAllEmps(me.pageInfo.pageNum);
				} else {
					if (undefined != response.data.dataMap.fieldErrorMap.email) {
						me.empEmailOnBlur2();
					}
				}
			}).catch(function(error) {
						console.log(error);
					});
		},
		validateEmpEmail2 : function() {
			var me = this;
			var empEmail = $("#inputEmpEmail2").val();
			var regEmpEmail = /^([A-Za-z0-9_\.-]+)@([\dA-Za-z\.-]+)\.([A-Za-z\.]{2,6})$/;
			/* alert(regEmpName.test(empName)); */
			me.isEmpEmailLegal = regEmpEmail.test(empEmail);
			return regEmpEmail.test(empEmail);
		},
		validate_add_form2 : function() {
			var me = this;
			/* return me.empNameOnBlur()&&me.empEmailOnBlur(); */
			me.empEmailOnBlur2();
			return me.isEmpEmailLegal;
		},
		empEmailOnBlur2 : function() {
			var me = this;
			if (!me.validateEmpEmail2()) {
				/* alert("邮箱格式不正确！"); */
				$("#inputEmpEmail2").parent().removeClass("has-success");
				$("#empEmailSuccessStatus2").removeClass("glyphicon-ok");

				$("#inputEmpEmail2").parent().addClass("has-error");
				$("#empEmailSuccessStatus2").addClass("glyphicon-remove");
				$("#inputEmpEmail2").next("span").text("邮箱格式不正确！");
			} else {
				$("#empEmailSuccessStatus2").removeClass("glyphicon-remove");
				$("#inputEmpEmail2").parent().removeClass("has-error");

				$("#inputEmpEmail2").parent().addClass("has-success");
				$("#empEmailSuccessStatus2").addClass("glyphicon-ok");
				$("#inputEmpEmail2").next("span").text("");
			}
		},
		showDeleteAddModal : function(empId) {
			var me = this;
			$("#empDeleteModal").modal({
						keyboard : false,
						backdrop : false
					});

			axios.get("/ssm-crud/employee/findbyIdWithDept/", {
						params : {
							empId : empId
						}
					}).then(function(response) {
						me.emp = response.data.dataMap.employee;
					}).catch(function(err) {
						console.log(err);
					});
		},
		deleteOneEmp :function(empId) {
			var me = this;
			axios.delete("/ssm-crud/employee/emp/"+empId).then(function(response) {
						/*alert(response.data.info);*/
						$("#empDeleteModal").modal("hide");
						
						me.getAllEmps(me.pageInfo.pageNum);
					}).catch(function(err) {
						console.log(err);
					});
		},
		checkAll:function(){
			var me = this;
			if (me.checkedIds.length === me.empList.length) {
                // 全不选
                me.checkedIds = [];
            } else {
                me.checkedIds = [];
                // 全选
                me.empList.forEach(function(item){
                    me.checkedIds.push(item.empId); // 与checkbox的value统一
                });
            }
		},
		deleteChecked:function() {
			var me = this;
			var me = this;
            if(me.checkedIds.length === 0){
                alert("请勾选要删除的员工数据！");
                return;
            }
            if(!confirm('您确定要删除选中的内容吗？')){
                return;
            }
          
            axios.delete("/ssm-crud/employee/delChecked",{
            	params:{
            		checkedIds : me.checkedIds.toString()
            	}
            }).then(function(response) {
						/*alert(response.data.info);*/
						$("#empDeleteModal").modal("hide");
						
						me.getAllEmps(me.pageInfo.pageNum);
					}).catch(function(err) {
						console.log(err);
					});
            me.checkedIds = [];
		}

	},
	created : function() {
		/* this.getAppPath(); */
		this.getAllEmps();
	}

});