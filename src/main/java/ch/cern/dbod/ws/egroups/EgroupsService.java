/*
 * Copyright (C) 2015, CERN
 * This software is distributed under the terms of the GNU General Public
 * Licence version 3 (GPL Version 3), copied verbatim in the file "LICENSE".
 * In applying this license, CERN does not waive the privileges and immunities
 * granted to it by virtue of its status as Intergovernmental Organization
 * or submit itself to any jurisdiction.
 */

package ch.cern.dbod.ws.egroups;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.1.6 in JDK 6
 * Generated source version: 2.1
 * 
 */
@WebService(name = "EgroupsService", targetNamespace = "https://foundservices.cern.ch/ws/egroups/v1/EgroupsWebService")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
@XmlSeeAlso({
    ObjectFactory.class
})
public interface EgroupsService {


    /**
     * 
     * @param updateEmailPropertiesRequest
     * @return
     *     returns ch.cern.dbod.ws.egroups.UpdateEmailPropertiesResponse
     */
    @WebMethod(operationName = "UpdateEmailProperties")
    @WebResult(name = "UpdateEmailPropertiesResponse", targetNamespace = "https://foundservices.cern.ch/ws/egroups/v1/schema/EgroupsServicesSchema", partName = "UpdateEmailPropertiesResponse")
    public UpdateEmailPropertiesResponse updateEmailProperties(
        @WebParam(name = "UpdateEmailPropertiesRequest", targetNamespace = "https://foundservices.cern.ch/ws/egroups/v1/schema/EgroupsServicesSchema", partName = "UpdateEmailPropertiesRequest")
        UpdateEmailPropertiesRequest updateEmailPropertiesRequest);

    /**
     * 
     * @param addEgroupEmailMembersRequest
     * @return
     *     returns ch.cern.dbod.ws.egroups.AddEgroupEmailMembersResponse
     */
    @WebMethod(operationName = "AddEgroupEmailMembers")
    @WebResult(name = "AddEgroupEmailMembersResponse", targetNamespace = "https://foundservices.cern.ch/ws/egroups/v1/schema/EgroupsServicesSchema", partName = "AddEgroupEmailMembersResponse")
    public AddEgroupEmailMembersResponse addEgroupEmailMembers(
        @WebParam(name = "AddEgroupEmailMembersRequest", targetNamespace = "https://foundservices.cern.ch/ws/egroups/v1/schema/EgroupsServicesSchema", partName = "AddEgroupEmailMembersRequest")
        AddEgroupEmailMembersRequest addEgroupEmailMembersRequest);

    /**
     * 
     * @param removeEgroupEmailMembersRequest
     * @return
     *     returns ch.cern.dbod.ws.egroups.RemoveEgroupEmailMembersResponse
     */
    @WebMethod(operationName = "RemoveEgroupEmailMembers")
    @WebResult(name = "RemoveEgroupEmailMembersResponse", targetNamespace = "https://foundservices.cern.ch/ws/egroups/v1/schema/EgroupsServicesSchema", partName = "RemoveEgroupEmailMembersResponse")
    public RemoveEgroupEmailMembersResponse removeEgroupEmailMembers(
        @WebParam(name = "RemoveEgroupEmailMembersRequest", targetNamespace = "https://foundservices.cern.ch/ws/egroups/v1/schema/EgroupsServicesSchema", partName = "RemoveEgroupEmailMembersRequest")
        RemoveEgroupEmailMembersRequest removeEgroupEmailMembersRequest);

    /**
     * 
     * @param addEgroupMembersRequest
     * @return
     *     returns ch.cern.dbod.ws.egroups.AddEgroupMembersResponse
     */
    @WebMethod(operationName = "AddEgroupMembers")
    @WebResult(name = "AddEgroupMembersResponse", targetNamespace = "https://foundservices.cern.ch/ws/egroups/v1/schema/EgroupsServicesSchema", partName = "AddEgroupMembersResponse")
    public AddEgroupMembersResponse addEgroupMembers(
        @WebParam(name = "AddEgroupMembersRequest", targetNamespace = "https://foundservices.cern.ch/ws/egroups/v1/schema/EgroupsServicesSchema", partName = "AddEgroupMembersRequest")
        AddEgroupMembersRequest addEgroupMembersRequest);

    /**
     * 
     * @param removeEgroupMembersRequest
     * @return
     *     returns ch.cern.dbod.ws.egroups.RemoveEgroupMembersResponse
     */
    @WebMethod(operationName = "RemoveEgroupMembers")
    @WebResult(name = "RemoveEgroupMembersResponse", targetNamespace = "https://foundservices.cern.ch/ws/egroups/v1/schema/EgroupsServicesSchema", partName = "RemoveEgroupMembersResponse")
    public RemoveEgroupMembersResponse removeEgroupMembers(
        @WebParam(name = "RemoveEgroupMembersRequest", targetNamespace = "https://foundservices.cern.ch/ws/egroups/v1/schema/EgroupsServicesSchema", partName = "RemoveEgroupMembersRequest")
        RemoveEgroupMembersRequest removeEgroupMembersRequest);

    /**
     * 
     * @param getEgroupsUserOwnOrManageRequest
     * @return
     *     returns ch.cern.dbod.ws.egroups.GetEgroupsUserOwnOrManageResponse
     */
    @WebMethod(operationName = "GetEgroupsUserOwnOrManage")
    @WebResult(name = "GetEgroupsUserOwnOrManageResponse", targetNamespace = "https://foundservices.cern.ch/ws/egroups/v1/schema/EgroupsServicesSchema", partName = "GetEgroupsUserOwnOrManageResponse")
    public GetEgroupsUserOwnOrManageResponse getEgroupsUserOwnOrManage(
        @WebParam(name = "GetEgroupsUserOwnOrManageRequest", targetNamespace = "https://foundservices.cern.ch/ws/egroups/v1/schema/EgroupsServicesSchema", partName = "GetEgroupsUserOwnOrManageRequest")
        GetEgroupsUserOwnOrManageRequest getEgroupsUserOwnOrManageRequest);

    /**
     * 
     * @param findEgroupByIdRequest
     * @return
     *     returns ch.cern.dbod.ws.egroups.FindEgroupByIdResponse
     */
    @WebMethod(operationName = "FindEgroupById")
    @WebResult(name = "FindEgroupByIdResponse", targetNamespace = "https://foundservices.cern.ch/ws/egroups/v1/schema/EgroupsServicesSchema", partName = "FindEgroupByIdResponse")
    public FindEgroupByIdResponse findEgroupById(
        @WebParam(name = "FindEgroupByIdRequest", targetNamespace = "https://foundservices.cern.ch/ws/egroups/v1/schema/EgroupsServicesSchema", partName = "FindEgroupByIdRequest")
        FindEgroupByIdRequest findEgroupByIdRequest);

    /**
     * 
     * @param findEgroupByNameRequest
     * @return
     *     returns ch.cern.dbod.ws.egroups.FindEgroupByNameResponse
     */
    @WebMethod(operationName = "FindEgroupByName")
    @WebResult(name = "FindEgroupByNameResponse", targetNamespace = "https://foundservices.cern.ch/ws/egroups/v1/schema/EgroupsServicesSchema", partName = "FindEgroupByNameResponse")
    public FindEgroupByNameResponse findEgroupByName(
        @WebParam(name = "FindEgroupByNameRequest", targetNamespace = "https://foundservices.cern.ch/ws/egroups/v1/schema/EgroupsServicesSchema", partName = "FindEgroupByNameRequest")
        FindEgroupByNameRequest findEgroupByNameRequest);

    /**
     * 
     * @param changeExternalEmailAddressRequest
     * @return
     *     returns ch.cern.dbod.ws.egroups.ChangeExternalEmailAddressResponse
     */
    @WebMethod(operationName = "ChangeExternalEmailAddress")
    @WebResult(name = "ChangeExternalEmailAddressResponse", targetNamespace = "https://foundservices.cern.ch/ws/egroups/v1/schema/EgroupsServicesSchema", partName = "ChangeExternalEmailAddressResponse")
    public ChangeExternalEmailAddressResponse changeExternalEmailAddress(
        @WebParam(name = "ChangeExternalEmailAddressRequest", targetNamespace = "https://foundservices.cern.ch/ws/egroups/v1/schema/EgroupsServicesSchema", partName = "ChangeExternalEmailAddressRequest")
        ChangeExternalEmailAddressRequest changeExternalEmailAddressRequest);

    /**
     * 
     * @param deleteEgroupRequest
     * @return
     *     returns ch.cern.dbod.ws.egroups.DeleteEgroupResponse
     */
    @WebMethod(operationName = "DeleteEgroup")
    @WebResult(name = "DeleteEgroupResponse", targetNamespace = "https://foundservices.cern.ch/ws/egroups/v1/schema/EgroupsServicesSchema", partName = "DeleteEgroupResponse")
    public DeleteEgroupResponse deleteEgroup(
        @WebParam(name = "DeleteEgroupRequest", targetNamespace = "https://foundservices.cern.ch/ws/egroups/v1/schema/EgroupsServicesSchema", partName = "DeleteEgroupRequest")
        DeleteEgroupRequest deleteEgroupRequest);

    /**
     * 
     * @param synchronizeEgroupRequest
     * @return
     *     returns ch.cern.dbod.ws.egroups.SynchronizeEgroupResponse
     */
    @WebMethod(operationName = "SynchronizeEgroup")
    @WebResult(name = "SynchronizeEgroupResponse", targetNamespace = "https://foundservices.cern.ch/ws/egroups/v1/schema/EgroupsServicesSchema", partName = "SynchronizeEgroupResponse")
    public SynchronizeEgroupResponse synchronizeEgroup(
        @WebParam(name = "SynchronizeEgroupRequest", targetNamespace = "https://foundservices.cern.ch/ws/egroups/v1/schema/EgroupsServicesSchema", partName = "SynchronizeEgroupRequest")
        SynchronizeEgroupRequest synchronizeEgroupRequest);

}
