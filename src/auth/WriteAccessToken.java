package auth;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.AccessToken;

import org.json.JSONObject;

/**
 * Servlet implementation class AccessToken
 * Get accessToken from Youku and write into accessToken.json
 */
@WebServlet("/AccessToken")
public class WriteAccessToken extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public WriteAccessToken() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html");
		
		long updateTime = System.currentTimeMillis();
		String accessToken = request.getParameter("access_token");
		String expire = request.getParameter("expires_in");
		long expiresIn = 0L;
		String tokenType = request.getParameter("token_type");
		String state = request.getParameter("state");
		String refreshToken = request.getParameter("refresh_token");
		
		if(accessToken == null) {
			response.sendRedirect(request.getContextPath() + "/GetAccessToken");
		} else {
			if(expire != null) expiresIn = Long.parseLong(expire);
			if(tokenType == null) tokenType = "bearer";
			
			JSONObject at = new JSONObject();
			at.putOpt("refresh_token", refreshToken);
			at.putOpt("state", state);
			at.putOpt("update_time", updateTime);
			at.put("expires_in", expiresIn);
			at.put("token_type", tokenType);
			at.put("access_token", accessToken);
			
			AccessToken.update(accessToken, expiresIn, updateTime, tokenType, at);
			
			String path = this.getServletContext().getRealPath("/");
	//		String path = "~/.youku";
			File file = new File(path + "/accessToken.json");
			if(file.exists()){
	//			System.out.println(file.getName() + " exist!");
				FileWriter fw = new FileWriter(file);
				fw.write(at.toString());
				fw.flush();
				fw.close();
			}
	//		else System.out.println(file.getName() + " does not exist!");
			response.sendRedirect(request.getContextPath() +"/video.html");
		}
	}

}
