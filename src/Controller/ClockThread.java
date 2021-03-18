/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JLabel;

/**
 *
 * @author LE NGUYEN TOAN
 */
public class ClockThread extends Thread {

    private JLabel lbl;

    public ClockThread(JLabel lbl) {
        this.lbl = lbl;
    }

    public void run() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy-hh:mm:ss aa");
        while (true) {
            Date date = new Date();
            lbl.setText(sdf.format(date));
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

}
