package org.rapidoid.commons;

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

import org.rapidoid.annotation.Authors;
import org.rapidoid.annotation.Since;
import org.rapidoid.lambda.Mapper;
import org.rapidoid.u.U;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

@Authors("Nikolche Mihajlovski")
@Since("5.1.0")
public class AutoExpandingMap<K, V> implements Map<K, V> {

	private final Map<K, V> target = Coll.synchronizedMap();

	private final Mapper<K, V> valueFactory;

	public AutoExpandingMap(Mapper<K, V> valueFactory) {
		this.valueFactory = valueFactory;
	}

	@SuppressWarnings("unchecked")
	@Override
	public V get(Object key) {
		synchronized (target) {
			V val = target.get(key);

			if (val == null) {
				try {
					val = valueFactory.map((K) key);
				} catch (Exception e) {
					throw U.rte(e);
				}

				target.put((K) key, val);
			}

			return val;
		}
	}

	public AutoExpandingMap<K, V> copy() {
		return new AutoExpandingMap<K, V>(valueFactory);
	}

	@Override
	public V put(K k, V v) {
		return target.put(k, v);
	}

	@Override
	public V remove(Object o) {
		return target.remove(o);
	}

	@Override
	public void putAll(Map<? extends K, ? extends V> map) {
		target.putAll(map);
	}

	@Override
	public void clear() {
		target.clear();
	}

	@Override
	public Set<K> keySet() {
		return target.keySet();
	}

	@Override
	public Collection<V> values() {
		return target.values();
	}

	@Override
	public Set<Entry<K, V>> entrySet() {
		return target.entrySet();
	}

	@Override
	public boolean equals(Object o) {
		return target.equals(o);
	}

	@Override
	public int hashCode() {
		return target.hashCode();
	}

	@Override
	public V getOrDefault(Object o, V v) {
		return target.getOrDefault(o, v);
	}

	@Override
	public V putIfAbsent(K k, V v) {
		return target.putIfAbsent(k, v);
	}

	@Override
	public boolean remove(Object o, Object o1) {
		return target.remove(o, o1);
	}

	@Override
	public int size() {
		return target.size();
	}

	@Override
	public boolean isEmpty() {
		return target.isEmpty();
	}

	@Override
	public boolean containsKey(Object o) {
		return target.containsKey(o);
	}

	@Override
	public boolean containsValue(Object o) {
		return target.containsValue(o);
	}

}
