package com.example.app.core.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.example.app.core.repository.Criteria.SqlOperator;

public class QueryObject<T> {
	private Repository repository;
	protected Class<T> domainClass;
	protected List<Criteria> criteria;
	
	private QueryObject(QueryObjectBuilder<T> builder) {
		this.repository = builder.getRepository();
		this.domainClass = builder.getDomainClass();
		this.criteria = builder.getCriteria();
	}
	
	public List<T> execute() {
		return repository.execute(this);
	}
	
	public Class<?> getModelClass() {
		return domainClass;
	}
	
	public List<Criteria> getCriteria() {
		return criteria;
	}
	
	public static <T> QueryObjectBuilder<T> builder(Repository repository, Class<T> domainClass) {
		return new QueryObjectBuilder<T>(repository, domainClass);
	}
	
	
	public static class QueryObjectBuilder<T> {
		protected Repository repository;
		protected Class<T> domainClass;
		protected List<Criteria> criteria = new ArrayList<>();
		
		public QueryObjectBuilder(Repository repository, Class<T> domainModel) {
			this.repository = repository;
			this.domainClass = domainModel;
		}
		
		public QueryObject<T> build() {
			return new QueryObject<T>(this);
		}
		
		public Repository getRepository() {
			return repository;
		}
		
		public Class<T> getDomainClass() {
			return domainClass;
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
