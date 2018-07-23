package meecrowave.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.PushBuilder;

@WebServlet("/ServerPushTest")
public class ServerPushTest extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PushBuilder pushBuilder = request.newPushBuilder();
		if(pushBuilder != null) {
			pushBuilder.path("img/death-note.jpg")
					   .addHeader("Content-Type", "image/jpeg")
					   .push();
		}
		response.getWriter()
				.append("<html>" + 
						"	<body>" + 
						"		<img src=\"img/death-note.jpg\">" + 
						"	</body>" + 
						"</html>");
	}

}
