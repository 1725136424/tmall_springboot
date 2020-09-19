package site.wanjiahao.pojo;

import javax.persistence.*;
import java.io.Serializable;
import lombok.Data;

/**
 * @Description  
 * @Author  wanjiahao
 * @Date 2020-09-19 16:43:30 
 */

@Entity
@Table(name ="propertyvalue")
@Data
public class PropertyValue implements Serializable {

	private static final long serialVersionUID =  7734905602636810204L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
   	@Column(name = "id" )
	private Integer id;

   	@JoinColumn(name = "pid" )
	@ManyToOne
	private Product product;

   	@JoinColumn(name = "ptid" )
	@ManyToOne
	private Property property;

   	@Column(name = "value" )
	private String value;
}
