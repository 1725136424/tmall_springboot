package site.wanjiahao.pojo;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "property")
@Data

public class Property {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @JoinColumn(name = "cid")
    @ManyToOne
    private Category category;

}
