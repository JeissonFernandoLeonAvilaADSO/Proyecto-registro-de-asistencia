package main.util.API_Actions.API_Data;

import main.util.API_Actions.API_DataApplications;

public class API_DataAmbientesApplications extends API_DataApplications {

    public String crearAmbiente(String nombreAmbiente) {
        return enviarDatoAAPI("Ambientes", "crear", "nombreAmbiente", nombreAmbiente, null);
    }

    public String actualizarAmbiente(Integer id, String nombreAmbiente) {
        return enviarDatoAAPI("Ambientes", "actualizar", "nombreAmbiente", nombreAmbiente, id);
    }

    public String eliminarAmbiente(Integer id) {
        return enviarDatoAAPI("Ambientes", "eliminar", "nombreAmbiente", "", id);
    }
}
