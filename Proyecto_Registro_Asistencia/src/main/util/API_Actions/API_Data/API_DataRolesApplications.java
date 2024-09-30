package main.util.API_Actions.API_Data;

import main.util.API_Actions.API_DataApplications;

public class API_DataRolesApplications extends API_DataApplications {

    public String crearRol(String nombreRol) {
        return enviarDatoAAPI("Rol", "crear", "nombreRol", nombreRol, null);
    }

    public String actualizarRol(Integer id, String nombreRol) {
        return enviarDatoAAPI("Rol", "actualizar", "nombreRol", nombreRol, id);
    }

    public String eliminarRol(Integer id) {
        return enviarDatoAAPI("Rol", "eliminar", "nombreRol", "", id);
    }
}
