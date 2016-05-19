package it.polito.tdp.emergency.simulation;

public class Medico {
	
	public enum StatoMedico{
		LIBERO, OCCUPATO, PAUSA
	};

	private int id;
	private String nome;
	private long tempo;
	private StatoMedico stato;
	private Paziente paziente;
	
	public Medico(int id, String nome,long tempo) {
		super();
		this.id = id;
		this.nome = nome;
		this.tempo=tempo;
		
		this.stato = Medico.StatoMedico.PAUSA;
	}

	public StatoMedico getStato() {
		return stato;
	}

	public void setStato(StatoMedico stato) {
		this.stato = stato;
	}

	public int getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	@Override
	public int hashCode() {
		return id;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Medico other = (Medico) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Medico [id=" + id + ", nome=" + nome + ", stato=" + stato + "]";
	}

	public Paziente getPaziente() {
		return paziente;
	}

	public void setPaziente(Paziente paziente) {
		this.paziente = paziente;
	}

	public long getTempo() {
		return tempo;
	}

	public void setTempo(long tempo) {
		this.tempo = tempo;
	}
	
	
	
}
