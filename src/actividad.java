public class actividad {

	private int tiempo_inicio;
	private int tiempo_fin;
	private int id;
	
	// Constructor
	public actividad(int tiempo_inicio, int tiempo_fin, int id) {
		super();
		this.tiempo_inicio = tiempo_inicio;
		this.tiempo_fin = tiempo_fin;
		this.id = id;
	}

	
	// Getters & Setters
	public int getTiempo_inicio() {
		return tiempo_inicio;
	}


	public void setTiempo_inicio(int tiempo_inicio) {
		this.tiempo_inicio = tiempo_inicio;
	}


	public int getTiempo_fin() {
		return tiempo_fin;
	}


	public void setTiempo_fin(int tiempo_fin) {
		this.tiempo_fin = tiempo_fin;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}
	
	
	
	
}
