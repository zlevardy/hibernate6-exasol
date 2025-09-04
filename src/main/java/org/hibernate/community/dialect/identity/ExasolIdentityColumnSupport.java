package org.hibernate.community.dialect.identity;

import java.sql.Types;
import org.hibernate.MappingException;
import org.hibernate.dialect.identity.IdentityColumnSupportImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A Hibernate 6 Dialect Identity Column Support for EXASOL
 *
 * based on the original version for hibernate v5 by exasol team:
 *    https://github.com/exasol/hibernate-exasol/blob/master/src/main/java/com/exasol/dialect/ExasolIdentityColumnSupport.java
 *
 * reworking for hb v6.2+
 *
 * @author zoltan
 *
 */
public class ExasolIdentityColumnSupport extends IdentityColumnSupportImpl {

	//HB6 style:
	public static final ExasolIdentityColumnSupport INSTANCE = new ExasolIdentityColumnSupport();

	private static final Logger logger = LoggerFactory.getLogger(ExasolIdentityColumnSupport.class);

	@Override
	public boolean hasDataTypeInIdentityColumn() {
		return false;
	}

	@Override
	public boolean supportsIdentityColumns() {
		return true;
	}

	public boolean supportsInsertSelectIdentity() {
		return false;
	}

	@Override
	public String appendIdentitySelectToInsert(String arg0) {
		return arg0;
	}

	@Override
	public String getIdentityColumnString(int type) throws MappingException {
		//MYSQL VERSION:
		//starts with 1, implicitly
		//return "not null auto_increment";

		//HB5:
		//return type==Types.BIGINT ?
	    //        "decimal(36, 0) identity not null" :
	    //        "decimal(19, 0) identity not null";

		//Types.BIGINT = -5

		return type==Types.BIGINT ?
		        "decimal(36) identity" :
		        "decimal(19) identity" ;
	}

	@Override
	/** get last id */
	public String getIdentitySelectString(String table, String column, int type) throws MappingException {
		logger.debug("exasol-identity-col-support-sel-str: table = "+table+", column="+column+", type="+type);

		//generates out of index errors by missing dot (".") in table name: "myschema.atable"
		//return "SELECT COLUMN_IDENTITY FROM EXA_ALL_COLUMNS WHERE COLUMN_NAME='"+column.toUpperCase() +
		//		"' AND COLUMN_SCHEMA='"+table.substring(0, table.indexOf(".")).toUpperCase() +
		//		"' AND COLUMN_TABLE='"+(table.substring(table.indexOf(".")+1)).toUpperCase()+"'";

		//schema may not find by "." from table to avoid out of index crash.
		String schema = ( table.indexOf(".")==-1 ) ? "" : table.substring(0, table.indexOf(".")).toUpperCase();
		logger.debug("exasol-identity-col-support-sel-str: schema="+schema);

		String sql = "SELECT COLUMN_IDENTITY FROM EXA_ALL_COLUMNS WHERE COLUMN_NAME='"+column.toUpperCase() +
				"' AND COLUMN_SCHEMA='"+schema+
				"' AND COLUMN_TABLE='"+(table.substring(table.indexOf(".")+1)).toUpperCase()+"'";
		logger.debug("exasol-identity-col-support-sel-str: sql="+sql);

		return sql;
	}

}
