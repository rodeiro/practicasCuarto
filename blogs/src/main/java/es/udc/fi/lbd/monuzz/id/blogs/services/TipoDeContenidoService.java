package es.udc.fi.lbd.monuzz.id.blogs.services;
import es.udc.fi.lbd.monuzz.id.blogs.model.TipoDeContenido;

public interface TipoDeContenidoService {
	void registrar (TipoDeContenido miTipo);
	void borrar(TipoDeContenido miTipo);
	TipoDeContenido BuscarPorId(Long id);
}
