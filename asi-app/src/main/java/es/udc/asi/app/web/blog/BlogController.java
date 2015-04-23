package es.udc.asi.app.web.blog;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;
import es.udc.asi.app.model.adminservice.AdminService;
import es.udc.asi.app.model.blog.Blog;
import es.udc.asi.app.model.blogservice.BlogService;
import es.udc.asi.app.model.entrada.Entrada;
import es.udc.asi.app.model.user.User;
import es.udc.asi.app.model.userservice.UserService;
import es.udc.asi.app.model.util.InstanceNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;



@Controller
public class BlogController {
	
	private BlogService blogService;
	private UserService userService;
	private AdminService adminService;
	
	/* BlogService */
	public void setBlogService(BlogService blogService) {
		this.blogService = blogService;
	}
	
	/* UserService */
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	/* UserService */
	public void setAdminService(AdminService adminService) {
		this.adminService = adminService;
	}
	

	
	@RequestMapping(method = RequestMethod.GET)
	public void listblog(Model model) throws InstanceNotFoundException, SQLException, FileNotFoundException {
		/*
		User addedUser = userService.registrar(new User("John", "Maven", "Springed", "johnms", "pass", "mavenS"));
		Calendar fecha = Calendar.getInstance();
		
		Blog blog1 = adminService.CrearBlog(new Blog(addedUser.getUserId(), "Cocina", fecha));
		Blog blog2 = adminService.CrearBlog(new Blog(addedUser.getUserId(), "Cine", fecha));
		Blog blog3 = adminService.CrearBlog(new Blog(addedUser.getUserId(), "Universidad", fecha));
		
		adminService.CrearEntrada(new Entrada(blog1.getBlogId(),"Pollo frito",fecha,0,"Llamar a Don Pollo", "url", null));
		adminService.CrearEntrada(new Entrada(blog2.getBlogId(),"Fury",fecha,0,"BRAD PITT", "url", null));

		
		//TipoEntrada
		String archivo = "/Users/tirso/Documents/workspace/asi-app/iconoGif.png";
		Long tipo = adminService.crearTipo("imagen", "gif", archivo);
		adminService.CrearEntrada(new Entrada(blog3.getBlogId(),"Universidade da Coruña",fecha,1,"No", "http://www.udc.es", tipo));
		*/
		
		List<Blog> list = blogService.ConsultarBlogs();
		model.addAttribute("blogList", list);
		
	}
	
	
	
	

	@RequestMapping(method = RequestMethod.GET)
	public void blog(Model model, Long id) throws InstanceNotFoundException, SQLException{		
		
		List<Entrada> listE = blogService.VerListaEntradas(id);	
		//System.out.print(listE.get(0).getEntradaId());
		model.addAttribute("listE", listE);
		model.addAttribute("id",id);
	}
	
	
	
	@RequestMapping(method = RequestMethod.GET)
	public void entrada(Model model, Long entradaId) throws InstanceNotFoundException, SQLException{
		Entrada entrada = blogService.VerEntrada(entradaId);
		model.addAttribute("entrada", entrada);
		
	}
	
	
	
	@RequestMapping(method = RequestMethod.GET)
	public void nuevaEntrada(Model model, Long blogId)throws InstanceNotFoundException, SQLException{
		//System.out.println("\n" + blogId);
		model.addAttribute("blogId", blogId);
	}
	
	
	@RequestMapping(method = RequestMethod.POST)
	public String crearEntrada(Model model, Long blogId, String titulo, String texto, String tipo, String url, Long tipoId) throws InstanceNotFoundException, SQLException{
		Calendar fecha = Calendar.getInstance();

		Integer tipoNum = 0;
		if (tipo.equals("Enlace")){tipoNum = 1;}
		
		Long tipoE = (long) 467;
		adminService.CrearEntrada(new Entrada(blogId,titulo,fecha, tipoNum,texto,url, tipoE));		
		/*
		System.out.print("\n El blog es: " + blogId);
		System.out.print("\n El titulo es: " + titulo);
		System.out.print("\n El tipo es: " + tipo);
		System.out.print("\n El numero de tipo es:" + tipoNum);
		System.out.print("\n El texto es: " + texto);
		System.out.print("\n La URL es: " + url);
		System.out.print("\n\n\n");*/
		
		//Calendar fecha = Calendar.getInstance();
		//adminService.CrearEntrada(new Entrada(blogId,titulo,fecha,0,texto, "url", null));
		return "redirect:/blog/listblog";
	}
	
	

	
	@RequestMapping(method = RequestMethod.GET)
	public String borrarEntrada(Model model, Long entradaId) throws InstanceNotFoundException, SQLException{
		adminService.BorrarEntrada(entradaId);
		//System.out.println("Estoy borrando");
		return "redirect:/blog/listblog";
		
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public void modificarEntrada(Model model, Long entradaId, Long blog)throws InstanceNotFoundException, SQLException{
		//System.out.println("\n" + blog);
		model.addAttribute("blog", blog);
		model.addAttribute("entradaId", entradaId);
		
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String modificar(Model model, Long entradaId, Long blog, String titulo, String texto, String tipo, String url, Long tipoId) throws InstanceNotFoundException, SQLException{
		Calendar fecha = Calendar.getInstance();
		//System.out.println("Estoy modificando");
		Integer tipoNum = 0;
		if (tipo.equals("Enlace")){tipoNum = 1;}
		
		
		/*
		System.out.print("\n El blog es: " + blog);
		System.out.print("\n La entrada es: " + entradaId);
		*/
		
		
		Long tipoE = (long) 467;
		adminService.ModificarEntrada(new Entrada(entradaId,blog,titulo,fecha, tipoNum,texto,url, tipoE));		
		
		//System.out.print("\n El blog es: " + blog);
		/*
		System.out.print("\n El titulo es: " + titulo);
		System.out.print("\n El tipo es: " + tipo);
		System.out.print("\n El numero de tipo es:" + tipoNum);
		System.out.print("\n El texto es: " + texto);
		System.out.print("\n La URL es: " + url);
		System.out.print("\n\n\n");*/
		
		//Calendar fecha = Calendar.getInstance();
		//adminService.CrearEntrada(new Entrada(blogId,titulo,fecha,0,texto, "url", null));
		return "redirect:/blog/listblog";
	}
	
}