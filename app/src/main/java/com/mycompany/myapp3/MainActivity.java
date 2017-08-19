package com.mycompany.myapp3;

import android.app.*;
import android.os.*;
import android.widget.*;
import android.view.View.*;
import android.view.*;
import android.util.*;

public class MainActivity extends Activity 
{
	Map map;
	Button up, down, left, right;
	TextView score;
	int a = -1;
	int olda;
	int _score = 0;
	int sleep = 500;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		map = (Map)findViewById(R.id.map);
		up = (Button)findViewById(R.id.up);
		down = (Button)findViewById(R.id.down);
		left = (Button)findViewById(R.id.left);
		right = (Button)findViewById(R.id.right);
		score = (TextView)findViewById(R.id.score);
		
		up.setOnClickListener(arrowclick);
		down.setOnClickListener(arrowclick);
		left.setOnClickListener(arrowclick);
		right.setOnClickListener(arrowclick);
		
		new Thread(new Runnable()
			{
				@Override
				public void run()
				{
					
					while(true)
					{
						runOnUiThread(new Runnable(){
								@Override
								public void run()
								{
									map.invalidate();
								}
							});
							
						int x = map.snack.get(0).getx();
						int y = map.snack.get(0).gety();
						if(a != -1)
						{
							for(int i = map.snack.size() - 1; i >= 1; i --)
							{
								map.snack.get(i).setx(map.snack.get(i - 1).getx());
								map.snack.get(i).sety(map.snack.get(i - 1).gety());
							}
						}
						switch(a)
						{
							case 0:
								map.snack.get(0).sety(y -= map.space);
								break;
							case 1:
								map.snack.get(0).sety(y += map.space);
								break;
							case 2:
								map.snack.get(0).setx(x -= map.space);
								break;
							case 3:
								map.snack.get(0).setx(x += map.space);
								break;
							default:
								break;
						}
						for(int i = 1; i < map.snack.size(); i++)
						{
							if(x == map.snack.get(i).getx() && y == map.snack.get(i).gety())
							{
								for(int n = map.snack.size() - 1; n > 0; n--)
									map.snack.remove(n);
								_score = 0;
								sleep = 500;
								runOnUiThread(new Runnable(){
									@Override
									public void run()
									{
										score.setText("得分：" + _score);
									}
								});
							}
						}
						if(x == map.foodx && y == map.foody)
						{
							Snack s = new Snack(x, y);
							map.snack.add(s);
							map.ate = true;
							_score ++;
							if(sleep > 50)
								sleep = sleep -10;
							runOnUiThread(new Runnable(){
								@Override
								public void run()
								{
									score.setText("得分：" + _score);
								}
							});
						}
						if(x >= map.width)
							map.snack.get(0).setx(0);
						if(x < 0)
							map.snack.get(0).setx(map.width - map.space);
						if(y >= map.height)
							map.snack.get(0).sety(0);
						if(y < 0)
							map.snack.get(0).sety(map.height - map.space);
						try
						{
							Thread.sleep(sleep);
						}catch(InterruptedException e)
						{
							e.printStackTrace();
						}


					}
				}
			}).start();
		
    }
	OnClickListener arrowclick = new OnClickListener(){

		@Override
		public void onClick(View p1)
		{
			switch(p1.getId())
			{
				case R.id.up:
					if(olda != 1)
					a = 0;
					break;
				case R.id.down:
					if(olda != 0)
					a = 1;
					break;
				case R.id.left:
					if(olda != 3)
					a = 2;
					break;
				case R.id.right:
					if(olda != 2)
					a = 3;
					break;
				default:
					a = -1;
					break;
			}
			olda = a;
		}

	};
}
