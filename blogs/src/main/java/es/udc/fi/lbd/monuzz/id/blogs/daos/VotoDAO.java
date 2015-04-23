package es.udc.fi.lbd.monuzz.id.blogs.daos;
import es.udc.fi.lbd.monuzz.id.blogs.model.Voto;

public interface VotoDAO {
	void create (Voto miVoto);
	void remove (Voto miVoto);
}

