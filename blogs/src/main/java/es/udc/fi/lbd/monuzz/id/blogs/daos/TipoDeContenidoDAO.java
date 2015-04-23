package es.udc.fi.lbd.monuzz.id.blogs.daos;

import es.udc.fi.lbd.monuzz.id.blogs.model.TipoDeContenido;

public interface TipoDeContenidoDAO {
	Long create(TipoDeContenido miTipo);
	void remove (TipoDeContenido miTipo);
	TipoDeContenido findById(Long Id);
}
