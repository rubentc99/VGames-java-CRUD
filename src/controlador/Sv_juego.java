package controlador;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import modelo.Juego;

/**
 * Servlet implementation class Sv_videojuegos
 */
@WebServlet("/Sv_juego")
public class Sv_juego extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public Sv_juego() {
        // TODO Auto-generated constructor stub
    }

    
    protected void inicio(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException{
    	switch (request.getParameter("opcion")) {
		case "1":
			insertJuego(request, response);
			break;
		case "2":
			deleteJuego(request, response);
			break;
		case "3":
			searchJuego(request, response);
			break;
		default:
			System.out.println("Opcion no valida");
		}
    }
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			this.inicio(request, response);
		} catch (ServletException | IOException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			inicio(request, response);
		} catch (ServletException | IOException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void insertJuego(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException{
		String nombre = request.getParameter("nombre");
		String desarrollador = request.getParameter("desarrollador");
		String genero = request.getParameter("genero");
		String plataforma = request.getParameter("plataforma");
		String fechaLanzamiento = request.getParameter("fechaLanzamiento");
		String imagen = request.getParameter("imagen");
		
		Juego j = new Juego(nombre, desarrollador, genero, plataforma, fechaLanzamiento, imagen);
		
		if(request.getParameter("id") != "") { //si hemos pasado un id quiere decir que queremos actualizarlo
			int id = Integer.parseInt(request.getParameter("id"));
			j.setId(id);
			j.update();
		} else { //si no le pasamos id, es porque estamos insertando un elemento nuevo y el id se pondrá automático
			System.out.println("quiero insertar");
			j.insert();
		}
		response.sendRedirect("listaJuegos.jsp");	
	}

	
	private void deleteJuego(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		Juego j = new Juego();
		j.searchID(Integer.parseInt(request.getParameter("id")));
		j.delete();
		response.sendRedirect("listaJuegos.jsp");		
	}
	
	
	private void searchJuego(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id; //id en el que me apoyaré para referirme al objeto que quiero buscar
		if (Integer.parseInt(request.getParameter("id")) != 0) { //si el id del objeto es distinto de 0, el objeto es válido
			id = Integer.parseInt(request.getParameter("id"));
			request.setAttribute("id", id);
			RequestDispatcher vista = request.getRequestDispatcher("index.jsp"); //redirijo a esta página
			vista.forward(request, response);
		} else { //el id es 0 y el objeto no existe
			RequestDispatcher vista = request.getRequestDispatcher("index.jsp"); //redirijo a esta página
			vista.forward(request, response);
		}

	}
}
