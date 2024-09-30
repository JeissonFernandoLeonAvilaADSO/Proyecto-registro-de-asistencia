package main.util.API_Actions.API_Data;

import main.util.API_Actions.API_DataApplications;

public class API_DataTipoDocApplications extends API_DataApplications {

    public String crearTipoDocumento(String tipoDocumento) {
        return enviarDatoAAPI("TipoDocumento", "crear", "tipoDocumento", tipoDocumento, null);
    }

    public String actualizarTipoDocumento(Integer id, String tipoDocumento) {
        return enviarDatoAAPI("TipoDocumento", "actualizar", "tipoDocumento", tipoDocumento, id);
    }

    public String eliminarTipoDocumento(Integer id) {
        return enviarDatoAAPI("TipoDocumento", "eliminar", "tipoDocumento", "", id);
    }

}
