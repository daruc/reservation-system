package com.example.app.core.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.example.app.core.repository.Criteria.SqlOperator;

public class QueryObject<T> {
	private Repository repository;
	protected T domainModel;
	protected List<Criteria> criteria;
	
	private QueryObject(QueryObjectBuilder<T> builder) {
		this.repository = builder.getRepository();
		this.domainModel = builder.getDomainModel();
	}
	
	public List<T> execute() {
		return repository.execute(this);
	}
	
	public Class<?> getModelClass() {
		return domainModel.getClass();
	}
	
	public List<Criteria> getCriteria() {
		return criteria;
	}
	
	public static <T> QueryObjectBuilder<T> builder(Repository repository, T domainModel) {
		return new QueryObjectBuilder<T>(repository, domainModel);
	}
	
	
	public static class QueryObjectBuilder<T> {
		protected Repository repository;
		protected T domainModel;
		protected List<Criteria> criteria = new ArrayList<>();
		
		public QueryObjectBuilder(Repository repository, T domainModel) {
			this.repository = repository;
			this.domainModel = domainModel;
		}
		
		public QueryObject<T> build() {
			return new QueryObject<T>(this);
		}
		
		public Repository getRepository() {
			return repository;
		}
		
		public T getDomainModel() {
			return domainModel;
		}
		
		public QueryObjectBuilder<T> addCriteria(Criteria criteria) {
			this.criteria.add(criteria);
			return this;
		}
		
		public QueryObjectBuilder<T> addCriteria(String field, SqlOperator operator, Object value) {
			criteria.add(new Criteria(field, operator, value));
			return this;
		}
		
		public List<Criteria> getCriteria() {
			return criteria;
		}
	}
}
