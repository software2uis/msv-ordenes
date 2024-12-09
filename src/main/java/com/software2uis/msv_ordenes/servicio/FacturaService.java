package com.software2uis.msv_ordenes.servicio;

import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.software2uis.msv_ordenes.modelo.Factura;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
public class FacturaService {

    private final JavaMailSender mailSender;

    @Autowired
    public FacturaService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void enviarFacturaPorCorreo(Factura factura, String emailDestino) throws MessagingException, IOException {
        if (factura == null || emailDestino == null || emailDestino.isEmpty()) {
            throw new IllegalArgumentException("Factura o email destino no puede ser nulo/vacío.");
        }

        // Generar el PDF de la factura
        ByteArrayOutputStream pdfStream = generarFacturaPdf(factura);

        // Enviar el correo con el PDF adjunto
        enviarCorreoConAdjunto(emailDestino, pdfStream, "Factura_" + factura.getNumeroFactura() + ".pdf");
    }

    private ByteArrayOutputStream generarFacturaPdf(Factura factura) throws IOException {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(stream);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document document = new Document(pdfDoc);

        // Ejemplo básico de contenido en el PDF
        document.add(new Paragraph("Factura #" + factura.getNumeroFactura()));
        document.add(new Paragraph("Fecha de Emisión: " + factura.getFechaEmision()));
        document.add(new Paragraph("Total: $" + factura.getTotal()));
        document.add(new Paragraph("Detalles del Pedido: " + factura.getDetallesPedido()));

        document.close();
        return stream;
    }

    private void enviarCorreoConAdjunto(String destinatario, ByteArrayOutputStream pdfStream, String nombreArchivo) throws MessagingException {
        MimeMessage mensaje = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mensaje, true, "UTF-8");

        helper.setTo(destinatario);
        helper.setSubject("Factura de su pedido");
        helper.setText("Adjuntamos la factura de su pedido. ¡Gracias por su compra!");

        InputStreamSource archivoAdjunto = new ByteArrayResource(pdfStream.toByteArray());
        helper.addAttachment(nombreArchivo, archivoAdjunto);

        mailSender.send(mensaje);
    }
}
