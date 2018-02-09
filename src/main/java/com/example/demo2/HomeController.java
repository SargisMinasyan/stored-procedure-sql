package com.example.demo2;

import com.example.demo2.client.Contact;
import com.example.demo2.client.ContactDAO;
import com.example.demo2.client.ContactDAOImpl;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private ContactDAO contactDAO;


    @RequestMapping(value = "/")
    public ModelAndView listContact(ModelAndView model) throws IOException {
        List<Contact> listContact = contactDAO.list();
        model.addObject("listContact", listContact);
        model.setViewName("index");

        return model;
    }

    @RequestMapping(value = "/newContactpage")
    public  ModelAndView newContactpage(ModelAndView modelAndView ) {
        modelAndView.setViewName("index");
        return modelAndView;
    }
    @RequestMapping(value = "/newContact", method = RequestMethod.GET)
    public ModelAndView newContact(HttpServletRequest request) {
        Contact newContact = new Contact();
        ModelAndView model = new ModelAndView("ContactForm");
        model.addObject("contact", newContact);
        model.setViewName("ContactForm");
        return model;
    }

    @RequestMapping(value = "/saveContact", method = RequestMethod.POST)
    public ModelAndView saveContact(@ModelAttribute("contact") Contact contact, ModelAndView modelAndView) {

        contactDAO.saveOrUpdate(contact);
        modelAndView.setViewName("index");


    return modelAndView;
    }

    /*@RequestMapping(value = "/deleteContact", method = RequestMethod.GET)
    public ModelAndView deleteContact(HttpServletRequest request) {
        int contactId = Integer.parseInt(request.getParameter("id"));
        contactDAO.delete(contactId);
        return new ModelAndView("redirect:/");
    }*/
    @RequestMapping(value = "/editContact", method = RequestMethod.GET)
    public ModelAndView editContact(HttpServletRequest request) {
        int contactId = Integer.parseInt(request.getParameter("id"));
        Contact contact = contactDAO.get(contactId);
        ModelAndView model = new ModelAndView("ContactForm");
        model.addObject("contact", contact);

        return model;
    }
    @RequestMapping(value = "/well")
    public String well( ) {


        contactDAO.test();

        return "ok";
    }
}