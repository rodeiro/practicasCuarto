package es.udc.asi.app.model.user;



public class User {

    private Long userId;
    private String nombre;
    private String apellido1;
    private String apellido2;
    private String login;
    private String password;
    private String nombreEnPantalla;
   
     
    public User (){};

    public User(String name, String ap1, String ap2,
    		String log, String pass, String nombrePantalla){
         
    	
    		this.nombre = name;
    		this.apellido1 = ap1;
    		this.apellido2 = ap2;
    		this.login = log;
    		this.password=pass;
    		this.nombreEnPantalla = nombrePantalla;
        
    }
    
    public User(Long userId, String nombre, String apellido1, String apellido2,
    		String login, String password, String nombreEnPantalla)
    {
    	this(nombre, apellido1, apellido2, login, password, nombreEnPantalla);
    	this.userId = userId;
    	
    }


    public Long getUserId() {
        return userId;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String name) {
        this.nombre=name;
    }
    public void setOfertaId(Long usuarioId) {
        this.userId=usuarioId;
    }    
    
    public String getApellido1() {
      return apellido1;
    }
    public void setApellido1(String ap1) {
      this.apellido1 =ap1;
    }
    
    public String getApellido2() {
      return apellido2;
    }
    public void setApellido2(String ap2) {
      this.apellido2 =ap2;
    }

    
    public String getLogin() {
        return login;
    }
    public void setLogin(String log) {
        this.login =log;
    }
    
    public String getPassword() {
      return password;
  }
  public void setPassword(String pass) {
      this.password =pass;
  }
  
    public String getNombreEnPantalla() {
      return nombreEnPantalla;
    }
    public void setNombreEnPantalla(String nombre) {
      this.nombreEnPantalla = nombre;
    }
    
    

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		
		if (apellido1 == null) {
			if (other.apellido1 != null)
				return false;
		} else if (!apellido1.equals(other.apellido1))
			return false;
		
		if (apellido2 == null) {
			if (other.apellido2 != null)
				return false;
		} else if (!apellido2.equals(other.apellido2))
			return false;
		
		if (login == null) {
			if (other.login != null)
				return false;
		} else if (!login.equals(other.login))
			return false;
		
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		
		if (nombreEnPantalla == null) {
			if (other.nombreEnPantalla != null)
				return false;
		} else if (!nombreEnPantalla.equals(other.nombreEnPantalla))
			return false;
		
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "User [userId=" + userId + ", nombre=" + nombre
				+ ", apellido1=" + apellido1 + ", apellido2=" + apellido2
				+ ", login=" + login + ", password=" + password + ", NombreEnPantalla="
				+ nombreEnPantalla  + "]";
	}

	

}
