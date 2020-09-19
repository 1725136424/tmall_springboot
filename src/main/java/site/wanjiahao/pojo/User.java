package site.wanjiahao.pojo;

import javax.persistence.*;
import java.io.Serializable;
import lombok.Data;

/**
 * @Description  
 * @Author  wanjiahao
 * @Date 2020-09-19 17:25:31 
 */

@Entity
@Table(name ="user")
@Data
public class User  implements Serializable {

	private static final long serialVersionUID =  4765193291120232035L;

   	@Column(name = "uid" )
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer uid;

   	@Column(name = "username" )
	private String username;

   	@Column(name = "password" )
	private String password;
}
