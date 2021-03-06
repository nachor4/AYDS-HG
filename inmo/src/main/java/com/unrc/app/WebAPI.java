package com.unrc.app;

//Modelo ActiveJDBC
//import com.unrc.app.models.*;

//ActiveJDBC
import org.javalite.activejdbc.Base;

//Controladores de datos
import com.unrc.app.Buildings;
import com.unrc.app.RealEstates;
import com.unrc.app.Owners;


public class WebAPI {
	private static void connect(){
		if (!Base.hasConnection()){
			Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/inmoapp_development", "root", "");	
			System.out.println("---> Se conecto a la base de datos."+Base.connection()+"\n");
		}
	}
	
    public static String getOwners(String city)
    {
		connect();
		return Owners.getOwnersByCity(city);
    }

    public static String getRealEstates(String city)
    {
		connect();
		return RealEstates.getRealEstatesByCity(city);
    }
    
    public static String getBuildings(
		String type,
		String city,
		String pMin,
		String pMax
	){
		connect();
		return Buildings.getBuildings(type.split(","),city,pMin,pMax);
	}    

    public static String getBuildingTypes()
    {
		connect();
		return Buildings.getBuildingTypes();
    }

    public static String addOwner(		
		String first_name, 
		String last_name, 
		String city, 
		String street, 
		String neighborhood, 
		String email)
    {
		int id;
		
		connect();
		try{
			id = Owners.add(first_name, last_name, city, street, neighborhood, email);
		}catch(Exception e){
			return "{\"msg\":\"Sorry, an error occurred while processing your request\"}";
		}
		
		return "{\"msg\":\"Inserción OK (Nuevo ID:"+id+")\"}";
    }


    public static String addRealEstate(		
		String name, 
		String website, 
		String email,
		String city, 
		String street, 
		String neighborhood)
    {
		int id;

		connect();
		try{
			id = RealEstates.add(name, city, street, neighborhood, email, website);
		}catch(Exception e){
			return "{\"msg\":\"Sorry, an error occurred while processing your request\"}";
		}
		
		return "{\"msg\":\"Inserción OK (Nuevo ID:"+id+")\"}";
    }   
    
    public static String bindOwnerRealEstate(String owner, String realEstate)
    {
		connect();
		try{
			if (Owners.addRE(Integer.parseInt(owner), Integer.parseInt(realEstate))){
				return "{\"msg\":\"Binding OK\"}";
			}else{
				return "{\"msg\":\"No se pudo hacer el Binding\"}";
			}
		}catch(Exception e){
			return "{\"msg\":\"No se pudo hacer el Binding. Datos no válidos.\"}";
		}
    }        
}
