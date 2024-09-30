package main.util.API_Actions.API_Data;

import main.util.API_Actions.API_DataApplications;

public class API_DataSedeApplications extends API_DataApplications {

    public String crearSede(String nombreSede) {
        return enviarDatoAAPI("Sede", "crear", "nombreSede", nombreSede, null);
    }

    public String actualizarSede(Integer id, String nombreSede) {
        return enviarDatoAAPI("Sede", "actualizar", "nombreSede", nombreSede, id);
    }

    public String eliminarSede(Integer id) {
        return enviarDatoAAPI("Sede", "eliminar", "nombreSede", "", id);
    }
}
