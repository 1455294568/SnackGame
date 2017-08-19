package com.mycompany.myapp3;
import android.view.*;
import android.content.*;
import android.graphics.*;
import android.util.*;
import java.util.*;

public class Map extends View
{
	final int width = 900;
	final int height = 900;
	final int edgewidth = 10;
	final int space = 30;
	int foodx, foody;
	final Context context;
	public List<Snack> snack;
	public boolean ate = true;
	Paint paint;
	public Map(Context context, AttributeSet attr)
	{
		super(context, attr);
		this.context = context;
		snack = new ArrayList<Snack>();
		paint = new Paint();
		paint.setColor(Color.GRAY);
		paint.setStrokeWidth(2);
		init();
	}

	@Override
	protected void onDraw(final Canvas canvas)
	{
		super.onDraw(canvas);
		canvas.drawColor(Color.WHITE);
		int hor = 0;
		int ver = 0;
		for (int i = 0; i <= height / space; i ++)
		{
			canvas.drawLine(hor, 0, hor, height, paint);
			hor += space;
		}
		for (int i = 0; i <= width / space; i ++)
		{
			canvas.drawLine(0, ver, width, ver, paint);
			ver += space;
		}
		paint.setColor(Color.BLACK);
		for (Snack s : snack)
		{
			canvas.drawRect(s.getx(), s.gety(), s.getx() + space, s.gety() + space, paint);
		}
		paint.setColor(Color.RED);
		if (ate)
		{
			do{
				foodx = Math.abs(new Random().nextInt(900));
				foody = Math.abs(new Random().nextInt(900));
			}while(foodx % space != 0 || foody % space != 0);
			ate = false;
		}
		canvas.drawRect(foodx, foody, foodx + space, foody + space, paint);
		paint.setColor(Color.GRAY);
	}


	private void init()
	{
		int x,y;
		Random r = new Random();
		do
		{
			x = r.nextInt(width);
			y = r.nextInt(height);
		}while(x % space != 0 || y % space != 0);
		Snack s = new Snack(x, y);
		snack.add(s);
	}
}
