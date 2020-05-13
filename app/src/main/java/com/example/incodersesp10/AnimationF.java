
package com.example.incodersesp10;

import android.annotation.SuppressLint;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;


/** AnimationTool.java: 悬浮窗按钮动画效果 调用 Setting（）显示隐藏 ----- 2018-6-15 下午5:19:55 wangzhongyuan */
public class AnimationF
{
	View view;
	int[] ids;
	
	public AnimationF(View view, int[] R_ids)
	{
		this.view = view;
		ids = R_ids;
		
		initSetting(false);	// 初始隐藏
	}
	
	// 设置层叠按钮初始状态 true展开、false层叠
	public void initSetting(boolean on)
	{
		SettingAniOn = on;
		// int[] ids = { R.id.music, R.id.about, R.id.more, R.id.help };
		// for (int i = 0; i < ids.length; i++)
		// activity.findViewById(ids[i]).setVisibility(on ? View.VISIBLE : View.INVISIBLE);
		
		for (int i = 1; i < ids.length; i++)
			view.findViewById(ids[i]).setVisibility(on ? View.VISIBLE : View.INVISIBLE);
	}
	
	// 设置
	boolean SettingAniOn = true;
	
	// 切换层叠按钮状态（层叠、展开）
	public void Setting(View view)
	{
		settingAni(!SettingAniOn);
	}
	
	// 设置按钮层叠动画效果
	private void settingAni(boolean on)
	{
		SettingAniOn = on;
		final int ANITIME = 300;
		
		// 旋转动画
		// View setting = this.findViewById(R.id.setting);
		View setting = view.findViewById(ids[0]);
		rotateAni(setting, on, ANITIME);
		
		// 位移动画
		// int[] ids = { R.id.music, R.id.about, R.id.more, R.id.help };
		// for (int i = 0; i < ids.length; i++)
		for (int i = 1; i < ids.length; i++)
		{
			View V = view.findViewById(ids[i]);
			transAni(V, i + 1, on, ANITIME);
		}
	}
	
	private void transAni(final View view, int id, final boolean on, final int ANITIME)
	{
		// final float Y = id * 40;
		final float X = id * 40;
		
		AnimationSet AniSet = new AnimationSet(true);
		
		// TranslateAnimation transAin = new TranslateAnimation(0, 0, on ? Y : 0, on ? 0 : Y);
		TranslateAnimation transAin = new TranslateAnimation(on ? -X : 0, on ? 0 : -X, 0, 0);
		transAin.setDuration(ANITIME);
		transAin.setAnimationListener(new AnimationListener()
		{
			@Override
			public void onAnimationStart(Animation arg0)
			{
				view.setClickable(false);
			}
			
			@Override
			public void onAnimationRepeat(Animation arg0)
			{}
			
			@Override
			public void onAnimationEnd(Animation arg0)
			{
				view.setClickable(true);
				view.setVisibility(on ? View.VISIBLE : View.INVISIBLE);
			}
		});
		
		AniSet.addAnimation(transAin);
		view.startAnimation(AniSet);
	}
	
	private void rotateAni(final View view, final boolean on, final int ANITIME)
	{
		AnimationSet AniSet = new AnimationSet(true);
		final float DEGREE = 180;
		
		RotateAnimation rotateAni = new RotateAnimation(0, on ? DEGREE : -DEGREE, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		rotateAni.setDuration(ANITIME + 30);
		rotateAni.setAnimationListener(new AnimationListener()
		{
			@Override
			public void onAnimationStart(Animation arg0)
			{
				view.setClickable(false);
			}
			
			@Override
			public void onAnimationRepeat(Animation arg0)
			{}
			
			@SuppressLint("NewApi")
			@Override
			public void onAnimationEnd(Animation arg0)
			{
				view.setClickable(true);
				view.setRotation(on ? -DEGREE : 0);
			}
		});
		
		AniSet.addAnimation(rotateAni);
		view.startAnimation(AniSet);
	}
}
