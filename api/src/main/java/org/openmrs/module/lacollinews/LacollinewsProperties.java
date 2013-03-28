package org.openmrs.module.lacollinews;


import org.apache.commons.lang.StringUtils;
import org.openmrs.PatientIdentifierType;
import org.openmrs.api.context.Context;

public class LacollinewsProperties {
    public static final String ZL_EMR_ID = "lacollinews.primaryIdentifierType";


    public static final PatientIdentifierType getPrimaryIdentifier() {
        PatientIdentifierType identifierType = null;
        String propertyValue = Context.getAdministrationService().getGlobalProperty(ZL_EMR_ID);
        if (StringUtils.isNotBlank(propertyValue)) {
            identifierType = Context.getPatientService().getPatientIdentifierTypeByUuid(propertyValue);
            if (identifierType == null) {
                try {
                    identifierType = Context.getPatientService().getPatientIdentifierType(Integer.parseInt(propertyValue));
                }catch (Exception e) {}
            }
         }
        return identifierType;
    }
}
