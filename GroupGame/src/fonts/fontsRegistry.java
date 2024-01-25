package fonts;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;

public class fontsRegistry {
    private static String fontsDir = "GroupGame/src/fonts/";
    public static Font arcadePixel;
    public static Font optimusPrecepts;
    public static Font oldEnglishPixel;

    static String fontNames [] = new String [] {"PixelifySans-VariableFont_wght.ttf","OptimusPrinceps.ttf","OldEnglishGothicPixelRegular-ow2Bo.ttf"};

    static GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment(); //get the graphics for the computer 
    public static void registerFonts(){
    //Import and registering custom font
        //ARCADE FONT
         
            try { //https://www.youtube.com/watch?v=43duJsYmhxQ 
                
                arcadePixel = Font.createFont(Font.TRUETYPE_FONT, new File(fontsDir+ fontNames[0])).deriveFont(30f);
                ge.registerFont(arcadePixel); // register the font for use
                System.out.println("Font: "+ fontNames[0] +" sucessfully Registered!");
           
            } catch (FontFormatException | IOException e) {
                System.out.println("---Font: "+ fontNames[0] +" could not be Registered---");
                e.printStackTrace();
            }

            try {
                
                optimusPrecepts = Font.createFont(Font.TRUETYPE_FONT, new File(fontsDir+ fontNames[1])).deriveFont(30f);
                ge.registerFont(optimusPrecepts); // register the font for use
                System.out.println("Font: "+ fontNames[1] +" sucessfully Registered!");
           
            } catch (FontFormatException | IOException e) {
                System.out.println("---Font: "+ fontNames[1] +" could not be Registered---");
                e.printStackTrace();
            
            }


            try {
                
                oldEnglishPixel = Font.createFont(Font.TRUETYPE_FONT, new File(fontsDir+ fontNames[2])).deriveFont(30f);
                ge.registerFont(oldEnglishPixel); // register the font for use
                System.out.println("Font: "+ fontNames[2] +" sucessfully Registered!");
           
            } catch (FontFormatException | IOException e) {
                System.out.println("---Font: "+ fontNames[2] +" could not be Registered---");
                e.printStackTrace();
            
            }





    }
}
