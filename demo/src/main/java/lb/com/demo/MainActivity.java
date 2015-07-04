package lb.com.demo;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.LayoutParams;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.concurrent.atomic.AtomicReference;


public class MainActivity extends AppCompatActivity
  {

  @Override
  protected void onCreate(Bundle savedInstanceState)
    {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    final ViewPager viewPager=(ViewPager)findViewById(R.id.viewPager);
    final ViewPager viewPager2=(ViewPager)findViewById(R.id.viewPager2);
    viewPager.setAdapter(new MyPagerAdapter());
    viewPager2.setAdapter(new MyPagerAdapter());
    /**the current master viewPager*/
    AtomicReference<ViewPager> masterRef=new AtomicReference<>();
    viewPager.addOnPageChangeListener(new ParallaxOnPageChangeListener(viewPager,viewPager2,masterRef));
    viewPager2.addOnPageChangeListener(new ParallaxOnPageChangeListener(viewPager2,viewPager,masterRef));

    }

  private class MyPagerAdapter extends PagerAdapter
    {
    int[] colors=new int[]{0xffff0000,0xff00ff00,0xff0000ff};

    @Override
    public int getCount()
      {
      return 3;
      }

    @Override
    public void destroyItem(ViewGroup container,int position,Object object)
      {
      ((ViewPager)container).removeView((View)object);
      }

    @Override
    public boolean isViewFromObject(final View view,final Object object)
      {
      return (view==object);
      }

    @Override
    public Object instantiateItem(final ViewGroup container,final int position)
      {
      TextView textView=new TextView(MainActivity.this);
      textView.setText("item"+position);
      textView.setBackgroundColor(colors[position]);
      textView.setGravity(Gravity.CENTER);
      final LayoutParams params=new LayoutParams();
      params.height=LayoutParams.MATCH_PARENT;
      params.width=LayoutParams.MATCH_PARENT;
      params.gravity=Gravity.CENTER;
      textView.setLayoutParams(params);
      textView.setTextColor(0xff000000);
      container.addView(textView);
      return textView;
      }
    }

  @Override
  public boolean onCreateOptionsMenu(final android.view.Menu menu)
    {
    getMenuInflater().inflate(R.menu.activity_main,menu);
    return super.onCreateOptionsMenu(menu);
    }

  @SuppressWarnings("deprecation")
  @Override
  public boolean onOptionsItemSelected(final android.view.MenuItem item)
    {
    String url=null;
    switch(item.getItemId())
      {
      case R.id.menuItem_all_my_apps:
        url="https://play.google.com/store/apps/developer?id=AndroidDeveloperLB";
        break;
      case R.id.menuItem_all_my_repositories:
        url="https://github.com/AndroidDeveloperLB";
        break;
      case R.id.menuItem_current_repository_website:
        url="https://github.com/AndroidDeveloperLB/ParallaxViewPagers";
        break;
      }
    if(url==null)
      return true;
    final android.content.Intent intent=new android.content.Intent(android.content.Intent.ACTION_VIEW,android.net.Uri.parse(url));
    intent.addFlags(android.content.Intent.FLAG_ACTIVITY_NO_HISTORY|android.content.Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
    intent.addFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK|android.content.Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
    startActivity(intent);
    return true;
    }
  }

