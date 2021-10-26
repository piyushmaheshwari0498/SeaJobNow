package com.example.seajobnow.ApiEntity.request;

public class PlanMasterRequest {

    String plan_name;
    String trial_status;
    String trial_days;
    String annual_fees;
    String plan_duration;
    String cv_download_count;
    String cv_download_type;
    String unfiltered_per_week;
    String smart_filtered_status;
    String download_days;
    String sub_account_user;
    String whatsapp_alert;
    String email_alert;
    String sms_alert;
    String relationship_manager;
    String visit_per_month; //Service Visit
    String mobile_app_access;
    String urgent_vacancy;
    String bmti_candidate;
    int viewColor;

    public PlanMasterRequest(String plan_name, String plan_duration, int viewColor) {
        this.plan_name = plan_name;
        this.plan_duration = plan_duration;
        this.viewColor = viewColor;
    }

    public PlanMasterRequest(String plan_name, String trial_status, String trial_days, String annual_fees, String plan_duration,
                             String cv_download_count, String cv_download_type, String unfiltered_per_week,
                             String smart_filtered_status, String download_days, String sub_account_user,
                             String whatsapp_alert, String email_alert, String sms_alert, String relationship_manager,
                             String visit_per_month, String mobile_app_access, String urgent_vacancy, String bmti_candidate) {
        this.plan_name = plan_name;
        this.trial_status = trial_status;
        this.trial_days = trial_days;
        this.annual_fees = annual_fees;
        this.plan_duration = plan_duration;
        this.cv_download_count = cv_download_count;
        this.cv_download_type = cv_download_type;
        this.unfiltered_per_week = unfiltered_per_week;
        this.smart_filtered_status = smart_filtered_status;
        this.download_days = download_days;
        this.sub_account_user = sub_account_user;
        this.whatsapp_alert = whatsapp_alert;
        this.email_alert = email_alert;
        this.sms_alert = sms_alert;
        this.relationship_manager = relationship_manager;
        this.visit_per_month = visit_per_month;
        this.mobile_app_access = mobile_app_access;
        this.urgent_vacancy = urgent_vacancy;
        this.bmti_candidate = bmti_candidate;
    }

    public int getViewColor() {
        return viewColor;
    }

    public void setViewColor(int viewColor) {
        this.viewColor = viewColor;
    }

    public String getPlan_name() {
        return plan_name;
    }

    public void setPlan_name(String plan_name) {
        this.plan_name = plan_name;
    }

    public String getTrial_status() {
        return trial_status;
    }

    public void setTrial_status(String trial_status) {
        this.trial_status = trial_status;
    }

    public String getTrial_days() {
        return trial_days;
    }

    public void setTrial_days(String trial_days) {
        this.trial_days = trial_days;
    }

    public String getAnnual_fees() {
        return annual_fees;
    }

    public void setAnnual_fees(String annual_fees) {
        this.annual_fees = annual_fees;
    }

    public String getPlan_duration() {
        return plan_duration;
    }

    public void setPlan_duration(String plan_duration) {
        this.plan_duration = plan_duration;
    }

    public String getCv_download_count() {
        return cv_download_count;
    }

    public void setCv_download_count(String cv_download_count) {
        this.cv_download_count = cv_download_count;
    }

    public String getCv_download_type() {
        return cv_download_type;
    }

    public void setCv_download_type(String cv_download_type) {
        this.cv_download_type = cv_download_type;
    }

    public String getUnfiltered_per_week() {
        return unfiltered_per_week;
    }

    public void setUnfiltered_per_week(String unfiltered_per_week) {
        this.unfiltered_per_week = unfiltered_per_week;
    }

    public String getSmart_filtered_status() {
        return smart_filtered_status;
    }

    public void setSmart_filtered_status(String smart_filtered_status) {
        this.smart_filtered_status = smart_filtered_status;
    }

    public String getDownload_days() {
        return download_days;
    }

    public void setDownload_days(String download_days) {
        this.download_days = download_days;
    }

    public String getSub_account_user() {
        return sub_account_user;
    }

    public void setSub_account_user(String sub_account_user) {
        this.sub_account_user = sub_account_user;
    }

    public String getWhatsapp_alert() {
        return whatsapp_alert;
    }

    public void setWhatsapp_alert(String whatsapp_alert) {
        this.whatsapp_alert = whatsapp_alert;
    }

    public String getEmail_alert() {
        return email_alert;
    }

    public void setEmail_alert(String email_alert) {
        this.email_alert = email_alert;
    }

    public String getSms_alert() {
        return sms_alert;
    }

    public void setSms_alert(String sms_alert) {
        this.sms_alert = sms_alert;
    }

    public String getRelationship_manager() {
        return relationship_manager;
    }

    public void setRelationship_manager(String relationship_manager) {
        this.relationship_manager = relationship_manager;
    }

    public String getVisit_per_month() {
        return visit_per_month;
    }

    public void setVisit_per_month(String visit_per_month) {
        this.visit_per_month = visit_per_month;
    }

    public String getMobile_app_access() {
        return mobile_app_access;
    }

    public void setMobile_app_access(String mobile_app_access) {
        this.mobile_app_access = mobile_app_access;
    }

    public String getUrgent_vacancy() {
        return urgent_vacancy;
    }

    public void setUrgent_vacancy(String urgent_vacancy) {
        this.urgent_vacancy = urgent_vacancy;
    }

    public String getBmti_candidate() {
        return bmti_candidate;
    }

    public void setBmti_candidate(String bmti_candidate) {
        this.bmti_candidate = bmti_candidate;
    }
}
