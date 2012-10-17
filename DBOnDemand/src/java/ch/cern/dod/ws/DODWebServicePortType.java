
package ch.cern.dod.ws;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.1.6 in JDK 6
 * Generated source version: 2.1
 * 
 */
@WebService(name = "DODWebServicePortType", targetNamespace = "https://syscontrol.cern.ch/Dod/")
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface DODWebServicePortType {


    /**
     * 
     * @param instance
     * @return
     *     returns java.lang.String
     */
    @WebMethod(operationName = "GetSnapshots", action = "https://syscontrol.cern.ch/Dod/#GetSnapshots")
    @WebResult(name = "snapshots", partName = "snapshots")
    public String getSnapshots(
        @WebParam(name = "instance", partName = "instance")
        String instance);

    /**
     * 
     * @param instance
     * @return
     *     returns java.lang.String
     */
    @WebMethod(operationName = "GetSlowLogs", action = "https://syscontrol.cern.ch/Dod/#GetSlowLogs")
    @WebResult(name = "slowLogs", partName = "slowLogs")
    public String getSlowLogs(
        @WebParam(name = "instance", partName = "instance")
        String instance);

    /**
     * 
     * @param instance
     * @return
     *     returns java.lang.String
     */
    @WebMethod(operationName = "GetOracleLogs", action = "https://syscontrol.cern.ch/Dod/#GetOracleLogs")
    @WebResult(name = "logs", partName = "logs")
    public String getOracleLogs(
        @WebParam(name = "instance", partName = "instance")
        String instance);

    /**
     * 
     * @param filePath
     * @param instance
     * @return
     *     returns java.lang.String
     */
    @WebMethod(operationName = "GetFile", action = "https://syscontrol.cern.ch/Dod/#GetFile")
    @WebResult(name = "file", partName = "file")
    public String getFile(
        @WebParam(name = "instance", partName = "instance")
        String instance,
        @WebParam(name = "filePath", partName = "filePath")
        String filePath);

    /**
     * 
     * @param filePath
     * @param instance
     * @return
     *     returns java.lang.String
     */
    @WebMethod(operationName = "ServeFile", action = "https://syscontrol.cern.ch/Dod/#ServeFile")
    @WebResult(name = "url", partName = "url")
    public String serveFile(
        @WebParam(name = "instance", partName = "instance")
        String instance,
        @WebParam(name = "filePath", partName = "filePath")
        String filePath);

    /**
     * 
     * @param param
     * @param instance
     * @return
     *     returns java.lang.String
     */
    @WebMethod(operationName = "GetParam", action = "https://syscontrol.cern.ch/Dod/#GetParam")
    @WebResult(name = "paramValue", partName = "paramValue")
    public String getParam(
        @WebParam(name = "instance", partName = "instance")
        String instance,
        @WebParam(name = "param", partName = "param")
        String param);

}
