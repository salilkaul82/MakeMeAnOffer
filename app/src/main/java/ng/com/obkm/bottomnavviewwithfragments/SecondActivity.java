package ng.com.obkm.bottomnavviewwithfragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;

public class SecondActivity extends AppCompatActivity {

    ImageView selectedImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        selectedImage = (ImageView) findViewById(R.id.selectedImage); // init a ImageView
        Intent intent = getIntent(); // get Intent which we set from Previous Activity
        selectedImage.setImageResource(intent.getIntExtra("logo", 0)); // get image from Intent and set it in ImageView

        /*load the offer details in the webview*/
        WebView mywebview = (WebView) findViewById(R.id.webView);
        WebSettings webViewSettings = mywebview.getSettings();
        webViewSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webViewSettings.setJavaScriptEnabled(true);
        webViewSettings.setPluginState(WebSettings.PluginState.ON);
        mywebview.loadUrl(intent.getStringExtra("url"));
        //String test = intent.getStringExtra("url");
        //mywebview.loadUrl("https://www.javatpoint.com/");
    }
}
