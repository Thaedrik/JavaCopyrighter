/***************************************************************************
 * Copyright (c) 2013 Codestorming.org.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Codestorming - initial API and implementation
 ****************************************************************************/
package org.codestorming.string;

import java.io.*;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * Represents a {@link CharSequence} of a text {@link File file}.
 * 
 * @author Thaedrik <thaedrik@gmail.com>
 */
public class FileString implements CharSequence {

	protected static final int BUFFER_SIZE = 50;

	protected static final double LOAD_FACTOR = 0.25;

	protected File file;

	private char[] content;

	private int size = 0;

	private FutureTask<Boolean> loader;

	/**
	 * Creates a new {@code FileString} with the given {@link File file}.
	 * 
	 * @param file
	 * @throws IllegalArgumentException if the given file is not a <em>normal file</em>.
	 * @see File#isFile()
	 */
	public FileString(File file) {
		assert file != null;
		if (!file.exists()) {
			throw new IllegalArgumentException("The given file doesn't exist.");
		}// else

		if (!file.isFile()) {
			throw new IllegalArgumentException("The given file is not a 'normal' file");
		}// else

		this.file = file;
		load();
	}

	private synchronized void load() {
		loader = new FutureTask<Boolean>(new Callable<Boolean>() {
			@Override
			public Boolean call() throws Exception {
				BufferedReader reader = null;
				try {
					reader = new BufferedReader(new FileReader(file));
					content = new char[getInitialCapacity()];
					String line = reader.readLine();
					while (line != null) {
						ensureCapacity(size + line.length() + 1);
						line.getChars(0, line.length(), content, size);
						content[size + line.length()] = '\n';
						size += line.length() + 1;
						line = reader.readLine();
					}
					return true;
				} catch (FileNotFoundException e) {
					// Should never happen
					return false;
				} catch (IOException e) {
					e.printStackTrace();
					return false;
				} finally {
					if (reader != null) {
						reader.close();
					}
				}
			}
		});
		loader.run();
	}

	/**
	 * Ensures the capacity of the {@code content} array can accept {@link #BUFFER_SIZE}
	 * new elements.
	 */
	protected synchronized void ensureCapacity(int minimumCapacity) {
		if (content.length < minimumCapacity) {
			int newCapacity = (int) ((content.length) * (1 + LOAD_FACTOR));
			if (newCapacity < minimumCapacity) {
				newCapacity = minimumCapacity;
			}
			char[] newContent = new char[newCapacity];
			System.arraycopy(content, 0, newContent, 0, size);
			content = newContent;
		}
	}

	/**
	 * Returns the initial capacity of this char sequence.
	 * 
	 * @return the initial capacity of this char sequence.
	 */
	private int getInitialCapacity() {
		long length = file.length();
		if (length > Integer.MAX_VALUE) {
			length = Integer.MAX_VALUE / 2;
		} else if (length < BUFFER_SIZE) {
			length = BUFFER_SIZE;
		} else {
			length /= 2;
		}
		return (int) length;
	}

	@Override
	public synchronized int length() {
		if (!ready()) {
			return 0;
		}// else
		return size;
	}

	@Override
	public synchronized char charAt(int index) {
		if (index < 0 || index >= size) {
			throw new ArrayIndexOutOfBoundsException(index);
		}// else
		if (!ready()) {
			return 0;
		}// else
		return content[index];
	}

	@Override
	public synchronized CharSequence subSequence(int start, int end) {
		if (end < start) {
			throw new IllegalArgumentException("The given end value cannot be lesser than the start index.");
		}// else
		if (!ready()) {
			return null;
		}// else
		return new String(content, start, end - start);
	}

	/**
	 * Replaces the substring by the given replacement string.
	 * <p>
	 * The substring begin at the given {@code start} index and extends to {@code end - 1}.
	 * 
	 * @param start The index of the first character of the sustring to replace.
	 * @param end The end of the substring to replace.
	 * @param replacement The replacement string.
	 */
	public synchronized void replace(int start, int end, String replacement) {
		if (!ready()) {
			return;
		}// else

		int length = end - start;
		int offset = replacement.length() - length;
		if (offset != 0) {
			char[] newContent = new char[size + offset];
			System.arraycopy(content, 0, newContent, 0, start);
			System.arraycopy(content, end, newContent, start + replacement.length(), size - end);
			content = newContent;
		}
		replacement.getChars(0, replacement.length(), content, start);
		size += offset;
	}

	/**
	 * Flush the content into the file.
	 * 
	 * @throws IOException
	 */
	public synchronized void flush() throws IOException {
		if (!ready()) {
			return;
		}// else

		try {
			new FileOutputStream(file, false).write(toString().getBytes());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String toString() {
		return new String(content, 0, size);
	}

	@Override
	public synchronized boolean equals(Object anObject) {
		if (!ready()) {
			return false;
		}// else
		if (this == anObject) {
			return true;
		}// else
		if (anObject == null) {
			return false;
		}// else
		if (anObject instanceof CharSequence) {
			CharSequence anotherSequence = (CharSequence) anObject;
			int n = size;
			if (n == anotherSequence.length()) {
				char v1[] = content;
				int i = 0;
				while (n-- != 0) {
					if (v1[i] != anotherSequence.charAt(i)) {
						return false;
					}
					i++;
				}
				return true;
			}
		}
		return false;
	}

	/**
	 * Indicates if this sequence is correctly loaded.
	 * <p>
	 * If this sequence is currently loading, this method will wait until the end.
	 * 
	 * @return {@code true} if the sequence is fully loaded;<br>
	 *         {@code false} otherwise.
	 */
	protected boolean ready() {
		try {
			return loader.get();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
