package org.openmrs.module.lacollinews;

import org.openmrs.Patient;
import org.openmrs.module.webservices.rest.web.RequestContext;
import org.openmrs.module.webservices.rest.web.RestConstants;
import org.openmrs.module.webservices.rest.web.resource.api.PageableResult;
import org.openmrs.module.webservices.rest.web.resource.api.SearchConfig;
import org.openmrs.module.webservices.rest.web.resource.api.SearchHandler;
import org.openmrs.module.webservices.rest.web.resource.api.SearchQuery;
import org.openmrs.module.webservices.rest.web.resource.impl.NeedsPaging;
import org.openmrs.module.webservices.rest.web.response.ResponseException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RegisteredPatientSearchHandler implements SearchHandler {

    private final SearchQuery byName = new SearchQuery.Builder("Allows you to find patient by id, name, age, and gender")
            .withRequiredParameters("name").withOptionalParameters("age", "gender").build();
    private final SearchQuery byId = new SearchQuery.Builder("Allows you to find patient by id, name, age, and gender")
            .withRequiredParameters("id").build();
    private final SearchConfig searchConfig = new SearchConfig("registered", RestConstants.VERSION_1 + "/patient",
            "1.9.*", Arrays.asList(byName, byId));

    @Override
    public SearchConfig getSearchConfig() {
        return searchConfig;
    }

    @Override
    public PageableResult search(RequestContext requestContext) throws ResponseException {
        List<Patient> results = new ArrayList<Patient>();
        return new NeedsPaging<Patient>(results, requestContext);
    }
}
