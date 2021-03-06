package com.example.app.core.repository;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.app.core.repository.DataMap.Field;
import com.example.app.core.repository.QueryObject.QueryObjectBuilder;

public class Repository {
	
	private static final Logger logger = LoggerFactory.getLogger(Repository.class);
	
	private DataSource dataSource;
	private Orm orm = Orm.INSTANCE;
	
	public Repository(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	public <T> QueryObjectBuilder<T> queryObjectBuilder(Class<T> modelClass) {
		return QueryObject.builder(this, modelClass);
	}

	public <T> List<T> execute(QueryObject<T> queryObject) {
		List<T> result = new ArrayList<T>();
		String sql = buildSql(queryObject);
		DataMap dataMap = orm.get(queryObject.getModelClass());
		
		logger.info(sql);
		try (Connection con = dataSource.getConnection()) {
			ResultSet resultSet = con.prepareStatement(sql).executeQuery();
			while (resultSet.next()) {
				@SuppressWarnings("unchecked")
				T domainModel = (T) queryObject.getModelClass().newInstance();
				
				for (String column : dataMap.getColumns()) {
					Object columnValue = resultSet.getObject(column);
					if (columnValue == null) {
						continue;
					}
					Field field = dataMap.column2Field(column);
					String fieldName = field.label;
					String setterName = "set" + Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
					Method setter = domainModel.getClass().getMethod(setterName, field.type);
					setter.invoke(domainModel, columnValue);
				}
				result.add(domainModel);
			}
		} catch (SQLException e) {
			logger.error("Database error.", e);
		} catch (InvocationTargetException | IllegalArgumentException | SecurityException
				| NoSuchMethodException| InstantiationException | IllegalAccessException e) {
			logger.error("Reflection error.", e);
		}
		return result;
	}
	
	private <T> String buildSql(QueryObject<T> queryObject) {
		DataMap dataMap = orm.get(queryObject.getModelClass());
		StringBuilder stringBuilder =  new StringBuilder()
				.append("select * from ")
				.append(dataMap.getTableName());
		
		List<Criteria> criteria = queryObject.getCriteria();
		if (!criteria.isEmpty()) {
			stringBuilder.append(" where ");
		
			List<String> sqlConditions = criteria.stream()
					.map(qo -> qo.toSql(dataMap)).collect(Collectors.toList());
			
			stringBuilder.append(String.join(" and ", sqlConditions)); 
		}
		
		stringBuilder.append(" ;");
		return stringBuilder.toString();
	}
}
