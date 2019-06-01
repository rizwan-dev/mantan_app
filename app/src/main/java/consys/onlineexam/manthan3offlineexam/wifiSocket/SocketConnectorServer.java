package consys.onlineexam.manthan3offlineexam.wifiSocket;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Enumeration;

import org.apache.commons.io.IOUtils;

public class SocketConnectorServer extends Activity {
    TextView info;
    TextView infoip;
    String message = "";
    TextView msg;
    ServerSocket serverSocket;

    private class SocketServerThread extends Thread {
        static final int SocketServerPORT = 4321;
        int count;

        /* renamed from: consys.onlineexam.manthan3offlineexam.wifiSocket.SocketConnectorServer$SocketServerThread$1 */
        class C05541 implements Runnable {
            C05541() {
            }

            public void run() {
                SocketConnectorServer.this.info.setText("I'm waiting here: " + SocketConnectorServer.this.serverSocket.getLocalPort());
            }
        }

        /* renamed from: consys.onlineexam.manthan3offlineexam.wifiSocket.SocketConnectorServer$SocketServerThread$2 */
        class C05552 implements Runnable {
            C05552() {
            }

            public void run() {
                SocketConnectorServer.this.msg.setText(SocketConnectorServer.this.message);
            }
        }

        private SocketServerThread() {
            this.count = 0;
        }

        public void run() {
            IOException e;
            Throwable th;
            Socket socket = null;
            DataInputStream dataInputStream = null;
            DataOutputStream dataOutputStream = null;
            try {
                SocketConnectorServer.this.serverSocket = new ServerSocket();
                SocketConnectorServer.this.serverSocket.setReuseAddress(true);
                SocketConnectorServer.this.serverSocket.bind(new InetSocketAddress(SocketServerPORT));
                SocketConnectorServer.this.runOnUiThread(new C05541());
                DataOutputStream dataOutputStream2 = null;
                DataInputStream dataInputStream2 = null;
                while (true) {
                    try {
                        socket = SocketConnectorServer.this.serverSocket.accept();
                        dataInputStream = new DataInputStream(socket.getInputStream());
                        try {
                            dataOutputStream = new DataOutputStream(socket.getOutputStream());
                            String str = "";
                            str = dataInputStream.readUTF();
                            this.count++;
                            StringBuilder stringBuilder = new StringBuilder();
                            SocketConnectorServer socketConnectorServer = SocketConnectorServer.this;
                            socketConnectorServer.message = stringBuilder.append(socketConnectorServer.message).append("#").append(this.count).append(" from ").append(socket.getInetAddress()).append(":").append(socket.getPort()).append(IOUtils.LINE_SEPARATOR_UNIX).append("Msg from Student: ").append(str).append(IOUtils.LINE_SEPARATOR_UNIX).toString();
                            SocketConnectorServer.this.runOnUiThread(new C05552());
                            dataOutputStream.writeUTF("Hello You are connected to live exam." + this.count + ". Exam will start soon");
                            dataOutputStream2 = dataOutputStream;
                            dataInputStream2 = dataInputStream;
                        } catch (IOException e2) {
                            e = e2;
                            dataOutputStream = dataOutputStream2;
                        } catch (Throwable th2) {
                            th = th2;
                            dataOutputStream = dataOutputStream2;
                        }
                    } catch (IOException e3) {
                        e = e3;
                        dataOutputStream = dataOutputStream2;
                        dataInputStream = dataInputStream2;
                    } catch (Throwable th3) {
                        th = th3;
                        dataOutputStream = dataOutputStream2;
                        dataInputStream = dataInputStream2;
                    }
                }
            } catch (IOException e4) {
                e = e4;
            }
            try {
                e.printStackTrace();
                final String errMsg = e.toString();
                SocketConnectorServer.this.runOnUiThread(new Runnable() {
                    public void run() {
                        SocketConnectorServer.this.msg.setText(errMsg);
                    }
                });
                if (socket != null) {
                    try {
                        socket.close();
                    } catch (IOException e5) {
                        e5.printStackTrace();
                    }
                }
                if (dataInputStream != null) {
                    try {
                        dataInputStream.close();
                    } catch (IOException e52) {
                        e52.printStackTrace();
                    }
                }
                if (dataOutputStream == null) {
                    try {
                        dataOutputStream.close();
                    } catch (IOException e522) {
                        e522.printStackTrace();
                        return;
                    }
                }
            } catch (Throwable th4) {
                if (socket != null) {
                    try {
                        socket.close();
                    } catch (IOException e5222) {
                        e5222.printStackTrace();
                    }
                }
                if (dataInputStream != null) {
                    try {
                        dataInputStream.close();
                    } catch (IOException e52222) {
                        e52222.printStackTrace();
                    }
                }
                if (dataOutputStream != null) {
                    try {
                        dataOutputStream.close();
                    } catch (IOException e522222) {
                        e522222.printStackTrace();
                    }
                }
                throw th4;
            }

        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(C0539R.layout.activity_socket_connector_server);
        this.info = (TextView) findViewById(C0539R.id.info);
        this.infoip = (TextView) findViewById(C0539R.id.infoip);
        this.msg = (TextView) findViewById(C0539R.id.msg);
        new Thread(new SocketServerThread()).start();
        this.infoip.setText(getIpAddress());
    }

    protected void onDestroy() {
        super.onDestroy();
        if (this.serverSocket != null) {
            try {
                this.serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String getIpAddress() {
        String ip = "";
        try {
            Enumeration<NetworkInterface> enumNetworkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (enumNetworkInterfaces.hasMoreElements()) {
                Enumeration<InetAddress> enumInetAddress = ((NetworkInterface) enumNetworkInterfaces.nextElement()).getInetAddresses();
                while (enumInetAddress.hasMoreElements()) {
                    InetAddress inetAddress = (InetAddress) enumInetAddress.nextElement();
                    if (inetAddress.isSiteLocalAddress()) {
                        ip = ip + "SiteLocalAddress: " + inetAddress.getHostAddress() + IOUtils.LINE_SEPARATOR_UNIX;
                    }
                }
            }
            return ip;
        } catch (SocketException e) {
            e.printStackTrace();
            return ip + "Something Wrong! " + e.toString() + IOUtils.LINE_SEPARATOR_UNIX;
        }
    }
}
