import java.util.ArrayList;
import javax.swing.SwingUtilities;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.BorderFactory;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JDialog;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseAdapter;
import java.awt.geom.Ellipse2D;
import java.awt.Shape;
import java.awt.geom.GeneralPath;
import java.awt.event.*;
import java.awt.Font;
import java.awt.BasicStroke;
import javax.swing.JOptionPane;

public class Map {

    private boolean[][] adj = new boolean[24][24];
    private DisplayMapGUI p;
    private JFrame f;

    public Map() {
        p = new DisplayMapGUI();
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI(); 
            }
        });
        for (int i = 0; i < 24; i++)
            for (int j = 0; j < 24; j++)
                adj[i][j] = false;
        adj[0][1] = true; adj[0][18] = true; adj[1][0] = true; adj[1][2] = true;adj[1][19] = true;adj[2][1] = true;adj[2][3] = true;adj[2][19] = true;adj[3][2] = true;adj[3][4] = true;adj[3][11] = true;adj[3][20] = true;adj[4][3] = true;adj[4][5] = true;adj[5][4] = true;adj[5][6] = true;adj[5][11] = true;adj[6][5] = true;adj[6][7] = true;adj[6][10] = true;adj[7][6] = true;adj[7][8] = true;adj[7][9] = true;adj[8][7] = true;adj[8][9] = true;adj[8][12] = true;adj[9][7] = true;adj[9][8] = true;adj[9][10] = true;adj[9][13] = true;adj[9][22] = true;adj[9][23] = true;adj[10][6] = true;adj[10][9] = true;adj[10][11] = true;adj[10][21] = true;adj[10][22] = true;adj[11][3] = true;adj[11][5] = true;adj[11][10] = true;adj[11][20] = true;adj[12][8] = true;adj[12][13] = true;adj[12][14] = true;adj[13][9] = true;adj[13][12] = true;adj[13][14] = true;adj[13][23] = true;adj[14][12] = true;adj[14][13] = true;adj[14][15] = true;adj[14][17] = true;adj[15][14] = true;adj[15][16] = true;adj[15][17] = true;adj[16][15] = true;adj[17][14] = true;adj[17][15] = true;adj[17][23] = true;adj[18][0] = true;adj[18][19] = true;adj[19][1] = true;adj[19][2] = true;adj[19][18] = true;adj[19][20] = true;adj[20][3] = true;adj[20][11] = true;adj[20][19] = true;adj[20][21] = true;adj[21][10] = true;adj[21][20] = true;adj[21][22] = true;adj[22][9] = true;adj[22][10] = true;adj[22][21] = true;adj[22][23] = true;adj[23][9] = true;adj[23][13] = true;adj[23][17] = true;adj[23][22] = true;
    }

    private void createAndShowGUI() {
        f = new JFrame("Taiwan Risk!");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(p);
        f.pack();
        f.setVisible(true);
    }

    public boolean adjacent(int country1, int country2) {
        return adj[country1][country2];
    }

    public boolean invade(int country1, int country2, int army) {
        if (Combat.fight(p.countries[country1], p.countries[country2], army)) {
            p.show_phase(4, " wins.", "");
            int temp = p.countries[country1].get_owner();
            p.countries[country2].change_owner(temp);
            return true;
        }
        p.show_phase(4, " loses.", "");
        return false;
    }

    public Country get_country(int i) {
        return p.getC(i);
    }

    public int openMessageBox(int index) {
        return p.openMBox(index);
    }

    public void showPhase(int index, String temp, String temp2) {
        p.show_phase(index, temp, temp2);
    }

    public int getLastInput() {
        while (p.inputValue == -5) { System.out.printf("");}
        int temp = p.inputValue;
        p.inputValue = -5;
        return temp;
    }

    public boolean continentCheck(int cid, int pid) {
        if (cid == 1) {
            for (int a = 0; a < 4; a++) {
                if (p.countries[a].get_owner() != pid)
                    return false;
            }
        }
        else if (cid == 2) {
            for (int a1 = 4; a1 < 12; a1++) {
                if (p.countries[a1].get_owner() != pid)
                    return false;
            }
        }
        else if (cid == 3) {
            for (int a2 = 12; a2 < 18; a2++) {
                if (p.countries[a2].get_owner() != pid)
                    return false;
            }
        }
        else if (cid == 4) {
            for (int a3 = 18; a3 < 24; a3++) {
                if (p.countries[a3].get_owner() != pid)
                    return false;
            }
        }
        return true;
    }

}

class DisplayMapGUI extends JPanel {

    public  Country[] countries = new Country[24];
    private GeneralPath[] gp = new GeneralPath[25];
    private final int[] continent = {1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 3, 3, 3, 3, 3, 3, 4, 4, 4, 4, 4, 4};
    private int[] centerX   = {290, 241, 218, 211, 158, 147, 122, 107, 84, 181, 204, 208, 104, 170, 157, 156, 171, 211, 315, 296, 283, 276, 258, 236};
    private int[] centerY   = {53, 76, 113, 158, 170, 209, 255, 300, 347, 307, 251, 206, 404, 376, 434, 476, 531, 440, 94, 129, 186, 241, 289, 364};
    private String displayString  = "WELCOME TO";
    private String displayString2 = "TAIWAN RISK!";
    private String displayString3 = "PRESS CIRCLE";
    private String displayString4 = "<-- TO START";
    private Shape circle;
    public int inputValue = -5;

    public DisplayMapGUI() {

        setBorder(BorderFactory.createLineBorder(Color.black));
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                moveSquare(e.getX(),e.getY());
            }
        });

        for (int i = 0; i < 24; i++)
            countries[i] = new Country();

        gp[1] = new GeneralPath();
        double[] x1p = {344, 286, 240, 239, 242, 245, 248, 250, 253, 255, 255, 258, 261, 264, 269, 271, 273, 276, 278, 282, 285, 287, 291, 295, 298, 299, 302, 304, 305, 307, 309, 310, 310, 310, 311, 319, 332, 337, 341, 343, 344, 345, 346, 346};
        double[] y1p = {60, 88, 52, 50, 49, 49, 49, 48, 46, 46, 46, 45, 44, 42, 33, 30, 28, 27, 25, 23, 22, 21, 21, 23, 23, 25, 29, 32, 34, 36, 36, 36, 38, 41, 41, 44, 47, 47, 47, 48, 48, 55, 60, 63};
        gp[1].moveTo(x1p[0], y1p[0]);
        for (int i = 0; i < 42; i++)
            gp[1].lineTo(x1p[i], y1p[i]);

        gp[2] = new GeneralPath();
        double[] x15p = {268, 287, 239, 240, 238, 237, 234, 231, 227, 224, 221, 218, 216, 213, 211, 209, 209, 209, 208, 207, 206, 205, 209, 213, 270};
        double[] y15p = {113, 87, 50, 53, 53, 54, 55, 56, 58, 59, 61, 62, 64, 67, 70, 72, 74, 75, 77, 79, 81, 83, 85, 87, 116};
        gp[2].moveTo(x15p[0], y15p[0]);
        for (int i = 1; i < 25; i++) {
            gp[2].lineTo(x15p[i], y15p[i]);
        }

        gp[3] = new GeneralPath();
        double[] x16p = {251, 268, 205, 205, 204, 202, 201, 200, 199, 196, 195, 193, 194, 194, 195, 195, 195, 195, 193, 192, 190, 189, 187, 187, 185, 184, 190, 200, 209, 220, 237, 248, 253, 253, 253, 253};
        double[] y16p = {146, 113, 81, 82, 84, 86, 89, 91, 93, 93, 93, 92, 94, 97, 99, 102, 106, 108, 109, 111, 113, 115, 117, 119, 121, 123, 125, 129, 132, 136, 142, 146, 148, 148, 148, 148};
        gp[3].moveTo(x16p[0], y16p[0]);
        for (int i = 1; i < 36; i++) {
            gp[3].lineTo(x16p[i], y16p[i]);
        }

        gp[4] = new GeneralPath();
        double[] x26p = {186, 186, 186, 186, 186, 186, 186, 186, 186, 203, 218, 233, 247, 252, 253, 253, 253, 253, 253, 253, 249, 239, 227, 217, 208, 201, 197, 192, 188, 186};
        double[] y26p = {125, 130, 138, 147, 156, 166, 175, 181, 184, 183, 183, 181, 180, 180, 180, 171, 164, 158, 154, 149, 148, 144, 140, 137, 134, 132, 130, 128, 126, 125};
        gp[4].moveTo(x26p[0], y26p[0]);
        for (int i = 0; i < 30; i++)
            gp[4].lineTo(x26p[i], y26p[i]);

        gp[5] = new GeneralPath();
        double[] x27p = {184, 184, 184, 184, 183, 183, 182, 182, 174, 166, 155, 146, 140, 135, 133, 137, 143, 150, 156, 161, 164, 165, 166, 168, 171, 172, 174, 176, 177, 181, 183, 184};
        double[] y27p = {124, 135, 143, 155, 165, 178, 185, 186, 186, 187, 188, 189, 189, 189, 190, 185, 175, 165, 154, 147, 140, 137, 135, 133, 133, 130, 128, 126, 125, 125, 124, 124};
        gp[5].moveTo(x27p[0], y27p[0]);
        for (int i = 0; i < 32; i++)
            gp[5].lineTo(x27p[i], y27p[i]);

        gp[6] = new GeneralPath();
        double[] x20p = {135, 180, 170, 172, 169, 165, 159, 152, 146, 137, 130, 125, 117, 112, 108, 107, 108, 110, 111, 113, 114, 115, 113, 114, 116, 117, 118, 118, 119, 120, 122, 123, 125, 127, 129, 131, 132, 134, 133, 131, 130, 129, 128, 128, 130, 132, 134, 134};
        double[] y20p = {187, 183, 231, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 232, 232, 230, 228, 226, 224, 223, 221, 219, 216, 214, 213, 211, 209, 208, 206, 206, 206, 205, 203, 202, 201, 199, 199, 198, 198, 197, 195, 193, 191, 190, 188};
        gp[6].moveTo(x20p[0], y20p[0]);
        for (int i = 1; i < 48; i++) {
            gp[6].lineTo(x20p[i], y20p[i]);
        }

        gp[7] = new GeneralPath();
        double[] x28p = {140, 86, 86, 85, 85, 85, 85, 91, 93, 95, 98, 101, 103, 104, 105, 105, 106, 106, 171, 169, 164, 145, 140, 140};
        double[] y28p = {278, 276, 274, 273, 269, 265, 263, 262, 261, 259, 255, 252, 250, 247, 245, 239, 235, 234, 233, 237, 244, 267, 272, 278};
        gp[7].moveTo(x28p[0], y28p[0]);
        for (int i = 0; i < 24; i++)
            gp[7].lineTo(x28p[i], y28p[i]);

        gp[8] = new GeneralPath();
        double[] x36p = {135, 135, 136, 136, 137, 138, 138, 139, 136, 133, 130, 127, 122, 116, 111, 104, 100, 94, 89, 86, 85, 84, 83, 82, 82, 82, 82, 82, 82, 80, 77, 77, 77, 79, 79, 81, 83, 82, 82, 81, 88, 94, 102, 111, 120, 128, 132, 138};
        double[] y36p = {325, 319, 308, 301, 296, 288, 283, 281, 281, 281, 281, 281, 281, 280, 280, 280, 280, 279, 279, 279, 279, 280, 284, 287, 291, 295, 300, 304, 306, 308, 308, 309, 315, 315, 318, 317, 318, 323, 326, 325, 325, 325, 325, 325, 325, 325, 325, 326};
        gp[8].moveTo(x36p[0], y36p[0]);
        for (int i = 0; i < 48; i++)
            gp[8].lineTo(x36p[i], y36p[i]);

        gp[9] = new GeneralPath();
        double[] x35p = {84, 84, 91, 115, 129, 134, 137, 135, 135, 135, 131, 126, 122, 116, 110, 104, 97, 90, 85, 78, 72, 68, 65, 63, 63, 62, 62, 63, 66, 68, 68, 69, 69, 74, 74};
        double[] y35p = {324, 324, 324, 324, 324, 324, 324, 329, 335, 340, 340, 344, 346, 351, 354, 359, 365, 370, 373, 380, 385, 386, 383, 381, 380, 378, 374, 372, 371, 368, 362, 355, 349, 336, 329};
        gp[9].moveTo(x35p[0], y35p[0]);
        for (int i = 0; i < 35; i++)
            gp[9].lineTo(x35p[i], y35p[i]);

        gp[10] = new GeneralPath();
        double[] x11p = {137, 159, 176, 191, 201, 211, 216, 218, 222, 224, 228, 228, 218, 206, 189, 174, 161, 152, 144, 140, 139, 139, 138, 137, 137};
        double[] y11p = {342, 341, 341, 341, 341, 340, 340, 327, 306, 292, 277, 273, 273, 273, 274, 273, 273, 274, 274, 274, 288, 300, 314, 325, 342};
        gp[10].moveTo(x11p[0], y11p[0]);
        for (int i = 1; i < 25; i++) {
            gp[10].lineTo(x11p[i], y11p[i]);
        }

        gp[11] = new GeneralPath();
        double[] x12p = {258, 252, 245, 233, 221, 212, 202, 192, 182, 177, 174, 171, 167, 163, 157, 150, 144, 141, 147, 162, 174, 188, 201, 214, 227, 230, 236, 240, 241, 246, 251, 255, 258};
        double[] y12p = {214, 216, 217, 221, 224, 226, 229, 231, 234, 234, 235, 235, 240, 246, 252, 262, 268, 274, 275, 274, 274, 274, 274, 274, 274, 274, 262, 251, 247, 242, 233, 227, 214};
        gp[11].moveTo(x12p[0], y12p[0]);
        for (int i = 1; i < 33; i++) {
            gp[11].lineTo(x12p[i], y12p[i]);
        }

        gp[12] = new GeneralPath();
        double[] x19p = {173, 256, 256, 182, 181, 180, 179, 177, 176, 175, 173, 173};
        double[] y19p = {233, 211, 180, 185, 190, 196, 203, 210, 217, 223, 229, 233};
        gp[12].moveTo(x19p[0], y19p[0]);
        for (int i = 1; i < 12; i++) {
            gp[12].lineTo(x19p[i], y19p[i]);
        }

        gp[13] = new GeneralPath();
        double[] x31p = {135, 136, 136, 136, 136, 137, 138, 139, 139, 140, 136, 133, 131, 126, 122, 116, 112, 108, 104, 102, 99, 97, 97, 96, 95, 96, 97, 95, 91, 89, 88, 87, 85, 84, 84, 81, 79, 77, 76, 75, 73, 71, 78, 84, 91, 96, 102, 108, 115, 120, 126, 130, 133, 137};
        double[] y31p = {344, 351, 362, 372, 384, 392, 402, 407, 411, 413, 418, 422, 423, 430, 434, 441, 446, 450, 455, 457, 455, 451, 450, 447, 445, 443, 443, 437, 421, 417, 413, 411, 406, 404, 401, 398, 395, 393, 391, 390, 391, 391, 384, 379, 375, 369, 366, 360, 356, 352, 347, 344, 343, 343};
        gp[13].moveTo(x31p[0], y31p[0]);
        for (int i = 0; i < 54; i++)
            gp[13].lineTo(x31p[i], y31p[i]);

        gp[14] = new GeneralPath();
        double[] x32p = {137, 147, 160, 175, 186, 194, 201, 211, 217, 214, 212, 209, 206, 204, 202, 201, 201, 192, 180, 166, 158, 147, 141, 139, 139, 138, 137, 137, 137, 137, 137, 137};
        double[] y32p = {344, 343, 343, 343, 343, 343, 343, 343, 343, 353, 364, 376, 384, 397, 404, 409, 410, 410, 410, 410, 410, 410, 410, 410, 404, 397, 386, 378, 369, 361, 345, 343};
        gp[14].moveTo(x32p[0], y32p[0]);
        for (int i = 0; i < 32; i++)
            gp[14].lineTo(x32p[i], y32p[i]);

        gp[15] = new GeneralPath();
        double[] x29p = {103, 106, 111, 118, 127, 140, 151, 157, 162, 169, 174, 180, 185, 191, 196, 197, 197, 198, 199, 199, 200, 201, 201, 201, 197, 193, 191, 186, 181, 176, 173, 169, 166, 164, 160, 156, 153, 150, 146, 143, 141, 139, 137, 135, 133, 129, 126, 121, 118, 114, 112, 110, 107, 102}; 
        double[] y29p = {457, 457, 457, 457, 457, 457, 456, 456, 456, 456, 456, 456, 455, 455, 455, 455, 450, 442, 432, 426, 420, 416, 412, 410, 410, 410, 410, 410, 410, 410, 410, 410, 410, 410, 410, 410, 410, 410, 410, 410, 410, 413, 416, 418, 421, 424, 429, 434, 438, 442, 444, 448, 450, 457};
        gp[15].moveTo(x29p[0], y29p[0]);
        for (int i = 0; i < 54; i++)
            gp[15].lineTo(x29p[i], y29p[i]);

        gp[16] = new GeneralPath();
        double[] x30p = {103, 109, 113, 119, 126, 133, 139, 145, 150, 158, 168, 175, 181, 187, 194, 197, 197, 196, 195, 194, 194, 194, 193, 185, 178, 173, 166, 160, 155, 152, 147, 142, 139, 135, 130, 125, 122, 117, 113, 109, 107, 104, 102};
        double[] y30p = {460, 460, 460, 460, 460, 460, 460, 460, 460, 460, 460, 460, 460, 460, 459, 459, 459, 468, 479, 489, 494, 497, 499, 499, 499, 499, 499, 499, 499, 499, 499, 495, 493, 489, 485, 481, 478, 475, 472, 468, 464, 460, 460};
        gp[16].moveTo(x30p[0], y30p[0]);
        for (int i = 0; i < 43; i++)
            gp[16].lineTo(x30p[i], y30p[i]);

        gp[17] = new GeneralPath();
        double[] x24p = {153, 154, 159, 162, 162, 165, 169, 172, 175, 180, 184, 188, 187, 185, 188, 191, 193, 193, 194, 194, 153};
        double[] y24p = {498, 501, 520, 536, 547, 561, 562, 558, 558, 562, 566, 567, 559, 554, 551, 547, 543, 531, 501, 498, 498};
        gp[17].moveTo(x24p[0], y24p[0]);
        for (int i = 0; i < 21; i++)
            gp[17].lineTo(x24p[i], y24p[i]);

        gp[18] = new GeneralPath();
        double[] x8p = {194, 195, 196, 197, 198, 199, 200, 201, 202, 202, 210, 222, 232, 239, 247, 249, 247, 243, 240, 236, 237, 237, 236, 235, 233, 229, 226, 221, 217, 215, 214, 211, 210, 207, 204, 201, 200, 197, 196, 194, 194};
        double[] y8p = {499, 487, 477, 464, 457, 447, 438, 427, 415, 409, 409, 408, 407, 405, 403, 405, 407, 413, 417, 419, 424, 426, 428, 431, 433, 436, 438, 441, 444, 445, 448, 453, 459, 464, 468, 476, 482, 488, 494, 499, 499};
        gp[18].moveTo(x8p[0], y8p[0]);
        for (int i = 1; i < 41; i++) {
            gp[18].lineTo(x8p[i], y8p[i]);
        }

        gp[19] = new GeneralPath();
        double[] x25p = {331, 292, 295, 327, 346, 348, 354, 356, 352, 346, 341, 337, 334, 333, 332, 331};
        double[] y25p = {115, 92, 87, 72, 63, 67, 68, 69, 72, 77, 82, 86, 91, 98, 106, 115};
        gp[19].moveTo(x25p[0], y25p[0]);
        for (int i = 0; i < 16; i++)
            gp[19].lineTo(x25p[i], y25p[i]);

        gp[20] = new GeneralPath();
        double[] x14p = {291, 331, 334, 336, 337, 337, 335, 331, 334, 334, 329, 327, 326, 310, 299, 282, 266, 256, 253, 254, 260, 264, 268, 272, 281, 286, 289};
        double[] y14p = {92, 115, 124, 132, 140, 145, 147, 149, 153, 155, 161, 166, 168, 161, 161, 155, 152, 152, 151, 141, 134, 124, 116, 111, 100, 93, 90};
        gp[20].moveTo(x14p[0], y14p[0]);
        for (int i = 1; i < 27; i++) {
            gp[20].lineTo(x14p[i], y14p[i]);
        }

        gp[21] = new GeneralPath();
        double[] x34p = {303, 298, 288, 272, 266, 257, 257, 257, 257, 257, 257, 255, 255, 255, 254, 254, 258, 263, 266, 270, 276, 282, 290, 297, 300, 307, 311, 313, 319, 322, 324, 326, 325, 323, 321, 319, 316, 315, 312, 309, 305, 305, 305, 303, 301, 301, 301};
        double[] y34p = {219, 219, 219, 219, 219, 218, 215, 197, 191, 184, 178, 174, 169, 163, 157, 155, 154, 155, 154, 155, 156, 158, 160, 162, 163, 164, 164, 165, 166, 168, 170, 170, 173, 178, 181, 185, 186, 190, 194, 199, 203, 208, 211, 214, 218, 220, 221};
        gp[21].moveTo(x34p[0], y34p[0]);
        for (int i = 0; i < 47; i++)
            gp[21].lineTo(x34p[i], y34p[i]);

        gp[22] = new GeneralPath();
        double[] x33p = {257, 258, 257, 257, 257, 256, 253, 251, 249, 247, 245, 243, 241, 245, 251, 257, 263, 272, 281, 287, 291, 292, 294, 295, 297, 298, 300, 300, 301, 302, 302, 299, 296, 292, 289, 284, 279, 276, 273, 270, 268, 264, 261, 259, 257};
        double[] y33p = {216, 218, 221, 226, 228, 232, 236, 238, 241, 243, 245, 248, 249, 250, 252, 254, 256, 258, 261, 263, 264, 260, 254, 248, 242, 236, 231, 228, 226, 223, 222, 221, 220, 220, 219, 218, 217, 217, 217, 217, 216, 216, 215, 215, 215};
        gp[22].moveTo(x33p[0], y33p[0]);
        for (int i = 0; i < 45; i++)
            gp[22].lineTo(x33p[i], y33p[i]);

        gp[23] = new GeneralPath();
        double[] x10p = {224, 225, 227, 229, 231, 233, 237, 241, 255, 270, 281, 294, 292, 287, 282, 280, 279, 254, 241, 224};
        double[] y10p = {313, 305, 291, 282, 271, 266, 257, 247, 252, 256, 260, 264, 276, 298, 319, 332, 336, 326, 321, 313};
        gp[23].moveTo(x10p[0], y10p[0]);
        for (int i = 1; i < 20; i++) {
            gp[23].lineTo(x10p[i], y10p[i]);
        }

        gp[24] = new GeneralPath();
        double[] x9p = {202, 207, 214, 220, 225, 229, 234, 238, 243, 246, 249, 251, 255, 258, 262, 266, 270, 269, 270, 271, 274, 276, 278, 273, 256, 242, 225, 223, 221, 219, 218, 216, 214, 211, 208, 205, 202};
        double[] y9p = {412, 412, 412, 411, 411, 410, 409, 409, 407, 407, 405, 401, 397, 392, 383, 377, 374, 367, 360, 355, 347, 342, 333, 331, 325, 319, 312, 311, 316, 329, 340, 349, 361, 374, 388, 399, 412};
        gp[24].moveTo(x9p[0], y9p[0]);
        for (int i = 1; i < 37; i++) {
            gp[24].lineTo(x9p[i], y9p[i]);
        }

        circle = new Ellipse2D.Float(382, 500, 60, 60);
    }

    private void moveSquare(int x, int y) {
        if (circle.contains(x, y)) {
            inputValue = -1;
            displayString2 = " ";
            displayString3 = " ";
            displayString4 = " ";
        }
        for (int i = 1; i < 25; i++) {
            if (gp[i].contains(x, y)) {
                inputValue = i-1;
                return;
            }
        }
    }

    public Dimension getPreferredSize() {
        return new Dimension(675,675);
    }

    public Country getC(int index) {
        return countries[index];
    }

    public int openMBox(int index) {
        String input;
        input = JOptionPane.showInputDialog("Number of Armies");
        int temp = Integer.parseInt(input);
        repaint();
        return temp;
    }

    public void show_phase(int index, String temp, String temp2) {
        if (index == 0) {
            displayString   = "Reinforcement Phase:";
            displayString2  = "Player ";
            displayString2  += temp;
            displayString3  = "Available Count: ";
            displayString3  += temp2;
            displayString4 = "";
        }
        else if (index == 1) {
            displayString = "Attack Phase:";
            displayString2  = "Player ";
            displayString2  += temp;
            displayString3 = "";
            displayString4 = "<-- PressTo Skip";
        }
        else if (index == 2) {
            displayString = "Fortify Phase.";
            displayString2  = "Player ";
            displayString2  += temp;
            displayString3 = "";
            displayString4 = "<-- PressTo Skip";
        }
        else if (index == 3) {
            displayString = "Player ";
            displayString += temp;
            displayString2  = "Distribute 15 Armies";
            displayString3 = "";
            displayString4 = "";
        }
        else if (index == 4) {
            displayString2 += temp;
        }
        else if (index == 5) {
            displayString = "Player ";
            displayString += temp;
            displayString2 = "WINS!!!";
        }
        repaint();
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);       
        g.drawRect (358, 388, 150, 100); 
        Graphics2D g2D = (Graphics2D) g;
        g2D.setStroke(new BasicStroke(8));
        g2D.fill(circle);
        Color green  = Color.GREEN;
        Color yellow = Color.YELLOW;
        Color red    = Color.RED;
        Font f = new Font("SansSerif", Font.BOLD, 17);
        g2D.drawString(displayString,  368, 408);
        g2D.drawString(displayString2, 368, 438);
        g2D.drawString(displayString3, 368, 468);
        g2D.drawString(displayString4, 445, 538);
        g2D.setFont(f);
        for (int i = 0; i < 24; i++) {
            g2D.setPaint(Color.BLACK);
            g2D.draw(gp[i+1]);

            if (continent[i] == 1) {
                g2D.setPaint(Color.GRAY);
                g2D.fill(gp[i+1]);
            }
            else if (continent[i] == 2) {
                g2D.setPaint(yellow.darker());
                g2D.fill(gp[i+1]);
            }
            else if (continent[i] == 3) {
                g2D.setPaint(Color.PINK);
                g2D.fill(gp[i+1]);
            }
            else if (continent[i] == 4) {
                g2D.setPaint(green.darker());
                g2D.fill(gp[i+1]);
            }

            //STRING IS ARMY NUMBER, COLOR REPRESENTS DIFFERENT PLAYER
            String temp = Integer.toString(countries[i].get_soldier());
            if (countries[i].get_owner() == 0) {
                g2D.setPaint(Color.BLUE);
                g2D.drawString(temp, centerX[i], centerY[i]);
            }
            else if (countries[i].get_owner() == 1) {
                g2D.setPaint(Color.WHITE);
                g2D.drawString(temp, centerX[i], centerY[i]);
            }
            else if (countries[i].get_owner() == 2) {
                g2D.setPaint(Color.BLACK);
                g2D.drawString(temp, centerX[i], centerY[i]);
            }
            else if (countries[i].get_owner() == 3) {
                g2D.setPaint(red.darker());
                g2D.drawString(temp, centerX[i], centerY[i]);
            }
        }
    }  
}
