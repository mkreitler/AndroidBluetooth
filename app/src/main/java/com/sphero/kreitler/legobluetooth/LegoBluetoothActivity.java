package com.sphero.kreitler.legobluetooth;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceView;

import java.util.Iterator;
import java.util.Set;

public class LegoBluetoothActivity extends Activity {
    private static final int BLUETOOTH_ENABLE_INTENT_CODE = 0;

    private GameThread gameThread               = null;
    private BluetoothAdapter BA                 = null;
    private Set<BluetoothDevice> pairedDevices  = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initBluetooth();
    }

    @Override
    public void onDestroy() {
        if (gameThread != null) {
            gameThread.setRunning(false);

            boolean bRetry = true;

            while (bRetry) {
                try {
                    gameThread.join();
                    bRetry = false;
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        super.onDestroy();
    }

    // Implementation //////////////////////////////////////////////////////////////////////////////
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == BLUETOOTH_ENABLE_INTENT_CODE) {
            gameThread = new GameThread();
            CanvasView canvasView = new CanvasView(getApplicationContext());

            assert(canvasView != null);
            assert(gameThread != null);

            setContentView(canvasView);
            gameThread.setViewAndStart(canvasView);

            pairedDevices = BA.getBondedDevices();

            Iterator<BluetoothDevice> iDevice = pairedDevices.iterator();
            while(iDevice.hasNext()) {
                BluetoothDevice btDevice = iDevice.next();
                Log.d("FB", ">>> Device: " + btDevice.getName());
            }
        }
    }

    private void initBluetooth() {
        BA = BluetoothAdapter.getDefaultAdapter();
        assert(BA != null);

        Intent turnOn = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        startActivityForResult(turnOn, BLUETOOTH_ENABLE_INTENT_CODE);
    }
}

