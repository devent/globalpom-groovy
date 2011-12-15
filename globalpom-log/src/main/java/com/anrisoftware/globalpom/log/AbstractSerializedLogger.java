package com.anrisoftware.globalpom.log;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The base class of an {@link Logger} that can be serialized.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.13
 */
public abstract class AbstractSerializedLogger implements Externalizable {

	protected transient Logger log;

	private Class<?> contextClass;

	/**
	 * For serialization.
	 */
	@Deprecated
	protected AbstractSerializedLogger() {
	}

	/**
	 * Creates a new {@link Logger} for the given context {@link Class}.
	 */
	protected AbstractSerializedLogger(Class<?> contextClass) {
		this.contextClass = contextClass;
		this.log = LoggerFactory.getLogger(contextClass);
	}

	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeObject(contextClass);
		out.flush();
	}

	@Override
	public void readExternal(ObjectInput in) throws IOException,
			ClassNotFoundException {
		contextClass = (Class<?>) in.readObject();
		log = LoggerFactory.getLogger(contextClass);
	}
}
