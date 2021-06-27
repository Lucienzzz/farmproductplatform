package org.zzq.farmproductplatform.pay;


import org.zzq.farmproductplatform.common.pojo.BSResult;
import org.zzq.farmproductplatform.common.utils.BSResultUtil;

public abstract class AbstractPay implements IPayService {

    @Override
    public BSResult pay(PayContext payContext) throws Exception {
        prepareAndPay(payContext);
        afterPay(payContext);
        return BSResultUtil.success();
    }
}
