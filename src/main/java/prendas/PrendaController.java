package prendas;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.sql.*;
import java.sql.*;
import java.lang.String;
import java.io.PrintStream;


//lt --port 8080

@RestController
public class PrendaController {

	//private static final String URL_CONEXION = "jdbc:postgresql://localhost:5432/dit";
	//private static final String USUARIO = "dit";
	//private static final String CLAVE = "dit";
	
	private static final String URL_CONEXION = "jdbc:postgresql://ec2-44-215-176-210.compute-1.amazonaws.com/d9apohqs836gr6";
	private static final String USUARIO = "fguaaxnnyfjzrn";
	private static final String CLAVE = "3e9f7f20fb1287e9bffc0b2f6863b3751548d9e6e6b055f9ee7b86131042c7";
	
	
	@GetMapping("/listUsuarios")
    public List<Usuario> usuario() {
		List<Usuario> usuarios = new ArrayList();
		try {
			Connection connection = DriverManager.getConnection(URL_CONEXION, USUARIO, CLAVE);

			String sql = "SELECT * FROM usuarios";
			PreparedStatement statement = connection.prepareStatement(sql);

			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				Usuario usuario = new Usuario(resultSet.getString("id"), resultSet.getString("nombre"), resultSet.getString("email"));
	            usuarios.add(usuario);
			}

			resultSet.close();
			statement.close();
			connection.close();	

		} catch (java.sql.SQLException e) {
			e.printStackTrace(); 
		   }
		
		return usuarios;
    }
	
	
	
	
	
	
	@GetMapping("/listPrendas")
    public List<Prenda> prendas() {
		List<Prenda> prendas = new ArrayList();
		try {
			Connection connection = DriverManager.getConnection(URL_CONEXION, USUARIO, CLAVE);

			String sql = "SELECT * FROM prendas";
			PreparedStatement statement = connection.prepareStatement(sql);

			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				Prenda prenda = new Prenda(resultSet.getString("modelo"), resultSet.getString("talla"), resultSet.getString("cantidad"));
	            prendas.add(prenda);
			}

			resultSet.close();
			statement.close();
			connection.close();	

		} catch (java.sql.SQLException e) {
			e.printStackTrace(); 
		   }
		
		return prendas;
    }
	
	
	
	
	
	
	

    @GetMapping("/reqPrenda")
    public Prenda prenda(@RequestParam(value = "modelo") String modelo, @RequestParam(value = "talla") String talla) {
		Prenda prenda = null;
		try {
			Connection connection = DriverManager.getConnection(URL_CONEXION, USUARIO, CLAVE);

			String sql = "SELECT modelo, talla, cantidad FROM prendas WHERE modelo = ? AND talla = ?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, modelo);
			statement.setString(2, talla);

			ResultSet resultSet = statement.executeQuery();

			if (resultSet.next())
				    prenda = new Prenda(resultSet.getString("modelo"), resultSet.getString("talla"), resultSet.getString("cantidad"));

			resultSet.close();
			statement.close();
			connection.close();	

		} catch (java.sql.SQLException e) {
			e.printStackTrace(); 
		   }
		
		return prenda;
    }

//Este metodo lo que hace es añadir una nueva fila en la tabla de la base de datos. 
//Para añadir cantidades de una prenda, usaremos otro metodo que satisfaga esa necesidad
@PostMapping(path = "/addPrenda")
public ResponseEntity<String> addPrenda(@RequestBody Prenda nuevaPrenda){
	int resultadoExe=0;
	ResponseEntity<String> respuesta = null;
	Connection conn = null;
	Statement st = null;
	System.out.println("Ejecutando addPrenda de PrendaController");




	try{
		Class.forName("org.postgresql.Driver").newInstance();
		String sentenciaSQL = "INSERT INTO prendas (modelo, talla, cantidad) VALUES ('" + nuevaPrenda.getModelo() + "', '" + nuevaPrenda.getTalla() + "', '" + nuevaPrenda.getCantidad() + "')";
		conn = DriverManager.getConnection(URL_CONEXION, USUARIO, CLAVE);
		st = conn.createStatement();
		//Añade una nueva prenda a la base de datos de prendas
		resultadoExe = st.executeUpdate(sentenciaSQL);
		//System.out.println("Se ha insertado el contacto con resultado: " + resultadoExe);
	} catch (SQLException e){
	System.out.println("PrendaController: Excepción SQLException:" + e.getMessage());
	} catch (ClassNotFoundException e) {
	System.out.println("PrendaController: Excepción ClassNotFound Exception: " + e.getMessage());
	} catch (InstantiationException e) {
	System.out.println("PrendaController: Excepción Instantiation Exception: " + e.getMessage());
	} catch (IllegalAccessException e) {
	System.out.println("PrendaController: Excepción Illegal Access Exception: " + e.getMessage());
	} finally {
	try {
	if ( st != null ) 
		st.close();
	if ( conn != null ) 
		conn.close();
	} catch (SQLException e){
	System.out.println("PrendaController: Excepción SQLException:" + e.getMessage());
		}
	}
	if (resultadoExe >= 1)
		respuesta = new ResponseEntity<>("Prenda insertada correctamente", HttpStatus.CREATED);
	else
		respuesta = new ResponseEntity<>("Prenda no insertada",HttpStatus.INTERNAL_SERVER_ERROR);

	return respuesta;
}




@DeleteMapping(path = "/deletePrenda")
public ResponseEntity<String> deletePrenda(@RequestParam(value = "modelo") String modelo, @RequestParam(value = "talla") String talla){
	int resultadoExe=0;
	ResponseEntity<String> respuesta = null;
	Connection conn = null;
	Statement st = null;
	System.out.println("Ejecutando deletePrenda de PrendaController");




	try{
		Class.forName("org.postgresql.Driver").newInstance();
		//delete from prendas where modelo and talla
		String sentenciaSQL = "DELETE FROM prendas WHERE modelo='" + modelo + "' AND talla='" + talla + "'";
		conn = DriverManager.getConnection(URL_CONEXION, USUARIO, CLAVE);
		st = conn.createStatement();
		//Añade una nueva prenda a la base de datos de prendas
		resultadoExe = st.executeUpdate(sentenciaSQL);
		//System.out.println("Se ha insertado el contacto con resultado: " + resultadoExe);
	} catch (SQLException e){
	System.out.println("PrendaController: Excepción SQLException:" + e.getMessage());
	} catch (ClassNotFoundException e) {
	System.out.println("PrendaController: Excepción ClassNotFound Exception: " + e.getMessage());
	} catch (InstantiationException e) {
	System.out.println("PrendaController: Excepción Instantiation Exception: " + e.getMessage());
	} catch (IllegalAccessException e) {
	System.out.println("PrendaController: Excepción Illegal Access Exception: " + e.getMessage());
	} finally {
	try {
	if ( st != null ) 
		st.close();
	if ( conn != null ) 
		conn.close();
	} catch (SQLException e){
	System.out.println("PrendaController: Excepción SQLException:" + e.getMessage());
		}
	}
	if (resultadoExe >= 1)
		respuesta = new ResponseEntity<>("Prenda insertada correctamente", HttpStatus.CREATED);
	else
		respuesta = new ResponseEntity<>("Prenda no insertada",HttpStatus.INTERNAL_SERVER_ERROR);

	return respuesta;
}






@PutMapping(path = "/plusPrenda")
public ResponseEntity<String> plusPrenda(@RequestParam(value = "modelo") String modelo, @RequestParam(value = "talla") String talla, @RequestBody Prenda nuevaPrenda){
	int resultadoExe=0;
	int cantidadInicial=0;
	int cantidadFinal=0;
	ResponseEntity<String> respuesta = null;
	Connection conn = null;
	Statement st = null;
	

	try{
		conn = DriverManager.getConnection(URL_CONEXION, USUARIO, CLAVE);
		String sql = "SELECT cantidad FROM prendas WHERE modelo = ? AND talla = ?";
		PreparedStatement statement = conn.prepareStatement(sql);
		statement.setString(1, modelo);
		statement.setString(2, talla);

		//Revisar esta linea
		ResultSet resultSet = statement.executeQuery();
		if(resultSet.next())
			cantidadInicial = Integer.parseInt(resultSet.getString("cantidad"));

		cantidadFinal = cantidadInicial + Integer.parseInt(nuevaPrenda.getCantidad());


		Class.forName("org.postgresql.Driver").newInstance();
		String sentenciaSQL = "UPDATE prendas SET cantidad='" + cantidadFinal + "' WHERE modelo='" + modelo + "' AND talla='" + talla + "'";
		st = conn.createStatement();
		//Añade una nueva prenda a la base de datos de prendas

		resultadoExe=st.executeUpdate(sentenciaSQL);

		//System.out.println("Se ha insertado el contacto con resultado: " + resultadoExe);
	} catch (SQLException e){
	System.out.println("PrendaController: Excepción SQLException:" + e.getMessage());
	} catch (ClassNotFoundException e) {
	System.out.println("PrendaController: Excepción ClassNotFound Exception: " + e.getMessage());
	} catch (InstantiationException e) {
	System.out.println("PrendaController: Excepción Instantiation Exception: " + e.getMessage());
	} catch (IllegalAccessException e) {
	System.out.println("PrendaController: Excepción Illegal Access Exception: " + e.getMessage());
	} finally {
	try {
	if ( st != null ) 
		st.close();
	if ( conn != null ) 
		conn.close();
	} catch (SQLException e){
	System.out.println("PrendaController: Excepción SQLException:" + e.getMessage());
		}
	}
	if (resultadoExe >= 1)
		respuesta = new ResponseEntity<>("Prenda modificada correctamente", HttpStatus.OK);
	else
		respuesta = new ResponseEntity<>("Prenda no modificada",HttpStatus.NOT_FOUND);

	return respuesta;
}




@PutMapping(path = "/minusPrenda")
public ResponseEntity<String> minusPrenda(@RequestParam(value = "modelo") String modelo, @RequestParam(value = "talla") String talla, @RequestBody Prenda nuevaPrenda){
	int resultadoExe=0;
	int cantidadInicial=0;
	int cantidadFinal=0;
	ResponseEntity<String> respuesta = null;
	Connection conn = null;
	Statement st = null;
	

	try{
		conn = DriverManager.getConnection(URL_CONEXION, USUARIO, CLAVE);
		String sql = "SELECT cantidad FROM prendas WHERE modelo = ? AND talla = ?";
		PreparedStatement statement = conn.prepareStatement(sql);
		statement.setString(1, modelo);
		statement.setString(2, talla);

		//Revisar esta linea
		ResultSet resultSet = statement.executeQuery();
		if(resultSet.next())
			cantidadInicial = Integer.parseInt(resultSet.getString("cantidad"));

		cantidadFinal = cantidadInicial - Integer.parseInt(nuevaPrenda.getCantidad());

		if(cantidadFinal >= 0) {
			Class.forName("org.postgresql.Driver").newInstance();
			String sentenciaSQL = "UPDATE prendas SET cantidad='" + cantidadFinal + "' WHERE modelo='" + modelo + "' AND talla='" + talla + "'";
			st = conn.createStatement();
			//Añade una nueva prenda a la base de datos de prendas
	
			resultadoExe=st.executeUpdate(sentenciaSQL);
		}

		//System.out.println("Se ha insertado el contacto con resultado: " + resultadoExe);
	} catch (SQLException e){
	System.out.println("PrendaController: Excepción SQLException:" + e.getMessage());
	} catch (ClassNotFoundException e) {
	System.out.println("PrendaController: Excepción ClassNotFound Exception: " + e.getMessage());
	} catch (InstantiationException e) {
	System.out.println("PrendaController: Excepción Instantiation Exception: " + e.getMessage());
	} catch (IllegalAccessException e) {
	System.out.println("PrendaController: Excepción Illegal Access Exception: " + e.getMessage());
	} finally {
	try {
	if ( st != null ) 
		st.close();
	if ( conn != null ) 
		conn.close();
	} catch (SQLException e){
	System.out.println("PrendaController: Excepción SQLException:" + e.getMessage());
		}
	}
	if (resultadoExe >= 1)
		respuesta = new ResponseEntity<>("Prenda modificada correctamente", HttpStatus.CREATED);
	else
		respuesta = new ResponseEntity<>("Prenda no modificada",HttpStatus.INTERNAL_SERVER_ERROR);

	return respuesta;
}





}
