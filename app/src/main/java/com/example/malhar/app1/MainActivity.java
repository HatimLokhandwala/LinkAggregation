package com.example.malhar.app1;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;

import android.content.Context;
import android.os.PowerManager;
import android.util.Log;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {
    EditText editTextAddress, editTextPort;
    Button buttonConnect, buttonClear;
    TextView afterDown;
    public static final int DIALOG_DOWNLOAD_PROGRESS = 0;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextAddress = (EditText) findViewById(R.id.address);
        editTextPort = (EditText) findViewById(R.id.port);
        buttonConnect = (Button) findViewById(R.id.connect);
        buttonClear = (Button) findViewById(R.id.clear);
        buttonConnect.setOnClickListener(buttonConnectOnClickListener);
        buttonClear.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextAddress.setText("");
                editTextPort.setText("");
            }
        });
        //afterDown=(TextView)findViewById(R.id.afterDownload);


    }

    OnClickListener buttonConnectOnClickListener =
            new OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    MyClientTask myClientTask = new MyClientTask(
                            editTextAddress.getText().toString(),
                            Integer.parseInt(editTextPort.getText().toString()));
                    myClientTask.execute();
                }
            };


    @Override
    @Deprecated
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DIALOG_DOWNLOAD_PROGRESS:
                mProgressDialog = new ProgressDialog(this);
                mProgressDialog.setMessage("Downloading file..");
                mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                mProgressDialog.setCancelable(false);
                mProgressDialog.show();
                return mProgressDialog;
            default:
                return null;
        }
    }

    public class MyClientTask extends AsyncTask<String, String, String> {

        String dstAddress;
        private Context context;
        private PowerManager.WakeLock mWakeLock;
        int dstPort;
        String response = "";


        MyClientTask(String addr, int port) {
            dstAddress = addr;
            dstPort = port;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showDialog(DIALOG_DOWNLOAD_PROGRESS);
        }

        //@Override
        /*protected void onPreExecute() {
            super.onPreExecute();
            // take CPU lock to prevent CPU from going off if the user
            // presses the power button during download
//            PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
  //          mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
    //                getClass().getName());
      //      mWakeLock.acquire();
            mProgressDialog.show();
        }



        /*@Override
        protected Void doInBackground(Void... arg0) {

            Socket socket = null;

            try {
                socket = new Socket(dstAddress, dstPort);
                long filesize = 1022386;
                long bytesRead;
                long currentTot = 0;
                byte[] bytearray = new byte[(int)filesize];
                InputStream is = socket.getInputStream();
                //File mFolder = new File(getFilesDir() + "/sample");
                File imgFile = new File(Environment.getExternalStorageDirectory(), "temp1.mp4");//"/mnt/sdcard/someimage.txt");
                /*if (!mFolder.exists()) {
                    mFolder.mkdirs();
                }*/
        //BufferedWriter wr = new BufferedWriter(new FileWriter(new File(Environment.getExternalStorageDirectory(),"temp.txt")));
                /*if (!imgFile.exists()) {
                    imgFile.createNewFile();
                }
                FileOutputStream fos = new FileOutputStream(imgFile);
                BufferedOutputStream bos = new BufferedOutputStream(fos);
                bytesRead = is.read(bytearray, 0, bytearray.length);
                currentTot = bytesRead;
                do {
                    bytesRead = is.read(bytearray, (int)currentTot, (int)(bytearray.length - currentTot));
                    if (bytesRead >= 0)
                    {
                        currentTot += bytesRead;
                        int temp=(int)((currentTot*100)/lenghtOfFile);
                        String temp2=Integer.toString(temp);
                        publishProgress(" "+temp2);

                    }
                }
                while (bytesRead > -1);
                String sR = new String(bytearray);
                //System.out.println(sR);
                //wr.write(sR);
                //wr.close();
                bos.write(bytearray, 0, (int)currentTot);
                bos.flush();
                bos.close();
            } catch (UnknownHostException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                response = "UnknownHostException: " + e.toString();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                response = "IOException: " + e.toString();
            } finally {
                if (socket != null) {
                    try {
                        socket.close();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
            return null;
        }*/

        @Override
        protected String doInBackground(String... params) {
            Socket socket = null;

            try {
                socket = new Socket(dstAddress, dstPort);
                DataInputStream dis = new DataInputStream(socket.getInputStream());
                int lenghtOfFile = dis.readInt();
                System.out.println("***LOF*** : "+lenghtOfFile);
                /*InputStreamReader reader=new InputStreamReader(socket.getInputStream());
                BufferedReader bread = new BufferedReader(reader);
                String size0=bread.readLine();
                int size=Integer.parseInt(size0);
                int lenghtOfFile = size;*/
                long filesize = lenghtOfFile+100;
                long bytesRead;
                long currentTot = 0;
                byte[] bytearray = new byte[(int) filesize];
                InputStream is = socket.getInputStream();
                //File mFolder = new File(getFilesDir() + "/sample");
                File imgFile = new File(Environment.getExternalStorageDirectory(), "tempSong.mp3");//"/mnt/sdcard/someimage.txt");
                /*if (!mFolder.exists()) {
                    mFolder.mkdirs();
                }*/
                //BufferedWriter wr = new BufferedWriter(new FileWriter(new File(EnvironmentetExternalStorageDirectory(),"temp.txt")));
                if (!imgFile.exists()) {
                    imgFile.createNewFile();
                }
                FileOutputStream fos = new FileOutputStream(imgFile);
                BufferedOutputStream bos = new BufferedOutputStream(fos);
                bytesRead = is.read(bytearray, 0, bytearray.length);
                currentTot = bytesRead;
                int prev=0;
                do {
                    bytesRead = is.read(bytearray, (int) currentTot, (int) (bytearray.length - currentTot));
                    if (bytesRead >= 0) {
                        currentTot += bytesRead;

                        int temp = (int) ((currentTot * 100) / lenghtOfFile);
                        System.out.println("******LOF******* : "+lenghtOfFile);
                        System.out.println("******CurrentTotal******* : "+currentTot);
                        System.out.println("******temp******* : "+temp);
                     String temp2 = Integer.toString(temp);
                        publishProgress(" " +temp2);
                        mProgressDialog.incrementProgressBy(temp-prev);
                        prev=temp;
                        if(bytesRead==0)
                        {
                            mProgressDialog.setMessage("Download Complete!");
                        }
                    }
                }
                while (bytesRead > -1);
                String sR = new String(bytearray);
                //System.out.println(sR);
                //wr.write(sR);
                //wr.close();
                bos.write(bytearray, 0, (int) currentTot);
                bos.flush();
                bos.close();
            } catch (UnknownHostException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                response = "UnknownHostException: " + e.toString();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                response = "IOException: " + e.toString();
            } finally {
                if (socket != null) {
                    try {
                        socket.close();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }

                return null;
            }
        }


        protected void onProgressUpdate(String... progress) {
            super.onProgressUpdate(progress);
            // if we get here, length is known, now set indeterminate to false
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.setMax(100);
            mProgressDialog.setProgressNumberFormat(progress[0]);
            mProgressDialog.setMessage("Downloading File");

        }


        @Override
        protected void onPostExecute(String unused) {

            //mProgressDialog.setMessage("Download Complete!");
               mProgressDialog.setProgress(0);
            dismissDialog(DIALOG_DOWNLOAD_PROGRESS);
        }

    }
}

