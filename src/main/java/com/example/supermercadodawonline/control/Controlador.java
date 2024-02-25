package com.example.supermercadodawonline.control;



import com.example.supermercadodawonline.model.jpa.Cliente;
import com.example.supermercadodawonline.model.jpa.Proyecto;
import com.example.supermercadodawonline.model.jpa.Tarea;
import com.example.supermercadodawonline.repositorios.RepositorioCliente;
import com.example.supermercadodawonline.repositorios.RepositorioProyecto;
import com.example.supermercadodawonline.repositorios.RepositorioTarea;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Controller
public class Controlador {

    @Autowired
    PasswordEncoder encoder;
    @Autowired
    private RepositorioCliente reCliente;
    @Autowired
    private RepositorioProyecto reProyecto;
    @Autowired
    private RepositorioTarea reTarea;

    // Representa al cliente que ha iniciado sesión.
    private Cliente login;

    @RequestMapping("/")
    public ModelAndView peticionRaiz(Authentication aut) {
        ModelAndView mv = new ModelAndView();

        if (aut == null) {
            mv.addObject("user", null);
        }
        else {
            login = reCliente.findById(aut.getName()).get();
            mv.addObject("user", login);
        }

        mv.setViewName("index");
        return mv;
    }

    @RequestMapping("/login")
    public ModelAndView peticionSesion() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("login");
        return mv;
    }

    @RequestMapping("/proyectos")
    public ModelAndView peticionProyectos(Authentication aut) {
        ModelAndView mv = new ModelAndView();
        List<Proyecto> proyectos = reProyecto.findAll();
        mv.addObject("proyectos", proyectos);
        mv.setViewName("proyectos");

        if (aut == null) {
            mv.addObject("user", null);
        }
        else {
            mv.addObject("user", login);
        }

        return mv;
    }

    @RequestMapping("/perfil")
    public ModelAndView peticionPerfil(Authentication aut) {
        ModelAndView mv = new ModelAndView();
        if (aut == null) {
            mv.addObject("user", null);
        }
        else {
            login = reCliente.findById(aut.getName()).get();
            mv.addObject("user", login);
        }
        mv.setViewName("perfil");
        return mv;
    }

    @RequestMapping("/nuevo")
    public ModelAndView peticionNuevo() {
        ModelAndView mv = new ModelAndView();
        Proyecto p = new Proyecto();
        mv.addObject("proyecto",p);
        // Falta pasar a la vista también la lista de categorias.
        mv.setViewName("nuevoproyecto");
        return mv;
    }

    @RequestMapping("/nuevatarea")
    public ModelAndView peticionNuevaTarea(Authentication authentication) {
        ModelAndView mv = new ModelAndView();
        Tarea t = new Tarea();
        mv.addObject("tarea",t);
        List<Proyecto> p = reProyecto.findAll();
        mv.addObject("proyectos", p);
        Cliente user = reCliente.findById(authentication.getName()).get();
        mv.addObject("user", user);
        mv.setViewName("nuevatarea");
        return mv;
    }

    @RequestMapping("/guardarnuevo")
    public String peticionGuardarProyecto(Proyecto p) {
        int id = reProyecto.findMaxId();
        id++;
        p.setIdproyecto(id);
        System.out.println("NUEVO PROYECTO: "+p);
        reProyecto.save(p);
        return "redirect:/";
    }

    @RequestMapping("/guardarnuevatarea")
    public String peticionGuardarNuevaTarea(Tarea t, Authentication aut) {
        int id = reTarea.findMaxId();
        id++;
        t.setIdtarea(id);
        Cliente clienteActual = reCliente.findById(aut.getName()).get();
        t.setCliente(clienteActual);
        t.setEstado("En espera");
        System.out.println("NUEVA TAREA: "+t);
        reTarea.save(t);
        return "redirect:/";
    }

    @RequestMapping("/eliminar")
    public String peticionEliminar(HttpServletRequest request) {
        String idProyecto = request.getParameter("idProyecto");
        int id = Integer.parseInt(idProyecto);
        try {
            reProyecto.deleteById(id);
        } catch (Exception e) {
            return "errorfk";
        }
        return "redirect:/";
    }

    @RequestMapping("/eliminartarea")
    public String peticionEliminarTarea(HttpServletRequest request) {
        String idtarea = request.getParameter("idtarea");
        int id = Integer.parseInt(idtarea);
        try {
            reTarea.deleteById(id);
        } catch (Exception e) {
            return "errorfk";
        }
        return "redirect:/";
    }

    @RequestMapping("/denegado")
    public ModelAndView peticionDenegado(Authentication aut) {
        ModelAndView mv = new ModelAndView();
        if(aut==null)
            mv.addObject("user", "No se ha iniciado sesión");
        else
            mv.addObject("user", aut.getName());
        mv.setViewName("denegado");
        return mv;
    }

    @RequestMapping("/alltareas")
    public ModelAndView peticionAllTareas(Authentication aut) {
        ModelAndView mv = new ModelAndView();
        List<Tarea> tareas = reTarea.findAll();
        mv.addObject("tareas", tareas);
        mv.setViewName("alltareas");

        if (aut == null) {
            mv.addObject("user", null);
        }
        else {
            mv.addObject("user", login);
        }

        return mv;
    }

    @RequestMapping("/mistareas")
    public ModelAndView peticionMisTareas(Authentication aut) {
        ModelAndView mv = new ModelAndView();

        if (aut == null) {
            mv.addObject("user", null);
        }
        else {
            Cliente clienteActual = reCliente.findById(aut.getName()).get();
            List<Tarea> tareas = reTarea.findByCliente(clienteActual);
            mv.addObject("tareas", tareas);
            mv.addObject("user", clienteActual);
        }

        mv.setViewName("mistareas");
        return mv;
    }

    @RequestMapping(value = "/editar", method = RequestMethod.GET)
    public ModelAndView peticionEditar(@RequestParam("idtarea") int id, Authentication authentication) {
        ModelAndView mv = new ModelAndView();
        Optional<Tarea> optTarea = reTarea.findById(id);
        if (optTarea.isPresent()) {
            Tarea tarea = optTarea.get();
            if (tarea.getCliente().getDNI().equals(authentication.getName())) {
                mv.addObject("tarea", tarea);
                List<Proyecto> proyectos = reProyecto.findAll();
                mv.addObject("proyectos", proyectos);
                mv.setViewName("edittarea");
            } else {
                mv.setViewName("error");
            }
        } else {
            mv.setViewName("error");
        }
        return mv;
    }

    @RequestMapping(value = "/editar", method = RequestMethod.POST)
    public String peticionEditarPost(@ModelAttribute("tarea") Tarea tareaFormulario, BindingResult result) {
        if (result.hasErrors()) {
            return "edittarea";
        }

        Optional<Tarea> optTareaExistente = reTarea.findById(tareaFormulario.getIdtarea());

        if (optTareaExistente.isPresent()) {
            Tarea tareaExistente = optTareaExistente.get();


            if (tareaFormulario.getTitulo() != null) {
                tareaExistente.setTitulo(tareaFormulario.getTitulo());
            }
            if (tareaFormulario.getDescripcion() != null) {
                tareaExistente.setDescripcion(tareaFormulario.getDescripcion());
            }
            if (tareaFormulario.getInicioPrevisto() != null) {
                tareaExistente.setInicioPrevisto(tareaFormulario.getInicioPrevisto());
            }
            if (tareaFormulario.getFinPrevisto() != null) {
                tareaExistente.setFinPrevisto(tareaFormulario.getFinPrevisto());
            }
            if (tareaFormulario.getEstado() != null) {
                tareaExistente.setEstado(tareaFormulario.getEstado());
            }

            reTarea.save(tareaExistente);
        }

        return "redirect:/";
    }

    @RequestMapping("/guardar")
    public String peticionGuardar(Tarea t) {
        Optional<Tarea> optTarea = reTarea.findById(t.getIdtarea());
        Tarea editado = optTarea.get();
        Proyecto p = new Proyecto();
        p.setIdproyecto(p.getIdproyecto());
//        editado.setProyecto(p);
        editado.setTitulo(t.getTitulo());
        editado.setDescripcion(t.getDescripcion());
        editado.setInicioPrevisto(t.getInicioPrevisto());
        editado.setFinPrevisto(t.getFinPrevisto());
        editado.setInicioReal(t.getInicioReal());
        editado.setFinReal(t.getFinReal());
        editado.setEstado(t.getEstado());
        reTarea.save(editado);
        return "redirect:/";
    }

}
