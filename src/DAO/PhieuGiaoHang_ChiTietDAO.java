package DAO;

import Entity.PhieuGiaoHang;
import Entity.PhieuGiaoHang_ChiTiet;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import Utils.JDBCHelper;

public class PhieuGiaoHang_ChiTietDAO {

    final String select = "SELECT \n"
            + "    tblHoaDonChiTiet.MaHoaDon,\n"
            + "	tblAoKhoacMuaDong.TenAoKhoac,\n"
            + "	tblMauSacSP.TenMauSac,\n"
            + "    tblHoaDonChiTiet.SoLuongHDCT,\n"
            + "    tblHoaDonChiTiet.DonGiaHDCT,\n"
            + "    tblDonViVanChuyen.TenDonVi\n"
            + "FROM tblHoaDonChiTiet\n"
            + "JOIN tblChiTietSanPham ON tblHoaDonChiTiet.MaSPCT = tblChiTietSanPham.MaSPCT\n"
            + "JOIN tblAoKhoacMuaDong ON tblChiTietSanPham.MaAoKhoac = tblAoKhoacMuaDong.MaAoKhoac\n"
            + "JOIN tblMauSacSP on tblChiTietSanPham.MaMauSac = tblMauSacSP.MaMauSac\n"
            + "LEFT JOIN tblPhieuGiaoHang ON tblHoaDonChiTiet.MaHoaDon = tblPhieuGiaoHang.MaHoaDon\n"
            + "LEFT JOIN tblDonViVanChuyen ON tblPhieuGiaoHang.MaDonViVanChuyen = tblDonViVanChuyen.MaDonViVanChuyen\n"
            + "WHERE tblHoaDonChiTiet.MaHoaDon = ?";

    protected List<PhieuGiaoHang_ChiTiet> selectAllChiTietPhieuGiaoHang(String sql, Object... args) {
        List<PhieuGiaoHang_ChiTiet> list = new ArrayList<>();
        try {
            ResultSet rs = JDBCHelper.executeQuery(sql, args);
            while (rs.next()) {
                PhieuGiaoHang_ChiTiet chiTiet = new PhieuGiaoHang_ChiTiet();

                chiTiet.setMaHoaDon(rs.getInt("MaHoaDon"));
                chiTiet.setTenAoKhoac(rs.getString("TenAoKhoac"));
                chiTiet.setTenMauSac(rs.getString("TenMauSac"));
                chiTiet.setSoLuongHDCT(rs.getInt("SoLuongHDCT"));
                chiTiet.setDonGia(rs.getFloat("DonGiaHDCT"));
                chiTiet.setDVVC(rs.getString("TenDonVi"));

                list.add(chiTiet);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public PhieuGiaoHang_ChiTiet selectByMaHoaDon(Integer key) {
        List<PhieuGiaoHang_ChiTiet> list = selectAllChiTietPhieuGiaoHang(select, key);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

}
