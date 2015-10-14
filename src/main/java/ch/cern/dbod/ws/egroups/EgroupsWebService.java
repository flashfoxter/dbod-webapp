/*
 * Copyright (C) 2015, CERN
 * This software is distributed under the terms of the GNU General Public
 * Licence version 3 (GPL Version 3), copied verbatim in the file "LICENSE".
 * In applying this license, CERN does not waive the privileges and immunities
 * granted to it by virtue of its status as Intergovernmental Organization
 * or submit itself to any jurisdiction.
 */

package ch.cern.dbod.ws.egroups;

import java.net.URL;
import java.util.logging.Logger;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.1.6 in JDK 6
 * Generated source version: 2.1
 * 
 */
@WebServiceClient(name = "EgroupsWebService", targetNamespace = "https://foundservices.cern.ch/ws/egroups/v1/EgroupsWebService", wsdlLocation = "classpath:/ch/cern/dbod/ws/wsdl/egroups.wsdl")
public class EgroupsWebService
    extends Service
{

    private final static URL EGROUPSWEBSERVICE_WSDL_LOCATION;
    private final static Logger logger = Logger.getLogger(ch.cern.dbod.ws.egroups.EgroupsWebService.class.getName());

    static {
        EGROUPSWEBSERVICE_WSDL_LOCATION = ch.cern.dbod.ws.egroups.EgroupsWebService.class.getResource("/ch/cern/dbod/ws/wsdl/egroups.wsdl");
    }

    public EgroupsWebService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public EgroupsWebService() {
        super(EGROUPSWEBSERVICE_WSDL_LOCATION, new QName("https://foundservices.cern.ch/ws/egroups/v1/EgroupsWebService", "EgroupsWebService"));
    }

    /**
     * 
     * @return
     *     returns EgroupsService
     */
    @WebEndpoint(name = "EgroupsServiceSoap11")
    public EgroupsService getEgroupsServiceSoap11() {
        return super.getPort(new QName("https://foundservices.cern.ch/ws/egroups/v1/EgroupsWebService", "EgroupsServiceSoap11"), EgroupsService.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns EgroupsService
     */
    @WebEndpoint(name = "EgroupsServiceSoap11")
    public EgroupsService getEgroupsServiceSoap11(WebServiceFeature... features) {
        return super.getPort(new QName("https://foundservices.cern.ch/ws/egroups/v1/EgroupsWebService", "EgroupsServiceSoap11"), EgroupsService.class, features);
    }

}
