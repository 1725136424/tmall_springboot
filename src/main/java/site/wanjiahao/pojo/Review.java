package site.wanjiahao.pojo;

import javax.persistence.*;
import java.io.Serializable;
import lombok.Data;
import java.util.Date;

/**
 * @Description  
 * @Author  wanjiahao
 * @Date 2020-09-21 21:13:44 
 */

@Entity
@Table(name ="review")
@Data
public class Review  implements Serializable {

	private static final long serialVersionUID =  2377201915298501792L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
   	@Column(name = "id" )
	private Integer id;

   	@Column(name = "content" )
	private String content;

   	@JoinColumn(name = "uid" )
	@ManyToOne
	private User user;

   	@JoinColumn(name = "pid" )
	@ManyToOne
	private Product product;

   	@Column(name = "create_date" )
	private Date createDate;
}
