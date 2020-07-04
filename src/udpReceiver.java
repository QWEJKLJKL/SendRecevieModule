import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class udpReceiver {
    //Convert byte array to continuous hex string
    public static String bytesToHex(byte[] bytes) {
        StringBuilder hex = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            byte b = bytes[i];
            boolean flag = false;
            if (b < 0) flag = true;
            int absB = Math.abs(b);
            if (flag) absB = absB | 0x80;
            System.out.println(absB & 0xFF);
            String tmp = Integer.toHexString(absB & 0xFF);
            if (tmp.length() == 1) { //转化的十六进制不足两位，需要补0
                hex.append("0");
            }
            hex.append(tmp.toLowerCase());
        }
        return hex.toString();
    }

    public static void main(String[] args) throws IOException {
        byte[] buffer = new byte[1024];
        //Create a socket for receive the acknowledgement to KNX/IP gateway
        DatagramSocket datagramSocketReceiver = new DatagramSocket(60000);

        DatagramPacket receiverPacket = new DatagramPacket(buffer,buffer.length);

        datagramSocketReceiver.receive(receiverPacket);
        byte[] receiverData = receiverPacket.getData();

        String data = bytesToHex(receiverData);
        System.out.println(data);


        datagramSocketReceiver.close();
    }
}
