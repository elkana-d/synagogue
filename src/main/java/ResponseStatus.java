import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ResponseStatus extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // All response status is defined in the HttpServletResponse class. We
        // can then use these constants value to return process status to the
        // browser.
        response.setContentType("text/html");

        // Let say this servlet only handle request for page name inputForm. So
        // when user request for other page name error page not found 404 will
        // be returned, other wise it will be 200 which mean OK.
        String page = request.getParameter("page");
        if (page != null && page.equals("inputForm")) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "The requested page ["
                    + page + "] not found.");
        }
    }
}