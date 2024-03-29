package com.web.common.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.json.JSONObject;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class PetInfoByAPI {
    @Id
    @GeneratedValue()
    private Long Id;
    @Column(nullable = false, unique = true, name = "desertion_no")
    private Long desertionNo;
    @Column
    private String filename;
    @Column
    private String happenDt;
    @Column
    private String happenPlace;
    @Column
    private String kindCd;
    @Column
    private String colorCd;
    @Column
    private String age;
    @Column
    private String weight;
    @Column
    private String noticeNo;
    @Column
    private String noticeSdt;
    @Column
    private String noticeEdt;
    @Column
    private String popfile;
    @Column
    private String processState;
    @Column
    private String sexCd;
    @Column
    private String neuterYn;
    @Column
    private String specialMark;
    @Column
    private String careNm;
    @Column
    private String careTel;
    @Column
    private String careAddr;
    @Column
    private String orgNm;
    @Column
    private String chargeNm;
    @Column
    private String officetel;
    @Column
    private String petStateEnum;

    public static PetInfoByAPI of(JSONObject itemObject, String state) {
        Long desertionNo = null;
        String desertionNoStr = itemObject.optString("desertionNo");
        if (desertionNoStr != null && !desertionNoStr.isEmpty()) {
            desertionNo = Long.parseLong(desertionNoStr);
        }
        return PetInfoByAPI.builder()
                .desertionNo(desertionNo)
                .filename(itemObject.optString("filename"))
                .happenDt(itemObject.optString("happenDt"))
                .happenPlace(itemObject.optString("happenPlace"))
                .kindCd(itemObject.optString("kindCd"))
                .colorCd(itemObject.optString("colorCd"))
                .age(itemObject.optString("age"))
                .weight(itemObject.optString("weight"))
                .noticeNo(itemObject.optString("noticeNo"))
                .noticeSdt(itemObject.optString("noticeSdt"))
                .noticeEdt(itemObject.optString("noticeEdt"))
                .popfile(itemObject.optString("popfile"))
                .processState(itemObject.optString("processState"))
                .sexCd(itemObject.optString("sexCd"))
                .neuterYn(itemObject.optString("neuterYn"))
                .specialMark(itemObject.optString("specialMark"))
                .careNm(itemObject.optString("careNm"))
                .careTel(itemObject.optString("careTel"))
                .careAddr(itemObject.optString("careAddr"))
                .orgNm(itemObject.optString("orgNm"))
                .chargeNm(itemObject.optString("chargeNm"))
                .officetel(itemObject.optString("officetel"))
                .petStateEnum(state)
                .build();
    }

    public void update(JSONObject itemObject, String state) {
        String desertionNoStr = itemObject.optString("desertionNo");
        if (desertionNoStr != null && !desertionNoStr.isEmpty()) {
            this.desertionNo = Long.parseLong(desertionNoStr);
        } else {
            throw new NullPointerException("desertionNo 없음");
        }
        this.filename = itemObject.optString("filename");
        this.happenDt = itemObject.optString("happenDt");
        this.happenPlace = itemObject.optString("happenPlace");
        this.kindCd = itemObject.optString("kindCd");
        this.colorCd = itemObject.optString("colorCd");
        this.age = itemObject.optString("age");
        this.weight = itemObject.optString("weight");
        this.noticeNo = itemObject.optString("noticeNo");
        this.noticeSdt = itemObject.optString("noticeSdt");
        this.noticeEdt = itemObject.optString("noticeEdt");
        this.popfile = itemObject.optString("popfile");
        this.processState = itemObject.optString("processState");
        this.sexCd = itemObject.optString("sexCd");
        this.neuterYn = itemObject.optString("neuterYn");
        this.specialMark = itemObject.optString("specialMark");
        this.careNm = itemObject.optString("careNm");
        this.careTel = itemObject.optString("careTel");
        this.careAddr = itemObject.optString("careAddr");
        this.orgNm = itemObject.optString("orgNm");
        this.chargeNm = itemObject.optString("chargeNm");
        this.officetel = itemObject.optString("officetel");
        this.petStateEnum = state;
    }
}
