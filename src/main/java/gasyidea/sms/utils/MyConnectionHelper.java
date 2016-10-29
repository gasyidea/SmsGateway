package gasyidea.sms.utils;

import javafx.scene.control.TextArea;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;


public class MyConnectionHelper {

    private final static String ADB = "D:\\android_studio_sdk\\platform-tools\\adb.exe ";
    private final static Integer PORT = 11111;
    private final static String FORWARD = "forward tcp:" + PORT + " tcp:" + PORT;
    private final static String HOST = "localhost";

    private static Socket socket;
    private static PrintWriter writer;

    public static TextArea textArea;


    static {
        try {
            socket = new Socket(HOST, PORT);
            writer = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void initProcess() {
        try {
            Runtime.getRuntime().exec(ADB + FORWARD);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void initConnection() {
        initProcess();
    }

    public static PrintWriter getWriter() {
        return writer;
    }

    public static void setTextArea(TextArea textArea) {
        MyConnectionHelper.textArea = textArea;
    }
}
