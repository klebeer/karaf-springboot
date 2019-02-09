package ec.devnull.karafboot.dummy.model;

import java.util.ArrayList;
import java.util.List;


public class OrderResponse {


    private Integer returnCode;

    private String returnCodeDesc;
    private Integer partnerId;
    private String partnerTradeName;
    private Integer serviceId;
    private String serviceName;
    private Integer partnerServiceId;
    private String description;
    private Integer providerPartnerId;
    private String providerTradeName;
    private Integer providerServiceId;
    private String providerServiceName;
    private Integer providerPartnerServiceId;
    private String workorderReference;
    private String fileName;
    private Integer partnerAcctId;
    private String acctCountry;
    private String acctBankCode;
    private String acctBankName;
    private String acctType;
    private String acctNumber;
    private String uploadDate;
    private Integer workorderId;
    private Integer workorderUploadId;
    private String currency;
    private Double amount;
    private Integer recordsQuantity;
    private Integer uploadedRecords;
    private Integer recordsWithErrors;
    private String workorderStatus;
    private List<WorkorderSummary> workorderSummary = new ArrayList<WorkorderSummary>();


    public Integer getReturnCode() {
        return returnCode;
    }

    public String getReturnCodeDesc() {
        return returnCodeDesc;
    }

    public Integer getPartnerId() {
        return partnerId;
    }

    public String getPartnerTradeName() {
        return partnerTradeName;
    }

    public Integer getServiceId() {
        return serviceId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public Integer getPartnerServiceId() {
        return partnerServiceId;
    }

    public String getDescription() {
        return description;
    }

    public Integer getProviderPartnerId() {
        return providerPartnerId;
    }

    public String getProviderTradeName() {
        return providerTradeName;
    }

    public Integer getProviderServiceId() {
        return providerServiceId;
    }

    public String getProviderServiceName() {
        return providerServiceName;
    }

    public Integer getProviderPartnerServiceId() {
        return providerPartnerServiceId;
    }

    public String getWorkorderReference() {
        return workorderReference;
    }

    public String getFileName() {
        return fileName;
    }

    public Integer getPartnerAcctId() {
        return partnerAcctId;
    }

    public String getAcctCountry() {
        return acctCountry;
    }

    public String getAcctBankCode() {
        return acctBankCode;
    }

    public String getAcctBankName() {
        return acctBankName;
    }

    public String getAcctType() {
        return acctType;
    }

    public String getAcctNumber() {
        return acctNumber;
    }

    public String getUploadDate() {
        return uploadDate;
    }

    public Integer getWorkorderId() {
        return workorderId;
    }

    public Integer getWorkorderUploadId() {
        return workorderUploadId;
    }

    public String getCurrency() {
        return currency;
    }

    public Double getAmount() {
        return amount;
    }

    public Integer getRecordsQuantity() {
        return recordsQuantity;
    }

    public Integer getUploadedRecords() {
        return uploadedRecords;
    }

    public Integer getRecordsWithErrors() {
        return recordsWithErrors;
    }

    public String getWorkorderStatus() {
        return workorderStatus;
    }

    public List<WorkorderSummary> getWorkorderSummary() {
        return workorderSummary;
    }

    public OrderResponse returnCode(Integer returnCode) {
        this.returnCode = returnCode;
        return this;
    }

    public OrderResponse returnCodeDesc(String returnCodeDesc) {
        this.returnCodeDesc = returnCodeDesc;
        return this;
    }

    public OrderResponse partnerId(Integer partnerId) {
        this.partnerId = partnerId;
        return this;
    }

    public OrderResponse partnerTradeName(String partnerTradeName) {
        this.partnerTradeName = partnerTradeName;
        return this;
    }

    public OrderResponse serviceId(Integer serviceId) {
        this.serviceId = serviceId;
        return this;
    }

    public OrderResponse serviceName(String serviceName) {
        this.serviceName = serviceName;
        return this;
    }

    public OrderResponse partnerServiceId(Integer partnerServiceId) {
        this.partnerServiceId = partnerServiceId;
        return this;
    }

    public OrderResponse description(String description) {
        this.description = description;
        return this;
    }

    public OrderResponse providerPartnerId(Integer providerPartnerId) {
        this.providerPartnerId = providerPartnerId;
        return this;
    }

    public OrderResponse providerTradeName(String providerTradeName) {
        this.providerTradeName = providerTradeName;
        return this;
    }

    public OrderResponse providerServiceId(Integer providerServiceId) {
        this.providerServiceId = providerServiceId;
        return this;
    }

    public OrderResponse providerServiceName(String providerServiceName) {
        this.providerServiceName = providerServiceName;
        return this;
    }

    public OrderResponse providerPartnerServiceId(Integer providerPartnerServiceId) {
        this.providerPartnerServiceId = providerPartnerServiceId;
        return this;
    }

    public OrderResponse workorderReference(String workorderReference) {
        this.workorderReference = workorderReference;
        return this;
    }

    public OrderResponse fileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    public OrderResponse partnerAcctId(Integer partnerAcctId) {
        this.partnerAcctId = partnerAcctId;
        return this;
    }

    public OrderResponse acctCountry(String acctCountry) {
        this.acctCountry = acctCountry;
        return this;
    }

    public OrderResponse acctBankCode(String acctBankCode) {
        this.acctBankCode = acctBankCode;
        return this;
    }

    public OrderResponse acctBankName(String acctBankName) {
        this.acctBankName = acctBankName;
        return this;
    }

    public OrderResponse acctType(String acctType) {
        this.acctType = acctType;
        return this;
    }

    public OrderResponse acctNumber(String acctNumber) {
        this.acctNumber = acctNumber;
        return this;
    }

    public OrderResponse uploadDate(String uploadDate) {
        this.uploadDate = uploadDate;
        return this;
    }

    public OrderResponse workorderId(Integer workorderId) {
        this.workorderId = workorderId;
        return this;
    }

    public OrderResponse workorderUploadId(Integer workorderUploadId) {
        this.workorderUploadId = workorderUploadId;
        return this;
    }

    public OrderResponse currency(String currency) {
        this.currency = currency;
        return this;
    }

    public OrderResponse amount(Double amount) {
        this.amount = amount;
        return this;
    }

    public OrderResponse recordsQuantity(Integer recordsQuantity) {
        this.recordsQuantity = recordsQuantity;
        return this;
    }

    public OrderResponse uploadedRecords(Integer uploadedRecords) {
        this.uploadedRecords = uploadedRecords;
        return this;
    }

    public OrderResponse recordsWithErrors(Integer recordsWithErrors) {
        this.recordsWithErrors = recordsWithErrors;
        return this;
    }

    public OrderResponse workorderStatus(String workorderStatus) {
        this.workorderStatus = workorderStatus;
        return this;
    }

    public OrderResponse workorderSummary(List<WorkorderSummary> workorderSummary) {
        this.workorderSummary = workorderSummary;
        return this;
    }


}
