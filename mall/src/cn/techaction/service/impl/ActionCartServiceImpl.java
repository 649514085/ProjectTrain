package cn.techaction.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import cn.techaction.common.SverResponse;
import cn.techaction.dao.ActionCartDao;
import cn.techaction.dao.ActionProductDao;
import cn.techaction.pojo.ActionCart;
import cn.techaction.pojo.ActionProduct;
import cn.techaction.service.ActionCartService;
import cn.techaction.utils.CalcUtil;
import cn.techaction.vo.ActionCartVo;
import cn.techaction.vo.ActionCartsListVo;

@Service
public class ActionCartServiceImpl implements ActionCartService {
	
	@Autowired
	private ActionCartDao actionCartDao;
	
	@Autowired
	private ActionProductDao actionProductDao;

	@Override
	public SverResponse<String> saveOrUpdate(String userId, String productId, Integer count) {
		//��֤�����Ƿ���ȷ
		if(userId==null||productId==null||count==null) {
			return SverResponse.createByErrorMessage("��������");
		}
		//�鿴�û��Ĺ��ﳵ���Ƿ������Ʒ
		ActionCart actionCart = actionCartDao.findCartByUserAndProductId(userId, productId);
		if(actionCart==null) {
			//������������
			ActionCart cart = new ActionCart();
			cart.setUser_id(userId);
			cart.setProduct_id(productId);
			cart.setQuantity(count);
			cart.setProduct_name(actionProductDao.findProductById(productId).getProduct_name());
			actionCartDao.insertCart(cart);
		}else {
			//��������������
			int num = actionCart.getQuantity()+count;
			actionCart.setQuantity(num);
			actionCartDao.updateCart(actionCart);
		}
		return SverResponse.createRespBySuccessMessage("��Ʒ�ѳɹ���ӵ����ﳵ��");
	}

	@Override
	public SverResponse<ActionCartVo> findAllCarts(String user_id) {
		if(user_id==null) {
			return SverResponse.createByErrorMessage("��������");
		}
		//�����û����ﳵ�е���Ʒ
		List<ActionCart> list = actionCartDao.findCartByUserId(user_id);
		//��װactionCartVo����
		ActionCartVo actionCartVo = createCartVo(list);
		return SverResponse.createRespBySuccess(actionCartVo);
	}
	
	/**
	 * ��װ���ﳵvo����
	 * @param list
	 * @return
	 */
	private ActionCartVo createCartVo(List<ActionCart> carts) {
		ActionCartVo actionCartVo = new ActionCartVo();
		List<ActionCartsListVo> list = Lists.newArrayList();
		//���ﳵ����Ʒ�ܼ۸�
		BigDecimal cartTotalPrice = new BigDecimal("0");
		if(CollectionUtils.isNotEmpty(carts)) {
			for(ActionCart cart : carts) {
				//ת������
				ActionCartsListVo listVo = new ActionCartsListVo();
				listVo.setUser_id(cart.getUser_id());
				listVo.setProduct_id(cart.getProduct_id());
				listVo.setChecked(cart.getChecked());
				//��װ��Ʒ��Ϣ
				ActionProduct product = actionProductDao.findProductById(listVo.getProduct_id());
				if(product!=null) {
					listVo.setProduct_name(product.getProduct_name());
					listVo.setStatus(product.getStatus());
					listVo.setPrice(product.getPrice());
					listVo.setStock(product.getStock());
					//�жϿ��
					int buyCount = 0;
					if(product.getStock()>=cart.getQuantity()) {
						buyCount = cart.getQuantity();
					}
					else {
						buyCount = product.getStock();
						//���¹��ﳵ����Ʒ����
						ActionCart updateCart = new ActionCart();
						updateCart.setUser_id(cart.getUser_id());
						updateCart.setProduct_id(cart.getProduct_id());
						updateCart.setQuantity(buyCount);
						//����ѡ��״̬
						updateCart.setChecked(cart.getChecked());
						actionCartDao.updateCart(updateCart);
					}
					listVo.setQuantity(buyCount);
					//���㹺�ﳵ��ĳ����Ʒ���ܼ۸�
					BigDecimal totalPrice = CalcUtil.mul(listVo.getPrice().doubleValue(), listVo.getQuantity().doubleValue());
					listVo.setTotalPrice(totalPrice);
					if(cart.getChecked()==1) {
						//���㹺�ﳵѡ��״̬��Ʒ���ܼ۸�
						cartTotalPrice = CalcUtil.add(cartTotalPrice.doubleValue(), listVo.getTotalPrice().doubleValue());
					}
				}
				list.add(listVo);
			}
		}
		actionCartVo.setLists(list);
		actionCartVo.setTotalPrice(cartTotalPrice);
		return actionCartVo;
	}

	@Override
	public SverResponse<String> clearCarts(String user_id) {
		//�жϲ�����ȷ
		if(user_id==null) {
			return SverResponse.createByErrorMessage("��������");
		}
		//��չ��ﳵ
		int rs = actionCartDao.deleteCartByUserId(user_id);
		if(rs>0) {
			return SverResponse.createRespBySuccessMessage("�ɹ���չ��ﳵ��");
		}
		return SverResponse.createByErrorMessage("��չ��ﳵʧ�ܣ�");
	}

	@Override
	public SverResponse<ActionCartVo> updateCart(String user_id, String productId, Integer count, Integer checked) {
		//�жϲ���
		if(user_id==null || productId==null || count==null) {
			return SverResponse.createByErrorMessage("��������");
		}
		//���¹��ﳵ��Ϣ
		ActionCart actionCart = new ActionCart();
		actionCart.setUser_id(user_id);
		actionCart.setProduct_id(productId);
		actionCart.setQuantity(count);
		actionCart.setChecked(checked);
		actionCartDao.updateCartByUserAndProductId(actionCart);
		//�������й��ﳵ��Ϣ
		return findAllCarts(user_id);
	}

	@Override
	public SverResponse<ActionCartVo> deleteCart(String user_id, String productId) {
		//�жϲ���
		if(user_id==null || productId == null) {
			return SverResponse.createByErrorMessage("��������");
		}
		//ɾ����Ʒ
		int rs = actionCartDao.deleteCart(user_id, productId);
		if(rs>0) {
			//�������й��ﳵ��Ϣ
			return this.findAllCarts(user_id);
		}
		return SverResponse.createByErrorMessage("ɾ����Ʒʧ�ܣ�");
	}

	@Override
	public SverResponse<Integer> getCartsCount(String user_id) {
		//�жϲ���
		if(user_id==null) {
			return SverResponse.createByErrorMessage("��������");
		}
		int count = actionCartDao.getCartsCountByUserId(user_id);
		return SverResponse.createRespBySuccess(Integer.valueOf(count));
	}

	@Override
	public SverResponse<ActionCartVo> findAllCheckedCarts(String user_id) {
		if(user_id==null) {
			return SverResponse.createByErrorMessage("��������");
		}
		//�����û����ﳵ�е���Ʒ
		List<ActionCart> list = actionCartDao.findCheckedCartsByUserId(user_id);
		//��װactionCartVo����
		ActionCartVo actionCartVo = createCartVo(list);
		return SverResponse.createRespBySuccess(actionCartVo);
	}

	@Override
	public SverResponse<String> clearCheckedStatus(String user_id) {
		//�жϲ���
		if(user_id==null) {
			return SverResponse.createByErrorMessage("��������");
		}
		actionCartDao.clearCheckedStatus(user_id);
		return SverResponse.createRespBySuccessMessage("���ﳵȫ����ѡ�����óɹ���");
	}

}
