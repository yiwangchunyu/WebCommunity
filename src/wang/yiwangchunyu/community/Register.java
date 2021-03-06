package wang.yiwangchunyu.community;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

/**
 * Servlet implementation class Register
 */
@WebServlet("/Register")
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Register() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// 使用 GBK 设置中文正常显示
	    response.setCharacterEncoding("GBK");
	    response.getWriter().write("Register..." );
	    String ip = request.getRemoteAddr().toString();
	    System.out.println(ip);
		response.getWriter().append("Served at: ").append(request.getContextPath());
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String ip = request.getRemoteAddr().toString();
	    System.out.println(ip);
	    String user_id = request.getParameter("user_id");
	    System.out.println(user_id);
	    String user_name = request.getParameter("user_name");
	    System.out.println(user_name);
	    String user_password = request.getParameter("user_password");
	    System.out.println(user_password);
	    String user_email = request.getParameter("user_email");
	    System.out.println(user_email);
	    String user_address = request.getParameter("user_address");
	    System.out.println(user_address);
	    
	    SQLHelper sqlHelper = new SQLHelper();
	    String sql  = "INSERT INTO user_info(user_id, user_name, user_password, submission_time) "
	    		+ "VALUES('" + user_id +"','" + user_name + "','" + user_password + "', current_time());";
	    
        int rs = sqlHelper.update(sql);
        UserBaseInfo userBaseInfo = new UserBaseInfo();
        try {
        	if(rs>0)
        	{		
        		SQLHelper sqlHelper2 = new SQLHelper();
        		String sql2  = "INSERT INTO user_info_extension(user_id, user_email, user_address , submission_time) "
        	    		+ "VALUES('" + user_id +"','" + user_email + "','" + user_address + "', current_time());";
        		int rs2 = sqlHelper2.update(sql2);
        		if(rs2>0) {
        			userBaseInfo.setUserid(user_id);
        			userBaseInfo.setNickname(user_name);
        			userBaseInfo.setEmail(user_email);
        			userBaseInfo.setAddress(user_address);
        			userBaseInfo.setRet("0");
        			userBaseInfo.setRole("0");	
        		}
        		sqlHelper2.close();
        	}
        	else {
				userBaseInfo.setRet("1");
			}
        	Gson gson = new Gson();
			String userInfoString = gson.toJson(userBaseInfo);
			response.getOutputStream().write(userInfoString.getBytes("utf-8"));
			System.out.println(userInfoString);
			
			sqlHelper.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		//doGet(request, response);
	}

}
