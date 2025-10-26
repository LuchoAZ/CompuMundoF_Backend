package food.store.proyecto.impl;

import food.store.proyecto.entity.Base;
import food.store.proyecto.entity.mapper.BaseMapper;
import food.store.proyecto.repository.BaseRepository;
import food.store.proyecto.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public abstract class BaseImpl <E extends Base,D,DC,ID> implements BaseService<D,DC,ID> {

    @Autowired
    BaseRepository<E,ID> baseRepository;

    @Autowired
    BaseMapper<E,D,DC> baseMapper;

    @Override
    public D save (DC dc){
        E e = baseMapper.toEntity(dc);
        e = baseRepository.save(e);
        D d = baseMapper.toDto(e);
        return d;
    }

    @Override
    public D findById(ID id){ return baseMapper.toDto(baseRepository.getById(id)); }

    @Override
    public List<D> findAll(){ return baseRepository.findAll().stream().map(baseMapper::toDto).toList();}

    @Override
    public void deleteById(ID id){ baseRepository.deleteById(id);}
}
