package hello.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Stack;

@Controller
public class JoJoController {
    @GetMapping("jotaro")
    public String jotaro(Model model){
         model.addAttribute("data","オラオラオラオラオラオラ！！！");
         return "jotaro";
    }
    @GetMapping("joske")
    public String joske(Model model) {
        model.addAttribute("data","ドラドラドラドラ！");
        return "joske";
    }

    @GetMapping("Jorin")
    public String jorin(@RequestParam("word") String line, Model model){
        model.addAttribute("word",line);
        return "jorin";
    }
}
