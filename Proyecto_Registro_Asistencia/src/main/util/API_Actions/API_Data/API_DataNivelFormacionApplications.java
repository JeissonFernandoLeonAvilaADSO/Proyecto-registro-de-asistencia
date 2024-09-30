package main.util.API_Actions.API_Data;

import main.util.API_Actions.API_DataApplications;

public class API_DataNivelFormacionApplications extends API_DataApplications {

    public String crearNivelFormacion(String nombreNivelFormacion) {
        return enviarDatoAAPI("NivelFormacion", "crear", "nombreNivelFormacion", nombreNivelFormacion, null);
    }

    public String actualizarNivelFormacion(Integer id, String nombreNivelFormacion) {
        return enviarDatoAAPI("NivelFormacion", "actualizar", "nombreNivelFormacion", nombreNivelFormacion, id);
    }

    public String eliminarNivelFormacion(Integer id) {
        return enviarDatoAAPI("NivelFormacion", "eliminar", "nombreNivelFormacion", "", id);
    }
}
