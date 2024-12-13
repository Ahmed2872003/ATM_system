/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package atmsystem.view.utils;

import java.awt.Component;
import java.awt.Dialog;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

public class Message {

    public static void show(Component parentComponent, String message, int msgType, String title) {
        
        JDialog dialog = new JOptionPane(message, msgType).createDialog(parentComponent, title);

        dialog.setModalityType(Dialog.ModalityType.DOCUMENT_MODAL);

        dialog.setVisible(true);
    }
}
