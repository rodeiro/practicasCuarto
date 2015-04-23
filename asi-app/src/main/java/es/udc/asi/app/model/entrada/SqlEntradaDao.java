package es.udc.asi.app.model.entrada;

//import java.io.File;
import java.io.FileNotFoundException;
//import java.sql.Connection;
//import java.util.Calendar;
import java.util.List;
import es.udc.asi.app.model.util.InstanceNotFoundException;

public interface SqlEntradaDao {

	    public Entrada create(Entrada entrada);
	    
	    public Long crearTipo(String nombre, String mime, String archivo)
	    		throws FileNotFoundException;
	    
	    public void removeTipo(Long tipoId) throws InstanceNotFoundException;

    public Entrada findEntrada(Long entradaId)
            throws InstanceNotFoundException;
    
    
    public List<Entrada> findEntradasBlog(Long blogId);
    

    public void update(Entrada entrada)
            throws InstanceNotFoundException;

    public void remove(Long entradaId)
            throws InstanceNotFoundException;
    
    public void vote(Long entradaId, Long userId, Boolean voto)
            throws InstanceNotFoundException;
    
    public void removeVote(Long userId, Long entradaId) throws InstanceNotFoundException;
            
    public List <Entrada> findEntradasUsuario (Long userId)
    				throws InstanceNotFoundException;
}