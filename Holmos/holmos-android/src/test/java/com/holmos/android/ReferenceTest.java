package com.holmos.android;

import holmos.android.element.AElement;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

import org.junit.Test;

import com.holmos.android.tempclass.A;

public class ReferenceTest {
	@Test
	public void testWeakReference() {
		A a = new A();
		a.str = "Hello, reference";
		WeakReference<A> weak = new WeakReference<A>(a);
		a = null;
		int i = 0;
		while (weak.get() != null) {
			System.out.println(String.format(
					"Get str from object of WeakReference: %s, count: %d",
					weak.get().str, ++i));
			if (i % 10 == 0) {
				System.gc();
				System.out.println("System.gc() was invoked!");
			}
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {

			}
		}
		System.out.println("object a was cleared by JVM!");
	}

	@Test
	public void testSoftReference() {
		A a = new A();
		a.str = "Hello, reference";
		SoftReference<A> sr = new SoftReference<A>(a);
		a = null;
		int i = 0;
		while (sr.get() != null) {
			System.out.println(String.format(
					"Get str from object of SoftReference: %s, count: %d",
					sr.get().str, ++i));
			if (i % 10 == 0) {
				System.gc();
				System.out.println("System.gc() was invoked!");
			}
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {

			}
		}
		System.out.println("object a was cleared by JVM!");
	}
	@Test
	public void testReference(){
		A a = new A();
		A b =new A();
		WeakReference<A> c =new WeakReference<A>(b);
		b=null;
		System.out.println(c.get());
		System.gc();
		System.out.println(c.get());
		a.child=b;
	}
	@Test
	public void testSimpleName(){
		System.out.println(AElement.class.getSimpleName());
	}
}
