package pet.model;

public enum TipoUsuario {
	
	NAO_DEFINIDO(0, "Selecione um tipo..."),
	ADMINISTRADOR(1, "Adminstrador"), 
	FUNCIONARIO(2, "Funcionário"),
	CLIENTE(3, "Cliente");
	
	private int id;
	private String label;
	
	private TipoUsuario(int id, String label) {
		this.id = id;
		this.label = label;
	}

	public int getId() {
		return id;
	}
	
	public String getLabel() {
		return label;
	}
	
	public static TipoUsuario valueOf(int valor) {
		for (TipoUsuario tipoUsuario : TipoUsuario.values()) {
			if (valor == tipoUsuario.getId())
				return tipoUsuario;
		} 
		return null;
	}
	
}
