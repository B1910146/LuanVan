package warehouse.management.app.web.rest.vm;

import warehouse.management.app.domain.ChiTietPhieuNhap;
import warehouse.management.app.service.dto.ChiTietPhieuNhapDTO;
import warehouse.management.app.service.dto.PhieuNhapDTO;

import javax.validation.constraints.NotNull;
import java.util.List;

public class PhieuNhapVM {
    @NotNull
    private PhieuNhapDTO phieuNhapDTO;

    @NotNull
    private List<ChiTietPhieuNhap> chiTietPhieuNhapList;

    public List<ChiTietPhieuNhap> getChiTietPhieuNhapList() {
        return chiTietPhieuNhapList;
    }

    public void setChiTietPhieuNhapList(List<ChiTietPhieuNhap> chiTietPhieuNhapList) {
        this.chiTietPhieuNhapList = chiTietPhieuNhapList;
    }

    public PhieuNhapDTO getPhieuNhapDTO() {
        return phieuNhapDTO;
    }

    public void setPhieuNhapDTO(PhieuNhapDTO phieuNhapDTO) {
        this.phieuNhapDTO = phieuNhapDTO;
    }
}
