package org.rapidoid.test;

/*
 * #%L
 * rapidoid-commons
 * %%
 * Copyright (C) 2014 - 2016 Nikolche Mihajlovski and contributors
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

import org.junit.Before;
import org.rapidoid.data.JSON;
import org.rapidoid.ioc.IoC;
import org.rapidoid.log.Log;
import org.rapidoid.log.LogLevel;
import org.rapidoid.security.Roles;

public abstract class AbstractCommonsTest extends TestCommons {

	@Before
	public void openContext() {
		Roles.resetConfig();
		IoC.defaultContext().reset();
		Log.setLogLevel(LogLevel.INFO);
	}

	protected void verify(String name, Object actual) {
		super.verifyCase(name, JSON.prettify(actual), name);
	}

	protected void verifyIoC() {
		verify("ioc", IoC.defaultContext().info());
	}

	protected void verifyIoC(String name) {
		verify(name, IoC.defaultContext().info());
	}

}
