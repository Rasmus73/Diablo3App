package org.krabat.diablo3app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import org.krabat.diablo3api_library.D3API.IDownloadListener;
import org.krabat.diablo3api_library.D3API.ServiceProxy;
import org.krabat.diablo3api_library.Types.CareerProfile;
import org.krabat.diablo3api_library.Types.Diablo3APIError;
import org.krabat.diablo3api_library.Types.Diablo3APIRequestEnum;
import org.krabat.diablo3api_library.Types.HeroProfile;
import org.krabat.diablo3api_library.Types.IDiablo3APIType;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements IDownloadListener
{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onButtonClick(View v) {

//	    EditText editText = (EditText) findViewById(R.id.edit_message);
//	    String message = editText.getText().toString();
// https://eu.api.battle.net/d3/profile/Krabat%232146/?locale=en_GB&apikey=dfhu2jbc9ktsjttbr58b6abhsqc7wyk9

        ServiceProxy serviceProxy = new ServiceProxy("https://eu.api.battle.net", "dfhu2jbc9ktsjttbr58b6abhsqc7wyk9");

//        String address = "http://eu.battle.net/api/d3/profile/krabat-2146/";
        String text;
        try {
            //serviceProxy.AsyncServiceCall(address);
            serviceProxy.GetCareerProfile("Krabat", 2146, this);
            text = "Call successfull";
        } catch (IOException e) {
            // TODO: handle exception
            text = e.getMessage();
        }

        setTextMyLabel(text);

        // Do something in response to button
    }

    private void setTextMyLabel(String text) {
        TextView tv = (TextView) findViewById(R.id.textView1);
        tv.setText(text);
    }

    @Override
    public void onDownloadCompleted(Diablo3APIRequestEnum requestEnum, IDiablo3APIType diablo3APIType) {

        if(diablo3APIType instanceof Diablo3APIError)
        {
            Diablo3APIError error = (Diablo3APIError)diablo3APIType;
            setTextMyLabel(error.getErrorMessage());
        }
        else if(diablo3APIType instanceof CareerProfile)
        {
            CareerProfile careerProfile = (CareerProfile)diablo3APIType;
            setTextMyLabel(careerProfile.getHeroes().toString());
//            List<HeroProfile> heroes = careerProfile.getHeroes();

//            ArrayAdapter<HeroProfile> adapter = new ArrayAdapter<HeroProfile>(HeroListActivity.this, android.R.layout.simple_list_item_1, heroes);
//            this.setListAdapter(adapter);
        }
    }
}
