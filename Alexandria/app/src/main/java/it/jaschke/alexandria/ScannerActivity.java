package it.jaschke.alexandria;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import me.dm7.barcodescanner.zbar.BarcodeFormat;
import me.dm7.barcodescanner.zbar.Result;
import me.dm7.barcodescanner.zbar.ZBarScannerView;

public class ScannerActivity extends ActionBarActivity
        implements ZBarScannerView.ResultHandler {

    private ZBarScannerView mScannerView;

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);

        mScannerView = new ZBarScannerView(this);

        List<BarcodeFormat> acceptedFormats = new ArrayList<BarcodeFormat>();

        acceptedFormats.add(BarcodeFormat.EAN13);

        mScannerView.setFormats(acceptedFormats);

        setContentView(mScannerView);
    }

    @Override
    public void onResume() {
        super.onResume();

        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
    }

    @Override
    public void onPause() {
        super.onPause();

        mScannerView.stopCamera();
    }

    @Override
    public void handleResult(Result rawResult) {
        Toast.makeText(this, "Contents = " + rawResult.getContents() +
                ", Format = " + rawResult.getBarcodeFormat().getName(), Toast.LENGTH_SHORT).show();

        Intent mainActivityIntent = new Intent(this, MainActivity.class)
                .putExtra("ISBN", rawResult.getContents().replace("978", ""));
        startActivity(mainActivityIntent);
    }

}
