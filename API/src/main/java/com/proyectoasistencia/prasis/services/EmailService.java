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


        // Enviar el correo
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("EmailServiceResponse@RegistroAsistencias.sena.edu.co");
        mailMessage.setTo(perfilUsuario.getCorreo());  // Enviar al correo del usuario registrado
        mailMessage.setSubject("Registro Exitoso - Bienvenido!");
        mailMessage.setText(mensaje.toString());

        mailSender.send(mailMessage);
    }
}