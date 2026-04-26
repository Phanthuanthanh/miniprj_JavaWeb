package mini_prj.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDate;

@Entity
@Table(name = "todos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nội dung không được để trống")
    private String content;

    @NotNull(message = "Vui lòng chọn ngày hết hạn")
    @FutureOrPresent(message = "Ngày hết hạn phải từ hôm nay trở lên")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dueDate;

    private String status;
    private String priority;
}