package View;

import DAO.AoKhoacMuaDongDAO;
import DAO.ChiTietSanPhamDAO;
import DAO.HoaDonCTDoiDAO;
import DAO.HoaDonChiTietDAO;
import DAO.HoaDonDAO;
import DAO.LoaiAoDAO;
import DAO.MauSacDAO;
import DAO.PhieuDoiDAO;
import DAO.SanPhamHoaDonDAO;
import DAO.SizeDAO;
import Entity.AoKhoacMuaDong;
import Entity.ChiTietSanPham;
import Entity.HoaDonCTDoi;
import Entity.HoaDon;
import Entity.HoaDonChiTiet;
import Entity.LoaiAo;
import Entity.MauSac;
import Entity.PhieuDoi;
import Entity.SanPhamHoaDon;
import Entity.Size;
import Utils.MsgBox;
import java.awt.Font;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Admin
 */
public class TaoPhieuDoiJPanel extends javax.swing.JPanel {

    HoaDonCTDoiDAO hdctdDAO = new HoaDonCTDoiDAO();
    PhieuDoiDAO pdDAO = new PhieuDoiDAO();
    HoaDonDAO hdDAO = new HoaDonDAO();
    HoaDonChiTietDAO hdctDAO = new HoaDonChiTietDAO();
    AoKhoacMuaDongDAO akmdDAO = new AoKhoacMuaDongDAO();
    SanPhamHoaDonDAO sphdDAO = new SanPhamHoaDonDAO();
    ChiTietSanPhamDAO ctspDAO = new ChiTietSanPhamDAO();
    MauSacDAO msDAO = new MauSacDAO();
    SizeDAO szDAO = new SizeDAO();
    LoaiAoDAO laDAO = new LoaiAoDAO();
    DefaultTableModel tableModel = new DefaultTableModel();
    DefaultComboBoxModel<Object> dcbm = new DefaultComboBoxModel<>();
    int row = -1;
    int rowhd = -1;
    int rowsp = -1;
    int rowHDCTMoi = -1;
    int soluongtru;
    String soluongdoi;
    double tienTru;
    double totalSum;

    public TaoPhieuDoiJPanel() {
        initComponents();
        fillTableHoaDon();
        fillTableSP();
        initcboLoaiAoSP();
        initcboMauSPCT();
        initcboSizeSPCT();
    }

    public float laytienHDCTMoi() {
        int rowCount = tblHDCTMoi.getRowCount();
        //int columnCount = tblSanPhamDoi.getColumnCount();
        float totalSum = 0;

        // Duyệt qua cột mới chứa tổng của từng hàng và tính tổng của các tổng
        for (int row = 0; row < rowCount; row++) {
            totalSum += Integer.parseInt(tblHDCTMoi.getValueAt(row, 4).toString()) * Double.parseDouble(tblHDCTMoi.getValueAt(row, 5).toString());
        }
        return totalSum;
    }

    public float laytienHD() {
        int rowCount = tblHoaDon.getRowCount();
        //int columnCount = tblSanPhamDoi.getColumnCount();
        float totalSum = 0;

        // Duyệt qua cột mới chứa tổng của từng hàng và tính tổng của các tổng
        totalSum = Float.parseFloat(tblHoaDon.getValueAt(row, 4).toString());

        return totalSum;
    }

    private void fillTableSP() {
        DefaultTableModel tableModel = (DefaultTableModel) tblDSSP.getModel();
        tableModel.setRowCount(0);
        List<SanPhamHoaDon> list = sphdDAO.getAll();
        System.out.println(list.size());
        for (SanPhamHoaDon sphd : list) {
            tableModel.addRow(new Object[]{
                sphd.getMaCTSP(),
                sphd.getTenAo(),
                sphd.getSoLuongTon(),
                sphd.getMauSac(),
                sphd.getSize(),
                sphd.getLoaiAo(),
                sphd.getGiaDaGiam()
            });
        }
        tblDSSP.setModel(tableModel);
    }

    private void initcboMauSPCT() {
        DefaultComboBoxModel boxModel = (DefaultComboBoxModel) cboMauSac.getModel();
        boxModel.removeAllElements();
        List<MauSac> list = msDAO.selectAllHD();
        if (list == null) {
            boxModel.addElement("Chưa có màu sắc .");
        }
        boxModel.addElement("All");
        for (MauSac ms : list) {
            boxModel.addElement(ms);
        }
        cboMauSac.setModel(boxModel);
    }
// size

    private void initcboSizeSPCT() {
        DefaultComboBoxModel boxModel = (DefaultComboBoxModel) cboSize.getModel();
        boxModel.removeAllElements();
        List<Size> list = szDAO.selectAllHD();
        if (list == null) {
            boxModel.addElement("Chưa có size .");
        }
        boxModel.addElement("All");
        for (Size sz : list) {
            boxModel.addElement(sz);
        }
        cboSize.setModel(boxModel);
    }

    private void initcboLoaiAoSP() {
        DefaultComboBoxModel boxModel = (DefaultComboBoxModel) cboLoaiAo.getModel();
        boxModel.removeAllElements();
        List<LoaiAo> list = laDAO.selectAllHD();
        boxModel.addElement("All");
        for (LoaiAo la : list) {
            boxModel.addElement(la);
        }
        cboLoaiAo.setModel(boxModel);
    }

    public void fillTableHoaDon() {
        tableModel = (DefaultTableModel) tblHoaDon.getModel();
        tableModel.setRowCount(0);
        try {
            String keyword = txtTimKiemHD.getText();
            List<HoaDon> list = hdDAO.selectByKeyword(keyword);
            for (HoaDon pd : list) {
                Object[] rowData = new Object[]{
                    pd.getMaHoaDon(),
                    pd.getMaNhanVien(),
                    pd.getMaKhachHang(),
                    pd.getTongTien(),
                    pd.getThanhToan(),
                    pd.getNgayTaoHoaDon(),
                    pd.getTrangThaiHoaDon(),
                    pd.getGhiChuHD()
                };
                tableModel.addRow(rowData);
            }
        } catch (Exception e) {
            MsgBox.alert(this, "Lỗi truy vấn dữ liệu !");
        }
    }

    public void fillHoaDonChiTiet() {
        tableModel = (DefaultTableModel) tblHDCT.getModel();
        tableModel.setRowCount(0);

        int mahdct = Integer.parseInt(txtmahd.getText());

        try {
            List<HoaDonChiTiet> list = hdctDAO.selectByMaHD(mahdct);
            for (HoaDonChiTiet hdct : list) {
                AoKhoacMuaDong akmd = akmdDAO.getAoKhoacTheoCTSP(hdct.getMaSPCT());
                Object[] rowData = {
                    hdct.getMaHDCT(),
                    hdct.getMaHD(),
                    hdct.getMaSPCT(),
                    akmd.getTenAoKhoac(),
                    hdct.getSoLuongHDCT(),
                    hdct.getDonGiaHDCT(),
                    hdct.getThanhTien(),};
                tableModel.addRow(rowData);
            }

        } catch (Exception e) {
            MsgBox.alert(this, "Lỗi truy vấn !");
        }
    }

    void fillAllHoaDon() {
        tableModel = (DefaultTableModel) tblHoaDon.getModel();
        tableModel.setRowCount(0);
        try {
            List<HoaDon> list = hdDAO.selectAll1();
            for (HoaDon pd : list) {
                Object[] rowData = new Object[]{
                    pd.getMaHoaDon(),
                    pd.getMaNhanVien(),
                    pd.getMaKhachHang(),
                    pd.getTongTien(),
                    pd.getThanhToan(),
                    pd.getNgayTaoHoaDon(),
                    pd.getTrangThaiHoaDon(),
                    pd.getGhiChuHD()
                };
                tableModel.addRow(rowData);
            }
        } catch (Exception e) {
            MsgBox.alert(this, "Lỗi truy vấn dữ liệu !");
        }
    }

    void setModel(HoaDon hd) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        txtmahd.setText(hd.getMaHoaDon() + "");
        txtmanv.setText(hd.getMaNhanVien());
        txtmakh.setText(hd.getMaKhachHang() + "");
        txttongtien.setText(hd.getTongTien() + "");
        txtthanhtoan.setText(hd.getThanhToan() + "");
        jdcNgayTaoHD.setDate(hd.getNgayTaoHoaDon());
        cboTrangThaiHD.setSelectedItem(hd.getTrangThaiHoaDon());
        txtGhiChu.setText(hd.getGhiChuHD());
    }

    void edit() {
        try {
            Integer maKH = (Integer) tblHoaDon.getValueAt(this.row, 0);
            HoaDon hd = hdDAO.selectById(maKH);
            if (hd != null) {
                setModel(hd);

            }
        } catch (Exception e) {
            MsgBox.alert(this, "Lỗi truy vấn dữ liệu");
        }
    }

    private void fillTableTKSP() {
        DefaultTableModel tableModel = (DefaultTableModel) tblDSSP.getModel();
        tableModel.setRowCount(0);
        String timKiem = txtTimKiem.getText();
        System.out.println(sphdDAO.timKiemTheoTen(timKiem).size());
        if (sphdDAO.timKiemTheoTen(timKiem) == null) {
            return;
        }
        List<SanPhamHoaDon> list = sphdDAO.timKiemTheoTen(timKiem);
        for (SanPhamHoaDon sphd : list) {
            tableModel.addRow(new Object[]{
                sphd.getMaCTSP(), sphd.getTenAo(), sphd.getSoLuongTon(), sphd.getMauSac(), sphd.getSize(), sphd.getLoaiAo(), sphd.getGia(), sphd.getGiaDaGiam()
            });
        }

        tblDSSP.setModel(tableModel);
    }

    private float tinhTongTien() {
        int rowdssp = tblDSSP.getSelectedRow();
        float tongTien = 0;
        if (rowdssp > 0) {
            List<HoaDonCTDoi> list = hdctdDAO.selectAll();
            for (HoaDonCTDoi hdctd : list) {
                tongTien = (float) (tongTien + hdctd.getGiaAoKhoac());
            }

            float thanhToan = tongTien;
            String patternThanhToan = "###,###,###";
            DecimalFormat formatThanhToan = new DecimalFormat(patternThanhToan);
            String stringThanhToan = formatThanhToan.format(thanhToan);
            //txtThanhTien.setText(stringThanhToan);
        }
        return tongTien;
    }

    private void fillTableHDCT(int maHD) {
        DefaultTableModel tableModel = (DefaultTableModel) tblHDCTMoi.getModel();
        tableModel.setRowCount(0);
        List<HoaDonChiTiet> list = hdctDAO.selectByMaHD(maHD);
        for (HoaDonChiTiet hdct : list) {
            AoKhoacMuaDong akmd = akmdDAO.getAoKhoacTheoCTSP(hdct.getMaSPCT());
            tableModel.addRow(new Object[]{
                hdct.getMaHDCT(),
                hdct.getMaHD(),
                hdct.getMaSPCT(),
                akmd.getTenAoKhoac(),
                hdct.getSoLuongHDCT(),
                hdct.getDonGiaHDCT(),
                hdct.getThanhTien()
            });
        }
        tblHDCT.setModel(tableModel);
    }

    void ThemHDCTMoi() {
        int maHD;
        String maSPCT;
        int soLuongThem = 0;
        int soLuongSanPham;
        float donGia;
        float thanhTien;
        int soLuongTon;
        maHD = Integer.parseInt(tblHoaDon.getValueAt(rowhd, 0).toString());
        maSPCT = tblDSSP.getValueAt(rowsp, 0).toString();
        donGia = Float.parseFloat(tblDSSP.getValueAt(rowsp, 6).toString());
        String check = JOptionPane.showInputDialog(this, "Nhập số lượng sản phẩm muốn thêm .");
        soLuongSanPham = Integer.parseInt(tblDSSP.getValueAt(rowsp, 2).toString());
        System.out.println(check);
        if (check == null) {
            return;
        }

        try {
            soLuongThem = Integer.parseInt(check);
            if (soLuongThem < 0) {
                JOptionPane.showMessageDialog(this, "Số lượng nhập phải lớn hơn 0 .");
                return;
            }

            if (soLuongThem > soLuongSanPham) {
                JOptionPane.showMessageDialog(this, "Số lượng nhập lớn hơn số lượng tồn .");
                return;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Số lượng chỉ được chứa số .");
            return;
        }

        List<HoaDonChiTiet> list = hdctDAO.selectByMaHD(maHD);
        thanhTien = donGia * soLuongThem;
        soLuongTon = soLuongSanPham - soLuongThem;
        for (HoaDonChiTiet hdct : list) {
            if (hdct.getMaSPCT().equals(maSPCT)) {
                int soLuongTrung = hdct.getSoLuongHDCT() + soLuongThem;
                float thanhTien2 = soLuongTrung * donGia;
                // update hdct
                hdctDAO.updateHDCTSP(soLuongTrung, thanhTien2, hdct.getMaHDCT());
                fillTableHDCT(maHD);
                //update sản phẩm
                ctspDAO.updateCTSPSL(soLuongTon, maSPCT);
                fillTableSP();
                // đoạn này sẽ tính tổng tiền lại
//                float tongTien = tinhTongTien();
//                String patternTienTe = "###,###,###";
//                DecimalFormat formatTienTe = new DecimalFormat(patternTienTe);
//                String stringTienTe = formatTienTe.format(tongTien);
                float laytientt = laytienHD() - laytienHDCTMoi();
                String patternTienTe = "###,###,###";
                DecimalFormat formatTienTe = new DecimalFormat(patternTienTe);
                String TienTe = formatTienTe.format(laytientt);
                txtThanhTien.setText(TienTe);
                return;
            }
        }
        HoaDonChiTiet hdct = new HoaDonChiTiet(maHD, maSPCT, soLuongThem, donGia, thanhTien, true);
        hdctDAO.insert(hdct);
        fillTableHDCT(maHD);
        ctspDAO.updateCTSPSL(soLuongTon, maSPCT);
        fillTableSP();
        float tongTien = tinhTongTien();
        String patternTienTe = "###,###,###";
        DecimalFormat formatTienTe = new DecimalFormat(patternTienTe);
        String stringTienTe = formatTienTe.format(tongTien);
        //txtTongTien.setText(stringTienTe);

    }

    private void xoaSPHDCT() {
        int maHDCT = Integer.parseInt(tblHDCT.getValueAt(rowHDCTMoi, 0).toString());
        String maSPCT = tblHDCT.getValueAt(rowHDCTMoi, 2).toString();
        int maHD = Integer.parseInt(tblHoaDon.getValueAt(rowhd, 0).toString());
        int soLuongXoa;
        int soLuongSPHDCT = Integer.parseInt(tblHDCT.getValueAt(rowHDCTMoi, 4).toString());
        int soLuongSP = -1;
        String check = JOptionPane.showInputDialog(this, "Nhập số lượng muốn xóa .");
        if (check == null) {
            return;
        }
        try {
            soLuongXoa = Integer.parseInt(check);
            if (soLuongXoa < 1) {
                JOptionPane.showMessageDialog(this, "Số lượng xóa phải lớn hơn không .");
                return;
            }

            if (soLuongXoa > soLuongSPHDCT) {
                JOptionPane.showMessageDialog(this, "Số lượng xóa lơn hơn số lượng sản phẩm trong hóa đơn.");
                return;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Số lượng chỉ được chứa số ");
            return;
        }

        if (soLuongSPHDCT == soLuongXoa) {
            ChiTietSanPham ctsp = ctspDAO.selectById(maSPCT);
            soLuongSP = ctsp.getSoLuongAK() + soLuongXoa;
            ctspDAO.updateCTSPSL(soLuongSP, maSPCT);
            fillTableSP();
            hdctDAO.xoaHDCT(maHDCT);
            fillTableHDCT(maHD);
            //txtThanhTien.setText("");
            return;
        }

        if (soLuongSPHDCT > soLuongXoa) {
            // để update cho số lượng hdct
            int soLuongCon = soLuongSPHDCT - soLuongXoa;
            float donGia = Float.parseFloat(tblHDCT.getValueAt(rowHDCTMoi, 5).toString());
            float thanhTien = donGia * soLuongCon;
            ChiTietSanPham ctsp = ctspDAO.selectById(maSPCT);
            // để update cho số lượng của sp
            soLuongSP = ctsp.getSoLuongAK() + soLuongXoa;
            ctspDAO.updateCTSPSL(soLuongSP, maSPCT);
            fillTableSP();
            hdctDAO.updateHDCTSP(soLuongCon, thanhTien, maHDCT);
            fillTableHDCT(maHD);
//            float tongTien = tinhTongTien();
//            String patternTienTe = "###,###,###";
//            DecimalFormat formatTienTe = new DecimalFormat(patternTienTe);
//            String stringTienTe = formatTienTe.format(tongTien);
//            txtThanhTien.setText(stringTienTe);
            float laytientt = laytienHD() - laytienHDCTMoi();
            String patternTienTe = "###,###,###";
            DecimalFormat formatTienTe = new DecimalFormat(patternTienTe);
            String TienTe = formatTienTe.format(laytientt);
            txtThanhTien.setText(TienTe);
            return;
        }

    }

    private void xoaALLSPHDCT() {
        int maHD = Integer.parseInt(tblHoaDon.getValueAt(rowhd, 0).toString());
        List<HoaDonChiTiet> list = hdctDAO.selectByMaHD(maHD);
        for (HoaDonChiTiet hdct : list) {
            int soLuongSPHDCT = hdct.getSoLuongHDCT();
            ChiTietSanPham ctsp = ctspDAO.selectById(hdct.getMaSPCT());
            int soLuongSP = ctsp.getSoLuongAK() + soLuongSPHDCT;
            hdctDAO.xoaHDCT(hdct.getMaHDCT());
            fillTableHDCT(maHD);
            ctspDAO.updateCTSPSL(soLuongSP, hdct.getMaSPCT());
            fillTableSP();
            float laytientt = laytienHD() - laytienHDCTMoi();
            String patternTienTe = "###,###,###";
            DecimalFormat formatTienTe = new DecimalFormat(patternTienTe);
            String TienTe = formatTienTe.format(laytientt);
            txtThanhTien.setText(TienTe);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tab = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        btnDoiHang = new javax.swing.JButton();
        txtTienDaTT = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        txtLyDoDoiHang = new javax.swing.JTextArea();
        jPanel5 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtTimKiemHD = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblHoaDon = new javax.swing.JTable();
        btnTimKiemHD = new javax.swing.JButton();
        btnTatCa = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        txtmahd = new javax.swing.JTextField();
        txtmanv = new javax.swing.JTextField();
        txtmakh = new javax.swing.JTextField();
        txttongtien = new javax.swing.JTextField();
        txtthanhtoan = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtGhiChu = new javax.swing.JTextArea();
        cboTrangThaiHD = new javax.swing.JComboBox<>();
        jdcNgayTaoHD = new com.toedter.calendar.JDateChooser();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblHDCT = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jlbThanhToan = new javax.swing.JLabel();
        btnxoa = new javax.swing.JButton();
        btnHoanThanh = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tblHDCTMoi = new javax.swing.JTable();
        txtThanhTien = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblDSSP = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        txtTimKiem = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        cboMauSac = new javax.swing.JComboBox<>();
        cboSize = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        cboLoaiAo = new javax.swing.JComboBox<>();
        btnLoc = new javax.swing.JButton();

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "HOÀN TRẢ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        jLabel5.setText("Tiền Đã Thanh Toán");

        btnDoiHang.setText("Chọn Sản Phẩm");
        btnDoiHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDoiHangActionPerformed(evt);
            }
        });

        jLabel7.setText("Lý Do Đổi Hàng");

        txtLyDoDoiHang.setColumns(20);
        txtLyDoDoiHang.setRows(5);
        jScrollPane5.setViewportView(txtLyDoDoiHang);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 114, Short.MAX_VALUE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 501, Short.MAX_VALUE)
                    .addComponent(txtTienDaTT))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(256, 256, 256)
                .addComponent(btnDoiHang, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(288, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtTienDaTT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnDoiHang, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "HÓA ĐƠN", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        jLabel2.setText("Tìm Kiếm");

        txtTimKiemHD.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtTimKiemHDCaretUpdate(evt);
            }
        });
        txtTimKiemHD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTimKiemHDActionPerformed(evt);
            }
        });

        tblHoaDon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã Hóa Đơn", "Mã Nhân Viên", "Mã Khách Hàng", "Tổng Tiền ", "Thanh Toán", "Ngày Tạo HĐ", "Trạng Thái HĐ", "Ghi Chú"
            }
        ));
        tblHoaDon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblHoaDonMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblHoaDonMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(tblHoaDon);

        btnTimKiemHD.setText("Tìm Kiếm");
        btnTimKiemHD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKiemHDActionPerformed(evt);
            }
        });

        btnTatCa.setText("Tất Cả Hóa Đơn");
        btnTatCa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTatCaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 745, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(txtTimKiemHD, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(34, 34, 34)
                        .addComponent(btnTimKiemHD, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(16, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnTatCa)
                .addGap(352, 352, 352))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTimKiemHD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(btnTimKiemHD))
                .addGap(11, 11, 11)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnTatCa, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thông Tin Hóa Đơn", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        jLabel9.setText("Mã HĐ");

        jLabel10.setText("Mã NV");

        jLabel11.setText("Mã KH");

        jLabel12.setText("Tổng Tiền");

        jLabel13.setText("Thanh Toán");

        jLabel14.setText("Ngày Tạo HĐ");

        jLabel15.setText("Trạng Thái HĐ");

        jLabel16.setText("Ghi Chú");

        txtGhiChu.setColumns(20);
        txtGhiChu.setRows(5);
        jScrollPane2.setViewportView(txtGhiChu);

        cboTrangThaiHD.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Đã Thanh Toán", "Chờ Thanh Toán", "Đang Giao Hàng", "Đã Hủy" }));

        jdcNgayTaoHD.setDateFormatString("dd-MM-yyyy");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel15, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtmahd)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtthanhtoan, javax.swing.GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE)
                                    .addComponent(txttongtien)
                                    .addComponent(txtmakh)
                                    .addComponent(txtmanv)
                                    .addComponent(cboTrangThaiHD, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jdcNgayTaoHD, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addGap(33, 33, 33))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txtmahd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtmanv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(txtmakh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(txttongtien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(txtthanhtoan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel14)
                    .addComponent(jdcNgayTaoHD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(cboTrangThaiHD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33)
                .addComponent(jLabel16)
                .addGap(16, 16, 16)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "HÓA DƠN CHI TIẾT", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        tblHDCT.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã HĐCT", "Mã Hóa Đơn", "Mã Áo Khoác CT", "Tên Áo Khoác", "Số Lượng", "Đơn Giá", "Thành Tiền"
            }
        ));
        tblHDCT.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblHDCTMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tblHDCT);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 742, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(228, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(10, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(72, 72, 72))
        );

        tab.addTab("Tạo Phiếu Đổi", jPanel1);

        jlbThanhToan.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jlbThanhToan.setText("Tiền Từ Hóa Đơn");

        btnxoa.setText("Xóa");
        btnxoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnxoaActionPerformed(evt);
            }
        });

        btnHoanThanh.setText("TẠO PHIẾU ĐỔI");
        btnHoanThanh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHoanThanhActionPerformed(evt);
            }
        });

        jButton1.setText("Xóa Hết");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Chi Tiết Hóa Đơn Mới", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N

        tblHDCTMoi.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã HDCT", "Mã Hóa Đơn", "Mã CTSP", "Tên áo khoác", "Số Lượng", "Đơn Giá", "Thành tiền"
            }
        ));
        tblHDCTMoi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblHDCTMoiMouseClicked(evt);
            }
        });
        jScrollPane6.setViewportView(tblHDCTMoi);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 881, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(235, 235, 235))
        );

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap(946, Short.MAX_VALUE)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addComponent(btnxoa, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jlbThanhToan, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                        .addComponent(btnHoanThanh, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(14, 14, 14))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                        .addComponent(txtThanhTien, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
            .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel9Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(212, Short.MAX_VALUE)))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jlbThanhToan)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtThanhTien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(47, 47, 47)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnxoa, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(65, 65, 65)
                .addComponent(btnHoanThanh, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(90, 90, 90))
            .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel9Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(60, Short.MAX_VALUE)))
        );

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Danh Sách Sản Phẩm", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N

        tblDSSP.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã Áo Khoác CT", "Tên Áo", "Số Lượng Tồn", "Màu Sắc", "Size", "Loại áo", "Giá"
            }
        ));
        tblDSSP.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDSSPMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tblDSSP);

        jLabel1.setText("Tìm Kiếm");

        txtTimKiem.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtTimKiemCaretUpdate(evt);
            }
        });
        txtTimKiem.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                txtTimKiemMouseExited(evt);
            }
        });

        jLabel3.setText("Màu Sắc");

        cboMauSac.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        cboSize.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel4.setText("Size");

        jLabel6.setText("Loại Áo");

        cboLoaiAo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        btnLoc.setText("Lọc");
        btnLoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLocActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 70, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cboMauSac, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cboSize, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cboLoaiAo, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(56, 56, 56)
                .addComponent(btnLoc, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(106, 106, 106))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel3)
                    .addComponent(cboMauSac, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboSize, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel6)
                    .addComponent(cboLoaiAo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLoc))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(81, 81, 81))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(220, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(219, Short.MAX_VALUE)))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 376, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                    .addContainerGap(297, Short.MAX_VALUE)
                    .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, 313, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(72, Short.MAX_VALUE)))
        );

        tab.addTab("Chọn Sản Phẩm", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tab, javax.swing.GroupLayout.DEFAULT_SIZE, 1346, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tab))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnDoiHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDoiHangActionPerformed
        // TODO add your handling code here:
        String ma = txtmahd.getText();
        //int rowhd = tblHoaDon.getSelectedRow();
        String tt = tblHoaDon.getValueAt(row, 6).toString();
        if (ma.equals("")) {
            MsgBox.alert(this, "Vui lòng chọn hóa đơn !");
            return;
        }
        if (tt.equals("Chờ Thanh Toán")) {
            MsgBox.alert(this, "Đơn hàng đang chờ thanh toán !");
            return;
        }
        if (tt.equals("Đang Giao Hàng")) {
            MsgBox.alert(this, "Đơn hàng đang giao hàng !");
            return;
        }
        if (tt.equals("Đã Hủy")) {
            MsgBox.alert(this, "Đơn hàng đã hủy!");
            return;
        } else {
            tab.setSelectedIndex(1);
        }

    }//GEN-LAST:event_btnDoiHangActionPerformed

    private void txtTimKiemHDCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtTimKiemHDCaretUpdate
        // TODO add your handling code here:
        fillTableHoaDon();
    }//GEN-LAST:event_txtTimKiemHDCaretUpdate

    private void txtTimKiemHDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTimKiemHDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTimKiemHDActionPerformed

    private void tblHoaDonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHoaDonMouseClicked
        // TODO add your handling code here:

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

        rowhd = tblHoaDon.getSelectedRow();
        txtTienDaTT.setText(tblHoaDon.getValueAt(rowhd, 4).toString());

        if (rowhd < 0) {
            return;
        }
        if (evt.getClickCount() == 1) {
            this.row = tblHoaDon.rowAtPoint(evt.getPoint());
            int maHD = Integer.parseInt(tblHoaDon.getValueAt(rowhd, 0).toString());
            edit();
            fillHoaDonChiTiet();
            fillTableHDCT(maHD);
            float laytientt = laytienHD() - laytienHDCTMoi();
            String patternTienTe = "###,###,###";
            DecimalFormat formatTienTe = new DecimalFormat(patternTienTe);
            String stringTienTe = formatTienTe.format(laytientt);
            txtThanhTien.setText(stringTienTe);
        }
    }//GEN-LAST:event_tblHoaDonMouseClicked

    private void tblHoaDonMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHoaDonMousePressed

    }//GEN-LAST:event_tblHoaDonMousePressed

    private void btnTimKiemHDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKiemHDActionPerformed
        // TODO add your handling code here:
        int Search = Integer.parseInt(txtTimKiemHD.getText());
        tableModel = (DefaultTableModel) tblHoaDon.getModel();
        // Xóa dữ liệu hiện có trong bảng
        while (tableModel.getRowCount() > 0) {
            tableModel.removeRow(0);
        }
        boolean found = false;

        // Tìm kiếm hóa đơn theo mã và hiển thị kết quả
        List<HoaDon> listhd = hdDAO.selectAll1();
        for (HoaDon hd : listhd) {
            if (hd.getMaHoaDon() == Search) {
                tableModel.addRow(new Object[]{
                    hd.getMaHoaDon(),
                    hd.getMaNhanVien(),
                    hd.getMaKhachHang(),
                    hd.getTongTien(),
                    hd.getThanhToan(),
                    hd.getNgayTaoHoaDon(),
                    hd.getTrangThaiHoaDon(),
                    hd.getGhiChuHD(),});
                found = true;
            }

        }

        if (!found) {
            MsgBox.alert(this, "Không tìm thấy hóa đơn có mã là: " + Search);
            txtTimKiemHD.setText("");
        }
    }//GEN-LAST:event_btnTimKiemHDActionPerformed

    private void btnTatCaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTatCaActionPerformed
        // TODO add your handling code here:
        try {
            MsgBox.alert(this, "Hiển thị tất cả hóa đơn !");
            fillAllHoaDon();
        } catch (Exception e) {
        }
    }//GEN-LAST:event_btnTatCaActionPerformed

    private void tblHDCTMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHDCTMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tblHDCTMouseClicked

    private void btnxoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnxoaActionPerformed
        //soluongdoi = JOptionPane.showInputDialog(this, "Nhập số lượng");
        rowHDCTMoi = tblHDCTMoi.getSelectedRow();
        if (rowHDCTMoi < 0) {
            JOptionPane.showMessageDialog(this, "Chưa chọn sản phẩm để xóa !");
        } else {
            xoaSPHDCT();
            float laytientt = laytienHD() - laytienHDCTMoi();
            String patternTienTe = "###,###,###";
            DecimalFormat formatTienTe = new DecimalFormat(patternTienTe);
            String TienTe = formatTienTe.format(laytientt);
            txtThanhTien.setText(TienTe);
        }
    }//GEN-LAST:event_btnxoaActionPerformed

    private void btnHoanThanhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHoanThanhActionPerformed
        if (txtLyDoDoiHang.getText().trim().isEmpty()) {
            MsgBox.alert(this, "Hãy nhập lý do đổi hàng !");
            tab.setSelectedIndex(0);
            return;
        }

        if (laytienHDCTMoi() > laytienHD()) {
            MsgBox.alert(this, "Không được chọn sản phẩm quá số tiền !");
            return;
        }
        if (laytienHDCTMoi() < laytienHD()) {
            MsgBox.alert(this, "Hãy chọn sản phẩm phù hợp !");
            return;
        }
        if (laytienHDCTMoi() == laytienHD()) {
            for (int row : tblHoaDon.getSelectedRows()) {
                String trangthai = (String) tblHoaDon.getValueAt(row, 6);

                PhieuDoi pd = new PhieuDoi();
                pd.setMaHoaDon((int) tblHoaDon.getValueAt(row, 0));
                pd.setMaKhachHang((int) tblHoaDon.getValueAt(row, 2));
                pd.setTienThanhToan((float) laytienHDCTMoi());
                pd.setLyDoDoiHang(txtLyDoDoiHang.getText());
                pdDAO.insert(pd);
                MsgBox.alert(this, "Tạo phiếu đổi thành công !");

            }

            pdDAO.UpdateTT(Integer.parseInt(txtmahd.getText()));
            fillTableHoaDon();

        }
    }//GEN-LAST:event_btnHoanThanhActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if (rowhd < 0) {
            JOptionPane.showMessageDialog(this, "Chưa dòng nào trên bảng hóa đơn được chọn .");
        } else {
            xoaALLSPHDCT();
            float laytientt = laytienHD() - laytienHDCTMoi();
            String patternTienTe = "###,###,###";
            DecimalFormat formatTienTe = new DecimalFormat(patternTienTe);
            String TienTe = formatTienTe.format(laytientt);
            txtThanhTien.setText(TienTe);
        }
    }//GEN-LAST:event_jButton1ActionPerformed


    private void tblDSSPMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDSSPMouseClicked
        // TODO add your handling code here:
        rowsp = tblDSSP.getSelectedRow();

        if (rowsp < 0) {
            JOptionPane.showMessageDialog(this, "Chưa dòng nào trong bảng hóa đơn được chọn .");
        } else {
            ThemHDCTMoi();
            float laytientt = laytienHD() - laytienHDCTMoi();
            String patternTienTe = "###,###,###";
            DecimalFormat formatTienTe = new DecimalFormat(patternTienTe);
            String TienTe = formatTienTe.format(laytientt);
            txtThanhTien.setText(TienTe);
        }

    }//GEN-LAST:event_tblDSSPMouseClicked


    private void txtTimKiemCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtTimKiemCaretUpdate
        // TODO add your handling code here:
        fillTableTKSP();
    }//GEN-LAST:event_txtTimKiemCaretUpdate

    private void txtTimKiemMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtTimKiemMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTimKiemMouseExited

    private void btnLocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLocActionPerformed
        // TODO add your handling code here:
        String ms = "";
        String sz = "";
        String la = "";
        Object selectMs = cboMauSac.getSelectedItem();
        if (selectMs instanceof MauSac) {
            System.out.println("a");
            ms = (String) cboMauSac.getSelectedItem().toString();
        }
        Object selectSz = cboSize.getSelectedItem();
        if (selectSz instanceof Size) {
            sz = (String) cboSize.getSelectedItem().toString();
        }
        Object selectLa = cboLoaiAo.getSelectedItem();
        if (selectLa instanceof LoaiAo) {
            la = (String) cboLoaiAo.getSelectedItem().toString();
        }

        DefaultTableModel tableModel = (DefaultTableModel) tblDSSP.getModel();
        tableModel.setRowCount(0);
        List<SanPhamHoaDon> list = sphdDAO.locSanPham(ms, sz, la);
        for (SanPhamHoaDon sphd : list) {
            tableModel.addRow(new Object[]{
                sphd.getMaCTSP(), sphd.getTenAo(), sphd.getSoLuongTon(), sphd.getMauSac(), sphd.getSize(), sphd.getLoaiAo(), sphd.getGia(), sphd.getGiaDaGiam()
            });
        }

        tblDSSP.setModel(tableModel);
    }//GEN-LAST:event_btnLocActionPerformed

    private void tblHDCTMoiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHDCTMoiMouseClicked

    }//GEN-LAST:event_tblHDCTMoiMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDoiHang;
    private javax.swing.JButton btnHoanThanh;
    private javax.swing.JButton btnLoc;
    private javax.swing.JButton btnTatCa;
    private javax.swing.JButton btnTimKiemHD;
    private javax.swing.JButton btnxoa;
    private javax.swing.JComboBox<String> cboLoaiAo;
    private javax.swing.JComboBox<String> cboMauSac;
    private javax.swing.JComboBox<String> cboSize;
    private javax.swing.JComboBox<String> cboTrangThaiHD;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private com.toedter.calendar.JDateChooser jdcNgayTaoHD;
    private javax.swing.JLabel jlbThanhToan;
    private javax.swing.JTabbedPane tab;
    private javax.swing.JTable tblDSSP;
    private javax.swing.JTable tblHDCT;
    private javax.swing.JTable tblHDCTMoi;
    private javax.swing.JTable tblHoaDon;
    private javax.swing.JTextArea txtGhiChu;
    private javax.swing.JTextArea txtLyDoDoiHang;
    private javax.swing.JTextField txtThanhTien;
    private javax.swing.JTextField txtTienDaTT;
    private javax.swing.JTextField txtTimKiem;
    private javax.swing.JTextField txtTimKiemHD;
    private javax.swing.JTextField txtmahd;
    private javax.swing.JTextField txtmakh;
    private javax.swing.JTextField txtmanv;
    private javax.swing.JTextField txtthanhtoan;
    private javax.swing.JTextField txttongtien;
    // End of variables declaration//GEN-END:variables
}
