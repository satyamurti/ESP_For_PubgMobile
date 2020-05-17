package com.example.incodersesp10;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import java.io.FileOutputStream;
public class MainActivity extends Activity implements View.OnClickListener
{
    private int mTouchStartX, mTouchStartY;
    private boolean isMove = false;
    private WindowManager mwindow;
    private WindowManager.LayoutParams lparam;
    private ImageButton mbutton;
    private boolean isDisplay = false;

    private boolean isMenuDis = false;
    private View displayMenu;
    private WindowManager mwMenu;
    private WindowManager.LayoutParams mparam;
    private LayoutInflater inflater;
    private View dis;

    private View xfc;

    private Button hide;
    private Switch bt1,bt2,bt3,bt4,bt5,bt6,bt7;


    public boolean bu1=false,bu2=false,bu3=false,bu4=false,bu5=false,bu6=false,bu7=false,fkts=false,xssx=false,xswp=false,xscl=false;

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void CheckFloatViewPermission()
    {
        if (!Settings.canDrawOverlays(this))
        {
            startActivityForResult(new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName())), 0);
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);



        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);



        setContentView(R.layout.activity_main);
        CheckFloatViewPermission();
        Button mbutton1=(Button)findViewById(R.id.b01);
        Button mbutton2=(Button)findViewById(R.id.b02);
        mbutton1.setOnClickListener(this);
        mbutton2.setOnClickListener(this);
        ExecuteElf("su -c");

        //inflater=(LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater= LayoutInflater.from(this);
        displayMenu=inflater.inflate(R.layout.xf,null);
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.b01:
                if (!isDisplay && !isMenuDis)
                    ShowFloatWindow();
                //startService(new Intent(this,FloatingService.class));
                Toast.makeText(MainActivity.this,"Service Started",Toast.LENGTH_LONG).show();
                break;
            case R.id.b02:
                if (isDisplay)
                {
                    mwindow.removeView(mbutton);
                    isDisplay = false;
                    if (isMenuDis)
                    {
                        mwMenu.removeView(dis);
                        isMenuDis = false;
                    }
                }
                else
                {
                    if (isMenuDis)
                    {
                        mwMenu.removeView(dis);
                        isMenuDis = false;
                    }
                }
                break;
            default:
                //Toast.makeText(MainActivity.this,v.getId(),Toast.LENGTH_LONG).show();
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void ShowFloatWindow()
    {
        mwindow=(WindowManager)getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        lparam=new WindowManager.LayoutParams();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            lparam.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            lparam.type = WindowManager.LayoutParams.TYPE_PHONE;
        }
        if (Settings.canDrawOverlays(this))
        {
            //mbutton=(ImageButton)findViewById(R.id.xf1);
            mbutton=new ImageButton(getApplicationContext());
            mbutton.setBackgroundResource(R.drawable.menu_icon);
            mbutton.setOnTouchListener(new View.OnTouchListener()
            {
                @Override
                public boolean onTouch(View v, MotionEvent event)
                {
                    switch (event.getAction())
                    {
                        case MotionEvent.ACTION_DOWN://单击
                            isMove = false;
                            mTouchStartX = (int) event.getRawX();
                            mTouchStartY = (int) event.getRawY();
                            break;
                        case MotionEvent.ACTION_MOVE://拖动
                            int nowX = (int) event.getRawX();
                            int nowY = (int) event.getRawY();
                            int movedX = nowX - mTouchStartX;
                            int movedY = nowY - mTouchStartY;
                            if (movedX > 5 || movedY > 5)
                            {
                                isMove = true;
                            }
                            mTouchStartX = nowX;
                            mTouchStartY = nowY;
                            lparam.x += movedX;
                            lparam.y += movedY;
                            mwindow.updateViewLayout(mbutton, lparam);
                            break;
                        case MotionEvent.ACTION_UP://抬起
                            break;
                        case MotionEvent.ACTION_CANCEL:
                            break;
                    }
                    return isMove;
                }
            });
            mbutton.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    LoadMenu();
                    isMenuDis=true;
                    mwindow.removeView(mbutton);
                    isDisplay=false;
                }
            });
            lparam.format=PixelFormat.RGBA_8888;
            lparam.gravity=Gravity.LEFT;
            lparam.flags=WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
            lparam.width=120;

            lparam.height=120;

            if (mparam == null)
            {
                lparam.x=300;//x
                lparam.y=0;//y
            }
            else
            {
                lparam.x=mparam.x;
                lparam.y=mparam.y;//y
            }
            mwindow.addView(mbutton,lparam);
            isDisplay = true;
        }
        else
        {
            Toast.makeText(this,"UnSuccesfull",Toast.LENGTH_LONG).show();
        }
    }

    @SuppressLint({"CutPasteId", "ClickableViewAccessibility", "RtlHardcoded"})
    private void LoadMenu()
    {
        mwMenu=(WindowManager)getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        mparam=new WindowManager.LayoutParams();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mparam.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            mparam.type = WindowManager.LayoutParams.TYPE_PHONE;
        }

        displayMenu=inflater.inflate(R.layout.xf,null);
        dis=displayMenu.findViewById(R.id.menu);
        mparam.format=PixelFormat.RGBA_8888;
        mparam.gravity=Gravity.LEFT;
        mparam.flags=WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        mparam.width=WindowManager.LayoutParams.WRAP_CONTENT;
        mparam.height=WindowManager.LayoutParams.WRAP_CONTENT;
        mparam.x=lparam.x;//x
        mparam.y=lparam.y;//y
        mwMenu.addView(dis,mparam);


        LinearLayout ll1=displayMenu.findViewById(R.id.menu);
        ll1.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                switch (event.getAction())
                {
                    case MotionEvent.ACTION_DOWN:
                        isMove = false;
                        mTouchStartX = (int) event.getRawX();
                        mTouchStartY = (int) event.getRawY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        int nowX = (int) event.getRawX();
                        int nowY = (int) event.getRawY();
                        int movedX = nowX - mTouchStartX;
                        int movedY = nowY - mTouchStartY;
                        if (movedX > 5 || movedY > 5)
                        {
                            isMove = true;
                        }
                        mTouchStartX = nowX;
                        mTouchStartY = nowY;
                        lparam.x += movedX;
                        lparam.y += movedY;
                        mwindow.updateViewLayout(displayMenu, mparam);
                        break;
                    case MotionEvent.ACTION_UP:
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        break;
                }
                return isMove;
            }
        });


        ImageButton Im1=displayMenu.findViewById(R.id.Im1);
        ImageButton Im2=displayMenu.findViewById(R.id.Im2);
        Im1.setOnClickListener(new View.OnClickListener(){
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v)
            {

                ShowFloatWindow();

                mwMenu.removeView(dis);
                isMenuDis=false;
            }
        });

        Im2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                if (isDisplay)
                {
                    mwindow.removeView(mbutton);
                    isDisplay = false;
                    if (isMenuDis)
                    {
                        mwMenu.removeView(dis);
                        isMenuDis = false;
                    }
                }
                else
                {
                    if (isMenuDis)
                    {
                        mwMenu.removeView(dis);
                        isMenuDis = false;
                    }
                }
            }
        });

        bt1=(Switch)displayMenu.findViewById(R.id.s1);
        bt2=(Switch)displayMenu.findViewById(R.id.wp);
        bt4=(Switch)displayMenu.findViewById(R.id.sx);
        bt5=(Switch)displayMenu.findViewById(R.id.fk);

        UpdateSwitchButton();

        bt1.setOnCheckedChangeListener(new onc());
        bt2.setOnCheckedChangeListener(new onc());
        bt4.setOnCheckedChangeListener(new onc());
        bt5.setOnCheckedChangeListener(new onc());
    }

    private void UpdateSwitchButton()
    {

        bt1.setChecked(bu1);
    }




    private void ExecuteElf(String shell)
    {

        try
        {
            Runtime.getRuntime().exec(shell,null,null);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void SaveText(String path,String txt)
    {
        try
        {
            FileOutputStream fos = new FileOutputStream(path);
            fos.write(txt.getBytes());
            fos.close();
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    class onc implements OnCheckedChangeListener
    {
        @Override
        public void onCheckedChanged(CompoundButton p1, boolean isChecked)
        {
            switch (p1.getId())
            {

                case R.id.s1:
                    if (isChecked)
                    {
                        //TODO

                        ExecuteElf("su -c /data/nc/PUBG");
                        //TODO
                        Date.setA("Open PUBG");
                        FloatService.ShowFloat(MainActivity.this);
                        Toast.makeText(MainActivity.this,"opened",Toast.LENGTH_LONG).show();
                        bu1=true;
                    }
                    else
                    {
                        //TODO

                        //TODO
                        FloatService.HideFloat();
                        Toast.makeText(MainActivity.this,"closed",Toast.LENGTH_LONG).show();
                        bu1=false;
                    }
                    break;
                case R.id.fk:
                    if(isChecked)
                    {
                        Date.setA("Show box");
                        FloatService.ShowFloat(MainActivity.this);
                        Toast.makeText(MainActivity.this,"Opened",Toast.LENGTH_LONG).show();
                        fkts=true;
                    }
                    else
                    {
                        FloatService.HideFloat();
                        Toast.makeText(MainActivity.this,"Closed",Toast.LENGTH_LONG).show();
                        fkts=false;
                    }
                    break;
                case R.id.sx:
                    if (isChecked)
                    {
                        //TODO
                        Date.setA("Display rays");

                        //ExecuteElf("su -c /data/nc/hwz");
                        FloatService.ShowFloat(MainActivity.this);

                        //TODO
                        Toast.makeText(MainActivity.this,"opened",Toast.LENGTH_LONG).show();
                        xssx=true;
                    }
                    else
                    {
                        //TODO

                        //TODO
                        FloatService.HideFloat();
                        Toast.makeText(MainActivity.this,"closed",Toast.LENGTH_LONG).show();
                        xssx=false;
                    }
                    break;
                case R.id.wp:
                    if (isChecked)
                    {
                        //TODO
                        Date.setA("Show items");


                      //  ExecuteElf("su -c /data/nc/nwz");
                        FloatService.ShowFloat(MainActivity.this);


                        //TODO
                        Toast.makeText(MainActivity.this,"Opened",Toast.LENGTH_LONG).show();
                        xswp=true;
                    }
                    else
                    {
                        //TODO
                        FloatService.HideFloat();

                        //TODO
                        Toast.makeText(MainActivity.this,"Closed",Toast.LENGTH_LONG).show();
                        xswp=false;
                    }
                    break;
            }
            //UpdateSwitchButton();
        }
    }
}
