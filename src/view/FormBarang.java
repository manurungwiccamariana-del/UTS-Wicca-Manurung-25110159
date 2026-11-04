package view;
import Model.Barang;
import dao.BarangDAO;
import dao.Koneksi;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class FormBarang extends JFrame {
    JTextField txtKode = new JTextField();
    JTextField txtNama = new JTextField();
    JTextField txtStok = new JTextField();
    JComboBox<String> comboKategori = new JComboBox<>(new String[]{"Komputer","Jaringan","Alat Ukur"});
    JButton btnSimpan = new JButton("Simpan");
    JButton btnUbah = new JButton("Ubah");
    JButton btnHapus = new JButton("Hapus");
    JButton btnBersih = new JButton("Bersihkan");
    JTable table = new JTable();
    
    public FormBarang(){
        setTitle("Inventori - Wicca 25110159"); setSize(650,450); setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        add(new JLabel("Kode Barang:")).setBounds(20,20,100,25); txtKode.setBounds(130,20,200,25); add(txtKode);
        add(new JLabel("Nama Barang:")).setBounds(20,50,100,25); txtNama.setBounds(130,50,200,25); add(txtNama);
        add(new JLabel("Kategori:")).setBounds(20,80,100,25); comboKategori.setBounds(130,80,200,25); add(comboKategori);
        add(new JLabel("Stok:")).setBounds(20,110,100,25); txtStok.setBounds(130,110,200,25); add(txtStok);
        btnSimpan.setBounds(20,145,80,30); add(btnSimpan);
        btnUbah.setBounds(110,145,70,30); add(btnUbah);
        btnHapus.setBounds(190,145,70,30); add(btnHapus);
        btnBersih.setBounds(270,145,100,30); add(btnBersih);
        JScrollPane sp = new JScrollPane(table); sp.setBounds(20,190,600,200); add(sp);
        btnSimpan.addActionListener(e -> simpan());
        btnUbah.addActionListener(e -> ubah());
        btnHapus.addActionListener(e -> hapus());
        btnBersih.addActionListener(e -> bersih());
        table.addMouseListener(new java.awt.event.MouseAdapter(){
            public void mouseClicked(java.awt.event.MouseEvent e){ klikTabel(); }
        });
        tampilData(); setLocationRelativeTo(null);
    }
    void simpan(){
        if(txtKode.getText().isEmpty() || txtNama.getText().isEmpty()){
            JOptionPane.showMessageDialog(null, "Kode dan Nama tidak boleh kosong!"); return;
        }
        try{
            int stok = Integer.parseInt(txtStok.getText());
            if(stok < 0){ JOptionPane.showMessageDialog(null, "Stok harus >= 0"); return; }
            Barang b = new Barang(txtKode.getText(), txtNama.getText(), comboKategori.getSelectedItem().toString(), stok);
            if(new BarangDAO().simpan(b)){
                JOptionPane.showMessageDialog(null, "Data berhasil disimpan!"); tampilData(); bersih();
            } else JOptionPane.showMessageDialog(null, "Gagal: Kode sudah ada (Duplicate) - coba pakai Ubah");
        }catch(NumberFormatException ex){ JOptionPane.showMessageDialog(null, "Stok harus angka!"); }
    }
    void ubah(){
        try{
            Barang b = new Barang(txtKode.getText(), txtNama.getText(), comboKategori.getSelectedItem().toString(), Integer.parseInt(txtStok.getText()));
            if(new BarangDAO().ubah(b)){ JOptionPane.showMessageDialog(null, "Data berhasil diubah!"); tampilData(); }
        }catch(Exception ex){ JOptionPane.showMessageDialog(null, "Gagal ubah"); }
    }
    void hapus(){
        int konfirm = JOptionPane.showConfirmDialog(null, "Yakin hapus "+txtKode.getText()+"?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if(konfirm==JOptionPane.YES_OPTION){
            if(new BarangDAO().hapus(txtKode.getText())){ JOptionPane.showMessageDialog(null, "Data dihapus!"); tampilData(); bersih(); }
        }
    }
    void tampilData(){
        try{
            DefaultTableModel m = new DefaultTableModel(new String[]{"Kode","Nama","Kategori","Stok"},0);
            ResultSet rs = Koneksi.getConnection().createStatement().executeQuery("SELECT * FROM barang");
            while(rs.next()){ m.addRow(new Object[]{rs.getString(1), rs.getString(2), rs.getString(3), rs.getInt(4)}); }
            table.setModel(m);
        }catch(Exception e){}
    }
    void klikTabel(){ int r=table.getSelectedRow(); txtKode.setText(table.getValueAt(r,0).toString()); txtNama.setText(table.getValueAt(r,1).toString()); comboKategori.setSelectedItem(table.getValueAt(r,2).toString()); txtStok.setText(table.getValueAt(r,3).toString()); }
    void bersih(){ txtKode.setText(""); txtNama.setText(""); txtStok.setText(""); }
    public static void main(String[] args){ new FormBarang().setVisible(true); }
}