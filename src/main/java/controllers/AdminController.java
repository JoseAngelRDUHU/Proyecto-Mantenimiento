package controllers;

import domain.Article;
import domain.Editor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import security.Authority;
import security.UserAccount;
import security.UserAccountService;
import services.ArticleService;
import services.CommentService;
import services.EditorService;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AdminController extends AbstractController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private EditorService editorService;

    @Autowired
    private UserAccountService userAccountService;

    @RequestMapping(value ="dashboard", method = RequestMethod.GET)
    public ModelAndView dashboard() {
        ModelAndView result = new ModelAndView("admin/dashboard");

        // numero medio de comentarios por articulo
        double avgCommentsPerArticle = articleService.averageCommentsPerArticle();

        // lista ordeanda de articulos con mas comentarios
        Collection<Article> mostCommentedArticles = articleService.findArticlesOrderByCommentsDesc();

        result.addObject("avgCommentsPerArticle", avgCommentsPerArticle);
        result.addObject("mostCommentedArticles", mostCommentedArticles);

        return result;
    }

    @RequestMapping(value = "listEditors", method = RequestMethod.GET)
    public ModelAndView listEditors() {
        ModelAndView result;

        Collection<Editor> editors = editorService.findAll();

        Map<Integer, Integer> articlesCount = new HashMap<>();
        for (Editor editor : editors) {
            int count = editorService.countByAuthorId(editor.getId());
            articlesCount.put(editor.getId(), count);
        }

        result = new ModelAndView("admin/listEditors");
        result.addObject("editors", editors);
        result.addObject("articlesCount", articlesCount);
        return result;
    }

    @RequestMapping(value = "createEditor", method = RequestMethod.GET)
    public ModelAndView showCreateEditorForm() {
        ModelAndView mav = new ModelAndView("admin/createEditor");
        mav.addObject("editor", new Editor());  // un editor vacío para rellenar el formulario
        return mav;
    }

    @RequestMapping(value = "createEditor", method = RequestMethod.POST)
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
        authority.setAuthority("EDITOR");
        userAccount.getAuthorities().add(authority);

        UserAccount savedUserAccount = userAccountService.save(userAccount);
        userAccountService.flush();

        // Crear editor y asociar cuenta de usuario
        Editor editor = new Editor();
        editor.setName(name);
        editor.setEmail(email);
        editor.setPhone(phone);
        editor.setUserAccount(savedUserAccount);

        editorService.save(editor);

        return new ModelAndView("redirect:/admin/listEditors.do");
    }

    @RequestMapping(value = "/editEditor", method = RequestMethod.GET)
    public ModelAndView edit(@RequestParam(required = true) Integer editorId) {
        Editor editor;
        if (editorId != null) {
            editor = editorService.findOne(editorId);
            Assert.notNull(editor);
        } else {
            editor = editorService.create();
        }

        ModelAndView result = new ModelAndView("admin/editEditor");
        result.addObject("editorEditor", editor);
        return result;
    }

    @RequestMapping(value = "saveEditor", method = RequestMethod.POST)
    public ModelAndView save(HttpServletRequest request) {
        try {
            int editorId = Integer.parseInt(request.getParameter("id"));
            String name = request.getParameter("name");
            String email = request.getParameter("email");
            String phone = request.getParameter("phone");
            String username = request.getParameter("username");
            String password = request.getParameter("password");

            // Recuperar el editor existente
            Editor editor = editorService.findOne(editorId);
            if (editor == null) {
                return new ModelAndView("redirect:/admin/listEditors.do");
            }

            // Actualizar datos personales

            editor.setName(name);
            editor.setEmail(email);
            editor.setPhone(phone);

            // Actualizar datos del userAccount
            UserAccount userAccount = editor.getUserAccount();
            if (username != null && !username.trim().isEmpty()) {
                userAccount.setUsername(username);
            }

            if (password != null && !password.trim().isEmpty()) {
                userAccount.setPassword(password);
            }

            userAccountService.save(userAccount);
            userAccountService.flush();

            // Guardar editor actualizado
            editor.setUserAccount(userAccount);
            editorService.save(editor);

            return new ModelAndView("redirect:/admin/listEditors.do");
        } catch (Exception e) {
            e.printStackTrace();
            ModelAndView result = new ModelAndView("admin/editEditor");
            result.addObject("message", "error.editor.update");
            return result;
        }
    }

    @RequestMapping(value = "/deleteEditor", method = RequestMethod.POST)
    public ModelAndView deleteEditor(@RequestParam int editorId) {
        Editor editor = editorService.findOne(editorId);
        Assert.notNull(editor);
        editorService.delete(editor);
        return new ModelAndView("redirect:/admin/listEditors.do");
    }

    private String encodePasswordMD5(final String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] passwordBytes = password.getBytes(StandardCharsets.UTF_8);
            byte[] hashBytes = md.digest(passwordBytes);
            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes)
                sb.append(String.format("%02x", b));
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5 algorithm not available", e);
        }
    }
}
