package DAL;

import Models.*;
import java.sql.*;
import java.time.LocalDate;
import java.util.*;

public class DAO {

    private Connection con;
    private String status;
    private List<Products> pro;
    private List<Accounts> acc;
    private List<Categories> cate;
    private List<String> columnName;

    public DAO() {
        try {
            con = new DBContext().getConnection();
        } catch (Exception e) {
            status = "Error at Connection " + e.getMessage();
        }
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Products> getPro() {
        return pro;
    }

    public void setPro(List<Products> pro) {
        this.pro = pro;
    }

    public List<String> getColumnName() {
        return columnName;
    }

    public void setColumnName(List<String> columnName) {
        this.columnName = columnName;
    }

    public List<Categories> getCate() {
        return cate;
    }

    public void setCate(List<Categories> cate) {
        this.cate = cate;
    }

    public List<Accounts> getAcc() {
        return acc;
    }

    public void setAcc(List<Accounts> acc) {
        this.acc = acc;
    }

    public List<Products> loadProduct() {
        List<Products> pro = new ArrayList<>();
        String sql = "select * from ProductsHE172281";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                pro.add(new Products(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getFloat(4),
                        rs.getInt(5),
                        rs.getInt(6),
                        rs.getFloat(7),
                        rs.getString(8),
                        rs.getInt(9),
                        rs.getFloat(10)
                ));
            }

        } catch (Exception e) {
            status = "Error at read Products " + e.getMessage();
        }
        return pro;
    }

    public List<Products> loadProductOderBypIDcID() {
        List<Products> pro = new ArrayList<>();
        String sql = "select * from ProductsHE172281 "
                + "order by CategoryID, ProductID asc";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                pro.add(new Products(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getFloat(4),
                        rs.getInt(5),
                        rs.getInt(6),
                        rs.getFloat(7),
                        rs.getString(8),
                        rs.getInt(9),
                        rs.getFloat(10)
                ));
            }

        } catch (Exception e) {
            status = "Error at read Products " + e.getMessage();
        }
        return pro;
    }

    public int getTotalProduct() {
        String sql = "select count(*) from ProductsHE172281";
        try {
            con = new DBContext().getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return rs.getInt(1);
            }

        } catch (Exception e) {

        }
        return 0;
    }

    public List<Products> pagingProducts(int index) {
        List<Products> list = new ArrayList<>();
        String sql = "Select * from ProductsHE172281 "
                + "order by CategoryID asc, ProductID Offset ? rows fetch next 15 rows only;";
        // offset tu dong nao va tu dong day se lay may student
        try {
            con = new DBContext().getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, (index - 1) * 15);
            //khi click vao 1 thi no se offset bat dau tu 0 
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Products(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getFloat(4),
                        rs.getInt(5),
                        rs.getInt(6),
                        rs.getFloat(7),
                        rs.getString(8),
                        rs.getInt(9),
                        rs.getFloat(10)
                ));
            }
        } catch (Exception e) {
        }
        return list;
    }

    public List<Products> loadProductByCID(int cid) {
        List<Products> pro = new ArrayList<>();
        String sql = "select * from ProductsHE172281 where CategoryID = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, cid);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                pro.add(new Products(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getFloat(4),
                        rs.getInt(5),
                        rs.getInt(6),
                        rs.getFloat(7),
                        rs.getString(8),
                        rs.getInt(9),
                        rs.getFloat(10)
                ));
            }

        } catch (Exception e) {
            status = "Error at read Products " + e.getMessage();
        }
        return pro;
    }

    public List<Products> searchByPName(int cid, String name) {
        List<Products> pro = new ArrayList<>();
        String sql = "select * from ProductsHE172281 \n"
                + "where CategoryID = ? and ProductName like ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, cid);
            ps.setString(2, "%" + name + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                pro.add(new Products(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getFloat(4),
                        rs.getInt(5),
                        rs.getInt(6),
                        rs.getFloat(7),
                        rs.getString(8),
                        rs.getInt(9),
                        rs.getFloat(10)
                ));
            }

        } catch (Exception e) {
            status = "Error at read Products " + e.getMessage();
        }
        return pro;
    }

    public List<Products> searchAllByPName(String name) {
        List<Products> pro = new ArrayList<>();
        String sql = "select * from ProductsHE172281 \n"
                + "where ProductName like ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, "%" + name + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                pro.add(new Products(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getFloat(4),
                        rs.getInt(5),
                        rs.getInt(6),
                        rs.getFloat(7),
                        rs.getString(8),
                        rs.getInt(9),
                        rs.getFloat(10)
                ));
            }

        } catch (Exception e) {
            status = "Error at read Products " + e.getMessage();
        }
        return pro;
    }

    public Products loadProductByPID(int pid) {
        List<Products> pro = new ArrayList<>();
        String sql = "select * from ProductsHE172281 where ProductID = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, pid);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return new Products(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getFloat(4),
                        rs.getInt(5),
                        rs.getInt(6),
                        rs.getFloat(7),
                        rs.getString(8),
                        rs.getInt(9),
                        rs.getFloat(10)
                );
            }

        } catch (Exception e) {
            status = "Error at read Products " + e.getMessage();
        }
        return null;
    }

    public List<Categories> loadCategory() {
        List<Categories> cate = new ArrayList<>();
        String sql = "select * from CategoriesHE172281";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                cate.add(new Categories(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3)
                ));
            }
        } catch (Exception e) {
            status = "Error at read Products " + e.getMessage();
        }
        return cate;
    }

    public void getAllColumnName(String tableName) {
        columnName = new ArrayList<>();
        String sql = "SELECT column_name\n"
                + "FROM information_schema.columns\n"
                + "WHERE table_name = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, tableName);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String column = rs.getString("column_name");
                columnName.add(column);
            }
        } catch (Exception e) {
            status = "Error at get column name in table " + e.getMessage();
        }
    }

    public void Insert(int ProductID, String ProductName, String Category, String Price, int StockQuantity, int CategoryID, float Discount, String image_url, int Quantity, String Sellprice) {
        String sql = "Insert into ProductsHE172281 values(?,?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, ProductID);
            ps.setString(2, ProductName);
            ps.setString(3, Category);
            ps.setFloat(4, Float.parseFloat(Price));
            ps.setInt(5, StockQuantity);
            ps.setInt(6, CategoryID);
            ps.setFloat(7, Discount);
            ps.setString(8, image_url);
            ps.setInt(9, Quantity);
            ps.setFloat(10, Float.parseFloat(Sellprice));
            ps.execute();
        } catch (Exception e) {
            status = "Error at insert Products " + e.getMessage();
        }
    }

    public void InsertCategory(int CategoryID, String Category) {
        String sql = "Insert into CategoriesHE172281 values(?,?)";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, CategoryID);
            ps.setString(2, Category);
            ps.execute();
        } catch (Exception e) {
            status = "Error at insert Products " + e.getMessage();
        }
    }

    public void Update(int ProductID, String ProductName, String Category, float Price, int StockQuantity, int CategoryID, float Discount, String image_url, int Quantity, float Sellprice) {
        String sql = "UPDATE ProductsHE172281\n"
                + "SET [ProductName] = ?\n"
                + ",[Category] = ?\n"
                + ",[Price] = ?\n"
                + ",[StockQuantity] = ?\n"
                + ",[CategoryID] = ?\n"
                + ",[Discount] = ?\n"
                + ",[image_url] = ?\n"
                + ",[Quantity] = ?\n"
                + ",[Sellprice] = ? WHERE ProductID = ? ;";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, ProductName);
            ps.setString(2, Category);
            ps.setFloat(3, Price);
            ps.setInt(4, StockQuantity);
            ps.setInt(5, CategoryID);
            ps.setFloat(6, Discount);
            ps.setString(7, image_url);
            ps.setInt(8, Quantity);
            ps.setFloat(9, Sellprice);
            ps.setInt(10, ProductID);
            ps.execute();
        } catch (Exception e) {
            status = "Error at Update Products " + e.getMessage();
        }
    }

    public void Delete(int pid) {
        String sql = "delete from ProductsHE172281 where ProductID = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, pid);
            ps.execute();
        } catch (Exception e) {
            status = "Error at Update Products " + e.getMessage();
        }
    }

    public Accounts loadAccount(String user, String pass) {
        String sql = " select * from AccountsHE172281 \n"
                + "where [username] = ? and [password] = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, user);
            ps.setString(2, pass);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return new Accounts(rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getInt(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getString(7),
                        rs.getInt(8)
                );
            }
        } catch (Exception e) {
            status = "Error at read Products " + e.getMessage();
        }
        return null;
    }

    public Accounts checkAccount(String user) {
        String sql = " select * from AccountsHE172281 \n"
                + " where [username] = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, user);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return new Accounts(rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getInt(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getString(7)
                );
            }
        } catch (Exception e) {
            status = "Error at read Products " + e.getMessage();
        }
        return null;
    }

    public boolean checkUser(String user) {
        String sql = "SELECT COUNT(*) FROM AccountsHE172281 WHERE [username] = ?";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, user);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0; // Trả về true nếu có ít nhất một tài khoản có username tương ứng.
            }
        } catch (Exception e) {
            status = "Error at read Accounts " + e.getMessage();
        }

        return false; // Trả về false nếu có lỗi hoặc không tìm thấy tài khoản.
    }

    public boolean checkUserAndPass(String user, String pass) {
        String sql = "SELECT COUNT(*) FROM AccountsHE172281 WHERE [username] = ? and [password] = ? ";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, user);
            ps.setString(2, pass);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0; // Trả về true nếu có ít nhất một tài khoản có username tương ứng.
            }
        } catch (Exception e) {
            status = "Error at read Accounts " + e.getMessage();
        }

        return false; // Trả về false nếu có lỗi hoặc không tìm thấy tài khoản.
    }

    public void updateActive(int active, String user) {
        String sql = "UPDATE [dbo].[AccountsHE172281]\n"
                + "   SET [isActive] = ?\n"
                + " WHERE [username] = ?";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, active);
            ps.setString(2, user);
            ps.execute();
        } catch (Exception e) {
            status = "Error at Update Products " + e.getMessage();
        }
    }

    public void signUp(String user, String pass, String fname, String lname, String email) {
        String sql = "INSERT INTO [dbo].[AccountsHE172281]\n"
                + "           ([username]\n"
                + "           ,[password]\n"
                + "           ,[is_admin]\n"
                + "           ,[first_name]\n"
                + "           ,[last_name]\n"
                + "           ,[email]\n"
                + "           ,[isActive])\n"
                + "     VALUES\n"
                + "(?, ?, 0, ?, ?, ?, 0)";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, user);
            ps.setString(2, pass);
            ps.setString(3, fname);
            ps.setString(4, lname);
            ps.setString(5, email);
            ps.execute();
        } catch (Exception e) {
            status = "Error at insert Accounts " + e.getMessage();
        }
    }

    public void addOrder(Order od) {
        LocalDate curDate = java.time.LocalDate.now();
        String date = curDate.toString();
        String sql = "INSERT INTO [dbo].[OrdersHE172281] ([OrderDate],[account_id]) VALUES(?,?)";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, date);
            ps.setInt(2, od.getUserID().getAccount_id());
            ps.executeUpdate();
        } catch (Exception e) {
            status = "Error at insert Orders " + e.getMessage();
        }
    }

    public int getOrder() {
        int ordID = 0;
        String sql = "select top 1 OrderID from OrdersHE172281 order by OrderID desc";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                ordID = rs.getInt(1);
            }
        } catch (Exception e) {
            status = "Error at insert Orders " + e.getMessage();
        }
        return ordID;
    }

    public void addToOdItem(Items item, int orderId) {
        String sql1 = "INSERT INTO [dbo].[OrderItemsHE172281] "
                + "(OrderID, ProductID, ProductQuantity, Sellprice) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement ps = con.prepareStatement(sql1);
            ps.setInt(1, orderId);
            ps.setInt(2, item.getProduct().getProductID());
            ps.setInt(3, item.getProductQuantity());
            ps.setFloat(4, item.getProduct().getSellprice());
            ps.executeUpdate();
        } catch (Exception e) {
            status = "Error at insert Orders " + e.getMessage();
        }
    }

    public void updateOd(Order od, int oderID) {
        String sql = "UPDATE [dbo].[OrdersHE172281]\n"
                + "SET [TotalAmount] = ?\n"
                + "WHERE [OrderID] = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setFloat(1, od.getTotalAmount());
            ps.setInt(2, oderID);
            ps.executeUpdate();
        } catch (Exception e) {
            status = "Error at insert Orders " + e.getMessage();
        }
    }

    public void updateAdd(String location, int oderID) {
        String sql = "UPDATE [dbo].[OrdersHE172281]\n"
                + "SET [address] = ?\n"
                + "WHERE [OrderID] = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, location);
            ps.setInt(2, oderID);
            ps.executeUpdate();
        } catch (Exception e) {
            status = "Error at insert Orders " + e.getMessage();
        }
    }

    public void updateQuantity(int stockQuantity, int Quantity, int ProductID) {
        String sql = "UPDATE ProductsHE172281 SET \n"
                + "[StockQuantity] = ? "
                + ",[Quantity] = ? WHERE ProductID = ? ;";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, stockQuantity);
            ps.setInt(2, Quantity);
            ps.setInt(3, ProductID);
            ps.execute();
        } catch (Exception e) {
            status = "Error at Update Products " + e.getMessage();
        }
    }

    public static void main(String[] args) {
        DAO d = new DAO();
        d.updateAdd("Hanoi", 32);
    }
}
