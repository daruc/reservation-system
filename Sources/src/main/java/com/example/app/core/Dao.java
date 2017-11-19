package com.example.app.core;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.app.homepage.CreateUserModel;
import com.example.app.homepage.LoginModel;

public class Dao {
	private DataSource dataSource;

	@Autowired
	public Dao(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	public boolean createUser(CreateUserModel userFormModel) {
		String sql = new StringBuilder()
				.append("insert into users(login, password, name, surname) values('")
				.append(userFormModel.getLogin())
				.append("', '")
				.append(userFormModel.getPassword())
				.append("', '")
				.append(userFormModel.getName())
				.append("', '")
				.append(userFormModel.getSurname())
				.append("');")
				.toString();
		
		try {
			return dataSource.getConnection().prepareStatement(sql)
			.execute();
		} catch (SQLException e) {
			return false;
		}
	}
	
	public boolean checkUser(LoginModel loginModel) {
		String sql = new StringBuilder()
				.append("select count(*) from users where login = '")
				.append(loginModel.getLogin())
				.append("' and password = '")
				.append(loginModel.getPassword())
				.append("';")
				.toString();
		try {
			ResultSet resultSet = dataSource.getConnection().prepareStatement(sql).executeQuery();
			resultSet.next();
			int count = resultSet.getInt(1);
			return count == 1;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

}
