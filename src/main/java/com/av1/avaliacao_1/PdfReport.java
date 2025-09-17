package com.av1.avaliacao_1;

import java.io.Closeable;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;

public class PdfReport implements Closeable {
    private final PdfDocument pdf;
    private final Document doc;
    private final Table table;
    private boolean zebra = false;
    private int rows = 0;

    public PdfReport(String outPath) throws IOException {
        PdfWriter writer = new PdfWriter(outPath);
        this.pdf = new PdfDocument(writer);
        this.doc = new Document(pdf);

        String when = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
        doc.add(new Paragraph("Relatório de Benchmarks de Ordenação")
                .setBold().setFontSize(16).setTextAlignment(TextAlignment.CENTER));
        doc.add(new Paragraph("Gerado em: " + when)
                .setFontSize(10).setTextAlignment(TextAlignment.CENTER));
        doc.add(new Paragraph("\n"));

        this.table = new Table(UnitValue.createPercentArray(new float[]{12, 28, 20, 20, 20}))
                .useAllAvailableWidth();
        addHeader("size");
        addHeader("dataset");
        addHeader("algorithm");
        addHeader("time_ms");
        addHeader("status");
    }

    public void addRow(int size, String dataset, String algorithm, String timeMs, String status) {
        com.itextpdf.kernel.colors.Color bg =
                (zebra = !zebra) ? ColorConstants.LIGHT_GRAY : ColorConstants.WHITE;
        addCell(String.valueOf(size), bg, TextAlignment.RIGHT);
        addCell(dataset, bg, TextAlignment.LEFT);
        addCell(algorithm, bg, TextAlignment.LEFT);
        addCell((timeMs == null || timeMs.isEmpty()) ? "-" : timeMs, bg, TextAlignment.RIGHT);
        addCell(status, bg, TextAlignment.CENTER);
        rows++;
    }

    private void addHeader(String text) {
        table.addHeaderCell(new Cell()
                .add(new Paragraph(text).setBold())
                .setBackgroundColor(ColorConstants.BLACK)
                .setFontColor(ColorConstants.WHITE)
                .setTextAlignment(TextAlignment.CENTER)
                .setBorder(new SolidBorder(ColorConstants.BLACK, 0.5f)));
    }

    private void addCell(String text,
                         com.itextpdf.kernel.colors.Color bg,
                         TextAlignment align) {
        table.addCell(new Cell()
                .add(new Paragraph(text == null || text.isEmpty() ? "-" : text))
                .setBackgroundColor(bg)
                .setTextAlignment(align)
                .setBorder(new SolidBorder(ColorConstants.GRAY, 0.25f)));
    }

    @Override
    public void close() {
        doc.add(table);
        doc.add(new Paragraph("\nTotal de linhas: " + rows).setFontSize(9).setItalic());
        doc.close();
        pdf.close();
    }
}
