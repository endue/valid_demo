package com.simon.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {

    @RequestMapping("hello")
    public ModelAndView hello() {
        ModelAndView view = new ModelAndView("hello");
        view.addObject("hello", "mvc");
        return view;
    }

}