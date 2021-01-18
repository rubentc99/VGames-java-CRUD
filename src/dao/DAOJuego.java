package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import modelo.Juego;
import singleton.DBconnection;

public class DAOJuego {

	private Connection con = null;
	public static DAOJuego instance = null;
	
	public DAOJuego() throws SQLException{
		//cuando el objeto se crea, me conecta a la base de datos
		con = DBconnection.getConnection();
	}
	
	public static DAOJuego getInstance() throws SQLException {
		
		if (instance == null){
			instance = new DAOJuego();
		}
		
		return instance;
	}
	
	public void insertar(Juego j) throws SQLException {
		//construyo la query
		PreparedStatement ps = con.prepareStatement(
				"INSERT INTO juegos (nombre, desarrollador, genero, plataforma, fechaLanzamiento, imagen) VALUES (?, ?, ?, ?, ?, ?)");
		//mediante los getters, le doy valor a esas '?'
		ps.setString(1, j.getNombre());
		ps.setString(2, j.getDesarrollador());
		ps.setString(3, j.getGenero());
		ps.setString(4, j.getPlataforma());
		ps.setString(5, j.getFechaLanzamiento());
		ps.setString(6, j.getImagen());
		System.out.println(ps);
		//ejecuto la query y cierro la db.
		ps.executeUpdate();
		ps.close();
		
	}
	
	public void actualizar(Juego j) throws SQLException {
		if (j.getId() == 0) { //si el id es 0 no hace nada, porque mis id empiezan a partir de 1
			return;
		}
		//construyo la query
		PreparedStatement ps = con.prepareStatement(
				"UPDATE juegos SET nombre = ?, desarrollador = ?, genero = ? plataforma = ?, fechaLanzamiento = ?, imagen = ? WHERE id = ?");
		
		//mediante los getters, le doy valor a esas '?'
		ps.setString(1, j.getNombre());
		ps.setString(2, j.getDesarrollador());
		ps.setString(3, j.getGenero());
		ps.setString(4, j.getPlataforma());
		ps.setString(5, j.getFechaLanzamiento());
		ps.setString(6, j.getImagen());
		ps.setInt(7, j.getId());

		//ejecuto la query y cierro la db.
		ps.executeUpdate();
		ps.close();
	}
	
	public ArrayList<Juego> listaJuego() throws SQLException {
		PreparedStatement ps = con.prepareStatement("SELECT * from juegos");
		ResultSet rs = ps.executeQuery();
		//creo un arraylist estático de objetos juego
		ArrayList<Juego> lista = new ArrayList<>();
		//añado el objeto al arraylist 
		while (rs.next()) {
			lista.add(new Juego(rs.getInt("id"), rs.getString("nombre"), rs.getString("desarrollador"), rs.getString("genero"),
					rs.getString("plataforma"), rs.getString("fechaLanzamiento"), rs.getString("imagen")));
		}
		rs.close();
		ps.close();
		//devuelvo la lista estática
		return lista;
	}
	
	
	/***
	 * Función que recibe un objeto juego y se encarga de llamar a la funcion borrar, mandándole a ésta el id del objeto juego sacado mediante un getter.
	 * @param j: objeto de tipo juego
	 * @throws SQLException
	 */
	public void borrar(Juego j) throws SQLException {
		borrar(j.getId());
	}

	
	/***
	 * Función que recibe un id de un objeto juego, y se encargará de eliminar el objeto de la base de datos que tiene ese ID como referencia.
	 * @param id: id de un objeto juego que quiero borrar.
	 * @throws SQLException
	 */
	public void borrar(int id) throws SQLException {
		//si el id es 0, como no existirá no hará nada
		if (id <= 0) {
			return;
		}
		//preparo la query
		PreparedStatement ps = con.prepareStatement("DELETE FROM juegos WHERE id = ?");
		ps.setInt(1, id);
		//y la ejecuto
		ps.executeUpdate();
		ps.close();
	}
	
	/***
	 * Función encargada de buscar un objeto juego mediante un ID.
	 * @param id: id del objeto que busco.
	 * @return: objeto juego al que corresponde el id que mando.
	 * @throws SQLException
	 */
	public Juego buscarID(int id) throws SQLException {
		//creo la query
		PreparedStatement ps = con.prepareStatement("SELECT * FROM juegos WHERE id = ?");
		//asigno el id al '?' con el setter
		ps.setInt(1, id);
		//ejecuto la query
		ResultSet rs = ps.executeQuery();
		
		Juego j = null; //creo un objeto juego vacío
		//y mediante los getters le doy los atributos
		if (rs.next()) {
			j = new Juego(rs.getInt("id"), rs.getString("nombre"), rs.getString("desarrollador"), rs.getString("genero"),
					rs.getString("plataforma"), rs.getString("fechaLanzamiento"), rs.getString("imagen"));
		}
		rs.close();
		ps.close();
		
		//devuelvo ese objeto juego lleno
		return j;
	}   
	
}