# ParallaxViewPagers
A POC to show how to have multiple ViewPagers work in Parallax effect with each other

Code is based on this StackOverflow post:
http://stackoverflow.com/q/31138652/878126

Demo
--
Here's how it looks like:

![enter image description here](https://github.com/AndroidDeveloperLB/ParallaxViewPagers/blob/master/demo.gif?raw=true)

How to import
--
Currently, because there is an issue (read below), I didn't put an official release, but you can still use Gradle in case you don't care or can't reproduce the issue in your app (link [here](https://jitpack.io/#AndroidDeveloperLB/ParallaxViewPagers/)) :

    repositories {
    	    maven {
    	        url "https://jitpack.io"
    	    }
    	}
    
    dependencies {
    	        compile 'com.github.AndroidDeveloperLB:ParallaxViewPagers:19516eb03a'
    	}

How to use
--
Just prepare the viewpagers, and for each "viewPager" that you need to allow to affect "viewPager2" , use:

    viewPager.addOnPageChangeListener(new ParallaxOnPageChangeListener(viewPager,viewPager2,masterRef));

where "masterRef" is a shared AtomicReference variable that's used to decide which of them is the current "master" to affect the others.

Example of 2 ViewPagers, each can affect the other:

    AtomicReference<ViewPager> masterRef=new AtomicReference<>();
    viewPager.addOnPageChangeListener(new ParallaxOnPageChangeListener(viewPager,viewPager2,masterRef));
    viewPager2.addOnPageChangeListener(new ParallaxOnPageChangeListener(viewPager2,viewPager,masterRef));


Known issues
------------
Sometimes, the other ViewPager might have a "flashing" effect.
