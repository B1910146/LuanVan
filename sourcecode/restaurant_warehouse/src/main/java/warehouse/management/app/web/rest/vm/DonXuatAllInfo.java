package warehouse.management.app.web.rest.vm;

import warehouse.management.app.domain.ChiTietDonXuat;
import warehouse.management.app.service.dto.DonXuatDTO;

import javax.validation.constraints.NotNull;
import java.util.List;

public class DonXuatAllInfo {

    @NotNull
    private DonXuatDTO donXuatDTO;

    @NotNull
    private List<ChiTietDonXuat> chiTietDonXuatList;

    private String nguoiXacNhanDon;

    public String getNguoiXacNhanDon() {
        return nguoiXacNhanDon;
    }

    public DonXuatDTO getDonXuatDTO() {
        return donXuatDTO;
    }

    public List<ChiTietDonXuat> getChiTietDonXuatList() {
        return chiTietDonXuatList;
    }

    public void setDonXuatDTO(DonXuatDTO donXuatDTO) {
        this.donXuatDTO = donXuatDTO;
    }

    public void setNguoiXacNhanDon(String nguoiXacNhanDon) {
        this.nguoiXacNhanDon = nguoiXacNhanDon;
    }

    public void setChiTietDonXuatList(List<ChiTietDonXuat> chiTietDonXuatList) {
        this.chiTietDonXuatList = chiTietDonXuatList;
    }
}
