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
