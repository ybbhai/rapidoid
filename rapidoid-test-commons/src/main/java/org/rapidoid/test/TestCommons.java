package org.rapidoid.test;

/*
 * #%L
 * rapidoid-test-commons
 * %%
 * Copyright (C) 2014 Nikolche Mihajlovski
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.Random;

import org.mockito.Mockito;
import org.mockito.stubbing.OngoingStubbing;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public abstract class TestCommons {

	protected static final Random RND = new Random();

	private boolean hasError = false;

	@BeforeMethod(alwaysRun = true)
	public void init() {
		hasError = false;

		try {
			Class<?> clazz = Class.forName("org.rapidoid.util.IOC");
			try {
				Method reset = clazz.getMethod("reset");
				try {
					reset.invoke(null);
				} catch (Exception e) {
					throw new RuntimeException("Cannot find method: UTILS.reset", e);
				}
			} catch (NoSuchMethodException e) {
				throw new RuntimeException("Cannot find method: UTILS.reset");
			} catch (SecurityException e) {
				throw new RuntimeException("Cannot access method: UTILS.reset");
			}

		} catch (ClassNotFoundException e) {
		}
	}

	@AfterMethod(alwaysRun = true)
	public void checkForErrors() {
		if (hasError) {
			Assert.fail("Assertion error(s) occured, probably were caught or were thrown on non-main thread!");
		}
	}

	protected void registerError(AssertionError e) {
		hasError = true;
	}

	protected void fail(String msg) {
		try {
			Assert.fail(msg);
		} catch (AssertionError e) {
			registerError(e);
			throw e;
		}
	}

	protected void isNull(Object value) {
		try {
			Assert.assertNull(value);
		} catch (AssertionError e) {
			registerError(e);
			throw e;
		}
	}

	protected void notNull(Object value) {
		try {
			Assert.assertNotNull(value);
		} catch (AssertionError e) {
			registerError(e);
			throw e;
		}
	}

	protected void notNullAll(Object... value) {
		for (Object object : value) {
			notNull(object);
		}
	}

	protected void isTrue(boolean cond) {
		try {
			Assert.assertTrue(cond);
		} catch (AssertionError e) {
			registerError(e);
			throw e;
		}
	}

	protected void isFalse(boolean cond) {
		try {
			Assert.assertFalse(cond);
		} catch (AssertionError e) {
			registerError(e);
			throw e;
		}
	}

	protected void same(Object... objects) {
		for (int i = 0; i < objects.length - 1; i++) {
			isTrue(objects[i] == objects[i + 1]);
		}
	}

	protected void eq(Object actual, Object expected) {
		try {
			Assert.assertEquals(actual, expected);
		} catch (AssertionError e) {
			registerError(e);
			throw e;
		}
	}

	protected void eq(String actual, String expected) {
		try {
			Assert.assertEquals(actual, expected);
		} catch (AssertionError e) {
			registerError(e);
			throw e;
		}
	}

	protected void eq(char actual, char expected) {
		try {
			Assert.assertEquals(actual, expected);
		} catch (AssertionError e) {
			registerError(e);
			throw e;
		}
	}

	protected void eq(long actual, long expected) {
		try {
			Assert.assertEquals(actual, expected);
		} catch (AssertionError e) {
			registerError(e);
			throw e;
		}
	}

	protected void eq(double actual, double expected) {
		try {
			Assert.assertEquals(actual, expected);
		} catch (AssertionError e) {
			registerError(e);
			throw e;
		}
	}

	protected void eq(byte[] actual, byte[] expected) {
		try {
			Assert.assertEquals(actual, expected);
		} catch (AssertionError e) {
			registerError(e);
			throw e;
		}
	}

	protected void expectedException() {
		try {
			Assert.fail("Expected exception!");
		} catch (AssertionError e) {
			registerError(e);
			throw e;
		}
	}

	protected void hasType(Object instance, Class<?> clazz) {
		try {
			Assert.assertEquals(instance.getClass(), clazz);
		} catch (AssertionError e) {
			registerError(e);
			throw e;
		}
	}

	protected char rndChar() {
		return (char) (65 + rnd(26));
	}

	protected String rndStr(int length) {
		return rndStr(length, length);
	}

	protected String rndStr(int minLength, int maxLength) {
		int len = minLength + rnd(maxLength - minLength + 1);
		StringBuffer sb = new StringBuffer();

		for (int i = 0; i < len; i++) {
			sb.append(rndChar());
		}

		return sb.toString();
	}

	protected int rnd(int n) {
		return RND.nextInt(n);
	}

	protected int rndExcept(int n, int except) {
		if (n > 1 || except != 0) {
			while (true) {
				int num = RND.nextInt(n);
				if (num != except) {
					return num;
				}
			}
		} else {
			throw new RuntimeException("Cannot produce such number!");
		}
	}

	protected <T> T rnd(T[] arr) {
		return arr[rnd(arr.length)];
	}

	protected int rnd() {
		return RND.nextInt();
	}

	protected long rndL() {
		return RND.nextLong();
	}

	protected boolean yesNo() {
		return RND.nextBoolean();
	}

	protected URL resource(String filename) {
		return getClass().getClassLoader().getResource(filename);
	}

	protected File resourceFile(String filename) {
		return new File(resource(filename).getFile());
	}

	protected <T> T mock(Class<T> classToMock) {
		return Mockito.mock(classToMock);
	}

	protected <T> OngoingStubbing<T> when(T methodCall) {
		return Mockito.when(methodCall);
	}

	protected <T> void returns(T methodCall, T result) {
		Mockito.when(methodCall).thenReturn(result);
	}

}
