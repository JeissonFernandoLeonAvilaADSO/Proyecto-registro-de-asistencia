package main.util.API_Actions.API_Data;

import main.util.API_Actions.API_DataApplications;

public class API_DataGenerosApplications extends API_DataApplications {

    public String crearGenero(String nombreGenero) {
        return enviarDatoAAPI("Genero", "crear", "nombreGenero", nombreGenero, null);
    }

    public String actualizarGenero(Integer id, String nombreGenero) {
        return enviarDatoAAPI("Genero", "actualizar", "nombreGenero", nombreGenero, id);
    }

    public String eliminarGenero(Integer id) {
        return enviarDatoAAPI("Genero", "eliminar", "nombreGenero", "", id);
    }
}

