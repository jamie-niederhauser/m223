package com.vaadin.example.data.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import com.vaadin.example.ApplicationServiceInitListener;
import com.vaadin.example.data.entity.Kunde;

/**
 * Standard Spring Service class, where we access the database to get our data.
 * <p>
 * See the <code>application.properties</code> file for database connections
 * properties.
 *
 * @see ApplicationServiceInitListener Dummy data generation
 */
@Service
public class KundenService {

	/**
	 * Access point to our DB, automatically configured by Spring.
	 */
	@Autowired
	private JdbcTemplate template;

	/**
	 * @return All movies in the database, with director information.
	 */

	public void addKunde(Kunde kunde) {

		final String sql = KundeRowMapper.INSERT_INTO;
		template.update(
				sql,
				kunde.getName(),
				kunde.getVorname(),
				kunde.getEmail(),
				kunde.getUid()
		);

	}


	public void deleteKunde(Long kundeId) {

		final String sql = KundeRowMapper.DELETE_ALL;
		template.update(sql, kundeId);

	}


	public void updateKundeEmail(long kundeId, String newEmail) {
		final String updateSql = "UPDATE kunde SET email = ? WHERE id = ?";

		template.update(updateSql, newEmail, kundeId);
	}


	public List<Kunde> getKunden() {

		// Use a query the sql mapper class understands
		final String sql = KundeRowMapper.SELECT_ALL;


		// Use Spring's JdbcTemplate helper class to run the sql
		final List<Kunde> list = template.query(sql, new KundeRowMapper());

		return list;
	}




	/**
	 * JDBCTemplate mapper class, where we take a SQL ResultSet and parse out our
	 * Java DTO(s).
	 * <p>
	 * The ResultSet is dependent on the SQL query we do in the Service class, so we
	 * define the query here as a public constant.
	 */
	public static class KundeRowMapper implements RowMapper<Kunde> {

		/**
		 * SQL clause to fetch all Movies from the DB, with Director names included.
		 */
		public static final String SELECT_ALL = "SELECT * FROM kunde kun";
		public static final String INSERT_INTO = "INSERT INTO kunde(name,vorname,email,uid) values(?,?,?,?)";

		public static final String DELETE_ALL = "DELETE FROM kunde WHERE id = ?";



		@Override
		public Kunde mapRow(ResultSet rs, int rowNum) throws SQLException {

			final Kunde kunde = new Kunde();
			kunde.setId(rs.getLong("id"));
			kunde.setName(rs.getString("name"));
			kunde.setVorname(rs.getString("vorname"));
			kunde.setEmail(rs.getString("email"));
			kunde.setUid(rs.getString("uid"));



			return kunde;
		}
	}
}
