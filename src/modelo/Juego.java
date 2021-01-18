package modelo;

import java.sql.SQLException;

import dao.DAOJuego;

public class Juego {

	private int id;
	private String nombre;
	private String desarrollador;
	private String genero;
	private String plataforma;
	private String fechaLanzamiento;
	private String imagen;
	
	//constructor vacío
	public Juego() {
		
	}


	//constructor con id
	public Juego(int id, String nombre, String desarrollador, String genero, String plataforma, String fechaLanzamiento, String imagen) {
		this.id = id;
		this.nombre = nombre;
		this.desarrollador = desarrollador;
		this.genero = genero;
		this.plataforma = plataforma;
		this.fechaLanzamiento = fechaLanzamiento;
		this.imagen = imagen;
	}

	//constructor sin id
	public Juego(String nombre, String desarrollador, String genero, String plataforma, String fechaLanzamiento, String imagen) {
		this.nombre = nombre;
		this.desarrollador = desarrollador;
		this.genero = genero;
		this.plataforma = plataforma;
		this.fechaLanzamiento = fechaLanzamiento;
		this.imagen = imagen;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getNombre() {
		return nombre;
	}



	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public String getDesarrollador() {
		return desarrollador;
	}


	public void setDesarrollador(String desarrollador) {
		this.desarrollador = desarrollador;
	}


	public String getGenero() {
		return genero;
	}


	public void setGenero(String genero) {
		this.genero = genero;
	}


	public String getPlataforma() {
		return plataforma;
	}


	public void setPlataforma(String plataforma) {
		this.plataforma = plataforma;
	}


	public String getFechaLanzamiento() {
		return fechaLanzamiento;
	}


	public void setFechaLanzamiento(String fechaLanzamiento) {
		this.fechaLanzamiento = fechaLanzamiento;
	}


	public String getImagen() {
		return imagen;
	}


	public void setImagen(String imagen) {
		this.imagen = imagen;
	}
	
	
	public void insert() throws SQLException {
		try {
			System.out.println("he llegado 1");
			DAOJuego.getInstance().insertar(this);
			System.out.println("Nuevo registro insertado");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void update() {
		try {
			DAOJuego.getInstance().actualizar(this);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void delete() {
		try {
			//llamo a la función borrar y le mando un objeto juego, que se encargará de sacar el id de ese objeto
			//y mandarselo a otra funcion borrar que se encargará de eliminarlo
			DAOJuego.getInstance().borrar(this);
			System.out.println("Registro eliminado");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void searchID(int id) {
		Juego j = null;
		try {
			j = DAOJuego.getInstance().buscarID(id);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		if (j != null) {
			this.id = j.getId();
			this.nombre = j.getNombre();
			this.desarrollador = j.getDesarrollador();
			this.genero = j.getGenero();
			this.plataforma = j.getPlataforma();
			this.imagen = j.getImagen();
		}
	}
}
