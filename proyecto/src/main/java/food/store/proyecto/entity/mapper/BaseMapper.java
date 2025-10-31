package food.store.proyecto.entity.mapper;

public interface BaseMapper <E,D,DC>{
    public E toEntity(DC dc);
    public D toDto(E e);

}
