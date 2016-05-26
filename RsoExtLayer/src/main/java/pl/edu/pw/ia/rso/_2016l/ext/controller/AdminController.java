package pl.edu.pw.ia.rso._2016l.ext.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by maczaj on 19.05.16.
 */
@Controller
@RequestMapping(value = "/admin")
public class AdminController {

    @RequestMapping(value = "/addNode", method = RequestMethod.POST)
    public void addNode() {
//        TODO: implement me! to ma sluzyc dodawaniu wewnetrznego
    }




}
