package prendas;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Usuario {
    private String id;
	private String nombre;
    private String email;

    public Usuario(String id, String nombre, String email) {
		this.id = id;
		this.nombre = nombre;
		this.email = email;
    }
    public String getId() {
		return this.id;
    }
    public String getNombre() {
		return this.nombre;
    }
	public String getEmail() {
		return this.email;
	}

	@JsonProperty("id")
	public void setId(String id) {
		this.id = id;
    }

	@JsonProperty("nombre")
    public void setNombre(String nombre) {
		this.nombre = nombre;
    }

	@JsonProperty("email")
	public void setEmail(String email) {
		this.email = email;
	}
}
