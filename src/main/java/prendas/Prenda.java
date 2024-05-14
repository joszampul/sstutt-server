package prendas;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Prenda {
    private String modelo;
	private String talla;
    private String cantidad;

    public Prenda(String modelo, String talla, String cantidad) {
		this.modelo = modelo;
		this.talla = talla;
		this.cantidad = cantidad;
    }
    public String getModelo() {
		return this.modelo;
    }
    public String getTalla() {
		return this.talla;
    }
	public String getCantidad() {
		return this.cantidad;
	}

	@JsonProperty("modelo")
	public void setModelo(String modelo) {
		this.modelo = modelo;
    }

	@JsonProperty("talla")
    public void setTalla(String talla) {
		this.talla = talla;
    }

	@JsonProperty("cantidad")
	public void setCantidad(String cantidad) {
		this.cantidad = cantidad;
	}
}
