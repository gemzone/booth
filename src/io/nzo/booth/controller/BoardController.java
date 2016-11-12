package io.nzo.booth.controller;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpSession;

import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import de.neuland.jade4j.template.JadeTemplate;
import io.nzo.booth.JadeConfig;
import io.nzo.booth.model.Post;
import io.nzo.booth.model.User;
import io.nzo.orm.HibernateUtil;

@Controller
@SessionAttributes("user")
public class BoardController
{
	@ResponseBody
	@RequestMapping(path="/list", produces="text/html")
	public String create(Model model, HttpSession session1)
	{
		
//		userService.createUser();
//		User user = userJpaRepository.findByName("sdfsdfsdf");
//		System.out.println(user.getName());
//		userJpaRepository.create("asdf", "bserb", "123123", "ageewg", "nrtrtnrtn");
//		User user = session.getAttribute("user");
//		model.addAttribute("user", id);
		
		// model.addAttribute("user", "sadfasdf" );

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        EntityManager manager =  sessionFactory.createEntityManager();
        
        
        /*
        {javax.persistence.lock.timeout=-1, 
        		org.hibernate.flushMode=AUTO, 
        		javax.persistence.cache.retrieveMode=USE, 
        		javax.persistence.lock.scope=EXTENDED, 
        		javax.persistence.cache.storeMode=USE}
        */
        System.out.println("properties: " + manager.getProperties().toString());
        
        User u = (User)session.get(User.class, 604L);
        
        // User u = (User)q.getSingleResult();
        System.out.println( u.getUsername() );
        System.out.println( u.getPasswordSha2() );
        System.out.println( u.getCreationTime() );


        {
        	Post p = (Post)session.get("Post0", 1L);
    		System.out.println(  p.getText() );	
        }
		
        try
        {
        	Post p = (Post)session.get("Post1", 1L);
    		System.out.println(  p.getText() );
    		
        }
		catch(Exception e)
        {
			System.out.println( e.getMessage() );
        }
		
		JadeTemplate template = JadeConfig.getTemplate("list");
		String html = JadeConfig.renderTemplate(template, model.asMap());
		return html;
	
	}

}