package DAO;

import Entity.HoaDonCTDoi;
import java.util.ArrayList;
import java.util.List;
import Utils.JDBCHelper;
import java.sql.*;

public class HoaDonCTDoiDAO extends DuAn1DAO1<HoaDonCTDoi, String>{
    String SELECT_ALL_SQL = "SELECT tblChiTietSanPham.MaSPCT, tblAoKhoacMuaDong.MaAoKhoac, tblAoKhoacMuaDong.TenAoKhoac, tblAoKhoacMuaDong.TenPhongCach,tblSize.TenSize,tblChiTietSanPham.GiaAK ,tblChiTietSanPham.SoLuongAK\n" +
"FROM tblAoKhoacMuaDong\n" +
"INNER JOIN tblChiTietSanPham ON tblAoKhoacMuaDong.MaAoKhoac = tblChiTietSanPham.MaAoKhoac\n" +
"INNER JOIN tblSize  ON tblChiTietSanPham.MaSize = tblSize.MaSize;";
    
    String SELECT_BY_ID = "SELECT tblChiTietSanPham.MaSPCT, tblAoKhoacMuaDong.MaAoKhoac, tblAoKhoacMuaDong.TenAoKhoac, tblAoKhoacMuaDong.TenPhongCach,tblSize.TenSize,tblChiTietSanPham.GiaAK ,tblChiTietSanPham.SoLuongAK\n" +
"FROM tblAoKhoacMuaDong\n" +
"INNER JOIN tblChiTietSanPham ON tblAoKhoacMuaDong.MaAoKhoac = tblChiTietSanPham.MaAoKhoac\n" +
"INNER JOIN tblSize  ON tblChiTietSanPham.MaSize = tblSize.MaSize"
            + "WHERE tblChiTietSanPham.MaSPCT=?";
    @Override
    public int insert(HoaDonCTDoi entity) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int update(HoaDonCTDoi entity) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public HoaDonCTDoi selectById(String id) {
        List<HoaDonCTDoi> list = selectBySql(SELECT_BY_ID, id);
        if(list.isEmpty()){
            return null ;
        }
        return list.get(0);
    }
    

    @Override
    public List<HoaDonCTDoi> selectAll() {
        return this.selectBySql(SELECT_ALL_SQL);
    }

    @Override
    protected List<HoaDonCTDoi> selectBySql(String sql, Object... args) {
        List<HoaDonCTDoi> list = new ArrayList<>();
        try {
            ResultSet rs = JDBCHelper.executeQuery(sql, args);
            while (rs.next()) {
                HoaDonCTDoi chsp = new HoaDonCTDoi();
                chsp.setMaSPCT(rs.getString(1));
                chsp.setMaAoKhoac(rs.getString(2));
                chsp.setTenAoKhoac(rs.getString(3));
                chsp.setTenPhongCach(rs.getString(4));
                chsp.setTenSize(rs.getString(5));
                chsp.setGiaAoKhoac(rs.getFloat(6));
                chsp.setSoLuong(rs.getInt(7));
                
                list.add(chsp);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
    
}
