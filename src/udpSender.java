import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class udpSender {
    //Convert continuous hex string to byte array
    public static byte[] hexToBytes(String hex) {
        byte[] bytes = new byte[hex.length() / 2];
        for (int i = 0; i < hex.length(); i = i + 2) {
            String subStr = hex.substring(i, i + 2);
            boolean flag = false;
            int intH = Integer.parseInt(subStr, 16);
            if (intH > 127) flag = true;
            if (intH == 128) {
                intH = -128;
            } else if (flag) {
                intH = 0 - (intH & 0x7F);
            }
            byte b = (byte) intH;
            bytes[i / 2] = b;
        }
        return bytes;
    }
    //Convert byte array to continuous hex string
    public static String bytesToHex(byte[] bytes) {
        StringBuilder hex = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            byte b = bytes[i];
            boolean flag = false;
            if (b < 0) flag = true;
            int absB = Math.abs(b);
            if (flag) absB = absB | 0x80;
            //System.out.println(absB & 0xFF);
            String tmp = Integer.toHexString(absB & 0xFF);
            if (tmp.length() == 1) { //转化的十六进制不足两位，需要补0
                hex.append("0");
            }
            hex.append(tmp.toLowerCase());
        }
        return hex.toString();
    }

    public static String KNXTunnelingRequest(String state){
        String switchOn = "061004200015046630001100bce000000900010081";
        String switchOff = "061004200015046631001100bce000000900010080";

        if (state.equals("on")){
            return switchOn;
        }else {
            return switchOff;
        }

    }
    public static void main(String[] args) throws IOException {

        String switchOn = "061004200015046630001100bce000000900010081";
        String switchOff = "061004200015046631001100bce000000900010080";

        //Create a socket for sending command to KNX/IP gateway
        DatagramSocket datagramSocketSender = new DatagramSocket(64275);

        //2, 确定数据,并封装成数据包.
        //byte[] data = switchOn.getBytes();
        byte[] data = hexToBytes(switchOn);
        System.out.println("Data: " + bytesToHex(data) + "\t" + "Data length: " + data.length + "\t" + "IP address: " + InetAddress.getByName("localhost"));
        DatagramPacket dp = new DatagramPacket(data,data.length, InetAddress.getByName("localhost"),60000);

        datagramSocketSender.send(dp);
        datagramSocketSender.close();

//        byte[] buffer = new byte[1024];
//        //Create a socket for receive the acknowledgement to KNX/IP gateway
//        DatagramSocket datagramSocketReceiver = new DatagramSocket(64275);
//
//        DatagramPacket receiverPacket = new DatagramPacket(buffer,buffer.length);
//
//        datagramSocketReceiver.receive(receiverPacket);
//        String receiverData = new String(receiverPacket.getData(),0,receiverPacket.getLength());
//
//        System.out.println(receiverData);
//
//        datagramSocketReceiver.close();

//        byte[] testByte = hexToBytes("1F0F");
//        for(int i = 0; i < testByte.length; i++)
//            System.out.print(testByte[i] + "  ");
    }
}
