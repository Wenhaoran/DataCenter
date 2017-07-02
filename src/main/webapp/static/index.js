	function switchRole(accountId, roleId) {
		$.ajax({
			"dataType" : 'json',
			"type" : 'POST',
			"url" : basePath + '/pages/frame/switchRole',
			"data" : {
				'id' : roleId
			},
			"success" : function(response) {
				if (response.isSuccess == "true") {
					var ticket = '${sessionScope.ticket}';
					getMenuList(response.roleId, response.sysId, ticket);
					getRoleList(accountId, response.roleId);
				} else {
					alertMsg('alertModal', 'alertMsg', response.msg);
				}
			},
			"error" : function(response) {
				window.location.href = basePath;
			}
		});
	}

	function getRoleList(accountId, roleId) {
		$.ajax({
			"dataType" : 'json',
			"type" : 'POST',
			"url" : basePath + '/pages/frame/getRoleListByAccountId',
			"data" : {
				'id' : accountId
			},
			"success" : function(response) {
				if (response.isSuccess == "true") {
					var html = "";
					for (var i = 0; i < response.roleList.length; i++) {
						var role = response.roleList[i];
						var id = role.id;
						var name = role.name;
						if (id == roleId) {
							html = html + "<li> <a> <i class='icon-user'></i><font color='#FF0000'>当前角色是</font>：" + name + "</a> </li>";
						} else {
							html = html + "<li> <a href='#' onclick='switchRole(\"" + accountId + "\", \"" + id + "\");'> <i class='icon-user'></i>" + name + "</a> </li>";
						}
					}
					html = html + "<li class='divider'></li>";
					html = html + "<li> <a style='cursor:pointer' onclick='setPasswd()'> <i class='icon-user'></i> 密码设置 </a> </li>";
					html = html + "<li class='divider'></li>";
					html = html + "<li> <a style='cursor:pointer' onclick='logout()'> <i class='icon-off'></i> 退出 </a> </li>";
					//alert(html);
					$('#user_menu').html(html);
				} else {
					alertMsg('alertModal', 'alertMsg', response.msg);
					window.location.href = basePath + response.url;
				}
			},
			"error" : function(response) {
				window.location.href = basePath ;
			}
		});
	}
