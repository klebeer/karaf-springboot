/*
 * mcm-rest-client
 *
 * Copyright (c) 2018.  FISA Group.
 *
 * This software is the confidential and proprietary information FISA GROUP
 * ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with FISA Group.
 */

package ec.devnull.rest.client.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;

@ApiModel(value = "Order", description = "Detalles de la Orden de Entrada")
public class Order {

    @ApiModelProperty(notes = "Identifici\u00EDn de Empresa", required = true)
    private Integer partnerId;
    @ApiModelProperty(notes = "Identifici\u00EDn de Servicio", required = true)
    private Integer serviceId;
    @ApiModelProperty(notes = "Identifici\u00EDn del Servicio de la Empresa", required = false)
    private Integer partnerServiceId;
    @ApiModelProperty(notes = "Cuenta de la Empresa", required = false)
    private Integer partnerAcctId;
    private String acctCountry;
    private String acctBankCode;
    private String acctType;
    private String acctNumber;
    private Double acctBalance;
    private Double acctOverdraft;
    private String workorderReference;
    private String workorderValidSince;
    private String workorderValidUntil;
    private Boolean manualEntry;
    private String uploadFormat;
    private String fileName;
    private Boolean fileEncripted;
    private Integer fileType;
    private Integer groupServiceId;
    private Integer providerPartnerId;
    private Integer providerServiceId;
    private Integer providerPartnerServiceId;
    private String userCode;
    private String workstationIp;
    private String workstationMacAddr;
    private String securityToken;
    private List<RequiresRetry> requiresRetries = new ArrayList<RequiresRetry>();
    private List<CollectionRule> collectionRules = new ArrayList<CollectionRule>();


    public Integer getPartnerId() {
        return partnerId;
    }

    public Integer getServiceId() {
        return serviceId;
    }

    public Integer getPartnerServiceId() {
        return partnerServiceId;
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

    public String getAcctType() {
        return acctType;
    }

    public String getAcctNumber() {
        return acctNumber;
    }

    public Double getAcctBalance() {
        return acctBalance;
    }

    public Double getAcctOverdraft() {
        return acctOverdraft;
    }

    public String getWorkorderReference() {
        return workorderReference;
    }

    public String getWorkorderValidSince() {
        return workorderValidSince;
    }

    public String getWorkorderValidUntil() {
        return workorderValidUntil;
    }

    public Boolean getManualEntry() {
        return manualEntry;
    }

    public String getUploadFormat() {
        return uploadFormat;
    }

    public String getFileName() {
        return fileName;
    }

    public Boolean getFileEncripted() {
        return fileEncripted;
    }

    public Integer getFileType() {
        return fileType;
    }

    public Integer getGroupServiceId() {
        return groupServiceId;
    }

    public Integer getProviderPartnerId() {
        return providerPartnerId;
    }

    public Integer getProviderServiceId() {
        return providerServiceId;
    }

    public Integer getProviderPartnerServiceId() {
        return providerPartnerServiceId;
    }

    public String getUserCode() {
        return userCode;
    }

    public String getWorkstationIp() {
        return workstationIp;
    }

    public String getWorkstationMacAddr() {
        return workstationMacAddr;
    }

    public String getSecurityToken() {
        return securityToken;
    }

    public List<RequiresRetry> getRequiresRetries() {
        return requiresRetries;
    }

    public List<CollectionRule> getCollectionRules() {
        return collectionRules;
    }

    public Order partnerId(Integer partnerId) {
        this.partnerId = partnerId;
        return this;
    }

    public Order serviceId(Integer serviceId) {
        this.serviceId = serviceId;
        return this;
    }

    public Order partnerServiceId(Integer partnerServiceId) {
        this.partnerServiceId = partnerServiceId;
        return this;
    }

    public Order partnerAcctId(Integer partnerAcctId) {
        this.partnerAcctId = partnerAcctId;
        return this;
    }

    public Order acctCountry(String acctCountry) {
        this.acctCountry = acctCountry;
        return this;
    }

    public Order acctBankCode(String acctBankCode) {
        this.acctBankCode = acctBankCode;
        return this;
    }

    public Order acctType(String acctType) {
        this.acctType = acctType;
        return this;
    }

    public Order acctNumber(String acctNumber) {
        this.acctNumber = acctNumber;
        return this;
    }

    public Order acctBalance(Double acctBalance) {
        this.acctBalance = acctBalance;
        return this;
    }

    public Order acctOverdraft(Double acctOverdraft) {
        this.acctOverdraft = acctOverdraft;
        return this;
    }

    public Order workorderReference(String workorderReference) {
        this.workorderReference = workorderReference;
        return this;
    }

    public Order workorderValidSince(String workorderValidSince) {
        this.workorderValidSince = workorderValidSince;
        return this;
    }

    public Order workorderValidUntil(String workorderValidUntil) {
        this.workorderValidUntil = workorderValidUntil;
        return this;
    }

    public Order manualEntry(Boolean manualEntry) {
        this.manualEntry = manualEntry;
        return this;
    }

    public Order uploadFormat(String uploadFormat) {
        this.uploadFormat = uploadFormat;
        return this;
    }

    public Order fileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    public Order fileEncripted(Boolean fileEncripted) {
        this.fileEncripted = fileEncripted;
        return this;
    }

    public Order fileType(Integer fileType) {
        this.fileType = fileType;
        return this;
    }

    public Order groupServiceId(Integer groupServiceId) {
        this.groupServiceId = groupServiceId;
        return this;
    }

    public Order providerPartnerId(Integer providerPartnerId) {
        this.providerPartnerId = providerPartnerId;
        return this;
    }

    public Order providerServiceId(Integer providerServiceId) {
        this.providerServiceId = providerServiceId;
        return this;
    }

    public Order providerPartnerServiceId(Integer providerPartnerServiceId) {
        this.providerPartnerServiceId = providerPartnerServiceId;
        return this;
    }

    public Order userCode(String userCode) {
        this.userCode = userCode;
        return this;
    }

    public Order workstationIp(String workstationIp) {
        this.workstationIp = workstationIp;
        return this;
    }

    public Order workstationMacAddr(String workstationMacAddr) {
        this.workstationMacAddr = workstationMacAddr;
        return this;
    }

    public Order securityToken(String securityToken) {
        this.securityToken = securityToken;
        return this;
    }

    public Order requiresRetries(List<RequiresRetry> requiresRetries) {
        this.requiresRetries = requiresRetries;
        return this;
    }

    public Order collectionRules(List<CollectionRule> collectionRules) {
        this.collectionRules = collectionRules;
        return this;
    }


}
