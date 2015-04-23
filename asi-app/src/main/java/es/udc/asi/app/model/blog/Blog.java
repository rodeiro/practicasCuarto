package es.udc.asi.app.model.blog;

import java.util.Calendar;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class Blog {

    private Long blogId;
    private String titulo;
    private Calendar fechaCreacion;
    private Long userId;
     
    
    public Blog(){}
    
    public Blog(  Long userId, String titulo, Calendar fecha)
    {
    	this.titulo=titulo;
    	this.fechaCreacion=fecha;
    	/*if (fecha != null) {
         this.fechaCreacion.set(Calendar.MILLISECOND, 0);
     }else{        this.fechaCreacion = fecha;}
    	 */
    	 this.userId=userId;
    }
    
    
    
    public Blog(Long blogId,  Long userId ,String titulo, Calendar fecha)
    {
   
    	this(userId,titulo, fecha);
    	this.blogId=blogId;
    }


    public Long getBlogId() {
        return blogId;
    }
    
    public void setBlogId(Long id){
    	this.blogId= id;
    }
    public String getTitulo() {
        return titulo;
    }
    public void setTitulo(String titulo) {
        this.titulo=titulo;
    }
    
    public void setFechaCreacion(Calendar fecha) {
        this.fechaCreacion = fecha;
        if (fechaCreacion != null) {
            this.fechaCreacion.set(Calendar.MILLISECOND, 0);
        }
    }
    public Calendar getFechaCreacion() {
        return fechaCreacion;
    }
    
    public Long getUserId() {
      return userId;
  }
    
    public void setUserId(Long UserId){
    	this.userId= UserId;
    }

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Blog other = (Blog) obj;
		
		if (fechaCreacion == null) {
			if (other.fechaCreacion != null)
				return false;
		} else if (!fechaCreacion.equals(other.fechaCreacion))
			return false;
	
		if (titulo == null) {
			if (other.titulo != null)
				return false;
		} else if (!titulo.equals(other.titulo))
			return false;
		
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;		
		
		if (blogId == null) {
			if (other.blogId != null)
				return false;
		} else if (!blogId.equals(other.blogId))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "Blog [blogId=" + blogId + ", titulo=" + titulo
				+ ", fechaCreacion=" + fechaCreacion + ", UserId=" + userId + "]";
	}

	

}