package food.store.proyecto.service;

import java.util.List;

public interface BaseService <D, DC, ID, DE>{
    public D save (DC dc);
    public D findById(ID id);
    public List<D> findAll();
    public void deleteById(ID id);
    public D update(ID id, DE de);
}
