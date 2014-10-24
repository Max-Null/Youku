package servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import model.AccessToken;

/**
 * Servlet implementation class GetAccessToken
 * Front-end request accessToken by ajax
 */
@WebServlet("/GetAccessToken")
public class GetAccessToken extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetAccessToken() {
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
		response.setContentType("application/x-json");
		PrintWriter out = response.getWriter();
		
		if(AccessToken.jsonAccessToken != null) {
			out.print(AccessToken.jsonAccessToken);
		} else {
			String path = this.getServletContext().getRealPath("/");
			File file = new File(path + "/accessToken.json");
			if(file.exists()){
				AccessToken.update(file);
//				out.print(new JSONObject("{\'access_token\': \'" + AccessToken.accessToken + "\'}"));
				out.print(AccessToken.jsonAccessToken);
			} else {
				String redirect_url = "http://youku.elasticbeanstalk.com/WriteAccessToken";
				String url = "https://openapi.youku.com/v2/oauth2/authorize?client_id=" + AccessToken.getClientid() 
						+ "&response_type=token&state=state&redirect_uri=" + redirect_url;
				JSONObject err = new JSONObject("{\'error\': \'Get access token error!\', \'redirect_url\': \'" + url + "\'}");
				out.print(err);
			}
		}
		
		out.flush();
		out.close();
	}

}
