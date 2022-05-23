package com.atguigu.crowd.service.impl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeRefundModel;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.atguigu.crowd.service.api.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 支付宝退款和转账
 */
@Service
public class AliPayService {

    private Logger logger = LoggerFactory.getLogger(AliPayService.class);

    @Autowired
    private OrderService orderService;

    public  String appId="2016110300788957";

    // 商户私钥，您的PKCS8格式RSA2私钥
    public  String merchantPrivateKey="MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCVyvR68VfSmCLjKZlm1LdLwaD/fh533kYH4dQnH0PO8EJ+IW4D5mkjNvkt37TBhrBCJmYl85imXwE/u+mA4P6Bd9EWm9F3i+59Uy5FZoeqNOBmh0+fEBlSz41Y2eveTQi7880azpSxpDLrzX8RJTRGozLXAx1wxWbIlQTBF0tJXy7gfsKZkyQM/ngjObHVhByR+HzGHf0zRdHGcUQf1m4Yjngo++SNbmwS4HoCkmMnuLnDgqHIAYG3bnbcNPn7fOAyL6O30Qws5O8UKqfJO5PsCs0VlDElhxEynoatZ9TNNg0b994/hcCQpxJEjJV2HX+tPOf2Ddc5Zjyd6j67mMwtAgMBAAECggEAHq14MI2o/Npnmrp0TlkWSQ+Y13qO5uHA2fBoMLL4gjM2ivpxu56+vZWOptSAAnqBMqsGlJM1Gi2u5cyoX9q0hPNzAPxcg+1GpKgae+ApvLQjmRvxNJRpe0pCmoXXBqokNcoSoYDZdyA3xY9KjMpf0G+wA9UBfI0RJddPJ/SLWLUzf6oQ/V/uHuT3MYJE2br+V96MJpmr/Xtpro/s6xnHyDi1dqn2jy0lB5frBtOjz1oyJqK+8XVvvF2X82MpH6nPVVVaPaoPjx5wYa3rGqX0wFQCgMGXYRSDRU5u2xCx9XxqMV2ljKdHGPQU0nTTakiE9nuZ0zahN92uyF6xyWR3gQKBgQDFrxoBazXC8lL6lj6x6TrBMx+V1lif/XyPoq9lCW8D9wLJU9AKCkAZ5KRmkkoruABOPrLS2Fy8X52f0BRO2Tyiyrrll/P00Eyo/7n9e3GWC0IvOZ+TRwRW3H1CuA9HivhLSy32i4EAlogImGbxyzZ7Y/vACHZYf6IvTstTQlVcfQKBgQDB+yhlB74+EfOpXy1Oz0KahjxF3GVgbjdJzryUOY/BrUQqIjMdvh9m4UaEJZQxH5MpFxX3rCfRoUKf3nvAjmqGbrqYwoOJ2r6okkdzyC4s6DUqTKNTA2Z0auzjMNMYrhym9vnp8rcge5sEgy9BMNXdgqdrn06NDyiEXN0Jwp0tcQKBgQCyFvJlrtELJEXPDMXEf6n33XeqBCVpVPL/b391lY8W0lQHTeNQ+DURXqMg6M+7HRPhaaiuPE8nQTh+ffWdpyVyPxMqvb8AoRs+8Mr86lXE57WjV9VD2Cm3mwGZtrnFKRRQuJTa1IjOH87Dd5DeacVWWhe1w6niRnG1DtYr1smR/QKBgEdBhWMnZwDSA1myNqh+hg7RRI69GRhFJFYedTNVubMBfMqlaXWBg3N9STP9weQRnzM24cDrIm1k+V5Ukcz4A7WCM1UuDrey/NpbcZfcbuncGruBD3XNB/O323DRXJnyS2Ah1UfU+uQZw6XysocV81m9RtbH3EkAyndkzWVqceZhAoGAPAIj3sqqNKjfD+mxyiYCJ4lwgZZxBYERVdftLMigfR8Droz9Qa2MeHgPCvRRfHJYiPRQGGS/wDizNJBnISRJ9n36Gf5jXrfP5yqCcYRQSyh8cO0YpofOKSpLHSrsctELXRuG1g10rk74Ah+qgm/TBSD9/Zr4m0/HkipPKi7iJ4w=";

    // 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public  String alipayPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAgBrgK3AG1euBlBBF/MweN0j468YqbxtVD7a0jr4hNYLSl5zkgZvfUyfLDP5PV1cxTwIYVz68FC+lm4V64iwQPVNS+0w/hodYIa7kiXKvQvBk7kEjJO3+/7Q9BQSUdvAQW3V+NLCQOcj9t/AubpdeCq3K5r+HWXJGQ89ZG73kXo7x6Q39gKb3iM02fjCF812GRoNDu58RHBjTOLPSYikoFbuaetCuysT1e2e8pATThpBIoGn8D7p4+KaNMfjDFexNdwoLEek/SosK72dcq4FjKfMH2QH8ovQAH+KH0F19ZL1VxXkzclwAE19fUFshbafHRQnLUWby7utuXjYtphacAQIDAQAB";

    // 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
//    public static String notifyUrl = "http://工程公网访问地址/alipay.trade.page.pay-JAVA-UTF-8/notify_url.jsp";
    public  String notifyUrl="http://localhost/pay/return";

    //    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问00000
//    public static String returnUrl = "http://工程公网访问地址/alipay.trade.page.pay-JAVA-UTF-8/return_url.jsp";
    public  String returnUrl="http://localhost/pay/notify";

    // 签名方式
//    public static String sign_type = "RSA2";
    public  String signType = "RSA2";

    // 字符编码格式
//    public static String charset = "utf-8";
    public  String charset="utf-8";

    // 支付宝网关
//    public static String gateWayUrl = "https://openapi.alipay.com/gateway.do";
    public  String gatewayUrl="https://openapi.alipaydev.com/gateway.do";
    /**
     * 退款
     */
    public AlipayTradeRefundResponse refund(String outTradeNo,String tradeNo,Double totalAmount){
        try {
            AlipayClient client = new DefaultAlipayClient(gatewayUrl,appId,merchantPrivateKey,"json",charset,alipayPublicKey,signType);
            AlipayTradeRefundModel alipayTradeRefundModel = new AlipayTradeRefundModel();
            alipayTradeRefundModel.setOutTradeNo(outTradeNo);
            alipayTradeRefundModel.setTradeNo(tradeNo);
            alipayTradeRefundModel.setRefundAmount(totalAmount.toString());
            AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
            request.setBizModel(alipayTradeRefundModel);
            AlipayTradeRefundResponse execute = client.execute(request);
            // 修改订单状态
            return execute;
        } catch (AlipayApiException e) {
            logger.error("退款出现问题,详情：{}", e.getErrMsg());
            e.printStackTrace();
        }
        return null;
    }
}
