package mini_prj.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import mini_prj.model.entity.Todo;
import mini_prj.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class TodoController {

    @Autowired
    private TodoRepository todoRepository;

    @GetMapping("/welcome")
    public String showWelcome() {
        return "welcome";
    }

    @PostMapping("/login")
    public String login(@RequestParam String ownerName, HttpSession session) {
        if (ownerName == null || ownerName.isBlank()) {
            return "redirect:/welcome";
        }
        session.setAttribute("ownerName", ownerName);
        return "redirect:/";
    }

    @GetMapping("/")
    public String list(Model model, HttpSession session) {
        String owner = (String) session.getAttribute("ownerName");
        if (owner == null) {
            return "redirect:/welcome";
        }
        model.addAttribute("todos", todoRepository.findAll());
        return "listTodos";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("todo", new Todo());
        return "insertTodo";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Todo todo = todoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid ID"));
        model.addAttribute("todo", todo);
        return "insertTodo";
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("todo") Todo todo, BindingResult result, RedirectAttributes ra) {
        if (result.hasErrors()) {
            return "insertTodo";
        }
        todoRepository.save(todo);
        ra.addFlashAttribute("message", "Thao tác thành công!");
        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes ra) {
        todoRepository.deleteById(id);
        ra.addFlashAttribute("message", "Xóa thành công!");
        return "redirect:/";
    }
}