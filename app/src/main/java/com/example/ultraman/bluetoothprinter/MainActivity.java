package com.example.ultraman.bluetoothprinter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.Set;
import java.util.UUID;

import me.anwarshahriar.calligrapher.Calligrapher;

public class MainActivity extends Activity implements Runnable {

    protected static final String TAG = "TAG";
    private static final int REQUEST_CONNECT_DEVICE = 1;
    private static final int REQUEST_ENABLE_BT = 2;
    Button mScan, mPrint, mDisc;


    BluetoothAdapter mBluetoothAdapter;
    private UUID applicationUUID = UUID
            .fromString("00001101-0000-1000-8000-00805F9B34FB");
    private ProgressDialog mBluetoothConnectProgressDialog;
    private BluetoothSocket mBluetoothSocket;
    BluetoothDevice mBluetoothDevice;


    TextView stat;
    int printstat;

    LinearLayout layout;


    private ProgressDialog loading;


    AlertDialog.Builder builder;

    EditText customer_dtl,order_detail,total_price, agent_detail;


    TextView tvid;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this,"fonts/abel-regular.ttf", true );
        stat = (TextView)findViewById(R.id.bpstatus);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        layout = (LinearLayout)findViewById(R.id.layout);

        customer_dtl = (EditText)findViewById(R.id.et_customer_details);
        order_detail = (EditText)findViewById(R.id.et_order_summary);



        mScan = (Button)findViewById(R.id.Scan);
        mScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View mView) {

                if(mScan.getText().equals("Connect"))
                {
                    mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                    if (mBluetoothAdapter == null) {
                        Toast.makeText(MainActivity.this, "Message1", Toast.LENGTH_SHORT).show();
                    } else {
                        if (!mBluetoothAdapter.isEnabled()) {
                            Intent enableBtIntent = new Intent(
                                    BluetoothAdapter.ACTION_REQUEST_ENABLE);
                            startActivityForResult(enableBtIntent,
                                    REQUEST_ENABLE_BT);
                        } else {
                            ListPairedDevices();
                            Intent connectIntent = new Intent(MainActivity.this,
                                    DeviceListActivity.class);
                            startActivityForResult(connectIntent,
                                    REQUEST_CONNECT_DEVICE);

                        }
                    }

                }
                else if(mScan.getText().equals("Disconnect"))
                {
                    if (mBluetoothAdapter != null)
                        mBluetoothAdapter.disable();
                    stat.setText("");
                    stat.setText("Disconnected");
                    stat.setTextColor(Color.rgb(199, 59, 59));
                    mPrint.setEnabled(false);
                    mScan.setEnabled(true);
                    mScan.setText("Connect");
                }
            }
        });






        mPrint = (Button) findViewById(R.id.mPrint);
        //mPrint.setTypeface(custom);
        mPrint.setOnClickListener(new View.OnClickListener() {
            public void onClick(View mView) {


                p1();
            }
        });




    }


    public void connectDisconnect(View view)
    {
        if(mScan.getText().toString() == "Connect")
        {
            mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            if (mBluetoothAdapter == null) {
                Toast.makeText(MainActivity.this, "Message1", Toast.LENGTH_SHORT).show();
            } else {
                if (!mBluetoothAdapter.isEnabled()) {
                    Intent enableBtIntent = new Intent(
                            BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(enableBtIntent,
                            REQUEST_ENABLE_BT);
                } else {
                    ListPairedDevices();
                    Intent connectIntent = new Intent(MainActivity.this,
                            DeviceListActivity.class);
                    startActivityForResult(connectIntent,
                            REQUEST_CONNECT_DEVICE);
                }
            }


        }else{
            if (mBluetoothAdapter != null)
            {
                mBluetoothAdapter.disable();
            }
            else{
                stat.setText("");
                stat.setText("Disconnected");
                stat.setTextColor(Color.rgb(199, 59, 59));
                mPrint.setEnabled(false);
                /*mDisc.setEnabled(false);
                mDisc.setBackgroundColor(Color.rgb(161, 161, 161));*/
                //mPrint.setBackgroundColor(Color.rgb(161, 161, 161));
                mScan.setBackgroundColor(Color.rgb(0,0,0));
                mScan.setEnabled(true);
                mScan.setText("Disconnect");
            }
        }

    }

    public void p1(){

        Thread t = new Thread() {
            public void run() {
                try {
                    OutputStream os = mBluetoothSocket
                            .getOutputStream();
                    String header = "";
                    char tr1 ;
                    char tr2 ;
                    char tr3 ;
                    char tr4 ;
                    char tr5 ;
                    char tr6 ;
                    char tr7 ;
                    char tr8 ;
                    char tr9 ;
                    char tr10 ;
                    char tr11 ;
                    char tr12 ;

                    String header2 = "";
                    String BILL ;
                    String vio = "";
                    String header3 = "";
                    String mvdtail = "";
                    String header4 = "" ;
                    String offname = "";
                    String copy = "";
                    String checktop_status = "";

                    //tr karakterler
                    tr1=167;//ğ
                    tr2=166;//Ğ
                    tr3=135;//ç
                    tr4=128;//Ç
                    tr5=159;//ş
                    tr6=158;//Ş
                    tr7=129;//ü
                    tr8=154;//Ü
                    tr9=148;//ö
                    tr10=153;//Ö
                    tr11=141;//ı
                    tr12=152;//İ


                    header =  "\nAFANDA YAZICI DENEME:\n";
                    BILL = customer_dtl.getText().toString()+"\n";
                  //  BILL = BILL + "================================\n";
                    header2= "TR KARAKTER :\n";
                    vio = order_detail.getText().toString()+"\n";


                    for (int i=0 ;i<vio.length();i++ ){
                       if(vio.charAt(i)=='ğ'){
                            os.write(tr1);
                        }
                        else if(vio.charAt(i)=='Ğ'){
                            os.write(tr2);
                        }
                        else if(vio.charAt(i)=='ç'){
                            os.write(tr3);
                        }
                        else if(vio.charAt(i)=='Ç'){
                            os.write(tr4);
                        }
                        else if(vio.charAt(i)=='ş'){
                            os.write(tr5);
                        }
                        else if(vio.charAt(i)=='Ş'){
                            os.write(tr6);
                        }
                        else if(vio.charAt(i)=='ü'){
                            os.write(tr7);
                        }
                        else if(vio.charAt(i)=='Ü'){
                            os.write(tr8);
                        }
                        else if(vio.charAt(i)=='ö'){
                            os.write(tr9);
                        }
                        else if(vio.charAt(i)=='Ö'){
                            os.write(tr10);
                        }
                        else if(vio.charAt(i)=='ı'){
                            os.write(tr11);
                        }
                        else if(vio.charAt(i)=='İ'){
                            os.write(tr12);
                        }
                       else if(vio.charAt(i)=='a'){
                           os.write('a');
                       }
                       else if(vio.charAt(i)=='b'){
                           os.write('b');
                       }
                       else if(vio.charAt(i)=='c'){
                           os.write('c');
                       }
                       else if(vio.charAt(i)=='d'){
                           os.write('d');
                       }
                       else if(vio.charAt(i)=='e'){
                           os.write('e');
                       }
                       else if(vio.charAt(i)=='f'){
                           os.write('f');
                       }
                       else if(vio.charAt(i)=='g'){
                           os.write('g');
                       }
                       else if(vio.charAt(i)=='h'){
                           os.write('h');
                       }
                       else if(vio.charAt(i)=='i'){
                           os.write('i');
                       }
                       else if(vio.charAt(i)=='j'){
                           os.write('j');
                       }
                       else if(vio.charAt(i)=='k'){
                           os.write('k');
                       }
                       else if(vio.charAt(i)=='l'){
                           os.write('l');
                       }
                       else if(vio.charAt(i)=='m'){
                           os.write('m');
                       }
                       else if(vio.charAt(i)=='n'){
                           os.write('n');
                       }
                       else if(vio.charAt(i)=='o'){
                           os.write('o');
                       }
                       else if(vio.charAt(i)=='p'){
                           os.write('p');
                       }
                       else if(vio.charAt(i)=='r'){
                           os.write('r');
                       }
                       else if(vio.charAt(i)=='s'){
                           os.write('s');
                       }
                       else if(vio.charAt(i)=='t'){
                           os.write('t');
                       }
                       else if(vio.charAt(i)=='u'){
                           os.write('u');
                       }
                       else if(vio.charAt(i)=='v'){
                           os.write('v');
                       }
                       else if(vio.charAt(i)=='y'){
                           os.write('y');
                       }
                       else if(vio.charAt(i)=='z'){
                           os.write('z');
                       }
                       else if(vio.charAt(i)==','){
                           os.write(',');
                       }
                       else if(vio.charAt(i)=='.'){
                           os.write('.');
                       }
                       else if(vio.charAt(i)=='?'){
                           os.write('?');
                       }
                       else if(vio.charAt(i)=='!'){
                           os.write('!');
                       }
                       else if(vio.charAt(i)=='+'){
                           os.write('+');
                       }
                       else if(vio.charAt(i)=='-'){
                           os.write('-');
                       }
                       else if(vio.charAt(i)=='*'){
                           os.write('*');
                       }
                       else if(vio.charAt(i)=='/'){
                           os.write('/');
                       }
                    }
                    os.write(header.getBytes());
                    int gs = 29;
                    os.write(intToByteArray(gs));
                    int h = 104;
                    os.write(intToByteArray(h));
                    int n = 162;
                    os.write(intToByteArray(n));

                    // Setting Width
                    int gs_width = 29;
                    os.write(intToByteArray(gs_width));
                    int w = 119;
                    os.write(intToByteArray(w));
                    int n_width = 2;
                    os.write(intToByteArray(n_width));


                } catch (Exception e) {
                    Log.e("PrintActivity", "Exe ", e);
                }
            }
        };
        t.start();
    }



    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        try {
            if (mBluetoothSocket != null)
                mBluetoothSocket.close();
        } catch (Exception e) {
            Log.e("Tag", "Exe ", e);
        }
    }


    public void onActivityResult(int mRequestCode, int mResultCode,
                                 Intent mDataIntent) {
        super.onActivityResult(mRequestCode, mResultCode, mDataIntent);

        switch (mRequestCode) {
            case REQUEST_CONNECT_DEVICE:
                if (mResultCode == Activity.RESULT_OK) {
                    Bundle mExtra = mDataIntent.getExtras();
                    String mDeviceAddress = mExtra.getString("DeviceAddress");
                    Log.v(TAG, "Coming incoming address " + mDeviceAddress);
                    mBluetoothDevice = mBluetoothAdapter
                            .getRemoteDevice(mDeviceAddress);
                    mBluetoothConnectProgressDialog = ProgressDialog.show(this,
                            "Connecting...", mBluetoothDevice.getName() + " : "
                                    + mBluetoothDevice.getAddress(), true, false);
                    Thread mBlutoothConnectThread = new Thread(this);
                    mBlutoothConnectThread.start();
                    // pairToDevice(mBluetoothDevice); This method is replaced by
                    // progress dialog with thread
                }
                break;

            case REQUEST_ENABLE_BT:
                if (mResultCode == Activity.RESULT_OK) {
                    ListPairedDevices();
                    Intent connectIntent = new Intent(MainActivity.this,
                            DeviceListActivity.class);
                    startActivityForResult(connectIntent, REQUEST_CONNECT_DEVICE);
                } else {
                    Toast.makeText(MainActivity.this, "Message", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void ListPairedDevices() {
        Set<BluetoothDevice> mPairedDevices = mBluetoothAdapter
                .getBondedDevices();
        if (mPairedDevices.size() > 0) {
            for (BluetoothDevice mDevice : mPairedDevices) {
                Log.v(TAG, "PairedDevices: " + mDevice.getName() + "  "
                        + mDevice.getAddress());
            }
        }
    }

    public void run() {
        try {
            mBluetoothSocket = mBluetoothDevice
                    .createRfcommSocketToServiceRecord(applicationUUID);
            mBluetoothAdapter.cancelDiscovery();
            mBluetoothSocket.connect();
            mHandler.sendEmptyMessage(0);
        } catch (IOException eConnectException) {
            Log.d(TAG, "CouldNotConnectToSocket", eConnectException);
            closeSocket(mBluetoothSocket);
            return;
        }
    }
    private void closeSocket(BluetoothSocket nOpenSocket) {
        try {
            nOpenSocket.close();
            Log.d(TAG, "SocketClosed");
        } catch (IOException ex) {
            Log.d(TAG, "CouldNotCloseSocket");
        }
    }


    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            mBluetoothConnectProgressDialog.dismiss();
            // Snackbar snackbar = Snackbar.make(layout, "Bluetooth Printer is Connected!", Snackbar.LENGTH_LONG);
            // snackbar.show();
            stat.setText("");
            stat.setText("bağlandı");
            stat.setTextColor(Color.rgb(97, 170, 74));
            mPrint.setEnabled(true);
            mScan.setText("Disconnect");
            //mDisc.setEnabled(true);
            //mDisc.setBackgroundColor(Color.rgb(0, 0, 0));
            //mScan.setEnabled(false);
            //mScan.setBackgroundColor(Color.rgb(161, 161, 161));

        }
    };
    public static byte intToByteArray(int value) {
        byte[] b = ByteBuffer.allocate(4).putInt(value).array();

        for (int k = 0; k < b.length; k++) {
            System.out.println("Selva  [" + k + "] = " + "0x"
                    + UnicodeFormatter.byteToHex(b[k]));
        }
        return b[3];
    }
    public byte[] sel(int val) {
        ByteBuffer buffer = ByteBuffer.allocate(2);
        buffer.putInt(val);
        buffer.flip();
        return buffer.array();
    }
}
