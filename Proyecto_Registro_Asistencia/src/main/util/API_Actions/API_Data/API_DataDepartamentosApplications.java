package main.util.API_Actions.API_Data;

import main.util.API_Actions.API_DataApplications;

public class API_DataDepartamentosApplications extends API_DataApplications {
    public String crearDepartamento(String nombreDepartamento) {
        return enviarDatoAAPI("Departamentos", "crear", "nombreDepartamento", nombreDepartamento, null);
    }

    public String actualizarDepartamento(Integer id, String nombreDepartamento) {
        return enviarDatoAAPI("Departamentos", "actualizar", "nombreDepartamento", nombreDepartamento, id);
    }

    public String eliminarDepartamento(Integer id) {
        return enviarDatoAAPI("Departamentos", "eliminar", "nombreDepartamento", "", id);
    }
}
