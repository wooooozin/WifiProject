package model;

import java.util.List;

public class PublicWifiInfo {
    private TbPublicWifiInfo TbPublicWifiInfo;

    public TbPublicWifiInfo getTbPublicWifiInfo() {
        return TbPublicWifiInfo;
    }

    public void setTbPublicWifiInfo(TbPublicWifiInfo tbPublicWifiInfo) {
        TbPublicWifiInfo = tbPublicWifiInfo;
    }

    public static class TbPublicWifiInfo {
        private int list_total_count;
        private Result RESULT;
        private List<Row> row;

        public int getList_total_count() {
            return list_total_count;
        }

        public void setList_total_count(int list_total_count) {
            this.list_total_count = list_total_count;
        }

        public Result getRESULT() {
            return RESULT;
        }

        public void setRESULT(Result RESULT) {
            this.RESULT = RESULT;
        }

        public List<Row> getRow() {
            return row;
        }

        public void setRow(List<Row> row) {
            this.row = row;
        }
    }

    public static class Result {
        private String CODE;
        private String MESSAGE;

        public String getCODE() {
            return CODE;
        }

        public void setCODE(String CODE) {
            this.CODE = CODE;
        }

        public String getMESSAGE() {
            return MESSAGE;
        }

        public void setMESSAGE(String MESSAGE) {
            this.MESSAGE = MESSAGE;
        }
    }

    public static class Row {
        private String X_SWIFI_MGR_NO;
        private String X_SWIFI_WRDOFC;
        private String X_SWIFI_MAIN_NM;
        private String X_SWIFI_ADRES1;
        private String X_SWIFI_ADRES2;
        private String X_SWIFI_INSTL_FLOOR;
        private String X_SWIFI_INSTL_TY;
        private String X_SWIFI_INSTL_MBY;
        private String X_SWIFI_SVC_SE;
        private String X_SWIFI_CMCWR;
        private String X_SWIFI_CNSTC_YEAR;
        private String X_SWIFI_INOUT_DOOR;
        private String X_SWIFI_REMARS3;
        private String LAT;
        private String LNT;
        private String WORK_DTTM;

        public String getX_SWIFI_MGR_NO() {
            return X_SWIFI_MGR_NO;
        }

        public void setX_SWIFI_MGR_NO(String X_SWIFI_MGR_NO) {
            this.X_SWIFI_MGR_NO = X_SWIFI_MGR_NO;
        }

        public String getX_SWIFI_WRDOFC() {
            return X_SWIFI_WRDOFC;
        }

        public void setX_SWIFI_WRDOFC(String X_SWIFI_WRDOFC) {
            this.X_SWIFI_WRDOFC = X_SWIFI_WRDOFC;
        }

        public String getX_SWIFI_MAIN_NM() {
            return X_SWIFI_MAIN_NM;
        }

        public void setX_SWIFI_MAIN_NM(String X_SWIFI_MAIN_NM) {
            this.X_SWIFI_MAIN_NM = X_SWIFI_MAIN_NM;
        }

        public String getX_SWIFI_ADRES1() {
            return X_SWIFI_ADRES1;
        }

        public void setX_SWIFI_ADRES1(String X_SWIFI_ADRES1) {
            this.X_SWIFI_ADRES1 = X_SWIFI_ADRES1;
        }

        public String getX_SWIFI_ADRES2() {
            return X_SWIFI_ADRES2;
        }

        public void setX_SWIFI_ADRES2(String X_SWIFI_ADRES2) {
            this.X_SWIFI_ADRES2 = X_SWIFI_ADRES2;
        }

        public String getX_SWIFI_INSTL_FLOOR() {
            return X_SWIFI_INSTL_FLOOR;
        }

        public void setX_SWIFI_INSTL_FLOOR(String X_SWIFI_INSTL_FLOOR) {
            this.X_SWIFI_INSTL_FLOOR = X_SWIFI_INSTL_FLOOR;
        }

        public String getX_SWIFI_INSTL_TY() {
            return X_SWIFI_INSTL_TY;
        }

        public void setX_SWIFI_INSTL_TY(String X_SWIFI_INSTL_TY) {
            this.X_SWIFI_INSTL_TY = X_SWIFI_INSTL_TY;
        }

        public String getX_SWIFI_INSTL_MBY() {
            return X_SWIFI_INSTL_MBY;
        }

        public void setX_SWIFI_INSTL_MBY(String X_SWIFI_INSTL_MBY) {
            this.X_SWIFI_INSTL_MBY = X_SWIFI_INSTL_MBY;
        }

        public String getX_SWIFI_SVC_SE() {
            return X_SWIFI_SVC_SE;
        }

        public void setX_SWIFI_SVC_SE(String X_SWIFI_SVC_SE) {
            this.X_SWIFI_SVC_SE = X_SWIFI_SVC_SE;
        }

        public String getX_SWIFI_CMCWR() {
            return X_SWIFI_CMCWR;
        }

        public void setX_SWIFI_CMCWR(String X_SWIFI_CMCWR) {
            this.X_SWIFI_CMCWR = X_SWIFI_CMCWR;
        }

        public String getX_SWIFI_CNSTC_YEAR() {
            return X_SWIFI_CNSTC_YEAR;
        }

        public void setX_SWIFI_CNSTC_YEAR(String X_SWIFI_CNSTC_YEAR) {
            this.X_SWIFI_CNSTC_YEAR = X_SWIFI_CNSTC_YEAR;
        }

        public String getX_SWIFI_INOUT_DOOR() {
            return X_SWIFI_INOUT_DOOR;
        }

        public void setX_SWIFI_INOUT_DOOR(String X_SWIFI_INOUT_DOOR) {
            this.X_SWIFI_INOUT_DOOR = X_SWIFI_INOUT_DOOR;
        }

        public String getX_SWIFI_REMARS3() {
            return X_SWIFI_REMARS3;
        }

        public void setX_SWIFI_REMARS3(String X_SWIFI_REMARS3) {
            this.X_SWIFI_REMARS3 = X_SWIFI_REMARS3;
        }

        public String getLAT() {
            return LAT;
        }

        public void setLAT(String LAT) {
            this.LAT = LAT;
        }

        public String getLNT() {
            return LNT;
        }

        public void setLNT(String LNT) {
            this.LNT = LNT;
        }

        public String getWORK_DTTM() {
            return WORK_DTTM;
        }

        public void setWORK_DTTM(String WORK_DTTM) {
            this.WORK_DTTM = WORK_DTTM;
        }
    }
}
