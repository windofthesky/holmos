package com.holmos.android;

import org.junit.Test;

import android.test.ActivityInstrumentationTestCase2;

import com.jayway.android.robotium.solo.Solo;

public class LocatorText extends ActivityInstrumentationTestCase2{
	public LocatorText(Class activityClass) {
		super(activityClass);
	}

	@Test
	public void testLocator(){
		Solo solo=new Solo(getInstrumentation(), getActivity());
		
	}
}
