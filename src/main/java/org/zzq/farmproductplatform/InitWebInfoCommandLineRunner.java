package org.zzq.farmproductplatform;

import org.zzq.farmproductplatform.model.dao.GlobalParameterMapper;
import org.zzq.farmproductplatform.model.entity.ProductCategory;
import org.zzq.farmproductplatform.model.entity.GlobalParameter;
import org.zzq.farmproductplatform.model.service.IProductCateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.ServletContext;
import java.util.List;

@Component
public class InitWebInfoCommandLineRunner implements CommandLineRunner {

    @Autowired
    private ServletContext servletContext;

    @Autowired
    private IProductCateService bookCateService;

    @Autowired
    private GlobalParameterMapper globalParameterMapper;



    @Override
    public void run(String... args){
        List<ProductCategory> bookCategories = bookCateService.getCategoryList();
        List<GlobalParameter> globalParameters = globalParameterMapper.selectByExample(new Example(GlobalParameter.class));

        servletContext.setAttribute("bookCategories", bookCategories);
        if(globalParameters!=null && globalParameters.size() != 0){
            servletContext.setAttribute("globalParameter", globalParameters.get(0));
        }
    }
}
