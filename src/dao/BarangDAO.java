package dao;
import Model.Barang;
import java.sql.*;
public class BarangDAO {
    public boolean simpan(Barang b){
        try{
            Connection c = Koneksi.getConnection();
            PreparedStatement ps = c.prepareStatement("INSERT INTO barang VALUES (?,?,?,?)");
            ps.setString(1, b.getKodeBarang());
            ps.setString(2, b.getNamaBarang());
            ps.setString(3, b.getKategori());
            ps.setInt(4, b.getStok());
            ps.executeUpdate(); return true;
        }catch(Exception e){ return false; }
    }
    public boolean ubah(Barang b){
        try{
            Connection c = Koneksi.getConnection();
            PreparedStatement ps = c.prepareStatement("UPDATE barang SET nama_barang=?, kategori=?, stok=? WHERE kode_barang=?");
            ps.setString(1, b.getNamaBarang());
            ps.setString(2, b.getKategori());
            ps.setInt(3, b.getStok());
            ps.setString(4, b.getKodeBarang());
            ps.executeUpdate(); return true;
        }catch(Exception e){ return false; }
    }
    public boolean hapus(String kode){
        try{
            Connection c = Koneksi.getConnection();
            PreparedStatement ps = c.prepareStatement("DELETE FROM barang WHERE kode_barang=?");
            ps.setString(1, kode);
            ps.executeUpdate(); return true;
        }catch(Exception e){ return false; }
    }
}