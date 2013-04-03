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
package org.openmrs.module.lacollinews.api.db.hibernate;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.openmrs.Patient;
import org.openmrs.PatientIdentifierType;
import org.openmrs.PatientIdentifier;
import org.openmrs.api.db.hibernate.PatientSearchCriteria;
import org.openmrs.module.lacollinews.api.db.LacollineWebServiceDAO;

import java.util.ArrayList;
import java.util.List;

/**
 * It is a default implementation of  {@link LacollineWebServiceDAO}.
 */
public class HibernateLacollineWebServiceDAO implements LacollineWebServiceDAO {
	protected final Log log = LogFactory.getLog(this.getClass());
	
	private SessionFactory sessionFactory;
	
	/**
     * @param sessionFactory the sessionFactory to set
     */
    public void setSessionFactory(SessionFactory sessionFactory) {
	    this.sessionFactory = sessionFactory;
    }
    
	/**
     * @return the sessionFactory
     */
    public SessionFactory getSessionFactory() {
	    return sessionFactory;
    }

    @Override
    public List<Patient> searchPatient(String query, String gender, PatientIdentifierType identifierType) {
        Criteria criteria =null;
        if(identifierType!=null){
            criteria = sessionFactory.getCurrentSession().createCriteria(PatientIdentifier.class);
            criteria.setProjection(Projections.distinct(Property.forName("patient")));
            criteria.add(Restrictions.eq("identifierType", identifierType));
            criteria.add(Restrictions.isNotNull("identifier"));

            Criteria patientCriteria = criteria.createCriteria("patient");
            if(StringUtils.isNotBlank(gender)){
                patientCriteria.add(Restrictions.eq("gender", gender));
            }
            if (StringUtils.isNotBlank(query)) {
                patientCriteria = buildCriteria(query, patientCriteria);
            }
            criteria = patientCriteria;
        }else{
            criteria = sessionFactory.getCurrentSession().createCriteria(Patient.class);
            criteria = buildCriteria(query, criteria);
        }
        List<Patient> patients = new ArrayList<Patient>();
        try{
            patients =(List<Patient>) criteria.list();
        }catch(Exception e){
            log.error(e);
        }
        return patients;
    }

    private Criteria buildCriteria(String query, Criteria criteria) {
        return new PatientSearchCriteria(sessionFactory, criteria).prepareCriteria(query, null, new ArrayList<PatientIdentifierType>(), true, true);
    }
}