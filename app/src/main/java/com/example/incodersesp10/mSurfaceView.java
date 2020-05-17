package com.example.incodersesp10;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

//import com.aw.*;

public class mSurfaceView extends SurfaceView implements SurfaceHolder.Callback,Runnable
{

    //Used to mark whether the thread continues
    private boolean Flag=true;
	private ServerSocket server;
	private Socket socket;
    //SurfaceHolder
    SurfaceHolder surfaceHolder;

    //Define Paint Brush
    Paint paint=new Paint();
    Paint paint1=new Paint();
	Paint paint2=new Paint();
	Paint paint3=new Paint();
	Paint paint4=new Paint();
	Paint paint5=new Paint();
	Canvas canvas=null;
	int id=105;
	float x=0;
	float y=0;
	float x1=0;
    float y1=0;
	float w=0;
	float h=0;
	float M=0;
	float hp=0;
	float isbot=0;
	float mz=0;
	float tdid=0;
	float lx=0;
	float ypx=0;
	float ypy=0;
	float zsn=0;
	float yph=0;
	float wqid=0,xssx=0,xswp=0,kills=0;
	//float dxzbid=0;

    public mSurfaceView(Context context, AttributeSet attrs)
	{
        super(context, attrs);

        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
        this.setZOrderOnTop(true);
        surfaceHolder.setFormat(PixelFormat.TRANSLUCENT);
    }


	private Bitmap getBitmapFromPath(String path) {

		if (!new File(path).exists()) {
			System.err.println("getBitmapFromPath: file not exists");
			return null;
		}
		// Bitmap bitmap = Bitmap.createBitmap(1366, 768, Config.ARGB_8888);
		// Canvas canvas = new Canvas(bitmap);
		// Movie movie = Movie.decodeFile(path);
		// movie.draw(canvas, 0, 0);
		//
		// return bitmap;

		byte[] buf = new byte[1024 * 1024];// 1M
		Bitmap bitmap = null;

		try {

			FileInputStream fis = new FileInputStream(path);
			int len = fis.read(buf, 0, buf.length);
			bitmap = BitmapFactory.decodeByteArray(buf, 0, len);
			int bw = bitmap.getWidth();
			int bh = bitmap.getHeight();
			float scale = Math.min(1f * 43 / bw, 1f * 43 / bh);
			bitmap = scaleBitmap(bitmap, scale);
			if (bitmap == null) {
				System.out.println("len= " + len);
				System.err
					.println("path: " + path + "  could not be decode!!!");
			}
		} catch (Exception e) {
			e.printStackTrace();

		}

		return bitmap;
	}


    private Bitmap scaleBitmap(Bitmap origin, float scale) {
        if (origin == null) {
            return null;
        }
        int height = origin.getHeight();
        int width = origin.getWidth();
        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale);// After USe
        Bitmap newBM = Bitmap.createBitmap(origin, 0, 0, width, height, matrix, false);
        if (!origin.isRecycled()) {
            origin.recycle();
        }
        return newBM;
    }


	public static String getFileContent(File file)
	{
        String content = "";
        if (!file.isDirectory())
		{
            try
			{
                InputStream instream = new FileInputStream(file);
				InputStreamReader inputreader
					= new InputStreamReader(instream, "UTF-8");
				BufferedReader buffreader = new BufferedReader(inputreader);
				String line = "";
				while ((line = buffreader.readLine()) != null)
				{
					content += line;
				}
				instream.close();//Close Input Stream
			} catch (IOException ignored)
			{
            }
		}
        return content;
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder)
	{
        //Iinisiale Brush
        init();
        Flag = true;
        new Thread(this).start();
    }
    private void init()
	{
        //paint.setAntiAlias(true);
        paint.setColor(Color.rgb(1, 255, 255));
        paint.setStyle(Paint.Style.FILL);  //Square Background
		paint.setAlpha(50);

        //paint.setDither(true);
		//paint.setFilterBitmap(true);

        //paint1.setAntiAlias(true);
        paint1.setColor(Color.rgb(255, 255, 255));
        paint1.setStyle(Paint.Style.STROKE);  //frame
        paint1.setStrokeWidth(2f);

		//paint1.setDither(true);
		//paint1.setFilterBitmap(true);

        //paint2.setAntiAlias(true);
        paint2.setColor(Color.rgb(255, 52, 25));  //Blodd Bar
        paint2.setStyle(Paint.Style.FILL);
        paint2.setStrokeWidth(5f);
		paint2.setAlpha(200);

		//paint3.setAntiAlias(true);
        paint3.setColor(Color.rgb(255, 255, 255));  //ditance
        paint3.setStyle(Paint.Style.FILL);
        paint3.setStrokeWidth(10f);
		paint3.setTextSize(25);
		paint3.setAlpha(255);

		//paint4.setAntiAlias(true);
        paint4.setColor(Color.rgb(255, 255, 255));  //Back distance
        paint4.setStyle(Paint.Style.FILL);
		paint4.setTextSize(25);
		paint4.setAlpha(205);

		//paint5.setAntiAlias(true);
        paint5.setColor(Color.rgb(255, 0, 0));  //Betray
        paint5.setStyle(Paint.Style.FILL);
		paint5.setTextSize(15);
		paint5.setAlpha(130);
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2)
	{
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder)
	{
        Flag = false;
    }


	private void wzry(){
		File file = new File("/sdcard/c.log");
		if (file == null)
		{
			return;

		}
		String fileContent = getFileContent(file);
		String[] concent = fileContent.split(";");

		Log.d("log", "run: " + concent.length);
		for (int i = 0; i < 5; i++)
		{
			String[] zb = concent[i].split(",");
			try
			{
				id = Integer.parseInt(zb[0]);
				x = Float.parseFloat(zb[1]);
				y = Float.parseFloat(zb[2]);
				h = Float.parseFloat(zb[3]);
				x1 = Float.parseFloat(zb[4]);
				y1 = Float.parseFloat(zb[5]);
				hp = Float.parseFloat(zb[6]);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}

			if(x>0)
			{
                Bitmap btimap=getBitmapFromPath("/sdcard/Download/hero1/"+id+".png");
				canvas.drawLine(1170,540,x-h,y-h,paint2);
				canvas.drawBitmap(btimap,x1*330/100000+165,y1*330/100000+150,paint1);
				canvas.drawText(zb[6],x1*330/100000+165,y1*330/100000+150,paint);
				canvas.drawRect(x-h,y-2*h,x,y,paint);
			}
			/**canvas.drawBitmap(getBitmapFromPath("/sdcard/Download/hero1/"+id+".png"),220,220,paint1);

			 canvas.drawBitmap(getBitmapFromPath("/sdcard/Download/hero1/"+id+".png"),120,120,paint1);

			 canvas.drawBitmap(getBitmapFromPath("/sdcard/Download/hero1/"+id+".png"),50,50,paint1);

			 canvas.drawBitmap(getBitmapFromPath("/sdcard/Download/hero1/"+id+".png"),0,0,paint1);**/
		}
	}

	@SuppressLint("SdCardPath")
	private void pubg(){


		File file = new File("/sdcard/Android/b.txt");
		if (file == null)
		{
			return;

		}
		String fileContent = getFileContent(file);
		String[] concent = fileContent.split(";");
			//Get get = new Get(9898);

			//get.start();
		int rs=0;

		Log.d("/sdcard/Android/log", "run: " + concent.length);
		for (String s : concent) {
			String[] zb = s.split(",");

			try {
				lx = Float.parseFloat(zb[0]);
				x = Float.parseFloat(zb[1]);
				y = Float.parseFloat(zb[2]);
				w = Float.parseFloat(zb[3]);
				h = Float.parseFloat(zb[4]);
				M = Float.parseFloat(zb[5]);
				isbot = Float.parseFloat(zb[6]);
				hp = Float.parseFloat(zb[7]);
				ypx = Float.parseFloat(zb[8]);
				ypy = Float.parseFloat(zb[9]);
				zsn = Float.parseFloat(zb[10]);
				yph = Float.parseFloat(zb[11]);
				mz = Float.parseFloat(zb[12]);
				wqid = Float.parseFloat(zb[13]);


				//Test.main()
			} catch (Exception e) {
				e.printStackTrace();
			}
			int m = (int) M;
			int Lx = (int) lx;
			int Zsn = (int) zsn;
			//int Dxzbid=(int)dxzbid;

			//canvas.drawRect(x, y , w, h, paint1);
			//canvas.drawRect(x, y , w, h, paint);
			String isrj = "Man-machine";
			if (isbot != 0) {
				isrj = "Player";
			}

			if (mz == 1) {
				isrj = isrj + ":Be targeted";
			}

			if (hp <= 0 && Lx == 1) {
				continue;
			}


			if (Lx == 1) {

				if (Zsn == 1) {
					isrj = isrj + "|Blocked|";
				}
				if (wqid != 0) {
					canvas.drawText(getname((int) wqid), x + h / 4, y - 25, paint3);
				}
				if (kills != 0) {
					canvas.drawText("Kills:" + (int) kills, x + h / 4, y - 50, paint3);
				}
				canvas.drawText(isrj + " HP" + (int) hp, x + h / 4, y, paint3);

				canvas.drawText(m + "M", x + h / 4, y - h + 20, paint3);

				canvas.drawLine(x - h / 4, y, x + h / 4, y, paint1);//Upper lIne
				canvas.drawLine(x - h / 4, y - h, x + h / 4, y - h, paint1);//underline
				canvas.drawLine(x - h / 4, y, x - h / 4, y - h, paint1);//Right vertical line
				canvas.drawLine(x + h / 4, y, x + h / 4, y - h, paint1);//Left vertical bar

				canvas.drawLine(2280 / 2, 0, x, y, paint1);//射线


				canvas.drawText("MrSPD ", 200, 200, paint3);
				//Draw out the prediction points

				//canvas.drawText("*",ypx,ypy,paint2);
				//Anticipate hit point
				canvas.drawLine(ypx - yph / 4, ypy, ypx + yph / 4, ypy, paint2);//上横线
				canvas.drawLine(ypx - yph / 4, ypy - yph, ypx + yph / 4, ypy - yph, paint2);//下横线
				canvas.drawLine(ypx - yph / 4, ypy, ypx - yph / 4, ypy - yph, paint2);//右竖线
				canvas.drawLine(ypx + yph / 4, ypy, ypx + yph / 4, ypy - yph, paint2);//左竖线


				rs = rs + 1;
			} else {
				canvas.drawText(getname(Lx) + " " + m + "M", x, w, paint3);

			}


		}
	}
	//Assigning IDs for equipments :))))
	private String getname(int id)
	{
		String name="";
		if (id==10002)
		{
			name="[Equipment] A Lv2";
		}
		if (id==10003)
		{
			name="[Equipment] ALv3";
		}
		if (id==10102)
		{
			name="[Equipment] ALv2";
		}
		if (id==10103)
		{
			name="[Equipment] helmet Lv3";
		}
		if (id==10202)
		{
			name = "[Equipment] Pack Lv2";
		}
		if (id == 10203)
		{
			name = "[Equipment] Pack Lv3";
		}
		if (id == 10301)
		{
			name = "[drug] painkiller";
		}
		if (id == 10302)
		{
			name = "[Drug] Adrenaline";
		}
		if (id == 10303)
		{
			name = "[pharmaceutical] energy drink";
		}
		if (id == 10304)
		{
			name = "[Drug] First Aid Kit";
		}
		if (id == 10305)
		{
			name = "[pharmaceutical] computer";
		}
		if (id == 10701)
		{
			name = "Airdrop";
		}
		if (id == 10702)
		{
			name = "Bone Ash Box";
		}







		if (id == 10401)
		{
			name = "[Weapon-Rifle] m416";
		}
		if (id == 10402)
		{
			name = "[Weapon-Rifle] M16";
		}
		if (id == 10403)
		{
			name = "[Weapon-Rifle] SCAR";
		}
		if (id == 10404)
		{
			name = "[Weapon-Rifle] QBZ";
		}
		if (id == 10405)
		{
			name = "[Weapon-Rifle] M762";
		}
		if (id == 10406)
		{
			name = "[Weapon-Rifle] Mk47";
		}
		if (id == 10407)
		{
			name = "[Weapon-Rifle] Groza";
		}
		if (id == 10408)
		{
			name = "[Weapon-Sniper] AWM";
		}
		if (id == 10409)
		{
			name = "[Weapon-Sniper] Kar98k";
		}
		if (id == 10410)
		{
			name = "[Weapon-Sniper] M24";
		}
		if (id == 10411)
		{
			name = "[Weapon-Sniper] Mini14";
		}
		if (id == 10412)
		{
			name = "[Weapon-Sniper] SKS";
		}
		if (id == 10413)
		{
			name = "[Weapon-Sniper] QBU";
		}
		if (id == 10414)
		{
			name = "[Weapon-Submachine Gun] Thomson";
		}
		if (id == 10415)
		{
			name = "Signal Gun";
		}
		if (id == 10501)
		{
			name = "[bullet] 556";
		}
		if (id == 10502)
		{
			name = "[bullet] 762";
		}
		if (id == 10601)
		{
			name = "[Accessories] Rifle rapid expansion";
		}
		if (id == 10602)
		{
			name = "[Accessories] Rifle expansion";
		}
		if (id == 10603)
		{
			name = "[Accessories] Sniper rapid expansion";
		}
		if (id == 10604)
		{
			name = "[Accessories] Sniper expansion";
		}
		if (id == 10605)
		{
			name = "[Accessories] Rifle silencer";
		}
		if (id == 10606)
		{
			name = "[Accessories] Sniper silencer";
		}
		if (id == 10607)
		{
			name = "[fold mirror] x4";
		}
		if (id == 10608)
		{
			name = "[fold mirror] x6";
		}
		if (id == 10609)
		{
			name = "[fold mirror] x8";
		}
		if (id == 10610)
		{
			name = "Caution Grenade !!!!!!!!!";
		}
		if (id == 10901)
		{
			name = "Motorcycle";
		}
		if (id == 10902)
		{
			name = "Little Sheep Cart";
		}
		if (id == 10903)
		{
			name = "Tricycle";
		}
		if (id == 10904)
		{
			name = "Tricycle";
		}
		if (id == 10905)
		{
			name = "Bungee";
		}
		if (id == 10906)
		{
			name = "sports car";
		}
		if (id == 10907)
		{
			name = "Car";
		}
		if (id == 10908)
		{
			name = "Piggy";
		}
		if (id == 10909)
		{
			name = "Truck";
		}
		if (id == 10910)
		{
			name = "Jeep";
		}
		if (id == 10911)
		{
			name = "Speedboat";
		}



		return name;

	}

	//End

	@Override
	public void run()
	{


        while (Flag)
		{
            try
			{
                canvas = surfaceHolder.lockCanvas();
                canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
            }
			catch (Exception e)
			{
                break;
            }
			if (Date.getA().equals("Turn on peace")) {
				pubg ();
			}
			if (Date.getA().equals("Display box")) {
// xsfk = 1;
			}
			if (Date.getA().equals("Display Ray")) {
// xssx = 1;
			}
			if (Date.getA () == "Show items") {
// xswp = 1;
			}




			surfaceHolder.unlockCanvasAndPost(canvas);
		}
    }
}
	
	
