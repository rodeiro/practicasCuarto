
package es.udc.asi.app.model.blog;

//import java.sql.Connection;
//import java.util.Calendar;
import java.util.List;

import es.udc.asi.app.model.util.InstanceNotFoundException;

public interface SqlBlogDao {

    public Blog create(Blog blog);

    public Blog find(Long blogId)
            throws InstanceNotFoundException;
    

    public void update(Blog blog)
            throws InstanceNotFoundException;

    public void remove(Long blogId)
            throws InstanceNotFoundException;
    
    public List<Blog> findAll(); 
 
    public List<Blog> findBlogsUsuario (Long idUser)
    			throws InstanceNotFoundException;
    
}
