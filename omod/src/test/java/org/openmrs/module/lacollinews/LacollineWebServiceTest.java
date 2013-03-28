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
package org.openmrs.module.lacollinews;

import org.junit.Test;
import org.openmrs.api.context.Context;
import org.openmrs.module.lacollinews.api.LacollineWebService;
import org.openmrs.web.test.BaseModuleWebContextSensitiveTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.ServletWebRequest;

import java.lang.Exception;

import static org.junit.Assert.assertNotNull;

public class LacollineWebServiceTest extends BaseModuleWebContextSensitiveTest {
	
	@Test
	public void shouldSetupContext() {
		assertNotNull(Context.getService(LacollineWebService.class));
	}

    @Test
    public void shouldFindPatientByID() throws Exception{
        //newRequest(RequestMethod.GET, requestURI, parameters);
        MockHttpServletRequest request = new MockHttpServletRequest(RequestMethod.GET.toString(), "/rest/v1/patient/?id=2F14NR" );
        request.addHeader("content-type", "application/json");

        MockHttpServletResponse response = new MockHttpServletResponse();

        ServletWebRequest webRequest = new ServletWebRequest(request, response);

        assertNotNull(request);
    }
}
