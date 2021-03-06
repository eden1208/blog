package com.cos.controller.member;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cos.action.Action;
import com.cos.dao.MemberDAO;
import com.cos.dto.MemberVO;
import com.cos.util.SHA256;
import com.cos.util.Script;

public class MemberUpdateProcAction implements Action{
	private static String naming = "MemberUpdateProcAction : ";
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String url = "Member?cmd=member_update";
		
		
		MemberDAO dao = new MemberDAO();
		MemberVO member = new MemberVO();
		
		String id=null;
		String password=null;
		String roadFullAddr=null;
		String email=null;
		String salt = null;

	
		if(request.getParameter("id")!=null) id = request.getParameter("id");
		if(request.getParameter("password")!=null) {
			salt = dao.select_salt(id);
			password = request.getParameter("password");
			password = SHA256.getEncrypt(password, salt);
		}
		if(request.getParameter("roadFullAddr")!=null) roadFullAddr = request.getParameter("roadFullAddr");
		if(request.getParameter("email")!=null) email = request.getParameter("email");
		
		member.setId(id);
		member.setPassword(password);
		member.setRoadFullAddr(roadFullAddr);
		member.setEmail(email);
		member.setSalt(salt);
		

		
		//update 함수 실행.
			//실행 체크
			int result = dao.update(member);
			
			if(result == -1) {
				Script.moving(response, "DB Error");
			}else {
				Script.moving(response, "정보수정 성공!", url);
			}
		
	}
}
