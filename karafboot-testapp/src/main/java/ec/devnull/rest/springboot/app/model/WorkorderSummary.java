
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

package ec.devnull.rest.springboot.app.model;


public class WorkorderSummary {

    private String paymentCollectionMode;
    private Integer uploadedRecords;

    public String getPaymentCollectionMode() {
        return paymentCollectionMode;
    }

    public void setPaymentCollectionMode(String paymentCollectionMode) {
        this.paymentCollectionMode = paymentCollectionMode;
    }

    public Integer getUploadedRecords() {
        return uploadedRecords;
    }

    public void setUploadedRecords(Integer uploadedRecords) {
        this.uploadedRecords = uploadedRecords;
    }


}
