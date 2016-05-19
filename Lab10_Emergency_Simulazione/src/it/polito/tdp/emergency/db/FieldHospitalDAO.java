////////////////////////////////////////////////////////////////////////////////
//             //                                                             //
//   #####     // Field hospital simulator                                    //
//  ######     // (!) 2013 Giovanni Squillero <giovanni.squillero@polito.it>  //
//  ###   \    //                                                             //
//   ##G  c\   // Field Hospital DAO                                          //
//   #     _\  // Test with MariaDB 10 on win                                 //
//   |   _/    //                                                             //
//   |  _/     //                                                             //
//             // 03FYZ - Tecniche di programmazione 2012-13                  //
////////////////////////////////////////////////////////////////////////////////

package it.polito.tdp.emergency.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.emergency.simulation.Evento;
import it.polito.tdp.emergency.simulation.Paziente;
import it.polito.tdp.emergency.db.*;

public class FieldHospitalDAO {
	
	
	public List<Paziente> aggiungiPazienti(){
		
		List<Paziente> l=new ArrayList<Paziente>();
		
		Connection conn=DBConnect.getInstance().getConnection();
		
		
		String sql="select * from patients p,arrivals a where patient=id";
		
		PreparedStatement st;
		
		try {
			st=conn.prepareStatement(sql);
			ResultSet res=st.executeQuery();
			
			while(res.next()){
				
				switch(res.getString("triage")){
				case "White":
					Paziente p=new Paziente(res.getInt("id"),res.getString("name"),Paziente.StatoPaziente.BIANCO);
					l.add(p);
					break;
				case "Yellow":
					Paziente p2=new Paziente(res.getInt("id"),res.getString("name"),Paziente.StatoPaziente.GIALLO);
					l.add(p2);
					break;
				case "Green":
					Paziente p3=new Paziente(res.getInt("id"),res.getString("name"),Paziente.StatoPaziente.VERDE);
					l.add(p3);
					break;
				case "Red":
					Paziente p4=new Paziente(res.getInt("id"),res.getString("name"),Paziente.StatoPaziente.ROSSO);
					l.add(p4);
					break;
				default:
					break;
				}
			}
			res.close();
			conn.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		
		return l;
		
	}
	
	public List<Evento> aggiungiEventi(){
		
		List<Evento> l=new ArrayList<Evento>();
		
		Connection conn=DBConnect.getInstance().getConnection();
		
		
		String sql="select * from arrivals order by timestamp";
		
		PreparedStatement st;
		
		try {
			st=conn.prepareStatement(sql);
			ResultSet res=st.executeQuery();
			long mezzanotte=0;
			
			while(res.next()){
				if(mezzanotte==0){
					mezzanotte=res.getTimestamp("timestamp").getTime()-143000;
				}
				long tempo=(res.getTimestamp("timestamp").getTime()-mezzanotte)/(1000*60);
				Evento e=new Evento(tempo,Evento.TipoEvento.PAZIENTE_ARRIVA,res.getInt("patient"));
				l.add(e);
				
			}
			res.close();
			conn.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		
		return l;
		
	}
	
	
}
