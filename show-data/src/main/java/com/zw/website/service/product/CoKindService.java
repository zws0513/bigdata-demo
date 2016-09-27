package com.zw.website.service.product;

import com.zw.website.dao.product.CoKindMapper;
import com.zw.website.model.product.CoKind;
import com.zw.website.model.product.CoKindExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zhangws on 16/9/22.
 */
@Service
public class CoKindService {

    @Autowired
    private CoKindMapper dao;

    public List<CoKind> getAll() {
        return dao.selectByExample(new CoKindExample());
    }
}
