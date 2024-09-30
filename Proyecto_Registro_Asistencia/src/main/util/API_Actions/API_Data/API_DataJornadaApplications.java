package main.util.API_Actions.API_Data;

import main.util.API_Actions.API_DataApplications;

public class API_DataJornadaApplications extends API_DataApplications {

    public String crearJornada(String nombreJornada) {
        return enviarDatoAAPI("JornadaFormacion", "crear", "nombreJornada", nombreJornada, null);
    }

    public String actualizarJornada(Integer id, String nombreJornada) {
        return enviarDatoAAPI("JornadaFormacion", "actualizar", "nombreJornada", nombreJornada, id);
    }

    public String eliminarJornada(Integer id) {
        return enviarDatoAAPI("JornadaFormacion", "eliminar", "nombreJornada", "", id);
    }

}
