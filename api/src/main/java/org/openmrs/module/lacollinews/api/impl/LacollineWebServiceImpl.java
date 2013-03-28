/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package org.openmrs.module.lacollinews.api.impl;

import org.apache.commons.lang.StringUtils;
import org.openmrs.Patient;
import org.openmrs.PatientIdentifierType;
import org.openmrs.api.context.Context;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.module.lacollinews.LacollinewsProperties;
import org.openmrs.module.lacollinews.api.LacollineWebService;
import org.openmrs.module.lacollinews.api.db.LacollineWebServiceDAO;

import java.util.ArrayList;
import java.util.List;

/**
 * It is a default implementation of {@link org.openmrs.module.lacollinews.api.LacollineWebService}.
 */
public class LacollineWebServiceImpl extends BaseOpenmrsService implements LacollineWebService {
	
	protected final Log log = LogFactory.getLog(this.getClass());
	
	private LacollineWebServiceDAO dao;
	
	/**
     * @param dao the dao to set
     */
    public void setDao(LacollineWebServiceDAO dao) {
	    this.dao = dao;
    }
    
    /**
     * @return the dao
     */
    public LacollineWebServiceDAO getDao() {
	    return dao;
    }

    @Override
    public List<Patient> searchPatientById(String id) {
        List<Patient> patientList = null;
        if(StringUtils.isNotBlank(id)){
            List<PatientIdentifierType> identifierTypes = new ArrayList<PatientIdentifierType>();
            PatientIdentifierType preferredIdentifierType = LacollinewsProperties.getPrimaryIdentifier();
            if(preferredIdentifierType!=null){
                identifierTypes.add(preferredIdentifierType);
                patientList = Context.getPatientService().getPatients(null, id, identifierTypes, true);
            }
        }
        return patientList;
    }

    @Override
    public List<Patient> searchPatientByName(String name) {
        List<Patient> patientList = null;
        if(StringUtils.isNotBlank(name)){
            patientList = dao.searchPatient(name, LacollinewsProperties.getPrimaryIdentifier());
        }
        return patientList;
    }
}