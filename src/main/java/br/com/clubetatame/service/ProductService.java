package br.com.clubetatame.service;

import java.util.Collection;
import com.publisher.service.Service;
import com.publisher.utils.ResultList;
import br.com.clubetatame.entity.Product;
import net.bull.javamelody.MonitoredWithSpring;

@MonitoredWithSpring
public interface ProductService extends Service<Product> {
	
    Collection<Product> list(int page, int pageSize);
    
    Collection<Product> list(int page, int pageSize, String orderBy, String order);
    
    ResultList<Product> search(String query, int page, int pageSize);
}