package es.udc.asi.app.model.user;

//import java.sql.Connection;
//import java.util.Calendar;
//import java.util.List;

import es.udc.asi.app.model.util.InstanceNotFoundException;

public interface SqlUserDao {

    public User create(User user);

    public User find(String login)
            throws InstanceNotFoundException;
    

    public void update(User user)
            throws InstanceNotFoundException;

    public void remove(Long userId)
            throws InstanceNotFoundException;
}
