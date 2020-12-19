package top.ostp.web.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Book implements Serializable {
    private static final long serialVersionUID = 1L;
    private String isbn;
    private String name;
    private Long price;
    private String cover;
}