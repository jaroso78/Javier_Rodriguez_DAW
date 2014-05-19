import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JComboBox;


public class ControllerDB {
	//DB
	Connection conexion = null; //maneja la conexión
	Statement instruccion = null; //Almacena la instrucción
	
	
	public ControllerDB() {
		//Nos conectamos a la base de datos
		crearConexion();
		
	}
	
	private void crearConexion(){
		//Conectarnos a la base de datos
		try{
			Class.forName("com.mysql.jdbc.Driver");
			// establece la conexión a la base de datos
			conexion = DriverManager.getConnection("jdbc:mysql://localhost/apuestas","root","");
		}catch( SQLException excepcionSql ){
			excepcionSql.printStackTrace();
		}// fin de catch
		catch( ClassNotFoundException noEncontroClase )
		{
			noEncontroClase.printStackTrace();
		}// fin de catch			
	}
	
	//En este método leeremos de la base de datos los equipos existentes
	// 1.- Leer de la base de datos
	// 2.- Actualizar el combobox
	public void leerEquipos(JComboBox listadoEquipos){
		//Aquí realizaremos la consulta y actualización del combobox
		
		try{
		instruccion = (Statement) conexion.createStatement();
		ResultSet conjuntoResultados = instruccion.executeQuery("Select nombreEquipo from  equipos");
		//conjuntoResultados.next();
		while (conjuntoResultados.next()){
			
			
			listadoEquipos.addItem(conjuntoResultados.getString(1));
		}
		}
		catch (SQLException sqlex){
			sqlex.printStackTrace();
		}
		
		
		
			


	}
	
	//En este método leeremos de la base de datos los equipos existentes
	// 1.- Crearemos la consulta a la base de datos (Statement)
	// 2.- Lanzaremos la consulta
	// 3.- Eliminaremos todos los elementos del combobox
	// 4.- Volveremos a rellenar el combobox
	public void insertarEquipos(int idLiga,String nombreEquipo,int golesFavor,int golesContra, int partidosGanados,int partidosPerdidos,JComboBox listadoEquipos){
		//Aquí realizaremos la consulta
		
		try{
		instruccion = (Statement) conexion.createStatement();
		String sql_inst = "INSERT into Equipos (idLiga,nombreEquipo, golesFavor, golesEnContra, partidosGanados, partidosPerdidos )";
		sql_inst = sql_inst + " VALUES ( "+idLiga+",'"+nombreEquipo+"',"+golesFavor+","+golesContra+","+partidosGanados+","+partidosPerdidos+")";
		System.out.println(sql_inst);
		instruccion.executeUpdate(sql_inst);
		}
		catch(SQLException excepcionSql)
		{
			excepcionSql.printStackTrace();
		}
		
			
		
		//Actualización del combobox
		listadoEquipos.removeAllItems();
		leerEquipos(listadoEquipos);
	}
}
