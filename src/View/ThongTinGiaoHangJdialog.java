/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package View;

import DAO.HoaDonDAO;
import DAO.PhieuGiaoHangDAO;
import Entity.HoaDon;
import Entity.PhieuGiaoHang;
import Utils.MsgBox;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.JOptionPane;

/**
 *
 * @author admin
 */
public class ThongTinGiaoHangJdialog extends javax.swing.JDialog {

    PhieuGiaoHang model = new PhieuGiaoHang();
    PhieuGiaoHangDAO PGH_DAO = new PhieuGiaoHangDAO();
    HoaDonDAO hddao = new HoaDonDAO();

    /**
     * Creates new form ThongTinGiaoHangJdialog
     */
    public ThongTinGiaoHangJdialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    public ThongTinGiaoHangJdialog(java.awt.Frame parent, boolean modal, String maHD, String tongTien) {
        super(parent, modal);
        initComponents();
        txtMaHoaDon.setText(maHD);
        txtTongGiaTri.setText(tongTien);
        initCBO_DVVC();
        ChooseNgayGiaoHang.setDate(new Date());
        ChooseNgayNhanHang.setDate(new Date());
    }

    void initCBO_DVVC() {
        List<String> tenDonViList = PGH_DAO.selectAllTenDonViVanChuyen();
        Set<String> uniqueDonViSet = new HashSet<>(tenDonViList);

        for (String tenDonVi : uniqueDonViSet) {
            cboDonViVanChuyen.addItem(tenDonVi);
        }

    }

    PhieuGiaoHang getForm() {
        PhieuGiaoHang model = new PhieuGiaoHang();
        model.setMaHoaDon(Integer.parseInt(txtMaHoaDon.getText()));
        if ("Giao Hàng Nhanh".equals(cboDonViVanChuyen.getSelectedItem())) {
            model.setMaDonViVanChuyen(1);
        }
        if ("Giao Hàng Tiết Kiệm".equals(cboDonViVanChuyen.getSelectedItem())) {
            model.setMaDonViVanChuyen(2);
        }
        if ("Viettel Post".equals(cboDonViVanChuyen.getSelectedItem())) {
            model.setMaDonViVanChuyen(3);
        }
        if ("247 Express".equals(cboDonViVanChuyen.getSelectedItem())) {
            model.setMaDonViVanChuyen(4);
        }
        if ("J&T Express".equals(cboDonViVanChuyen.getSelectedItem())) {
            model.setMaDonViVanChuyen(5);
        }

        if (rdoKhiNhanHang.isSelected()) {
            model.setHinhThucThanhToan(true);
        } else {
            model.setHinhThucThanhToan(false);
        }
        model.setTongGiaTriPGH(Double.parseDouble(txtTongGiaTri.getText().replace(".", "")));
        model.setGhiChuPGH(txtGhiChu.getText());
        model.setTenNguoiNhan(txtNguoiNhan.getText());
        model.setDiaChiNhanHang(txtDiaChiNguoiNhan.getText());
        model.setSoDienThoaiNguoiNhan(txtSDTNguoiNhan.getText());
        model.setNgayGiaoHang(ChooseNgayGiaoHang.getDate());
        model.setNgayNhanHang(ChooseNgayNhanHang.getDate());

        if (model != null) {
            if (cboTrangThaiGiaoHang.getSelectedItem() == "Chuẩn bị hàng") {
                model.setTrangThaiGiaoHang("ChuanBiHang");
            } else if (cboTrangThaiGiaoHang.getSelectedItem() == "Đang Giao") {
                model.setTrangThaiGiaoHang("DangGiao");
            }
        }

        return model;
    }

    int insert(PhieuGiaoHang model) {
        try {
            PGH_DAO.insert(model);
            return 1;
        } catch (Exception e) {
            MsgBox.alert(this, "Thêm mới thất bại do lỗi " + e);
            return 0;
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

        buttonGroup1 = new javax.swing.ButtonGroup();
        bgThongTin = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        txtMaHoaDon = new javax.swing.JTextField();
        txtMaPhieuGiaoHang = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        rdoTaiQuay = new javax.swing.JRadioButton();
        rdoKhiNhanHang = new javax.swing.JRadioButton();
        jPanel2 = new javax.swing.JPanel();
        txtTongGiaTri = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        cboDonViVanChuyen = new javax.swing.JComboBox<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtGhiChu = new javax.swing.JTextPane();
        jPanel4 = new javax.swing.JPanel();
        txtNguoiNhan = new javax.swing.JTextField();
        txtDiaChiNguoiNhan = new javax.swing.JTextField();
        txtSDTNguoiNhan = new javax.swing.JTextField();
        ChooseNgayGiaoHang = new com.toedter.calendar.JDateChooser();
        cboTrangThaiGiaoHang = new javax.swing.JComboBox<>();
        ChooseNgayNhanHang = new com.toedter.calendar.JDateChooser();
        btnHoanThanh = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        bgThongTin.setBackground(new java.awt.Color(255, 255, 255));
        bgThongTin.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thông tin phiếu giao hàng", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N

        jLabel1.setText("Mã hoá đơn");
        jLabel1.setAlignmentY(2.0F);

        jLabel2.setText("Đơn vị vận chuyển");
        jLabel2.setAlignmentY(2.0F);

        jLabel3.setText("SĐT người nhận");

        jLabel4.setText("Địa chỉ người nhận");

        jLabel5.setText("Tên người nhận");

        jLabel6.setText("Hình thức thanh toán");

        jLabel7.setText("Ngày nhận hàng dự kiến ");

        jLabel8.setText("Trạng thái giao hàng");

        jLabel9.setText("Ngày giao hàng");

        jLabel10.setText("Tổng giá trị");

        jLabel11.setText("Ghi chú");

        jLabel12.setText("Mã phiếu giao hàng");
        jLabel12.setAlignmentY(2.0F);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel3.add(txtMaHoaDon, new org.netbeans.lib.awtextra.AbsoluteConstraints(2, 46, 240, -1));

        txtMaPhieuGiaoHang.setEditable(false);
        txtMaPhieuGiaoHang.setEnabled(false);
        jPanel3.add(txtMaPhieuGiaoHang, new org.netbeans.lib.awtextra.AbsoluteConstraints(2, 6, 240, -1));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        buttonGroup1.add(rdoTaiQuay);
        rdoTaiQuay.setText("Tại quầy");

        buttonGroup1.add(rdoKhiNhanHang);
        rdoKhiNhanHang.setText("Khi nhận hàng");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(rdoTaiQuay, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19)
                .addComponent(rdoKhiNhanHang, javax.swing.GroupLayout.DEFAULT_SIZE, 117, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdoKhiNhanHang)
                    .addComponent(rdoTaiQuay))
                .addContainerGap())
        );

        jPanel3.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 126, 240, -1));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel13.setText("VND");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(txtTongGiaTri, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTongGiaTri, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13))
                .addContainerGap())
        );

        jPanel3.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 180, 240, -1));

        jPanel3.add(cboDonViVanChuyen, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 90, 240, -1));

        jScrollPane2.setViewportView(txtGhiChu);

        jPanel3.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 240, 240, 70));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        txtSDTNguoiNhan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSDTNguoiNhanActionPerformed(evt);
            }
        });

        ChooseNgayGiaoHang.setDateFormatString("dd-MM-yyyy");

        cboTrangThaiGiaoHang.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Chuẩn bị hàng", "Đang Giao" }));

        ChooseNgayNhanHang.setDateFormatString("dd-MM-yyyy");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(cboTrangThaiGiaoHang, 0, 246, Short.MAX_VALUE)
            .addComponent(ChooseNgayNhanHang, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(ChooseNgayGiaoHang, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(txtDiaChiNguoiNhan)
            .addComponent(txtNguoiNhan)
            .addComponent(txtSDTNguoiNhan, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtNguoiNhan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addComponent(txtDiaChiNguoiNhan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addComponent(txtSDTNguoiNhan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addComponent(ChooseNgayGiaoHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(ChooseNgayNhanHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
                .addComponent(cboTrangThaiGiaoHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21))
        );

        javax.swing.GroupLayout bgThongTinLayout = new javax.swing.GroupLayout(bgThongTin);
        bgThongTin.setLayout(bgThongTinLayout);
        bgThongTinLayout.setHorizontalGroup(
            bgThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bgThongTinLayout.createSequentialGroup()
                .addGroup(bgThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(bgThongTinLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(bgThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12)
                            .addComponent(jLabel1)))
                    .addComponent(jLabel11)
                    .addComponent(jLabel10)
                    .addGroup(bgThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel2)))
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(bgThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel3)
                    .addComponent(jLabel5)
                    .addComponent(jLabel7)
                    .addComponent(jLabel9)
                    .addComponent(jLabel8))
                .addGap(18, 18, 18)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(33, Short.MAX_VALUE))
        );
        bgThongTinLayout.setVerticalGroup(
            bgThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bgThongTinLayout.createSequentialGroup()
                .addGroup(bgThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(bgThongTinLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(bgThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(bgThongTinLayout.createSequentialGroup()
                                .addComponent(jLabel12)
                                .addGap(27, 27, 27)
                                .addComponent(jLabel1)
                                .addGap(33, 33, 33)
                                .addComponent(jLabel2)
                                .addGap(27, 27, 27)
                                .addComponent(jLabel6)
                                .addGap(34, 34, 34)
                                .addComponent(jLabel10)
                                .addGap(49, 49, 49)
                                .addComponent(jLabel11))
                            .addGroup(bgThongTinLayout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addGap(40, 40, 40)
                                .addComponent(jLabel4)
                                .addGap(40, 40, 40)
                                .addComponent(jLabel3)
                                .addGap(36, 36, 36)
                                .addComponent(jLabel9)
                                .addGap(40, 40, 40)
                                .addComponent(jLabel7)
                                .addGap(45, 45, 45)
                                .addComponent(jLabel8))))
                    .addGroup(bgThongTinLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(27, Short.MAX_VALUE))
        );

        btnHoanThanh.setText("Hoàn Thành");
        btnHoanThanh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHoanThanhActionPerformed(evt);
            }
        });

        jButton2.setText("Hủy");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(573, Short.MAX_VALUE)
                .addComponent(btnHoanThanh)
                .addGap(29, 29, 29)
                .addComponent(jButton2)
                .addGap(77, 77, 77))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(bgThongTin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(419, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnHoanThanh)
                    .addComponent(jButton2))
                .addGap(35, 35, 35))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(bgThongTin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(86, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtSDTNguoiNhanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSDTNguoiNhanActionPerformed

    }//GEN-LAST:event_txtSDTNguoiNhanActionPerformed

    private void btnHoanThanhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHoanThanhActionPerformed
        // TODO add your handling code here:
        if (rdoTaiQuay.isSelected()) {
            if (insert(getForm()) == 1) {
                JOptionPane.showMessageDialog(this, "Thanh toán thành công .");
            } else {
                JOptionPane.showMessageDialog(this, "Thanh toán không thành công .");
            }
            HoaDon hd = new HoaDon();
            int maHD = Integer.parseInt(txtMaHoaDon.getText());
            hd.setMaHoaDon(maHD);
            hd.setTrangThaiHoaDon("Đã thanh toán");
            hddao.update(hd);
        }
        if (rdoKhiNhanHang.isSelected()) {
            if (insert(getForm()) == 1) {
                JOptionPane.showMessageDialog(this, "Tạo phiếu thành công .");
            } else {
                JOptionPane.showMessageDialog(this, "Tạo phiếu không thành công .");
            }
            HoaDon hd = new HoaDon();
            int maHD = Integer.parseInt(txtMaHoaDon.getText());
            hd.setMaHoaDon(maHD);
            hd.setTrangThaiHoaDon("Đang giao hàng");
            hddao.update(hd);
        }

    }//GEN-LAST:event_btnHoanThanhActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ThongTinGiaoHangJdialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ThongTinGiaoHangJdialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ThongTinGiaoHangJdialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ThongTinGiaoHangJdialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ThongTinGiaoHangJdialog dialog = new ThongTinGiaoHangJdialog(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.toedter.calendar.JDateChooser ChooseNgayGiaoHang;
    private com.toedter.calendar.JDateChooser ChooseNgayNhanHang;
    private javax.swing.JPanel bgThongTin;
    private javax.swing.JButton btnHoanThanh;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cboDonViVanChuyen;
    private javax.swing.JComboBox<String> cboTrangThaiGiaoHang;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JRadioButton rdoKhiNhanHang;
    private javax.swing.JRadioButton rdoTaiQuay;
    private javax.swing.JTextField txtDiaChiNguoiNhan;
    private javax.swing.JTextPane txtGhiChu;
    private javax.swing.JTextField txtMaHoaDon;
    private javax.swing.JTextField txtMaPhieuGiaoHang;
    private javax.swing.JTextField txtNguoiNhan;
    private javax.swing.JTextField txtSDTNguoiNhan;
    private javax.swing.JTextField txtTongGiaTri;
    // End of variables declaration//GEN-END:variables
}
