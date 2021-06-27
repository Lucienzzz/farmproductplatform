package org.zzq.farmproductplatform.pay;


import org.zzq.farmproductplatform.common.pojo.BSResult;

public interface IPayService {
    BSResult pay(PayContext payContext) throws Exception;
    void prepareAndPay(PayContext payContext) throws Exception;
    void afterPay(PayContext payContext) throws Exception;

}
