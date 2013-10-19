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
package org.codestorming.copyrighter;

import java.util.EmptyStackException;
import java.util.Iterator;
import java.util.Stack;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;

/**
 * Iterates over a {@link IContainer container}'s children resources.
 * 
 * @author Thaedrik <thaedrik@gmail.com>
 */
public class ResourceIterator<T extends IResource> implements Iterator<T> {

	private Stack<IResource> members;
	private Class<T> resourceType;

	/**
	 * Creates a new {@code ResourceIterator} for the given {@link IContainer container}.
	 * 
	 * @param container The container of the members to get.
	 * @param the type of resource over which iterate.
	 * @throws IllegalArgumentException if the container ot he resource type is
	 *         {@code null}
	 */
	public ResourceIterator(IContainer container, Class<T> resourceType) {
		if (container == null) {
			throw new IllegalArgumentException("The container must not be null.");
		}// else
		if (resourceType == null) {
			throw new IllegalArgumentException("The resource type must not be null.");
		}// else
		members = new Stack<IResource>();
		this.resourceType = resourceType;
		pushResources(container);
	}

	@Override
	public boolean hasNext() {
		return !members.isEmpty();
	}

	@Override
	public T next() {
		if (!hasNext()) {
			throw new EmptyStackException();
		}// else
		return getNext();
	}

	/**
	 * Returns the next resource and remove it from the stack.
	 * <p>
	 * If the returned resource is a {@link IContainer container}, its children members
	 * will be pushed into the stack.
	 * 
	 * @return the next {@link IResource resource}.
	 */
	@SuppressWarnings("unchecked")
	private T getNext() {
		assert !members.isEmpty();
		IResource resource;
		do {
			resource = members.pop();
			if ((resource instanceof IContainer)) {
				// Getting the children members
				pushResources(((IContainer) resource));
			}
		} while (!resourceType.isInstance(resource) && !members.isEmpty());
		return (T) resource;
	}

	/**
	 * Get the members of the given {@link IContainer container} and put them at the end
	 * of the stack.
	 * 
	 * @param container
	 */
	private void pushResources(IContainer container) {
		try {
			for (IResource member : container.members()) {
				this.members.add(member);
			}
		} catch (CoreException e) {
			CopyrighterActivator.log(e);
		}
	}

	/**
	 * <em>Not supported by {@link ResourceIterator}</em>.
	 */
	@Override
	public void remove() {
		throw new UnsupportedOperationException("Can't remove a IResource from a ResourceIterator.");
	}

}
