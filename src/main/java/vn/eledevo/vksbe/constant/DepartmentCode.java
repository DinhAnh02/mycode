package vn.eledevo.vksbe.constant;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RequiredArgsConstructor
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum DepartmentCode {
    PB_LANH_DAO("Lãnh đạo"),
    PB_KY_THUAT("Kỹ thuật"),
    PB_TRAT_TU_XA_HOI("Trật tự xã hội"),
    PB_AN_NINH_MA_TUY("An ninh - Ma Túy"),
    PB_KINH_TE_THAM_NHUNG("Kinh tế - Tham nhũng"),
    PB_DAN_SU_HANH_CHINH_KINH_DOANH("Dân sự, hành chính, kinh doanh, thương mại"),
    PB_KHIEU_NAI_TO_CAO("Khiếu nại tố cáo"),
    PB_TO_CHUC_CAN_BO("Tổ chức cán bộ"),
    PB_THANH_TRA_KHIEU_TO("Thanh tra - khiếu tố"),
    PB_THI_HANH_AN("Thi hành án");

    String description;
}
