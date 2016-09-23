package com.zw.website.service.product;

import com.zw.website.dao.product.CoKindModelMapper;
import com.zw.website.model.CoKindModel;
import com.zw.website.model.CoKindModelExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zhangws on 16/9/22.
 */
@Service
public class CoKindService {

    @Autowired
    private CoKindModelMapper dao;

    public List<CoKindModel> getAll() {
        return dao.selectByExample(new CoKindModelExample());
    }
}
