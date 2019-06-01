package consys.onlineexam.manthan3offlineexam.wifiSocket;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class SocketConnectionClient extends Activity {
    Button buttonConnect;
    TextView textResponse;

    public class MyClientTask extends AsyncTask<Void, Void, Void> {
        String dstAddress;
        int dstPort;
        String msgToServer;
        String response = "";

        MyClientTask(String addr, int port, String msgTo) {
            this.dstAddress = addr;
            this.dstPort = port;
            this.msgToServer = msgTo;
        }

        protected Void doInBackground(Void... arg0) {
            UnknownHostException e;
            IOException e2;
            Throwable th;
            Socket socket = null;
            DataOutputStream dataOutputStream = null;
            DataInputStream dataInputStream = null;
         /*   try {
                DataOutputStream dataOutputStream2;
                DataInputStream dataInputStream2;
                Socket socket2 = new Socket(this.dstAddress, this.dstPort);
                try {
                    Log.e("Connection Established", "Connection Established");
                    dataOutputStream2 = new DataOutputStream(socket2.getOutputStream());
                    try {
                        dataInputStream2 = new DataInputStream(socket2.getInputStream());
                    } catch (UnknownHostException e3) {
                        e = e3;
                        dataOutputStream = dataOutputStream2;
                        socket = socket2;
                        try {
                            e.printStackTrace();
                            this.response = "UnknownHostException: " + e.toString();
                            if (socket != null) {
                                try {
                                    socket.close();
                                } catch (IOException e22) {
                                    e22.printStackTrace();
                                }
                            }
                            if (dataOutputStream != null) {
                                try {
                                    dataOutputStream.close();
                                } catch (IOException e222) {
                                    e222.printStackTrace();
                                }
                            }
                            if (dataInputStream != null) {
                                try {
                                    dataInputStream.close();
                                } catch (IOException e2222) {
                                    e2222.printStackTrace();
                                }
                            }
                            return null;
                        } catch (Throwable th2) {
                            th = th2;
                            if (socket != null) {
                                try {
                                    socket.close();
                                } catch (IOException e22222) {
                                    e22222.printStackTrace();
                                }
                            }
                            if (dataOutputStream != null) {
                                try {
                                    dataOutputStream.close();
                                } catch (IOException e222222) {
                                    e222222.printStackTrace();
                                }
                            }
                            if (dataInputStream != null) {
                                try {
                                    dataInputStream.close();
                                } catch (IOException e2222222) {
                                    e2222222.printStackTrace();
                                }
                            }
                            throw th;
                        }
                    } catch (IOException e4) {
                        dataOutputStream = dataOutputStream2;
                        socket = socket2;
                        e4.printStackTrace();
                        this.response = "IOException: " + e4.toString();
                        if (socket != null) {
                            try {
                                socket.close();
                            } catch (IOException e22222222) {
                                e22222222.printStackTrace();
                            }
                        }
                        if (dataOutputStream != null) {
                            try {
                                dataOutputStream.close();
                            } catch (IOException e222222222) {
                                e222222222.printStackTrace();
                            }
                        }
                        if (dataInputStream != null) {
                            try {
                                dataInputStream.close();
                            } catch (IOException e2222222222) {
                                e2222222222.printStackTrace();
                            }
                        }
                        return null;
                    } catch (Throwable th3) {
                        th = th3;
                        dataOutputStream = dataOutputStream2;
                        socket = socket2;
                        if (socket != null) {
                            socket.close();
                        }
                        if (dataOutputStream != null) {
                            dataOutputStream.close();
                        }
                        if (dataInputStream != null) {
                            dataInputStream.close();
                        }
                        throw th;
                    }
                } catch (UnknownHostException e5) {
                    e = e5;
                    socket = socket2;
                    e.printStackTrace();
                    this.response = "UnknownHostException: " + e.toString();
                    if (socket != null) {
                        socket.close();
                    }
                    if (dataOutputStream != null) {
                        dataOutputStream.close();
                    }
                    if (dataInputStream != null) {
                        dataInputStream.close();
                    }
                    return null;
                } catch (IOException e6) {
                    socket = socket2;
                    e6.printStackTrace();
                    this.response = "IOException: " + e6.toString();
                    if (socket != null) {
                        socket.close();
                    }
                    if (dataOutputStream != null) {
                        dataOutputStream.close();
                    }
                    if (dataInputStream != null) {
                        dataInputStream.close();
                    }
                    return null;
                } catch (Throwable th4) {

                }
                try {
                    if (this.msgToServer != null) {
                        Log.e("msg sending", "msg sending");
                        dataOutputStream2.writeUTF(this.msgToServer);
                        Log.e("msg send", "msg send");
                    }
                    this.response = dataInputStream2.readUTF();
                    if (socket2 != null) {
                        try {
                            socket2.close();
                        } catch (IOException e22222222222) {
                            e22222222222.printStackTrace();
                        }
                    }
                    if (dataOutputStream2 != null) {
                        try {
                            dataOutputStream2.close();
                        } catch (IOException e222222222222) {
                            e222222222222.printStackTrace();
                        }
                    }
                    if (dataInputStream2 != null) {
                        try {
                            dataInputStream2.close();
                            dataInputStream = dataInputStream2;
                            dataOutputStream = dataOutputStream2;
                            socket = socket2;
                        } catch (IOException e2222222222222) {
                            e2222222222222.printStackTrace();
                            dataInputStream = dataInputStream2;
                            dataOutputStream = dataOutputStream2;
                            socket = socket2;
                        }
                    } else {
                        dataOutputStream = dataOutputStream2;
                        socket = socket2;
                    }
                } catch (UnknownHostException e7) {
                    e = e7;
                    dataInputStream = dataInputStream2;
                    dataOutputStream = dataOutputStream2;
                    socket = socket2;
                    e.printStackTrace();
                    this.response = "UnknownHostException: " + e.toString();
                    if (socket != null) {
                        socket.close();
                    }
                    if (dataOutputStream != null) {
                        dataOutputStream.close();
                    }
                    if (dataInputStream != null) {
                        dataInputStream.close();
                    }
                    return null;
                } catch (IOException e8) {
                    dataInputStream = dataInputStream2;
                    dataOutputStream = dataOutputStream2;
                    socket = socket2;
                    e8.printStackTrace();
                    this.response = "IOException: " + e8.toString();
                    if (socket != null) {
                        socket.close();
                    }
                    if (dataOutputStream != null) {
                        dataOutputStream.close();
                    }
                    if (dataInputStream != null) {
                        dataInputStream.close();
                    }
                    return null;
                } catch (Throwable th5) {
                    dataInputStream = dataInputStream2;
                    dataOutputStream = dataOutputStream2;
                    socket = socket2;
                    if (socket != null) {
                        socket.close();
                    }
                    if (dataOutputStream != null) {
                        dataOutputStream.close();
                    }
                    if (dataInputStream != null) {
                        dataInputStream.close();
                    }
                    throw th5;
                }
            } catch (UnknownHostException e9) {
                e = e9;
                e.printStackTrace();
                this.response = "UnknownHostException: " + e.toString();
                try {
                    if (socket != null) {
                        socket.close();
                    }
                    if (dataOutputStream != null) {
                        dataOutputStream.close();
                    }
                    if (dataInputStream != null) {
                        dataInputStream.close();
                    }
                } catch (Exception ef) {
                    ef.printStackTrace();
                }
                return null;
            } catch (IOException e10) {
                e10.printStackTrace();
                this.response = "IOException: " + e10.toString();
                try {
                    if (socket != null) {
                        socket.close();
                    }
                    if (dataOutputStream != null) {
                        dataOutputStream.close();
                    }
                    if (dataInputStream != null) {
                        dataInputStream.close();
                    }
                } catch (Exception es) {
                    es.printStackTrace();
                }
                return null;
            }*/
            return null;
        }

        protected void onPostExecute(Void result) {
            SocketConnectionClient.this.textResponse.setText(this.response);
            super.onPostExecute(result);
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(C0539R.layout.activity_socket_connection_client);
        this.textResponse = (TextView) findViewById(C0539R.id.response);
        new MyClientTask("192.168.43.1", 4321, "Connection Live Exam Name:" + SearchWifi.fullName + " Mobileno:" + SearchWifi.phoneNo).execute(new Void[0]);
    }
}
