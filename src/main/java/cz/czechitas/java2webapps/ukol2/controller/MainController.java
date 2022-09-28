package cz.czechitas.java2webapps.ukol2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;


@Controller
public class MainController {

    private final Random random = new Random();

    @GetMapping("/")
    public ModelAndView generator() {

        ModelAndView modelAndView = new ModelAndView("index");

        try{
            List<String> citaty = readAllLines("citaty.txt");
            int randomCitat = random.nextInt(citaty.size());
            String citat = citaty.get(randomCitat);

            List<String> obrazky = readAllLines("obrazky.txt");
            int randomObrazek = random.nextInt(obrazky.size());
            String obrazek = obrazky.get(randomObrazek);

            modelAndView.addObject("citat",citat);
            modelAndView.addObject("obrazek",obrazek);
        } catch (IOException e) {
            System.out.println("Něco je špatně: " + e.getMessage());
        }

        return modelAndView;
    }

    private static List<String> readAllLines(String resource) throws IOException {

        ClassLoader classLoader=Thread.currentThread().getContextClassLoader();

        try(InputStream inputStream=classLoader.getResourceAsStream(resource);
            BufferedReader reader=new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))){

           return reader
                    .lines()
                    .collect(Collectors.toList());
        }
    }

}
