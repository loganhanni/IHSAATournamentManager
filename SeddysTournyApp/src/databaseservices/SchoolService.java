package databaseservices;

import datatypes.Regional;
import datatypes.School;
import datatypes.SchoolSectional;
import datatypes.Sectional;
import datatypes.SemiState;
import datatypes.Tournament;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class SchoolService {

	// Returns all schools from the database
	public List<School> getAllSchools() {
		// The connection
		Connection connection = null;
		// The results from the database
		ResultSet resultSet = null;
		// The query statement
		Statement statement = null;
		// An empty schoolsList
		List<School> schools = new ArrayList<School>();
		// Must wrap in a try/catch
		try {
			// Try to import this into the class
			Class.forName("org.hsqldb.jdbcDriver");
			// Open a connection on the SitesDB
			connection = DriverManager.getConnection("jdbc:hsqldb:file:TournamentDB", "SA", "");
			// Create a new SQL Statement
			statement = connection.createStatement();
			// Execute a query and return the result
			resultSet = statement.executeQuery("SELECT * FROM SCHOOLS");
			// Read all results
			while (resultSet.next()) {
				School school = new School();
				school.schoolId = resultSet.getString("SchoolID");
				school.schoolName = resultSet.getString("SchoolName");
				school.address = resultSet.getString("Address");
				school.city = resultSet.getString("City");
				school.stateCode = resultSet.getString("StateCode");
				school.zip = resultSet.getString("Zip");
				school.countyName = resultSet.getString("CountyName");
				school.longitude = resultSet.getString("Longitude");
				school.latitude = resultSet.getString("Latitude");
				school.enrollmentAmount = resultSet.getInt("EnrollmentAmount");
				school.maxDistance = resultSet.getString("MaxDistance");
				school.averageDistance = resultSet.getString("AverageDistance");
				schools.add(school);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// Close the connections and such
			try {
				resultSet.close();
				statement.close();
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return schools;
	}

	public School getSchool(String schoolId) {
		// The connection
		Connection connection = null;
		// The results from the database
		ResultSet resultSet = null;
		// The query statement
		Statement statement = null;
		// An empty schoolsList
		School school = new School();
		// Must wrap in a try/catch
		try {
			// Try to import this into the class
			Class.forName("org.hsqldb.jdbcDriver");
			// Open a connection on the SitesDB
			connection = DriverManager.getConnection("jdbc:hsqldb:file:TournamentDB", "SA", "");
			// Create a new SQL Statement
			statement = connection.createStatement();
			// Execute a query and return the result
			resultSet = statement.executeQuery("SELECT * FROM SCHOOLS" + " WHERE SCHOOLID = '" + schoolId + "'");
			// Read all results
			while (resultSet.next()) {
				school.schoolId = resultSet.getString("SchoolID");
				school.schoolName = resultSet.getString("SchoolName");
				school.longitude = resultSet.getString("Longitude");
				school.latitude = resultSet.getString("Latitude");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// Close the connections and such
			try {
				resultSet.close();
				statement.close();
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return school;
	}

	// Returns all States from the database
	public List<SemiState> getStates() {
		// The connection
		Connection connection = null;
		// The results from the database
		ResultSet resultSet = null;
		// The query statement
		Statement statement = null;
		// An empty schoolsList
		List<SemiState> state = new ArrayList<SemiState>();
		// Must wrap in a try/catch
		try {
			// Try to import this into the class
			Class.forName("org.hsqldb.jdbcDriver");
			// Open a connection on the SitesDB
			connection = DriverManager.getConnection("jdbc:hsqldb:file:TournamentDB", "SA", "");
			// Create a new SQL Statement
			statement = connection.createStatement();
			// Execute a query and return the result
			resultSet = statement.executeQuery(
					"SELECT  SS.SEMISTATEID, SS.HOSTSCHOOLID, SS.TOURNAMENTID, T.TOURNAMENTNAME, S.SCHOOLNAME FROM SEMISTATES SS"
							+ " INNER JOIN SCHOOLS S ON S.SCHOOLID = SS.HOSTSCHOOLID"
							+ " INNER JOIN TOURNAMENTS T ON T.TournamentID = SS.TournamentID");
			// Read all results
			while (resultSet.next()) {
				SemiState semistate = new SemiState();
				semistate.semiStateId = resultSet.getString("SemiStateID");
				semistate.hostSchoolId = resultSet.getString("HostSchoolID");
				semistate.tournamentId = resultSet.getString("TournamentID");
				semistate.schoolName = resultSet.getString("SchoolName");
				semistate.tournamentName = resultSet.getString("TournamentName");
				state.add(semistate);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// Close the connections and such
			try {
				resultSet.close();
				statement.close();
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return state;
	}

	// Returns all semi-states from the database
	public List<Regional> getSemiStates() {
		// The connection
		Connection connection = null;
		// The results from the database
		ResultSet resultSet = null;
		// The query statement
		Statement statement = null;
		// An empty schoolsList
		List<Regional> semistate = new ArrayList<Regional>();
		// Must wrap in a try/catch
		try {
			// Try to import this into the class
			Class.forName("org.hsqldb.jdbcDriver");
			// Open a connection on the SitesDB
			connection = DriverManager.getConnection("jdbc:hsqldb:file:TournamentDB", "SA", "");
			// Create a new SQL Statement
			statement = connection.createStatement();
			// Execute a query and return the result
			resultSet = statement
					.executeQuery("SELECT S.SCHOOLNAME SCHOOLNAME, ST.SCHOOLNAME TOURNAMENTNAME FROM REGIONALS R"
							+ " INNER JOIN SEMISTATES SS ON SS.SEMISTATEID = R.PARENTSEMISTATEID"
							+ " INNER JOIN SCHOOLS S ON S.SCHOOLID = R.HOSTSCHOOLID"
							+ " INNER JOIN SCHOOLS ST ON ST.SCHOOLID = SS.HOSTSCHOOLID");
			// Read all results
			while (resultSet.next()) {
				Regional regional = new Regional();
				regional.schoolName = resultSet.getString("SchoolName");
				regional.tournamentName = resultSet.getString("TournamentName");
				semistate.add(regional);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// Close the connections and such
			try {
				resultSet.close();
				statement.close();
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return semistate;
	}

	// Returns all regionals from the database
	public List<Sectional> getRegionals() {
		// The connection
		Connection connection = null;
		// The results from the database
		ResultSet resultSet = null;
		// The query statement
		Statement statement = null;
		// An empty schoolsList
		List<Sectional> regionals = new ArrayList<Sectional>();
		// Must wrap in a try/catch
		try {
			// Try to import this into the class
			Class.forName("org.hsqldb.jdbcDriver");
			// Open a connection on the SitesDB
			connection = DriverManager.getConnection("jdbc:hsqldb:file:TournamentDB", "SA", "");
			// Create a new SQL Statement
			statement = connection.createStatement();
			// Execute a query and return the result
			resultSet = statement
					.executeQuery("SELECT S.SCHOOLNAME SCHOOLNAME, ST.SCHOOLNAME TOURNAMENTNAME FROM SECTIONALS R"
							+ " INNER JOIN REGIONALS SS ON SS.REGIONALID = R.PARENTREGIONALID"
							+ " INNER JOIN SCHOOLS S ON S.SCHOOLID = R.HOSTSCHOOLID"
							+ " INNER JOIN SCHOOLS ST ON ST.SCHOOLID = SS.HOSTSCHOOLID");
			// Read all results
			while (resultSet.next()) {
				Sectional sectional = new Sectional();
				sectional.schoolName = resultSet.getString("SchoolName");
				sectional.tournamentName = resultSet.getString("TournamentName");
				regionals.add(sectional);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// Close the connections and such
			try {
				resultSet.close();
				statement.close();
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return regionals;
	}

	// Returns all Sectionals from the database
	public List<Sectional> getSectionals() {
		// The connection
		Connection connection = null;
		// The results from the database
		ResultSet resultSet = null;
		// The query statement
		Statement statement = null;
		// An empty schoolsList
		List<Sectional> regionals = new ArrayList<Sectional>();
		// Must wrap in a try/catch
		try {
			// Try to import this into the class
			Class.forName("org.hsqldb.jdbcDriver");
			// Open a connection on the SitesDB
			connection = DriverManager.getConnection("jdbc:hsqldb:file:TournamentDB", "SA", "");
			// Create a new SQL Statement
			statement = connection.createStatement();
			// Execute a query and return the result
			resultSet = statement
					.executeQuery("SELECT S.SCHOOLNAME SCHOOLNAME, ST.SCHOOLNAME TOURNAMENTNAME FROM SCHOOLSECTIONALS R"
							+ " INNER JOIN SECTIONALS SS ON SS.SECTIONALID = R.SECTIONALID"
							+ " INNER JOIN SCHOOLS S ON S.SCHOOLID = R.SCHOOLID"
							+ " INNER JOIN SCHOOLS ST ON ST.SCHOOLID = SS.HOSTSCHOOLID");
			// Read all results
			while (resultSet.next()) {
				Sectional sectional = new Sectional();
				sectional.schoolName = resultSet.getString("SchoolName");
				sectional.tournamentName = resultSet.getString("TournamentName");
				regionals.add(sectional);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// Close the connections and such
			try {
				resultSet.close();
				statement.close();
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return regionals;
	}

	public Tournament getTournament(String tournamentId) {
		// The connection
		Connection connection = null;
		// The results from the database
		ResultSet resultSet = null;
		// The query statement
		Statement statement = null;
		// An empty schoolsList
		Tournament tournament = new Tournament();
		// Must wrap in a try/catch
		try {
			// Try to import this into the class
			Class.forName("org.hsqldb.jdbcDriver");
			// Open a connection on the SitesDB
			connection = DriverManager.getConnection("jdbc:hsqldb:file:TournamentDB", "SA", "");
			// Create a new SQL Statement
			statement = connection.createStatement();
			// Execute a query and return the result
			resultSet = statement
					.executeQuery("SELECT * FROM TOURNAMENTS" + " WHERE TOURNAMENTID = '" + tournamentId + "'");
			// Read all results
			while (resultSet.next()) {
				tournament.tournamentId = resultSet.getString("TournamentID");
				tournament.latitude = resultSet.getString("Latitude");
				tournament.longitude = resultSet.getString("Longitude");
				tournament.tournamentName = resultSet.getString("TournamentName");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// Close the connections and such
			try {
				resultSet.close();
				statement.close();
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return tournament;
	}

	public List<Tournament> getTournaments() {
		// The connection
		Connection connection = null;
		// The results from the database
		ResultSet resultSet = null;
		// The query statement
		Statement statement = null;
		// An empty schoolsList
		List<Tournament> tournaments = new ArrayList<Tournament>();
		// Must wrap in a try/catch
		try {
			// Try to import this into the class
			Class.forName("org.hsqldb.jdbcDriver");
			// Open a connection on the SitesDB
			connection = DriverManager.getConnection("jdbc:hsqldb:file:TournamentDB", "SA", "");
			// Create a new SQL Statement
			statement = connection.createStatement();
			// Execute a query and return the result
			resultSet = statement.executeQuery("SELECT * FROM TOURNAMENTS");
			// Read all results
			while (resultSet.next()) {
				Tournament tournament = new Tournament();
				tournament.tournamentId = resultSet.getString("TournamentID");
				tournament.latitude = resultSet.getString("Latitude");
				tournament.longitude = resultSet.getString("Longitude");
				tournament.tournamentName = resultSet.getString("TournamentName");
				tournaments.add(tournament);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// Close the connections and such
			try {
				resultSet.close();
				statement.close();
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return tournaments;
	}
	
	public List<School> getHostSchools(String tournamentId){
		// The connection
		Connection connection = null;
		// The results from the database
		ResultSet resultSet = null;
		// The query statement
		Statement statement = null;
		// An empty schoolsList
		List<School> schools = new ArrayList<School>();
		// Must wrap in a try/catch
		try {
			// Try to import this into the class
			Class.forName("org.hsqldb.jdbcDriver");
			// Open a connection on the SitesDB
			connection = DriverManager.getConnection("jdbc:hsqldb:file:TournamentDB", "SA", "");
			// Create a new SQL Statement
			statement = connection.createStatement();
			// Execute a query and return the result
			resultSet = statement
					.executeQuery("SELECT S.SCHOOLID, S.SCHOOLNAME FROM HOSTSCHOOLS HS "
							+ "INNER JOIN SCHOOLS S ON S.SCHOOLID = HS.SCHOOLID "
							+ "WHERE HS.TOURNAMENTID = '"+tournamentId+"'");
			// Read all results
			while (resultSet.next()) {
				School school = new School();
				school.schoolName = resultSet.getString("SchoolName");
				school.schoolId = resultSet.getString("SchoolID");
				schools.add(school);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// Close the connections and such
			try {
				resultSet.close();
				statement.close();
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return schools;
	}
	
	public List<School> getNonHostSchools(){
		// The connection
		Connection connection = null;
		// The results from the database
		ResultSet resultSet = null;
		// The query statement
		Statement statement = null;
		// An empty schoolsList
		List<School> schools = new ArrayList<School>();
		// Must wrap in a try/catch
		try {
			// Try to import this into the class
			Class.forName("org.hsqldb.jdbcDriver");
			// Open a connection on the SitesDB
			connection = DriverManager.getConnection("jdbc:hsqldb:file:TournamentDB", "SA", "");
			// Create a new SQL Statement
			statement = connection.createStatement();
			// Execute a query and return the result
			resultSet = statement
					.executeQuery("SELECT S.SCHOOLID, S.SCHOOLNAME FROM SCHOOLS S WHERE S.ISHOST = FALSE");
			// Read all results
			while (resultSet.next()) {
				School school = new School();
				school.schoolName = resultSet.getString("SchoolName");
				school.schoolId = resultSet.getString("SchoolID");
				schools.add(school);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// Close the connections and such
			try {
				resultSet.close();
				statement.close();
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return schools;
	}

	public boolean addNewHost(String schoolId, String tournamentId) {
		// The connection
		Connection connection = null;
		// The results from the database
		boolean resultSet = false;
		// The query statement
		Statement statement = null;
		// Must wrap in a try/catch
		try {
			// Try to import this into the class
			Class.forName("org.hsqldb.jdbcDriver");
			// Open a connection on the SitesDB
			connection = DriverManager.getConnection("jdbc:hsqldb:file:TournamentDB", "SA", "");
			// Create a new SQL Statement
			statement = connection.createStatement();
			// Execute a query and return the result
			resultSet = statement
					.execute("UPDATE SCHOOLS "
							+ "SET ISHOST = TRUE "
							+ "WHERE SCHOOLID = '"+schoolId+"'"
							+ " INSERT INTO HOSTSCHOOLS (SCHOOLID, TOURNAMENTID)"
							+ " VALUES('"+schoolId+"','"+tournamentId+"')");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// Close the connections and such
			try {
				statement.close();
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return resultSet;
	}
	
	public boolean transferSchoolSectional(String schoolId, String hostId, String tournamentId) {
		// The connection
		Connection connection = null;
		// The results from the database
		ResultSet resultSet = null;
		boolean success = false;
		//String oldSectionalId = "";
		String newSectionalId = "";
		// The query statement
		Statement statement = null;
		// Must wrap in a try/catch
		try {
			// Try to import this into the class
			Class.forName("org.hsqldb.jdbcDriver");
			// Open a connection on the SitesDB
			connection = DriverManager.getConnection("jdbc:hsqldb:file:TournamentDB", "SA", "");
			// Create a new SQL Statement
			statement = connection.createStatement();
			// Execute a query and return the result
			//resultSet = statement
			//		.executeQuery("SELECT SE.SECTIONALID FROM SECTIONALS SE "
			//				+ " INNER JOIN SCHOOLSECTIONALS SS ON SS.SCHOOLID = '"+schoolId+"' AND SS.SECTIONALID = SE.SECTIONALID"
			//				+ " WHERE SE.TOURNAMENTID = '"+tournamentId+"'");
			
			//while(resultSet.next()) {
			//	oldSectionalId = resultSet.getString("SectionalID");
			//}
			
			resultSet = statement
					.executeQuery("SELECT SE.SECTIONALID FROM SECTIONALS SE "							
							+ " WHERE SE.TOURNAMENTID = '"+tournamentId+"' AND SE.HOSTSCHOOLID = '"+hostId+"'");
			
			while(resultSet.next()) {
				newSectionalId = resultSet.getString("SectionalID");
			}
			
			if(newSectionalId != "" && newSectionalId != null) {
				success = statement.execute(""
						+ " INSERT INTO SCHOOLSECTIONALS(SCHOOLID, SECTIONALID)"
						+ " VALUES('"+schoolId+"','"+newSectionalId+"')");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// Close the connections and such
			try {
				statement.close();
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return success;
	}
	
	public List<School> getSectionalsMap(String tournamentId) {
		// The connection
		Connection connection = null;
		// The results from the database
		ResultSet resultSet = null;
		// The query statement
		Statement statement = null;
		// An empty schoolsList
		List<School> schools = new ArrayList<School>();
		// Must wrap in a try/catch
		try {
			// Try to import this into the class
			Class.forName("org.hsqldb.jdbcDriver");
			// Open a connection on the SitesDB
			connection = DriverManager.getConnection("jdbc:hsqldb:file:TournamentDB", "SA", "");
			// Create a new SQL Statement
			statement = connection.createStatement();
			// Execute a query and return the result
			resultSet = statement
					.executeQuery("SELECT S.SCHOOLID, S.SCHOOLNAME, S.LONGITUDE, S.LATITUDE FROM SECTIONALS R"
							+ " INNER JOIN SCHOOLS S ON S.SCHOOLID = R.HOSTSCHOOLID" + " WHERE R.TOURNAMENTID = '"
							+ tournamentId + "'");
			// Read all results
			while (resultSet.next()) {
				School school = new School();
				school.schoolName = resultSet.getString("SchoolName");
				school.schoolId = resultSet.getString("SchoolID");
				school.longitude = resultSet.getString("Longitude");
				school.latitude = resultSet.getString("Latitude");
				schools.add(school);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// Close the connections and such
			try {
				resultSet.close();
				statement.close();
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return schools;
	}

	public List<School> getRegionalsMap(String tournamentId) {
		// The connection
		Connection connection = null;
		// The results from the database
		ResultSet resultSet = null;
		// The query statement
		Statement statement = null;
		// An empty schoolsList
		List<School> schools = new ArrayList<School>();
		// Must wrap in a try/catch
		try {
			// Try to import this into the class
			Class.forName("org.hsqldb.jdbcDriver");
			// Open a connection on the SitesDB
			connection = DriverManager.getConnection("jdbc:hsqldb:file:TournamentDB", "SA", "");
			// Create a new SQL Statement
			statement = connection.createStatement();
			// Execute a query and return the result
			resultSet = statement
					.executeQuery("SELECT S.SCHOOLID, S.SCHOOLNAME, S.LONGITUDE, S.LATITUDE FROM REGIONALS R"
							+ " INNER JOIN SCHOOLS S ON S.SCHOOLID = R.HOSTSCHOOLID" + " WHERE R.TOURNAMENTID = '"
							+ tournamentId + "'");
			// Read all results
			while (resultSet.next()) {
				School school = new School();
				school.schoolName = resultSet.getString("SchoolName");
				school.schoolId = resultSet.getString("SchoolID");
				school.longitude = resultSet.getString("Longitude");
				school.latitude = resultSet.getString("Latitude");
				schools.add(school);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// Close the connections and such
			try {
				resultSet.close();
				statement.close();
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return schools;
	}

	public List<School> getSchoolSectionals(String tournamentId, String parentSectionalId) {
		// The connection
		Connection connection = null;
		// The results from the database
		ResultSet resultSet = null;
		// The query statement
		Statement statement = null;
		// An empty schoolsList
		List<School> schools = new ArrayList<School>();
		// Must wrap in a try/catch
		try {
			// Try to import this into the class
			Class.forName("org.hsqldb.jdbcDriver");
			// Open a connection on the SitesDB
			connection = DriverManager.getConnection("jdbc:hsqldb:file:TournamentDB", "SA", "");
			// Create a new SQL Statement
			statement = connection.createStatement();
			// Execute a query and return the result
			resultSet = statement
					.executeQuery("SELECT S.SCHOOLID, S.SCHOOLNAME, S.LONGITUDE, S.LATITUDE FROM SECTIONALS SEC"
							+ " INNER JOIN SCHOOLSECTIONALS SS ON SS.SECTIONALID = SEC.SECTIONALID"
							+ " INNER JOIN SCHOOLS S ON S.SCHOOLID = SS.SCHOOLID" + " WHERE SEC.TournamentID = '"
							+ tournamentId + "'" + " AND SEC.HostSchoolID = '" + parentSectionalId + "'");
			// Read all results
			while (resultSet.next()) {
				School school = new School();
				school.schoolName = resultSet.getString("SchoolName");
				school.schoolId = resultSet.getString("SchoolID");
				school.longitude = resultSet.getString("Longitude");
				school.latitude = resultSet.getString("Latitude");
				schools.add(school);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// Close the connections and such
			try {
				resultSet.close();
				statement.close();
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return schools;
	}

	public List<School> getSectionalHosts(String tournamentId, String parentRegionalId) {
		// The connection
		Connection connection = null;
		// The results from the database
		ResultSet resultSet = null;
		// The query statement
		Statement statement = null;
		// An empty schoolsList
		List<School> schools = new ArrayList<School>();
		// Must wrap in a try/catch
		try {
			// Try to import this into the class
			Class.forName("org.hsqldb.jdbcDriver");
			// Open a connection on the SitesDB
			connection = DriverManager.getConnection("jdbc:hsqldb:file:TournamentDB", "SA", "");
			// Create a new SQL Statement
			statement = connection.createStatement();
			// Execute a query and return the result
			resultSet = statement
					.executeQuery("SELECT S.SCHOOLID, S.SCHOOLNAME, S.LONGITUDE, S.LATITUDE FROM SECTIONALS R"
							+ " INNER JOIN SCHOOLS S ON S.SCHOOLID = R.HOSTSCHOOLID"
							+ " INNER JOIN REGIONALS ST ON ST.HOSTSCHOOLID = '" + parentRegionalId + "'"
							+ " WHERE R.TOURNAMENTID = '" + tournamentId + "'"
							+ " AND R.PARENTREGIONALID = ST.REGIONALID");
			// Read all results
			while (resultSet.next()) {
				School school = new School();
				school.schoolName = resultSet.getString("SchoolName");
				school.schoolId = resultSet.getString("SchoolID");
				school.longitude = resultSet.getString("Longitude");
				school.latitude = resultSet.getString("Latitude");
				schools.add(school);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// Close the connections and such
			try {
				resultSet.close();
				statement.close();
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return schools;
	}

	public List<School> getRegionalHosts(String tournamentId, String parentSemiStateId) {
		// The connection
		Connection connection = null;
		// The results from the database
		ResultSet resultSet = null;
		// The query statement
		Statement statement = null;
		// An empty schoolsList
		List<School> schools = new ArrayList<School>();
		// Must wrap in a try/catch
		try {
			// Try to import this into the class
			Class.forName("org.hsqldb.jdbcDriver");
			// Open a connection on the SitesDB
			connection = DriverManager.getConnection("jdbc:hsqldb:file:TournamentDB", "SA", "");
			// Create a new SQL Statement
			statement = connection.createStatement();
			// Execute a query and return the result
			resultSet = statement
					.executeQuery("SELECT S.SCHOOLID, S.SCHOOLNAME, S.LONGITUDE, S.LATITUDE FROM REGIONALS R"
							+ " INNER JOIN SCHOOLS S ON S.SCHOOLID = R.HOSTSCHOOLID"
							+ " INNER JOIN SEMISTATES ST ON ST.HOSTSCHOOLID = '" + parentSemiStateId + "'"
							+ " WHERE R.TOURNAMENTID = '" + tournamentId + "'"
							+ " AND R.PARENTSEMISTATEID = ST.SEMISTATEID");
			// Read all results
			while (resultSet.next()) {
				School school = new School();
				school.schoolName = resultSet.getString("SchoolName");
				school.schoolId = resultSet.getString("SchoolID");
				school.longitude = resultSet.getString("Longitude");
				school.latitude = resultSet.getString("Latitude");
				schools.add(school);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// Close the connections and such
			try {
				resultSet.close();
				statement.close();
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return schools;
	}

	// Returns all Tournaments from the database
	public List<School> getSemiStateHosts(String tournamentId) {
		// The connection
		Connection connection = null;
		// The results from the database
		ResultSet resultSet = null;
		// The query statement
		Statement statement = null;
		// An empty schoolsList
		List<School> schools = new ArrayList<School>();
		// Must wrap in a try/catch
		try {
			// Try to import this into the class
			Class.forName("org.hsqldb.jdbcDriver");
			// Open a connection on the SitesDB
			connection = DriverManager.getConnection("jdbc:hsqldb:file:TournamentDB", "SA", "");
			// Create a new SQL Statement
			statement = connection.createStatement();
			// Execute a query and return the result
			resultSet = statement
					.executeQuery("SELECT S.SCHOOLID, S.SCHOOLNAME, S.LONGITUDE, S.LATITUDE FROM SEMISTATES SS"
							+ " INNER JOIN SCHOOLS S ON S.SCHOOLID = SS.HOSTSCHOOLID" + " WHERE SS.TOURNAMENTID = '"
							+ tournamentId + "'");
			// Read all results
			while (resultSet.next()) {
				School school = new School();
				school.schoolName = resultSet.getString("SchoolName");
				school.schoolId = resultSet.getString("SchoolID");
				school.longitude = resultSet.getString("Longitude");
				school.latitude = resultSet.getString("Latitude");
				schools.add(school);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// Close the connections and such
			try {
				resultSet.close();
				statement.close();
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return schools;
	}

	// Returns all Tournament SemiStates from the database
	public List<SemiState> getTournamentSemiStates(String tournamentId) {
		// The connection
		Connection connection = null;
		// The results from the database
		ResultSet resultSet = null;
		// The query statement
		Statement statement = null;
		// An empty schoolsList
		List<SemiState> semiStates = new ArrayList<SemiState>();
		// Must wrap in a try/catch
		try {
			// Try to import this into the class
			Class.forName("org.hsqldb.jdbcDriver");
			// Open a connection on the SitesDB
			connection = DriverManager.getConnection("jdbc:hsqldb:file:TournamentDB", "SA", "");
			// Create a new SQL Statement
			statement = connection.createStatement();
			// Execute a query and return the result
			resultSet = statement
					.executeQuery("SELECT * FROM SEMISTATES" + " WHERE TOURNAMENTID = '" + tournamentId + "'");
			// Read all results
			while (resultSet.next()) {
				SemiState ss = new SemiState();
				ss.semiStateId = resultSet.getString("semiStateId");
				ss.hostSchoolId = resultSet.getString("hostSchoolId");
				ss.tournamentId = resultSet.getString("tournamentId");
				semiStates.add(ss);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// Close the connections and such
			try {
				resultSet.close();
				statement.close();
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return semiStates;
	}

	// Returns all Tournament SemiStates from the database
	public List<Regional> getTournamentRegionals(String tournamentId, String semiStateId) {
		// The connection
		Connection connection = null;
		// The results from the database
		ResultSet resultSet = null;
		// The query statement
		Statement statement = null;
		// An empty schoolsList
		List<Regional> regionals = new ArrayList<Regional>();
		// Must wrap in a try/catch
		try {
			// Try to import this into the class
			Class.forName("org.hsqldb.jdbcDriver");
			// Open a connection on the SitesDB
			connection = DriverManager.getConnection("jdbc:hsqldb:file:TournamentDB", "SA", "");
			// Create a new SQL Statement
			statement = connection.createStatement();
			// Execute a query and return the result
			resultSet = statement.executeQuery("SELECT * FROM REGIONALS" + " WHERE TOURNAMENTID = '" + tournamentId
					+ "'" + " AND PARENTSEMISTATEID = '" + semiStateId + "'");
			// Read all results
			while (resultSet.next()) {
				Regional r = new Regional();
				r.regionalId = resultSet.getString("regionalId");
				r.hostSchoolId = resultSet.getString("hostSchoolId");
				r.tournamentId = resultSet.getString("tournamentId");
				r.parentSemiStateId = resultSet.getString("parentSemiStateId");
				regionals.add(r);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// Close the connections and such
			try {
				resultSet.close();
				statement.close();
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return regionals;
	}

	// Returns all Tournament SemiStates from the database
	public List<Sectional> getTournamentSectionals(String tournamentId, String regionalId) {
		// The connection
		Connection connection = null;
		// The results from the database
		ResultSet resultSet = null;
		// The query statement
		Statement statement = null;
		// An empty schoolsList
		List<Sectional> sectionals = new ArrayList<Sectional>();
		// Must wrap in a try/catch
		try {
			// Try to import this into the class
			Class.forName("org.hsqldb.jdbcDriver");
			// Open a connection on the SitesDB
			connection = DriverManager.getConnection("jdbc:hsqldb:file:TournamentDB", "SA", "");
			// Create a new SQL Statement
			statement = connection.createStatement();
			// Execute a query and return the result
			resultSet = statement.executeQuery("SELECT * FROM SECTIONALS" + " WHERE TOURNAMENTID = '" + tournamentId
					+ "'" + " AND PARENTREGIONALID = '" + regionalId + "'");
			// Read all results
			while (resultSet.next()) {
				Sectional s = new Sectional();
				s.sectionalId = resultSet.getString("sectionalId");
				s.hostSchoolId = resultSet.getString("hostSchoolId");
				s.tournamentId = resultSet.getString("tournamentId");
				s.parentRegionalId = resultSet.getString("parentRegionalId");
				sectionals.add(s);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// Close the connections and such
			try {
				resultSet.close();
				statement.close();
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return sectionals;
	}

	// Returns all Tournament SemiStates from the database
	public List<SchoolSectional> getTournamentSchoolSectionals(String sectionalId) {
		// The connection
		Connection connection = null;
		// The results from the database
		ResultSet resultSet = null;
		// The query statement
		Statement statement = null;
		// An empty schoolsList
		List<SchoolSectional> ss = new ArrayList<SchoolSectional>();
		// Must wrap in a try/catch
		try {
			// Try to import this into the class
			Class.forName("org.hsqldb.jdbcDriver");
			// Open a connection on the SitesDB
			connection = DriverManager.getConnection("jdbc:hsqldb:file:TournamentDB", "SA", "");
			// Create a new SQL Statement
			statement = connection.createStatement();
			// Execute a query and return the result
			resultSet = statement
					.executeQuery("SELECT * FROM SCHOOLSECTIONALS" + " WHERE SECTIONALID = '" + sectionalId + "'");
			// Read all results
			while (resultSet.next()) {
				SchoolSectional s = new SchoolSectional();
				s.sectionalId = resultSet.getString("sectionalId");
				s.schoolId = resultSet.getString("schoolId");
				ss.add(s);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// Close the connections and such
			try {
				resultSet.close();
				statement.close();
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return ss;
	}

	public boolean saveSchoolAverageDistances(String schoolId, String average) {
		// The connection
		Connection connection = null;
		// The results from the database
		boolean resultSet = false;
		// The query statement
		Statement statement = null;
		// Must wrap in a try/catch
		try {
			// Try to import this into the class
			Class.forName("org.hsqldb.jdbcDriver");
			// Open a connection on the SitesDB
			connection = DriverManager.getConnection("jdbc:hsqldb:file:TournamentDB", "SA", "");
			// Create a new SQL Statement
			statement = connection.createStatement();
			// Execute a query and return the result
			resultSet = statement.execute("UPDATE SCHOOLS" + " SET AVERAGEDISTANCE = '" + average + " km'"
					+ " WHERE SCHOOLID = '" + schoolId + "'");

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// Close the connections and such
			try {
				statement.close();
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return resultSet;
	}
	
	public boolean saveHostSchool(String schoolId, String tournamentId) {
		// The connection
		Connection connection = null;
		// The results from the database
		boolean resultSet = false;
		// The query statement
		Statement statement = null;
		// Must wrap in a try/catch
		try {
			// Try to import this into the class
			Class.forName("org.hsqldb.jdbcDriver");
			// Open a connection on the SitesDB
			connection = DriverManager.getConnection("jdbc:hsqldb:file:TournamentDB", "SA", "");
			// Create a new SQL Statement
			statement = connection.createStatement();
			// Execute a query and return the result
			resultSet = statement.execute("INSERT INTO HOSTSCHOOLS(SCHOOLID, TOURNAMENTID)"
					+ " VALUES('"+schoolId+"','"+tournamentId+"')");

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// Close the connections and such
			try {
				statement.close();
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return resultSet;
	}
	
	public boolean saveReplacementHost(String hostId, String newHostId, String tournamentId, String selectedStructure) {
		// The connection
		Connection connection = null;
		// The results from the database
		boolean resultSet = false;
		// The query statement
		Statement statement = null;
		// Must wrap in a try/catch
		try {
			// Try to import this into the class
			Class.forName("org.hsqldb.jdbcDriver");
			// Open a connection on the SitesDB
			connection = DriverManager.getConnection("jdbc:hsqldb:file:TournamentDB", "SA", "");
			// Create a new SQL Statement
			statement = connection.createStatement();
			// Execute a query and return the result
			resultSet = statement.execute("UPDATE "+selectedStructure+" SET HOSTSCHOOLID = '"+newHostId+"'"
					+ " WHERE HOSTSCHOOLID = '"+hostId+"' AND TOURNAMENTID = '"+tournamentId+"'");

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// Close the connections and such
			try {
				statement.close();
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return resultSet;
	}

	public boolean copyAndSaveTournamentByTournamentId(String oldTournamentId, String newTournamentName) {
		try {
			String tempSemiStateId = "";
			String tempRegionalId = "";
			String tempSectionalId = "";
			Tournament t = getTournament(oldTournamentId);
			t.tournamentName = newTournamentName;
			String newTournamentId = saveTournament(t);
			
			List<School> hostSchools = getHostSchools(oldTournamentId);
			for(int q = 0, qq = hostSchools.size(); q<qq; q++) {
				School oldHost = hostSchools.get(q);
				saveHostSchool(oldHost.schoolId, newTournamentId);
			}

			List<SemiState> semiStates = getTournamentSemiStates(oldTournamentId);
			for (int i = 0, ii = semiStates.size(); i < ii; i++) {
				// Populate Regionals of list using original Regional ID
				List<Regional> regionals = getTournamentRegionals(oldTournamentId, semiStates.get(i).semiStateId);

				// Save new SemiState
				SemiState tempSs = semiStates.get(i);
				tempSs.tournamentId = newTournamentId;
				tempSemiStateId = saveSemiState(tempSs);

				// Repeat same step with Regionals
				for (int j = 0, jj = regionals.size(); j < jj; j++) {
					List<Sectional> sectionals = getTournamentSectionals(oldTournamentId, regionals.get(j).regionalId);

					// Save new Regional
					Regional tempR = regionals.get(j);
					tempR.tournamentId = newTournamentId;
					tempR.parentSemiStateId = tempSemiStateId;
					tempRegionalId = saveRegional(tempR);

					// Repeat same step with Sectionals
					for (int k = 0, kk = sectionals.size(); k < kk; k++) {
						List<SchoolSectional> schoolSectionals = getTournamentSchoolSectionals(sectionals.get(k).sectionalId);

						// Save Sectional
						Sectional tempS = sectionals.get(k);
						tempS.tournamentId = newTournamentId.toString();
						tempS.parentRegionalId = tempRegionalId.toString();
						tempSectionalId = saveSectional(tempS);

						// Final loop lol
						for (int l = 0, ll = schoolSectionals.size(); l < ll; l++) {
							SchoolSectional tempSl = schoolSectionals.get(l);
							tempSl.sectionalId = tempSectionalId.toString();
							saveSchoolSectional(schoolSectionals.get(l));
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	public boolean saveSchoolSectional(SchoolSectional ss) {
		// The connection
		Connection connection = null;
		// The results from the database
		boolean resultSet = false;
		// The query statement
		Statement statement = null;
		// Must wrap in a try/catch
		try {
			// Try to import this into the class
			Class.forName("org.hsqldb.jdbcDriver");
			// Open a connection on the SitesDB
			connection = DriverManager.getConnection("jdbc:hsqldb:file:TournamentDB", "SA", "");
			// Create a new SQL Statement
			statement = connection.createStatement();
			// Execute a query and return the result
			resultSet = statement.execute("INSERT INTO SCHOOLSECTIONALS(SCHOOLID, SECTIONALID)" + " VALUES('"
					+ ss.schoolId + "'," + "'" + ss.sectionalId + "')");

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// Close the connections and such
			try {
				statement.close();
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return resultSet;
	}

	public String saveSectional(Sectional s) {
		// The connection
		Connection connection = null;
		// The new UUID
		String sectionalId = UUID.randomUUID().toString();
		// The results from the database
		// boolean resultSet = false;
		// The query statement
		Statement statement = null;
		// Must wrap in a try/catch
		try {
			// Try to import this into the class
			Class.forName("org.hsqldb.jdbcDriver");
			// Open a connection on the SitesDB
			connection = DriverManager.getConnection("jdbc:hsqldb:file:TournamentDB", "SA", "");
			// Create a new SQL Statement
			statement = connection.createStatement();
			// Execute a query and return the result
			statement.execute("INSERT INTO SECTIONALS(SECTIONALID, HOSTSCHOOLID, TOURNAMENTID, PARENTREGIONALID)"
					+ " VALUES('" + sectionalId + "'," + "'" + s.hostSchoolId + "'," + "'" + s.tournamentId + "'," + "'"
					+ s.parentRegionalId + "')");

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// Close the connections and such
			try {
				statement.close();
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return sectionalId;
	}

	public String saveRegional(Regional r) {
		// The connection
		Connection connection = null;
		// The new UUID
		String regionalId = UUID.randomUUID().toString();
		// The results from the database
		// boolean resultSet = false;
		// The query statement
		Statement statement = null;
		// Must wrap in a try/catch
		try {
			// Try to import this into the class
			Class.forName("org.hsqldb.jdbcDriver");
			// Open a connection on the SitesDB
			connection = DriverManager.getConnection("jdbc:hsqldb:file:TournamentDB", "SA", "");
			// Create a new SQL Statement
			statement = connection.createStatement();
			// Execute a query and return the result
			statement.execute("INSERT INTO REGIONALS(REGIONALID, HOSTSCHOOLID, TOURNAMENTID, PARENTSEMISTATEID)"
					+ " VALUES('" + regionalId + "'," + "'" + r.hostSchoolId + "'," + "'" + r.tournamentId + "'," + "'"
					+ r.parentSemiStateId + "')");

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// Close the connections and such
			try {
				statement.close();
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return regionalId;
	}

	public String saveSemiState(SemiState ss) {
		// The connection
		Connection connection = null;
		// The new UUID
		String semiStateId = UUID.randomUUID().toString();
		// The results from the database
		// boolean resultSet = false;
		// The query statement
		Statement statement = null;
		// Must wrap in a try/catch
		try {
			// Try to import this into the class
			Class.forName("org.hsqldb.jdbcDriver");
			// Open a connection on the SitesDB
			connection = DriverManager.getConnection("jdbc:hsqldb:file:TournamentDB", "SA", "");
			// Create a new SQL Statement
			statement = connection.createStatement();
			// Execute a query and return the result
			statement.execute("INSERT INTO SEMISTATES(SEMISTATEID, HOSTSCHOOLID, TOURNAMENTID)" + " VALUES('"
					+ semiStateId + "'," + "'" + ss.hostSchoolId + "'," + "'" + ss.tournamentId + "')");

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// Close the connections and such
			try {
				statement.close();
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return semiStateId;
	}
	
	public boolean replaceHost(String hostId, String replacementHostId, String tournamentId) {
		// The connection
		Connection connection = null;
		// The results from the database
		boolean resultSet = false;
		// The query statement
		Statement statement = null;
		// Must wrap in a try/catch
		try {
			// Try to import this into the class
			Class.forName("org.hsqldb.jdbcDriver");
			// Open a connection on the SitesDB
			connection = DriverManager.getConnection("jdbc:hsqldb:file:TournamentDB", "SA", "");
			// Create a new SQL Statement
			statement = connection.createStatement();
			// Execute a query and return the result
			resultSet = statement.execute("DELETE FROM HOSTSCHOOLS WHERE SCHOOLID = '"+hostId+"' AND TOURNAMENTID = '"+tournamentId+"'"
					+ " UPDATE SCHOOLS SET ISHOST = FALSE WHERE SCHOOLID = '"+hostId+"'"
					+ " UPDATE SECTIONALS SET HOSTSCHOOLID = '"+replacementHostId+"' WHERE HOSTSCHOOLID = '"+hostId+"' AND TOURNAMENTID = '"+tournamentId+"'"
					+ " UPDATE REGIONALS SET HOSTSCHOOLID = '"+replacementHostId+"' WHERE HOSTSCHOOLID = '"+hostId+"' AND TOURNAMENTID = '"+tournamentId+"'"
					+ " UPDATE SEMISTATES SET HOSTSCHOOLID = '"+replacementHostId+"' WHERE HOSTSCHOOLID = '"+hostId+"' AND TOURNAMENTID = '"+tournamentId+"'");

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// Close the connections and such
			try {
				statement.close();
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return resultSet;
	}
	
	public boolean deleteTournament(String tournamentId) {
		// The connection
		Connection connection = null;
		// The results from the database
		boolean resultSet = false;
		// The query statement
		Statement statement = null;
		// Must wrap in a try/catch
		try {
			// Try to import this into the class
			Class.forName("org.hsqldb.jdbcDriver");
			// Open a connection on the SitesDB
			connection = DriverManager.getConnection("jdbc:hsqldb:file:TournamentDB", "SA", "");
			// Create a new SQL Statement
			statement = connection.createStatement();
			// Execute a query and return the result
			resultSet = statement.execute("DELETE FROM TOURNAMENTS WHERE TOURNAMENTID = '"+tournamentId+"'"
					+ " DELETE FROM HOSTSCHOOLS WHERE TOURNAMENTID = '"+tournamentId+"'"
					+ " DELETE FROM SECTIONALS WHERE TOURNAMENTID = '"+tournamentId+"'"
					+ " DELETE FROM REGIONALS WHERE TOURNAMENTID ='"+tournamentId+"'"
					+ " DELETE FROM SEMISTATES WHERE TOURNAMENTID ='"+tournamentId+"'");

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// Close the connections and such
			try {
				statement.close();
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return resultSet;
	}
	
	public boolean removeHost(String hostId, String tournamentId) {
		// The connection
		Connection connection = null;
		// The results from the database
		boolean resultSet = false;
		// The query statement
		Statement statement = null;
		// Must wrap in a try/catch
		try {
			// Try to import this into the class
			Class.forName("org.hsqldb.jdbcDriver");
			// Open a connection on the SitesDB
			connection = DriverManager.getConnection("jdbc:hsqldb:file:TournamentDB", "SA", "");
			// Create a new SQL Statement
			statement = connection.createStatement();
			// Execute a query and return the result
			resultSet = statement.execute("DELETE FROM HOSTSCHOOLS WHERE SCHOOLID = '"+hostId+"' AND TOURNAMENTID = '"+tournamentId+"'"
					+ " UPDATE SCHOOLS SET ISHOST = FALSE WHERE SCHOOLID ='"+hostId+"'");

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// Close the connections and such
			try {
				statement.close();
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return resultSet;
	}
	
	public boolean checkIfHostHasSchools(String hostId, String tournamentId) throws SQLException {
		// The connection
		Connection connection = null;
		// The results from the database
		ResultSet resultSet = null;
		boolean found = false;
		// The query statement
		Statement statement = null;
		// Must wrap in a try/catch
		try {
			// Try to import this into the class
			Class.forName("org.hsqldb.jdbcDriver");
			// Open a connection on the SitesDB
			connection = DriverManager.getConnection("jdbc:hsqldb:file:TournamentDB", "SA", "");
			// Create a new SQL Statement
			statement = connection.createStatement();
			// Execute a query and return the result
			resultSet = statement.executeQuery("SELECT * FROM SECTIONALS WHERE HOSTSCHOOLID = '"+hostId+"' AND TOURNAMENTID = '"+tournamentId+"'");
			
			found = resultSet.next();
			if(!found) {
				resultSet = statement.executeQuery("SELECT * FROM REGIONALS WHERE HOSTSCHOOLID = '"+hostId+"' AND TOURNAMENTID = '"+tournamentId+"'");
				found = resultSet.next();
			}
			
			if(!found) {
				resultSet = statement.executeQuery("SELECT * FROM SEMISTATES WHERE HOSTSCHOOLID = '"+hostId+"' AND TOURNAMENTID = '"+tournamentId+"'");
				found = resultSet.next();
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// Close the connections and such
			try {
				statement.close();
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return found;
	}

	public String saveTournament(Tournament t) {
		// The connection
		Connection connection = null;
		// The new UUID
		String tournamentId = UUID.randomUUID().toString();
		System.out.println(tournamentId);
		// The results from the database
		// boolean resultSet = false;
		// The query statement
		Statement statement = null;
		// Must wrap in a try/catch
		try {
			// Try to import this into the class
			Class.forName("org.hsqldb.jdbcDriver");
			// Open a connection on the SitesDB
			connection = DriverManager.getConnection("jdbc:hsqldb:file:TournamentDB", "SA", "");
			// Create a new SQL Statement
			statement = connection.createStatement();
			// Execute a query and return the result
			statement.execute(
					"INSERT INTO TOURNAMENTS(TOURNAMENTID, TOURNAMENTNAME, TOURNAMENTFULLADDRESS, LATITUDE, LONGITUDE)"
							+ " VALUES('" + tournamentId + "'," + "'" + t.tournamentName + "'," + "'"
							+ t.tournamentFullAddress + "'," + "'" + t.latitude + "'," + "'" + t.longitude + "')");

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// Close the connections and such
			try {
				statement.close();
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return tournamentId;
	}
}
