package com.example.demo2.client;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;


import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

/**
 * An implementation of the ContactDAO interface.
 *
 * @author www.codejava.net
 */
public class ContactDAOImpl implements ContactDAO {

    private JdbcTemplate jdbcTemplate;

    public ContactDAOImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void saveOrUpdate(Contact contact) {

        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("insertUser");
        Map<String, String> inParamMap = new HashMap<String, String>();
        inParamMap.put("name", contact.getName());
        inParamMap.put("email", contact.getEmail());
        inParamMap.put("address", contact.getAddress());
        inParamMap.put("telephone", contact.getTelephone());
        inParamMap.put("regDate", contact.getRegDate().toString());
        SqlParameterSource in = new MapSqlParameterSource(inParamMap);


        Map<String, Object> simpleJdbcCallResult = simpleJdbcCall.execute(in);


    }


    @Override
    public void delete(int contactId) {
        String sql = "DELETE FROM contact WHERE contact_id=?";
        jdbcTemplate.update(sql, contactId);
    }

    @Override
    public List<Contact> list() {
        String sql = "SELECT * FROM contact";
        List<Contact> listContact = jdbcTemplate.query(sql, new RowMapper<Contact>() {

            @Override
            public Contact mapRow(ResultSet rs, int rowNum) throws SQLException {
                Contact aContact = new Contact();

                aContact.setId(rs.getInt("contact_id"));
                aContact.setName(rs.getString("name"));
                aContact.setEmail(rs.getString("email"));
                aContact.setAddress(rs.getString("address"));
                aContact.setTelephone(rs.getString("telephone"));

                return aContact;
            }

        });

        return listContact;
    }

    @Override
    public void test() {

        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("demoSpp");
        Map<String, String> inParamMap = new HashMap<String, String>();
        inParamMap.put("inputParam1", "FirstNameValue");
        inParamMap.put("inputParam2", "LastNameValue");
        SqlParameterSource in = new MapSqlParameterSource(inParamMap);


        Map<String, Object> simpleJdbcCallResult = simpleJdbcCall.execute(in);
        System.out.println(simpleJdbcCallResult.containsKey("inOutParam"));

    }

    @Override
    public Contact get(int contactId) {
        String sql = "SELECT * FROM contact WHERE contact_id=" + contactId;
        return jdbcTemplate.query(sql, new ResultSetExtractor<Contact>() {

            @Override
            public Contact extractData(ResultSet rs) throws SQLException,
                    DataAccessException {
                if (rs.next()) {
                    Contact contact = new Contact();
                    contact.setId(rs.getInt("contact_id"));
                    contact.setName(rs.getString("name"));
                    contact.setEmail(rs.getString("email"));
                    contact.setAddress(rs.getString("address"));
                    contact.setTelephone(rs.getString("telephone"));
                    return contact;
                }

                return null;
            }

        });
    }

}