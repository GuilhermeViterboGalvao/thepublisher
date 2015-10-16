package com.publisher.service;

import java.util.Collection;
import com.publisher.entity.Skin;
import com.publisher.utils.ResultList;
import net.bull.javamelody.MonitoredWithSpring;

@MonitoredWithSpring
public interface SkinService extends Service<Skin> {

    Collection<Skin> list(int page, int pageSize);
    
    Collection<Skin> list(int page, int pageSize, String orderBy, String order);
    
    ResultList<Skin> search(String query, int page, int pageSize);
}