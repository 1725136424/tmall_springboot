package site.wanjiahao.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "category")
@Data
//@JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
public class Category {

    @Id
    // 自动增长策略
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Transient
    private List<Product> products;

    @Transient
    private List<List<Product>> productsByRow;
}
