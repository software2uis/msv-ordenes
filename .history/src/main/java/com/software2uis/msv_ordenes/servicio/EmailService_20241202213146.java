package com.software2uis.msv_ordenes.servicio;

import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void enviarFacturaPorCorreo(Factura factura) {
        MimeMessage mensaje = mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(mensaje, true, StandardCharsets.UTF_8.name());
            helper.setTo(factura.getCorreoUsuario());
            helper.setSubject("Factura: " + factura.getNumeroFactura());
            helper.setText(generarCuerpoCorreo(factura), true);

            // Generar y adjuntar un archivo PDF como factura
            byte[] pdfFactura = generarFacturaPDF(factura);
            helper.addAttachment("Factura-" + factura.getNumeroFactura() + ".pdf", new ByteArrayResource(pdfFactura));

            mailSender.send(mensaje);
        } catch (MessagingException e) {
            throw new RuntimeException("Error al enviar el correo con la factura: " + e.getMessage());
        }
    }

    private String generarCuerpoCorreo(Factura factura) {
        return "<h1>Gracias por tu compra</h1>" +
               "<p>Adjunto encontrarás la factura de tu pedido.</p>" +
               "<p><b>Número de Factura:</b> " + factura.getNumeroFactura() + "</p>" +
               "<p><b>Total:</b> $" + factura.getTotal() + "</p>";
    }

    private byte[] generarFacturaPDF(Factura factura) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        try (PdfWriter writer = new PdfWriter(byteArrayOutputStream);
            Document document = new Document(writer)) {

            document.add(new Paragraph("Factura: " + factura.getNumeroFactura()));
            document.add(new Paragraph("Fecha: " + factura.getFechaEmision()));
            document.add(new Paragraph("Detalles del Pedido: " + factura.getDetallesPedido()));
            document.add(new Paragraph("Subtotal: $" + factura.getSubtotal()));
            document.add(new Paragraph("Impuestos: $" + factura.getImpuestos()));
            document.add(new Paragraph("Descuentos: $" + factura.getDescuentos()));
            document.add(new Paragraph("Total: $" + factura.getTotal()));
        } catch (Exception e) {
            throw new RuntimeException("Error al generar el PDF: " + e.getMessage());
        }

        return byteArrayOutputStream.toByteArray();
    }
}
