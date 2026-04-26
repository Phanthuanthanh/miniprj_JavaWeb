package mini_prj.controller;

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

    @GetMapping("/")
    public String list(Model model) {
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
        Todo todo = todoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("ID không hợp lệ"));
        model.addAttribute("todo", todo);
        return "insertTodo";
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("todo") Todo todo, BindingResult result, RedirectAttributes ra) {
        // Kiểm tra nếu có lỗi validation
        if (result.hasErrors()) {
            return "insertTodo"; // Trả về form để hiển thị lỗi
        }
        todoRepository.save(todo);
        ra.addFlashAttribute("message", "Thao tác thành công");
        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes ra) {
        todoRepository.deleteById(id);
        ra.addFlashAttribute("message", "Xóa công việc thành công");
        return "redirect:/";
    }
}