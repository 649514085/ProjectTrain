package cn.techaction.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import cn.techaction.common.SverResponse;
import cn.techaction.pojo.Admin;
import cn.techaction.pojo.User;
import cn.techaction.vo.ActionUserVo;

public interface ActionAdminService {
	public SverResponse<Admin> doLogin(String account,String password);
	public SverResponse<List<ActionUserVo>> findUserList();
	public SverResponse<List<ActionUserVo>> findUser(String user_id);
	public SverResponse<ActionUserVo> findUserOne(String user_id);
	public SverResponse<String> updateUserInfo(ActionUserVo userVo);
	public SverResponse<String> deleteUser(String user_id);
}
