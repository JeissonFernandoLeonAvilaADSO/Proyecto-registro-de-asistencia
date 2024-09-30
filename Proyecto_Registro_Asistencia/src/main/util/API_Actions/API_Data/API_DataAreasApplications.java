package main.util.API_Actions.API_Data;

import main.util.API_Actions.API_DataApplications;

public class API_DataAreasApplications extends API_DataApplications {
    public String crearArea(String nombreArea) {
        return enviarDatoAAPI("Areas", "crear", "nombreArea", nombreArea, null);
    }

    public String actualizarArea(Integer id, String nombreArea) {
        return enviarDatoAAPI("Areas", "actualizar", "nombreArea", nombreArea, id);
    }

    public String eliminarArea(Integer id) {
        return enviarDatoAAPI("Areas", "eliminar", "nombreArea", "", id);
    }
}
