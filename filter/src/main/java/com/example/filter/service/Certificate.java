package com.example.filter.service;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.color.DeviceRgb;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.border.Border;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.renderer.DrawContext;
import com.itextpdf.layout.renderer.TableRenderer;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class Certificate {
    public static final String DEST = "D:\\test.pdf";
    public static final String TEMPLATE_LIBRARY = "D:\\Projects\\IFL";

    public static void main(String[] args) throws IOException {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new Certificate().createPdf(DEST);
    }

    public void createPdf(String dest) throws IOException {
        PdfWriter writer = new PdfWriter(dest);
        PageSize pageSize = new PageSize(PageSize.LETTER).rotate();
        pageSize.setWidth(740);
        pageSize.setHeight(480);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf, pageSize);

        InputStream inputStream = Files.newInputStream(Paths.get(TEMPLATE_LIBRARY + "\\idfcLogo.png"));
        BufferedImage bufferImg = ImageIO.read(inputStream);
        ByteArrayOutputStream outLogo = new ByteArrayOutputStream();
        ImageIO.write(bufferImg, "png", outLogo);
        byte[] logoBytes = outLogo.toByteArray();
        ImageData data = ImageDataFactory.create(logoBytes);
        Image logoIDFC = new Image(data);


        inputStream = Files.newInputStream(Paths.get(TEMPLATE_LIBRARY + "\\image-removebg-preview.png"));
        bufferImg = ImageIO.read(inputStream);
        outLogo = new ByteArrayOutputStream();
        ImageIO.write(bufferImg, "png", outLogo);
        logoBytes = outLogo.toByteArray();
        data = ImageDataFactory.create(logoBytes);
        Image logoAwards = new Image(data);
        logoAwards.setHeight(100);
        logoAwards.setWidth(120);

        Table certificateWrapper = new Table(new float[]{700F, 200F});
        Table leftWrapper = new Table(1);
        leftWrapper.setWidthPercent(100);
        leftWrapper.setBackgroundColor(null);

        String name = "Prashant Nair";
        String courseName = "Money Basics";
        String date = "August 9, 2023";


        Table logo = new Table(1);
        logo.addCell(new Cell().add(logoIDFC).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));
        leftWrapper.addCell(new Cell().add(logo).setBorder(Border.NO_BORDER));

        Paragraph p = new Paragraph("IDFC First Academy proudly present a\n");
        p.add(new Text("Certificate of completion to").setBold().setFontSize(14));
        leftWrapper.addCell(new Cell().add(p).setPaddingTop(15).setBorder(Border.NO_BORDER));

        p = new Paragraph(name).setBold();
        p.setFontSize(16);
        p.setFontColor(new DeviceRgb(120, 22, 35));
        leftWrapper.addCell(new Cell().add(p).setPaddingTop(10).setPaddingBottom(10).setBorder(Border.NO_BORDER));

        p = new Paragraph("For successfully completing the ");
        p.add(new Text(courseName).setBold());
        p.add(" course\n on ");
        p.add(new Text(date).setBold());
        leftWrapper.addCell(new Cell().add(p).setBorder(Border.NO_BORDER));

        p = new Paragraph(new Text("Top skills covered").setBold());
        p.add("\nMoney Basics, Financial Concepts, Budgeting, Savings, Borrowings and Investments.");
        leftWrapper.addCell(new Cell().add(p).setPaddingTop(15).setBorder(Border.NO_BORDER));

        Table logoBottom = new Table(new float[]{150F, 200F});
        logoBottom.addCell(new Cell().add(logoAwards).setTextAlignment(TextAlignment.LEFT).setBorder(Border.NO_BORDER));
        p = new Paragraph("\n\n\nPowered By\n");
        p.add(new Text("IDFC First Bank").setBold());
        logoBottom.addCell(new Cell().add(p).setTextAlignment(TextAlignment.RIGHT).setBorder(Border.NO_BORDER));
        leftWrapper.addCell(new Cell().add(logoBottom).setPaddingTop(15).setBorder(Border.NO_BORDER));

        inputStream = Files.newInputStream(Paths.get(TEMPLATE_LIBRARY + "\\rightWrapper.png"));
        bufferImg = ImageIO.read(inputStream);
        outLogo = new ByteArrayOutputStream();
        ImageIO.write(bufferImg, "png", outLogo);
        logoBytes = outLogo.toByteArray();
        data = ImageDataFactory.create(logoBytes);
        Image rightWrapperImg = new Image(data);
        rightWrapperImg.setHeight(300);
        rightWrapperImg.setWidth(200);

        Table rightWrapper = new Table(1);
        rightWrapper.setWidthPercent(100);
        rightWrapper.addCell(new Cell().add(rightWrapperImg).setBorder(Border.NO_BORDER));
        String imageUrl = TEMPLATE_LIBRARY + "\\bg.png";
        leftWrapper.setNextRenderer(new BackgroundImageLeftCellRenderer(leftWrapper, imageUrl));
        certificateWrapper.addCell(new Cell().add(leftWrapper).setBorder(Border.NO_BORDER));
        certificateWrapper.addCell(new Cell().add(rightWrapper).setBorder(Border.NO_BORDER));
        document.add(certificateWrapper);
        document.close();
    }

    private static class BackgroundImageLeftCellRenderer extends TableRenderer {
        private final String imageUrl;

        public BackgroundImageLeftCellRenderer(Table modelElement, String imageUrl) {
            super(modelElement);
            this.imageUrl = imageUrl;
        }

        @Override
        public void drawBackground(DrawContext drawContext) {
            Rectangle rect = getOccupiedAreaBBox();
            PdfCanvas canvas = drawContext.getCanvas();
            try {
                canvas.saveState();
                canvas.addImage(ImageDataFactory.create(imageUrl), rect.getLeft(), rect.getBottom(), rect.getWidth(), true);
                canvas.restoreState();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
