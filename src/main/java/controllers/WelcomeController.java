/*
 * WelcomeController.java
 *
 * Copyright (C) 2018 Universidad de Sevilla
 *
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import domain.Article;
import domain.Editor;
import domain.Reader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import repositories.EditorRepository;
import security.Authority;
import security.UserAccount;
import security.UserAccountService;
import services.ArticleService;
import services.EditorService;
import services.WelcomeService;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/welcome")
public class WelcomeController extends AbstractController {

	// Constructors -----------------------------------------------------------

	public WelcomeController() {
		super();
	}

	@Autowired
	UserAccountService userAccountService;

	@Autowired
	WelcomeService welcomeService;

	@Autowired
	ArticleService articleService;

	// Index ------------------------------------------------------------------

	@RequestMapping(value = "/index")
	public ModelAndView index(@RequestParam(required = false, defaultValue = "John Doe") String name) {
		ModelAndView result;
		SimpleDateFormat formatter;
		String moment;

		formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		moment = formatter.format(new Date());

		SecurityContext context;
		context = SecurityContextHolder.getContext();
		name = context.getAuthentication().getName();

		Collection<Article> articles = articleService.findAll();

		result = new ModelAndView("welcome/index");
		result.addObject("name", name);
		result.addObject("moment", moment);
		result.addObject("articles", articles);

		return result;
	}

	@RequestMapping(value = "createAccount", method = RequestMethod.GET)
	public ModelAndView showCreateAccountForm() {
		ModelAndView mav = new ModelAndView("welcome/createAccount");
		mav.addObject("reader", new Reader());  // un editor vacío para rellenar el formulario
		return mav;
	}

	@RequestMapping(value = "createAccount", method = RequestMethod.POST)
	public ModelAndView createEditor(HttpServletRequest request) {
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String phone = request.getParameter("phone");
		String username = request.getParameter("username");
		String password = request.getParameter("password");

		// Crear la cuenta de usuario con rol EDITOR
		UserAccount userAccount = new UserAccount();
		userAccount.setUsername(username);
		userAccount.setPassword(password);

		Authority authority = new Authority();
		authority.setAuthority("READER");
		userAccount.getAuthorities().add(authority);

		UserAccount savedUserAccount = userAccountService.save(userAccount);
		userAccountService.flush();

		// Crear reader y asociar cuenta de usuario
		Reader reader = new Reader();
		reader.setName(name);
		reader.setEmail(email);
		reader.setPhone(phone);
		reader.setUserAccount(savedUserAccount);

		welcomeService.save(reader);

		return new ModelAndView("redirect:/welcome/index.do");
	}
}
