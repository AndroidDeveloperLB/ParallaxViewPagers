package lb.com.parallaxviewpagers;
import android.support.v4.view.ViewPager;

import java.util.concurrent.atomic.AtomicReference;

public class ParallaxOnPageChangeListener implements ViewPager.OnPageChangeListener
  {
  private final AtomicReference<ViewPager> masterRef;
  /**
   * the viewpager that is being scrolled
   */
  private ViewPager viewPager;
  /**
   * the viewpager that should be synced
   */
  private ViewPager viewPager2;
  private float lastRemainder;
  private int mLastPos=-1;

  public ParallaxOnPageChangeListener(ViewPager viewPager,ViewPager viewPager2,final AtomicReference<ViewPager> masterRef)
    {
    this.viewPager=viewPager;
    this.viewPager2=viewPager2;
    this.masterRef=masterRef;
    }

  @Override
  public void onPageScrollStateChanged(int state)
    {
    final ViewPager currentMaster=masterRef.get();
    if(currentMaster==viewPager2)
      return;
    switch(state)
      {
      case ViewPager.SCROLL_STATE_DRAGGING:
        if(currentMaster==null)
          masterRef.set(viewPager);
        break;
      case ViewPager.SCROLL_STATE_SETTLING:
        if(mLastPos!=viewPager2.getCurrentItem())
          viewPager2.setCurrentItem(viewPager.getCurrentItem(),false);
        break;
      case ViewPager.SCROLL_STATE_IDLE:
        masterRef.set(null);
        viewPager2.setCurrentItem(viewPager.getCurrentItem(),false);
        mLastPos=-1;
        break;
      }
    }

  @Override
  public void onPageScrolled(int position,float positionOffset,int positionOffsetPixels)
    {
    if(masterRef.get()==viewPager2)
      return;
    if(mLastPos==-1)
      mLastPos=position;
    float diffFactor=(float)viewPager2.getWidth()/this.viewPager.getWidth();
    float scrollTo=this.viewPager.getScrollX()*diffFactor+lastRemainder;
    int scrollToInt=scrollTo<0?(int)Math.ceil(scrollTo):(int)Math.floor(scrollTo);
    lastRemainder=scrollToInt-scrollTo;
    if(mLastPos!=viewPager.getCurrentItem())
      viewPager2.setCurrentItem(viewPager.getCurrentItem(),false);
    viewPager2.scrollTo(scrollToInt,0);

    }

  @Override
  public void onPageSelected(int position)
    {
    }
  }