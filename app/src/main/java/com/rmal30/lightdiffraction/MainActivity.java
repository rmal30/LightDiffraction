package com.rmal30.lightdiffraction;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity{
    ImageView imageView = null;
    int wavelength;
    int distance;
    int swidth;
    int type;
    int sgap;
    int DOUBLE_SLIT = 0;
    int SINGLE_SLIT = 1;
    int DIFF_G = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SeekBar zseekBar = (SeekBar) findViewById(R.id.wavelength);
        SeekBar lseekBar = (SeekBar) findViewById(R.id.distance);
        SeekBar wseekBar = (SeekBar) findViewById(R.id.width);
        final SeekBar gseekBar = (SeekBar) findViewById(R.id.gap);
        final int low_wavelength = 380;
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        imageView = (ImageView) findViewById(R.id.imageView);
        wavelength = zseekBar.getProgress()+low_wavelength;
        distance = lseekBar.getProgress()+100;
        swidth = wseekBar.getProgress()+1;
        sgap = gseekBar.getProgress()+20;
        type = spinner.getSelectedItemPosition();
        final TextView wstr = (TextView) findViewById(R.id.wstr);
        wstr.setText(String.valueOf(wavelength)+" nm");
        final TextView lstr = (TextView) findViewById(R.id.lstr);
        lstr.setText(String.format("%.2f",((double)distance)*0.01)+" m");
        final TextView swstr = (TextView) findViewById(R.id.swstr);
        swstr.setText(String.format("%.1f",((double)swidth)*0.1)+"um");
        final TextView sgstr = (TextView) findViewById(R.id.gstr);
        sgstr.setText(String.valueOf(sgap)+"um");
        zseekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progressValue, boolean fromUser) {
                wavelength = progressValue+low_wavelength;
                wstr.setText(String.valueOf(wavelength)+" nm");

                drawInterferencePattern(type, wavelength, distance, swidth, sgap);
            }
            public void onStartTrackingTouch(SeekBar seekBar){}
            public void onStopTrackingTouch(SeekBar seekBar){}
        });
        lseekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progressValue, boolean fromUser) {
                distance = progressValue+100;
                lstr.setText(String.format("%.2f",((double)distance)*0.01)+" m");
                drawInterferencePattern(type, wavelength, distance, swidth, sgap);
            }
            public void onStartTrackingTouch(SeekBar seekBar){}
            public void onStopTrackingTouch(SeekBar seekBar){}
        });
        wseekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progressValue, boolean fromUser) {
                swidth = progressValue+1;
                swstr.setText(String.format("%.1f",((double)swidth)*0.1)+"um");
                drawInterferencePattern(type, wavelength, distance, swidth, sgap);
            }
            public void onStartTrackingTouch(SeekBar seekBar){}
            public void onStopTrackingTouch(SeekBar seekBar){}
        });
        gseekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progressValue, boolean fromUser) {
                sgap = progressValue+20;
                sgstr.setText(String.valueOf(sgap)+"um");
                drawInterferencePattern(type, wavelength, distance, swidth, sgap);
            }
            public void onStartTrackingTouch(SeekBar seekBar){}
            public void onStopTrackingTouch(SeekBar seekBar){}
        });
        spinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                type = position;
                if(type==SINGLE_SLIT){
                    findViewById(R.id.slitgap).setVisibility(View.GONE);

                }else{
                    findViewById(R.id.slitgap).setVisibility(View.VISIBLE);
                }
                drawInterferencePattern(type, wavelength, distance, swidth, sgap);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}

        });
    }

    public void drawInterferencePattern(int type, int wavelength, int distance, int swidth, int gap){
        if(imageView == null){
            return;
        }
        int width = imageView.getWidth();
        int height = imageView.getHeight();
        int center = width/2;
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        double dswidth = ((double) swidth)*0.0001;
        double dgap_width = ((double) gap)*0.001;
        double ddistance = ((double) distance)*10;
        double dwavelength = ((double) wavelength)*0.000001;
        double scale = 250;
        if(type==DOUBLE_SLIT){
            for(int i=0; i<width; i++){
                double x = Math.abs(((double)i+0.5)/width-0.5)*scale*2;
                //double x1 = Math.abs(((double)i+0.25)/width-0.5)*200;
                //double x2 = Math.abs(((double)i+0.75)/width-0.5)*200;
                //double l11 = Math.sqrt((x1-hswidth)*(x1-hswidth)+ddistance*ddistance);
                //double l21 = Math.sqrt((x1+hswidth)*(x1+hswidth)+ddistance*ddistance);
                //double l12 = Math.sqrt((x2-hswidth)*(x2-hswidth)+ddistance*ddistance);
                //double l22 = Math.sqrt((x2+hswidth)*(x2+hswidth)+ddistance*ddistance);
                //double u1 = 1000*Math.cos(2*Math.PI*l11/dwavelength)/l11;
                //double v1 = 1000*Math.cos(2*Math.PI*l21/dwavelength)/l21;
                //double u2 = 1000*Math.cos(2*Math.PI*l12/dwavelength)/l12;
                //double v2 = 1000*Math.cos(2*Math.PI*l22/dwavelength)/l22;
                //Log.i("u", String.valueOf(u));
                //Log.i("v", String.valueOf(v));
                double length = Math.sqrt(x*x+ddistance*ddistance);
                //double intensity = (ddistance/1000)*(u1+v1+u2+v2)*0.1/Math.PI;
                double factor1 = Math.cos(Math.PI*dgap_width*x/dwavelength/length);
                double delta = Math.PI*dswidth*x/dwavelength/length;
                double factor2 = Math.sin(delta)/delta;
                double intensity = factor1*factor1*Math.abs(factor2)*1000/length;
                paint.setColor(getColor((double) wavelength, intensity));
                canvas.drawRect(i, 0, i+1, 100, paint);
                //canvas.drawRect(center+i, 0, center+i+1, 100, paint);
            }

        }else if(type==SINGLE_SLIT){
            for(int i=0; i<center; i++){
                double x = (double) i*scale/center;
                //double u = 1000*Math.cos(2*l1*1000000*Math.PI/dwavelength)/l1;
                //double v = 1000*Math.cos(2*l2*1000000*Math.PI/dwavelength)/l2;
                double length = Math.sqrt(x*x+ddistance*ddistance);
                //double intensity = (ddistance*0.01)*(u+v)*(u+v)*0.5/Math.PI;
                double delta = Math.PI*dswidth*x/dwavelength/length;
                double factor = Math.sin(delta)/delta;
                if(delta==0){
                    factor = 1;
                }
                double intensity = Math.abs(factor)*1000/length;
                paint.setColor(getColor(wavelength, intensity));
                canvas.drawRect(center-i-1, 0, center-i, 100, paint);
                canvas.drawRect(center+i, 0, center+i+1, 100, paint);
            }
        }else{
            for(int i=0; i<center; i++){
                double x = (double) i*scale/center;
                //double u = 1000*Math.cos(2*l1*1000000*Math.PI/dwavelength)/l1;
                //double v = 1000*Math.cos(2*l2*1000000*Math.PI/dwavelength)/l2;
                double length = Math.sqrt(x*x+ddistance*ddistance);
                //double intensity = (ddistance*0.01)*(u+v)*(u+v)*0.5/Math.PI;
                double delta2 = Math.PI*dgap_width*x/dwavelength/length;
                double delta = Math.PI*dswidth*x/dwavelength/length;
                if(delta==0){
                    delta = 0.001;
                }
                if(delta2==0){
                    delta2 = 0.001;
                }
                double factor = Math.sin(20*delta2)*Math.sin(delta)*0.05*1000/delta/Math.sin(delta2)/length;
                double intensity = Math.abs(factor);
                if(intensity>1){
                    intensity = 1;
                }
                paint.setColor(getColor(wavelength, intensity));
                canvas.drawRect(center-i-1, 0, center-i, 100, paint);
                canvas.drawRect(center+i, 0, center+i+1, 100, paint);
            }
        }
        imageView.setImageBitmap(bitmap);
    }

    public int getColor(double wavelength, double intensity){
        double w = wavelength;
        double r, g, b;
        double sss;
        if(w >= 380 && w < 440){
            r = -(w - 440) / (440 - 350);
            g = 0.0;
            b = 1.0;
        }else if(w >= 440 && w < 490) {
            r = 0.0;
            g = (w - 440) / (490 - 440);
            b = 1.0;
        }else if(w >= 490 && w < 510) {
            r = 0;
            g = 1.0;
            b = -(w - 510) / (510 - 490);
        }else if(w >= 510 && w < 580){
            r = (w - 510) / (580 - 510);
            g = 1.0;
            b = 0.0;
        }else if(w >= 580 && w < 645) {
            r = 1.0;
            g = -(w - 645) / (645 - 580);
            b = 0.0;
        }else if(w >= 645 && w <= 780){
            r = 1.0;
            g = 0.0;
            b = 0.0;
        }else {
            r = 0.0;
            g = 0.0;
            b = 0.0;
        }
    if(w >= 380 && w < 420) {
        sss = 0.3 + 0.7 * (w - 350) / (420 - 350);
    }else if (w >= 420 && w <= 700) {
        sss = 1.0;
    }else if(w > 700 && w <= 780) {
        sss = 0.3 + 0.7 * (780 - w) / (780 - 700);
    } else {
        sss = 0.0;
    }
        sss *= 255;
        double x = sss*r*intensity;
        double y = sss*g*intensity;
        double z = sss*b*intensity;
        int col = Color.argb(255, (int) x, (int) y, (int) z);
        return col;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
