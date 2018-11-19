package edu.ncsu.csc.itrust.action;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.ncsu.csc.itrust.model.old.dao.DAOFactory;
import edu.ncsu.csc.itrust.model.old.dao.mysql.AuthDAO;
import edu.ncsu.csc.itrust.model.old.dao.mysql.UltrasoundDAO;


/**
 * Servlet implementation class DisplayServlet
 */
@WebServlet("/DisplayServlet")
public class DisplayServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private UltrasoundDAO ultrasoundDAO;
	private AuthDAO authDAO;
    
    public void init() throws ServletException {
    }

    /**
     * @see HttpServlet#HttpServlet()
     */
    public DisplayServlet() {
        super();
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

        String uid = request.getParameter("id");
        InputStream sImage;
        
        // Check if ID is supplied to the request.
        if (uid == null) {
            // Do your thing if the ID is not supplied to the request.
            // Throw an exception, or send 404, or show default/warning image, or just ignore it.
            response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404.
            return;
        }

        try{
        	DAOFactory prodDAO = DAOFactory.getProductionInstance();
        	ultrasoundDAO = prodDAO.getUltrasoundDAO();
        	sImage = ultrasoundDAO.getImageData(Long.parseLong(uid));
        	String imageType = ultrasoundDAO.getImageType(Long.parseLong(uid));

        	byte[] byteArray = new byte[1048576];
        	int size = 0;
        	response.reset();
        	//System.out.printf(imageType);
        	response.setContentType(imageType);
        	while ((size = sImage.read(byteArray)) != -1 ) {
                response.getOutputStream().
                write(byteArray, 0, size);
        }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}