package warehouse.management.app.web.rest.vm;

import warehouse.management.app.domain.ChiTietDonNhap;
import warehouse.management.app.service.dto.DonNhapDTO;

import javax.validation.constraints.NotNull;
import java.util.List;

public class DonNhapAllInfo {
    @NotNull
    private DonNhapDTO donNhapDTO;

//    @NotNull
    private String nguoiXacNhanDon;

    @NotNull
    private List<ChiTietDonNhap> chiTietDonNhapList;

    public String getNguoiXacNhanDon() {
        return nguoiXacNhanDon;
    }

    public DonNhapDTO getDonNhapDTO() {
        return donNhapDTO;
    }

    public List<ChiTietDonNhap> getChiTietDonNhapList() {
        return chiTietDonNhapList;
    }

    public void setDonNhapDTO(DonNhapDTO donNhapDTO) {
        this.donNhapDTO = donNhapDTO;
    }

    public void setNguoiXacNhanDon(String nguoiXacNhanDon) {
        this.nguoiXacNhanDon = nguoiXacNhanDon;
    }

    public void setChiTietDonNhapList(List<ChiTietDonNhap> chiTietDonNhapList) {
        this.chiTietDonNhapList = chiTietDonNhapList;
    }
}
