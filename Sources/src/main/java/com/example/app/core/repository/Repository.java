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

import com.example.app.core.repository.DataMap.Field;
import com.example.app.core.repository.QueryObject.QueryObjectBuilder;

public class Repository {
	
	private DataSource dataSource;
	private ORM orm = ORM.INSTANCE;
	
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
		
		try (Connection con = dataSource.getConnection()) {
			ResultSet resultSet = con.prepareStatement(sql).executeQuery();
			while (resultSet.next()) {
				@SuppressWarnings("unchecked")
				T domainModel = (T) queryObject.getModelClass().newInstance();
				
				for (String column : dataMap.getColumns()) {
					Object columnValue = resultSet.getObject(dataMap.column2Field(column).label);
					Field field = dataMap.column2Field(column);
					String fieldName = field.label;
					String setterName = "set" + Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
					Method setter = domainModel.getClass().getMethod(setterName, field.type);
					setter.invoke(domainModel, columnValue);
				}
				result.add(domainModel);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	private <T> String buildSql(QueryObject<T> queryObject) {
		DataMap dataMap = orm.get(queryObject.getModelClass());
		StringBuilder stringBuilder =  new StringBuilder()
				.append("select * from ")
				.append(dataMap.getTableName())
				.append(" where ");
		
		List<String> sqlConditions = queryObject.getCriteria().stream()
				.map(qo -> qo.toSql(dataMap)).collect(Collectors.toList());
		
		stringBuilder.append(String.join(" and ", sqlConditions));
		stringBuilder.append(" ;");
		return stringBuilder.toString();
	}
}
