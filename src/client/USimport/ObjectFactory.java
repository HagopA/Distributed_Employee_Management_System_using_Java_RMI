
package client.USimport;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the client.USimport package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _SearchIfRecordExistsResponse_QNAME = new QName("http://server/", "searchIfRecordExistsResponse");
    private final static QName _GetLocalCounts_QNAME = new QName("http://server/", "getLocalCounts");
    private final static QName _TransferRecordsResponse_QNAME = new QName("http://server/", "transferRecordsResponse");
    private final static QName _CreateERecordResponse_QNAME = new QName("http://server/", "createERecordResponse");
    private final static QName _EditRecordResponse_QNAME = new QName("http://server/", "editRecordResponse");
    private final static QName _CreateERecord_QNAME = new QName("http://server/", "createERecord");
    private final static QName _GetLocalCountsResponse_QNAME = new QName("http://server/", "getLocalCountsResponse");
    private final static QName _CreateMRecord_QNAME = new QName("http://server/", "createMRecord");
    private final static QName _SearchIfRecordExists_QNAME = new QName("http://server/", "searchIfRecordExists");
    private final static QName _TransferRecords_QNAME = new QName("http://server/", "transferRecords");
    private final static QName _CreateMRecordResponse_QNAME = new QName("http://server/", "createMRecordResponse");
    private final static QName _EditRecord_QNAME = new QName("http://server/", "editRecord");
    private final static QName _GetRecordCountsResponse_QNAME = new QName("http://server/", "getRecordCountsResponse");
    private final static QName _GetRecordCounts_QNAME = new QName("http://server/", "getRecordCounts");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: client.USimport
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link CreateERecordResponse }
     * 
     */
    public CreateERecordResponse createCreateERecordResponse() {
        return new CreateERecordResponse();
    }

    /**
     * Create an instance of {@link GetLocalCounts }
     * 
     */
    public GetLocalCounts createGetLocalCounts() {
        return new GetLocalCounts();
    }

    /**
     * Create an instance of {@link TransferRecordsResponse }
     * 
     */
    public TransferRecordsResponse createTransferRecordsResponse() {
        return new TransferRecordsResponse();
    }

    /**
     * Create an instance of {@link SearchIfRecordExistsResponse }
     * 
     */
    public SearchIfRecordExistsResponse createSearchIfRecordExistsResponse() {
        return new SearchIfRecordExistsResponse();
    }

    /**
     * Create an instance of {@link GetRecordCounts }
     * 
     */
    public GetRecordCounts createGetRecordCounts() {
        return new GetRecordCounts();
    }

    /**
     * Create an instance of {@link EditRecord }
     * 
     */
    public EditRecord createEditRecord() {
        return new EditRecord();
    }

    /**
     * Create an instance of {@link GetRecordCountsResponse }
     * 
     */
    public GetRecordCountsResponse createGetRecordCountsResponse() {
        return new GetRecordCountsResponse();
    }

    /**
     * Create an instance of {@link CreateMRecordResponse }
     * 
     */
    public CreateMRecordResponse createCreateMRecordResponse() {
        return new CreateMRecordResponse();
    }

    /**
     * Create an instance of {@link SearchIfRecordExists }
     * 
     */
    public SearchIfRecordExists createSearchIfRecordExists() {
        return new SearchIfRecordExists();
    }

    /**
     * Create an instance of {@link TransferRecords }
     * 
     */
    public TransferRecords createTransferRecords() {
        return new TransferRecords();
    }

    /**
     * Create an instance of {@link GetLocalCountsResponse }
     * 
     */
    public GetLocalCountsResponse createGetLocalCountsResponse() {
        return new GetLocalCountsResponse();
    }

    /**
     * Create an instance of {@link CreateMRecord }
     * 
     */
    public CreateMRecord createCreateMRecord() {
        return new CreateMRecord();
    }

    /**
     * Create an instance of {@link CreateERecord }
     * 
     */
    public CreateERecord createCreateERecord() {
        return new CreateERecord();
    }

    /**
     * Create an instance of {@link EditRecordResponse }
     * 
     */
    public EditRecordResponse createEditRecordResponse() {
        return new EditRecordResponse();
    }

    /**
     * Create an instance of {@link ProjectInfo }
     * 
     */
    public ProjectInfo createProjectInfo() {
        return new ProjectInfo();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SearchIfRecordExistsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server/", name = "searchIfRecordExistsResponse")
    public JAXBElement<SearchIfRecordExistsResponse> createSearchIfRecordExistsResponse(SearchIfRecordExistsResponse value) {
        return new JAXBElement<SearchIfRecordExistsResponse>(_SearchIfRecordExistsResponse_QNAME, SearchIfRecordExistsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetLocalCounts }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server/", name = "getLocalCounts")
    public JAXBElement<GetLocalCounts> createGetLocalCounts(GetLocalCounts value) {
        return new JAXBElement<GetLocalCounts>(_GetLocalCounts_QNAME, GetLocalCounts.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TransferRecordsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server/", name = "transferRecordsResponse")
    public JAXBElement<TransferRecordsResponse> createTransferRecordsResponse(TransferRecordsResponse value) {
        return new JAXBElement<TransferRecordsResponse>(_TransferRecordsResponse_QNAME, TransferRecordsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateERecordResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server/", name = "createERecordResponse")
    public JAXBElement<CreateERecordResponse> createCreateERecordResponse(CreateERecordResponse value) {
        return new JAXBElement<CreateERecordResponse>(_CreateERecordResponse_QNAME, CreateERecordResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EditRecordResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server/", name = "editRecordResponse")
    public JAXBElement<EditRecordResponse> createEditRecordResponse(EditRecordResponse value) {
        return new JAXBElement<EditRecordResponse>(_EditRecordResponse_QNAME, EditRecordResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateERecord }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server/", name = "createERecord")
    public JAXBElement<CreateERecord> createCreateERecord(CreateERecord value) {
        return new JAXBElement<CreateERecord>(_CreateERecord_QNAME, CreateERecord.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetLocalCountsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server/", name = "getLocalCountsResponse")
    public JAXBElement<GetLocalCountsResponse> createGetLocalCountsResponse(GetLocalCountsResponse value) {
        return new JAXBElement<GetLocalCountsResponse>(_GetLocalCountsResponse_QNAME, GetLocalCountsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateMRecord }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server/", name = "createMRecord")
    public JAXBElement<CreateMRecord> createCreateMRecord(CreateMRecord value) {
        return new JAXBElement<CreateMRecord>(_CreateMRecord_QNAME, CreateMRecord.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SearchIfRecordExists }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server/", name = "searchIfRecordExists")
    public JAXBElement<SearchIfRecordExists> createSearchIfRecordExists(SearchIfRecordExists value) {
        return new JAXBElement<SearchIfRecordExists>(_SearchIfRecordExists_QNAME, SearchIfRecordExists.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TransferRecords }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server/", name = "transferRecords")
    public JAXBElement<TransferRecords> createTransferRecords(TransferRecords value) {
        return new JAXBElement<TransferRecords>(_TransferRecords_QNAME, TransferRecords.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateMRecordResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server/", name = "createMRecordResponse")
    public JAXBElement<CreateMRecordResponse> createCreateMRecordResponse(CreateMRecordResponse value) {
        return new JAXBElement<CreateMRecordResponse>(_CreateMRecordResponse_QNAME, CreateMRecordResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EditRecord }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server/", name = "editRecord")
    public JAXBElement<EditRecord> createEditRecord(EditRecord value) {
        return new JAXBElement<EditRecord>(_EditRecord_QNAME, EditRecord.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetRecordCountsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server/", name = "getRecordCountsResponse")
    public JAXBElement<GetRecordCountsResponse> createGetRecordCountsResponse(GetRecordCountsResponse value) {
        return new JAXBElement<GetRecordCountsResponse>(_GetRecordCountsResponse_QNAME, GetRecordCountsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetRecordCounts }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server/", name = "getRecordCounts")
    public JAXBElement<GetRecordCounts> createGetRecordCounts(GetRecordCounts value) {
        return new JAXBElement<GetRecordCounts>(_GetRecordCounts_QNAME, GetRecordCounts.class, null, value);
    }

}
