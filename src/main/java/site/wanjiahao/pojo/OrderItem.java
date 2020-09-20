package site.wanjiahao.pojo;

import javax.persistence.*;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * @Description  
 * @Author  wanjiahao
 * @Date 2020-09-19 17:50:51 
 */

@Entity
@Table(name ="orderitem")
@Data
public class OrderItem implements Serializable {

	private static final long serialVersionUID =  846311927423022322L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
   	@Column(name = "id" )
	private Integer id;

   	@JoinColumn(name = "pid" )
	@ManyToOne
	private Product product;

   	@JoinColumn(name = "oid" )
	@ManyToOne
	@JsonIgnore
	private Order order;

   	@JoinColumn(name = "uid" )
	@ManyToOne
	private User user;

   	@JoinColumn(name = "num" )
	private Integer num;
}
