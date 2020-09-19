package site.wanjiahao.pojo;

import javax.persistence.*;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

/**
 * @Description  
 * @Author  wanjiahao
 * @Date 2020-09-17 21:27:21 
 */

@Entity
@Table(name ="productimage")
@Data
public class ProductImage implements Serializable {

	private static final long serialVersionUID =  756417256959883822L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
   	@Column(name = "id" )
	private Integer id;

   	@JoinColumn(name = "pid" )
	@ManyToOne
	private Product product;

   	@Column(name = "type" )
	private String type;
}
