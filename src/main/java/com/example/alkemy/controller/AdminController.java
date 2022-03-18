package com.example.alkemy.controller;

import com.example.alkemy.model.Genero;
import com.example.alkemy.model.Pelicula;
import com.example.alkemy.repo.GeneroRepository;
import com.example.alkemy.repo.PeliculaRepository;
import com.example.alkemy.service.FileSystemStorageService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private PeliculaRepository peliculaRepository;

    @Autowired
    private GeneroRepository generoRepository;

    @Autowired
    private FileSystemStorageService fileSystemStorageService;

    @GetMapping("")
    ModelAndView index(@PageableDefault(sort = "titulo", size = 5) Pageable pageable) {
        Page<Pelicula> peliculas = peliculaRepository.findAll(pageable);

        return new ModelAndView("admin/index")
                .addObject("peliculas", peliculas);
    }

    @GetMapping("/peliculas/nuevo")
    ModelAndView nuevaPelicula() {
        List<Genero> generos = generoRepository.findAll(Sort.by("titulo"));

        return new ModelAndView("admin/nueva-pelicula")
                .addObject("pelicula", new Pelicula())
                .addObject("generos", generos);
    }

    @PostMapping("/peliculas/nuevo")
    ModelAndView crearPelicula(@Validated Pelicula pelicula, BindingResult bindingResult) {
        if (bindingResult.hasErrors() || pelicula.getPortada().isEmpty()) {
            if (pelicula.getPortada().isEmpty()) {
                bindingResult.rejectValue("portada", "MultipartNotEmpty");
            }

            List<Genero> generos = generoRepository.findAll(Sort.by("titulo"));

            return new ModelAndView("admin/nueva-pelicula")
                    .addObject("pelicula", pelicula)
                    .addObject("generos", generos);
        }
        String rutaPortada = fileSystemStorageService.storage(pelicula.getPortada());
        pelicula.setRutaPortada(rutaPortada);

        peliculaRepository.save(pelicula);
        return new ModelAndView("redirect:/admin");
    }

}