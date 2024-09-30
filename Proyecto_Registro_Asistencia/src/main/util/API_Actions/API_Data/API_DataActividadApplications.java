package main.util.API_Actions.API_Data;

import main.util.API_Actions.API_DataApplications;

public class API_DataActividadApplications extends API_DataApplications {

    public String crearActividad(String nombreActividad) {
        return enviarDatoAAPI("Actividades", "crear", "nombreActividad", nombreActividad, null);
    }

    public String actualizarActividad(Integer id, String nombreActividad) {
        return enviarDatoAAPI("Actividades", "actualizar", "nombreActividad", nombreActividad, id);
    }

    public String eliminarActividad(Integer id) {
        return enviarDatoAAPI("Actividades", "eliminar", "nombreActividad", "", id);
    }

}
