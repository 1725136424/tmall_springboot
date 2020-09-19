package site.wanjiahao.pojo;

import javax.persistence.*;
import java.io.Serializable;
import lombok.Data;
import java.util.Date;
import java.math.BigDecimal;

/**
 * @Description  
 * @Author  wanjiahao
 * @Date 2020-09-17 18:55:59 
 */

@Entity
@Table(name ="product")
@Data
public class Product  implements Serializable {

	private static final long serialVersionUID =  195454973913641150L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
   	@Column(name = "id" )
	private Integer id;

   	@Column(name = "name" )
	private String name;

   	@Column(name = "sub_title" )
	private String subTitle;

   	@Column(name = "original_price" )
	private BigDecimal originalPrice;

   	@Column(name = "promote_price" )
	private BigDecimal promotePrice;

   	@Column(name = "stock" )
	private Integer stock;

   	@JoinColumn(name = "cid")
	@ManyToOne
	private Category category;

   	@Column(name = "create_date" )
	private Date createDate;

   	// 不会关联属性
   	@Transient
   	private ProductImage firstProductImage;
}
