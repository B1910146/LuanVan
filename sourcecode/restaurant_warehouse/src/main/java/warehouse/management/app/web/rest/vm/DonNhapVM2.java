package warehouse.management.app.web.rest.vm;

import warehouse.management.app.service.dto.DonNhapDTO;

import javax.validation.constraints.NotNull;

public class DonNhapVM2 {
    @NotNull
    private DonNhapDTO donNhapDTO;

//    @NotNull
    private String nguoiXacNhanDon;

    public DonNhapDTO getDonNhapDTO() {
        return donNhapDTO;
    }

    public String getNguoiXacNhanDon() {
        return nguoiXacNhanDon;
    }

    public void setDonNhapDTO(DonNhapDTO donNhapDTO) {
        this.donNhapDTO = donNhapDTO;
    }

    public void setNguoiXacNhanDon(String nguoiXacNhanDon) {
        this.nguoiXacNhanDon = nguoiXacNhanDon;
    }
}
