/*
   Licensed to the Apache Software Foundation (ASF) under one
   or more contributor license agreements.  See the NOTICE file
   distributed with this work for additional information
   regarding copyright ownership.  The ASF licenses this file
   to you under the Apache License, Version 2.0 (the
   "License"); you may not use this file except in compliance
   with the License.  You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing,
   software distributed under the License is distributed on an
   "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
   KIND, either express or implied.  See the License for the
   specific language governing permissions and limitations
   under the License.    
 */

package org.opentides.persistence.hibernate;

import org.apache.log4j.Logger;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.opentides.util.StringUtil;
import org.opentides.util.TenantContextHolder;
import org.springframework.beans.factory.annotation.Value;

/**
 * For supporting multi-tenancy applications. The current tenant is retrieved
 * from spring security context because the tenant access will depend on the
 * user logged-in.
 * 
 * @author allantan
 *
 */
public class MultiTenantIdentifierResolver implements
		CurrentTenantIdentifierResolver {

	private static final Logger _log = Logger
			.getLogger(MultiTenantIdentifierResolver.class);

	@Value("${database.default_schema}")
	private String defaultSchema;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.hibernate.context.spi.CurrentTenantIdentifierResolver#
	 * resolveCurrentTenantIdentifier()
	 */
	@Override
	public String resolveCurrentTenantIdentifier() {
		// Check if the schema name was saved by the session filter
		final String schemaName = TenantContextHolder.getSchemaName();
		if (!StringUtil.isEmpty(schemaName)) {
			_log.debug("Using thread local schema [" + schemaName
					+ "] for schema.");
			return schemaName;
		}

		// use default tenant
		_log.debug("Using default schema [" + defaultSchema + "] for schema.");
		return defaultSchema;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.hibernate.context.spi.CurrentTenantIdentifierResolver#
	 * validateExistingCurrentSessions()
	 */
	@Override
	public boolean validateExistingCurrentSessions() {
		return false;
	}

}
