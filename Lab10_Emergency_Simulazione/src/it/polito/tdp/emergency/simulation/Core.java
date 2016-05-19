//////////////////////////////////////////////////////////////////-*-java-*-//
//             // Classroom code for "Tecniche di Programmazione"           //
//   #####     // (!) Giovanni Squillero <giovanni.squillero@polito.it>     //
//  ######     //                                                           //
//  ###   \    // Copying and distribution of this file, with or without    //
//   ##G  c\   // modification, are permitted in any medium without royalty //
//   #     _\  // provided this notice is preserved.                        //
//   |   _/    // This file is offered as-is, without any warranty.         //
//   |  _/     //                                                           //
//             // See: http://bit.ly/tecn-progr                             //
//////////////////////////////////////////////////////////////////////////////

package it.polito.tdp.emergency.simulation;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

public class Core {
	
	public int getPazientiSalvati() {
		return pazientiSalvati;
	}

	public int getPazientiPersi() {
		return pazientiPersi;
	}

	int pazientiSalvati = 0;
	int pazientiPersi = 0;

	Queue<Evento> listaEventi = new PriorityQueue<Evento>();
	Map<Integer, Paziente> pazienti = new HashMap<Integer, Paziente>();
	int mediciDisponibili = 0;
	Queue<Paziente> pazientiInAttesa = new PriorityQueue<Paziente>();
	Map<Integer, Medico> medici= new HashMap<Integer, Medico>();

	public int getMediciDisponibili() {
		return mediciDisponibili;
	}

	public void setMediciDisponibili(int mediciDisponibili) {
		this.mediciDisponibili = mediciDisponibili;
	}

	public void aggiungiEvento(Evento e) {
		listaEventi.add(e);
	}

	public void aggiungiPaziente(Paziente p) {
		pazienti.put(p.getId(), p);
	}
	
	public void aggiungiMedico(Medico m){
		medici.put(m.getId(), m);
		this.aggiungiEvento(new Evento(m.getTempo(),Evento.TipoEvento.DOCTOR_INIZIA_TURNO,m.getId()));
	}

	public StringBuilder passo() {
		StringBuilder s=new StringBuilder();
		Evento e = listaEventi.remove();
		switch (e.getTipo()) {
		case PAZIENTE_ARRIVA:
			System.out.println("Arrivo paziente:" + e);
			s.append("\nArrivo paziente:" + e);
			pazientiInAttesa.add(pazienti.get(e.getDato()));
			switch (pazienti.get(e.getDato()).getStato()) {
			case BIANCO:
				break;
			case GIALLO:
				this.aggiungiEvento(new Evento(e.getTempo() + 6 * 60, Evento.TipoEvento.PAZIENTE_MUORE, e.getDato()));
				break;
			case ROSSO:
				this.aggiungiEvento(new Evento(e.getTempo() + 1 * 60, Evento.TipoEvento.PAZIENTE_MUORE, e.getDato()));
				break;
			case VERDE:
				this.aggiungiEvento(new Evento(e.getTempo() + 12 * 60, Evento.TipoEvento.PAZIENTE_MUORE, e.getDato()));
				break;
			default:
				System.err.println("Panik!");
			}
			break;
		case PAZIENTE_GUARISCE:
			if (pazienti.get(e.getDato()).getStato() != Paziente.StatoPaziente.NERO) {
				System.out.println("Paziente salvato: " + e);
				s.append("\nPaziente salvato: " + e);
				pazienti.get(e.getDato()).setStato(Paziente.StatoPaziente.SALVO);
				//++mediciDisponibili;
				for(Medico m:medici.values()){
					if(m.getPaziente()!=null && m.getPaziente().getId()==e.getDato()){
						m.setPaziente(null);
						m.setStato(Medico.StatoMedico.LIBERO);
					}
				}
				++pazientiSalvati;
			}
			break;
		case PAZIENTE_MUORE:
			if (pazienti.get(e.getDato()).getStato() == Paziente.StatoPaziente.SALVO) {
				System.out.println("Paziente già salvato: " + e);
				s.append("\nPaziente già salvato: " + e);
			} else {
				if(pazienti.get(e.getDato()).getStato()==Paziente.StatoPaziente.IN_CURA){
					//++mediciDisponibili;
					for(Medico m:medici.values()){
						if(m.getPaziente()!=null && m.getPaziente().getId()==e.getDato()){
							m.setPaziente(null);
							m.setStato(Medico.StatoMedico.LIBERO);
						}
					}
				}
				++pazientiPersi;
				pazienti.get(e.getDato()).setStato(Paziente.StatoPaziente.NERO);
				System.out.println("Paziente morto: " + e);
				s.append("\nPaziente morto: " + e);
			}
			break;
		case DOCTOR_INIZIA_TURNO:
			//++mediciDisponibili;
			medici.get(e.getDato()).setStato(Medico.StatoMedico.LIBERO);
			System.out.println("Inizio turno dottore:" + medici.get(e.getDato()));
			s.append("\nInizio turno dottore:" + medici.get(e.getDato()));
			this.aggiungiEvento(new Evento(e.getTempo()+8*60,Evento.TipoEvento.DOCTOR_FINE_TURNO,e.getDato()));
			break;
		case DOCTOR_FINE_TURNO:
			//--mediciDisponibili;
			medici.get(e.getDato()).setStato(Medico.StatoMedico.PAUSA);
			System.out.println("Fine turno dottore:" + medici.get(e.getDato()));
			s.append("\nFine turno dottore:" + medici.get(e.getDato()));
			this.aggiungiEvento(new Evento(e.getTempo()+16*60,Evento.TipoEvento.DOCTOR_INIZIA_TURNO,e.getDato()));
			break;
		default:
			System.err.println("Panik!");
		}

		while (cura(e.getTempo()))
			;
		return s;
	}

	protected boolean cura(long adesso) {
		if (pazientiInAttesa.isEmpty())
			return false;
		/*if (mediciDisponibili == 0)
			return false;*/
		for(Medico m:medici.values()){
			if(m.getStato()==Medico.StatoMedico.LIBERO){
				
				Paziente p = pazientiInAttesa.remove();
				m.setPaziente(p);
				m.setStato(Medico.StatoMedico.OCCUPATO);
				if (p.getStato() != Paziente.StatoPaziente.NERO) {
					//--mediciDisponibili;
					pazienti.get(p.getId()).setStato(Paziente.StatoPaziente.IN_CURA);
					aggiungiEvento(new Evento(adesso + 30, Evento.TipoEvento.PAZIENTE_GUARISCE, p.getId()));
					System.out.println("Il medico "+m+" inizia a curare: " + p);
					
				}

				return true;
			}
		}
		return false;
	}

	public StringBuilder simula() {
		StringBuilder s=new StringBuilder();
		
		while (/*!listaEventi.isEmpty()*/(pazientiSalvati+pazientiPersi)<2000) {
			String g=passo().toString();
			s.append("\n"+g);
		}
		s.append("\nPazienti salvati: "+pazientiSalvati+"\nPazienti persi: "+pazientiPersi);
		return s;
	}

}
