package cn.techaction.utils;

public class ConstUtil {
	//��ǰ�û���ʶ
	public static final String CUR_USER="curUser";
	
	//�û�������֤����Ϣ
	public static final String TYPE_ACCOUNT="user_id";
	public static final String TYPE_EMAIL="email";
	public static final String TYPE_PHONE="phone";
	//ͼƬ�ļ��ϴ�·��
	public static final String UPLOAD_IMAGES_PATH="/upload/";
	
	/**
	 * �û���ɫ����
	 */
	public static class Role{
		public static final int ROLE_CUSTOMER=1;
		public static final int ROLE_ADMIN=2;
	}
	//��Ʒ״̬����
	public static class ProductStatus{
		//public static final int STATUS_NEW=1;        //����������
		public static final int STATUS_ON_SALE=1;	   //�ϼܣ�����
		public static final int STATUS_OFF_SALE=0;     //�¼ܣ�
		public static String getStatusDesc(int code) {
			String desc = "δ֪״̬";
			switch (code) {
			/*case STATUS_NEW:
				desc="����";
				break;*/
			case STATUS_ON_SALE:
				desc="����";
				break;
			case STATUS_OFF_SALE:
				desc="�¼�";
				break;
			default:
				break;
			}
			return desc;
		}
	}
	//����״̬
	public static class OrderStatus{
		
		public static final int ORDER_NO_PAY=0;		//δ����
		public static final int ORDER_PAID=1;		//�Ѿ�����
		public static final int ORDER_SHIPPED=2;	//�Ѿ�����
		public static final int ORDER_SUCCESS=3;	//�������
		//public static final int ORDER_CLOESD = 5;	//�����ر�
		public static final int ORDER_CANCELED=4;  //����ȡ��
		public static String getStatusDesc(int code) {
			String desc="δ֪״̬";
			switch (code) {
			case ORDER_CANCELED:
				desc="����ȡ��";
				break;
			case ORDER_NO_PAY:
				desc="δ����";
				break;
			case ORDER_PAID:
				desc="�Ѹ���";
				break;
			case ORDER_SHIPPED:
				desc="�ѷ���";
				break;
			case ORDER_SUCCESS:
				desc="�������";
				break;
			/*case ORDER_CLOESD:
				desc="���׹ر�";
				break;*/
			default:
				break;
			}
			return desc;
		}
	}
	//���ʽ
	public static class PaymentType{
		public static final int PAY_ON_LINE=1;    //����֧��
		public static final int PAY_CASH_ON_DELIVERY=2;//��������
		
		public static String getTypeDesc(int type) {
			if(type==PAY_ON_LINE) {
				return "����֧��";
			}else if(type==PAY_CASH_ON_DELIVERY) {
				return "����";
			}else {
				return "δ֪��ʽ";
			}
		}
	}

	public static class HotStatus{
		public static final int HOT_STATUS=1;//����
		public static final int NORMAL_STATUS=0;	//������
		public static String getHotDesc(int hot) {
			if(hot==HOT_STATUS) {
				return "����";
			}else {
				return "һ��";
			}
		}
	}
	
	public static class ProductType{
		public static final int TYPE_HNTJX=1;	//��������е
		public static final int TYPE_JZQZJJX=4;	//�������ػ���е
		public static final int TYPE_GCQZJJX=2;	//�������ػ���е
		public static final int TYPE_LMJX=7;	//·���е
	}

}
