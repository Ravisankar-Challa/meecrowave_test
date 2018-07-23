package meecrowave.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/HttpOnlyServlet")
public class HttpOnlyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Cookie cookie = new Cookie("ravi", "sankar");
		cookie.setHttpOnly(true);
		//cookie.setSecure(true);
		response.addCookie(cookie);
		response.getWriter()
				.append("<html>" + 
						"	<body>" + 
						"		<img src=\"death-note.jpg\">" + 
						"       Ravi Sankar" +
						"	</body>" + 
						"</html>");
	}

}
