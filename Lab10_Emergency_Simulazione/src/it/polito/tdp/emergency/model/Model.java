package it.polito.tdp.emergency.model;

import it.polito.tdp.emergency.db.FieldHospitalDAO;
import it.polito.tdp.emergency.simulation.Core;
import it.polito.tdp.emergency.simulation.Evento;
import it.polito.tdp.emergency.simulation.Medico;
import it.polito.tdp.emergency.simulation.Paziente;

public class Model {

	Core simulatore=new Core();
	int numMedici=1;
	int numPazienti=0;
	FieldHospitalDAO dao=new FieldHospitalDAO();

	/*protected void stub() {
		simulatore = new Core();

		simulatore.setMediciDisponibili(1);

		simulatore.aggiungiPaziente(new Paziente(1, Paziente.StatoPaziente.ROSSO));
		simulatore.aggiungiPaziente(new Paziente(2, Paziente.StatoPaziente.ROSSO));
		simulatore.aggiungiPaziente(new Paziente(3, Paziente.StatoPaziente.ROSSO));
		simulatore.aggiungiPaziente(new Paziente(4, Paziente.StatoPaziente.ROSSO));
		
		simulatore.aggiungiMedico(new Medico(1,"Marco"));
		simulatore.aggiungiMedico(new Medico(2,"Luca"));
		simulatore.aggiungiMedico(new Medico(3,"Matteo"));
		simulatore.aggiungiMedico(new Medico(4,"Mattia"));
		simulatore.aggiungiMedico(new Medico(5,"Giuseppe"));

		simulatore.aggiungiEvento(new Evento(10, Evento.TipoEvento.PAZIENTE_ARRIVA, 1));
		simulatore.aggiungiEvento(new Evento(11, Evento.TipoEvento.PAZIENTE_ARRIVA, 2));
		simulatore.aggiungiEvento(new Evento(12, Evento.TipoEvento.PAZIENTE_ARRIVA, 3));
		
		simulatore.aggiungiEvento(new Evento(10, Evento.TipoEvento.DOCTOR_INIZIA_TURNO, 1));
		simulatore.aggiungiEvento(new Evento(130, Evento.TipoEvento.DOCTOR_INIZIA_TURNO, 2));
		simulatore.aggiungiEvento(new Evento(130, Evento.TipoEvento.DOCTOR_INIZIA_TURNO, 3));
		simulatore.aggiungiEvento(new Evento(200, Evento.TipoEvento.DOCTOR_INIZIA_TURNO, 4));
		
		simulatore.aggiungiEvento(new Evento(13, Evento.TipoEvento.PAZIENTE_ARRIVA, 4));
		

		simulatore.simula();

		System.err.println("Persi:" + simulatore.getPazientiPersi());
		System.err.println("Salvati:" + simulatore.getPazientiSalvati());
	}*/
	
	public StringBuilder simula(){
		StringBuilder s=new StringBuilder();
		
		s.append(simulatore.simula());
		System.err.println("Persi:" + simulatore.getPazientiPersi());
		System.err.println("Salvati:" + simulatore.getPazientiSalvati());
		
		return s;
	}
	
	public void aggiungiPazienti(){
		//dao
		for(Paziente p :dao.aggiungiPazienti()){
			simulatore.aggiungiPaziente(p);
		}
		
	}

	public void aggiungiEventi(){
		for(Evento e:dao.aggiungiEventi()){
			simulatore.aggiungiEvento(e);
		}
	}
	
	public void aggiungiMedico(String nome,long tempo){

		
		Medico m=new Medico(numMedici,nome,tempo);
		simulatore.aggiungiMedico(m);
		numMedici++;
	}
	
	
	/*public static void main(String[] args){
		Model m=new Model();
		m.stub();
	}*/

}
