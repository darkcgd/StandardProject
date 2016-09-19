package com.standardproject.util.api.payutil;

import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;



import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.util.Xml;

import com.alipay.sdk.app.PayTask;
import com.standardproject.util.Config;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.ugiant.util.AbToastUtil;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.xmlpull.v1.XmlPullParser;


/**
 * 支付功能模块原型
 * @author cgd
 * 2016-2-22
 */
public class PayUtil {
	private Context context;
	private Map<String, String> resultunifiedorder;
	private PayReq req;
	private IWXAPI msgApi;
	public PayUtil(Context context) {
		this.context=context;
		msgApi = WXAPIFactory.createWXAPI(context, null);
		// 初始化微信支付变量
		req = new PayReq();
		msgApi.registerApp(Config.APP_ID);

	}
	/*
	 * 发起微信支付
	 * @param body 商品描述
	 * @param code 商户号
	 * @param price
	 * @param notifyUrl  回调地址,为空时使用默认回调地址(一般设置为null,只有当一个app里有几种支付回调.才用到  例如幸汇社区)
	 **/
	public void weixinpay(String body,String code,String price,String notifyUrl) {
		GetPrepayIdTask getPrepayId = new GetPrepayIdTask();
		if(price!=null){
			try {
				//商品价格,微信的价格单位是分 (100代表1元)
				price=String.valueOf((int)((Double.parseDouble(price))*100));
				getPrepayId.execute(body,code,price,notifyUrl);
			} catch (NumberFormatException e) {
				e.printStackTrace();
				AbToastUtil.showToast(context, "商品价格有误!");
			}
		}
	}

	/**
	 * 支付宝支付
	 * @param activty  支付界面
	 * @param productName  商品名称(一般填写商品描述即可 )
	 * @param productDetail  商品描述
	 * @param price  商品价格
	 * @param code  订单号
	 * @param notifyUrl  回调地址,为空时使用默认回调地址(一般设置为null,只有当一个app里有几种支付回调.才用到  例如幸汇社区)
	 */
	public void zhifubaoPay(final Activity activty,String productName,String productDetail,String code,String price,String notifyUrl) {
		// 订单
		String orderInfo = getOrderInfo(productName, productName, price, code,notifyUrl); // Price总价格
		// 对订单做RSA 签名
		String sign = sign(orderInfo);
		try {
			// 仅需对sign 做URL编码
			sign = URLEncoder.encode(sign, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		// 完整的符合支付宝参数规范的订单信息
		final String payInfo = orderInfo + "&sign=\"" + sign + "\"&" + getSignType();
		Runnable payRunnable = new Runnable() {
			@Override
			public void run() {
				// 构造PayTask 对象
				PayTask alipay = new PayTask(activty);
				// 调用支付接口，获取支付结果
				String result = alipay.pay(payInfo);

				Message msg = new Message();
				msg.what = 1;
				msg.obj = result;
				mHandler.sendMessage(msg);
			}
		};

		// 必须异步调用
		Thread payThread = new Thread(payRunnable);
		payThread.start();
	}


	/**
	 * create the order info. 创建订单信息
	 * @param sn
	 *
	 */
	public String getOrderInfo(String subject, String body, String price, String sn,String notifyUrl) {
		// 签约合作者身份ID
		String orderInfo = "partner=" + "\"" + Config.PARTNER + "\"";

		// 签约卖家支付宝账号
		orderInfo += "&seller_id=" + "\"" + Config.SELLER + "\"";

		// 商户网站唯一订单号
		orderInfo += "&out_trade_no=" + "\"" + sn + "\"";

		// 商品名称
		orderInfo += "&subject=" + "\"" + subject + "\"";

		// 商品详情
		orderInfo += "&body=" + "\"" + body + "\"";

		// 商品金额
		orderInfo += "&total_fee=" + "\"" + price + "\"";


		// 服务器异步通知页面路径
		//  支付宝回调地址
		if(notifyUrl==null){//回调地址,为空时使用默认回调地址
			orderInfo += "&notify_url=" + "\"" + Config.ZHIFUBAO_NOTIFY_URL + "\"";
		}else{
			orderInfo += "&notify_url=" + "\"" + notifyUrl + "\"";
		}

		// 服务接口名称， 固定值
		orderInfo += "&service=\"mobile.securitypay.pay\"";

		// 支付类型， 固定值
		orderInfo += "&payment_type=\"1\"";

		// 参数编码， 固定值
		orderInfo += "&_input_charset=\"utf-8\"";

		// 设置未付款交易的超时时间
		// 默认30分钟，一旦超时，该笔交易就会自动被关闭。
		// 取值范围：1m～15d。
		// m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
		// 该参数数值不接受小数点，如1.5h，可转换为90m。
		orderInfo += "&it_b_pay=\"30m\"";

		// extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
		// orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

		// 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
		orderInfo += "&return_url=\"m.alipay.com\"";

		// 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
		// orderInfo += "&paymethod=\"expressGateway\"";

		return orderInfo;
	}

	/**
	 * get the out_trade_no for an order. 生成商户订单号，该值在商户端应保持唯一（可自定义格式规范）
	 *
	 */
	public String getOutTradeNo() {
		SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss",
				Locale.getDefault());
		Date date = new Date();
		String key = format.format(date);

		Random r = new Random();
		key = key + r.nextInt();
		key = key.substring(0, 15);
		return key;
	}

	/**
	 * sign the order info. 对订单信息进行签名
	 *
	 * @param content
	 *            待签名订单信息
	 */
	public String sign(String content) {
		return SignUtils.sign(content, Config.RSA_PRIVATE);
	}

	/**
	 * get the sign type we use. 获取签名方式
	 *
	 */
	public String getSignType() {
		return "sign_type=\"RSA\"";
	}


	/**
	 * 以下为支付宝支付所需方法
	 */
	private static final int SDK_PAY_FLAG = 1;
	private static final int SDK_CHECK_FLAG = 2;
	/**
	 * mHandler包含支付结果信息
	 */
	private  Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case SDK_PAY_FLAG: {
					PayResult payResult = new PayResult((String) msg.obj);
					// 支付宝返回此次支付结果及加签，建议对支付宝签名信息拿签约时支付宝提供的公钥做验签
					String resultInfo = payResult.getResult();

					String resultStatus = payResult.getResultStatus();

					//iPayUtil.handlerPayResult(resultStatus);

					// 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
					if (TextUtils.equals(resultStatus, "9000")) {
						/*Toast.makeText(PayActivity.this, "支付成功",Toast.LENGTH_SHORT).show();
						// 跳转到我的订单列表
						Intent intent = new Intent(PayActivity.this,MainActivity.class);
						startActivity(intent);
						finish();*/
						iPayUtil.handlerPaySucceed();
					} else {
						// 判断resultStatus 为非“9000”则代表可能支付失败
						// “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
						if (TextUtils.equals(resultStatus, "8000")) {
							//Toast.makeText(PayActivity.this, "支付结果确认中",Toast.LENGTH_SHORT).show();
							iPayUtil.handlerPayConfirming();
						} else if (TextUtils.equals(resultStatus, "6001")) {
							//Toast.makeText(PayActivity.this, "您取消了支付",Toast.LENGTH_SHORT).show();
							iPayUtil.handlerPayCancel();
						} else {
							// 其他值就可以判断为支付失败，包括系统返回的错误
							//Toast.makeText(PayActivity.this, "支付失败",Toast.LENGTH_SHORT).show();
							iPayUtil.handlerPayFail();
						}
					}
					break;
				}
				case SDK_CHECK_FLAG: {
					//iPayUtil.handlerPayResult(resultStatus);
					//Toast.makeText(PayActivity.this, "检查结果为：" + msg.obj,Toast.LENGTH_SHORT).show();
					break;
				}
				default:
					break;
			}
		};
	};


	private class GetPrepayIdTask extends AsyncTask<String, Void, Map<String, String>> {

		private ProgressDialog dialog;

		@Override
		protected void onPreExecute() {
			dialog = ProgressDialog.show(context, "提示","正在获取预支付订单...");
		}

		@Override
		protected void onCancelled() {
			super.onCancelled();
		}

		@Override
		protected Map<String, String> doInBackground(String... params) {
			String url = String.format("https://api.mch.weixin.qq.com/pay/unifiedorder");
			String entity = genProductArgs(params[0],params[1],params[2],params[3]);// 2015091012432,2015091012431

			Log.e("orion", entity);

			byte[] buf = Util.httpPost(url, entity);

			String content = new String(buf);
			Log.e("orion", content);
			Map<String, String> xml = decodeXml(content);

			return xml;
		}

		@Override
		protected void onPostExecute(Map<String, String> result) {
			if (dialog != null) {
				dialog.dismiss();
			}
			resultunifiedorder = result;
			// 生成签名参数
			genPayReq();
			// 发起支付
			sendPayReq();

		}
	}

	public Map<String, String> decodeXml(String content) {

		try {
			Map<String, String> xml = new HashMap<String, String>();
			XmlPullParser parser = Xml.newPullParser();
			parser.setInput(new StringReader(content));
			int event = parser.getEventType();
			while (event != XmlPullParser.END_DOCUMENT) {

				String nodeName = parser.getName();
				switch (event) {
					case XmlPullParser.START_DOCUMENT:

						break;
					case XmlPullParser.START_TAG:

						if ("xml".equals(nodeName) == false) {
							// 实例化student对象
							xml.put(nodeName, parser.nextText());
						}
						break;
					case XmlPullParser.END_TAG:
						break;
				}
				event = parser.next();
			}

			return xml;
		} catch (Exception e) {
			Log.e("orion", e.toString());
		}
		return null;

	}

	private String genNonceStr() {
		Random random = new Random();
		return MD5.getMessageDigest(String.valueOf(random.nextInt(10000)).getBytes());
	}

	private long genTimeStamp() {
		return System.currentTimeMillis() / 1000;
	}

	/**
	 * 拼凑参数
	 * */
	private String genProductArgs(String body,String code,String price,String notifyUrl) {
		StringBuffer xml = new StringBuffer();

		try {
			String nonceStr = genNonceStr();

			xml.append("</xml>");
			List<NameValuePair> packageParams = new LinkedList<NameValuePair>();

			packageParams
					.add(new BasicNameValuePair("appid", Config.APP_ID));
			packageParams.add(new BasicNameValuePair("body", body));
			packageParams
					.add(new BasicNameValuePair("mch_id", Config.MCH_ID));
			packageParams.add(new BasicNameValuePair("nonce_str", nonceStr));
			if(notifyUrl==null){//回调地址,为空时使用默认回调地址
				packageParams.add(new BasicNameValuePair("notify_url",Config.WEIXIN_NOTIFY_URL));// 支付回调地址
			}else{
				packageParams.add(new BasicNameValuePair("notify_url",Config.WEIXIN_NOTIFY_URL));// 支付回调地址
			}
			// packageParams.add(new
			// BasicNameValuePair("out_trade_no",genOutTradNo()));
			packageParams.add(new BasicNameValuePair("out_trade_no", code));//// 提交订单传过来的参数
			packageParams.add(new BasicNameValuePair("spbill_create_ip","127.0.0.1"));
			// packageParams.add(new BasicNameValuePair("total_fee", "1"));
			packageParams.add(new BasicNameValuePair("total_fee", price));//PriceWeChat;// 微信的价格单位是分 (100代表1元)
			packageParams.add(new BasicNameValuePair("trade_type", "APP"));

			String sign = genPackageSign(packageParams);
			packageParams.add(new BasicNameValuePair("sign", sign));

			String xmlstring = toXml(packageParams);
			xmlstring = new String(xmlstring.getBytes("UTF-8"), "ISO-8859-1");
			return xmlstring;

		} catch (Exception e) {
			String message = e.getMessage();
			return null;
		}

	}


	/**
	 * 生成签名 以下为微信支付操作
	 */
	private String genPackageSign(List<NameValuePair> params) {
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < params.size(); i++) {
			sb.append(params.get(i).getName());
			sb.append('=');
			sb.append(params.get(i).getValue());
			sb.append('&');
		}
		sb.append("key=");
		sb.append(Config.API_KEY);

		String packageSign = MD5.getMessageDigest(sb.toString().getBytes())
				.toUpperCase();
		Log.e("orion", packageSign);
		return packageSign;
	}

	private String genAppSign(List<NameValuePair> params) {
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < params.size(); i++) {
			sb.append(params.get(i).getName());
			sb.append('=');
			sb.append(params.get(i).getValue());
			sb.append('&');
		}
		sb.append("key=");
		sb.append(Config.API_KEY);

		String appSign = MD5.getMessageDigest(sb.toString().getBytes())
				.toUpperCase();
		Log.e("orion", appSign);
		return appSign;
	}

	private String toXml(List<NameValuePair> params) {
		StringBuilder sb = new StringBuilder();
		sb.append("<xml>");
		for (int i = 0; i < params.size(); i++) {
			sb.append("<" + params.get(i).getName() + ">");

			sb.append(params.get(i).getValue());
			sb.append("</" + params.get(i).getName() + ">");
		}
		sb.append("</xml>");

		Log.e("orion", sb.toString());
		return sb.toString();
	}

	/**
	 * 生成签名参数
	 * */
	private void genPayReq() {
		String aa = Config.APP_ID;
		req.appId = Config.APP_ID;
		req.partnerId = Config.MCH_ID;
		req.prepayId = resultunifiedorder.get("prepay_id");
		req.packageValue = "Sign=WXPay";
		req.nonceStr = genNonceStr();
		req.timeStamp = String.valueOf(genTimeStamp());

		List<NameValuePair> signParams = new LinkedList<NameValuePair>();
		signParams.add(new BasicNameValuePair("appid", req.appId));
		signParams.add(new BasicNameValuePair("noncestr", req.nonceStr));
		signParams.add(new BasicNameValuePair("package", req.packageValue));
		signParams.add(new BasicNameValuePair("partnerid", req.partnerId));
		signParams.add(new BasicNameValuePair("prepayid", req.prepayId));
		signParams.add(new BasicNameValuePair("timestamp", req.timeStamp));

		req.sign = genAppSign(signParams);

		Log.e("orion", signParams.toString());

	}

	/**
	 * 发起支付
	 * */
	private void sendPayReq() {

		msgApi.registerApp(Config.APP_ID);
		msgApi.sendReq(req);
	}





	private IPayUtil iPayUtil;
	public void setPayListener(IPayUtil iPayUtil){
		this.iPayUtil=iPayUtil;
	}


	public interface IPayUtil {
		/**
		 * 支付成功
		 */
		void handlerPaySucceed();

		/**
		 * 支付失败
		 */
		void handlerPayFail();

		/**
		 * 取消支付
		 */
		void handlerPayCancel();

		/**
		 * 支付结果确认中
		 */
		void handlerPayConfirming();

	}









}
