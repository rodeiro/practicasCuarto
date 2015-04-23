package es.udc.asi.app.model.entrada;

import java.util.Calendar;
//import java.sql.Date;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Entrada {

    private Long entradaId;
    private Long blog;
    private String titulo;
    private Calendar fechaPublicacion;
    private Integer tipo;
    private String texto;
    private String url;
    private Long tipoId;
     

    public Entrada(){};

    public Entrada(Long blogP, String tit, Calendar fecha, 
    		Integer tip, String text, String urlEn, Long tipoEnlace){
        

    	
    		this.blog=blogP;
        this.titulo=tit;
        this.fechaPublicacion=fecha;
        /*if (fecha != null) {
            this.fechaPublicacion.set(Calendar.MILLISECOND, 0);
        }else{        this.fechaPublicacion = fecha;}*/
        
        this.tipo=tip;
        this.texto=text;
        this.url=urlEn;
        this.tipoId=tipoEnlace;

        
    }
    
    
    public Entrada(Long entrId,Long blogP, String tit, Calendar fecha, 
    		Integer tip, String text, String urlEn, Long tipoEnlace)
    {
    	this(blogP,tit, fecha, tip, text,urlEn,tipoEnlace);  	
    	this.entradaId=entrId;

    }


    public Long getEntradaId() {
        return entradaId;
    }
    public String getTitulo() {
        return titulo;
    }
    public void setTitulo(String titulo) {
        this.titulo=titulo;
    }
    
    public void setFechaPublicacion(Calendar fecha) {
        this.fechaPublicacion = fecha;
        if (fechaPublicacion != null) {
            this.fechaPublicacion.set(Calendar.MILLISECOND, 0);
        }
    }
    public Calendar getFechaPublicacion() {
        return fechaPublicacion;
    }
    
    public Integer getTipo(){
    	return tipo;
    }
    
    public void setTipo(Integer tipo){
    	this.tipo=tipo;
    }

    public Long getBlog(){
    	return blog;
    }
    
    public void setBlog(Long blog){
    	this.blog=blog;
    }
    
    public String getTexto(){
  	 return texto;
    }

	   public void setTexto(String texto){
	  	 this.texto=texto;
	   }
	   
	   
	   public Long getTipoId(){
	    	return tipoId;
	    }
	    
	    public void setTipoId(Long tipoId){
	    	this.tipoId=tipoId;
	    }
	    
	   public void setUrl(String url) {
	  	 this.url=url;
	   }
	   
	   public String getUrl(){
	  	 return url;
	   }
   
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Entrada other = (Entrada) obj;
			
			if (fechaPublicacion == null) {
				if (other.fechaPublicacion != null)
					return false;
			} else if (!fechaPublicacion.equals(other.fechaPublicacion))
				return false;
		
			if (titulo == null) {
				if (other.titulo != null)
					return false;
			} else if (!titulo.equals(other.titulo))
				return false;
			
			if (tipo == null) {
				if (other.tipo != null)
					return false;
			} else if (!tipo.equals(other.tipo))
				return false;
			
			if (texto == null) {
				if (other.texto != null)
					return false;
			} else if (!texto.equals(other.texto))
				return false;
			
			
			if (blog == null) {
				if (other.blog != null)
					return false;
			} else if (!blog.equals(other.blog))
				return false;
			
			if (tipoId == null) {
				if (other.tipoId != null)
					return false;
			} else if (!tipoId.equals(other.tipoId))
				return false;
			
			if (url == null) {
				if (other.url != null)
					return false;
			} else if (!url.equals(other.url))
				return false;
			
			if (entradaId == null) {
				if (other.entradaId != null)
					return false;
			} else if (!entradaId.equals(other.entradaId))
				return false;
			return true;
		}
		
		@Override
		public String toString() {
			return "Entrada [blogId=" + blog + ", titulo=" + titulo
					+ ", fechaPublicacion=" + fechaPublicacion +  ", tipo=" + tipo +"]";
		}

	

}