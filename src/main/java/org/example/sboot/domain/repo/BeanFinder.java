package org.example.sboot.domain.repo;

import io.ebean.Database;
import io.ebean.EbeanServer;
import io.ebean.Finder;

public abstract class BeanFinder<I,T> extends Finder<I,T> {

	protected final Database server;

	public BeanFinder(Class<T> type, Database server) {
		super(type);
		this.server = server;
	}

	@Override
	public Database db() {
		return server;
	}
}
