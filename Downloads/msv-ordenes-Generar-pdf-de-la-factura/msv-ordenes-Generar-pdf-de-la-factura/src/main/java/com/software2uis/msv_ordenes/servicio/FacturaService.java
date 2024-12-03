package com.software2uis.msv_ordenes.servicio;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.ByteArrayOutputStream;

public class FacturaService {

    public byte[] generarFactura(String cliente, String producto, double precio, int cantidad) {
        try (PDDocument documento = new PDDocument(); ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

            // Crear una nueva página
            PDPage pagina = new PDPage();
            documento.addPage(pagina);

            // Crear contenido en la página
            try (PDPageContentStream contenido = new PDPageContentStream(documento, pagina)) {
                contenido.setFont(PDType1Font.HELVETICA_BOLD, 18);
                contenido.beginText();
                contenido.setLeading(20f);
                contenido.newLineAtOffset(50, 750);

                // Encabezado
                contenido.showText("Factura");
                contenido.newLine();

                contenido.setFont(PDType1Font.HELVETICA, 12);
                contenido.newLine();
                contenido.showText("Cliente: " + cliente);
                contenido.newLine();
                contenido.showText("Producto: " + producto);
                contenido.newLine();
                contenido.showText("Cantidad: " + cantidad);
                contenido.newLine();
                contenido.showText("Precio unitario: $" + precio);
                contenido.newLine();
                contenido.showText("Total: $" + (precio * cantidad));
                contenido.endText();
            }

            // Guardar el documento en un stream de bytes
            documento.save(outputStream);
            return outputStream.toByteArray();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
