package org.zzq.farmproductplatform.model.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "product_desc")
public class ProductDesc {
    @Id
    @Column(name = "product_id")
    private Integer productId;

    @Column(name = "created")
    private Date created;

    @Column(name = "updated")
    private Date updated;

    @Column(name = "product_desc")
    private String productDesc;

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public String getproductDesc() {
        return productDesc;
    }

    public void setproductDesc(String productDesc) {
        this.productDesc = productDesc;
    }
}