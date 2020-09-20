package site.wanjiahao.pojo;

import javax.persistence.*;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import site.wanjiahao.service.OrderService;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Description  
 * @Author  wanjiahao
 * @Date 2020-09-19 17:53:00 
 */

@Entity
@Table(name ="order_")
@Data
public class Order  implements Serializable {

	private static final long serialVersionUID =  5210154293232199009L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
   	@Column(name = "id" )
	private Integer id;

   	@Column(name = "order_code" )
	private String orderCode;

   	@Column(name = "address" )
	private String address;

   	@Column(name = "post" )
	private String post;

   	@Column(name = "receiver" )
	private String receiver;

   	@Column(name = "mobile" )
	private String mobile;

   	@Column(name = "user_message" )
	private String userMessage;

   	@Column(name = "create_date" )
	private Date createDate;

   	@Column(name = "pay_date" )
	private Date payDate;

   	@Column(name = "delivery_date" )
	private Date deliveryDate;

   	@Column(name = "confirm_date" )
	private Date confirmDate;

   	@JoinColumn(name = "uid" )
	@ManyToOne
	private User user;

   	@Column(name = "status" )
	private String status;

   	// 需求订单项
	@Transient
	private List<OrderItem> orderItems;

	@Transient
	private BigDecimal total;

	@Transient
	private Integer totalNumber;

	@Transient
	private String statusDesc;

	public String getStatusDesc(){
		String desc ="未知";
		switch(status){
			case OrderService.waitPay:
				desc="待付款";
				break;
			case OrderService.waitDelivery:
				desc="待发货";
				break;
			case OrderService.waitConfirm:
				desc="待收货";
				break;
			case OrderService.waitReview:
				desc="等评价";
				break;
			case OrderService.finish:
				desc="完成";
				break;
			case OrderService.delete:
				desc="刪除";
				break;
			default:
				desc="未知";
		}
		return desc;
	}
}
