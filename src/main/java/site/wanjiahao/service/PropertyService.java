package site.wanjiahao.service;

import site.wanjiahao.pojo.Page4Navigator;
import site.wanjiahao.pojo.Property;

public interface PropertyService {

    Property save(Property property);

    void delete(int id);

    Property update(Property property);

    Property findOne(int id);

    Page4Navigator<Property> list(int cid, int start, int size, int navigatePages);

    Page4Navigator<Property> list(int cid, int start, int size);

}
