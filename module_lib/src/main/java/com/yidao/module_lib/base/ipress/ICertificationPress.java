package com.yidao.module_lib.base.ipress;

import com.yidao.module_lib.base.ibase.IBasePress;

/**
 * Created with XIAOYUDEXIEE.
 * Date: 2019/8/7
 */
public interface ICertificationPress extends IBasePress {

    void getCertificationStatus(long userId);

    void commitUserCertification(String userId,String idCardPositive,String idCardNegative,String personalPhotos, String introduction, String phone,String wechat,String sina,String other);

    void commitCompanyCertification(String userId, String introduction, String phone, String address, String businessLicense,String dockingPeoplePhone,String dockingPeopleName);

    void commitOperaCertification(String userId, String idCardPositive, String idCardNegative,String filmCrewName,String position,String introduction,String contactName,String contactPhone);
}
