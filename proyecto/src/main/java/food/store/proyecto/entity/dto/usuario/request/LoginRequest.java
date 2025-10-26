package food.store.proyecto.entity.dto.usuario.request;

//Clase que representa la solicitud de login
//Las clases de tipo record sirven para crear objetos inmutables

public record LoginRequest(String mail, String password) {
}
