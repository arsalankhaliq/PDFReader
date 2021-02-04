import com.adobe.acrobat.Viewer;
import java.io.File;
import java.io.IOException;
import org.apache.fontbox.util.BoundingBox;
import org.apache.fontbox.util.BoundingBox;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDCIDFontType2;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDSimpleFont;
import org.apache.pdfbox.pdmodel.font.PDTrueTypeFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.font.PDType3CharProc;
import org.apache.pdfbox.pdmodel.font.PDType3Font;
import org.apache.pdfbox.pdmodel.font.PDVectorFont;
import org.apache.pdfbox.pdmodel.interactive.pagenavigation.PDThreadBead;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.TextPosition;
import org.apache.pdfbox.util.Matrix;
import org.apache.pdfbox.util.Vector;
import java.awt.geom.Rectangle2D;
import java.io.FileWriter;
import java.util.List;
import java.util.regex.Pattern;
import org.apache.pdfbox.multipdf.Splitter;
import org.apache.pdfbox.text.PDFTextStripperByArea;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author AKhaliq
 */


public class ExtractText {

    // Usage: xxx.jar filepath page x y width height
    public static void main(String[] args) throws IOException {

        if (args.length != 5) {
            System.out.println("args[0]: " + args[0] + "\n"
            + args[1] + "\n"
            + args[2] + "\n"
            + args[3] + "\n"
            + args[4] + "\n");
            return;
        }

       String filepath = args[0];
        String line = null;
        File file = new File(filepath);
        //File folder = new File(filepath);
//        File[] allFiles = folder.listFiles();
        double x = Integer.parseInt(args[1]);
        double y = Integer.parseInt(args[2]);
        double width = Integer.parseInt(args[3]);
        double height = Integer.parseInt(args[4]);

            PDDocument document = new PDDocument();
            
            try{
                document = PDDocument.load(file);
                int pages = document.getNumberOfPages();
                PDFTextStripperByArea textStripper = new PDFTextStripperByArea();

                Rectangle2D rect1 = new java.awt.geom.Rectangle2D.Double(x, y, width, height);
                textStripper.addRegion("region", rect1);

                String parent = file.getAbsoluteFile().getParent();
                String fName = file.getName();
                String outTxt = fName.substring(0, fName.toLowerCase().lastIndexOf(".pdf"));
                System.out.println("Parent: " + parent + "\\" + outTxt + ".txt");
                File fileWrite = new File(parent + "\\" + outTxt + ".txt");
                fileWrite.createNewFile();
                FileWriter writer = new FileWriter(fileWrite);

                for (int i = 0; i < pages; i++) {
//                    if(duplex){
//                        if(i % 2 == 0){
//                            PDPage docPage = document.getPage(i);
//
//                            textStripper.extractRegions(docPage);
//
//                            String textForRegion = textStripper.getTextForRegion("region");
//                            String textForRegion2 = textStripper.getTextForRegion("region2");
//                            writer.write("Page" + (i+1) + ":\r\n" + textForRegion + "**************************\r\n");
//                        }
//                    }
//                    else{
                        PDPage docPage = document.getPage(i);

                        textStripper.extractRegions(docPage);

                        String textForRegion = textStripper.getTextForRegion("region");
                        System.out.println(textForRegion + " | " + (i+1));
                        writer.write(textForRegion+"|Page: "+(i+1)+"\r\n");
//                        String textForRegion2 = textStripper.getTextForRegion("region2");
//                        String add1 = textStripper.getTextForRegion("add1");
//                        String add2 = textStripper.getTextForRegion("add2");
//                        String[] lines1 = add1.split("\\r?\\n");
//                        String[] lines2 = add2.split("\\r?\\n");
//                        System.out.println(textForRegion);
//                        System.out.println(textForRegion2);

//                        if(Pattern.matches("[0-9][0-9][0-9][0-9][0-9]", textForRegion.trim())){
//                            String[] postal1 = lines1[lines1.length-1].split("  ");
//                            String pos1 = postal1[postal1.length-1];
//                            writer.write("Page" + (i+1) + ":\r\nText for Region: " + textForRegion + "Postal Code1: " + pos1 + "\r\n");
//                            if(Pattern.matches("[0-9]+", pos1.trim()) || Pattern.matches("[0-9]+-[0-9]+", pos1.trim())){
//                                writer.write("USA\r\n\r\n");
//                            }
//                            else{
//                                writer.write("CANADA\r\n\r\n");
//                            }
//                        }
//                        else if(Pattern.matches("[0-9][0-9][0-9][0-9][0-9]", textForRegion2.trim())){
//                            String[] postal2 = lines2[lines2.length-1].split("  ");
//                            String pos2 = postal2[postal2.length-1];
//                            writer.write("Page" + (i+1) + ":\r\nText for Region2: " + textForRegion2 + "Postal Code2: " + pos2 + "\r\n");
//                            if(Pattern.matches("[0-9]+", pos2.trim()) || Pattern.matches("[0-9]+-[0-9]+", pos2.trim())){
//                                writer.write("USA\r\n\r\n");
//                            }
//                            else{
//                                writer.write("CANADA\r\n\r\n");
//                            }
//                        }
                        //System.out.println("Page" + (i+1) + ":\n" + textForRegion);
                    }
//                }
                writer.close();
            }
            finally{
                    document.close();
            }
    }
}