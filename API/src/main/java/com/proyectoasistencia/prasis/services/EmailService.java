package com.proyectoasistencia.prasis.services;

import com.proyectoasistencia.prasis.models.UsersModels.AprendizModel;
import com.proyectoasistencia.prasis.models.UsersModels.InstructorModel;
import com.proyectoasistencia.prasis.models.UsersModels.PerfilUsuarioModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Async
    public void enviarCorreoRegistro(PerfilUsuarioModel perfilUsuario) {
        // Construir el mensaje del correo con los datos comunes
        StringBuilder mensaje = new StringBuilder();
        mensaje.append("Bienvenido, ").append(perfilUsuario.getNombres()).append(" ").append(perfilUsuario.getApellidos()).append("!\n\n");
        mensaje.append("Estos son tus datos de registro:\n");
        mensaje.append("Usuario: ").append(perfilUsuario.getUser()).append("\n");
        mensaje.append("Documento: ").append(perfilUsuario.getDocumento()).append("\n");
        mensaje.append("Tipo de Documento: ").append(perfilUsuario.getTipoDocumento()).append("\n");
        mensaje.append("Correo: ").append(perfilUsuario.getCorreo()).append("\n");
        mensaje.append("Teléfono: ").append(perfilUsuario.getTelefono()).append("\n");
        mensaje.append("Género: ").append(perfilUsuario.getGenero()).append("\n");
        mensaje.append("Residencia: ").append(perfilUsuario.getResidencia()).append("\n");

        // Determinar el rol y agregar información específica
        if (perfilUsuario instanceof AprendizModel) {
            AprendizModel aprendiz = (AprendizModel) perfilUsuario;
            mensaje.append("Ficha: ").append(aprendiz.getFicha()).append("\n");
            mensaje.append("Programa de Formación: ").append(aprendiz.getProgramaFormacion()).append("\n");
            mensaje.append("Jornada de Formación: ").append(aprendiz.getJornadaFormacion()).append("\n");
            mensaje.append("Nivel de Formación: ").append(aprendiz.getNivelFormacion()).append("\n");
            mensaje.append("Centro de Formación: ").append(aprendiz.getSede()).append("\n");
            mensaje.append("Área: ").append(aprendiz.getArea()).append("\n");
            mensaje.append("Rol: Aprendiz\n");
        } else if (perfilUsuario instanceof InstructorModel) {
            InstructorModel instructor = (InstructorModel) perfilUsuario;
            mensaje.append("Fichas asignadas: ").append(instructor.getFichas()).append("\n");
            mensaje.append("Programas de Formación: ").append(instructor.getProgramasFormacion()).append("\n");
            mensaje.append("Jornadas de Formación: ").append(instructor.getJornadasFormacion()).append("\n");
            mensaje.append("Niveles de Formación: ").append(instructor.getNivelesFormacion()).append("\n");
            mensaje.append("Centros de Formación: ").append(instructor.getSedes()).append("\n");
            mensaje.append("Áreas: ").append(instructor.getAreas()).append("\n");
            mensaje.append("Rol: Instructor\n");
        }

        // Enviar el correo
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("EmailServiceResponse@RegistroAsistencias.sena.edu.co");
        mailMessage.setTo(perfilUsuario.getCorreo());  // Enviar al correo del usuario registrado
        mailMessage.setSubject("Registro Exitoso - Bienvenido!");
        mailMessage.setText(mensaje.toString());

        mailSender.send(mailMessage);
    }
}